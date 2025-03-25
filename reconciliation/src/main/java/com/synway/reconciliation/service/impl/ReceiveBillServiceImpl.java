package com.synway.reconciliation.service.impl;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSONArray;
import com.hazelcast.core.HazelcastInstance;
import com.synway.reconciliation.common.Constants;
import com.synway.reconciliation.common.ErrorCode;
import com.synway.reconciliation.common.SystemException;
import com.synway.reconciliation.pojo.*;
import com.synway.reconciliation.schedule.consumer.api.BillFileWriter;
import com.synway.reconciliation.schedule.issue.IssueKafkaManage;
import com.synway.reconciliation.util.SpringContextUtil;
import com.synway.reconciliation.service.ReceiveBillService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author DZH
 */
@Slf4j
@Service
public class ReceiveBillServiceImpl implements ReceiveBillService {

    /**
     * 记录读取文件行数
     */
    private Integer lineNumberTag = 0;

    /**
     * 是否 是下发
     */
    @Value("${isIssue}")
    private String isIssue;

    @Autowired
    private DruidDataSource oracleDataSource;

    private final HazelcastInstance hazelcastInstance;

    @Autowired
    public ReceiveBillServiceImpl(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    /**
     * 读取文件入库线程池
     */
    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(6, 6,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(),
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "consumerBill");
                }
            });

    @Override
    public boolean handleBill(String data, String type) {
        log.info("API接收账单数据 类型： " + type + "数据： " + data);
        try {
            if (StringUtils.isBlank(data) || StringUtils.isBlank(type)) {
                return false;
            }

            // 去除data中带的换行符 保证写入文件为1行
            data = handleData(data);

            if ((StringUtils.equalsIgnoreCase(isIssue, Boolean.TRUE.toString()))) {
                // 如果是下发 需要发送的kafka
                IssueKafkaManage issueKafkaManage = SpringContextUtil.getBean(IssueKafkaManage.class);
                return issueKafkaManage.sendAccount(data, type);
            } else {
                // 获取对应BillFileWriter 写入磁盘
                BillFileWriter billFileWriter = SpringContextUtil.getBillFileWriterBean(type);
                if (billFileWriter != null) {
                    boolean write = billFileWriter.write(data);
                    if (write) {
                        log.info("接收账单写入文件成功");
                    }
                    return write;
                }
            }
            return false;
        } catch (Exception e) {
            log.error("账单写入错误" + e);
            return false;
        }
    }

    @Override
    public void consumerBill(String billPath, String dateDir) {
        // 另开启一个线程执行每一种账单入库
        // 接入接收方账单
        threadPoolExecutor.submit(() -> readAndStorageBill(billPath, Constants.ACCESS_ACCEPTOR, dateDir));
        // 接入提供方账单
        threadPoolExecutor.submit(() -> readAndStorageBill(billPath, Constants.ACCESS_PROVIDER, dateDir));
        // 标准化接收方账单
        threadPoolExecutor.submit(() -> readAndStorageBill(billPath, Constants.STANDARD_ACCEPTOR, dateDir));
        // 标准化提供方账单
        threadPoolExecutor.submit(() -> readAndStorageBill(billPath, Constants.STANDARD_PROVIDER, dateDir));
        // 入库接收方账单
        threadPoolExecutor.submit(() -> readAndStorageBill(billPath, Constants.STORAGE_ACCEPTOR, dateDir));
        // 入库提供方账单
        threadPoolExecutor.submit(() -> readAndStorageBill(billPath, Constants.STORAGE_PROVIDER, dateDir));
    }

    @Override
    public void insertAcceptorBill(List<DacAcceptorBill> acceptorBills, BillTableType billTableType) {
        PreparedStatement insertStatement = null;
        Connection connection = null;
        if (acceptorBills.size() == 0) {
            return;
        }
        try {
            String insertSqlString = "INSERT INTO " + billTableType.getTableName() +" (SJJRF_DZDBH,SJJSWZ_JYQK,SJCCXSDM,SJ_DZWJMC,SJZYBSF,SJZYMC,BZSJXJBM,SJTS,SJ_DZWJDX,SJQSBH,SJJWBH,SJJYZ," +
                    "SJJYSFMC,SCSB_DZDBH,SCSJ_RQSJ,SJJRFGLY_XM,SJJRFGLY_LXDH,XXRWBH,DZDLXDM,DZDZTDM) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            connection = oracleDataSource.getConnection();
            connection.setAutoCommit(false);
            insertStatement = connection.prepareStatement(insertSqlString);
            int count = 0;
            for (DacAcceptorBill entry : acceptorBills) {
                insertStatement.setString(1, entry.getSJJRF_DZDBH());
                insertStatement.setString(2, entry.getSJJSWZ_JYQK());
                insertStatement.setInt(3, Integer.parseInt(entry.getSJCCXSDM()));
                insertStatement.setString(4, entry.getSJ_DZWJMC());
                insertStatement.setString(5, entry.getSJZYBSF());
                insertStatement.setString(6, entry.getSJZYMC());
                insertStatement.setString(7, entry.getBZSJXJBM());
                insertStatement.setLong(8, entry.getSJTS());
                insertStatement.setLong(9, entry.getSJ_DZWJDX());
                insertStatement.setString(10, entry.getSJQSBH());
                insertStatement.setString(11, entry.getSJJWBH());
                insertStatement.setString(12, entry.getSJJYZ());
                insertStatement.setString(13, entry.getSJJYSFMC());
                insertStatement.setString(14, entry.getSCSB_DZDBH());
                insertStatement.setInt(15, Integer.parseInt(entry.getSCSJ_RQSJ()));
                insertStatement.setString(16, entry.getSJJRFGLY_XM());
                insertStatement.setString(17, entry.getSJJRFGLY_LXDH());
                insertStatement.setString(18, entry.getXXRWBH());
                insertStatement.setInt(19, 0);
                insertStatement.setInt(20, 0);
                insertStatement.addBatch();
                count++;
            }
            insertStatement.executeBatch();
            connection.commit();
            log.info("成功插入" + billTableType.getTableNameCh() + "对账单：" + count + "条。");
        } catch (Exception e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (Exception e2) {
                throw SystemException.asSystemException(ErrorCode.DB_ROLLBACK_ERROR, e2);
            }
            log.error("插入" + billTableType.getTableNameCh() + "账单错误：" + e.toString());
            throw SystemException.asSystemException(ErrorCode.DATA_INSERT_ERROR, e);
        } finally {
            try {
                if (insertStatement != null) {
                    insertStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                log.error(e.toString());
            }
        }
    }

    @Override
    public void insertProviderBill(List<DacProviderBill> providerBills, BillTableType billTableType) {
        PreparedStatement insertStatement = null;
        Connection connection = null;
        if (providerBills.size() == 0) {
            return;
        }
        try {
            String insertSqlString = "INSERT INTO " + billTableType.getTableName() +" (SJTGF_DZDBH,SJJSWZ_JYQK,SJCCXSDM,SJ_DZWJMC,SJZYBSF,SJZYMC,BZSJXJBM,SJTS,SJ_DZWJDX,SJQSBH,SJJWBH,SJJYZ," +
                    "SJJYSFMC,SCSB_DZDBH,SCSJ_RQSJ,SJTGFGLY_XM,SJTGFGLY_LXDH,XXRWBH) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            connection = oracleDataSource.getConnection();
            connection.setAutoCommit(false);
            insertStatement = connection.prepareStatement(insertSqlString);
            int count = 0;
            for (DacProviderBill entry : providerBills) {
                insertStatement.setString(1, entry.getSJTGF_DZDBH());
                insertStatement.setString(2, entry.getSJJSWZ_JYQK());
                insertStatement.setInt(3, Integer.parseInt(entry.getSJCCXSDM()));
                insertStatement.setString(4, entry.getSJ_DZWJMC());
                insertStatement.setString(5, entry.getSJZYBSF());
                insertStatement.setString(6, entry.getSJZYMC());
                insertStatement.setString(7, entry.getBZSJXJBM());
                insertStatement.setLong(8, entry.getSJTS());
                insertStatement.setLong(9, entry.getSJ_DZWJDX());
                insertStatement.setString(10, entry.getSJQSBH());
                insertStatement.setString(11, entry.getSJJWBH());
                insertStatement.setString(12, entry.getSJJYZ());
                insertStatement.setString(13, entry.getSJJYSFMC());
                insertStatement.setString(14, entry.getSCSB_DZDBH());
                insertStatement.setInt(15, Integer.parseInt(entry.getSCSJ_RQSJ()));
                insertStatement.setString(16, entry.getSJTGFGLY_XM());
                insertStatement.setString(17, entry.getSJTGFGLY_LXDH());
                insertStatement.setString(18, entry.getXXRWBH());
                insertStatement.addBatch();
                count++;
            }
            insertStatement.executeBatch();
            connection.commit();
            log.info("成功插入" + billTableType.getTableNameCh() + "对账单：" + count + "条。");
        } catch (Exception e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (Exception e2) {
                throw SystemException.asSystemException(ErrorCode.DB_ROLLBACK_ERROR, e2);
            }
            log.error("插入" + billTableType.getTableNameCh() + "账单错误：" + e.toString());
            throw SystemException.asSystemException(ErrorCode.DATA_INSERT_ERROR, e);
        } finally {
            try {
                if (insertStatement != null) {
                    insertStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                log.error(e.toString());
            }
        }
    }


    /**
     * 去除data中带的换行符
     * @param data 账单数据
     * @return
     */
    private String handleData(String data) {
        data = data.replace("\n", "");
        data = data.replace("\t", "");
        data = data.replace("\r", "");
        return data;
    }

    /**
     * 读文件并且入库账单
     * @param billPath 账单根路径
     * @param billType 账单类型
     */
    private void readAndStorageBill(String billPath, String billType, String dateDir) {
        try {
            long startTime = System.currentTimeMillis();
            //  最终目录格式  自定义前缀根目录billPath + 日期dateDir + 账单类型typeDir
            //  不包含已经读取的账单且持有所得文件
            List<String> onWriterFileList = new ArrayList<>();
            //  根据bilType获取不同的billFileWriter
            BillFileWriter billFileWriter = SpringContextUtil.getBillFileWriterBean(billType + "_writer");
            if (billFileWriter != null) {
                for (BillFileWriter.Writer writer : billFileWriter.getWriterList()) {
                    if (writer.getCurFile() != null) {
                        String name = writer.getCurFile().getName();
                        onWriterFileList.add(name);
                    }
                }
            }
            //  处理账单数据 先获取目录下所有文件名
            String absPath = String.format("%s%s%s%s%s", billPath, File.separator, dateDir, File.separator, billType);
            File file = new File(absPath);
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for (File f : files) {
                    int lineNumber = 0;
                    boolean done = false;
                    String fileName;
                    // 初始化tag
                    lineNumberTag = 0;
                    fileName = f.getName();
                    //如果没有加载过 且 没有在写
                    if (StringUtils.isNotBlank(fileName) && !fileName.endsWith(Constants.DONE_BILL_FILE_SUFFIX) && !onWriterFileList.contains(fileName)) {
                        try {
                            //判断是否是未写完的文件
                            String[] split = fileName.split(Constants.BILL_FILE_SUFFIX);
                            if (split.length > 1) {
                                lineNumber = Integer.parseInt(split[1]);
                            }
                            // 处理文件
                            handleBillData(f, billType, lineNumber);
                            done = true;
                        } finally {
                            //如果有之前lineNumber就去掉
                            fileName = fileName.substring(0, fileName.indexOf(Constants.BILL_FILE_SUFFIX) + 4);
                            String newName;
                            if (done) {
                                // 处理完改文件名 + done
                                newName = String.format("%s%s%s%s", absPath, File.separator, fileName, Constants.DONE_BILL_FILE_SUFFIX);
                            } else {
                                // 处理中有异常 改文件名需要记录lineNumber;
                                newName = String.format("%s%s%s%s", absPath, File.separator, fileName, String.valueOf(lineNumberTag));
                            }
                            boolean rename = f.renameTo(new File(newName));
                            if (!rename) {
                                log.error(fileName + "，文件改名失败");
                            }
                        }
                    }
                }
            }
            long endTime = System.currentTimeMillis();
            log.info(billType + "----------consumer time: " + (endTime - startTime));
        } catch (Exception e) {
            log.error("加载账单失败" + e);
        }
    }

    /**
     * 处理账单数据
     * @param file      要读取文件
     * @param billType  账单类型
     */
    private void handleBillData(File file, String billType, int lineNumber) throws IOException {
        log.info("读文件写入数据库 类型： " + billType + "文件名： " + file.getName());
        FileReader fileReader = null;
        LineNumberReader lineNumberReader = null;
        try {
            fileReader = new FileReader(file);
            lineNumberReader = new LineNumberReader(fileReader);
            List<String> dataList = new ArrayList<>();
            // 按行读取整个文件
            while (true) {
                if (lineNumberReader.getLineNumber() < lineNumber) {
                    lineNumberReader.readLine();
                    continue;
                }

                String data = lineNumberReader.readLine();
                if (StringUtils.isBlank(data)) {
                    break;
                }

                dataList.add(data);
                // 50行 提交一次 避免数据量过大oom
                if (dataList.size() > 50) {
                    handleBillByType(dataList, billType);
                    lineNumber+=dataList.size();
                    dataList.clear();
                }
            }
            // 处理最后<50行的数据
            if (dataList.size() > 0) {
                handleBillByType(dataList, billType);
                lineNumber+=dataList.size();
                dataList.clear();
            }

        } catch (IOException e) {
            log.error("处理账单报错" + e);
            throw e;
        } finally {
            // 记录行数
            lineNumberTag = lineNumber;
            // 关闭两个流
            try {
                if (lineNumberReader != null) {
                    lineNumberReader.close();
                }
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException e) {
                log.error("close reader error");
            }
        }
    }

    /**
     * 根据不同的账单类型处理入库
     * @param dataList  账单数据列表
     * @param billType  账单类型
     */
    private void handleBillByType(List<String> dataList, String billType) {
        if (Constants.ACCESS_ACCEPTOR.equals(billType)) {
            handleAccessAcceptorBill(dataList);
        } else if (Constants.ACCESS_PROVIDER.equals(billType)) {
            handleAccessProviderBill(dataList);
        } else if (Constants.STANDARD_ACCEPTOR.equals(billType)) {
            handleStandardAcceptorBill(dataList);
        } else if (Constants.STANDARD_PROVIDER.equals(billType)) {
            handleStandardProviderBill(dataList);
        } else if (Constants.STORAGE_ACCEPTOR.equals(billType)) {
            handleStorageAcceptorBill(dataList);
        } else if (Constants.STORAGE_PROVIDER.equals(billType)) {
            handleStorageProviderBill(dataList);
        } else {
            log.error("billType error");
        }
    }

    private void handleAccessAcceptorBill(List<String> dataList) {
        List<DacAcceptorBill> acceptorBills = convertToAcceptorBill(dataList);
        // 填充接入接收方账单数据 数据资源名称、数据接入方管理员_姓名、数据接入方管理员_联系电话
        ConcurrentMap<String, DsDetectedTable> billRelateInfoMap = hazelcastInstance.getMap(Constants.BILL_RELATE_INFO);
        for (DacAcceptorBill acceptorBill : acceptorBills) {
            acceptorBill.setSJCCXSDM("3");
            String resourceId = acceptorBill.getBZSJXJBM();
            if(StringUtils.isNotBlank(resourceId)){
                DsDetectedTable dsDetectedTable = billRelateInfoMap.get(resourceId);
                if (dsDetectedTable != null) {
                    if (StringUtils.isNotBlank(dsDetectedTable.getTableNameCn())) {
                        acceptorBill.setSJZYMC(dsDetectedTable.getTableNameCn());
                    } else {
                        acceptorBill.setSJZYMC(resourceId);
                    }
                    acceptorBill.setSJJRFGLY_XM(dsDetectedTable.getRoutineLinkman());
                    acceptorBill.setSJJRFGLY_LXDH(dsDetectedTable.getRoutineTel());
                }
            }
        }
        insertAcceptorBill(acceptorBills, BillTableType.ACCESS_ACCEPTOR);
    }

    private void handleAccessProviderBill(List<String> dataList) {
        List<DacProviderBill> providerBills = convertToProviderBill(dataList);
        // 填充接入提供方账单数据 数据资源名称、数据提供方管理员_姓名、数据提供方管理员_联系电话
        ConcurrentMap<String, DsDetectedTable> billRelateInfoMap = hazelcastInstance.getMap(Constants.BILL_RELATE_INFO);
        for (DacProviderBill providerBill : providerBills) {
            providerBill.setSJCCXSDM("3");
            String resourceId = providerBill.getBZSJXJBM();
            if(StringUtils.isNotBlank(resourceId)){
                DsDetectedTable dsDetectedTable = billRelateInfoMap.get(resourceId);
                if (dsDetectedTable != null) {
                    if (StringUtils.isNotBlank(dsDetectedTable.getTableNameCn())) {
                        providerBill.setSJZYMC(dsDetectedTable.getTableNameCn());
                    } else {
                        providerBill.setSJZYMC(resourceId);
                    }
                    providerBill.setSJTGFGLY_XM(dsDetectedTable.getRoutineLinkman());
                    providerBill.setSJTGFGLY_LXDH(dsDetectedTable.getRoutineTel());
                }
            }
        }
        insertProviderBill(providerBills, BillTableType.ACCESS_PROVIDER);
    }

    private void handleStandardAcceptorBill(List<String> dataList) {
        List<DacAcceptorBill> acceptorBills = convertToAcceptorBill(dataList);
        insertAcceptorBill(acceptorBills, BillTableType.STANDARD_ACCEPTOR);
    }

    private void handleStandardProviderBill(List<String> dataList) {
        List<DacProviderBill> providerBills = convertToProviderBill(dataList);
        insertProviderBill(providerBills, BillTableType.STANDARD_PROVIDER);
    }

    private void handleStorageAcceptorBill(List<String> dataList) {
        List<DacAcceptorBill> acceptorBills = convertToAcceptorBill(dataList);
        insertAcceptorBill(acceptorBills, BillTableType.STORAGE_ACCEPTOR);
    }

    private void handleStorageProviderBill(List<String> dataList) {
        List<DacProviderBill> providerBills = convertToProviderBill(dataList);
        insertProviderBill(providerBills, BillTableType.STORAGE_PROVIDER);
    }


    private List<DacAcceptorBill> convertToAcceptorBill(List<String> dataList) {
        List<DacAcceptorBill> acceptorBills = new ArrayList<>();
        for (String data : dataList) {
            List<DacAcceptorBill> dacAcceptorBills = JSONArray.parseArray(data, DacAcceptorBill.class);
            acceptorBills.addAll(dacAcceptorBills);
        }
        return acceptorBills;
    }

    private List<DacProviderBill> convertToProviderBill(List<String> dataList) {
        List<DacProviderBill> providerBills = new ArrayList<>();
        for (String data : dataList) {
            List<DacProviderBill> dacProviderBills = JSONArray.parseArray(data, DacProviderBill.class);
            providerBills.addAll(dacProviderBills);
        }
        return providerBills;
    }

}
