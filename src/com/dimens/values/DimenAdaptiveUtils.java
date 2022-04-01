package com.dimens.values;

import java.io.*;

/**
 * @author zhangjing
 * @创建时间 3/11/22 12:03 PM
 * 批量生成dp值
 */
public class DimenAdaptiveUtils {
    public static void makeString(String rootPath, float scale) {
        File file = new File(rootPath);
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString;
            int line = 1;
            while ((tempString = reader.readLine()) != null) {
                if (tempString.contains("</dimen>")) {
                    //tempString = tempString.replaceAll(" ", "");
                    String start = tempString.substring(0, tempString.indexOf(">") + 1);
                    String end = tempString.substring(tempString.lastIndexOf("<") - 2);
                    //截取<dimen></dimen>标签内的内容，从>右括号开始，到左括号减2，取得配置的数字
                    try {
                        Double num = Double.parseDouble(tempString.substring(tempString.indexOf(">") + 1, tempString.indexOf("</dimen>") - 2));
                        //根据不同的尺寸，计算新的值，拼接新的字符串，并且结尾处换行。
                        builder.append(start).append(num * scale).append(end);
                    } catch (Exception e) {
                        System.out.println("write error " + tempString);
                        e.printStackTrace();
                    }
                } else {
                    builder.append(tempString).append("");
                }
                builder.append("\n");
                line++;
            }
            reader.close();
            //将新的内容，写入到指定的文件中去
            makeFile(rootPath, builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    //创建文件并写入内容
    private static void makeFile(String path, String text) {
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
