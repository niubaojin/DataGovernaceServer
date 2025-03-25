package com.synway.reconciliation.util;

import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * 写文件类
 * @author DZH
 */
public class TextWriter {

    private BufferedWriter bufferedWriter = null;
    private File fileName  = null;

    public void buildWriter(String filePath) throws Exception {
        this.fileName = new File(filePath);
        if (this.fileName.exists()){
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, true)));
        } else {
            bufferedWriter = new BufferedWriter(new FileWriter(filePath));
        }
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
            bufferedWriter.close();;
        }
    }

}
