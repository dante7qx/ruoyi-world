-- ----------------------------
-- 1、部门表
-- ----------------------------
drop table if exists sys_dept;
create table sys_dept (
  dept_id           bigserial      not null primary key,
  parent_id         bigint      default 0,
  ancestors         varchar(50)     default '',
  dept_name         varchar(30)     default '',
  order_num         integer          default 0,
  leader            varchar(20)     default null,
  phone             varchar(11)     default null,
  email             varchar(50)     default null,
  status            char(1)         default '0',
  del_flag          char(1)         default '0',
  dept_key          varchar(30)     default '',
  create_by         varchar(64)     default '',
  create_time 	    timestamp,
  update_by         varchar(64)     default '',
  update_time       timestamp
);
COMMENT ON COLUMN sys_dept.dept_id IS '部门id';
COMMENT ON COLUMN sys_dept.parent_id IS '父部门id';
COMMENT ON COLUMN sys_dept.ancestors IS '祖级列表';
COMMENT ON COLUMN sys_dept.dept_name IS '部门名称';
COMMENT ON COLUMN sys_dept.order_num IS '显示顺序';
COMMENT ON COLUMN sys_dept.leader IS '负责人';
COMMENT ON COLUMN sys_dept.phone IS '联系电话';
COMMENT ON COLUMN sys_dept.email IS '邮箱';
COMMENT ON COLUMN sys_dept.status IS '部门状态（0正常 1停用）';
COMMENT ON COLUMN sys_dept.del_flag IS '删除标志（0代表存在 2代表删除）';
COMMENT ON COLUMN sys_dept.dept_key IS '部门标识';
COMMENT ON COLUMN sys_dept.create_by IS '创建者';
COMMENT ON COLUMN sys_dept.create_time IS '创建时间';
COMMENT ON COLUMN sys_dept.update_by IS '更新者';
COMMENT ON COLUMN sys_dept.update_time IS '更新时间';
COMMENT ON TABLE sys_dept IS '部门表';

-- ----------------------------
-- 初始化-部门表数据
-- ----------------------------
insert into sys_dept values(nextval('sys_dept_dept_id_seq'),  0,   '0',          '睿阳科技',   0, '系统管理员', '15888888888', 'admin@admin.com', '0', '0', '', 'fqyczadmin', CURRENT_TIMESTAMP, '', null);

-- ----------------------------
-- 2、用户信息表
-- ----------------------------
drop table if exists sys_user;
create table sys_user (
  user_id           bigserial      not null primary key,
  dept_id           bigint      default null,
  user_name         varchar(30)     not null,
  nick_name         varchar(30)     not null,
  user_type         varchar(2)      default '00',
  email             varchar(50)     default '',
  phonenumber       varchar(11)     default '',
  sex               char(1)         default '0',
  avatar            varchar(100)    default '',
  password          varchar(400)    default '',
  py_name	        varchar(200)    default '',
  first_spell_name	varchar(16)     default '',
  status            char(1)         default '0',
  del_flag          char(1)         default '0',
  login_ip          varchar(128)    default '',
  login_date        timestamp,
  create_by         varchar(64)     default '',
  create_time       timestamp,
  update_by         varchar(64)     default '',
  update_time       timestamp,
  remark            varchar(500)    default null
);
COMMENT ON COLUMN sys_user.user_id IS '用户ID';
COMMENT ON COLUMN sys_user.dept_id IS '部门ID';
COMMENT ON COLUMN sys_user.user_name IS '用户账号';
COMMENT ON COLUMN sys_user.nick_name IS '用户昵称';
COMMENT ON COLUMN sys_user.user_type IS '用户类型（00系统用户）';
COMMENT ON COLUMN sys_user.email IS '用户邮箱';
COMMENT ON COLUMN sys_user.phonenumber IS '手机号码';
COMMENT ON COLUMN sys_user.sex IS '用户性别（0男 1女 2未知）';
COMMENT ON COLUMN sys_user.avatar IS '头像地址';
COMMENT ON COLUMN sys_user.password IS '密码';
COMMENT ON COLUMN sys_user.py_name IS '用户名拼音';
COMMENT ON COLUMN sys_user.first_spell_name IS '用户名拼音首字母';
COMMENT ON COLUMN sys_user.status IS '帐号状态（0正常 1停用）';
COMMENT ON COLUMN sys_user.del_flag IS '删除标志（0代表存在 2代表删除）';
COMMENT ON COLUMN sys_user.login_ip IS '最后登录IP';
COMMENT ON COLUMN sys_user.login_date IS '最后登录时间';
COMMENT ON COLUMN sys_user.create_by IS '创建者';
COMMENT ON COLUMN sys_user.create_time IS '创建时间';
COMMENT ON COLUMN sys_user.update_by IS '更新者';
COMMENT ON COLUMN sys_user.update_time IS '更新时间';
COMMENT ON COLUMN sys_user.remark IS '备注';
COMMENT ON TABLE sys_user IS '用户信息表';

-- ----------------------------
-- 初始化-用户信息表数据
-- ----------------------------
insert into sys_user values(nextval('sys_user_user_id_seq'),  (select dept_id from sys_dept where dept_name = '睿阳科技'), 'fqyczadmin', '系统管理员', '00', 'fqyczadmin@admin.com', '15888888888', '1', '', 'MLSXAYsCSDZiZRqNDy4S0QMPqnpJ3K+rZZEVcl+xNHNyHdggEdwomfc0hGlL5QvcsxXxA1VJigcVqEM5wZ3XbMkYw0HIwaytIjXtV2VFYgE1IWsYw8QdUnNeJn9aySBoeIl0ojGj3qS7XULnvBUm6/w1IYw4t31PFuAPc9XQAEMdvw0mH9fEEWGXhWIOff0ZE/dowNNjFJrn4/CMsxy0GPIm/WqRaRR+dCliZYjg9z4TE3c4EYi75/DIdfHGhAdL/60CVijKrsxXeV/61D+qpK30jYE79kyRiI+er8y1ryxBFyKxgC7Obi3yrnH/T5fWHkGJT2tDF3eNvTFN4ZQbxQ==', 'xitongguanliyuan', 'XTGLY', '0', '0', '127.0.0.1', CURRENT_TIMESTAMP, 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '管理员');

-- ----------------------------
-- 用户密码修改记录表
-- ----------------------------
drop table if exists sys_user_pwd_modify;
create table sys_user_pwd_modify (
  user_id           bigint      not null primary key,
  modify_by         varchar(64)     default '',
  modify_time       timestamp
);
COMMENT ON COLUMN sys_user_pwd_modify.user_id IS '用户ID';
COMMENT ON COLUMN sys_user_pwd_modify.modify_by IS '更新者';
COMMENT ON COLUMN sys_user_pwd_modify.modify_time IS '更新时间';
COMMENT ON TABLE  sys_user_pwd_modify IS '用户密码修改记录表';


-- ----------------------------
-- 3、岗位信息表
-- ----------------------------
drop table if exists sys_post;
create table sys_post
(
  post_id       bigserial      not null primary key,
  post_code     varchar(64)     not null,
  post_name     varchar(50)     not null,
  post_sort     integer          not null,
  status        char(1)         not null,
  create_by     varchar(64)     default '',
  create_time   timestamp ,
  update_by     varchar(64)     default '',
  update_time   timestamp,
  remark        varchar(500)    default null
);
COMMENT ON COLUMN sys_post.post_id IS '岗位ID';
COMMENT ON COLUMN sys_post.post_code IS '岗位编码';
COMMENT ON COLUMN sys_post.post_name IS '岗位名称';
COMMENT ON COLUMN sys_post.post_sort IS '显示顺序';
COMMENT ON COLUMN sys_post.status IS '状态（0正常 1停用）';
COMMENT ON COLUMN sys_post.create_by IS '创建者';
COMMENT ON COLUMN sys_post.create_time IS '创建时间';
COMMENT ON COLUMN sys_post.update_by IS '更新者';
COMMENT ON COLUMN sys_post.update_time IS '更新时间';
COMMENT ON COLUMN sys_post.remark IS '备注';
COMMENT ON TABLE sys_post IS '岗位信息表';

-- ----------------------------
-- 初始化-岗位信息表数据
-- ----------------------------
insert into sys_post values(nextval('sys_post_post_id_seq'), 'ceo',  '总负责人',    1, '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');

-- ----------------------------
-- 4、角色信息表
-- ----------------------------
drop table if exists sys_role;
create table sys_role (
  role_id              bigserial      not null primary key,
  role_name            varchar(30)     not null,
  role_key             varchar(100)    not null,
  role_sort            integer         not null,
  data_scope           char(1)         default '1',
  menu_check_strictly  smallint        default 1,
  dept_check_strictly  smallint        default 1,
  status               char(1)         not null,
  del_flag             char(1)         default '0',
  create_by            varchar(64)     default '',
  create_time          timestamp,
  update_by            varchar(64)     default '',
  update_time          timestamp,
  remark               varchar(500)    default null
);
COMMENT ON COLUMN sys_role.role_id IS '角色ID';
COMMENT ON COLUMN sys_role.role_name IS '角色名称';
COMMENT ON COLUMN sys_role.role_key IS '角色权限字符串';
COMMENT ON COLUMN sys_role.role_sort IS '显示顺序';
COMMENT ON COLUMN sys_role.data_scope IS '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）';
COMMENT ON COLUMN sys_role.menu_check_strictly IS '菜单树选择项是否关联显示';
COMMENT ON COLUMN sys_role.dept_check_strictly IS '部门树选择项是否关联显示';
COMMENT ON COLUMN sys_role.status IS '角色状态（0正常 1停用）';
COMMENT ON COLUMN sys_role.del_flag IS '删除标志（0代表存在 2代表删除）';
COMMENT ON COLUMN sys_role.create_by IS '创建者';
COMMENT ON COLUMN sys_role.create_time IS '创建时间';
COMMENT ON COLUMN sys_role.update_by IS '更新者';
COMMENT ON COLUMN sys_role.update_time IS '更新时间';
COMMENT ON COLUMN sys_role.remark IS '备注';
COMMENT ON TABLE sys_role IS '角色信息表';

