package com.synway.datarelation.pojo.modelmonitor;

import java.util.Comparator;

/**
 * @author
 * @date 2019/4/25 18:47
 */
public class NodeInsComporator implements Comparator<ModelNodeInsInfo> {

    //按instanceId升序排序
    @Override
    public int compare(ModelNodeInsInfo o1, ModelNodeInsInfo o2) {
        int flag = 0;
        if(o1.getInstanceId()>o2.getInstanceId()){
            flag = 1;
        }
        if(o1.getInstanceId()<o2.getInstanceId()){
            flag = -1;
        }
        return flag;
    }


}
