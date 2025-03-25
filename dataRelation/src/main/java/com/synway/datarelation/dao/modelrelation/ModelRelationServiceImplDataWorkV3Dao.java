package com.synway.datarelation.dao.modelrelation;


import com.synway.datarelation.dao.BaseDAO;
import com.synway.datarelation.pojo.monitor.node.MaxVersionNodeId;
import com.synway.datarelation.pojo.datawork.v3.NodeCode;
import com.synway.datarelation.pojo.modelrelation.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ModelRelationServiceImplDataWorkV3Dao extends BaseDAO {

    @Select("select distinct odpsproject_name  from m_node_three")
    List<String> findProjectNameList();

    @Select("select distinct flowName from(" +
            "   select distinct node_name as flowName from m_node_three where prg_type =98 and upper(odpsproject_name) = upper(#{projectName}) and upper(node_name) like upper('%'||#{flowName}||'%')" +
            "   union all" +
            "   select distinct node_name as flowName from m_node_three where  source is  null  and prg_type != 23 and upper(odpsproject_name) = upper(#{projectName}) and upper(node_name) like upper('%'||#{flowName}||'%')" +
            ")")
    List<String> findFlowNameList(@Param("projectName")String projectName, @Param("flowName")String flowName);

    @Select("select node_Id as key,node_Name as text, 'true' as isGroup,'' as groupname , prg_type as nodeType from m_node_three where upper(source) =upper(#{flowName}) and upper(odpsproject_name) = upper(#{projectName})")
    List<ModelNodeData> findAllNodeByFlow(@Param("projectName")String projectName, @Param("flowName")String flowName);

     @Insert("<script>" +
        "begin\n" +
             "             <foreach  collection=\"list\" item=\"record\">\n" +
             "                 insert into M_NODE_CODE_THREE(node_Id,node_code,node_type,FILE_VERSION) \n" +
             "\t\t\t\t values( \n" +
             "                 #{record.nodeId,jdbcType=VARCHAR},#{record.nodeCode,jdbcType=CLOB},\n" +
             "                 #{record.nodeType,jdbcType=VARCHAR},#{record.fileVersion,jdbcType=VARCHAR}\n" +
             "                 );\n" +
             "             </foreach>\n" +
             "             end;"+
        "</script> ")
    void insertNodeCode(@Param("list") List<NodeCode> list);

    @Select("select node_id as nodeId,max(FILE_VERSION) as maxVersion from M_NODE_CODE_THREE\n" +
            "where FILE_VERSION is not null\n" +
            "group by node_id")
    List<MaxVersionNodeId> getMaxVersionNodeCodeByNodetype();

    @Delete("<script>DELETE from M_NODE_CODE_THREE\n" +
            "        <where>\n" +
            "            <foreach collection=\"list\" item=\"item\" separator=\"or\">\n" +
            "                (NODE_ID=#{item.nodeId,jdbcType=NUMERIC} and FILE_VERSION=#{item.maxVersion,jdbcType=NUMERIC})\n" +
            "            </foreach>\n" +
            "        </where></script>")
    int delMaxVersionNodeCode(@Param("list")List<MaxVersionNodeId> paramList);


    @Delete("delete from M_NODE_CODE_THREE where node_type = #{type}")
    int deleteNodeCode(@Param("type") String nodeType);

    @Select("select node_code from M_NODE_CODE_THREE where node_Id = #{nodeId} and rownum =1 ")
    String getNodeCode(@Param("nodeId") String nodeId);

    @Select("select distinct node_id from M_NODE_THREE where source is  null  and prg_type != 23 and upper(odpsproject_name) = upper(#{projectName}) and upper(node_name) = upper(#{flowName}) and rownum =1")
    String getNodeIdByFlowName(@Param("projectName")String projectName, @Param("flowName")String flowName);

    @Select("select nodeId from(select distinct node_id as nodeId from M_NODE_THREE where upper(odpsproject_name) = upper(#{projectName}) and upper(source) = upper(#{flowName}) " +
            "union all " +
            "select distinct node_id as nodeId from M_NODE_THREE where source is  null  and prg_type != 23 and upper(odpsproject_name) = upper(#{projectName}) and upper(node_name) = upper(#{flowName}) " +
            ") where  rownum =1 ")
    String findNodeIdBySource(@Param("projectName")String projectName, @Param("flowName")String flowName);

    @Select("select create_time as CREATED_ON,create_user as created_by ,  modify_user as last_modified_by ,MODIFY_TIME as last_modified_on ,'' as deploy_status,'' as commit_status" +
            "  from(select distinct node_id as nodeId ,create_time,create_user,modify_user,MODIFY_TIME from M_NODE_THREE where upper(odpsproject_name) = upper(#{projectName}) and upper(source) = upper(#{flowName}) " +
            "union all " +
            "select distinct node_id as nodeId ,create_time,create_user,modify_user,MODIFY_TIME from M_NODE_THREE where source is  null  and prg_type != 23 and upper(odpsproject_name) = upper(#{projectName}) and upper(node_name) = upper(#{flowName}) " +
            ") where  rownum =1 ")
    ModelPageMessage getModelPageMessageDao(@Param("projectName")String projectName, @Param("flowName")String flowName);

    @Select("select distinct  biz_id from M_NODE_THREE where upper(odpsproject_name) = upper(#{projectName}) and upper(node_name) = upper(#{flowName}) and source is null\n" +
            "and rownum =1")
    String getBizIdByFlowName(@Param("projectName")String projectName, @Param("flowName")String flowName);

    @Select("SELECT distinct node_Id as key,node_Name as text, 'true' as isGroup,'' as groupname , prg_type as nodeType FROM M_NODE_THREE where upper(odpsproject_name) = upper(#{projectName}) and BIZ_ID = #{bizId}")
    List<ModelNodeData>  getModelNodeNewByFlowName(@Param("projectName")String projectName, @Param("bizId")String bizId);


    @Select("select node_Id as nodeId,node_name as nodeName ,flow_id as flowId, prg_type as prgType,inputs,outputs , odpsproject_name as project,biz_id as bizId from M_NODE_THREE" +
            " where upper(source) = upper(#{flowName}) and upper(odpsproject_name) =upper(#{projectName})")
    List<ModeNodeLinkData> findNodeRelationBySource(@Param("projectName")String projectName, @Param("flowName")String flowName);

    @Select("select node_Id as nodeId,node_name as nodeName ,flow_id as flowId, prg_type as prgType,inputs,outputs , odpsproject_name as project,biz_id as bizId from M_NODE_THREE" +
            " where upper(odpsproject_name) = upper(#{projectName}) and BIZ_ID = #{bizId}")
    List<ModeNodeLinkData> findNodeRelationByNewNode(@Param("projectName")String projectName, @Param("bizId")String bizId);


    @Select("select node_id as nodeId,node_code as nodeCode ,node_type as nodeType,node_name as nodeName ,ODPSPROJECT_NAME as projectName, \n" +
            "case when source is null then node_name when source ='atcloud_flow' then node_name   else source end as flowName ," +
            " fileVersion  from(\n" +
            "           select l.* ,r.node_name,r.source,r.ODPSPROJECT_NAME,r.file_version as fileVersion from " +
            " (select node_id, node_code, node_type, insert_date , file_version ,ROW_NUMBER() over(partition by node_id  " +
            "order by file_version desc) as num  from m_node_code_three)" +
            " l left join M_NODE_THREE r on l.node_id = r.node_id \n" +
            "            ) where node_id is not null and node_type is not null and ODPSPROJECT_NAME is not null  and num = 1")
    List<DataBloodlineRaw> selectDataBloodlineRawList();

//    @Insert("<script>" +
//            "insert all\n" +
//            "     <foreach  collection=\"list\" item=\"record\" index=\"index\">" +
//            "         into  M_NODE_IN_OUT_TABLE(node_Id,node_type,node_name,flow_name,input_tablename,output_tablename)" +
//            "         values( #{record.nodeId,jdbcType=VARCHAR},#{record.nodeType,jdbcType=VARCHAR}," +
//            "         #{record.nodeName,jdbcType=VARCHAR},#{record.flowName,jdbcType=VARCHAR}," +
//            "         #{record.inputTableName,jdbcType=VARCHAR},#{record.outputTableName,jdbcType=VARCHAR}" +
//            "         )" +
//            "     </foreach>" +
//            "     select 1 from dual"+
//            "</script> ")


}
