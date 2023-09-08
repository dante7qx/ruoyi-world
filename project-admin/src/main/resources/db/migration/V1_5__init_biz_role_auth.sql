drop table if exists t_demo_all;
create table t_demo_all (
  demo_id bigint not null auto_increment comment '业务主键ID',
  demo_name varchar(30) default '' comment '业务名称',
  dept_id bigint not null comment '部门ID',
  create_by varchar(64) default '' comment '创建者',
  create_time datetime comment '创建时间',
  update_by varchar(64) default '' comment '更新者',
  update_time datetime comment '更新时间',
  primary key (demo_id)
) engine=innodb comment = '全部业务功能';

drop table if exists t_demo_self;
create table t_demo_self (
  demo_id bigint not null auto_increment comment '业务主键ID',
  demo_name varchar(30) default '' comment '业务名称',
  dept_id bigint not null comment '部门ID',
  create_by varchar(64) default '' comment '创建者',
  create_time datetime comment '创建时间',
  update_by varchar(64) default '' comment '更新者',
  update_time datetime comment '更新时间',
  primary key (demo_id)
) engine=innodb comment = '私有业务功能';


-- 菜单 SQL
select @allId := (select menu_id from sys_menu where menu_name = '全部业务功能');
select @selfId := (select menu_id from sys_menu where menu_name = '私有业务功能');

delete from sys_menu where parent_id = @allId;
delete from sys_menu where parent_id = @selfId;

delete from sys_menu where menu_id = @allId;
delete from sys_menu where menu_id = @selfId;

-- 全部业务功能
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('全部业务功能', '4', '3', 'demoall', 'biz/demo/all/index', 1, 0, 'C', '0', '0', 'biz:demoall:list', '#', 'fqyczadmin', sysdate(), '', null, '全部业务功能菜单');

SELECT @parentId1 := LAST_INSERT_ID();

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('全部业务功能查询', @parentId1, '1',  '#', '', 1, 0, 'F', '0', '0', 'biz:demoall:query',        '#', 'fqyczadmin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('全部业务功能新增', @parentId1, '2',  '#', '', 1, 0, 'F', '0', '0', 'biz:demoall:add',          '#', 'fqyczadmin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('全部业务功能修改', @parentId1, '3',  '#', '', 1, 0, 'F', '0', '0', 'biz:demoall:edit',         '#', 'fqyczadmin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('全部业务功能删除', @parentId1, '4',  '#', '', 1, 0, 'F', '0', '0', 'biz:demoall:remove',       '#', 'fqyczadmin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('全部业务功能导入', @parentId1, '5',  '#', '', 1, 0, 'F', '0', '0', 'biz:demoall:import',       '#', 'fqyczadmin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('全部业务功能导出', @parentId1, '6',  '#', '', 1, 0, 'F', '0', '0', 'biz:demoall:export',       '#', 'fqyczadmin', sysdate(), '', null, '');

-- 私有业务功能
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('私有业务功能', '4', '4', 'demoself', 'biz/demo/self/index', 1, 0, 'C', '0', '0', 'biz:demoself:list', '#', 'fqyczadmin', sysdate(), '', null, '私有业务功能菜单');

SELECT @parentId2 := LAST_INSERT_ID();

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('私有业务功能查询', @parentId2, '1',  '#', '', 1, 0, 'F', '0', '0', 'biz:demoself:query',        '#', 'fqyczadmin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('私有业务功能新增', @parentId2, '2',  '#', '', 1, 0, 'F', '0', '0', 'biz:demoself:add',          '#', 'fqyczadmin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('私有业务功能修改', @parentId2, '3',  '#', '', 1, 0, 'F', '0', '0', 'biz:demoself:edit',         '#', 'fqyczadmin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('私有业务功能删除', @parentId2, '4',  '#', '', 1, 0, 'F', '0', '0', 'biz:demoself:remove',       '#', 'fqyczadmin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('私有业务功能导入', @parentId2, '5',  '#', '', 1, 0, 'F', '0', '0', 'biz:demoself:import',       '#', 'fqyczadmin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('私有业务功能导出', @parentId2, '6',  '#', '', 1, 0, 'F', '0', '0', 'biz:demoself:export',       '#', 'fqyczadmin', sysdate(), '', null, '');