package com.synway.datarelation.pojo.common.melon;

import lombok.Data;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author chenfei
 * @Data 2024/6/6 10:54
 * @Description
 */
@Data
public class RelationLinksCache {

    private LinkedBlockingQueue<RelationLink> linksQueue;


}
