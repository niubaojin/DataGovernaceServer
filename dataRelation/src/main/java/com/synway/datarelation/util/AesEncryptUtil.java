package com.synway.datarelation.util;


import com.synway.common.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * AES加密解密的相关代码
 * @author wdw
 * @version 1.0
 * @date 2021/6/7 16:08
 */
@Slf4j
public class AesEncryptUtil {


    // 使用 AES-128-CBC加密方式，key需要16位，key和iv可以相同
    // 密钥的数据
    private static final  String KEY = "9751346784124879";
    // 密钥偏移量
    private static final String IV = "9787458731457896";

    /**
     *  加密方法
     * @param data  需要加密的数据
     * @return    加密的结果
     * @throws Exception
     */
    public static String encrypt(String data) {
        try{
            if(StringUtils.isBlank(data)){
                return "";
            }
            // 加密类型/模式/补码方式
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if(plaintextLength % blockSize != 0){
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes,0,plaintext,0,dataBytes.length);
            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(),"AES");
            IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE,keySpec,ivSpec);
            byte[] encrypted = cipher.doFinal(plaintext);
            return new Base64().encodeToString(encrypted);
        }catch (Exception e){
            log.error("数据加密报错："+ ExceptionUtil.getExceptionTrace(e));
            return "";
        }
    }

    /**
     * 解密
     * @param data
     * @return
     */
    public static String desEncrypt(String data){
        try{
            if(StringUtils.isBlank(data)){
                return "";
            }
            byte[] encrypted = new Base64().decode(data);
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(),"AES");
            IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.DECRYPT_MODE,keySpec,ivSpec);
            byte[] original = cipher.doFinal(encrypted);
            return new String(original, StandardCharsets.UTF_8).trim();
        }catch(Exception e){
            log.error("解密报错"+ExceptionUtil.getExceptionTrace(e));
            return "";
        }
    }

}