-- ----------------------------
-- 初始化-角色信息表数据
-- ----------------------------
insert into sys_role values(nextval('sys_role_role_id_seq'), '超级管理员',  'admin',  1, 1, 1, 1, '0', '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '超级管理员');
insert into sys_role values(nextval('sys_role_role_id_seq'), '普通角色',    'common', 2, 2, 1, 1, '0', '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '普通角色');
insert into sys_role values(nextval('sys_role_role_id_seq'), '信息录入员',  'info_record', 3, 2, 0, 0, '0', '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '信息录入员');
insert into sys_role values(nextval('sys_role_role_id_seq'), '信息管理员',  'info_mgr', 4, 2, 0, 0, '0', '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '信息管理员');

-- ----------------------------
-- 5、菜单权限表
-- ----------------------------
drop table if exists sys_menu;
create table sys_menu (
  menu_id           bigserial      not null primary key,
  menu_name         varchar(50)     not null,
  parent_id         bigint      	default 0,
  order_num         integer          default 0,
  path              varchar(200)    default '',
  component         varchar(255)    default null,
  query             varchar(255)    default null,
  is_frame          integer          default 1,
  is_cache          integer          default 0,
  menu_type         char(1)         default '',
  visible           char(1)         default 0,
  status            char(1)         default 0,
  perms             varchar(100)    default null,
  icon              varchar(100)    default '#',
  create_by         varchar(64)     default '',
  create_time       timestamp,
  update_by         varchar(64)     default '',
  update_time       timestamp,
  remark            varchar(500)    default ''
);
COMMENT ON COLUMN sys_menu.menu_id IS '菜单ID';
COMMENT ON COLUMN sys_menu.menu_name IS '菜单名称';
COMMENT ON COLUMN sys_menu.parent_id IS '父菜单ID';
COMMENT ON COLUMN sys_menu.order_num IS '显示顺序';
COMMENT ON COLUMN sys_menu.path IS '路由地址';
COMMENT ON COLUMN sys_menu.component IS '组件路径';
COMMENT ON COLUMN sys_menu.query IS '路由参数';
COMMENT ON COLUMN sys_menu.is_frame IS '是否为外链（0是 1否）';
COMMENT ON COLUMN sys_menu.is_cache IS '是否缓存（0缓存 1不缓存）';
COMMENT ON COLUMN sys_menu.menu_type IS '菜单类型（M目录 C菜单 F按钮）';
COMMENT ON COLUMN sys_menu.visible IS '菜单状态（0显示 1隐藏）';
COMMENT ON COLUMN sys_menu.status IS '菜单状态（0正常 1停用）';
COMMENT ON COLUMN sys_menu.perms IS '权限标识';
COMMENT ON COLUMN sys_menu.icon IS '菜单图标';
COMMENT ON COLUMN sys_menu.create_by IS '创建者';
COMMENT ON COLUMN sys_menu.create_time IS '创建时间';
COMMENT ON COLUMN sys_menu.update_by IS '更新者';
COMMENT ON COLUMN sys_menu.update_time IS '更新时间';
COMMENT ON COLUMN sys_menu.remark IS '备注';
COMMENT ON TABLE sys_menu IS '菜单权限表';

-- ----------------------------
-- 初始化-菜单信息表数据
-- ----------------------------
-- 一级菜单
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '系统管理', '0', '100', 'system',           null, '', 1, 0, 'M', '0', '0', '', 'system',   'fqyczadmin', CURRENT_TIMESTAMP, '', null, '系统管理目录');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '系统监控', '0', '110', 'monitor',          null, '', 1, 0, 'M', '0', '0', '', 'monitor',  'fqyczadmin', CURRENT_TIMESTAMP, '', null, '系统监控目录');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '系统工具', '0', '120', 'tool',             null, '', 1, 0, 'M', '0', '0', '', 'tool',     'fqyczadmin', CURRENT_TIMESTAMP, '', null, '系统工具目录');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '业务逻辑', '0', '10', 'biz',               null, '', 1, 0, 'M', '0', '0', '', 'list',   'fqyczadmin', CURRENT_TIMESTAMP, '', null, '业务逻辑目录');
-- 二级菜单
insert into sys_menu values(nextval('sys_menu_menu_id_seq'),  '用户管理', (select menu_id from sys_menu where menu_name = '系统管理'),   '1', 'user',       'system/user/index',        '', 1, 0, 'C', '0', '0', 'system:user:list',        'user',          'fqyczadmin', CURRENT_TIMESTAMP, '', null, '用户管理菜单');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'),  '角色管理', (select menu_id from sys_menu where menu_name = '系统管理'),   '2', 'role',       'system/role/index',        '', 1, 0, 'C', '0', '0', 'system:role:list',        'peoples',       'fqyczadmin', CURRENT_TIMESTAMP, '', null, '角色管理菜单');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'),  '菜单管理', (select menu_id from sys_menu where menu_name = '系统管理'),   '3', 'menu',       'system/menu/index',        '', 1, 0, 'C', '0', '0', 'system:menu:list',        'tree-table',    'fqyczadmin', CURRENT_TIMESTAMP, '', null, '菜单管理菜单');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'),  '部门管理', (select menu_id from sys_menu where menu_name = '系统管理'),   '4', 'dept',       'system/dept/index',        '', 1, 0, 'C', '0', '0', 'system:dept:list',        'tree',          'fqyczadmin', CURRENT_TIMESTAMP, '', null, '部门管理菜单');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'),  '岗位管理', (select menu_id from sys_menu where menu_name = '系统管理'),   '5', 'post',       'system/post/index',        '', 1, 0, 'C', '0', '0', 'system:post:list',        'post',          'fqyczadmin', CURRENT_TIMESTAMP, '', null, '岗位管理菜单');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'),  '字典管理', (select menu_id from sys_menu where menu_name = '系统管理'),   '6', 'dict',       'system/dict/index',        '', 1, 0, 'C', '0', '0', 'system:dict:list',        'dict',          'fqyczadmin', CURRENT_TIMESTAMP, '', null, '字典管理菜单');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'),  '参数设置', (select menu_id from sys_menu where menu_name = '系统管理'),   '7', 'config',     'system/config/index',      '', 1, 0, 'C', '0', '0', 'system:config:list',      'edit',          'fqyczadmin', CURRENT_TIMESTAMP, '', null, '参数设置菜单');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'),  '信息管理', (select menu_id from sys_menu where menu_name = '系统管理'),   '8', 'info',		 '',                         '', 1, 0, 'M', '0', '0', '',                        'message',       'fqyczadmin', CURRENT_TIMESTAMP, '', null, '信息管理菜单');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'),  '系统附件', (select menu_id from sys_menu where menu_name = '系统管理'),   '9', 'attachment', 'system/attachment/index',  '', 1, 0, 'C', '0', '0', 'system:attachment:list',  'upload',        'fqyczadmin', CURRENT_TIMESTAMP, '', null, '系统业务附件菜单');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'),  '日志管理', (select menu_id from sys_menu where menu_name = '系统管理'),   '10', 'log',        '',                        '', 1, 0, 'M', '0', '0', '',                       'log',           'fqyczadmin', CURRENT_TIMESTAMP, '', null, '日志管理菜单');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'),  '在线用户', (select menu_id from sys_menu where menu_name = '系统监控'),   '1', 'online',     'monitor/online/index',     '', 1, 0, 'C', '0', '0', 'monitor:online:list',     'online',        'fqyczadmin', CURRENT_TIMESTAMP, '', null, '在线用户菜单');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'),  '定时任务', (select menu_id from sys_menu where menu_name = '系统监控'),   '2', 'job',        'monitor/job/index',        '', 1, 0, 'C', '0', '0', 'monitor:job:list',        'job',           'fqyczadmin', CURRENT_TIMESTAMP, '', null, '定时任务菜单');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'),  '数据监控', (select menu_id from sys_menu where menu_name = '系统监控'),   '3', 'druid',      'monitor/druid/index',      '', 1, 0, 'C', '0', '0', 'monitor:druid:list',      'druid',         'fqyczadmin', CURRENT_TIMESTAMP, '', null, '数据监控菜单');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'),  '服务监控', (select menu_id from sys_menu where menu_name = '系统监控'),   '4', 'server',     'monitor/server/index',     '', 1, 0, 'C', '0', '0', 'monitor:server:list',     'server',        'fqyczadmin', CURRENT_TIMESTAMP, '', null, '服务监控菜单');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'),  '缓存监控', (select menu_id from sys_menu where menu_name = '系统监控'),   '5', 'cache',      'monitor/cache/index',      '', 1, 0, 'C', '0', '0', 'monitor:cache:list',      'redis',         'fqyczadmin', CURRENT_TIMESTAMP, '', null, '缓存监控菜单');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'),  '缓存列表', (select menu_id from sys_menu where menu_name = '系统监控'),   '6', 'cacheList',  'monitor/cache/list',       '', 1, 0, 'C', '0', '0', 'monitor:cache:list',      'redis-list',    'fqyczadmin', CURRENT_TIMESTAMP, '', null, '缓存列表菜单');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'),  '审批记录', (select menu_id from sys_menu where menu_name = '系统监控'),   '7', 'approvalList','monitor/approval/index',  '', 1, 0, 'C', '0', '0', 'monitor:approvallog:list','time-range',    'fqyczadmin', CURRENT_TIMESTAMP, '', null, '审批记录菜单');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'),  '表单构建', (select menu_id from sys_menu where menu_name = '系统工具'),   '1', 'build',      'tool/build/index',         '', 1, 0, 'C', '0', '0', 'tool:build:list',         'build',         'fqyczadmin', CURRENT_TIMESTAMP, '', null, '表单构建菜单');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'),  '代码生成', (select menu_id from sys_menu where menu_name = '系统工具'),   '2', 'gen',        'tool/gen/index',           '', 1, 0, 'C', '0', '0', 'tool:gen:list',           'code',          'fqyczadmin', CURRENT_TIMESTAMP, '', null, '代码生成菜单');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'),  '代码示例', (select menu_id from sys_menu where menu_name = '系统工具'),   '4', 'codeexample','tool/example/index',       '', 1, 0, 'C', '0', '0', '',                        'code',          'fqyczadmin', CURRENT_TIMESTAMP, '', null, '代码示例菜单');
-- 三级菜单
insert into sys_menu values(nextval('sys_menu_menu_id_seq'),  '信息发布', (select menu_id from sys_menu where menu_name = '信息管理'), '1', 'infomgr',    'system/info/index',        '', 1, 0, 'C', '0', '0', 'system:info:list',        'message',       'fqyczadmin', CURRENT_TIMESTAMP, '', null, '信息发布菜单');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'),  '信息浏览', (select menu_id from sys_menu where menu_name = '信息管理'), '2', 'infoview',   'system/info/view',        '', 1, 0, 'C', '0', '0', 'system:info:list',         'eye-open',      'fqyczadmin', CURRENT_TIMESTAMP, '', null, '信息浏览菜单');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'),  '操作日志', (select menu_id from sys_menu where menu_name = '日志管理'), '1', 'operlog',    'monitor/operlog/index',    '', 1, 0, 'C', '0', '0', 'monitor:operlog:list',    'form',          'fqyczadmin', CURRENT_TIMESTAMP, '', null, '操作日志菜单');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'),  '登录日志', (select menu_id from sys_menu where menu_name = '日志管理'), '2', 'logininfor', 'monitor/logininfor/index', '', 1, 0, 'C', '0', '0', 'monitor:logininfor:list', 'logininfor',    'fqyczadmin', CURRENT_TIMESTAMP, '', null, '登录日志菜单');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'),  '邮件日志', (select menu_id from sys_menu where menu_name = '日志管理'), '3', 'emaillog',   'monitor/emaillog/index',   '', 1, 0, 'C', '0', '0', 'monitor:emaillog:list',   'email',         'fqyczadmin', CURRENT_TIMESTAMP, '', null, '邮件日志菜单');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'),  '短信日志', (select menu_id from sys_menu where menu_name = '日志管理'), '4', 'smslog',     'monitor/smslog/index',     '', 1, 0, 'C', '0', '0', 'monitor:smslog:list',     'message',       'fqyczadmin', CURRENT_TIMESTAMP, '', null, '短信日志菜单');
-- 用户管理按钮
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '用户查询', (select menu_id from sys_menu where menu_name = '用户管理'), '1',  '', '', '', 1, 0, 'F', '0', '0', 'system:user:query',          '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '用户新增', (select menu_id from sys_menu where menu_name = '用户管理'), '2',  '', '', '', 1, 0, 'F', '0', '0', 'system:user:add',            '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '用户修改', (select menu_id from sys_menu where menu_name = '用户管理'), '3',  '', '', '', 1, 0, 'F', '0', '0', 'system:user:edit',           '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '用户删除', (select menu_id from sys_menu where menu_name = '用户管理'), '4',  '', '', '', 1, 0, 'F', '0', '0', 'system:user:remove',         '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '用户导出', (select menu_id from sys_menu where menu_name = '用户管理'), '5',  '', '', '', 1, 0, 'F', '0', '0', 'system:user:export',         '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '用户导入', (select menu_id from sys_menu where menu_name = '用户管理'), '6',  '', '', '', 1, 0, 'F', '0', '0', 'system:user:import',         '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '重置密码', (select menu_id from sys_menu where menu_name = '用户管理'), '7',  '', '', '', 1, 0, 'F', '0', '0', 'system:user:resetPwd',       '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
-- 角色管理按钮
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '角色查询', (select menu_id from sys_menu where menu_name = '角色管理'), '1',  '', '', '', 1, 0, 'F', '0', '0', 'system:role:query',          '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '角色新增', (select menu_id from sys_menu where menu_name = '角色管理'), '2',  '', '', '', 1, 0, 'F', '0', '0', 'system:role:add',            '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '角色修改', (select menu_id from sys_menu where menu_name = '角色管理'), '3',  '', '', '', 1, 0, 'F', '0', '0', 'system:role:edit',           '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '角色删除', (select menu_id from sys_menu where menu_name = '角色管理'), '4',  '', '', '', 1, 0, 'F', '0', '0', 'system:role:remove',         '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '角色导出', (select menu_id from sys_menu where menu_name = '角色管理'), '5',  '', '', '', 1, 0, 'F', '0', '0', 'system:role:export',         '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
-- 菜单管理按钮
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '菜单查询', (select menu_id from sys_menu where menu_name = '菜单管理'), '1',  '', '', '', 1, 0, 'F', '0', '0', 'system:menu:query',          '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '菜单新增', (select menu_id from sys_menu where menu_name = '菜单管理'), '2',  '', '', '', 1, 0, 'F', '0', '0', 'system:menu:add',            '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '菜单修改', (select menu_id from sys_menu where menu_name = '菜单管理'), '3',  '', '', '', 1, 0, 'F', '0', '0', 'system:menu:edit',           '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '菜单删除', (select menu_id from sys_menu where menu_name = '菜单管理'), '4',  '', '', '', 1, 0, 'F', '0', '0', 'system:menu:remove',         '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
-- 部门管理按钮
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '部门查询', (select menu_id from sys_menu where menu_name = '部门管理'), '1',  '', '', '', 1, 0, 'F', '0', '0', 'system:dept:query',          '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '部门新增', (select menu_id from sys_menu where menu_name = '部门管理'), '2',  '', '', '', 1, 0, 'F', '0', '0', 'system:dept:add',            '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '部门修改', (select menu_id from sys_menu where menu_name = '部门管理'), '3',  '', '', '', 1, 0, 'F', '0', '0', 'system:dept:edit',           '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '部门删除', (select menu_id from sys_menu where menu_name = '部门管理'), '4',  '', '', '', 1, 0, 'F', '0', '0', 'system:dept:remove',         '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
-- 岗位管理按钮
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '岗位查询', (select menu_id from sys_menu where menu_name = '岗位管理'), '1',  '', '', '', 1, 0, 'F', '0', '0', 'system:post:query',          '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '岗位新增', (select menu_id from sys_menu where menu_name = '岗位管理'), '2',  '', '', '', 1, 0, 'F', '0', '0', 'system:post:add',            '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '岗位修改', (select menu_id from sys_menu where menu_name = '岗位管理'), '3',  '', '', '', 1, 0, 'F', '0', '0', 'system:post:edit',           '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '岗位删除', (select menu_id from sys_menu where menu_name = '岗位管理'), '4',  '', '', '', 1, 0, 'F', '0', '0', 'system:post:remove',         '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '岗位导出', (select menu_id from sys_menu where menu_name = '岗位管理'), '5',  '', '', '', 1, 0, 'F', '0', '0', 'system:post:export',         '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
-- 字典管理按钮
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '字典查询', (select menu_id from sys_menu where menu_name = '字典管理'), '1', '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:query',          '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '字典新增', (select menu_id from sys_menu where menu_name = '字典管理'), '2', '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:add',            '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '字典修改', (select menu_id from sys_menu where menu_name = '字典管理'), '3', '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:edit',           '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '字典删除', (select menu_id from sys_menu where menu_name = '字典管理'), '4', '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:remove',         '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '字典导出', (select menu_id from sys_menu where menu_name = '字典管理'), '5', '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:export',         '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
-- 参数设置按钮
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '参数查询', (select menu_id from sys_menu where menu_name = '参数设置'), '1', '#', '', '', 1, 0, 'F', '0', '0', 'system:config:query',        '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '参数新增', (select menu_id from sys_menu where menu_name = '参数设置'), '2', '#', '', '', 1, 0, 'F', '0', '0', 'system:config:add',          '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '参数修改', (select menu_id from sys_menu where menu_name = '参数设置'), '3', '#', '', '', 1, 0, 'F', '0', '0', 'system:config:edit',         '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '参数删除', (select menu_id from sys_menu where menu_name = '参数设置'), '4', '#', '', '', 1, 0, 'F', '0', '0', 'system:config:remove',       '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '参数导出', (select menu_id from sys_menu where menu_name = '参数设置'), '5', '#', '', '', 1, 0, 'F', '0', '0', 'system:config:export',       '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
-- 信息管理按钮
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '信息查询', (select menu_id from sys_menu where menu_name = '信息管理'), '1', '#', '', '', 1, 0, 'F', '0', '0', 'system:info:query',        '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '信息新增', (select menu_id from sys_menu where menu_name = '信息管理'), '2', '#', '', '', 1, 0, 'F', '0', '0', 'system:info:add',          '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '信息修改', (select menu_id from sys_menu where menu_name = '信息管理'), '3', '#', '', '', 1, 0, 'F', '0', '0', 'system:info:edit',         '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '信息删除', (select menu_id from sys_menu where menu_name = '信息管理'), '4', '#', '', '', 1, 0, 'F', '0', '0', 'system:info:remove',       '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
-- 系统附件按钮
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '系统附件查询', (select menu_id from sys_menu where menu_name = '系统附件'), '1', '#', '', '', 1, 0, 'F', '0', '0', 'system:attachment:query',   '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '系统附件删除', (select menu_id from sys_menu where menu_name = '系统附件'), '2', '#', '', '', 1, 0, 'F', '0', '0', 'system:attachment:remove',  '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
-- 操作日志按钮
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '操作查询', (select menu_id from sys_menu where menu_name = '操作日志'), '1', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:query',      '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '操作删除', (select menu_id from sys_menu where menu_name = '操作日志'), '2', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:remove',     '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '日志导出', (select menu_id from sys_menu where menu_name = '操作日志'), '4', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:export',     '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
-- 登录日志按钮
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '登录查询', (select menu_id from sys_menu where menu_name = '登录日志'), '1', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:query',   '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '登录删除', (select menu_id from sys_menu where menu_name = '登录日志'), '2', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:remove',  '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '日志导出', (select menu_id from sys_menu where menu_name = '登录日志'), '3', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:export',  '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
-- 邮件日志按钮
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '邮件日志查询', (select menu_id from sys_menu where menu_name = '邮件日志'), '1', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:emaillog:query',   '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '邮件日志删除', (select menu_id from sys_menu where menu_name = '邮件日志'), '2', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:emaillog:remove',  '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '邮件日志导出', (select menu_id from sys_menu where menu_name = '邮件日志'), '3', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:emaillog:export',  '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
-- 短信日志按钮
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '短信日志查询', (select menu_id from sys_menu where menu_name = '短信日志'), '1', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:smslog:query',   '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '短信日志删除', (select menu_id from sys_menu where menu_name = '短信日志'), '2', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:smslog:remove',  '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '短信日志导出', (select menu_id from sys_menu where menu_name = '短信日志'), '3', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:smslog:export',  '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');

