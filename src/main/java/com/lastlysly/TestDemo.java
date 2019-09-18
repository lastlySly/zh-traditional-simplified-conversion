package com.lastlysly;

import com.github.houbb.opencc4j.util.ZhConverterUtil;
import com.lastlysly.utils.MyFileUtils;
import com.lastlysly.utils.MyZHConverterUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-09-16 14:46
 **/
public class TestDemo {

    private static Properties charMap = new Properties();



    public static void main(String[] args) throws IOException, URISyntaxException {
        MyZHConverterUtils myZHConverterUtils = new MyZHConverterUtils();
        List<String> pathList = new ArrayList<String>(16);
        MyFileUtils.getFilesPathList("C:\\Users\\lastlySly\\Desktop\\test\\mytest\\test",pathList);

        for (String path : pathList){
            File file = new File(path);
            String content = FileUtils.readFileToString(file, "utf-8");
            System.out.println(content);

            String str = myZHConverterUtils.myConvertToTW(content);
            String result = ZhConverterUtil.convertToTraditional(str);

            FileUtils.write(new File("C:\\Users\\lastlySly\\Desktop\\test\\mytest\\test\\1.txt"),result,"UTF-8");

        }

        String original = "咪錶生命不息计价器，奋斗不止计价器";
        String str = myZHConverterUtils.myConvertToTW(original);
        String result = ZhConverterUtil.convertToTraditional(str);
        System.out.println(result);
    }




}
