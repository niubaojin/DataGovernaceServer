--------------------------------------------------------------------------------常用sql
-- 建表
create table TABLE_ORGANIZATION_ASSETS_BAKP AS SELECT * FROM TABLE_ORGANIZATION_ASSETS where to_char(statistics_time, 'yyyy/MM/dd') > to_char(sysdate - 15, 'yyyy/MM/dd')

-- 查询
select count(*) from TABLE_ORGANIZATION_ASSETS t where to_char(statistics_time, 'yyyy/MM/dd') = to_char(sysdate, 'yyyy/MM/dd')

-- 删除
delete TABLE_ORGANIZATION_ASSETS t where to_char(statistics_time, 'yyyy/MM/dd') = to_char(sysdate, 'yyyy/MM/dd')

-- 更新
update TABLE_ORGANIZATION_ASSETS_BAKP
   set statistics_time = sysdate
 where to_char(statistics_time, 'yyyy/MM/dd') = to_char(sysdate -1, 'yyyy/MM/dd')

-- 插入
insert into TABLE_ORGANIZATION_ASSETS select * from TABLE_ORGANIZATION_ASSETS_BAKP where to_char(statistics_time, 'yyyy/MM/dd') = to_char(sysdate, 'yyyy/MM/dd')

-- 杀掉占用回话进程
select b.username,b.sid,b.serial#,logon_time from v$locked_object a,v$session b where a.session_id=b.sid
alter system kill session '1902,3309'

select to_char(sysdate, 'yyyy/MM/dd') from dual
select userenv('language') from dual

-- 创建表空间
select * from dba_data_files
create tablespace synlte datafile '/data1/oracle/app/oadat/oadat/synlte01.dbf' size 50m autoextend on next 50m maxsize unlimited extent management local;

-- 查看字符集编码
select userenv('language') from dual
-- 设置Windows字符集，打开命令行窗口
set nls_lang=AMERICAN_AMERICA.AL32UTF8


-- 修改Oracle的process和session数量
alter system set sessions=2000 scope=spfile sid='*';
alter system set processes=1000 scope=spfile sid='*';

-- 释放资源
select l.session_id,o.owner,o.object_name from v$locked_object l, dba_objects o where l.object_id=o.object_id
select sid, serial#, username, osuser, terminal,program, action, prev_exec_start from v$session where sid = 1921
alter system kill session '1921,21049'

------------------------





