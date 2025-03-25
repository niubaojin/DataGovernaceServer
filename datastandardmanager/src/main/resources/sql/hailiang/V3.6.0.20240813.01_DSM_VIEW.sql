CREATE OR REPLACE VIEW V_CLASSIFY_INFO AS
SELECT 'JZCODEGASJZZFL' root, CODEID, CODENAME, CODETEXT, PARCODEID
FROM synlte.FIELDCODE
WHERE CODEID = 'JZCODEGASJZZFL'
UNION ALL
SELECT 'JZCODEGASJZZFL' root,
       CODEVALID CODEID,
       VALVALUE CODENAME,
       VALTEXT CODETEXT,
       CODEID PARCODEID
FROM (SELECT fv.*
      FROM synlte.FIELDCODEVAL fv
      WHERE NOT EXISTS (SELECT 1
                        from (SELECT CODEID
                              FROM synlte.FIELDCODE
                              WHERE DELETED != '0'
                              START WITH PARCODEID = 'JZCODEGASJZZFL'
                              CONNECT BY PRIOR CODEID = PARCODEID) fd
                        WHERE fd.CODEID = fv.CODEID)) v
START WITH v.CODEID = 'JZCODEGASJZZFL'
CONNECT BY PRIOR v.CODEVALID = v.CODEID
UNION ALL
SELECT 'GACODE000404' root,
       CODEID,
       CODENAME,
       CASE
           WHEN CODEID = 'GACODE000404' THEN
               '数据来源分类'
           ELSE
               CODETEXT
           END AS CODETEXT,
       PARCODEID
FROM synlte.FIELDCODE
WHERE CODEID = 'GACODE000404'
UNION ALL
SELECT 'GACODE000404' root,
       CODEVALID CODEID,
       VALVALUE CODENAME,
       VALTEXT CODETEXT,
       CODEID PARCODEID
FROM (SELECT fv.*
      FROM synlte.FIELDCODEVAL fv
      WHERE NOT EXISTS (SELECT 1
                        from (SELECT CODEID
                              FROM synlte.FIELDCODE
                              WHERE DELETED != '0'
                              START WITH PARCODEID = 'GACODE000404'
                              CONNECT BY PRIOR CODEID = PARCODEID) fd
                        WHERE fd.CODEID = fv.CODEID)) v
START WITH v.CODEID = 'GACODE000404'
CONNECT BY PRIOR v.CODEVALID = v.CODEID;