-- 在线用户按钮
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '在线查询', (select menu_id from sys_menu where menu_name = '在线用户'), '1', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:online:query',       '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '批量强退', (select menu_id from sys_menu where menu_name = '在线用户'), '2', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:online:batchLogout', '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '单条强退', (select menu_id from sys_menu where menu_name = '在线用户'), '3', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:online:forceLogout', '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
-- 定时任务按钮
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '任务查询', (select menu_id from sys_menu where menu_name = '定时任务'), '1', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:query',          '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '任务新增', (select menu_id from sys_menu where menu_name = '定时任务'), '2', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:add',            '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '任务修改', (select menu_id from sys_menu where menu_name = '定时任务'), '3', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:edit',           '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '任务删除', (select menu_id from sys_menu where menu_name = '定时任务'), '4', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:remove',         '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '状态修改', (select menu_id from sys_menu where menu_name = '定时任务'), '5', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:changeStatus',   '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '任务导出', (select menu_id from sys_menu where menu_name = '定时任务'), '7', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:export',         '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
-- 代码生成按钮
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '生成查询', (select menu_id from sys_menu where menu_name = '表单构建'), '1', '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:query',             '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '生成修改', (select menu_id from sys_menu where menu_name = '表单构建'), '2', '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:edit',              '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '生成删除', (select menu_id from sys_menu where menu_name = '表单构建'), '3', '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:remove',            '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '导入代码', (select menu_id from sys_menu where menu_name = '表单构建'), '2', '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:import',            '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '预览代码', (select menu_id from sys_menu where menu_name = '表单构建'), '4', '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:preview',           '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values(nextval('sys_menu_menu_id_seq'), '生成代码', (select menu_id from sys_menu where menu_name = '表单构建'), '5', '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:code',              '#', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');

