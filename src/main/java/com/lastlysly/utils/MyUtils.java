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
        }
    }

}
