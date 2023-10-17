-- ----------------------------
-- 1、移动菜单权限表
-- ----------------------------
drop table if exists sys_mobile_menu;
create table sys_mobile_menu (
  menu_id           	bigint      	not null auto_increment    	comment '菜单ID',
  menu_name         	varchar(50)     not null                   	comment '菜单名称',
  menu_type     	    varchar(23)                        			comment '菜单种类',
  parent_id         	bigint      	default 0                  	comment '父菜单ID',
  order_num         	int          	default 0                  	comment '显示顺序',
  page_path         	varchar(200)    default ''                  comment '菜单地址',
  icon_path         	varchar(200)    default ''                  comment '初始图标地址',
  selected_icon_path    varchar(200)    default ''         			comment '选中图标地址',
  query_param           varchar(255)    default null                comment '路由参数',
  icon_type         	varchar(64)     default ''                  comment '菜单Icon',
  icon_size         	varchar(24)     default '30'                comment '菜单Icon尺寸',
  view_class			varchar(64)     default ''                  comment '视图样式',
  img_src				varchar(64)     default ''                  comment '图片src',
  is_frame          	tinyint         default 1                   comment '是否为外链（0是 1否）',
  status           		tinyint         default '0'                 comment '菜单状态（0显示 1隐藏）',
  perms             	varchar(100)    default null               	comment '权限标识',
  create_by         	varchar(64)     default ''                 	comment '创建者',
  create_time       	datetime                                   	comment '创建时间',
  update_by         	varchar(64)     default ''                 	comment '更新者',
  update_time       	datetime                                   	comment '更新时间',
  remark            	varchar(500)    default ''                 	comment '备注',
  primary key (menu_id)
) engine=innodb comment = '移动菜单权限表';

-- ----------------------------
-- 2、角色和移动菜单关联表  角色1-N移动菜单
-- ----------------------------
drop table if exists sys_role_mobile_menu;
create table sys_role_mobile_menu (
  role_id   bigint not null comment '角色ID',
  menu_id   bigint not null comment '菜单ID',
  primary key(role_id, menu_id)
) engine=innodb comment = '角色和移动菜单关联表';



-- 添加菜单
select @root_id := menu_id from sys_menu where menu_name = '移动端管理';
select @menu_id := menu_id from sys_menu where menu_name = '移动菜单';

delete from sys_menu where parent_id = @menu_id;
delete from sys_menu where parent_id = @root_id;
delete from sys_menu where menu_id = @root_id;


insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('移动端管理', 0, 130, 'mobile', null, 1, 0, 'M', '0', '0', null, 'phone', 'superadmin', sysdate(), '', null, '移动端管理目录');


SELECT @parentId := LAST_INSERT_ID();

-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('移动菜单', @parentId, '1', 'mbmenu', 'system/mobile/menu/index', 1, 0, 'C', '0', '0', 'system:mobile:menu:list', 'tree-table', 'superadmin', sysdate(), '', null, '移动菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('移动菜单查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'system:mobile:menu:query',        '#', 'superadmin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('移动菜单新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'system:mobile:menu:add',          '#', 'superadmin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('移动菜单修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'system:mobile:menu:edit',         '#', 'superadmin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('移动菜单删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'system:mobile:menu:remove',       '#', 'superadmin', sysdate(), '', null, '');
