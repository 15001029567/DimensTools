package com.dimens.values;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * @author zhangjing
 * @创建时间 3/11/22 12:03 PM
 */
public class DimenUtils {
    private final static String rootPath = "./app/src/main/res/com.dimens.values-{0}x{1}";
    private final static int defaultWidth = 1440;//默认布局的宽
    private final static int defaultHeight = 1728;//默认布局的高
    private final static String WTemplate = "<dimen name=\"dp_{0}\">{1}px</dimen>\n";

    public static void main(String[] args) {
        makeString(1440,1728);
    }

    public static void makeString(int w, int h) {
        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        sb.append("<resources>");

        //遍历获取一系列宽的值
        float cellw = w / defaultWidth;//宽的比例
        for (int i = 0; i < defaultWidth; i++) {
            sb.append(WTemplate.replace("{0}", i + "").replace("{1}",
                    cellw * i + ""));
        }

        sb.append(WTemplate.replace("{0}", defaultWidth + "").replace("{1}", w + ""));

        //遍历获取一系列高的值
        float cellh = h / defaultHeight;//高的比例
//        for (int i = 0; i <dh ; i++) {
//            sb.append(HTemplate.replace("{0}",i + "").replace("{1}",
//                    change(cellh * i) + ""));
//        }
//
//        sb.append(HTemplate.replace("{0}",dh+"").replace("{1}", h+ ""));
        sb.append("</resources>");

        makeFile(w, h, sb.toString());
    }

    //创建文件并写入内容
    private static void makeFile(int w, int h, String text) {
        String path = rootPath.replace("{0}", w + "").replace("{1}", h + "");
        File rootFile = new File(path);
        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }
        File file = new File(path, "dimens.xml");
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(file));
            pw.println(text);
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
