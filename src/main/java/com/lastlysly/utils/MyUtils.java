package com.lastlysly.utils;

import com.github.houbb.opencc4j.util.ZhConverterUtil;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-09-17 09:29
 **/
public class MyUtils {


    /**
     * 扫描文件夹并进行转换
     * @param myZHConverterUtils
     * @param filePath
     * @throws IOException
     */
    public static void scanFolderAndConver(MyZHConverterUtils myZHConverterUtils, String filePath, String converType, JTextArea jTextArea) throws IOException {
        List<String> pathList = new ArrayList<String>(16);
        MyFileUtils.getFilesPathList(filePath,pathList);
        jTextArea.append("非图片文件转换开始：\n");
        for (String path : pathList){
            jTextArea.append("开始转换："+path + "\n");
            System.out.println(path);
            File file = new File(path);
            String content = FileUtils.readFileToString(file, "utf-8");

            if (converType.equals(CommonConstants.zhSimple2zhTw)){
                String str = myZHConverterUtils.myConvertToTW(content);
                String result = ZhConverterUtil.convertToTraditional(str);
                FileUtils.write(new File(path),result,"UTF-8");
            }else{
                String str = myZHConverterUtils.myConvertToSimple(content);
                String result = ZhConverterUtil.convertToSimple(str);
                FileUtils.write(new File(path),result,"UTF-8");
            }
            jTextArea.append("转换完成："+path + "\n");
            jTextArea.setCaretPosition(jTextArea.getText().length());
        }
        jTextArea.append("非图片文件转换结束：\n");
        jTextArea.append("==========================================================================：\n");
        jTextArea.append("==========================================================================：\n");


        jTextArea.append("图片文件文件名转换，并复制开始：\n");
        List<String> imgPathList = new ArrayList<String>(16);
        MyFileUtils.getImgFilesPathList(filePath,imgPathList);
        for(String path : imgPathList){
            System.out.println(path);
            File imgFile = new File(path);
            String imgFileName = imgFile.getName();
            String newImgFileName = "";
            if (converType.equals(CommonConstants.zhSimple2zhTw)){
                newImgFileName = myZHConverterUtils.myConvertToTW(imgFileName);
                newImgFileName= ZhConverterUtil.convertToTraditional(newImgFileName);
                //如果转换后图片名不同，则复制这张图片并改名为新名称
                if (!imgFileName.equals(newImgFileName)){
                    String newFilePath = imgFile.getParent() + "/" + newImgFileName;
                    FileUtils.copyFile(imgFile,new File(newFilePath));
                    System.out.println("新图片："+newFilePath);
                    jTextArea.append("图片复制完成："+path + "\n");
                }
            }else{
                newImgFileName = myZHConverterUtils.myConvertToSimple(imgFileName);
                newImgFileName= ZhConverterUtil.convertToSimple(newImgFileName);
                //如果转换后图片名不同，则复制这张图片并改名为新名称
                if (!imgFileName.equals(newImgFileName)){
                    String newFilePath = imgFile.getParent() + "/" + newImgFileName;
                    FileUtils.copyFile(imgFile,new File(newFilePath));
                    System.out.println("新图片："+newFilePath);
                    jTextArea.append("图片复制完成："+path + "\n");
                }
            }

            jTextArea.append("图片操作完成："+path + "\n");
            jTextArea.setCaretPosition(jTextArea.getText().length());
        }

        jTextArea.append("图片文件文件名转换，并复制结束：\n");
    }

}
