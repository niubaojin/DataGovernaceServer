package com.synway.reconciliation.schedule.issue;


import java.util.List;

/**
 * @author : chenfei
 * @date : 2023/9/6 10:40
 * @describe :
 */
public interface MessageService {

    /**
     * 创建生产者
     */
    void createProducer();

    /**
     * 创建消费者
     */
    void createConsumer();

    /**
     * 发送消息
     * @param topic 主题
     * @param data 数据
     */
    void send(String topic, String data);

    /**
     * 消费消息
     * @param topic 主题
     * @return
     */
    List<String> consumer(String topic);

    /**
     * 关闭生产者
     */
    void closeProducer();

    /**
     * 关闭消费者
     */
    void closeConsumer();

}
