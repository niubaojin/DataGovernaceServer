package com.synway.dataoperations.pojo.dataPiledMonitor;

import lombok.Data;
import org.apache.directory.server.kerberos.shared.keytab.KeytabEntry;

import java.io.Serializable;
import java.util.List;

/**
 * @author
 */
@Data
public class ConnectInfo implements Serializable {
    private String linkType;        // 验证方式
    private String zookeeperIp;     // zookeeperIP地址
    private String clusterAddr;     // Kafka集群IP地址
    private String group;           // 组
    private String topic;           // 主题
    private List<KeytabEntry> userKeytabEntries; // user.KeyTab 文件信息
    private byte[] userKeytabVersion;
    private String userKeytab;
    private String krb5Conf;        // krb5.conf的文件信息
    private String userName;        // kafka的用户名信息
    private String saslKerberosServiceName;     // saslKerberosServiceName 默认为 kafka

    public ConnectInfo(){

    }

    public ConnectInfo(String linkType, String zookeeperIp, String clusterAddr, String group, String topic, List<KeytabEntry> userKeytabEntries, byte[] userKeytabVersion, String krb5Conf, String userName, String saslKerberosServiceName) {
        this.linkType = linkType;
        this.zookeeperIp = zookeeperIp;
        this.clusterAddr = clusterAddr;
        this.group = group;
        this.topic = topic;
        this.userKeytabEntries = userKeytabEntries;
        this.userKeytabVersion = userKeytabVersion;
        this.krb5Conf = krb5Conf;
        this.userName = userName;
        this.saslKerberosServiceName = saslKerberosServiceName;
    }

}