create or replace view classify_interface_all_date as
select SJZYSQDW_SQDWDM,SJHQFS,YYXTBH,SJZYGLDW_GAJGJGDM,SJZYWZ,SJZYCCFZX,SJZYBSF,SJZYMLBH,SJXJBM,
       SJXJZWMC,SJZYBQ1,SJZYBQ2,SJZYBQ3,SJZYBQ4,SJZYBQ5,SJZYBQ6,SJZYGXZQ,CLSJJLGM,CLSJCCGM,ZLSJJLGM,ZLSJCCGM,
       SJZYCCZQ,SJZYLY,NAME,MEMO,INSERT_DATE,TABLENAME,REGISTER_STATE,LABELS,SJYJFL,SJEJFL,SJLYYJ,SJLYEJ,PRIMARYORGANIZATIONCH,
       FIRSTORGANIZATIONCH,SECONDARYORGANIZATIONCH,PRIMARYDATASOURCECH ,SECONDARYDATASOURCECH,OBJECTID ,OBJECTSTATE,
       SJYJFLCODENAME,SJEJFLCODENAME,SJSJFLCODENAME,SJLYYJCODENAME,SJLYEJCODENAME from (
select
-- 数据资源事权单位_事权单位代码(弃用)
null as SJZYSQDW_SQDWDM,
-- 数据获取方式 01侦控 02管控 03管理 04公开(弃用)
null as SJHQFS,
-- 应用系统编号(弃用)
null as YYXTBH,
-- 数据资源管理单位_公安机关机构代码(弃用)
null as SJZYGLDW_GAJGJGDM,
-- 数据资源位置 01部 02省 03市 04 网站(弃用)
null as SJZYWZ,
-- 数据资源存储分中心(弃用)
null as SJZYCCFZX,
-- 数据资源标识符 R-数据资源管理单位机构代码-8位数字流水号(弃用)
null as SJZYBSF,
-- 数据资源目录编号(弃用)
null as SJZYMLBH,
-- 对应标准数据项集编码
tableid as SJXJBM,
-- 数据项集中文名称
TABLENAME_CN as SJXJZWMC,
-- 数据资源标签1 1人 2物 3组织 4虚拟标识 5时空(弃用)
SJZYBQ1 as SJZYBQ1,
SJZYBQ2 as SJZYBQ2,
SJZYBQ3 as SJZYBQ3,
SJZYBQ4 as SJZYBQ4,
SJZYBQ5 as SJZYBQ5,
SJZYBQ6 as SJZYBQ6,
-- 数据资源更新周期(弃用)
null as SJZYGXZQ,
-- 存量数据记录规模(弃用)
null as CLSJJLGM,
-- 存量数据存储规模(弃用)
null as CLSJCCGM,
-- 增量数据记录规模(弃用)
null as ZLSJJLGM,
-- 增量数据存储规模(弃用)
null as ZLSJCCGM,
-- 数据资源存储周期(弃用)
null as SJZYCCZQ,
-- 数据资源来源(弃用)
null as SJZYLY,
-- 数据名称
case when objectname is null then TABLENAME_CN else objectname end as NAME,
-- 数据资源描述
objectmemo as MEMO,
-- 数据的插入时间
case when MODDATE is null then sysdate else MODDATE end as INSERT_DATE,
-- 表名
upper(tableName) as TABLENAME,
-- 注册状态
-1 as REGISTER_STATE,
-- 6类标签的数据
LABELS,
-- 数据组织一级分类id
parClass_id as SJYJFL,
-- 数据组织二级分类id
secondClass_id as SJEJFL,
-- 数据来源一级分类id
parClass_id_source as SJLYYJ,
-- 数据来源二级分类ID
secondClass_id_source as SJLYEJ,
-- 数据组织一级分类codename
Parcodename_id as SJYJFLCODENAME,

-- 数据组织二级分类codename
firstCodename_id as SJEJFLCODENAME,

-- 数据组织三级分类codename
codename_id as SJSJFLCODENAME,

-- 数据来源一级分类codename
ParsourceCodename_id as SJLYYJCODENAME,
-- 数据来源二级分类codename
ourcecodename_id as SJLYEJCODENAME,
case when (firstClass_ch is null and parClass_ch is not null) then '' when (firstClass_ch is null and parClass_ch is null) then '未知' else firstClass_ch end as FIRSTORGANIZATIONCH,
case when (secondClass_ch is null and parClass_ch is not null) then '' when (secondClass_ch is null and parClass_ch is  null) then '未知' else secondClass_ch end  as SECONDARYORGANIZATIONCH,
case when parClass_ch is null then '未知' else parClass_ch end  as PRIMARYORGANIZATIONCH,
case when (secondClass_ch_source is null and parClass_ch_source is null) then '未知' when (secondClass_ch_source is null and parClass_ch_source is not null)
    then '' else secondClass_ch_source end as  SECONDARYDATASOURCECH,
case when parClass_ch_source is null then '未知' else parClass_ch_source end  as PRIMARYDATASOURCECH,
objectid,
OBJECTSTATE
from(
       select
           org.TABLENAME as tableName,
           org.objectname as TABLENAME_CN,
           org.tableid ,
           org.objectname,
           org.objectmemo,
           org.SJZYLYLXVALUE,
           org.SJZZYJFLVALLUE,
           org.SJZZEJFLVALUE,
           org.objectid,
           org.OBJECTSTATE,
           org.CREATETIME as CREATEDATE,
           org.UPDATETIME as MODDATE,
           SJZYBQ1,
           SJZYBQ2,
           SJZYBQ3,
           SJZYBQ4,
           SJZYBQ5,
           SJZYBQ6,
           '' as LABELS,
           -- 一级组织的codename
           case when upper(parvci.codeid) = 'JZCODEGASJZZFL' then vci.codename else parvci.codename end  as  parClass_id,
           -- 一级组织的codetext
           case when upper(parvci.codeid) = 'JZCODEGASJZZFL' then vci.CODETEXT else parvci.CODETEXT end  as  parClass_ch,
           -- 二级组织的codename
           case when upper(parvci.codeid) = 'JZCODEGASJZZFL'  then ''
                when upper(sparvci.codeid) = 'JZCODEGASJZZFL03' THEN vci.CODENAME
                else sparvci.CODENAME end as firstCodename_id,
           -- 二级组织的codetext
           case when upper(parvci.CODEID) = 'JZCODEGASJZZFL' then ''
                when upper(sparvci.codeid) = 'JZCODEGASJZZFL03' THEN vci.CODETEXT
                else sparvci.CODETEXT end as firstClass_ch,
           -- 三级组织的codename(只有原始库有三级)
           case when upper(parvci.CODEID) = 'JZCODEGASJZZFL'  then '' else vci.codename end  as secondClass_id ,
           -- 三级组织的codetext(只有原始库有三级)
           case when upper(parvci.CODEID) = 'JZCODEGASJZZFL'  then '' else vci.CODETEXT end  as secondClass_ch ,
           -- 一级来源的codename
           sourceParVci.codename   as parClass_id_source ,
           -- 一级来源的codetext
           sourceParVci.CODETEXT  as parClass_ch_source,
           -- 二级来源的codename
           sourceVci.codename as secondClass_id_source ,
           -- 二级来源codetext
           sourceVci.CODETEXT  as secondClass_ch_source,

           -- 一级组织的codename
           parvci.CODENAME as Parcodename_id,
           -- 二、三级组织的codename(针对于原始库是三级codename，除原始库是二级codename)
           vci.codename as codename_id,
           -- 一级来源的codename
           sourceParVci.CODENAME as ParsourceCodename_id,
           -- 二级来源的codename
           sourceVci.CODENAME as ourcecodename_id
       from(
               select * from SYNLTE.object  where DELETED = 0 and
                   upper(relate_tablename) = 'OBJECTFIELD') org
               left join  v_classify_info parvci on upper(parvci.codename) = upper(org.SJZZYJFLVALLUE) and parvci.root = 'JZCODEGASJZZFL'
               left join  v_classify_info vci on upper(vci.codename) = upper(org.SJZZEJFLVALUE)
               left join v_classify_info sourceVci on upper(sourceVci.codename) = upper(org.SJZYLYLXVALUE) and sourceVci.root = ' GACODE000404'
               left join  v_classify_info sourceParVci on upper(sourceParVci.codeid) = upper(sourceVci.parcodeid)
               left join v_classify_info sparvci on upper(vci.parcodeid) = upper(sparvci.codeid)
   )where tableid is not null
);

