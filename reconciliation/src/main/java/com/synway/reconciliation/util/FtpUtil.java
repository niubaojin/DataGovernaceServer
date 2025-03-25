package com.synway.reconciliation.util;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FTP工具类
 *
 * @author
 */
public class FtpUtil {
    private static Logger LOGGER = LoggerFactory.getLogger(FtpUtil.class);
    /**
     * 缺省的编码方式。
     */
    public static final String ENCODING_GBK = "GBK";

    public static final String ENCODING_GB2312 = "GB2312";

    public static final String ENCODING_UTF8 = "UTF-8";

    public static final String ENCODING_DEFAULT = ENCODING_UTF8;

    /**
     * 测试连接返回FtpClient
     *
     * @param ip IP
     * @param port 端口号
     * @param userName 用户名
     * @param password  密码
     */
    public static FTPClient testLinkAndReturnFtpClient(String ip, String port, String userName, String password) {
        FTPClient ftpClient = new FTPClient();
        try {
            int reply;
            ftpClient.setControlEncoding(ENCODING_UTF8);
            ftpClient.connect(ip, Integer.parseInt(port));
            ftpClient.setConnectTimeout(0);
            ftpClient.login(userName, password);
            ftpClient.setBufferSize(1024);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            // 获取ftp登录应答代码
            reply = ftpClient.getReplyCode();
            // 验证是否登陆成功
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                return null;
            }
        } catch (Exception e) {
            LOGGER.error("FTP连接失败：" + ExceptionUtil.getExceptionTrace(e));
            return null;
        }
        return ftpClient;
    }

    /**
     * 关闭FtpClient连接
     *
     * @param ftpClient
     * @return 操作结果
     */
    public static boolean closeFtpClientConnection(FTPClient ftpClient) {
        try {
            if (ftpClient != null && ftpClient.isConnected()) {
                ftpClient.logout();
            }
            return true;
        } catch (IOException e) {
            LOGGER.error(ExceptionUtil.getExceptionTrace(e));
            return false;
        } finally {
            try {
                if (ftpClient != null && ftpClient.isConnected()) {
                    ftpClient.disconnect();
                }
            } catch (IOException ioe) {
                LOGGER.error(ExceptionUtil.getExceptionTrace(ioe));
            }
        }
    }

    /**
     * 进入目录
     *
     * @param directory 目录
     * @param ftpClient
     */
    public static void cdDirectory(String directory, FTPClient ftpClient) throws Exception {
        FTPFile[] f = ftpClient.listFiles(directory);
        // 说明文件夹已经创建好，只需要进入目录
        if (f.length > 0) {
            ftpClient.changeWorkingDirectory(directory);
            return;
        }
        String regex = "[0-9a-zA-Z-_]+";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(directory);
        // 将目录按/分开并且一级一级地进去或者创建
        while (m.find()) {
            String dirName = m.group().substring(0, m.group().length());
            ftpClient.makeDirectory(dirName);
            ftpClient.changeWorkingDirectory(new String(dirName.getBytes(FTP.DEFAULT_CONTROL_ENCODING), "UTF-8"));
        }
    }

}
