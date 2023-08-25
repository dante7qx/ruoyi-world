drop table if exists t_demo;
create table t_demo (
  demo_id bigserial not null primary key,
  demo_name varchar(30) default '',
  demo_time date,
  demo_image varchar(2048),
  attachment varchar(2048),
  demo_content text,
  role_id bigint,
  post_id bigint default 1,
  del_flag smallint default 0,
  create_by varchar(64) default '',
  create_time timestamp,
  update_by varchar(64) default '',
  update_time timestamp
);

COMMENT ON COLUMN t_demo.demo_id is '业务主键ID';
COMMENT ON COLUMN t_demo.demo_name is '业务名称';
COMMENT ON COLUMN t_demo.demo_time is '业务时间';
COMMENT ON COLUMN t_demo.demo_image is '业务图片';
COMMENT ON COLUMN t_demo.attachment is '业务附件';
COMMENT ON COLUMN t_demo.demo_content is '业务内容';
COMMENT ON COLUMN t_demo.role_id is '角色ID';
COMMENT ON COLUMN t_demo.post_id is '岗位ID';
COMMENT ON COLUMN t_demo.del_flag is '删除标识 0 未删除 1 已删除';
COMMENT ON COLUMN t_demo.create_by is '创建者';
COMMENT ON COLUMN t_demo.create_time is '创建时间';
COMMENT ON COLUMN t_demo.update_by is '更新者';
COMMENT ON COLUMN t_demo.update_time is '更新时间';
COMMENT ON TABLE t_demo is '业务表';

-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values
('业务', 4, 1, 'demo', 'biz/demo/index', 1, 0, 'C', '0', '0', 'biz:demo:list', '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '业务菜单'),
('业务部门树', 4, 2, 'demodept', 'biz/demo/index_dept', 1, 0, 'C', '0', '0', 'biz:demo:list', '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '业务菜单');
