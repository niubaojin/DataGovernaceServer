package com.synway.datarelation.dao.model;

import com.synway.datarelation.dao.BaseDAO;
import com.synway.datarelation.pojo.modelmonitor.ModelNodeInfo;
import com.synway.datarelation.pojo.modelmonitor.ModelNodeInsInfo;
import com.synway.datarelation.pojo.modelmonitor.ModelProject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;


@Mapper
@Repository
public interface ModelStatisticDao  extends BaseDAO {

    /**
     *
     * @return
     */
    List<ModelProject> selectModelProject();

    /**
     * 入库模型数据
     * @param modelProjects
     */
    void insertModelProject(List<ModelProject> modelProjects);

    /**
     * 删除模型项目数据
     * @param modelProject
     */
    void deleteModelProject(@NotNull @Param("modelProject") ModelProject modelProject);

    /**
     * 更新项目数据
     * @param modelProject
     */
    void updateProject(@Param("modelProject") ModelProject modelProject);



    /**
     *
     * @return
     */
    List<ModelNodeInfo> selectModelNodeInfo();

    /**
     * 入库节点数据
     * @param modelNodeInfos
     */
    void insertModelNodeInfo(List<ModelNodeInfo> modelNodeInfos);

    /**
     * 删除模型节点数据
     * @param modelNodeInfo
     */
    void deleteModelNode(@NotNull @Param("modelNodeInfo") ModelNodeInfo modelNodeInfo);


    /**
     * 获取工作流类型实例
     * @return
     */
    List<ModelNodeInsInfo> selectModelFlowNodeIns();

    /**
     * 入库节点实例数据
     * @param modelNodeInsInfos
     */
    void insertModelFlowNodeIns(List<ModelNodeInsInfo> modelNodeInsInfos);

    /**
     * 更新工作流实例数据
     * @param modelNodeInsInfo
     */
    void updateFlowIns(@Param("nodeInstance") ModelNodeInsInfo modelNodeInsInfo);

    /**
     * 删除M_Flow表数据
     */
    void deleteModelFlowIns();


    /**
     * 获取 m_flow 表的数据量大小
     * @return
     */
    int getModelFlowTableCount();

}
