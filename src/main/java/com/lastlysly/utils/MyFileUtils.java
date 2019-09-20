package com.lastlysly.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-09-16 15:43
 **/
public class MyFileUtils {

    public static List<String> folderNameList = new ArrayList<>();
    public static List<String> fileSuffixList = new ArrayList<>();

    static {
        folderNameList.add(".git");
        folderNameList.add(".svn");
        folderNameList.add(".idea");
//        fileSuffixList.add("iml");
//        fileSuffixList.add("css");
//        fileSuffixList.add("eot");
//        fileSuffixList.add("woff");
//        fileSuffixList.add("mp3");
//        fileSuffixList.add("ico");
//        fileSuffixList.add("ttf");

//
        fileSuffixList.add("java");
        fileSuffixList.add("js");
        fileSuffixList.add("vue");
        fileSuffixList.add("html");
        fileSuffixList.add("htm");
        fileSuffixList.add("jsp");
        fileSuffixList.add("xml");
        fileSuffixList.add("pom");
        fileSuffixList.add("properties");
        fileSuffixList.add("txt");
        fileSuffixList.add("md");
//        fileSuffixList.add();

//        fileSuffixList.add("png");
//        fileSuffixList.add("jif");
//        fileSuffixList.add("jpg");
    }

    /**
     * 判断是否为图片
     * @param file
     * @return
     */
    public static boolean isImg(File file){
        try {
            Image image = ImageIO.read(file);
            return image != null;
        } catch(IOException ex) {
            return false;
        }
    }

    /**
     * 获取某路径下所有文件路径
     * @param path
     * @return
     */
    public static void getFilesPathList(String path, List<String> filePathList) {
        File file = new File(path);
        // 如果这个路径是文件夹
        if (file.isDirectory()) {

            if (!folderNameList.contains(file.getName())){
                // 获取路径下的所有文件
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    // 如果还是文件夹 递归获取里面的文件 文件夹
                    if (files[i].isDirectory()) {
                        getFilesPathList(files[i].getPath(),filePathList);
                    } else {
                        String suffix = files[i].getName().substring(files[i].getName().lastIndexOf(".") + 1);
//                        if (!fileSuffixList.contains(suffix) && !MyFileUtils.isImg(files[i])){
//                            filePathList.add(files[i].getPath());
//                        }
                        if (fileSuffixList.contains(suffix.toLowerCase())){
                            filePathList.add(files[i].getPath());
                        }
                    }

                }
            }

        } else {
            String suffix = file.getName().substring(file.getName().lastIndexOf(".") + 1);
            if (fileSuffixList.contains(suffix.toLowerCase())){
                filePathList.add(file.getPath());
            }
        }
    }


    /**
     * 获取某路径下所有图片文件路径
     * @param path
     * @return
     */
    public static void getImgFilesPathList(String path, List<String> imgFilePathList) {
        File file = new File(path);
        // 如果这个路径是文件夹
        if (file.isDirectory()) {

            if (!folderNameList.contains(file.getName())){
                // 获取路径下的所有文件
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    // 如果还是文件夹 递归获取里面的文件 文件夹
                    if (files[i].isDirectory()) {
                        getImgFilesPathList(files[i].getPath(),imgFilePathList);
                    } else {
                        //如果是图片
                        if (MyFileUtils.isImg(files[i])){
                            imgFilePathList.add(files[i].getPath());
                        }
                    }
                }
            }
        } else {
            if (MyFileUtils.isImg(file)){
                imgFilePathList.add(file.getPath());
            }
        }
    }


}
