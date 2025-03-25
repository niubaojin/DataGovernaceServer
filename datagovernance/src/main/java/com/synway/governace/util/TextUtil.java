package com.synway.governace.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @ClassName TextUtil
 * @Descroption TODO
 * @Author majia
 * @Date 2020/5/15 18:19
 * @Version 1.0
 **/
public class TextUtil {

    public static String readText(MultipartFile file) throws Exception{
        InputStream input = file.getInputStream();
        StringBuilder stringBuilder = new StringBuilder();
        String code = resolveCode(file);
        InputStreamReader reader = new InputStreamReader(input,code);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String s ="";
        while((s=bufferedReader.readLine())!=null){
            stringBuilder.append(System.lineSeparator()+s);
        }
        bufferedReader.close();
        return stringBuilder.toString();
    }

    public static String textToHtml(MultipartFile file) throws Exception{
        InputStream input = file.getInputStream();
        StringBuilder stringBuilder = new StringBuilder();
        String code = resolveCode(file);
        InputStreamReader reader = new InputStreamReader(input,code);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String s ="";
        while((s=bufferedReader.readLine())!=null){
            StringBuilder sb = new StringBuilder();
            if(StringUtils.isNotEmpty(s)){
                sb.append("<p>");
                for (char c:s.toCharArray()){
                    switch (c) {
                        case ' ':
                            sb.append("&nbsp;");
                            break;
                        default:
                            sb.append(c);
                    }
                }
                sb.append("</p>");
            } else {
                sb.append("<p></br></p>");
            }
            stringBuilder.append(sb);
        }
        bufferedReader.close();
        return stringBuilder.toString();
    }

    public static String resolveCode(MultipartFile file) throws Exception {
        InputStream input = file.getInputStream();
        byte[] head = new byte[3];
        input.read(head);
        String code = "gbk";
        if(head[0] == -1&& head[1]==-2) {
            code = "UTF-16";
        }else if(head[0]==-2&&head[1]==-1){
            code = "Unicode";
        }else if(head[0]==-17&&head[1]==-69&&head[2]==-65){
            code = "UTF-8";
        }
        input.close();
        return code;
    }

}
