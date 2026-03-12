--将 data_common_setting 的数据转移到  dgn_common_setting 中,并淘汰 data_common_setting表的使用
insert into dgn_common_setting (PARENT_ID, NAME, LOGICAL_JUDGMENT, THRESHOLD_VALUE, ID, TREE_TYPE, IS_ACTIVE, PAGE_URL)
select PARENT_ID, NAME, LOGICAL_JUDGMENT, THRESHOLD_VALUE, ID, TREE_TYPE, IS_ACTIVE, PAGE_URL from data_threshold_setting;

--将 large_screen_data 的数据转移到  dgn_data_large_screen 中,并淘汰 large_screen_data
insert into dgn_data_large_screen (MODULE_ID, MODULE_NAME, DATA_INFO, CREATE_TIME)
select MODULE_ID, MODULE_NAME, DATA_INFO, CREATE_TIME from large_screen_data;

--将 data_navbar 的数据转移到  dgn_navbar 中,并淘汰 data_navbar
insert into dgn_navbar (NAV_ID, NAV_NAME, NAV_CLASS, NAV_LINK, NAV_SHOW, NAV_BLANK, NAV_LEVEL, NAV_PARENT_NAME, NAV_ORDER, NAV_NAME_EN, NAV_IP, NAV_REDIRECT, NAV_HIDDEN)
select NAV_ID, NAV_NAME, NAV_CLASS, NAV_LINK, NAV_SHOW, NAV_BLANK, NAV_LEVEL, NAV_PARENT_NAME, NAV_ORDER, NAV_NAME_EN, NAV_IP, NAV_REDIRECT, NAV_HIDDEN from data_navbar;

--将 DP_APPROVAL_INFO 的数据转移到  dgn_approval 中,并淘汰 DP_APPROVAL_INFO
insert into dgn_approval (APPROVAL_ID, MODULE_NAME, OPERATION_NAME, APPLICATION_INFO, CALLBACK_DATA, CALLBACK_URL, CREATE_TIME, STATUS, MODULE_ID, OPERATOR_ID, TASK_ID, VIEW_URL, PROCESSINSTANCEID, EXECUTE_RESULT)
select APPROVAL_ID, MODULE_NAME, OPERATION_NAME, APPLICATION_INFO, CALLBACK_DATA, CALLBACK_URL, CREATE_TIME, STATUS, MODULE_ID, OPERATOR_ID, TASK_ID, VIEW_URL, PROCESSINSTANCEID, EXECUTE_RESULT from DP_APPROVAL_INFO;

--将 data_property_large 的数据转移到  dgn_property_large 中,并淘汰 data_property_large
insert into dgn_property_large (ID, DATA, CREATE_TIME, TYPE, SAVE_FLAG)
select ID, DATA, CREATE_TIME, TYPE, SAVE_FLAG from data_property_large;

--将 city_abb 的数据转移到 dgn_city_abb 中,并淘汰 city_abb
insert into dgn_city_abb (CITY_ABB, CITY_CODE, CITY_NAME)
select CITY_ABB, CITY_CODE, CITY_NAME from city_abb;

--将 inspect_operators_stat 的数据转移到 dgn_inspect_operators 中,并淘汰 inspect_operators_stat
insert into dgn_inspect_operators (TABLEID, TABLENAME, TABLENAME_CH, DATA_TYPE, OPERATOR_NET, NETMODE, CITYCODE, CITYNAME, RECORDS_ALL, PARTITION, INSERTTIME)
select TABLEID, TABLENAME, TABLENAME_CH, DATA_TYPE, OPERATOR_NET, NETMODE, CITYCODE, CITYNAME, RECORDS_ALL, PARTITION, INSERTTIME from inspect_operators_stat;

--将 data_distribution_statistic 的数据转移到 dgn_distribution_statistic 中,并淘汰 data_distribution_statistic
insert into dgn_distribution_statistic (BIAOMING, NAME, TOTALCOUNT, STATISTICTIME, PARTITIONTIME, DT, DZ)
select BIAOMING, NAME, TOTALCOUNT, STATISTICTIME, PARTITIONTIME, DT, DZ from data_distribution_statistic;

--将 source_nonstandarddata 的数据转移到 dgn_nostd_distribute_statistic 中,并淘汰 source_nonstandarddata
insert into dgn_nostd_distribute_statistic (ID, NAME, TOTALCOUNT, STATISTICTIME, PARTITIONTIME, TABLENAME, PROJECT, DZ)
select ID, NAME, TOTALCOUNT, STATISTICTIME, PARTITIONTIME, TABLENAME, PROJECT, DZ from source_nonstandarddata;

--将 pytj 的数据转移到 dgn_puyuanmessagebus 中,并淘汰 pytj
insert into dgn_puyuanmessagebus (PLATFORM, PLATFORM_CODE, INTERFACE_NUM, SUMTOTAL, LAST_TIME)
select PLATFORM, PLATFORM_CODE, INTERFACE_NUM, SUMTOTAL, LAST_TIME from pytj;

--将 instructions_text 的数据转移到 dgn_instructions 中,并淘汰 instructions_text
insert into dgn_instructions (TREE_NAME, PARENT_ID, TREE_TYPE, CONTENT, ID)
select TREE_NAME, PARENT_ID, TREE_TYPE, CONTENT, ID from instructions_text;

