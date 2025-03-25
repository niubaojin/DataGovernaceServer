/**
 * @module id:
 * @module name:
 * @description:
 * @version:
 * @relative module: (package.class列表)
 * <p>
 * ========History Version=========
 * @version:
 * @modification:
 * @reason:
 * @date:
 * @author: --------------------------------
 * @version:
 * @modification:
 * @reason:
 * @date:
 * @author: ================================
 */

package com.synway.property.util;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @description: 文本文件操作工具类
 * @author: lht
 * @email: lht@five.net
 * @date: 2010-3-11
 */
public class FileUtil {
    private static Logger log = LoggerFactory.getLogger(FileUtil.class);
    //带文件名后缀
    public static boolean HAS_FILENAME_SUFFIX = true;
    /**
     * 不带文件名后缀
     */
    public static boolean NO_FILENAME_SUFFIX = false;
    //连带目录自身删除
    public static boolean DEL_SELF_AND_CONTENTS = true;
    //只删除目录内容
    public static boolean DEL_CONTENTS = false;
    //级联查找
    public static boolean CASCADE = true;
    //不级联
    public static boolean NO_CASCADE = false;

    /**
     * 创建新的随机访问文件
     * @title: createRandomAccessFile
     * @author: lht
     * @date: 2010-4-20
     * @param fileName
     * @param mode
     * @return
     * @return: RandomAccessFile
     * @throws
     */
    public static RandomAccessFile createRandomAccessFile(String fileName, String mode) {
        File file = new File(fileName);
        try {
            return new RandomAccessFile(file, mode);
        } catch (FileNotFoundException e) {
            log.error(ExceptionUtil.getExceptionTrace(e));
        }
        return null;
    }

