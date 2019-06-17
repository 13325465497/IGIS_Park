package com.isoft.igis.common.utils;

import java.io.File;

public class DeleteDirUtils {
    /**
     * 删除空目录
     *
     * @param dir 将要删除的目录路径
     */
    public static void doDeleteEmptyDir(String dir) {
        boolean success = (new File(dir)).delete();
        if (success) {
            System.out.println("Successfully deleted empty directory: " + dir);
        } else {
            System.out.println("Failed to delete empty directory: " + dir);
        }
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     * If a deletion fails, the method stops attempting to
     * delete and returns "false".
     */
    public static void deleteDir(File dir) {
        if (dir == null) {
            return;
        } else if (dir.exists()) {//如果此抽象指定的对象存在并且不为空。
            if (dir.isFile()) {
                boolean delete = dir.delete();//如果此抽象路径代表的是文件，直接删除。
                if (!delete) {
                    System.gc();
                    dir.delete();
                }
            } else if (dir.isDirectory()) {//如果此抽象路径指代的是目录
                String[] str = dir.list();//得到目录下的所有路径
                if (str == null) {
                    dir.delete();//如果这个目录下面是空，则直接删除
                } else {//如果目录不为空，则遍历名字，得到此抽象路径的字符串形式。
                    for (String st : str) {
                        deleteDir(new File(dir, st));
                    }//遍历清楚所有当前文件夹里面的所有文件。
                    dir.delete();//清楚文件夹里面的东西后再来清楚这个空文件夹
                }
            }
        }
    }
}
