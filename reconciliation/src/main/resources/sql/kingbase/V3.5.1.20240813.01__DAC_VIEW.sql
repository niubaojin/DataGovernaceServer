CREATE OR REPLACE VIEW CLASSIFY_SOURCE_TARGET AS
select r.inputObjEngName as YXYBM , SJXJBM,
       SJXJZWMC,SJYJFL,SJEJFL,NAME,TABLENAME,INSERT_DATE,SJLYYJ,SJLYEJ,
       SECONDARYORGANIZATIONCH,FIRSTORGANIZATIONCH,PRIMARYORGANIZATIONCH,SECONDARYDATASOURCECH,PRIMARYDATASOURCECH,OBJECTID ,OBJECTSTATE
from (
         select
             -- 对应标准数据项集编码
             tableid as SJXJBM,
             -- 数据项集中文名称
             TABLENAME_CN as SJXJZWMC,
             -- 数据组织一级分类id
             parClass_id as SJYJFL,
             -- 数据组织二级分类id
             secondClass_id as SJEJFL,
             -- 数据名称
             case when objectname is null then TABLENAME_CN else objectname end as NAME,
             -- 表名
             upper(tableName) as TABLENAME,
             -- 数据的插入时间
             case when MODDATE is null then sysdate else MODDATE end as INSERT_DATE,
             -- 数据来源一级分类id
             parClass_id_source as SJLYYJ,
             -- 数据来源二级分类ID
             secondClass_id_source as SJLYEJ,
             case when (firstClass_ch is null and parClass_ch is not null) then '' when (firstClass_ch is null and parClass_ch is null) then '未知' else firstClass_ch end as FIRSTORGANIZATIONCH,
             case when (secondClass_ch is null and parClass_ch is not null) then '' when (secondClass_ch is null and parClass_ch is  null) then '未知' else secondClass_ch end  as SECONDARYORGANIZATIONCH,
             case when parClass_ch is null then '未知' else parClass_ch end  as PRIMARYORGANIZATIONCH,
             case when (secondClass_ch_source is null and parClass_ch_source is null) then '未知' when (secondClass_ch_source is null and parClass_ch_source is not null) then '' else secondClass_ch_source end as  SECONDARYDATASOURCECH,
             case when parClass_ch_source is null then '未知' else parClass_ch_source end  as PRIMARYDATASOURCECH,objectid,OBJECTSTATE
         from(
                 select
-- 表英文名
org.TABLENAME as tableName,
-- 表中文名称
org.objectname as TABLENAME_CN,
-- 最后修改时间
org.UPDATETIME as MODDATE,
org.tableid ,
org.objectname,
org.objectid,
org.OBJECTSTATE,
-- 针对于原始库的二级组织分类id
case when upper(parvci.codeid) = 'JZCODEGASJZZFL'  then '' else sparvci.CODENAME end as firstCodename_id,
-- 针对于原始库的二级组织的中文名
case when upper(parvci.CODEID)='JZCODEGASJZZFL' then ''else sparvci.CODETEXT end as firstClass_ch,
-- 二级组织分类id
case when upper(parvci.CODEID) = 'JZCODEGASJZZFL'  then '' else vci.codename end  as secondClass_id ,
-- 二级组织分类的中文名
case when upper(parvci.CODEID) = 'JZCODEGASJZZFL'  then '' else vci.CODETEXT end  as secondClass_ch ,
-- 一级组织分类id
case when upper(parvci.codeid) = 'JZCODEGASJZZFL' then vci.codename else parvci.codename end  as  parClass_id,
-- 一级组织分类的中文名
case when upper(parvci.codeid) = 'JZCODEGASJZZFL' then vci.CODETEXT else parvci.CODETEXT end  as  parClass_ch,
-- 二级来源的分类
sourceVci.codename as secondClass_id_source ,
sourceVci.CODETEXT  as secondClass_ch_source,
-- 一级来源的分类
sourceParVci.codename   as parClass_id_source ,
sourceParVci.CODETEXT  as parClass_ch_source
                 from (select * from SYNLTE.object  where DELETED = 0 and
                     upper(relate_tablename) = 'OBJECTFIELD') org
                          left join  v_classify_info parvci on upper(parvci.codename) = upper(org.SJZZYJFLVALLUE) and parvci.root = 'JZCODEGASJZZFL'
                          left join v_classify_info vci on upper(vci.codename) = upper(org.SJZZEJFLVALUE)
                          left join v_classify_info sourceVci on upper(sourceVci.codename) = upper(org.SJZYLYLXVALUE) and sourceVci.root = 'GACODE000404'
                          left join  v_classify_info sourceParVci on upper(sourceParVci.codeid) = upper(sourceVci.parcodeid)
                          left join v_classify_info sparvci on upper(vci.parcodeid) = upper(sparvci.codeid)
             )where tableid is not null
     ) t left join (
    SELECT outobjengname, inputObjEngName, objectname
    FROM (SELECT f4.sys_id      as inputSysId,
                 f4.OBJ_ENGNAME as inputObjEngName,
                 f4.data_id     as dataId,
                 f4.table_id    as tableId,
                 f4.OBJ_CHINAME as inputObjChiName,
                 f4.IOBJ_SOURCE as inputIobjSource,
                 f1.IOR_STATUS  as iorStatus,
                 f7.sys_id      AS outSysId,
                 f7.OBJ_ENGNAME AS outObjEngName,
                 f7.OBJ_CHINAME AS outObjChiName,
                 f7.OOBJ_SOURCE AS outOobjSource,
                 f8.tablename   as tableRealName,
                 f8.objectname  as objectname
          FROM STANDARDIZE_INPUTOBJECTRELATE f1
                   inner join (select sys_id,
                                      OBJ_ENGNAME,
                                      data_id,
                                      table_id,
                                      OBJ_CHINAME,
                                      IOBJ_SOURCE,
                                      IOBJ_GUID
                               from STANDARDIZE_INPUTOBJECT f2
                                        inner join STANDARDIZE_OBJECT f3
                                                   on f3.OBJ_GUID = f2.obj_guid
                               where f2.iobj_status = 1) f4
                              on upper(f1.IOBJ_GUID) = upper(f4.IOBJ_GUID)
                   inner join (select sys_id,
                                      OBJ_ENGNAME,
                                      OBJ_CHINAME,
                                      OOBJ_SOURCE,
                                      oobj_guid
                               from STANDARDIZE_OUTPUTOBJECT f5
                                        inner join STANDARDIZE_OBJECT f6
                                                   on f5.OBJ_GUID = f6.obj_guid
                               where f5.OOBJ_STATUS = 1) f7
                              on upper(f1.OOBJ_GUID) = upper(f7.oobj_guid)
                   inner join (select tablename, tableid, objectname
                               from synlte.OBJECT
                               where deleted = 0
                                 and relate_tablename = 'OBJECTFIELD') f8
                              on upper(f8.tableid) = upper(f7.OBJ_ENGNAME))
    where iorStatus = 1
) r on t.SJXJBM=r.outobjengname;