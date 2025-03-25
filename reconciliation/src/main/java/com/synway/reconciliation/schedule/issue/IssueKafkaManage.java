package com.synway.reconciliation.schedule.issue;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hazelcast.core.HazelcastInstance;
import com.synway.reconciliation.common.Constants;
import com.synway.reconciliation.conditional.IssueCondition;
import com.synway.reconciliation.conditional.KafkaMqCondition;
import com.synway.reconciliation.dao.IssueReconciliationDao;
import com.synway.reconciliation.pojo.DacAcceptorBill;
import com.synway.reconciliation.pojo.DacProviderBill;
import com.synway.reconciliation.pojo.issue.RelateJob;
import com.synway.reconciliation.service.IssueReconciliationService;
import com.synway.reconciliation.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

/**
 * @author DZH
 */
@Slf4j
@Component
@Conditional({IssueCondition.class, KafkaMqCondition.class})
public class IssueKafkaManage {

    @Autowired
    private MessageService messageService;

    @Autowired
    private IssueReconciliationDao issueReconciliationDao;

    @Autowired
    private IssueReconciliationService issueReconciliationService;

    @Value("${account.kafka.inceptProvideBill}")
    private String inceptProvideBill;

    @Value("${account.kafka.inceptAcceptBill}")
    private String inceptAcceptBill;

    @Value("${account.kafka.inceptTaskInfo}")
    private String inceptTaskInfo;

    @Value("${local}")
    private String local;

    private final HazelcastInstance hazelcastInstance;

    @Autowired
    public IssueKafkaManage(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    /**
     * 消费下发提供方账单
     */
    public void consumerIssueProviderBill() {
        try {
            messageService.createConsumer();
            List<String> consumer = messageService.consumer(inceptProvideBill);
            List<DacProviderBill> list = new ArrayList<>();
            for (String s : consumer) {
                List<DacProviderBill> dataBills  = JSONArray.parseArray(s, DacProviderBill.class);
                list.addAll(dataBills);
            }
            log.info(JSONObject.toJSONString(list)+ "---------------------------------PROVIDER");
            issueReconciliationService.insertIssueProviderBill(list);
        } catch (Exception e) {
            log.error("consumeIssueProvider error" + e.toString());
        } finally {
            messageService.closeConsumer();
        }
    }

    /**
     * 消费下发接收方账单
     */
    public void consumerIssueAcceptorBill() {
        try {
            messageService.createConsumer();
            List<String> consumer = messageService.consumer(inceptAcceptBill);
            List<DacAcceptorBill> list = new ArrayList<>();
            for (String s : consumer) {
                List<DacAcceptorBill> dataBills  = JSONArray.parseArray(s, DacAcceptorBill.class);
                list.addAll(dataBills);
            }
            log.info(JSONObject.toJSONString(list)+ "---------------------------------ACCEPTOR");
            issueReconciliationService.insertIssueAcceptorBill(list);
        } catch (Exception e) {
            log.error("consumerIssueAcceptorBill error" + e.toString());
        } finally {
            messageService.closeConsumer();
        }
    }

    /**
     * 消费任务相关信息
     */
    public void consumeTaskInfo() {
        try {
            messageService.createConsumer();
            List<String> consumer = messageService.consumer(inceptTaskInfo);
            List<RelateJob> list = new ArrayList<>();
            for (String s : consumer) {
                List<RelateJob> dataBills  = JSONArray.parseArray(s, RelateJob.class);
                List<RelateJob> relateJobs = new ArrayList<>();
                for (RelateJob dataBill : dataBills) {
                    if (dataBill == null) {
                        continue;
                    }
                    if (dataBill.getDataStartTime() != null && dataBill.getDataEndTime() != null) {
                        relateJobs.add(dataBill);
                    }
                }
                list.addAll(relateJobs);
            }
            issueReconciliationService.insertTaskInfos(list);
        } catch (Exception e) {
            log.error("consumeTaskInfo error :"  + e.toString());
        } finally {
            messageService.closeConsumer();
        }
    }

    /**
     * 发送任务相关信息
     */
    public void sendTaskInfo() {
        try {
            messageService.createProducer();
            ConcurrentMap<String, String> taskInfoTagMap = hazelcastInstance.getMap(Constants.TASK_INFO_TAG_MAP);
            String tag = taskInfoTagMap.get("taskInfoTag");
            if (StringUtils.isBlank(tag)) {
                tag = "";
            }
            String taskInfoTag = null;
            List<RelateJob> allRelateJob = issueReconciliationDao.getAllRelateJob(tag);
            if (null != allRelateJob && allRelateJob.size() > 0) {
                taskInfoTag = DateUtil.formatDate(allRelateJob.stream().max(Comparator.comparing(t -> t.getCreateTime().getTime())).get().getCreateTime(), DateUtil.DEFAULT_PATTERN_DATETIME);
                for (RelateJob relateJob : allRelateJob) {
                    relateJob.setLocal(local);
                }
                String data = JSONArray.toJSONString(allRelateJob);
                messageService.send(inceptTaskInfo, data);
            }
            if (StringUtils.isNotBlank(taskInfoTag)) {
                issueReconciliationDao.deleteTaskInfoTag();
                issueReconciliationDao.insertTaskInfoTag(taskInfoTag);
                taskInfoTagMap.put("taskInfoTag", taskInfoTag);
            }
        } catch (Exception e) {
            log.error("sendTaskInfo error :" + e.toString());
        } finally {
            messageService.closeProducer();
        }
    }

    /**
     * 发送下发对账单到kafka
     * @param data 账单数据
     * @param type 账单类型
     * @return
     */
    public boolean sendAccount(String data, String type) {
        try{
            log.info("开始向kafka发送账单...");
            this.messageService.createProducer();
            String topic = inceptAcceptBill;
            if (type.contains("provider")) {
                topic = inceptProvideBill;
            }
            this.messageService.send(topic, data);
            log.info("向kafka发送账单完毕...");
            return true;
        } catch (Exception e) {
            log.error("向kafka发送账单失败", e);
            return false;
        } finally {
            this.messageService.closeProducer();
        }
    }
}
