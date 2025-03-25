/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.synway.dataoperations.util;

import com.alibaba.fastjson.JSONObject;
import com.synway.common.exception.ExceptionUtil;
import com.synway.common.exception.SystemException;
import com.synway.dataoperations.pojo.dataPiledMonitor.ConnectInfo;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.directory.server.kerberos.shared.keytab.Keytab;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author
 */
public class KafkaUtil {
    protected static final Logger logger = LoggerFactory.getLogger(KafkaUtil.class);

    /**
     * 构造kafka properties
     *
     * @param connectInfo
     * @return
     */
    public static Properties geneConsumerProp(ConnectInfo connectInfo) throws ClassNotFoundException {
        Properties props = new Properties();
        props.put("max.poll.interval.ms", "86400000");
        props.put("key.deserializer", Class.forName("org.apache.kafka.common.serialization.StringDeserializer"));
        props.put("value.deserializer", Class.forName("org.apache.kafka.common.serialization.ByteArrayDeserializer"));
        props.put("enable.auto.commit", "false");
        props.put("auto.offset.reset", "earliest");
        props.put("zookeeper.connect", connectInfo.getZookeeperIp());
        props.put("bootstrap.servers", connectInfo.getClusterAddr());
        props.put("group.id", connectInfo.getGroup());
        return props;
    }

    /**
     * 创建用户登录信息-文件方式
     *
     * @param kafkaLoginInfo
     * @param props
     */
    public static Map<String, File> setUserLogin(ConnectInfo kafkaLoginInfo, Properties props) {
        if (StringUtils.isNotBlank(kafkaLoginInfo.getKrb5Conf()) && StringUtils.isNotBlank(kafkaLoginInfo.getUserName())) {
            logger.info("存在验证文件，需要配置验证文件信息,配置文件路径saslKerberosServiceName:[{}],userName:[{}],user.keytab:"
                    , kafkaLoginInfo.getSaslKerberosServiceName(), kafkaLoginInfo.getUserName());
            File tempKrb5ConfPath = null;
            File tempKeyTabPath = null;
            Map<String, File> map = new HashMap<>();
            try {
                tempKrb5ConfPath = File.createTempFile("krb5-", ".conf");
                tempKeyTabPath = File.createTempFile("user-", ".keytab");
                try (PrintWriter writer = new PrintWriter(tempKrb5ConfPath);) {
                    Keytab keytab = new Keytab();
                    keytab.setEntries(kafkaLoginInfo.getUserKeytabEntries());
                    keytab.setKeytabVersion(kafkaLoginInfo.getUserKeytabVersion());
                    keytab.write(tempKeyTabPath);
                    writer.println(kafkaLoginInfo.getKrb5Conf());
                } catch (Exception e) {
                    logger.error(ExceptionUtils.getMessage(e));
                }
                String krb5ConfPathStr = tempKrb5ConfPath.getPath().replaceAll("\\\\", "/");
                String keyTabPathStr = tempKeyTabPath.getPath().replaceAll("\\\\", "/");
                logger.info("krb5的临时存储路径{}，keyTab文件的存储路径{}", krb5ConfPathStr, keyTabPathStr);
                System.setProperty("java.security.krb5.conf", krb5ConfPathStr);
                // jaasConf 文件方式或配置sasl.jaas.config
//                configureJAAS(keyTabPathStr, kafkaLoginInfo.getUserName());
                String principal = kafkaLoginInfo.getUserName();
                String jaasConfStr = "com.sun.security.auth.module.Krb5LoginModule required\nuseKeyTab=true\nkeyTab=\"" + keyTabPathStr + "\"\nprincipal=\"" + principal + "\"\nstoreKey=true\ndebug=true;\n";
                props.put("sasl.jaas.config", jaasConfStr);
                logger.info("jaasConf:{}", jaasConfStr);
                System.setProperty("javax.security.auth.useSubjectCredsOnly", "false");
                props.put("acks", "all");
                props.put("security.protocol", "SASL_PLAINTEXT");
                props.put("sasl.kerberos.service.name", kafkaLoginInfo.getSaslKerberosServiceName());
            } catch (Exception e) {
                logger.error(ExceptionUtils.getMessage(e));
            } finally {
                map.put("krb5Conf", tempKrb5ConfPath);
                map.put("userKeytab", tempKeyTabPath);
                return map;
            }

        }
        return null;
    }