-- ----------------------------
-- 6、用户和角色关联表  用户N-1角色
-- ----------------------------
drop table if exists sys_user_role;
create table sys_user_role (
  user_id   bigint not null,
  role_id   bigint not null
);
ALTER TABLE sys_user_role ADD CONSTRAINT sys_user_role_pk PRIMARY KEY (user_id, role_id);
COMMENT ON COLUMN sys_user_role.user_id IS '用户ID';
COMMENT ON COLUMN sys_user_role.role_id IS '角色ID';
COMMENT ON TABLE sys_user_role IS '用户和角色关联表';

-- ----------------------------
-- 初始化-用户和角色关联表数据
-- ----------------------------
insert into sys_user_role values ((select user_id from sys_user where user_name = 'fqyczadmin'), (select role_id from sys_role where role_key = 'admin'));


-- ----------------------------
-- 7、角色和菜单关联表  角色1-N菜单
-- ----------------------------
drop table if exists sys_role_menu;
create table sys_role_menu (
  role_id   bigint not null,
  menu_id   bigint not NULL
);
ALTER TABLE sys_role_menu ADD CONSTRAINT sys_role_menu_pk PRIMARY KEY (role_id, menu_id);
COMMENT ON COLUMN sys_role_menu.role_id IS '角色ID';
COMMENT ON COLUMN sys_role_menu.menu_id IS '菜单ID';
COMMENT ON TABLE sys_role_menu IS '角色和菜单关联表';

-- ----------------------------
-- 初始化-角色和菜单关联表数据
-- ----------------------------
insert into sys_role_menu (role_id, menu_id)
select (select role_id from sys_role where role_key = 'common'), menu_id from sys_menu;

insert into sys_role_menu (role_id, menu_id)
select (select role_id from sys_role where role_key = 'info_record'), menu_id from sys_menu 
where menu_name in ('信息管理', '信息发布', '信息浏览', '信息查询', '信息新增', '信息修改', '信息删除');

insert into sys_role_menu (role_id, menu_id)
select (select role_id from sys_role where role_key = 'info_mgr'), menu_id from sys_menu 
where menu_name in ('信息管理', '信息发布', '信息浏览', '信息查询', '信息新增', '信息修改', '信息删除');


-- ----------------------------
-- 8、角色和部门关联表  角色1-N部门
-- ----------------------------
drop table if exists sys_role_dept;
create table sys_role_dept (
  role_id   bigint not null,
  dept_id   bigint not NULL
);
ALTER TABLE sys_role_dept ADD CONSTRAINT sys_role_dept_pk PRIMARY KEY (role_id, dept_id);
COMMENT ON COLUMN sys_role_dept.role_id IS '角色ID';
COMMENT ON COLUMN sys_role_dept.dept_id IS '部门ID';
COMMENT ON TABLE sys_role_dept IS '角色和部门关联表';

-- ----------------------------
-- 初始化-角色和部门关联表数据
-- ----------------------------
insert into sys_role_dept values ((select role_id from sys_role where role_key = 'common'), (select dept_id from sys_dept where dept_name = '睿阳科技'));


-- ----------------------------
-- 9、用户与岗位关联表  用户1-N岗位
-- ----------------------------
drop table if exists sys_user_post;
create table sys_user_post
(
  user_id   bigint not null,
  post_id   bigint not null
);
ALTER TABLE sys_user_post ADD CONSTRAINT sys_user_post_pk PRIMARY KEY (user_id, post_id);
COMMENT ON COLUMN sys_user_post.user_id IS '用户ID';
COMMENT ON COLUMN sys_user_post.post_id IS '岗位ID';
COMMENT ON TABLE sys_user_post IS '用户与岗位关联表';

-- ----------------------------
-- 初始化-用户与岗位关联表数据
-- ----------------------------
insert into sys_user_post values ((select user_id from sys_user where user_name = 'fqyczadmin'), (select post_id from sys_post where post_code = 'ceo'));


-- ----------------------------
-- 10、操作日志记录
-- ----------------------------
drop table if exists sys_oper_log;
create table sys_oper_log (
  oper_id           bigserial      not null primary key,
  title             varchar(50)     default '',
  business_type     integer          default 0,
  method            varchar(100)    default '',
  request_method    varchar(10)     default '',
  operator_type     integer          default 0,
  oper_name         varchar(50)     default '',
  dept_name         varchar(50)     default '',
  oper_url          varchar(255)    default '',
  oper_ip           varchar(128)    default '',
  oper_location     varchar(255)    default '',
  oper_param        varchar(2000)   default '',
  json_result       varchar(2000)   default '',
  status            integer          default 0,
  error_msg         varchar(2000)   default '',
  oper_time         timestamp,
  cost_time         bigint      default 0
);

