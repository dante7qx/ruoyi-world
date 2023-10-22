
-- 自定义高级查询模板表
drop table if exists sys_cust_adv_query;
create table sys_cust_adv_query (
  query_id bigserial not null primary key,
  template_name varchar(64) not null default '',
  table_name varchar(128) not null default '',
  table_desc varchar(128) default '',
  table_alias varchar(20) not null default '',
  pk_col varchar(64) not null default '',
  create_by varchar(64) default '',
  create_time timestamp,
  update_by varchar(64) default '',
  update_time timestamp
);

COMMENT ON COLUMN sys_cust_adv_query.query_id is '主键Id';
COMMENT ON COLUMN sys_cust_adv_query.template_name is '模板名称';
COMMENT ON COLUMN sys_cust_adv_query.table_name is '数据库表名';
COMMENT ON COLUMN sys_cust_adv_query.table_desc is '数据库表描述';
COMMENT ON COLUMN sys_cust_adv_query.table_alias is '数据库表别名';
COMMENT ON COLUMN sys_cust_adv_query.pk_col is '数据库表主键列名';
COMMENT ON COLUMN sys_cust_adv_query.create_by is '创建者';
COMMENT ON COLUMN sys_cust_adv_query.create_time is '创建时间';
COMMENT ON COLUMN sys_cust_adv_query.update_by is '更新者';
COMMENT ON COLUMN sys_cust_adv_query.update_time is '更新时间';
COMMENT ON TABLE sys_cust_adv_query is '自定义高级查询模板表';

-- 自定义高级查询条件表
drop table if exists sys_cust_adv_query_cond;
create table sys_cust_adv_query_cond (
  cond_id bigserial not null primary key,
  query_id bigint not null,
  col_name varchar(128) not null default '',
  col_desc varchar(128) not null default '',
  java_field varchar(64) not null default '',
  java_type varchar(64) not null default '',
  dict_type varchar(64) default '',
  cipher_flag smallint not null default 0,
  query_flag smallint not null default 0,
  query_type varchar(16) not null default ''
);

COMMENT ON COLUMN sys_cust_adv_query_cond.cond_id is '主键Id';
COMMENT ON COLUMN sys_cust_adv_query_cond.query_id is '模板Id';
COMMENT ON COLUMN sys_cust_adv_query_cond.col_name is '列名';
COMMENT ON COLUMN sys_cust_adv_query_cond.col_desc is '列描述';
COMMENT ON COLUMN sys_cust_adv_query_cond.java_field is 'Java字段名';
COMMENT ON COLUMN sys_cust_adv_query_cond.java_type is 'Java字段类型';
COMMENT ON COLUMN sys_cust_adv_query_cond.dict_type is '字典类型';
COMMENT ON COLUMN sys_cust_adv_query_cond.cipher_flag is '密文标识';
COMMENT ON COLUMN sys_cust_adv_query_cond.query_flag is '查询标识';
COMMENT ON COLUMN sys_cust_adv_query_cond.query_type is '查询类型';
COMMENT ON TABLE sys_cust_adv_query_cond is '自定义高级查询条件表';

-- 添加菜单
delete from sys_menu where parent_id = (select menu_id from sys_menu where menu_name = '查询模板管理');
delete from sys_menu where menu_name = '查询模板管理';

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('查询模板管理', '1', '11', 'caq', 'system/caq/index', 1, 0, 'C', '0', '0', 'system:caq:list', 'icon', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '自定义高级查询模板菜单');

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('查询模板查询', (select menu_id from sys_menu where menu_name = '查询模板管理'), '1',  '#', '', 1, 0, 'F', '0', '0', 'system:caq:query',        '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('查询模板新增', (select menu_id from sys_menu where menu_name = '查询模板管理'), '2',  '#', '', 1, 0, 'F', '0', '0', 'system:caq:add',          '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('查询模板修改', (select menu_id from sys_menu where menu_name = '查询模板管理'), '3',  '#', '', 1, 0, 'F', '0', '0', 'system:caq:edit',         '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('查询模板删除', (select menu_id from sys_menu where menu_name = '查询模板管理'), '4',  '#', '', 1, 0, 'F', '0', '0', 'system:caq:remove',       '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');


