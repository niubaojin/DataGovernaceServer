package com.synway.datarelation.dao.monitor;

import com.synway.datarelation.pojo.monitor.business.BusinessEntity;
import com.synway.datarelation.pojo.monitor.page.BusinessNormalReportPageParams;
import com.synway.datarelation.pojo.monitor.page.NormalBusinessInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author majia
 * @version 1.0
 * @date 2020/12/14 16:56
 */
@Mapper
@Repository
public interface NormaInstancePageDao {
    List<NormalBusinessInfo> getNormalBusiness(@Param("queryParams") BusinessNormalReportPageParams queryParams);

    List<Map<String, String>> getNormalBusinessFilterList(@Param("queryParams") BusinessNormalReportPageParams queryParams);

    List<BusinessEntity> getBusiness();
}
