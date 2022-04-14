package com.dimens.values;

import org.apache.http.util.TextUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class InputDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    public JLabel label;
    private JTextField textField1;
    private String path;

    public InputDialog(String path) {
        this.path = path;
        setContentPane(contentPane);
        setModal(true);
        setLocationRelativeTo(null);//设置居中
        getRootPane().setDefaultButton(buttonOK);
        setTitle("请输入需要生成文件的宽度、高度、缩放比");
        setMinimumSize(new Dimension(400, 400));
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        String value = textField1.getText();
        StringBuilder stringBuilder = new StringBuilder("出错了：");
        if (TextUtils.isEmpty(value)) {
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
            dispose();
        } else {
            stringBuilder.append("请输入内容：宽度x高度x缩放比，多条数据间使用英文字符;分割；例如：1080x1920x1.2;1440x1230x0.8");
        }
        if (!"出错了：".equals(stringBuilder.toString())) {
            label.setText(stringBuilder.toString());
        }
        label.setText(textField1.getText()+"  ss=="+textField1.getName()+" ds=="+textField1.getToolTipText()+" s6"+textField1.getSelectedText());

    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}
