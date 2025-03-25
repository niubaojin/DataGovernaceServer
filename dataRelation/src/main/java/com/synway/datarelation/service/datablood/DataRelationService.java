package com.synway.datarelation.service.datablood;



import com.synway.datarelation.pojo.databloodline.DataRelationVo;
import com.synway.datarelation.pojo.databloodline.OdpsFiled;

import java.util.List;

/**
 * 数据溯源的相关接口
 * @author wangdongwei
 */
public interface DataRelationService {

    /**
     * 查询odps中相关的表结构 使用的还是直接连接odps查询
     * @TODO     不知道是否需要使用连接数据仓库的接口来查询表结构
     * @param projectName
     * @param tablename
     * @return
     */
    public List<OdpsFiled> getOdpsFiledMsg(String projectName, String tablename);

    /**
     *  判断这个数据在表中是否存在
     * @param dataRelationVo
     * @return
     */
    public DataRelationVo getDataExist(DataRelationVo dataRelationVo);

    /**
     * 查询Odps中数据溯源方法
     * @param dataRelationVo
     * @return
     */
    public DataRelationVo getDataDetail(DataRelationVo dataRelationVo);
}
