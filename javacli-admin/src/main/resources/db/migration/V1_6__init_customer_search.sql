
-- 自定义高级查询模板表
drop table if exists sys_cust_adv_query;
create table sys_cust_adv_query (
  query_id bigint not null auto_increment comment '主键Id',
  template_name varchar(64) not null default '' comment '模板名称',
  table_name varchar(128) not null default '' comment '数据库表名',
  table_desc varchar(128) default '' comment '数据库表描述',
  table_alias varchar(20) not null default '' comment '数据库表别名',
  pk_col varchar(64) not null default '' comment '数据库表主键列名',
  create_by varchar(64) default '' comment '创建者',
  create_time datetime comment '创建时间',
  update_by varchar(64) default '' comment '更新者',
  update_time datetime comment '更新时间',
  primary key (query_id)
) engine=innodb comment = '自定义高级查询模板表';


-- 自定义高级查询条件表
drop table if exists sys_cust_adv_query_cond;
create table sys_cust_adv_query_cond (
  cond_id bigint not null auto_increment comment '主键Id',
  query_id bigint not null comment '模板Id',
  col_name varchar(128) not null default '' comment '列名',
  col_desc varchar(128) not null default '' comment '列描述',
  java_field varchar(64) not null default '' comment 'Java字段名',
  java_type varchar(64) not null default '' comment 'Java字段类型',
  dict_type varchar(64) default '' comment '字典类型',
  query_flag tinyint not null default 0 comment '查询标识',
  query_type varchar(16) not null default '' comment '查询类型',
  primary key (cond_id)
) engine=innodb comment = '自定义高级查询条件表';

-- 添加菜单
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('查询模板管理', '1', '11', 'caq', 'system/caq/index', 1, 0, 'C', '0', '0', 'system:caq:list', 'icon', 'superadmin', sysdate(), '', null, '自定义高级查询模板菜单');

SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('查询模板查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'system:caq:query',        '#', 'superadmin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('查询模板新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'system:caq:add',          '#', 'superadmin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('查询模板修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'system:caq:edit',         '#', 'superadmin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('查询模板删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'system:caq:remove',       '#', 'superadmin', sysdate(), '', null, '');