COMMENT ON COLUMN sys_oper_log.oper_id IS '日志主键';
COMMENT ON COLUMN sys_oper_log.title IS '模块标题';
COMMENT ON COLUMN sys_oper_log.business_type IS '业务类型（0其它 1新增 2修改 3删除）';
COMMENT ON COLUMN sys_oper_log.method IS '方法名称';
COMMENT ON COLUMN sys_oper_log.request_method IS '请求方式';
COMMENT ON COLUMN sys_oper_log.operator_type IS '操作类别（0其它 1后台用户 2手机端用户）';
COMMENT ON COLUMN sys_oper_log.oper_name IS '操作人员';
COMMENT ON COLUMN sys_oper_log.dept_name IS '部门名称';
COMMENT ON COLUMN sys_oper_log.oper_url IS '请求URL';
COMMENT ON COLUMN sys_oper_log.oper_ip IS '主机地址';
COMMENT ON COLUMN sys_oper_log.oper_location IS '操作地点';
COMMENT ON COLUMN sys_oper_log.oper_param IS '请求参数';
COMMENT ON COLUMN sys_oper_log.json_result IS '返回参数';
COMMENT ON COLUMN sys_oper_log.status IS '操作状态（0正常 1异常）';
COMMENT ON COLUMN sys_oper_log.error_msg IS '错误消息';
COMMENT ON COLUMN sys_oper_log.oper_time IS '操作时间';
COMMENT ON COLUMN sys_oper_log.cost_time IS '消耗时间';
COMMENT ON TABLE sys_oper_log IS '操作日志记录';

drop index if exists idx_sys_oper_log_bt;
CREATE INDEX idx_sys_oper_log_bt ON sys_oper_log USING btree (
  business_type pg_catalog.int4_ops ASC NULLS LAST
);
drop index if exists idx_sys_oper_log_ot;
CREATE INDEX idx_sys_oper_log_ot ON sys_oper_log USING btree (
  oper_time pg_catalog.timestamp_ops ASC NULLS LAST
);
drop index if exists idx_sys_oper_log_s;
CREATE INDEX idx_sys_oper_log_s ON sys_oper_log USING btree (
  status pg_catalog.int4_ops ASC NULLS LAST
);

-- ----------------------------
-- 11、字典类型表
-- ----------------------------
drop table if exists sys_dict_type;
create table sys_dict_type
(
  dict_id          bigserial      not null primary key,
  dict_name        varchar(100)    default '',
  dict_type        varchar(100)    default '',
  status           char(1)         default '0',
  create_by        varchar(64)     default '',
  create_time      timestamp,
  update_by        varchar(64)     default '',
  update_time      timestamp,
  remark           varchar(500)    default null
);
COMMENT ON COLUMN sys_dict_type.dict_id IS '字典主键';
COMMENT ON COLUMN sys_dict_type.dict_name IS '字典名称';
COMMENT ON COLUMN sys_dict_type.dict_type IS '字典类型';
COMMENT ON COLUMN sys_dict_type.status IS '状态（0正常 1停用）';
COMMENT ON COLUMN sys_dict_type.create_by IS '创建者';
COMMENT ON COLUMN sys_dict_type.create_time IS '创建时间';
COMMENT ON COLUMN sys_dict_type.update_by IS '更新者';
COMMENT ON COLUMN sys_dict_type.update_time IS '更新时间';
COMMENT ON COLUMN sys_dict_type.remark IS '备注';
COMMENT ON TABLE sys_dict_type IS '字典类型表';

drop index if exists dict_type;
CREATE UNIQUE INDEX dict_type ON sys_dict_type USING btree (
  dict_type pg_catalog.text_ops ASC NULLS LAST
);

