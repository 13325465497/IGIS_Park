package com.isoft.igis.common.utils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipUtils {

    /**
     * @param ZipPath     zip压缩包路径
     * @param parkDirPath //解压到指定路径
     * @return
     */
    public static boolean JieYa(String ZipPath, String parkDirPath, String rename) {
        try {
            //删除原有的数据(命名)
            File delete = new File(parkDirPath);
            if (delete.exists()) {
                File[] files = delete.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.getName().contains(rename)) {
                            file.delete();//删除原文件,上传新的
                        }
                    }
                }
            }
            ZipInputStream Zin = new ZipInputStream(new FileInputStream(
                    ZipPath), Charset.forName("GBK"));//输入源zip路径
            BufferedInputStream Bin = new BufferedInputStream(Zin);
            String Parent = parkDirPath; //输出路径（文件夹目录）
            File Fout = null;
            ZipEntry entry;
            try {
                //解压判断文件是否存在, 或者是不是文件夹  && !entry.isDirectory()
                while ((entry = Zin.getNextEntry()) != null) {
                    String entryName = entry.getName();
                    if (entryName.contains("/") || entry.isDirectory()) {//如果是文件夹就按原名称创建
                        String start = entry.getName().substring(0, entry.getName().lastIndexOf("/"));
                        String end = "";
                        if (start.contains(".")) {
                            end = entry.getName().substring(entry.getName().lastIndexOf("."));
                        }
                        String path = "";
                        if (rename.equals("")) {
                            path = entry.getName();
                        } else {
                            path = start + "/" + rename + end;
                        }
                        Fout = new File(Parent, path);
                    } else {//如果是文件就按照格式创建
                        String fileName = entry.getName();
                        if (!rename.equals("")) {
                            fileName = rename + fileName.substring(fileName.lastIndexOf("."));
                        }
                        Fout = new File(Parent, fileName);
                    }
                    //文件不存在则创建
                    if (!Fout.exists()) {
                        String name = Fout.getName();
                        if (name.contains(".")) {//是文件
                            (new File(Fout.getParent())).mkdirs();
                        } else {
                            Fout.mkdir();//是文件
                            continue;
                        }
                    }

                    FileOutputStream out = new FileOutputStream(Fout);
                    BufferedOutputStream Bout = new BufferedOutputStream(out);
                    int b;
                    while ((b = Bin.read()) != -1) {
                        Bout.write(b);
                    }
                    Bout.close();
                    out.close();
                }
                Bin.close();
                Zin.close();
                File file = new File(ZipPath);
                file.delete();
            } catch (IOException e) {
                return false;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
