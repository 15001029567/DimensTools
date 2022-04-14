package com.dimens.values;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import org.apache.http.util.TextUtils;

import javax.swing.*;
import java.util.ArrayList;

public class BuildDimesFiles extends AnAction {
    private String path;

    @Override
    public void actionPerformed(AnActionEvent e) {
        VirtualFile data = CommonDataKeys.VIRTUAL_FILE.getData(e.getDataContext());
        if (data == null) {
            System.out.println("file is empty");
            return;
        }
        path = data.getPath();
        showInputDialog();
    }

    private void showInputDialog() {
        String value = JOptionPane.showInputDialog(null, "例如：1440x1088x3.0;640x340x1", "请输入分辨率宽度、高度、缩放比字符串", JOptionPane.INFORMATION_MESSAGE);
        StringBuilder stringBuilder = new StringBuilder("出错了：");
        if (!TextUtils.isEmpty(value)) {
            String trim = value.trim();
            ArrayList<DimensBean> dimensBeans = new ArrayList<>();
            String[] split = trim.split(";");
            for (int i = 0; i < split.length; i++) {
                String[] xes = split[i].split("x");
                if (xes.length == 3) {
                    DimensBean dimensBean = new DimensBean(Integer.parseInt(xes[0]), Integer.parseInt(xes[1]), Float.parseFloat(xes[2]));
                    dimensBeans.add(dimensBean);
                } else {
                    stringBuilder.append(split[i]);
                }
            }
            for (int i = 0; i < dimensBeans.size(); i++) {
                DimensBean dimensBean = dimensBeans.get(i);
                ParserXMLMakeDimesFiles.makeFile(path, dimensBean.getWidth(), dimensBean.getHeight(), dimensBean.getScale());
            }
        } else {
            stringBuilder.append("请输入内容：宽度x高度x缩放比，多条数据间使用英文字符;分割；例如：1080x1920x1.2;1440x1230x0.8");
        }
        if (!"出错了：".equals(stringBuilder.toString())) {
            int i = JOptionPane.showConfirmDialog(null, stringBuilder.toString(), "", JOptionPane.ERROR_MESSAGE);
            if (JOptionPane.OK_OPTION == i) {
                showInputDialog();
            }
        }
    }
}