insert into sys_dict_type values(nextval('sys_dict_type_dict_id_seq'),  '用户性别', 'sys_user_sex',        '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '用户性别列表');
insert into sys_dict_type values(nextval('sys_dict_type_dict_id_seq'),  '菜单状态', 'sys_show_hide',       '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '菜单状态列表');
insert into sys_dict_type values(nextval('sys_dict_type_dict_id_seq'),  '系统开关', 'sys_normal_disable',  '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '系统开关列表');
insert into sys_dict_type values(nextval('sys_dict_type_dict_id_seq'),  '任务状态', 'sys_job_status',      '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '任务状态列表');
insert into sys_dict_type values(nextval('sys_dict_type_dict_id_seq'),  '任务分组', 'sys_job_group',       '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '任务分组列表');
insert into sys_dict_type values(nextval('sys_dict_type_dict_id_seq'),  '系统是否', 'sys_yes_no',          '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '系统是否列表');
insert into sys_dict_type values(nextval('sys_dict_type_dict_id_seq'),  '信息类型', 'sys_info_type',       '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '信息类型列表');
insert into sys_dict_type values(nextval('sys_dict_type_dict_id_seq'),  '操作类型', 'sys_oper_type',       '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '操作类型列表');
insert into sys_dict_type values(nextval('sys_dict_type_dict_id_seq'), '系统状态', 'sys_common_status',   '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '登录状态列表');
insert into sys_dict_type values(nextval('sys_dict_type_dict_id_seq'), '业务模块', 'sys_biz_model',       '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '业务模块列表');
insert into sys_dict_type values(nextval('sys_dict_type_dict_id_seq'), '审批类型', 'sys_approval_type',   '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '审批类型列表');


-- ----------------------------
-- 12、字典数据表
-- ----------------------------
drop table if exists sys_dict_data;
create table sys_dict_data
(
  dict_code        bigserial      not null primary key,
  dict_sort        integer          default 0,
  dict_label       varchar(100)    default '',
  dict_value       varchar(100)    default '',
  dict_type        varchar(100)    default '',
  css_class        varchar(100)    default null,
  list_class       varchar(100)    default null,
  is_default       char(1)         default 'N',
  status           char(1)         default '0',
  create_by        varchar(64)     default '',
  create_time      timestamp,
  update_by        varchar(64)     default '',
  update_time      timestamp,
  remark           varchar(500)    default null
);
COMMENT ON COLUMN sys_dict_data.dict_code IS '字典编码';
COMMENT ON COLUMN sys_dict_data.dict_sort IS '字典排序';
COMMENT ON COLUMN sys_dict_data.dict_label IS '字典标签';
COMMENT ON COLUMN sys_dict_data.dict_value IS '字典键值';
COMMENT ON COLUMN sys_dict_data.dict_type IS '字典类型';
COMMENT ON COLUMN sys_dict_data.css_class IS '样式属性（其他样式扩展）';
COMMENT ON COLUMN sys_dict_data.list_class IS '表格回显样式';
COMMENT ON COLUMN sys_dict_data.is_default IS '是否默认（Y是 N否）';
COMMENT ON COLUMN sys_dict_data.status IS '状态（0正常 1停用）';
COMMENT ON COLUMN sys_dict_data.create_by IS '创建者';
COMMENT ON COLUMN sys_dict_data.create_time IS '创建时间';
COMMENT ON COLUMN sys_dict_data.update_by IS '更新者';
COMMENT ON COLUMN sys_dict_data.update_time IS '更新时间';
COMMENT ON COLUMN sys_dict_data.remark IS '备注';
COMMENT ON TABLE sys_dict_data IS '字典数据表';

insert into sys_dict_data values(nextval('sys_dict_data_dict_code_seq'),  1,  '男',       '0',       'sys_user_sex',        '',   '',        'Y', '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '性别男');
insert into sys_dict_data values(nextval('sys_dict_data_dict_code_seq'),  2,  '女',       '1',       'sys_user_sex',        '',   '',        'N', '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '性别女');
insert into sys_dict_data values(nextval('sys_dict_data_dict_code_seq'),  3,  '未知',     '2',       'sys_user_sex',        '',   '',        'N', '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '性别未知');
insert into sys_dict_data values(nextval('sys_dict_data_dict_code_seq'),  1,  '显示',     '0',       'sys_show_hide',       '',   'primary', 'Y', '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '显示菜单');
insert into sys_dict_data values(nextval('sys_dict_data_dict_code_seq'),  2,  '隐藏',     '1',       'sys_show_hide',       '',   'danger',  'N', '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '隐藏菜单');
insert into sys_dict_data values(nextval('sys_dict_data_dict_code_seq'),  1,  '正常',     '0',       'sys_normal_disable',  '',   'primary', 'Y', '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '正常状态');
insert into sys_dict_data values(nextval('sys_dict_data_dict_code_seq'),  2,  '停用',     '1',       'sys_normal_disable',  '',   'danger',  'N', '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '停用状态');
insert into sys_dict_data values(nextval('sys_dict_data_dict_code_seq'),  1,  '正常',     '0',       'sys_job_status',      '',   'primary', 'Y', '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '正常状态');
insert into sys_dict_data values(nextval('sys_dict_data_dict_code_seq'),  2,  '暂停',     '1',       'sys_job_status',      '',   'danger',  'N', '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '停用状态');
insert into sys_dict_data values(nextval('sys_dict_data_dict_code_seq'), 1,  '默认',     'DEFAULT', 'sys_job_group',       '',   '',        'Y', '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '默认分组');
insert into sys_dict_data values(nextval('sys_dict_data_dict_code_seq'), 2,  '系统',     'SYSTEM',  'sys_job_group',       '',   '',        'N', '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '系统分组');
insert into sys_dict_data values(nextval('sys_dict_data_dict_code_seq'), 1,  '是',       '1',       'sys_yes_no',          '',   'primary', 'Y', '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '系统默认是');
insert into sys_dict_data values(nextval('sys_dict_data_dict_code_seq'), 2,  '否',       '0',       'sys_yes_no',          '',   'danger',  'N', '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '系统默认否');
insert into sys_dict_data values(nextval('sys_dict_data_dict_code_seq'), 1,  '新闻',     '1',       'sys_info_type',       '',   'danger',  'Y', '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '新闻');
insert into sys_dict_data values(nextval('sys_dict_data_dict_code_seq'), 2,  '公告',     '2',       'sys_info_type',       '',   'success', 'N', '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '公告');
insert into sys_dict_data values(nextval('sys_dict_data_dict_code_seq'), 1,  '新增',     '1',       'sys_oper_type',       '',   'info',    'N', '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '新增操作');
insert into sys_dict_data values(nextval('sys_dict_data_dict_code_seq'), 2,  '修改',     '2',       'sys_oper_type',       '',   'info',    'N', '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '修改操作');
insert into sys_dict_data values(nextval('sys_dict_data_dict_code_seq'), 3,  '删除',     '3',       'sys_oper_type',       '',   'danger',  'N', '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '删除操作');
insert into sys_dict_data values(nextval('sys_dict_data_dict_code_seq'), 4,  '授权',     '4',       'sys_oper_type',       '',   'primary', 'N', '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '授权操作');
insert into sys_dict_data values(nextval('sys_dict_data_dict_code_seq'), 5,  '导出',     '5',       'sys_oper_type',       '',   'warning', 'N', '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '导出操作');
insert into sys_dict_data values(nextval('sys_dict_data_dict_code_seq'), 6,  '导入',     '6',       'sys_oper_type',       '',   'warning', 'N', '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '导入操作');
insert into sys_dict_data values(nextval('sys_dict_data_dict_code_seq'), 7,  '强退',     '7',       'sys_oper_type',       '',   'danger',  'N', '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '强退操作');
insert into sys_dict_data values(nextval('sys_dict_data_dict_code_seq'), 8,  '生成代码', '8',       'sys_oper_type',       '',   'warning', 'N', '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '生成操作');
insert into sys_dict_data values(nextval('sys_dict_data_dict_code_seq'), 9,  '清空数据', '9',       'sys_oper_type',       '',   'danger',  'N', '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '清空操作');
insert into sys_dict_data values(nextval('sys_dict_data_dict_code_seq'), 1,  '成功',     '0',       'sys_common_status',   '',   'primary', 'N', '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '正常状态');
insert into sys_dict_data values(nextval('sys_dict_data_dict_code_seq'), 2,  '失败',     '1',       'sys_common_status',   '',   'danger',  'N', '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '停用状态');
insert into sys_dict_data values(nextval('sys_dict_data_dict_code_seq'), 1,  '信息发布',  'SysInfo', 'sys_biz_model',       '',   '', 'N', '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '信息发布');
insert into sys_dict_data values(nextval('sys_dict_data_dict_code_seq'), 1,  '提交',     'COMMIT',  'sys_approval_type',   '',   '', 'N', '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '提交');
insert into sys_dict_data values(nextval('sys_dict_data_dict_code_seq'), 2,  '审批通过',  'PASS',    'sys_approval_type',   '',   '', 'N', '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '审批通过');
insert into sys_dict_data values(nextval('sys_dict_data_dict_code_seq'), 3,  '审批驳回',  'REJECT',  'sys_approval_type',   '',   '', 'N', '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '审批驳回');

-- ----------------------------
-- 13、参数配置表
-- ----------------------------
drop table if exists sys_config;
create table sys_config (
  config_id         serial      not null primary key,
  config_name       varchar(100)    default '',
  config_key        varchar(100)    default '',
  config_value      varchar(500)    default '',
  config_type       char(1)         default 'N',
  create_by         varchar(64)     default '',
  create_time       timestamp,
  update_by         varchar(64)     default '',
  update_time       timestamp,
  remark            varchar(500)    default null
);
COMMENT ON COLUMN sys_config.config_id IS '参数主键';
COMMENT ON COLUMN sys_config.config_name IS '参数名称';
COMMENT ON COLUMN sys_config.config_key IS '参数键名';
COMMENT ON COLUMN sys_config.config_value IS '参数键值';
COMMENT ON COLUMN sys_config.config_type IS '系统内置（Y是 N否）';
COMMENT ON COLUMN sys_config.create_by IS '创建者';
COMMENT ON COLUMN sys_config.create_time IS '创建时间';
COMMENT ON COLUMN sys_config.update_by IS '更新者';
COMMENT ON COLUMN sys_config.update_time IS '更新时间';
COMMENT ON COLUMN sys_config.remark IS '备注';
COMMENT ON TABLE sys_config IS '参数配置表';

insert into sys_config values(nextval('sys_config_config_id_seq'), '账号自助-验证码开关',           'sys.account.captchaEnabled',    'true',         '1', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '是否开启验证码功能（true开启，false关闭）');
insert into sys_config values(nextval('sys_config_config_id_seq'), '账号自助-是否开启用户注册功能',  'sys.account.registerUser',      'false',         '1', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '是否开启注册用户功能（true开启，false关闭）');
insert into sys_config values(nextval('sys_config_config_id_seq'), '短信发送标识码',               'sys.sms.sendModelID',           '123456',        '1', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '短信发送标识（向领导申请）');
insert into sys_config values(nextval('sys_config_config_id_seq'), '用户管理-账号初始密码修改',      'sys.user.modifyInitPassword',   '0',        	    '1', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '初始密码修改（0: 不提示修改；1: 提示修改）' );
insert into sys_config values(nextval('sys_config_config_id_seq'), '用户管理-账号密码更新周期',      'sys.user.modifyPasswordPeriod', '0',            '1', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '单位为天，账号密码每隔多少天进行修改提示。0表示不提示修改' );
insert into sys_config values(nextval('sys_config_config_id_seq'), '用户管理-账号初始密码',         'sys.user.initPassword',         'rs@123!',      '1', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '初始化密码 rs@123!' );
insert into sys_config values(nextval('sys_config_config_id_seq'), '系统访问地址',         		   'sys.visit.baseurl',             'http://localhost:1024',      '0', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '系统访问地址' );


-- ----------------------------
-- 14、系统访问记录
-- ----------------------------
drop table if exists sys_logininfor;
create table sys_logininfor (
  info_id        bigserial      not null primary key,
  user_name      varchar(50)    default '',
  ipaddr         varchar(128)   default '',
  login_location varchar(255)   default '',
  browser        varchar(50)    default '',
  os             varchar(50)    default '',
  status         char(1)        default '0',
  msg            varchar(255)   default '',
  login_time     timestamp
);
COMMENT ON COLUMN sys_logininfor.info_id IS '访问ID';
COMMENT ON COLUMN sys_logininfor.user_name IS '用户账号';
COMMENT ON COLUMN sys_logininfor.ipaddr IS '登录IP地址';
COMMENT ON COLUMN sys_logininfor.login_location IS '登录地点';
COMMENT ON COLUMN sys_logininfor.browser IS '浏览器类型';
COMMENT ON COLUMN sys_logininfor.os IS '操作系统';
COMMENT ON COLUMN sys_logininfor.status IS '登录状态（0成功 1失败）';
COMMENT ON COLUMN sys_logininfor.msg IS '提示消息';
COMMENT ON COLUMN sys_logininfor.login_time IS '访问时间';
COMMENT ON TABLE sys_logininfor IS '系统访问记录';

drop index if exists idx_sys_logininfor_lt;
CREATE INDEX idx_sys_logininfor_lt ON sys_logininfor USING btree (
  login_time pg_catalog.timestamp_ops ASC NULLS LAST
);
drop index if exists idx_sys_logininfor_s;
CREATE INDEX idx_sys_logininfor_s ON sys_logininfor USING btree (
  status pg_catalog.bpchar_ops ASC NULLS LAST
);

-- ----------------------------
-- 15、定时任务调度表
-- ----------------------------
drop table if exists sys_job;
create table sys_job (
  job_id              bigserial      not null,
  job_name            varchar(64)   default '',
  job_group           varchar(64)   default 'DEFAULT',
  invoke_target       varchar(500)  not null,
  cron_expression     varchar(255)  default '',
  misfire_policy      varchar(20)   default '3',
  concurrent          char(1)       default '1',
  status              char(1)       default '0',
  create_by           varchar(64)   default '',
  create_time         timestamp,
  update_by           varchar(64)   default '',
  update_time         timestamp,
  remark              varchar(500)  default ''
);
ALTER TABLE sys_job ADD CONSTRAINT sys_job_pk PRIMARY KEY (job_id, job_name, job_group);

COMMENT ON COLUMN sys_job.job_id IS '任务ID';
COMMENT ON COLUMN sys_job.job_name IS '任务名称';
COMMENT ON COLUMN sys_job.job_group IS '任务组名';
COMMENT ON COLUMN sys_job.invoke_target IS '调用目标字符串';
COMMENT ON COLUMN sys_job.cron_expression IS 'cron执行表达式';
COMMENT ON COLUMN sys_job.misfire_policy IS '计划执行错误策略（1立即执行 2执行一次 3放弃执行）';
COMMENT ON COLUMN sys_job.concurrent IS '是否并发执行（0允许 1禁止）';
COMMENT ON COLUMN sys_job.status IS '状态（0正常 1暂停）';
COMMENT ON COLUMN sys_job.create_by IS '创建者';
COMMENT ON COLUMN sys_job.create_time IS '创建时间';
COMMENT ON COLUMN sys_job.update_by IS '更新者';
COMMENT ON COLUMN sys_job.update_time IS '更新时间';
COMMENT ON COLUMN sys_job.remark IS '备注信息';
COMMENT ON TABLE sys_job IS '定时任务调度表';

insert into sys_job values(1, '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams',        '0/10 * * * * ?', '3', '1', '1', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_job values(2, '系统默认（有参）', 'DEFAULT', 'ryTask.ryParams(''ry'')',  '0/15 * * * * ?', '3', '1', '1', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_job values(3, '系统默认（多参）', 'DEFAULT', 'ryTask.ryMultipleParams(''ry'', true, 2000L, 316.50D, 100)',  '0/20 * * * * ?', '3', '1', '1', 'fqyczadmin', CURRENT_TIMESTAMP, '', null, '');


-- ----------------------------
-- 16、定时任务调度日志表
-- ----------------------------
drop table if exists sys_job_log;
create table sys_job_log (
  job_log_id          bigserial      not null primary key,
  job_name            varchar(64)    not null,
  job_group           varchar(64)    not null,
  invoke_target       varchar(500)   not null,
  job_message         varchar(500),
  status              char(1)        default '0',
  exception_info      varchar(2000)  default '',
  create_time         timestamp
);
COMMENT ON COLUMN sys_job_log.job_log_id IS '任务日志ID';
COMMENT ON COLUMN sys_job_log.job_name IS '任务名称';
COMMENT ON COLUMN sys_job_log.job_group IS '任务组名';
COMMENT ON COLUMN sys_job_log.invoke_target IS '调用目标字符串';
COMMENT ON COLUMN sys_job_log.job_message IS '日志信息';
COMMENT ON COLUMN sys_job_log.status IS '执行状态（0正常 1失败）';
COMMENT ON COLUMN sys_job_log.exception_info IS '异常信息';
COMMENT ON COLUMN sys_job_log.create_time IS '创建时间';
COMMENT ON TABLE sys_job_log IS '定时任务调度日志表';

-- ----------------------------
-- 17、信息发布表
-- ----------------------------
drop table if exists sys_info;
create table sys_info (
  info_id     		bigserial      not null primary key,
  title             varchar(128)     default '',
  sub_title         varchar(64)      default '',
  cover				varchar(256)     default '',
  content           text,
  type           	varchar(24)      default '',
  source			varchar(24)      default '',
  author			varchar(16)      default '',
  set_top			smallint       default 0,
  anonymous			smallint       default 0,
  publish_time 	    timestamp,
  status			varchar(2)		 default '0',
  disabled			smallint       default 0,
  create_by         varchar(64)      default '',
  create_time 	    timestamp,
  update_by         varchar(64)      default '',
  update_time       timestamp
);
COMMENT ON COLUMN sys_info.info_id IS '信息id';
COMMENT ON COLUMN sys_info.title IS '标题';
COMMENT ON COLUMN sys_info.sub_title IS '副标题';
COMMENT ON COLUMN sys_info.cover IS '封面';
COMMENT ON COLUMN sys_info.content IS '内容';
COMMENT ON COLUMN sys_info.type IS '类型';
COMMENT ON COLUMN sys_info.source IS '来源';
COMMENT ON COLUMN sys_info.author IS '作者';
COMMENT ON COLUMN sys_info.set_top IS '是否置顶';
COMMENT ON COLUMN sys_info.anonymous IS '是否匿名访问';
COMMENT ON COLUMN sys_info.publish_time IS '发布时间';
COMMENT ON COLUMN sys_info.status IS '状态（0: 草稿，1: 待发布审批，2:已发布）';
COMMENT ON COLUMN sys_info.disabled IS '停用（0: 否，1: 是）';
COMMENT ON COLUMN sys_info.create_by IS '创建者';
COMMENT ON COLUMN sys_info.create_time IS '创建时间';
COMMENT ON COLUMN sys_info.update_by IS '更新者';
COMMENT ON COLUMN sys_info.update_time IS '更新时间';
COMMENT ON TABLE sys_info IS '信息发布表';

-- 信息访问范围
drop table if exists sys_info_range;
create table sys_info_range ( 
  range_id          bigserial      not null primary key,
  info_id     		bigint      not null,
  dept_id 			bigint      not null
); 
COMMENT ON COLUMN sys_info_range.range_id IS '访问范围id';
COMMENT ON COLUMN sys_info_range.info_id IS '信息发布id';
COMMENT ON COLUMN sys_info_range.dept_id IS '部门id';
COMMENT ON TABLE sys_info_range IS '信息访问范围';

-- ----------------------------
-- 18、代码生成业务表
-- ----------------------------
drop table if exists gen_table;
create table gen_table (
  table_id          bigserial      not null primary key,
  table_name        varchar(200)    default '',
  table_comment     varchar(500)    default '',
  sub_table_name    varchar(64)     default null,
  sub_table_fk_name varchar(64)     default null,
  class_name        varchar(100)    default '',
  tpl_category      varchar(200)    default 'crud',
  package_name      varchar(100),
  module_name       varchar(30),
  business_name     varchar(30),
  function_name     varchar(50),
  function_author   varchar(50),
  gen_type          char(1)         default '0',
  gen_path          varchar(200)    default '/',
  options           varchar(1000),
  create_by         varchar(64)     default '',
  create_time 	    timestamp,
  update_by         varchar(64)     default '',
  update_time       timestamp,
  remark            varchar(500)    default null
);
COMMENT ON COLUMN gen_table.table_id IS '编号';
COMMENT ON COLUMN gen_table.table_name IS '表名称';
COMMENT ON COLUMN gen_table.table_comment IS '表描述';
COMMENT ON COLUMN gen_table.sub_table_name IS '关联子表的表名';
COMMENT ON COLUMN gen_table.sub_table_fk_name IS '子表关联的外键名';
COMMENT ON COLUMN gen_table.class_name IS '实体类名称';
COMMENT ON COLUMN gen_table.tpl_category IS '使用的模板（crud单表操作 tree树表操作）';
COMMENT ON COLUMN gen_table.package_name IS '生成包路径';
COMMENT ON COLUMN gen_table.module_name IS '生成模块名';
COMMENT ON COLUMN gen_table.business_name IS '生成业务名';
COMMENT ON COLUMN gen_table.function_name IS '生成功能名';
COMMENT ON COLUMN gen_table.function_author IS '生成功能作者';
COMMENT ON COLUMN gen_table.gen_type IS '生成代码方式（0zip压缩包 1自定义路径）';
COMMENT ON COLUMN gen_table.gen_path IS '生成路径（不填默认项目路径）';
COMMENT ON COLUMN gen_table.options IS '其它生成选项';
COMMENT ON COLUMN gen_table.create_by IS '创建者';
COMMENT ON COLUMN gen_table.create_time IS '创建时间';
COMMENT ON COLUMN gen_table.update_by IS '更新者';
COMMENT ON COLUMN gen_table.update_time IS '更新时间';
COMMENT ON COLUMN gen_table.remark IS '备注';
COMMENT ON TABLE gen_table IS '代码生成业务表';


-- ----------------------------
-- 19、代码生成业务表字段
-- ----------------------------
drop table if exists gen_table_column;
create table gen_table_column (
  column_id         bigserial      not null primary key,
  table_id          varchar(64),
  column_name       varchar(200),
  column_comment    varchar(500),
  column_type       varchar(100),
  column_length     integer,
  java_type         varchar(500),
  java_field        varchar(200),
  is_pk             char(1),
  is_increment      char(1),
  is_required       char(1),
  is_insert         char(1),
  is_edit           char(1),
  is_list           char(1),
  is_query          char(1),
  query_type        varchar(200)    default 'EQ',
  html_type         varchar(200),
  dict_type         varchar(200)    default '',
  sort              integer,
  create_by         varchar(64)     default '',
  create_time 	    timestamp,
  update_by         varchar(64)     default '',
  update_time       timestamp
);
COMMENT ON COLUMN gen_table_column.column_id IS '编号';
COMMENT ON COLUMN gen_table_column.table_id IS '归属表编号';
COMMENT ON COLUMN gen_table_column.column_name IS '列名称';
COMMENT ON COLUMN gen_table_column.column_comment IS '列描述';
COMMENT ON COLUMN gen_table_column.column_type IS '列类型';
COMMENT ON COLUMN gen_table_column.column_length IS '列长度（字符类型）';
COMMENT ON COLUMN gen_table_column.java_type IS 'JAVA类型';
COMMENT ON COLUMN gen_table_column.java_field IS 'JAVA字段名';
COMMENT ON COLUMN gen_table_column.is_pk IS '是否主键（1是）';
COMMENT ON COLUMN gen_table_column.is_increment IS '是否自增（1是）';
COMMENT ON COLUMN gen_table_column.is_required IS '是否必填（1是）';
COMMENT ON COLUMN gen_table_column.is_insert IS '是否为插入字段（1是）';
COMMENT ON COLUMN gen_table_column.is_edit IS '是否编辑字段（1是）';
COMMENT ON COLUMN gen_table_column.is_list IS '是否列表字段（1是）';
COMMENT ON COLUMN gen_table_column.is_query IS '是否查询字段（1是）';
COMMENT ON COLUMN gen_table_column.query_type IS '查询方式（等于、不等于、大于、小于、范围）';
COMMENT ON COLUMN gen_table_column.html_type IS '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）';
COMMENT ON COLUMN gen_table_column.dict_type IS '字典类型';
COMMENT ON COLUMN gen_table_column.sort IS '排序';
COMMENT ON COLUMN gen_table_column.create_by IS '创建者';
COMMENT ON COLUMN gen_table_column.create_time IS '创建时间';
COMMENT ON COLUMN gen_table_column.update_by IS '更新者';
COMMENT ON COLUMN gen_table_column.update_time IS '更新时间';
COMMENT ON TABLE gen_table_column IS '代码生成业务表字段';

-- ----------------------------
-- 20、邮件日志表
-- ----------------------------
drop table if exists sys_email_log;
create table sys_email_log (
  email_id          bigserial      not null primary key,
  send_to           varchar(512)     default '',
  send_cc           varchar(512)     default '',
  send_bcc          varchar(512)     default '',
  subject           varchar(256)     default '',
  content           varchar(4096)    default '',
  send_date         timestamp,
  send_log          varchar(1024)    default '',
  status            char(1)        default '0',
  create_by         varchar(64)     default '',
  create_time 	    timestamp,
  update_by         varchar(64)     default '',
  update_time       timestamp
);
COMMENT ON COLUMN sys_email_log.email_id IS '邮件id';
COMMENT ON COLUMN sys_email_log.send_to IS '接收人';
COMMENT ON COLUMN sys_email_log.send_cc IS '抄送人';
COMMENT ON COLUMN sys_email_log.send_bcc IS '密送人';
COMMENT ON COLUMN sys_email_log.subject IS '邮件主题';
COMMENT ON COLUMN sys_email_log.content IS '邮件内容';
COMMENT ON COLUMN sys_email_log.send_date IS '发送时间';
COMMENT ON COLUMN sys_email_log.send_log IS '发送日志';
COMMENT ON COLUMN sys_email_log.status IS '发送状态（0正常 1失败）';
COMMENT ON COLUMN sys_email_log.create_by IS '创建者';
COMMENT ON COLUMN sys_email_log.create_time IS '创建时间';
COMMENT ON COLUMN sys_email_log.update_by IS '更新者';
COMMENT ON COLUMN sys_email_log.update_time IS '更新时间';
COMMENT ON TABLE sys_email_log IS '邮件日志表';

-- ----------------------------
-- 21、短信日志表
-- ----------------------------
drop table if exists sys_sms_log;
create table sys_sms_log (
  sms_id            bigserial      not null primary key,
  send_to           varchar(512)     default '',
  content           varchar(4096)    default '',
  send_date         timestamp,
  send_log          varchar(1024)    default '',
  status            char(1)          default '0',
  create_by         varchar(64)      default '',
  create_time 	    timestamp,
  update_by         varchar(64)      default '',
  update_time       timestamp
);
COMMENT ON COLUMN sys_sms_log.sms_id IS '短信id';
COMMENT ON COLUMN sys_sms_log.send_to IS '接收人';
COMMENT ON COLUMN sys_sms_log.content IS '短信内容';
COMMENT ON COLUMN sys_sms_log.send_date IS '发送时间';
COMMENT ON COLUMN sys_sms_log.send_log IS '发送日志';
COMMENT ON COLUMN sys_sms_log.status IS '发送状态（0正常 1失败）';
COMMENT ON COLUMN sys_sms_log.create_by IS '创建者';
COMMENT ON COLUMN sys_sms_log.create_time IS '创建时间';
COMMENT ON COLUMN sys_sms_log.update_by IS '更新者';
COMMENT ON COLUMN sys_sms_log.update_time IS '更新时间';
COMMENT ON TABLE sys_sms_log IS '短信日志表';

-- ----------------------------
-- 22、业务附件表
-- ----------------------------
drop table if exists sys_attachment;
create table sys_attachment (
  attach_id     	bigserial      not null primary key,
  file_name         varchar(128)     default '',
  file_url          varchar(256)     default '',
  file_suffix		varchar(24)      default '',
  file_size         varchar(128)     default '',
  biz_model         varchar(64)      default '',
  create_by         varchar(64)      default '',
  create_time 	    timestamp,
  update_by         varchar(64)      default '',
  update_time       timestamp
);
COMMENT ON COLUMN sys_attachment.attach_id IS '附件id';
COMMENT ON COLUMN sys_attachment.file_name IS '文件名称';
COMMENT ON COLUMN sys_attachment.file_url IS '文件路径';
COMMENT ON COLUMN sys_attachment.file_suffix IS '文件后缀';
COMMENT ON COLUMN sys_attachment.file_size IS '文件大小';
COMMENT ON COLUMN sys_attachment.biz_model IS '业务模块';
COMMENT ON COLUMN sys_attachment.create_by IS '创建者';
COMMENT ON COLUMN sys_attachment.create_time IS '创建时间';
COMMENT ON COLUMN sys_attachment.update_by IS '更新者';
COMMENT ON COLUMN sys_attachment.update_time IS '更新时间';
COMMENT ON TABLE sys_attachment IS '业务附件表';

-- ----------------------------
-- 23、审批日志表
-- ----------------------------
drop table if exists sys_approval_log;
create table sys_approval_log ( 
  log_id            bigserial      not null primary key,
  biz_model			varchar(64)     default '',
  biz_id     		bigint      not null,
  comment 		    varchar(256)    default '',
  operate_type 	    varchar(24)     default '',
  operator          varchar(24)     default '',
  operate_time 	    timestamp
);
COMMENT ON COLUMN sys_approval_log.log_id IS '日志id';
COMMENT ON COLUMN sys_approval_log.biz_model IS '业务模块';
COMMENT ON COLUMN sys_approval_log.biz_id IS '业务id';
COMMENT ON COLUMN sys_approval_log.comment IS '审批意见';
COMMENT ON COLUMN sys_approval_log.operate_type IS '操作类型';
COMMENT ON COLUMN sys_approval_log.operator IS '操作人';
COMMENT ON COLUMN sys_approval_log.operate_time IS '操作时间';
COMMENT ON TABLE sys_approval_log IS '审批日志表';

-- ----------------------------
-- 24、留言评论表
-- ----------------------------
drop table if exists sys_comment;
create table sys_comment ( 
  comment_id        bigserial      not null primary key,
  biz_model			varchar(64)     default '',
  biz_id     		bigint      not null,
  from_id		    varchar(64)     default '',
  comment_date 		timestamp    	null,
  content			varchar(1024)   default '',
  img_url			varchar(1024)   default '',
  thumb_url         varchar(1024)   default '',
  video_url			varchar(1024)   default ''
);
COMMENT ON COLUMN sys_comment.comment_id IS '评论id';
COMMENT ON COLUMN sys_comment.biz_model IS '业务模块';
COMMENT ON COLUMN sys_comment.biz_id IS '业务id';
COMMENT ON COLUMN sys_comment.from_id IS '评论者id';
COMMENT ON COLUMN sys_comment.comment_date IS '评论时间';
COMMENT ON COLUMN sys_comment.content IS '评论内容';
COMMENT ON COLUMN sys_comment.img_url IS '评论图片';
COMMENT ON COLUMN sys_comment.thumb_url IS '评论缩略图';
COMMENT ON COLUMN sys_comment.video_url IS '评论视频';
COMMENT ON TABLE sys_comment IS '留言评论表';

-- ----------------------------
-- 25、留言评论回复表
-- ----------------------------
drop table if exists sys_comment_reply;
create table sys_comment_reply ( 
  reply_id       	bigserial       not null primary key,
  comment_id        bigint      	not null,
  from_id		    varchar(64)     default '',
  to_id		    	varchar(64)     default '',
  comment_date 		timestamp    	null,
  content			varchar(1024)   default '',
  img_url			varchar(1024)   default '',
  thumb_url         varchar(1024)   default '',
  video_url			varchar(1024)   default ''
);
COMMENT ON COLUMN sys_comment_reply.reply_id IS '回复id';
COMMENT ON COLUMN sys_comment_reply.comment_id IS '父评论id';
COMMENT ON COLUMN sys_comment_reply.from_id IS '评论者id';
COMMENT ON COLUMN sys_comment_reply.to_id IS '被评论者id';
COMMENT ON COLUMN sys_comment_reply.comment_date IS '评论时间';
COMMENT ON COLUMN sys_comment_reply.content IS '评论内容';
COMMENT ON COLUMN sys_comment_reply.img_url IS '评论图片';
COMMENT ON COLUMN sys_comment_reply.thumb_url IS '评论缩略图';
COMMENT ON COLUMN sys_comment_reply.video_url IS '评论视频';
COMMENT ON TABLE sys_comment_reply IS '留言评论回复表';

-- ----------------------------
-- 26、留言点赞表
-- ----------------------------
drop table if exists sys_comment_like;
create table sys_comment_like ( 
  like_id        	bigserial       not null primary key,
  comment_id        bigint          not null,
  user_id		    varchar(64)     default '',
  like_date 		timestamp    	null
);
COMMENT ON COLUMN sys_comment_like.like_id IS '点赞id';
COMMENT ON COLUMN sys_comment_like.comment_id IS '评论id';
COMMENT ON COLUMN sys_comment_like.user_id IS '评论者id';
COMMENT ON COLUMN sys_comment_like.like_date IS '点赞时间';
COMMENT ON TABLE sys_comment_like IS '留言点赞表';
