package com.synway.reconciliation.schedule.issue;

import com.synway.common.exception.CommonErrorCode;
import com.synway.common.exception.SystemException;
import com.synway.reconciliation.conditional.KafkaMqCondition;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * @author : chenfei
 * @date : 2023/9/6 15:41
 * @describe :
 */
@Service
@Slf4j
@Conditional(KafkaMqCondition.class)
public class KafkaMessageServiceImpl implements MessageService{
    private Producer<String, String> producer;
    private KafkaConsumer<String, String> consumer;

    @Value("${account.kafka.bootstrap}")
    private String bootstrap;
    @Value("${account.kafka.zookeeper}")
    private String zookeeper;
    @Value("${groupId}")
    private String groupId;


    @Override
    public void createProducer(){
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrap);
        //这意味着leader需要等待所有备份都成功写入日志，这种策略会保证只要有一个备份存活就不会丢失数据。这是最强的保证。
        props.put("acks", "0");
        props.put("retries", "1");
        props.put("linger.ms", 50);
        props.put("receive.buffer.bytes", 1048576);
        props.put("batch.size", 1048576);
        //max强制设成16mb，为防止非结构化文件等大数据单挑记录大小超出无法发送;可以理解成kafka客户端与服务端同步数据的最大单位。
        props.put("max.request.size", 1048576*16);
        //buffer.memory必须比batch.size大很多，这里设置成64mb
        props.put("buffer.memory", 1048576*64);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        this.producer  = new KafkaProducer<String, String>(props);
    }

    @Override
    public void createConsumer() {
        Properties props = new Properties();
        props.put("session.timeout.ms",6000);
        props.put("enable.auto.commit", "true");
        props.put("fetch.max.bytes", 1024*1024*32);
        props.put("fetch.max.wait.ms",6000);
        props.put("max.poll.records",10000);
        props.put("zookeeper.connect",zookeeper);
        props.put("bootstrap.servers",bootstrap);
        props.put("group.id",groupId);
        props.put("auto.offset.reset","earliest");
        // receive.buffer.bytes
        props.put("receive.buffer.bytes", 1024*1024);
        props.put("max.partition.fetch.bytes", 1024*1024);
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        this.consumer = new KafkaConsumer<String, String>(props);
    }

    @Override
    public void send(String topic, String data){
        try{
            this.producer.send(new ProducerRecord<String, String>(topic, this.createKey(), data));
            this.producer.flush();
        }catch (Exception e){
            throw new SystemException(CommonErrorCode.RUNTIME_ERROR, "发送任务信息数据到kafka异常：" + ExceptionUtils.getStackTrace(e));
        }
    }

    @Override
    public List<String> consumer(String topic) {
        List<String> result = new ArrayList<>();
        try {
            List<PartitionInfo> partitionInfos = this.consumer.partitionsFor(topic);
            List<TopicPartition> topicPartitions = new ArrayList<>();
            for (PartitionInfo part : partitionInfos) {
                int pNum = part.partition();
                TopicPartition topicPartition = new TopicPartition(topic, pNum);
                topicPartitions.add(topicPartition);
            }
            this.consumer.assign(topicPartitions);
            boolean isRun = true;
            while (isRun){
                ConsumerRecords<String, String> records = this.consumer.poll(Duration.of(6000, ChronoUnit.MILLIS));
                if (records.count() <= 0){
                    isRun = false;
                }else {
                    for (ConsumerRecord<String, String> record : records) {
                        result.add(String.valueOf(record.value()));
                        log.info("consumer kafka data:{}", record.value());
                    }
                    this.consumer.commitSync();
                }
            }
            this.consumer.commitSync();
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }

    @Override
    public void closeProducer() {
        if (this.producer != null){
            this.producer.close();
        }
    }

    @Override
    public void closeConsumer() {
        if (this.consumer != null){
            this.consumer.close();
        }
    }

    private String createKey(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String var1 = sdf.format(new Date());
        String var2 = UUID.randomUUID().toString().replace("-","");
        String var3 = String.valueOf((int) (Math.random() * 1000));
        return var1 + '.' + var2 + '.' + var3;
    }

}
