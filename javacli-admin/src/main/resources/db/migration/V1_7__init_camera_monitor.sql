-- ----------------------------
-- 27、视频监控表
-- ----------------------------
drop table if exists sys_camera_monitor;
create table sys_camera_monitor ( 
  monitor_id       	bigint     		not null 	auto_increment   comment '监控id',
  parent_id         bigint      	default 0                 	 comment '父id',
  ancestors         varchar(50)     default ''                   comment '祖级列表',
  monitor_name		varchar(64)     not null     default ''      comment '监控名称',
  order_num         int          	default 0                    comment '显示顺序',
  rtsp_url			varchar(1024)   not null     default ''      comment 'RTSP地址',
  play_uri			varchar(1024)   default ''                   comment '播放地址',
  remark			varchar(512)    default ''				     comment '备注',
  create_by         varchar(64)     default ''                	 comment '创建者',
  create_time 	    datetime                                     comment '创建时间',
  update_by         varchar(64)     default ''                   comment '更新者',
  update_time       datetime                                     comment '更新时间',
  primary key (monitor_id)
) engine=innodb comment = '视频监控表';

select @menu_id := menu_id from sys_menu where menu_name = '视频监控';

delete from sys_menu where parent_id = @menu_id;
delete from sys_menu where menu_id = @menu_id;
delete from sys_role_menu where menu_id = @menu_id;
delete from sys_role_menu where menu_id in (select menu_id from sys_menu where parent_id = @menu_id);

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('视频监控', '2', '7', 'camera', 'monitor/camera/index', 1, 0, 'C', '0', '0', 'monitor:camera:list', 'eye-open', 'superadmin', sysdate(), '', null, '视频监控菜单');

SELECT @parentId := LAST_INSERT_ID();

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('视频监控查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'monitor:camera:query',        '#', 'superadmin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('视频监控新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'monitor:camera:add',          '#', 'superadmin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('视频监控修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'monitor:camera:edit',         '#', 'superadmin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('视频监控删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'monitor:camera:remove',       '#', 'superadmin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('视频监控导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'monitor:camera:export',       '#', 'superadmin', sysdate(), '', null, '');

-- 角色菜单
select @newId := menu_id from sys_menu where menu_name = '视频监控';
insert into sys_role_menu values (2, @newId);
insert into sys_role_menu select 2, menu_id from sys_menu where parent_id = @newId;


-- 参数配置
delete from sys_config where config_id = 8;
insert into sys_config values(8, '视频监控服务器',         	   'sys.monitor.camera.server',     '127.0.0.1:3000',      '1', 'superadmin', sysdate(), '', null, '视频监控服务器地址(IP:PORT)' );