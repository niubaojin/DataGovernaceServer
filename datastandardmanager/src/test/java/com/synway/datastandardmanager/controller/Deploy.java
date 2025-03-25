//package com.synway.datastandardmanager.controller;
//
//import com.synway.datastandardmanager.util.ExceptionUtil;
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.*;
//import java.util.Objects;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.regex.Pattern;
//
///**
// * @author wangdongwei
// * @date 2021/8/30 19:10
// */
//@Slf4j
//public class Deploy {
//
//    /**
//     * settings-Nexus.xml 里面的 本地仓库地址与 PATH 这个地址不能一样
//     */
//    public static final String BASE_CMD = "cmd /c mvn " +
//            "deploy:deploy-file " +
//            "-Durl=http://1.1.1.29:8888/repository/maven-releases/ " +
//            "-DrepositoryId=nexus-releases " +
//            "  -settings=D:\\apache-maven-3.5.2\\conf\\settings-Nexus.xml";
//
//    /**
//     * 需要修改
//     */
//    public static final String PATH ="E:\\mvnRespon_chen\\org\\apache";
//    public static final Pattern DATE_PATTERN = Pattern.compile("-[\\d]{8}\\.[\\d]{6}-");
//
//    public static final Runtime CMD = Runtime.getRuntime();
//
//
//    public static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(5);
//
//
//
//    public static void main(String[] args) {
//        File[] files = new File(PATH).listFiles(new FileFilter() {
//            @Override
//            public boolean accept(File pathname) {
//                return !pathname.getName().startsWith(".") && !pathname.getName().startsWith("-");
//            }
//        });
//        deploy(files);
//
//        EXECUTOR_SERVICE.shutdown();
//
//    }
//
//
//    public static void deploy(File[] files) {
//        if (files.length == 0) {
//            //ignore
//        } else if (files[0].isDirectory()) {
//            for (File file : files) {
//                if (file.isDirectory()) {
//                    deploy(Objects.requireNonNull(file.listFiles(new FileFilter() {
//                        @Override
//                        public boolean accept(File pathname) {
//                            return (!pathname.getName().startsWith(".") && !pathname.getName().startsWith("-"));
//                        }
//                    })));
//                }
//            }
//        } else if (files[0].isFile()) {
//            File pom = null;
//            File jar = null;
//            File source = null;
//            File javadoc = null;
//            //忽略日期快照版本，如 xxx-mySql-2.2.6-20170714.095105-1.jar
//            for (File file : files) {
//                String name = file.getName();
//                if(DATE_PATTERN.matcher(name).find()){
//                    //skip
//                } else if (name.endsWith(".pom")) {
//                    pom = file;
//                } else if (name.endsWith("-javadoc.jar")) {
//                    javadoc = file;
//                } else if (name.endsWith("-sources.jar")) {
//                    source = file;
//                } else if (name.endsWith(".jar")) {
//                    jar = file;
//                }
//            }
//            if(pom != null){
//                if(jar != null){
//                    deploy(pom, jar, source, javadoc);
//                } else if(packingIsPom(pom)){
//                    deployPom(pom);
//                }
//            }
//        }
//    }
//
//    public static boolean packingIsPom(File pom){
//        BufferedReader reader = null;
//        try {
//            reader =
//                    new BufferedReader(new InputStreamReader(new FileInputStream(pom)));
//            String line;
//            while((line = reader.readLine()) != null){
//                if(line.trim().indexOf("<packaging>pom</packaging>")!=-1){
//                    return true;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try{reader.close();}catch(Exception e){}
//        }
//        return false;
//    }
//
//    public static void deployPom(final File pom) {
//        EXECUTOR_SERVICE.execute(new Runnable() {
//            @Override
//            public void run() {
//                StringBuffer cmd = new StringBuffer(BASE_CMD);
//                cmd.append(" -DpomFile=").append(pom.getName());
//                cmd.append(" -Dfile=").append(pom.getName());
//                try {
//                    final Process proc = CMD.exec(cmd.toString(), null, pom.getParentFile());
//                    InputStream inputStream = proc.getInputStream();
//                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//                    BufferedReader reader = new BufferedReader(inputStreamReader);
//                    String line;
//                    StringBuffer logBuffer = new StringBuffer();
//                    logBuffer.append("\n\n\n==================================\n");
//                    while((line = reader.readLine()) != null){
//                        if (line.startsWith("[INFO]") || line.startsWith("Upload")) {
//                            logBuffer.append(Thread.currentThread().getName() + " : " + line + "\n");
//                        }
//                    }
//                    System.out.println(logBuffer);
//                    int result = proc.waitFor();
//                    if(result != 0){
//                        log.error("上传失败：" + pom.getAbsolutePath());
//                    }
//                } catch (Exception e) {
//                    log.error("上传失败：" + pom.getAbsolutePath() + ExceptionUtil.getExceptionTrace(e));
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//
//    public static void deploy(final File pom, final File jar, final File source, final File javadoc) {
//        EXECUTOR_SERVICE.execute(new Runnable() {
//            @Override
//            public void run() {
//                StringBuffer cmd = new StringBuffer(BASE_CMD);
//                cmd.append(" -DpomFile=").append(pom.getPath());
//                if(jar != null){
//                    //当有bundle类型时，下面的配置可以保证上传的jar包后缀为.jar
//                    cmd.append(" -Dpackaging=jar -Dfile=").append(jar.getPath());
//                } else {
//                    cmd.append(" -Dfile=").append(pom.getPath());
//                }
//                if(source != null){
//                    cmd.append(" -Dsources=").append(source.getPath());
//                }
//                if(javadoc != null){
//                    cmd.append(" -Djavadoc=").append(javadoc.getPath());
//                }
//
//                try {
//                    log.info(cmd.toString());
//                    final Process proc = CMD.exec(cmd.toString(), null, pom.getParentFile());
//                    InputStream inputStream = proc.getInputStream();
//                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//                    BufferedReader reader = new BufferedReader(inputStreamReader);
//                    String line;
//                    StringBuffer logBuffer = new StringBuffer();
//                    logBuffer.append("\n\n\n=======================================\n");
//                    while((line = reader.readLine()) != null){
//                        if (line.startsWith("[INFO]") || line.startsWith("Upload")) {
//                            logBuffer.append(Thread.currentThread().getName() + " : " + line + "\n");
//                        }
//                    }
//                    System.out.println(logBuffer);
//                    int result = proc.waitFor();
//                    if(result != 0){
//                        log.error("上传失败：" + pom.getAbsolutePath());
//                    }
//                } catch (Exception e) {
//                    log.error("上传失败：" + pom.getAbsolutePath()+ "  "+e.getMessage());
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//
//}
