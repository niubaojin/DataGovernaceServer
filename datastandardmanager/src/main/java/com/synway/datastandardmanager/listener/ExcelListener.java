package com.synway.datastandardmanager.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.Getter;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @author wdw
 * @version 1.0
 * @date 2021/6/9 19:01
 */
public class ExcelListener<T> extends AnalysisEventListener<T> {
    private Logger logger = LoggerFactory.getLogger(ExcelListener.class);
    @Getter
    List<T> voList = Lists.newArrayList();

    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        voList.add(t);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        logger.info("所有数据解析完成");
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
//        super.invokeHeadMap(headMap, context);
        logger.info("解析到得excel表头数据:{}",headMap);
    }
}
