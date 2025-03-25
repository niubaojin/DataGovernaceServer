package com.synway.governace.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtil {
    private static Logger logger = Logger.getLogger(ImageUtil.class);

    public static String imageToBase64Head(String imgFile){
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        String type = imgFile.substring(imgFile.length()-3,imgFile.length());
        //为编码添加头文件字符串
        String head = "data:image/"+type+";base64,";

        return head + imageToBase64(imgFile);
    }

    public static String imageToBase64(String path){
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理

        InputStream in = null;
        byte[] data = null;

        try {
            //读取图片字节数组
            in = new FileInputStream(path);
            data = new byte[in.available()];
            in.read(data);
        }
        catch (IOException e) {
            logger.error("图片转换失败：", e);
        } finally {
            if(in!=null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("图片转换失败：", e);
                }
            }
        }
        return new String(Base64.encodeBase64(data));
    }

}
