drop table if exists t_demo;
create table t_demo (
  demo_id bigint not null auto_increment comment '业务主键ID',
  demo_name varchar(30) default '' comment '业务名称',
  demo_phone varchar(64) default '' comment '业务手机',
  demo_phone_search varchar(4) default '' comment '业务手机后四位',
  demo_time datetime comment '业务时间',
  demo_image varchar(2048) comment '业务图片',
  attachment varchar(2048) comment '业务附件',
  demo_content text comment '业务内容',
  del_flag tinyint default 0 comment '删除标识 0 未删除 1 已删除',
  role_id bigint comment '角色ID',
  post_id bigint default 1 comment '岗位ID',
  create_by varchar(64) default '' comment '创建者',
  create_time datetime comment '创建时间',
  update_by varchar(64) default '' comment '更新者',
  update_time datetime comment '更新时间',
  primary key (demo_id)
) engine=innodb comment = '业务表';


-- 菜单 SQL
DELETE FROM sys_menu WHERE parent_id = 4;

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES
('业务', '4', '1', 'demo', 'biz/demo/index', 1, 0, 'C', '0', '0', 'biz:demo:list', '#', 'superadmin', sysdate(), '', null, '业务菜单'),
('业务部门树', '4', '2', 'demodept', 'biz/demo/index_dept', 1, 0, 'C', '0', '0', 'biz:demo:list', '#', 'superadmin', sysdate(), '', null, '业务菜单');

-- alter table sys_user add column py_name varchar(200) null default '' COMMENT '用户名拼音' after password;
-- alter table sys_user add column first_spell_name varchar(16) null default '' COMMENT '用户名拼音首字母' after py_name;