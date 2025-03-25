--------------------------------------------------------------------------------1.7更新
-- t_ads_odps表增加字段，存储oss文件数
alter table t_ads_odps add (file_count varchar2(255));
comment on column t_ads_odps.file_count is '文件数，主要针对oss';

-- syndmg_table_all表增加字段，存储分区数
alter table synlte.syndmg_table_all add (partitionnum number);
comment on column synlte.syndmg_table_all.partitionnum is '分区表的分区个数，非分区表为0';

-- table_organization_assets表增加字段，数据资源id，用于关联权限表的仓库数据
alter table table_organization_assets add (resourceId varchar2(255));
comment on column table_organization_assets.resourceId is '数据资源id，用于关联权限表的仓库数据';

--------------------------------------------------------------------------------
-- 表组织资产加字段
alter table TABLE_ORGANIZATION_ASSETS add (THREELEVEL_ORGANIZATION_CH varchar2(200),ORGANIZATION_ID_LAST_LEVEL varchar2(150),DATASOURCE_LAST_LEVEL varchar2(150));
comment on column TABLE_ORGANIZATION_ASSETS.THREELEVEL_ORGANIZATION_CH is '数据组织三级分类中文名';
comment on column TABLE_ORGANIZATION_ASSETS.ORGANIZATION_ID_LAST_LEVEL is '数据组织分类id(最后一级)';
comment on column TABLE_ORGANIZATION_ASSETS.DATASOURCE_LAST_LEVEL is '数据来源分类id(最后一级)';

-- 首页重点组织表加字段
alter table focus_organization_add_monitor add (USER_ID varchar2(100),USER_AUTHORITY_ID varchar2(100),USER_NAME varchar2(100),IS_ADMIN varchar2(50));
comment on column focus_organization_add_monitor.USER_ID is '用户id';
comment on column focus_organization_add_monitor.USER_AUTHORITY_ID is '权限表用户id';
comment on column focus_organization_add_monitor.USER_NAME is '用户名';
comment on column focus_organization_add_monitor.IS_ADMIN is '是否管理员';

alter table focus_organization_add_monitor add (last_level_classifycode varchar2(100));
comment on column focus_organization_add_monitor.last_level_classifycode is '最后一级分类代码';

--------------------------------------------------------------------------------