    /**
     * 创建用户登录信息-配置方式
     *
     * @param connectInfo
     * @param props
     */
    public static void setUserLoginByConfig(ConnectInfo connectInfo, Properties props) {
        if (StringUtils.isNotBlank(connectInfo.getKrb5Conf()) && StringUtils.isNotBlank(connectInfo.getUserName())) {
            logger.info("存在验证文件，需要配置验证文件信息,userName:[{}],krb5.conf:[{}],user.keytab:[{}]"
                    , connectInfo.getUserName(), connectInfo.getKrb5Conf(), connectInfo.getUserKeytab());
            try {
                String krb5ConfPathStr = connectInfo.getKrb5Conf();
                String keyTabPathStr = connectInfo.getUserKeytab();
                logger.info("krb5的临时存储路径{}，keyTab文件的存储路径{}", krb5ConfPathStr, keyTabPathStr);
                // krb5环境变量设置
                System.setProperty("java.security.krb5.conf", krb5ConfPathStr);
                // jaasConf配置
                String principal = connectInfo.getUserName();
                String jaasConfStr = "com.sun.security.auth.module.Krb5LoginModule required\nuseKeyTab=true\nkeyTab=\"" + keyTabPathStr + "\"\nprincipal=\"" + principal + "\"\nstoreKey=true\ndebug=true;\n";
                props.put("sasl.jaas.config", jaasConfStr);
                logger.info("jaasConf:{}", jaasConfStr);
                System.setProperty("javax.security.auth.useSubjectCredsOnly", "false");
                props.put("acks", "all");
                props.put("security.protocol", "SASL_PLAINTEXT");
                props.put("sasl.kerberos.service.name", connectInfo.getSaslKerberosServiceName());

            } catch (Exception e) {
                logger.error(e.toString());
            }
        }
    }


    /**
     * 生成jaas.conf临时文件
     */
    public static void configureJAAS(String keyTab, String principal) {
        String content = String.format(JAAS_TEMPLATE, keyTab, principal);
        logger.info("jaas.conf的配置内容为：" + content);
        File jaasConf = null;
        PrintWriter writer = null;

        try {
            jaasConf = File.createTempFile("jaas", ".conf");
            writer = new PrintWriter(jaasConf);
            writer.println(content);
        } catch (Exception e) {
            logger.error("创建临时文件jaas.conf报错" + e.getMessage());
        } finally {
            if (writer != null) {
                writer.close();
            }
            if (jaasConf != null) {
                jaasConf.deleteOnExit();
                logger.info("生成临时文件jaas.conf路径为：" + jaasConf.getAbsolutePath());
                System.setProperty("java.security.auth.login.config", jaasConf.getAbsolutePath());
            } else {
                logger.info("生成临时文件jaas.conf报错，没有生成成功");
            }
        }
    }

    private static final String JAAS_TEMPLATE =
            "Client {\n" +
                    "   com.sun.security.auth.module.Krb5LoginModule required\n" +
                    "   useKeyTab=true\n" +
                    "   storeKey=true\n" +
                    "   keyTab=\"%1$s\"\n" +
                    "   principal=\"%2$s\";\n" +
                    "};" +
                    "\n" +
                    "KafkaClient {\n" +
                    "   com.sun.security.auth.module.Krb5LoginModule required\n" +
                    "   useKeyTab=true\n" +
                    "   storeKey=true\n" +
                    "   keyTab=\"%1$s\"\n" +
                    "   principal=\"%2$s\";\n" +
                    "};";

    public static KafkaConsumer connect(ConnectInfo connectInfo) {
        Map<String, File> fileMap = null;
        try {
            Properties props = KafkaUtil.geneConsumerProp(connectInfo);
            // Kerberos认证
            if (StringUtils.equalsIgnoreCase("1", connectInfo.getLinkType())) {
                fileMap = KafkaUtil.setUserLogin(connectInfo, props);
            }
            KafkaConsumer kafkaConsumer = new KafkaConsumer<>(props);
            return kafkaConsumer;
        } catch (Exception e) {
            logger.error("获取KafkaConsumer失败：\n" + ExceptionUtil.getExceptionTrace(e));
        } finally {
            if (null != fileMap) {
                try {
                    File tempKrb5ConfPath = fileMap.get("krb5Conf");
                    File tempKeyTabPath = fileMap.get("userKeytab");
                    if (tempKrb5ConfPath != null) {
                        tempKrb5ConfPath.delete();
                        logger.info("文件Krb5Conf{}删除", tempKrb5ConfPath.getPath());
                    }
                    if (tempKeyTabPath != null) {
                        tempKeyTabPath.delete();
                        logger.info("文件KeyTab{}删除", tempKeyTabPath.getPath());
                    }
                } catch (Exception e) {
                    logger.error("文件删除失败{}", ExceptionUtils.getMessage(e));
                }
            }
        }
        return null;
    }

    public void close(KafkaConsumer kafkaConsumer) {
        try {
            if(null != kafkaConsumer) {
                kafkaConsumer.close();
            }
        } catch (Exception e) {
            logger.error("关闭KafkaConsumer失败：\n" + ExceptionUtil.getExceptionTrace(e));
        }
    }

}
