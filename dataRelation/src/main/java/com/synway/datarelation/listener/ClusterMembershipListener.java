package com.synway.datarelation.listener;

import com.alibaba.fastjson.JSONObject;
import com.hazelcast.cluster.MembershipEvent;
import com.hazelcast.cluster.MembershipListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 不同会员的监听事件 出现相同节点上线/下线的事件
 * @author wangdongwei
 * @date 2021/3/17 15:23
 */
public class ClusterMembershipListener implements MembershipListener {
    private static Logger logger = LoggerFactory.getLogger(ClusterMembershipListener.class);

    @Override
    public void memberAdded(MembershipEvent membershipEvent) {
        logger.info("数据血缘出现新的程序:"+ membershipEvent.toString());
    }

    @Override
    public void memberRemoved(MembershipEvent membershipEvent) {
        logger.info("数据血缘去除掉新的程序:"+ membershipEvent.toString());
    }
}