    /**
     * 序列化对象
     * @return
     */
    public static boolean serialize(String fileName, Object obj) {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream(fileName));
            out.writeObject(obj);
        } catch (IOException e) {
            log.error(ExceptionUtil.getExceptionTrace(e));
//			e.printStackTrace();
        } finally {
            close(out);
        }
        return false;
    }

    /**
     * 反序列化对象
     * @return
     */
    public static Object reSerialize(String fileName) {
        ObjectInputStream in = null;
        Object obj = new Object();
        try {
            in = new ObjectInputStream(new FileInputStream(fileName));
            obj = in.readObject();
        } catch (IOException e) {
            log.error(ExceptionUtil.getExceptionTrace(e));
//			e.printStackTrace();
        } catch (ClassNotFoundException e) {
            log.error(ExceptionUtil.getExceptionTrace(e));
//			e.printStackTrace();
        } finally {
            close(in);
        }
        return obj;
    }

    /**
     * 检查目录是否存在，不存在则创建
     * @title: checkFolder
     * @author: lht
     * @date: 2010-3-15
     * @param folder 被检查目录
     * @param defaultValue 默认值
     * @return
     * @return: String
     * @throws
     */
    public static String checkFolder(String folder, String defaultValue) {
        if (folder == null || folder.trim().length() == 0) {
            folder = defaultValue;
        }
        // 目录不存在自动创建
        File file = new File(folder);
        if (!file.exists()) {
            file.mkdirs();
        }
        return folder;
    }

    /**
     * 获取指定目录文件
     * @param path
     * @return
     */
    public static File getFile(String path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                return file;
            }
        } catch (Exception e) {
        }
        return null;
    }


    /**
     * 删除单个目录
     * 可删除目录下所有内容，可连带自身删除
     * @author mjb
     * @date 2015-7-10
     * @param dir 删除目录
     * @param isDelSelf 是否连带自身删除
     * @return
     */
    public static boolean delDir(String dir, boolean isDelSelf) {
        List<String> dirs = new ArrayList<String>();
        dirs.add(dir);
        boolean flag = delDir(dirs, isDelSelf);
        return flag;
    }

    /**
     * 删除目录
     * 可删除目录下所有内容，可连带自身删除
     * @title: delDir
     * @author: lht
     * @date: 2013-6-8
     * @param dirs 被删除对象
     * @param isDelSelf 是否连带自身删除
     * @return
     * @return: boolean
     * @throws
     */
    public static boolean delDir(List<String> dirs, boolean isDelSelf) {
        boolean flag = true;
        if (dirs == null || dirs.size() == 0) {
            return false;
        }
        List<String> list = null;
        try {
            for (String dir : dirs) {
                File d = new File(dir);
                if (!d.exists()) {
                    continue;
                }
                if (!d.isDirectory()) {
                    continue;
                }
                if (isDelSelf) {
                    list = new ArrayList<String>();
                    list.add(dir);
                    delFile(list);
                } else {
                    list = new ArrayList<String>();
                    File[] files = d.listFiles();
                    for (File file : files) {
                        list.add(file.getAbsolutePath());
                    }
                    delFile(list);
                }
            }
        } catch (Exception e) {
//			e.printStackTrace();
            log.error(ExceptionUtil.getExceptionTrace(e));
            flag = false;
        }
        return flag;
    }

    /**
     * 删除空目录
     * @title delEmptyDir
     * @author yy
     * @date 2015-2-3
     * @return
     */
    public static void delEmptyDir(File path) {
        if (path.exists() && path.isDirectory()) {
            File[] children = path.listFiles();
            if (children != null && children.length > 0) {
                for (File child : children) {
                    delEmptyDir(child);
                }
                if (!path.delete()) {
                    log.error("删除失败" + path.getAbsolutePath());
                }
            } else {
                if (!path.delete()) {
                    log.error("删除失败" + path.getAbsolutePath());
                }
            }
        }

    }

    /**
     * 删除空目录
     * @author: lht
     * @date: 2015-8-13 下午09:17:11
     * @param path
     * @param isDelSelf 是否删除自身
     * @return: void
     */
    public static void delEmptyDir(File path, boolean isDelSelf) {
        if (path.exists() && path.isDirectory()) {
            File[] children = path.listFiles();
            if (children != null && children.length > 0) {
                for (File child : children) {
                    delEmptyDir(child);
                }
                if (isDelSelf) {
                    if (!path.delete()) {
                        log.error("删除失败" + path.getAbsolutePath());
                    }
                }
            } else {
                if (isDelSelf) {
                    if (!path.delete()) {
                        log.error("删除失败" + path.getAbsolutePath());
                    }
                }
            }
        }
    }

    /**
     * 删除空目录
     * @author: lht
     * @date: 2015-8-13 下午09:17:11
     * @param path
     * @param isDelSelf 是否删除自身
     * @return: void
     */
    public static void delEmptyDirNoFin(File path, boolean isDelSelf) {
        if (path.exists() && path.isDirectory()) {
            File[] children = path.listFiles();
            if (children != null && children.length > 0) {
                for (File child : children) {
                    if (child.toString().indexOf("finished") != -1) {
                        continue;
                    }
                    delEmptyDir(child);
                }
                if (isDelSelf) {
                    if (!path.delete()) {
                        log.error("删除失败" + path.getAbsolutePath());
                    }
                }
            } else {
                if (isDelSelf) {
                    if (!path.delete()) {
                        log.error("删除失败" + path.getAbsolutePath());
                    }
                }
            }
        }
    }

    /**
     * 删除文件
     * 如果是目录，连同删除目录下内容
     * @title: delFile
     * @author: lht
     * @date: 2013-3-28
     * @param files
     * @return
     * @return: boolean
     * @throws
     */
    public static boolean delFile(List<String> files) {
        boolean flag = true;
        if (files == null || files.size() == 0) {
            return true;
        }
        List<File> newList = new ArrayList<File>();
        for (String file : files) {
            File f = new File(file);
            if (f.exists()) {
                newList.add(f);
            }
        }
        flag = delFile(newList, true);
        return flag;
    }

    /**
     * 删除单个文件
     * @param file
     * @return
     */
    public static boolean delFile(File file) {
        boolean flag = true;
        if (file.exists()) {
            try {
                flag = file.delete();
            } catch (Exception e) {
                flag = false;
            }
        }
        return flag;
    }

    /**
     * 删除文件
     * @author: lht
     * @date: 2015-8-9下午04:23:30
     * @param files 文件列表
     * @param cascade 是否删除目录
     * @return
     * @return boolean
     */
    public static boolean delFile(List<File> files, boolean cascade) {
        boolean flag = true;
        if (files == null || files.size() == 0) {
            return true;
        }
        for (File f : files) {
            if (f.exists()) {
                try {
                    if (cascade && f.isDirectory()) {
                        File[] fs = f.listFiles();
                        List<String> list = new ArrayList<String>();
                        for (File iFile : fs) {
                            list.add(iFile.getAbsolutePath());
                        }
                        delFile(list);

                        if (!f.delete()) {
                            log.error("删除失败" + f.getAbsolutePath());
                        }

                    } else {
                        if (!f.delete()) {
                            log.error("删除失败" + f.getAbsolutePath());
                        }
                    }
                    log.debug("文件" + f.getAbsolutePath() + "已经被删除！");
                } catch (Exception e) {
                    flag = false;
                    log.error(ExceptionUtil.getExceptionTrace(e));
                }
            }
        }
        return flag;
    }

    /**
     * 获取文件路径中的文件名
     * @param filePath 文件全路径
     * @param hasSuffix 是否返回带后缀的文件名
     * @return
     */
    public static String getFileName(String filePath, boolean hasSuffix) {
        String fileName = null;
        if (filePath == null || filePath.trim().length() == 0) {
            return fileName;
        }
        filePath = filePath.replace("\\", "/");
        String temp = filePath.substring(filePath.lastIndexOf("/") + 1);
        if (hasSuffix) {
            fileName = temp;
        } else {
            fileName = temp.substring(0, temp.lastIndexOf("."));
        }
        return fileName;
    }

    /**
     * 获取文件路径中的文件名
     * @author: lht
     * @date: 2013-3-28
     * @param filePath
     * @param hasPoint
     * @return
     * @return: String
     * @throws
     */
    public static String getFileType(String filePath, boolean hasPoint) {
        String suffix = null;
        if (filePath == null || filePath.trim().length() == 0) {
            return suffix;
        }
        filePath = filePath.replace("\\", "/");
        String temp = filePath.substring(filePath.lastIndexOf("."));
        if (hasPoint) {
            suffix = temp;
        } else {
            suffix = temp.substring(1);
        }
        return suffix;
    }

    /**
     * 安全创建目录。包括不存在的父目录。
     *
     * @param path
     *            目录。
     * @return 规范的文件形式。
     * @throws Exception
     */
    public static File CreatePath(File path) throws Exception {
        File goodPath = path.getCanonicalFile();
        if (goodPath.exists()) {
            if (goodPath.isFile()) {
                throw new InvalidParameterException("指定参数不是有效目录：" + path.getPath());
            }
            return path;
        }
        File topPath = goodPath;
        while (!topPath.exists()) {
            topPath = topPath.getParentFile();
        }
        if (topPath.exists()) {
            if (!goodPath.mkdirs()) {
                throw new IOException("创建目录失败。" + goodPath);
            }
        }
        return goodPath;
    }


    /**
     * 安全创建目录。包括不存在的父目录。
     *
     * @param path
     *            目录。
     * @return 规范的文件形式。
     * @throws Exception
     */
    public static File CreatePath(String path) throws Exception {
        return CreatePath(new File(path));
    }

    /**
     * 获取空目录
     * @title: getEmptyDirs
     * @author: lht
     * @date: 2014-4-17
     * @param homeDir
     * @param cascade 是否级联查询
     * @return
     * @return: List<String>
     * @throws
     */
    public static List<String> getEmptyDirs(String homeDir, boolean cascade) {
        List<String> list = new ArrayList<String>();
        //目录
        File dir = new File(homeDir);
        if (!dir.exists()) {
            log.error("目录不存在！");
            return list;
        }
        //文件过滤
        File[] files = dir.listFiles();
        if (files == null || files.length <= 0) {
            return list;
        }
        //文件数或目录数
        int size = files.length;
        //遍历
        for (int i = 0; i < size; i++) {
            File file = files[i];
            if (!file.isDirectory()) {
                continue;
            }
            String fileName = file.getName();
            String filePath = homeDir + File.separator + fileName;
            if (cascade && file.listFiles().length > 0) {
                list.addAll(getEmptyDirs(filePath, cascade));
            } else {
                list.add(filePath);
            }
        }
        return list;
    }

    /**
     *
     * @title: readFirstLine
     * @description: 读取文件的首行
     * @author: lht
     * @date: 2010-3-15
     * @param filepath
     * @return
     * @return: String
     * @throws
     */
    public static String readFirstLine(String filepath) {
        String data = null;
        BufferedReader br = null;
        File file = new File(filepath);
        if (file.exists()) {
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(filepath)));
                data = br.readLine();
            } catch (Exception e) {
                log.error("读取文件出错！");
                log.error(ExceptionUtil.getExceptionTrace(e));
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (Exception e) {
                    log.error(ExceptionUtil.getExceptionTrace(e));
                }
            }
        }
        return data;
    }

    public static List<String> readFileContent(String filepath, String encoding) {
        if (encoding == null) {
            encoding = "UTF-8";
        }
        ArrayList<String> list = new ArrayList<String>();
        File file = new File(filepath);
        if (!file.exists()) {
            log.error("读取的文件[" + filepath + "]不存在!");
            return list;
        }
        String data = null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
            while ((data = br.readLine()) != null) {
                list.add(data);
            }
        } catch (IOException e) {
            log.error("读取文件出错！");
            log.error(ExceptionUtil.getExceptionTrace(e));
        } finally {
            close(br);
        }
        return list;
    }

    /**
     *
     * @title: writeToFile
     * @description: 向文件写入信息（覆盖）
     * @author: lht
     * @date: 2010-3-15
     * @param filepath
     * @param msg
     * @param append
     * @return: void
     * @throws
     */
    public static void writeToFile(String filepath, String msg, boolean append) {
        FileWriter fw = null;
        File file = new File(filepath);
        if (!file.exists()) {
            try {
                if(!file.createNewFile()){
                	log.error("未创建新文件");
				}
            } catch (IOException e) {
                log.error(ExceptionUtil.getExceptionTrace(e));
            }
        }
        if (file.exists()) {
            try {
                fw = new FileWriter(filepath, append);
                fw.write(msg, 0, msg.length());
                fw.flush();
            } catch (Exception e) {
                log.error(ExceptionUtil.getExceptionTrace(e));
            } finally {
                try {
                	if(fw!=null){
						fw.close();
					}
                } catch (Exception e) {
                    log.error(ExceptionUtil.getExceptionTrace(e));
                }
            }
        }
    }

    /**
     * 向文件写入信息 行唯一
     * @title: writeToFileUniqueLine
     * @author: lht
     * @date: 2010-7-17
     * @param filepath
     * @param msg
     * @return: void
     * @throws
     */
    public static void writeToFileUniqueLine(String filepath, String msg) {
        FileWriter fw = null;
        File file = new File(filepath);
        if (!file.exists()) {
            try {
				if(!file.createNewFile()){
					log.error("未创建新文件");
				}
            } catch (IOException e) {
                log.error(ExceptionUtil.getExceptionTrace(e));
            }
        }
        if (file.exists()) {
            try {
                fw = new FileWriter(filepath, true);
                fw.write(msg, 0, msg.length());
                fw.flush();
            } catch (Exception e) {
                log.error(ExceptionUtil.getExceptionTrace(e));
            } finally {
                try {
					if(fw!=null){
						fw.close();
					}
                } catch (Exception e) {
                    log.error(ExceptionUtil.getExceptionTrace(e));
                }
            }
        }
    }

    /**
     * 创建一个新文件
     * @author: lht
     * @date: 2010-3-15
     * @param filepath
     * @return: File
     */
    public static File createNewFile(String filepath) {
        File file = new File(filepath);
        File dir = new File(file.getParent());
        if (!file.exists()) {
            try {
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                if (!file.createNewFile()) {
                    throw new Exception("文件创建失败！");
                }
            } catch (Exception e) {
                log.error(ExceptionUtil.getExceptionTrace(e));
            }
        }
        return file;
    }

    /**
     *
     * @title: creatNewFolder
     * @description: 创建一个目录
     * @author: lht
     * @date: 2010-3-15
     * @param filepath
     * @return: void
     * @throws
     */
    public static void creatNewFolder(String filepath) {
        File file = new File(filepath);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    /**
     * 创建文件夹，如果上级文件夹不存在则创建
     * @author: lht
     * @date: 2012-2-24
     * @param path
     * @return: void
     */
    public static boolean md(String path) {
        boolean flag = true;
        if (path == null || path.trim().length() == 0) {
            return false;
        }
        path = path.replace("\\", "/");
        File file = new File(path);
        file.mkdirs();
        return flag;
    }

    /**
     * 查找文件
     * @title: searchFile
     * @author: lht
     * @date: 2010-5-26
     * @param folderPath  要搜索的文件夹
     * @param fileType  搜索的文件类型 "txt,xls"
     * @param cascade  是否级联
     * @return
     * @return: ArrayList<String>
     * @throws
     */
    public static ArrayList<String> searchFile(String folderPath, String fileType, boolean cascade, List<String> excludeSubFolders) {
        ArrayList<String> fileList = new ArrayList<String>();
        File[] files = null;
        File myDir = new File(folderPath);

        files = myDir.listFiles();
        ;
        if (files == null || files.length == 0) {
            return fileList;
        }

        try {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory() == true) {//目录
                    if (cascade) {//扫描子文件夹中的文件
                        if (excludeSubFolders != null && excludeSubFolders.size() > 0) {//需要排除特定目录
                            if (!excludeSubFolders.contains(files[i].getName())) {//当前目录不在排除列表内
                                ArrayList<String> subFileList = searchFile(files[i].getAbsolutePath(), fileType, cascade, excludeSubFolders);
                                if (subFileList != null && !subFileList.isEmpty()) {
                                    fileList.addAll(subFileList);
                                } else {
                                    //log.info("空文件夹"+files[i].getAbsolutePath()+"删除成功"+files[i].delete());
                                }
                            }
                        } else {//不需要排除
                            ArrayList<String> subFileList = searchFile(files[i].getAbsolutePath(), fileType, cascade, excludeSubFolders);
                            if (subFileList != null && !subFileList.isEmpty()) {
                                fileList.addAll(subFileList);
                            } else {
                                //log.info("空文件夹"+files[i].getAbsolutePath()+"删除成功"+files[i].delete());
                            }
                        }
                    } else {
                        continue;
                    }
                } else {
                    if (fileType == null || fileType.trim().length() == 0) {
                        String filePath = files[i].getAbsolutePath().replace(File.separator.charAt(0), '/');
                        fileList.add(filePath);
                    } else {
                        String[] fileTypes = fileType.toLowerCase().split(",");
                        if (isContion(fileTypes, files[i].getAbsolutePath())) {
                            String filePath = files[i].getAbsolutePath().replace(File.separator.charAt(0), '/');
                            fileList.add(filePath);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionTrace(e));
        }
        return fileList;

    }

    /**
     * 查找文件并删除空目录
     * 修改YTZ版
     * @title: searchFileBydel
     * @author: lht
     * @date: 2011-9-26
     * @param folderPath 数据文件所在位置
     * @param fileType 文件类型
     * @param cascade 是否级联查找
     * @param excludeSubFolders  被排除的文件夹
     * @param str_filter 文件名中被过滤的内容
     * @return
     * @return: ArrayList<String>
     * @throws
     */
    public static ArrayList<String> searchFileBydel(String folderPath, String fileType, boolean cascade, List<String> excludeSubFolders, String str_filter) {
        ArrayList<String> fileList = new ArrayList<String>();
        File[] files = null;
        File myDir = new File(folderPath);
        files = myDir.listFiles();//列出所有文件夹和文件
        if (files == null || files.length == 0) {
            return fileList;
        }

        try {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory() == true) {//目录
                    if (cascade) {//扫描子文件夹中的文件
                        if (excludeSubFolders != null && excludeSubFolders.size() > 0) {//需要排除特定目录
                            if (!excludeSubFolders.contains(files[i].getName())) {//当前目录不在排除列表内
                                ArrayList<String> subFileList = searchFileBydel(files[i].getAbsolutePath(), fileType, cascade, excludeSubFolders, str_filter);
                                if (subFileList != null && !subFileList.isEmpty()) {
                                    fileList.addAll(subFileList);
                                } else {
                                    log.info("空文件夹" + files[i].getAbsolutePath() + "删除成功" + files[i].delete());
                                }
                            }
                        } else {//不需要排除
                            ArrayList<String> subFileList = searchFileBydel(files[i].getAbsolutePath(), fileType, cascade, excludeSubFolders, str_filter);
                            if (subFileList != null && !subFileList.isEmpty()) {
                                fileList.addAll(subFileList);
                            } else {
                                log.info("空文件夹" + files[i].getAbsolutePath() + "删除成功" + files[i].delete());
                            }
                        }
                    } else {
                        continue;
                    }
                } else {//文件
                    String[] fileTypes = fileType.toLowerCase().split(",");

                    if (isContion(fileTypes, files[i].getAbsolutePath())) {//文件存在

                        if (isHasChar(files[i].getAbsolutePath().toLowerCase(), str_filter)) {
                            if(!files[i].delete()){
                            	log.error("删除文件失败"+files[i].getAbsolutePath());
							}
                            log.info("过滤文件" + files[i].getAbsolutePath() + "删除成功");
                        } else {
                            String separator = File.separator;
                            char oldSep = separator.charAt(0);
                            String filePath = files[i].getAbsolutePath().replace(oldSep, '/');
                            fileList.add(filePath);
                        }
                    } else {//文件不存在
                        //files[i].delete();
                        //log.info("文件"+files[i].getAbsolutePath()+"删除成功");
                    }

                }
            }
        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionTrace(e));
        }

        return fileList;
    }


    private static boolean isContion(String[] fileTypes, String fileName) {
        boolean isContion = false;
        if (fileTypes != null) {
            for (int i = 0; i < fileTypes.length; i++) {
                if (fileName.toLowerCase().indexOf("." + fileTypes[i].toLowerCase()) == fileName.toLowerCase().length() - ("." + fileTypes[i].toLowerCase()).length()) {
                    isContion = true;
                    break;
                }
            }
        }
        return isContion;
    }

    /**
     * 移动文件
     * @title: moveFile
     * @author: lht
     * @date: 2010-5-26
     * @param fileName  源文件
     * @param destFolder 目的文件夹绝对路径
     * @param fileType  被移动的文件类型
     * @return: String
     */
    public static String moveFile(String fileName, String destFolder, String fileType) {
        String newFilePath = null;
        if (fileType != null && fileType.trim().length() > 0 && !fileName.toLowerCase().endsWith(fileType.toLowerCase())) {
            return null;
        }
        // 文件原地址
        File srcFile = new File(fileName);
        // new一个新文件夹
        File destDir = new File(destFolder);
        try {
            File destFile = new File(destDir.getAbsolutePath() + File.separator + srcFile.getName());
            if (destFile.exists()) {
                if(!destFile.delete()){
					log.error("删除文件失败"+destFile.getAbsolutePath());
				}
            }
            FileUtils.moveFileToDirectory(srcFile, destDir, true);
            newFilePath = destDir + File.separator + srcFile.getName();
        } catch (IOException e) {
            log.error(ExceptionUtil.getExceptionTrace(e));
        }
        return newFilePath;
    }


    /**
     * 批量移动文件并修改后缀
     * @author: lht
     * @date: 2015-8-21 下午09:17:15
     * @param filePath
     * @param destFolder
     * @param newFileSuffix
     * @return
     * @return: String
     */
    public static String moveFileAndResuffix(String filePath, String destFolder, String newFileSuffix) {
        List<String> files = new ArrayList<String>();
        files.add(filePath);
        files = moveFileAndResuffix(files, destFolder, newFileSuffix);
        if(files==null) {
        	return "";
		}
        return files.get(0);
    }

    /**
     * 批量移动文件并修改后缀
     * @author: lht
     * @date: 2015-8-21 下午08:44:18
     * @param files
     * @param destFolder
     * @param newFileSuffix
     * @return
     * @return: ArrayList<String>
     */
    public static ArrayList<String> moveFileAndResuffix(List<String> files, String destFolder, String newFileSuffix) {
        ArrayList<String> newFiles = new ArrayList<String>();
        if (SysUtil.isNullOrEmpty(files) || SysUtil.isNullOrEmpty(destFolder)) {
            return null;
        }
        for (String f : files) {
            String newFile = moveFile(f, destFolder, null);
            renameFileSuffix(newFile, newFileSuffix);
            newFiles.add(newFile);
        }
        return newFiles;
    }

    /**
     * 批量移动文件并修改后缀
     * @author: lht
     * @date: 2015-8-21 下午08:44:18
     * @param files
     * @param destFolder
     * @param newFileSuffix
     * @param flag true表示先传输文件再进行重命名，false表示一边传输一边重命名
     * @return
     * @return: ArrayList<String>
     */
    public static ArrayList<String> moveFileAndResuffix(List<String> files, String destFolder, String newFileSuffix, boolean flag) {
        ArrayList<String> newFiles = new ArrayList<String>();
        if (SysUtil.isNullOrEmpty(files) || SysUtil.isNullOrEmpty(destFolder)) {
            return null;
        }
        for (String f : files) {
            String newFile = moveFile(f, destFolder, null);
            renameFileSuffix(newFile, newFileSuffix);
            newFiles.add(newFile);
        }
        return newFiles;
    }


    /**
     * 是否存在特殊字符
     * @author: lht
     * @param s
     * @return
     */
    private static boolean isHasChar(String s, String str_filter) {
        boolean flag = false;
        String charFilter = str_filter;

        if (charFilter == null) {
            return flag;
        }

        String[] filters = split(charFilter, ',');
        if (filters != null) {
            for (int i = 0; i < filters.length; i++) {
                if (s.indexOf(filters[i].toLowerCase()) > -1) {
                    flag = true;
                    break;
                }
            }
        }

        return flag;
    }


    /**
     *
     *@author：ytz
     *功能：解析字符串
     *@param s
     *@param separator
     *@return
     */
    public static String[] split(String s, char separator) {
        if (s == null || "".equals(s.trim())) {
            return null;
        }
        List<String> result = new ArrayList<String>();
        int beginIndex = 0;


        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == separator) {
                if (beginIndex > i - 1) {
                    result.add(null);
                } else {
                    result.add(s.substring(beginIndex, i));
                }
                beginIndex = i + 1;
            }
        }
        if (beginIndex != s.length()) {
            result.add(s.substring(beginIndex, s.length()));
        }

        if (s.charAt(s.length() - 1) == separator) {
            result.add(null);
        }
        String[] res = new String[result.size()];
        result.toArray(res);
        return res;
    }

    /**
     * 扫描分表数据文件
     * @title: searchMultiTableDataFile
     * @author: lht
     * @date: 2011-9-26
     * @param folderPath
     * @param fileType
     * @param excludeSubFolder
     * @param folderNamePrefix
     * @return
     * @throws Exception
     * @return: Map<String, List < String>>
     * @throws
     */
    public static Map<String, List<String>> searchMultiTableDataFile(String folderPath, String fileType, List<String> excludeSubFolder, String folderNamePrefix) throws Exception {
        Map<String, List<String>> map = null;
        File[] files = null;
        File myDir = new File(folderPath);
        files = myDir.listFiles();//列出所有文件夹和文件
        if (files == null || files.length == 0) {
            return map;
        }
        map = new HashMap<String, List<String>>();
        try {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory() == true) {//目录
                    String fileName = files[i].getName();
                    if (fileName.startsWith(folderNamePrefix, 0)) {
                        List<String> subFileList = searchFile(files[i].getAbsolutePath(), fileType, false, excludeSubFolder);
                        if (subFileList != null && !subFileList.isEmpty()) {
                            map.put(fileName, subFileList);
                        } else {
                            log.info("空文件夹" + files[i].getAbsolutePath() + "删除成功" + files[i].delete());
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("获取分表数据文件出错！", e);
        }
        return map;
    }

    /**
     * 文件重命名
     * @author: lht
     * @date: 2015-2-8
     * @param srcFilePath 源文件路径
     * @param destFilePath 目标文件路径
     * @return
     * @return: boolean
     * @throws
     */
    public static boolean renameFile(String srcFilePath, String destFilePath) {
        boolean flag = false;
        if (SysUtil.isNullStr(srcFilePath) || SysUtil.isNullStr(destFilePath)) {
            return false;
        }
        File srcFile = new File(srcFilePath);
        File destFile = new File(destFilePath);
        flag = renameFile(srcFile, destFile);
        return flag;
    }

    /**
     * 文件重命名
     * 如果目标文件已经存在，将被删除
     * @author: lht
     * @date: 2015-2-8
     * @param srcFile 源文件
     * @param destFile 目标文件
     * @return: boolean
     */
    public static boolean renameFile(File srcFile, File destFile) {
        boolean flag = false;
        if (!srcFile.exists() || !srcFile.isFile()) {
            return false;
        }
        try {
            if (destFile.exists()) {
                FileUtils.forceDelete(destFile);
            }
            if (srcFile.renameTo(destFile)) {
                flag = true;
            } else {
                FileUtils.copyFile(srcFile, destFile);
                FileUtils.forceDelete(srcFile);
                flag = true;
            }
        } catch (IOException e) {
            log.error(ExceptionUtil.getExceptionTrace(e));
            flag = false;
        }
        return flag;
    }

    /**
     * 文件修改后缀名
     * @author: lht
     * @date: 2015-2-8
     * @param suffix 文件后缀
     * @return: String
     */
    public static String renameFileSuffix(String srcFilePath, String suffix) {
        String newFilePath = null;
        File srcFile = new File(srcFilePath);
        if (!srcFile.exists() || !srcFile.isFile()) {
            return newFilePath;
        }
        try {
            if (!suffix.startsWith(".")) {
                suffix = "." + suffix;
            }
            newFilePath = srcFilePath.substring(0, srcFilePath.indexOf(".")) + suffix;
            File newFile = new File(newFilePath);
            if (newFile.exists()) {
                FileUtils.forceDelete(newFile);
            }
            if (!srcFile.renameTo(newFile)) {
                FileUtils.copyFile(srcFile, newFile);
                FileUtils.forceDelete(srcFile);
                newFilePath = null;
            }
        } catch (IOException e) {
            log.error(ExceptionUtil.getExceptionTrace(e));
            newFilePath = null;
        }
        return newFilePath;
    }
//数据上传下发专用-------------------------------------------------

    /**
     * 取排除扫描的文件夹
     * @title: getExcludeFolder
     * @author: lht
     * @date: 2012-3-13
     * @param excludeFolders
     * @return
     * @return: List<String>
     * @throws
     */
    private static List<String> getExcludeFolder(String excludeFolders) {
        List<String> folders = null;
        if (excludeFolders == null || excludeFolders.trim().length() == 0) {
            return folders;
        }
        folders = new ArrayList<String>();
        String[] efArray = excludeFolders.split(",");
        for (String excludeFolder : efArray) {
            folders.add(excludeFolder);
        }
        return folders;
    }

    //------------------------------------------------------

    /**
     * @desc 流数据生成文件
     * @param is
     *            读入数据
     * @param file
     *            生成文件
     * */
    public static boolean writeToFile(InputStream is, String file)
            throws Exception {
        OutputStream os = null;
        try{
			os = new BufferedOutputStream(new FileOutputStream(file));
			byte[] buf = new byte[1024];
			int len;
			while ((len = is.read(buf)) != -1) {
				os.write(buf, 0, len);
			}
			os.flush();
		} catch (Exception e) {
        	log.error(ExceptionUtil.getExceptionTrace(e));
		} finally {
			is.close();
            if (os != null) {
                os.close();
            }
        }
        return new File(file).exists();

    }

    @SuppressWarnings("unchecked")
    public static <T extends Closeable> boolean close(T... t) {
        for (T t2 : t) {
            try {
                if (t2 != null) {
                    t2.close();
                }
            } catch (Exception e) {
                log.error(t2.getClass().getSimpleName() + "关闭流出错");
                log.error(e.getMessage());
            }
        }
        return false;
    }

    public static boolean writeAnaFile(String fileName, String... content) {
        boolean flag = false;
        FileWriter writer = null;

        try {
            writer = new FileWriter(new File(fileName), true);
            for (String string : content) {
                writer.write(string);
            }
            writer.flush();
            writer.close();
        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionTrace(e));
        } finally {
            FileUtil.close(writer);
        }
        return flag;
    }

    public static BufferedWriter initFileWriter(String fileName, String fileEncoding) {
        File file = null;
        //文件写缓冲器
        BufferedWriter out = null;
        if (fileEncoding == null) {
            fileEncoding = "utf-8";
        }
        try {
            //创建新的数据文件
            file = FileUtil.createNewFile(fileName);
            //文件写缓冲器
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), fileEncoding));
        } catch (Exception e) {
            log.error("创建文件" + fileName + "出错！" + e.getMessage(), e);
        }
        return out;
    }

    /**
     * 迁移文件(将原有目录下各子文件夹及内容迁移至目标文件夹)
     * @author YY
     * @param oldFile 源文件
     * @param destFolder 目标文件夹
     * @param oldFolder 源根文件夹
     * @return 迁移数量
     * */
    public static int moveFile(File oldFile, String destFolder,
                               String oldFolder) {
        String newFilePath = null;
        if (oldFile.exists() && oldFile.isFile()) {
            String oldFileParent = oldFile.getParent();
            String childFolder = null;
            if (oldFolder.equals(oldFileParent + File.separator)) {
                childFolder = oldFileParent.substring(oldFolder.length() - 1);
            } else {
                childFolder = oldFileParent.substring(oldFolder.length());
            }
            if(destFolder!=null){
				if (SysUtil.isNullStr(childFolder)) {
					newFilePath = generatePath(destFolder, null);
				} else {
					newFilePath = generatePath(destFolder, childFolder);
				}
				File newDir = new File(newFilePath);
				if (!newDir.exists()) {
					newDir.mkdirs();
				}
				File newFile = new File(newFilePath + oldFile.getName());
				if(!newFile.delete()){
					log.error("删除失败"+newFile.getAbsolutePath());
				}
				return oldFile.renameTo(newFile) ? 1 : 0;
			}
        }
        return 0;
    }

    /**
     * 迁移文件(将原有目录下各子文件夹及内容迁移至目标文件夹)
     * @author YY
     * @param fileNames 源文件集合
     * @param destFolder 目标文件夹
     * @param oldRootFolder 源根文件夹
     * @return 迁移数量
     * */
    public static int moveFiles(List<String> fileNames, String destFolder, String oldRootFolder) {
        int count = 0;
        if (SysUtil.isNullList(fileNames)) {
            return 0;
        } else {
            for (String fileName : fileNames) {
                count += moveFile(new File(fileName), destFolder, oldRootFolder);
            }
        }
        return count;
    }

    /**
     * 拷贝文件(将原有目录下各子文件夹及内容迁移至目标文件夹)
     * @author YY
     * @param srcFile 源文件
     * @param destFolder 目标文件夹
     * @param srcFolder 源根文件夹
     * @return 迁移数量
     * @throws Exception
     * */
    public static int copyFile(File srcFile, String destFolder,
                               String srcFolder) throws Exception {
        String newFilePath = null;
        if (srcFile.exists() && srcFile.isFile()) {
            String oldFileParent = srcFile.getParent();
            String childFolder = null;
            if (srcFolder.equals(oldFileParent + File.separator)) {
                childFolder = oldFileParent.substring(srcFolder.length() - 1);
            } else {
                childFolder = oldFileParent.substring(srcFolder.length());
            }
            if(destFolder!=null) {
				if (SysUtil.isNullStr(childFolder)) {
					newFilePath = generatePath(destFolder, null);
				} else {
					newFilePath = generatePath(destFolder, childFolder);
				}
				File newDir = new File(newFilePath);
				if (!newDir.exists()) {
					newDir.mkdirs();
				}
				File newFile = new File(newFilePath + srcFile.getName());
				if(!newFile.delete()){
					log.error("删除失败"+newFile.getAbsolutePath());
				}
				//拷贝文件，不修改文件时间
				FileUtils.copyFile(srcFile, newFile, false);
				return 1;
			}
        }
        return 0;
    }

    /**
     * 按根目录字符串及子目录字符串拼装目录
     * @author YY
     * @param root 根目录字符串
     * @param child 子目录字符串
     * @return 所需目录字符串
     * */
    public static String generatePath(String root, String child) {
        String path = "";
        if (!SysUtil.isNullStr(root)) {
            if (!root.endsWith(File.separator)) {
                path = root + File.separator;
            } else {
                path = root;
            }
        }
        if (child!=null && !SysUtil.isNullStr(child)) {
            if (!child.endsWith(File.separator)) {
                path += child + File.separator;
            } else {
                path += child;
            }
        }
        return path;
    }

    /**
     * 文件名验证(是否符合正则表达式)
     * @author YY
     * @param file 待验证文件名称
     * @param regex 验证的正则表达式
     * @return 验证结果
     * */
    public static boolean validateFileName(File file, String regex) {
        return file.getAbsolutePath().matches(regex);
    }

    /**
     * 查找文件
     * @author: lht
     * @date: 2010-5-26
     * @param folderPath  要搜索的文件夹
     * @param fileNameExp 文件名表达式 支持%,?占位符
     * @param fileType  搜索的文件类型 "txt,xls"
     * @param cascade  是否级联
     * @return
     * @return: ArrayList<String>
     * @throws
     */
    public static ArrayList<String> searchFile(String folderPath, String fileNameExp, String fileType, boolean cascade, List<String> excludeSubFolders) {
        ArrayList<String> fileList = new ArrayList<String>();
        File[] files = null;
        File myDir = new File(folderPath);
        files = myDir.listFiles();
        ;
        if (files == null || files.length == 0) {
            return fileList;
        }
        if (fileNameExp != null) {
            fileNameExp = fileNameExp.trim();
        }
        try {
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                if (f.isDirectory() == true) {//目录
                    if (cascade) {//扫描子文件夹中的文件
                        if (excludeSubFolders != null && excludeSubFolders.size() > 0) {//需要排除特定目录
                            if (!excludeSubFolders.contains(f.getName())) {//当前目录不在排除列表内
                                ArrayList<String> subFileList = searchFile(f.getAbsolutePath(), fileNameExp, fileType, cascade, excludeSubFolders);
                                if (subFileList != null && !subFileList.isEmpty()) {
                                    fileList.addAll(subFileList);
                                } else {
                                    //log.info("空文件夹"+f.getAbsolutePath()+"删除成功"+f.delete());
                                }
                            }
                        } else {//不需要排除
                            ArrayList<String> subFileList = searchFile(f.getAbsolutePath(), fileNameExp, fileType, cascade, excludeSubFolders);
                            if (subFileList != null && !subFileList.isEmpty()) {
                                fileList.addAll(subFileList);
                            } else {
                                //log.info("空文件夹"+f.getAbsolutePath()+"删除成功"+f.delete());
                            }
                        }
                    } else {
                        continue;
                    }
                } else {
                    if (checkFileByType(f, fileType) && checkFileByName(f, fileNameExp)) {
                        String filePath = f.getAbsolutePath().replace(File.separator.charAt(0), '/');
                        fileList.add(filePath);
                    }
                }
            }
        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionTrace(e));
        }
        return fileList;

    }

    /**
     * 检测文件类型
     * @author: lht
     * @date: 2015-7-11 下午05:48:57
     * @param f 文件
     * @param fileType 文件类型，以逗号分隔，不带点号
     * @return: boolean
     * @throws
     */
    private static boolean checkFileByType(File f, String fileType) {
        boolean flag = false;
        if (fileType == null || fileType.trim().length() == 0) {
            flag = true;
        } else {
            fileType = fileType.toLowerCase();
            fileType = "^.*\\." + fileType.replace("%", ".*").replace("?", ".") + "$";
            String fileName = getFileName(f.getAbsolutePath(), true);
            String[] fileTypes = fileType.split(",");
            if (fileTypes != null) {
                for (String type : fileTypes) {
                    Pattern pattern = Pattern.compile(type);
                    Matcher matcher = pattern.matcher(fileName);
                    flag = matcher.matches();
                    if (flag) {
                        flag = true;
                        break;
                    }
                }
            }
        }
        return flag;
    }

    /**
     * 检测文件名
     * @author: lht
     * @date: 2015-7-12 下午03:58:58
     * @param f
     * @param fileNameExp
     * @return
     * @return: boolean
     * @throws
     */
    private static boolean checkFileByName(File f, String fileNameExp) {
        boolean flag = false;
        String fileName = getFileName(f.getAbsolutePath(), false);
        if (fileNameExp == null || fileNameExp.trim().length() == 0 || "%".equals(fileNameExp)) {
            flag = true;
        } else {
            fileNameExp = "^" + fileNameExp.replace("%", ".*").replace("?", ".") + "$";
            Pattern pattern = Pattern.compile(fileNameExp);
            Matcher matcher = pattern.matcher(fileName);
            flag = matcher.matches();
        }
        return flag;
    }

    /**
     * @param isNeedFirstLine 是否需要第一行
     * @return 文件的行数
     */
    public static int getLineNum(File file, boolean isNeedFirstLine) {
        if (!file.exists()) {
            log.error("统计文件：" + file.getName() + "不存在");
            return -1;
        }
        BufferedReader br = null;
        int count = 0;
        try {
            br = new BufferedReader(new FileReader(file));
            String trim = "";
            if (!isNeedFirstLine) {
                br.readLine();
            }
            while ((trim = br.readLine()) != null && !"".equals(trim.trim())) {
                count++;
            }
        } catch (Exception e) {
            return -1;
        } finally {
            try {
            	if(br!=null) {
					br.close();
				}
            } catch (Exception e2) {
                count = -1;
            }
        }
        return count;
    }

    /**
     * 过滤出constant结尾的文件
     * @param constant
     * @return
     */
    public static File[] filterFile(File dir, final String constant) {
        return dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                String fileName = pathname.getName();
                return fileName.length() - fileName.indexOf(constant) == constant.length();
            }
        });
    }

    /**获取jar包程序存放的位置
     * @return
     */
    public static String getPath() {
        String path = System.getProperty("java.class.path");
        int firstIndex = path.lastIndexOf(System.getProperty("path.separator")) + 1;
        int lastIndex = path.lastIndexOf(File.separator) + 1;
        path = path.substring(firstIndex, lastIndex);
        File file = new File(path);
        String inceementFloder = file.getAbsolutePath() + File.separator;
        return inceementFloder;
    }

}
