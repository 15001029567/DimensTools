package com.dimens.values;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.vfs.VirtualFile;

import javax.swing.*;

public class BuildDimesFiles extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        VirtualFile data = CommonDataKeys.VIRTUAL_FILE.getData(e.getDataContext());
        if (data == null) {
            System.out.println("file is empty");
            return;
        }
        InputDimensDialog dialog = new InputDimensDialog(data.getPath());
        dialog.pack();
        dialog.setVisible(true);
    }
}

