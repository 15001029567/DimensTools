package com.dimens.values;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InputDimensDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JSpinner spinner1;
    private JSpinner spinner2;
    private JSpinner spinner3;
    private String path;

    /**
     * Config jspinner's content as the given format
     * e.g.
     * 104.3322340234-->
     * 104.33
     *
     * @param spinner
     */
    public void ConfigSpinnerFloatFormat(JSpinner spinner) {
        JSpinner.NumberEditor numberEditor = new JSpinner.NumberEditor(spinner, "###0.00");
        spinner.setEditor(numberEditor);
    }

    public InputDimensDialog(String path) {
        this.path = path;
        setContentPane(contentPane);
        pack();
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
//        ConfigSpinnerFloatFormat(spinner3);
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        spinner3.setModel(new SpinnerNumberModel(1.00f, 0.1f, 100, 0.01));

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
//        Component[] components = contentPane.getComponents();
//        for (int i = 0; i < components.length; i++) {
//            Component component = components[i];
//            if (component instanceof JSpinner) {
//                onOkListener
//            }
//        }
        ParserXMLMakeDimesFiles.makeFile(path, ((Number) spinner1.getValue()).intValue(), ((Number) spinner2.getValue()).intValue(), ((Double) spinner3.getValue()).floatValue());
//        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    //
    private void createUIComponents() {
    }
}
