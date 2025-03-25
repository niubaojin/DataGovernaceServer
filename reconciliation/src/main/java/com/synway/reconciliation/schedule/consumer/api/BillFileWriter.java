package com.synway.reconciliation.schedule.consumer.api;

import com.synway.reconciliation.util.DateUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 写文件类 ,
 *  1、维护多个buffer
 * @author DZH
 */
@Slf4j
public class BillFileWriter {

    /**
     * writerList
     */
    private List<Writer> writerList = new ArrayList<>(5);

    /**
     * 控制均衡写入
     */
    private int index = 0;

    /**
     * 账单根路径
     */
    private String billPath;

    /**
     * 账单类型
     */
    private String billType;

    @Value("${billFileMaxSize}")
    private long billFileMaxSize;

    @Value("${billFileMaxTime}")
    private long billFileMaxTime;

    @Value("${receiveBillExpireTime}")
    private long receiveBillExpireTime;

    public BillFileWriter(String billPath, String billType){

        this.billPath = billPath;
        this.billType = billType;

        //获取天数
        Calendar calendar = Calendar.getInstance();
        String dateDir = DateUtil.formatDate(calendar.getTime(), DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
        String absPath = String.format("%s%s%s%s%s", billPath, File.separator, dateDir, File.separator, billType);
        //创建目录
        createDir(new File(absPath));
        for (int i = 0; i < 5; i++) {
            writerList.add(new Writer(absPath));
        }
    }

    public List<Writer> getWriterList() {
        return writerList;
    }

    public void setWriterList(List<Writer> writerList) {
        this.writerList = writerList;
    }

    /**
     * 递归创建路径
     * @param dirName
     */
    public static void createDir(File dirName){
        if(dirName.exists()){
            return;
        }
        String parent=dirName.getParent();
        File file = new File(parent);

        if(!file.exists()){
            /**递归找到最上层没有路径的文件夹*/
            createDir(file);
            /**创建目标文件夹*/
            dirName.mkdir();
        }else{
            dirName.mkdir();
        }
    }


    public boolean write(String data) throws Exception{
        long startTime = System.currentTimeMillis();
        for (; index < writerList.size();index = (index+1) % writerList.size()) {
            if (System.currentTimeMillis() - startTime > receiveBillExpireTime * 1000) {
                throw new RuntimeException();
            }
            if (writerList.get(index).setBusy()) {
                Writer writer = writerList.get(index);
                if(!writer.isIfOpen()) {
                    Calendar calendar = Calendar.getInstance();
                    String dateDir = DateUtil.formatDate(calendar.getTime(), DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
                    String absPath = String.format("%s%s%s%s%s", billPath, File.separator, dateDir, File.separator, billType);
                    createDir(new File(absPath));
                    writer.buildWriter(absPath);
                }
                try {
                    writer.writerLine(data);
                    writer.flush();
                    writer.release();
                    // 保证下次写从下一个文件开始判断
                    index = (index+1) % writerList.size();
                    return true;
                } catch (Exception ex) {
                    log.error("在写入对账信息文件时[{}]出错，错误信息为:{}",writer.getCurFile().getAbsoluteFile(),ex);
                    throw ex;
                }
            }
        }
        return true;
    }

    /**
     * 在空闲的时候，将buffer释放
     */
    @Scheduled(cron = "${checkWriterAlive}")
    public void checkAlive() {
        try {
            long curTime = System.currentTimeMillis();
            for (int i = 0; i < writerList.size(); i++) {
                Writer writer = writerList.get(i);
                //如果已经关闭就跳过
                if (!writer.ifOpen) {
                    continue;
                }
                //自旋等待
                for (; !writer.setBusy(); DateUtil.sleep(1));
                //如果文件>100M或者开启超过5分钟 就关闭writer
                if (writer.getCurFile().length() > billFileMaxSize || curTime - writer.getBeginTime() >=  billFileMaxTime) {
                    writer.flush();
                    writer.close();
                }
                writer.release();
            }
        }catch (Exception ex){
            log.error("在checkAlive时出错，错误信息为:",ex);
        }
    }


    @Data
    public class Writer {
        private BufferedWriter bufferedWriter ;
        private File curFile;
        private AtomicBoolean busy = new AtomicBoolean(false);
        private long beginTime;
        private boolean ifOpen;
        Writer(String path){
            try {
                buildWriter(path);
            }catch (Exception ex){
                log.error("在创建writer的时候出错，错误信息为:", ex);
            }
        }

        public void buildWriter(String fileNamePre) throws Exception {
            File newFile = buildFileName(fileNamePre);
            curFile = newFile;
            beginTime = System.currentTimeMillis();
            bufferedWriter = new BufferedWriter(new FileWriter(newFile));
            ifOpen =true;
        }

        public boolean setBusy(){
            return busy.compareAndSet(false,true);
        }

        public boolean release(){
            return busy.compareAndSet(true,false);
        }

        public void writerLine(String line) throws IOException {
            bufferedWriter.write(
                    String.format("%s%s", line, IOUtils.LINE_SEPARATOR));
        }

        public void flush() throws IOException {
            bufferedWriter.flush();
        }

        public void close() throws IOException {
            if (bufferedWriter != null){
                bufferedWriter.close();
            }
            ifOpen =false;
            curFile = null;
            bufferedWriter = null;
        }

        public File buildFileName (String filePath) {
            Calendar c = Calendar.getInstance();
            String dateStr = DateUtil.formatDateTime(c.getTime(),DateUtil.DEFAULT_PATTERN_DATETIME_SIMPLE);
            for (int i = 0; i < 10000; i++) {
                String absPath =String.format("%s%s%s_%s.txt",filePath, File.separator,dateStr,i);
                File newFile = new File(absPath);
                try {
                    if(newFile.createNewFile()) {
                        return newFile;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            return null;
        }
    }


}
