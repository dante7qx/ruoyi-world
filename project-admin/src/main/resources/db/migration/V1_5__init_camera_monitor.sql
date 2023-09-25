-- ----------------------------
-- 视频监控表
-- ----------------------------
drop table if exists sys_camera_monitor;
create table sys_camera_monitor ( 
  monitor_id       	bigserial       not null 	primary key,
  parent_id         bigint      	default 0,
  ancestors         varchar(50)     default '',
  monitor_name		varchar(64)     not null     default '',
  order_num         int          	default 0,
  rtsp_url			varchar(1024)   not null     default '',
  play_uri			varchar(1024)   default '',
  remark			varchar(512)    default '',
  create_by         varchar(64)     default '',
  create_time 	    timestamp,
  update_by         varchar(64)     default '',
  update_time       timestamp
);

COMMENT ON COLUMN sys_camera_monitor.monitor_id is '监控id';
COMMENT ON COLUMN sys_camera_monitor.monitor_id is '父id';
COMMENT ON COLUMN sys_camera_monitor.monitor_id is '祖级列表';
COMMENT ON COLUMN sys_camera_monitor.monitor_id is '监控名称';
COMMENT ON COLUMN sys_camera_monitor.monitor_id is '显示顺序';
COMMENT ON COLUMN sys_camera_monitor.monitor_id is 'RTSP地址';
COMMENT ON COLUMN sys_camera_monitor.monitor_id is '播放地址';
COMMENT ON COLUMN sys_camera_monitor.monitor_id is '备注';
COMMENT ON COLUMN sys_camera_monitor.monitor_id is '创建者';
COMMENT ON COLUMN sys_camera_monitor.monitor_id is '创建时间';
COMMENT ON COLUMN sys_camera_monitor.monitor_id is '更新者';
COMMENT ON COLUMN sys_camera_monitor.monitor_id is '更新时间';
COMMENT ON TABLE t_demo is '视频监控表';


-- 菜单相关
delete from sys_menu where parent_id = (select menu_id from sys_menu where menu_name = '视频监控');
delete from sys_menu where menu_name = '视频监控';
delete from sys_role_menu where menu_id = (select menu_id from sys_menu where menu_name = '视频监控');
delete from sys_role_menu where menu_id in (select menu_id from sys_menu where parent_id = (select menu_id from sys_menu where menu_name = '视频监控'));

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('视频监控', '2', '7', 'camera', 'monitor/camera/index', 1, 0, 'C', '0', '0', 'monitor:camera:list', 'eye-open', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '视频监控菜单');


insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('视频监控查询', (select menu_id from sys_menu where menu_name = '视频监控'), '1',  '#', '', 1, 0, 'F', '0', '0', 'monitor:camera:query',        '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('视频监控新增', (select menu_id from sys_menu where menu_name = '视频监控'), '2',  '#', '', 1, 0, 'F', '0', '0', 'monitor:camera:add',          '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('视频监控修改', (select menu_id from sys_menu where menu_name = '视频监控'), '3',  '#', '', 1, 0, 'F', '0', '0', 'monitor:camera:edit',         '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('视频监控删除', (select menu_id from sys_menu where menu_name = '视频监控'), '4',  '#', '', 1, 0, 'F', '0', '0', 'monitor:camera:remove',       '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('视频监控导出', (select menu_id from sys_menu where menu_name = '视频监控'), '5',  '#', '', 1, 0, 'F', '0', '0', 'monitor:camera:export',       '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');

-- 角色菜单
insert into sys_role_menu values (2, (select menu_id from sys_menu where menu_name = '视频监控'));
insert into sys_role_menu select 2, menu_id from sys_menu where parent_id = (select menu_id from sys_menu where menu_name = '视频监控');


-- 参数配置
delete from sys_config where config_id = 8;
insert into sys_config values(8, '视频监控服务器',         	   'sys.monitor.camera.server',     '127.0.0.1:3000',      '1', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '视频监控服务器地址(IP:PORT)' );
