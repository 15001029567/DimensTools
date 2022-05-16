package com.dimens.values;


import org.apache.http.util.TextUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.*;
import java.text.DecimalFormat;

/**
 * @author zhangjing
 * @创建时间 3/16/22 2:06 PM
 */
public class ParserXMLMakeDimesFiles {
    private static DecimalFormat doubleFormat = new DecimalFormat("0.00");

    public static String pullParser(String path, float scale) {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        try {
            XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
            //获得xml的解析器
            XmlPullParser parser = parserFactory.newPullParser();
            //给解析器设置一个输入源
            parser.setInput(new FileInputStream(new File(path)), "UTF-8");
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {

                //调用parser.next()方法解析下一个元素
                eventType = parser.next();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                    case XmlPullParser.END_TAG:
                        builder.append(parser.getText());
                        break;
                    case XmlPullParser.TEXT:
                        String text = parser.getText();
                        if (!TextUtils.isEmpty(text) && !TextUtils.isEmpty(text.trim())) {
                            String num = "0";
                            String unit = "px";
                            if (text.contains("@")) {
                                builder.append(text);
                            } else {
                                if (text.endsWith("px")) {
                                    num = text.substring(0, text.length() - 2);
                                } else if (text.endsWith("sp")) {
                                    num = text.substring(0, text.length() - 2);
                                    unit = "sp";
                                } else if (text.endsWith("dp")) {
                                    unit = "dp";
                                    num = text.substring(0, text.length() - 2);
                                } else if (text.endsWith("dip")) {
                                    unit = "dip";
                                    num = text.substring(0, text.length() - 3);
                                }
                                builder.append(Double.parseDouble(doubleFormat.format(Double.parseDouble(num) * scale))).append(unit);
                            }
                        } else {
                            builder.append(text);
                        }
                        break;
                    case XmlPullParser.END_DOCUMENT:
                        break;
                }

            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    //创建文件并写入内容
    public static void makeFile(String rootPath, int w, int h, float scale) {
        if (TextUtils.isEmpty(rootPath)) {
            return;
        }
        String basePath;
        if (rootPath.contains("dimens.xml")) {
            basePath = rootPath.replace("/dimens.xml", "").trim();
        } else {
            basePath = rootPath;
        }
        String path = basePath + "-" + w + "x" + h;
        File rootFile = new File(path);
        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }
        File file = new File(path, "dimens.xml");
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(file));
            pw.println(pullParser(basePath + "/dimens.xml", scale));
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //创建文件并写入内容
    public static void makeFileForSW(String rootPath, int w, float scale) {
        if (TextUtils.isEmpty(rootPath)) {
            return;
        }
        String targetPath;
        String baseFilePath;
        if (rootPath.contains("-sw")) {
            targetPath = rootPath.split("-sw")[0];
        } else if (rootPath.contains("dimens.xml")) {
            targetPath = rootPath.replace("/dimens.xml", "").trim();
        } else {
            targetPath = rootPath;
        }
        if (rootPath.contains("dimens.xml")) {
            baseFilePath = rootPath.replace("/dimens.xml", "").trim();
        } else {
            baseFilePath = rootPath;
        }
        String path = targetPath + "-sw" + w + "dp";
        File rootFile = new File(path);
        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }
        File file = new File(path, "dimens.xml");
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(file));
            pw.println(pullParser(baseFilePath + "/dimens.xml", scale));
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
