-- ----------------------------
-- 1、部门表
-- ----------------------------
drop table if exists sys_dept;
create table sys_dept (
  dept_id           bigint      not null auto_increment    comment '部门id',
  parent_id         bigint      default 0                  comment '父部门id',
  ancestors         varchar(50)     default ''                 comment '祖级列表',
  dept_name         varchar(30)     default ''                 comment '部门名称',
  order_num         int          default 0                  comment '显示顺序',
  leader            varchar(20)     default null               comment '负责人',
  phone             varchar(11)     default null               comment '联系电话',
  email             varchar(50)     default null               comment '邮箱',
  status            char(1)         default '0'                comment '部门状态（0正常 1停用）',
  del_flag          char(1)         default '0'                comment '删除标志（0代表存在 2代表删除）',
  dept_key          varchar(30)     default ''                 comment '部门标识',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time 	    datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  primary key (dept_id)
) engine=innodb auto_increment=200 comment = '部门表';

-- ----------------------------
-- 初始化-部门表数据
-- ----------------------------
insert into sys_dept values(100,  0,   '0',          '精灵Spirit',   0, '系统管理员', '15888888888', 'admin@admin.com', '0', '0', '', 'superadmin', sysdate(), '', null);


-- ----------------------------
-- 2、用户信息表
-- ----------------------------
drop table if exists sys_user;
create table sys_user (
  user_id           bigint      not null auto_increment    comment '用户ID',
  dept_id           bigint      default null               comment '部门ID',
  user_name         varchar(30)     not null                   comment '用户账号',
  nick_name         varchar(30)     not null                   comment '用户昵称',
  user_type         varchar(2)      default '00'               comment '用户类型（00系统用户）',
  email             varchar(50)     default ''                 comment '用户邮箱',
  phonenumber       varchar(11)     default ''                 comment '手机号码',
  sex               char(1)         default '0'                comment '用户性别（0男 1女 2未知）',
  avatar            varchar(100)    default ''                 comment '头像地址',
  password          varchar(400)    default ''                 comment '密码',
  py_name	        varchar(200)    default ''                 comment '用户名拼音',
  first_spell_name	varchar(16)     default ''                 comment '用户名拼音首字母',
  status            char(1)         default '0'                comment '帐号状态（0正常 1停用）',
  del_flag          char(1)         default '0'                comment '删除标志（0代表存在 2代表删除）',
  login_ip          varchar(128)    default ''                 comment '最后登录IP',
  login_date        datetime                                   comment '最后登录时间',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default null               comment '备注',
  primary key (user_id)
) engine=innodb auto_increment=100 comment = '用户信息表';

-- ----------------------------
-- 初始化-用户信息表数据
-- ----------------------------
insert into sys_user values(1,  100, 'superadmin', '系统管理员', '00', 'superadmin@admin.com', '15888888888', '1', '', 'I75nbL6vrBvIljOAfD1nKWCq7o+4+2YZiDJmJ1KsBBs+P5u2DBBm25DoV5SFGtQ/FXscGCpfU+blwrzsn70gUKHF22F934Pett2k5KskrO4DcaJZhVrV6Rp/fw7loElRPXgIMmUQ7+8AaTwmWAT1xVE91Xp9HQQfwLLTFjctPP8EEvrzgNDG01NZbYubvRTDmWWy/V8F6T++tvsxv6FJz1KDOkMaCH2OUiCeGo7kMrTjmAaCuCiLbe6ny/f90kmOSTzcJFMj2x8UpFADjujTOnXKWX2sFE4S/+am4Ten6a4EjjfVq+N2UNighWDBBZ0DysKrjzlMXJSb2gOxmArpBA==', 'xitongguanliyuan', 'XTGLY', '0', '0', '127.0.0.1', sysdate(), 'superadmin', sysdate(), '', null, '管理员');

-- ----------------------------
-- 用户密码修改记录表
-- ----------------------------
drop table if exists sys_user_pwd_modify;
create table sys_user_pwd_modify (
  user_id           bigint      not null 				   comment '用户ID',
  modify_by         varchar(64)     default ''                 comment '更新者',
  modify_time       datetime                                   comment '更新时间',
  primary key (user_id)
) engine=innodb comment = '用户密码修改记录表';


-- ----------------------------
-- 3、岗位信息表
-- ----------------------------
drop table if exists sys_post;
create table sys_post
(
  post_id       bigint      not null auto_increment    comment '岗位ID',
  post_code     varchar(64)     not null                   comment '岗位编码',
  post_name     varchar(50)     not null                   comment '岗位名称',
  post_sort     int          not null                   comment '显示顺序',
  status        char(1)         not null                   comment '状态（0正常 1停用）',
  create_by     varchar(64)     default ''                 comment '创建者',
  create_time   datetime                                   comment '创建时间',
  update_by     varchar(64)     default ''			       comment '更新者',
  update_time   datetime                                   comment '更新时间',
  remark        varchar(500)    default null               comment '备注',
  primary key (post_id)
) engine=innodb comment = '岗位信息表';

-- ----------------------------
-- 初始化-岗位信息表数据
-- ----------------------------
insert into sys_post values(1, 'ceo',  '总负责人',    1, '0', 'superadmin', sysdate(), '', null, '');


-- ----------------------------
-- 4、角色信息表
-- ----------------------------
drop table if exists sys_role;
create table sys_role (
  role_id              		  bigint          not null  auto_increment   comment '角色ID',
  role_name            		  varchar(30)     not null                   comment '角色名称',
  role_key             		  varchar(100)    not null                   comment '角色权限字符串',
  role_sort            		  int             not null                   comment '显示顺序',
  data_scope                  char(1)         default '1'                comment '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  menu_check_strictly  		  tinyint         default 1                  comment '菜单树选择项是否关联显示',
  mobile_menu_check_strictly  tinyint  		  default 1                  comment '移动菜单树选择项是否关联显示',
  dept_check_strictly  		  tinyint         default 1                  comment '部门树选择项是否关联显示',
  status               		  char(1)         not null                   comment '角色状态（0正常 1停用）',
  del_flag             		  char(1)         default '0'                comment '删除标志（0代表存在 2代表删除）',
  create_by            		  varchar(64)     default ''                 comment '创建者',
  create_time          		  datetime                                   comment '创建时间',
  update_by            		  varchar(64)     default ''                 comment '更新者',
  update_time          		  datetime                                   comment '更新时间',
  remark               		  varchar(500)    default null               comment '备注',
  primary key (role_id)
) engine=innodb auto_increment=100 comment = '角色信息表';

-- ----------------------------
-- 初始化-角色信息表数据
-- ----------------------------
insert into sys_role values('1', '超级管理员',  'admin',  1, 1, 1, 1, 1, '0', '0', 'superadmin', sysdate(), '', null, '超级管理员');
insert into sys_role values('2', '普通角色',    'common', 2, 2, 1, 1, 1, '0', '0', 'superadmin', sysdate(), '', null, '普通角色');
insert into sys_role values('3', '信息录入员',  'info_record', 3, 2, 0, 0, 0, '0', '0', 'superadmin', sysdate(), '', null, '信息录入员');
insert into sys_role values('4', '信息管理员',  'info_mgr', 4, 2, 0, 0, 0, '0', '0', 'superadmin', sysdate(), '', null, '信息管理员');


-- ----------------------------
-- 5、菜单权限表
-- ----------------------------
drop table if exists sys_menu;
create table sys_menu (
  menu_id           bigint      not null auto_increment    comment '菜单ID',
  menu_name         varchar(50)     not null                   comment '菜单名称',
  parent_id         bigint      default 0                  comment '父菜单ID',
  order_num         int          default 0                  comment '显示顺序',
  path              varchar(200)    default ''                 comment '路由地址',
  component         varchar(255)    default null               comment '组件路径',
  query             varchar(255)    default null               comment '路由参数',
  is_frame          int          default 1                  comment '是否为外链（0是 1否）',
  is_cache          int          default 0                  comment '是否缓存（0缓存 1不缓存）',
  menu_type         char(1)         default ''                 comment '菜单类型（M目录 C菜单 F按钮）',
  visible           char(1)         default 0                  comment '菜单状态（0显示 1隐藏）',
  status            char(1)         default 0                  comment '菜单状态（0正常 1停用）',
  perms             varchar(100)    default null               comment '权限标识',
  icon              varchar(100)    default '#'                comment '菜单图标',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default ''                 comment '备注',
  primary key (menu_id)
) engine=innodb auto_increment=2000 comment = '菜单权限表';

-- ----------------------------
-- 初始化-菜单信息表数据
-- ----------------------------
-- 一级菜单
insert into sys_menu values('1', '系统管理', '0', '100', 'system',           null, '', 1, 0, 'M', '0', '0', '', 'system',   'superadmin', sysdate(), '', null, '系统管理目录');
insert into sys_menu values('2', '系统监控', '0', '110', 'monitor',          null, '', 1, 0, 'M', '0', '0', '', 'monitor',  'superadmin', sysdate(), '', null, '系统监控目录');
insert into sys_menu values('3', '系统工具', '0', '120', 'tool',             null, '', 1, 0, 'M', '0', '0', '', 'tool',     'superadmin', sysdate(), '', null, '系统工具目录');
insert into sys_menu values('4', '业务逻辑', '0', '10', 'biz',               null, '', 1, 0, 'M', '0', '0', '', 'list',   'superadmin', sysdate(), '', null, '业务逻辑目录');
-- 二级菜单
insert into sys_menu values('100',  '用户管理', '1',   '1', 'user',       'system/user/index',        '', 1, 0, 'C', '0', '0', 'system:user:list',        'user',          'superadmin', sysdate(), '', null, '用户管理菜单');
insert into sys_menu values('101',  '角色管理', '1',   '2', 'role',       'system/role/index',        '', 1, 0, 'C', '0', '0', 'system:role:list',        'peoples',       'superadmin', sysdate(), '', null, '角色管理菜单');
insert into sys_menu values('102',  '菜单管理', '1',   '3', 'menu',       'system/menu/index',        '', 1, 0, 'C', '0', '0', 'system:menu:list',        'tree-table',    'superadmin', sysdate(), '', null, '菜单管理菜单');
insert into sys_menu values('103',  '部门管理', '1',   '4', 'dept',       'system/dept/index',        '', 1, 0, 'C', '0', '0', 'system:dept:list',        'tree',          'superadmin', sysdate(), '', null, '部门管理菜单');
insert into sys_menu values('104',  '岗位管理', '1',   '5', 'post',       'system/post/index',        '', 1, 0, 'C', '0', '0', 'system:post:list',        'post',          'superadmin', sysdate(), '', null, '岗位管理菜单');
insert into sys_menu values('105',  '字典管理', '1',   '6', 'dict',       'system/dict/index',        '', 1, 0, 'C', '0', '0', 'system:dict:list',        'dict',          'superadmin', sysdate(), '', null, '字典管理菜单');
insert into sys_menu values('106',  '参数设置', '1',   '7', 'config',     'system/config/index',      '', 1, 0, 'C', '0', '0', 'system:config:list',      'edit',          'superadmin', sysdate(), '', null, '参数设置菜单');
insert into sys_menu values('107',  '信息管理', '1',   '8', 'info',		 '',                         '', 1, 0, 'M', '0', '0', '',                        'message',       'superadmin', sysdate(), '', null, '信息管理菜单');
insert into sys_menu values('119',  '系统附件', '1',   '9', 'attachment', 'system/attachment/index',  '', 1, 0, 'C', '0', '0', 'system:attachment:list',  'upload',        'superadmin', sysdate(), '', null, '系统业务附件菜单');
insert into sys_menu values('108',  '日志管理', '1',   '10', 'log',        '',                        '', 1, 0, 'M', '0', '0', '',                       'log',           'superadmin', sysdate(), '', null, '日志管理菜单');
insert into sys_menu values('109',  '在线用户', '2',   '1', 'online',     'monitor/online/index',     '', 1, 0, 'C', '0', '0', 'monitor:online:list',     'online',        'superadmin', sysdate(), '', null, '在线用户菜单');
insert into sys_menu values('110',  '定时任务', '2',   '2', 'job',        'monitor/job/index',        '', 1, 0, 'C', '0', '0', 'monitor:job:list',        'job',           'superadmin', sysdate(), '', null, '定时任务菜单');
insert into sys_menu values('111',  '数据监控', '2',   '3', 'druid',      'monitor/druid/index',      '', 1, 0, 'C', '0', '0', 'monitor:druid:list',      'druid',         'superadmin', sysdate(), '', null, '数据监控菜单');
insert into sys_menu values('112',  '服务监控', '2',   '4', 'server',     'monitor/server/index',     '', 1, 0, 'C', '0', '0', 'monitor:server:list',     'server',        'superadmin', sysdate(), '', null, '服务监控菜单');
insert into sys_menu values('113',  '缓存监控', '2',   '5', 'cache',      'monitor/cache/index',      '', 1, 0, 'C', '0', '0', 'monitor:cache:list',      'redis',         'superadmin', sysdate(), '', null, '缓存监控菜单');
insert into sys_menu values('114',  '缓存列表', '2',   '6', 'cacheList',  'monitor/cache/list',       '', 1, 0, 'C', '0', '0', 'monitor:cache:list',      'redis-list',    'superadmin', sysdate(), '', null, '缓存列表菜单');
insert into sys_menu values('120',  '审批记录', '2',   '7', 'approvalList','monitor/approval/index',  '', 1, 0, 'C', '0', '0', 'monitor:approvallog:list','time-range',    'superadmin', sysdate(), '', null, '审批记录菜单');
insert into sys_menu values('115',  '表单构建', '3',   '1', 'build',      'tool/build/index',         '', 1, 0, 'C', '0', '0', 'tool:build:list',         'build',         'superadmin', sysdate(), '', null, '表单构建菜单');
insert into sys_menu values('116',  '代码生成', '3',   '2', 'gen',        'tool/gen/index',           '', 1, 0, 'C', '0', '0', 'tool:gen:list',           'code',          'superadmin', sysdate(), '', null, '代码生成菜单');
insert into sys_menu values('118',  '代码示例', '3',   '4', 'codeexample','tool/example/index',       '', 1, 0, 'C', '0', '0', '',                        'code',          'superadmin', sysdate(), '', null, '代码示例菜单');
-- 三级菜单
insert into sys_menu values('497',  '栏目分类', '107', '1', 'infocategory',    'system/info/category/index', '', 1, 0, 'C', '0', '0', 'system:info:list',  'dict',          'superadmin', sysdate(), '', null, '信息栏目菜单');
insert into sys_menu values('498',  '信息发布', '107', '2', 'infomgr',         'system/info/index',        '', 1, 0, 'C', '0', '0', 'system:info:list',    'message',       'superadmin', sysdate(), '', null, '信息发布菜单');
insert into sys_menu values('499',  '信息浏览', '107', '3', 'infoview',        'system/info/view',        '', 1, 0, 'C', '0', '0', 'system:info:list',     'eye-open',      'superadmin', sysdate(), '', null, '信息浏览菜单');

insert into sys_menu values('500',  '操作日志', '108', '1', 'operlog',    'monitor/operlog/index',    '', 1, 0, 'C', '0', '0', 'monitor:operlog:list',    'form',          'superadmin', sysdate(), '', null, '操作日志菜单');
insert into sys_menu values('501',  '登录日志', '108', '2', 'logininfor', 'monitor/logininfor/index', '', 1, 0, 'C', '0', '0', 'monitor:logininfor:list', 'logininfor',    'superadmin', sysdate(), '', null, '登录日志菜单');
insert into sys_menu values('502',  '邮件日志', '108', '3', 'emaillog',   'monitor/emaillog/index',   '', 1, 0, 'C', '0', '0', 'monitor:emaillog:list',   'email',         'superadmin', sysdate(), '', null, '邮件日志菜单');
insert into sys_menu values('503',  '短信日志', '108', '4', 'smslog',     'monitor/smslog/index',     '', 1, 0, 'C', '0', '0', 'monitor:smslog:list',     'message',       'superadmin', sysdate(), '', null, '短信日志菜单');

-- 用户管理按钮
insert into sys_menu values('1000', '用户查询', '100', '1',  '', '', '', 1, 0, 'F', '0', '0', 'system:user:query',          '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1001', '用户新增', '100', '2',  '', '', '', 1, 0, 'F', '0', '0', 'system:user:add',            '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1002', '用户修改', '100', '3',  '', '', '', 1, 0, 'F', '0', '0', 'system:user:edit',           '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1003', '用户删除', '100', '4',  '', '', '', 1, 0, 'F', '0', '0', 'system:user:remove',         '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1004', '用户导出', '100', '5',  '', '', '', 1, 0, 'F', '0', '0', 'system:user:export',         '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1005', '用户导入', '100', '6',  '', '', '', 1, 0, 'F', '0', '0', 'system:user:import',         '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1006', '重置密码', '100', '7',  '', '', '', 1, 0, 'F', '0', '0', 'system:user:resetPwd',       '#', 'superadmin', sysdate(), '', null, '');
-- 角色管理按钮
insert into sys_menu values('1007', '角色查询', '101', '1',  '', '', '', 1, 0, 'F', '0', '0', 'system:role:query',          '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1008', '角色新增', '101', '2',  '', '', '', 1, 0, 'F', '0', '0', 'system:role:add',            '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1009', '角色修改', '101', '3',  '', '', '', 1, 0, 'F', '0', '0', 'system:role:edit',           '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1010', '角色删除', '101', '4',  '', '', '', 1, 0, 'F', '0', '0', 'system:role:remove',         '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1011', '角色导出', '101', '5',  '', '', '', 1, 0, 'F', '0', '0', 'system:role:export',         '#', 'superadmin', sysdate(), '', null, '');
-- 菜单管理按钮
insert into sys_menu values('1012', '菜单查询', '102', '1',  '', '', '', 1, 0, 'F', '0', '0', 'system:menu:query',          '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1013', '菜单新增', '102', '2',  '', '', '', 1, 0, 'F', '0', '0', 'system:menu:add',            '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1014', '菜单修改', '102', '3',  '', '', '', 1, 0, 'F', '0', '0', 'system:menu:edit',           '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1015', '菜单删除', '102', '4',  '', '', '', 1, 0, 'F', '0', '0', 'system:menu:remove',         '#', 'superadmin', sysdate(), '', null, '');
-- 部门管理按钮
insert into sys_menu values('1016', '部门查询', '103', '1',  '', '', '', 1, 0, 'F', '0', '0', 'system:dept:query',          '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1017', '部门新增', '103', '2',  '', '', '', 1, 0, 'F', '0', '0', 'system:dept:add',            '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1018', '部门修改', '103', '3',  '', '', '', 1, 0, 'F', '0', '0', 'system:dept:edit',           '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1019', '部门删除', '103', '4',  '', '', '', 1, 0, 'F', '0', '0', 'system:dept:remove',         '#', 'superadmin', sysdate(), '', null, '');
-- 岗位管理按钮
insert into sys_menu values('1020', '岗位查询', '104', '1',  '', '', '', 1, 0, 'F', '0', '0', 'system:post:query',          '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1021', '岗位新增', '104', '2',  '', '', '', 1, 0, 'F', '0', '0', 'system:post:add',            '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1022', '岗位修改', '104', '3',  '', '', '', 1, 0, 'F', '0', '0', 'system:post:edit',           '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1023', '岗位删除', '104', '4',  '', '', '', 1, 0, 'F', '0', '0', 'system:post:remove',         '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1024', '岗位导出', '104', '5',  '', '', '', 1, 0, 'F', '0', '0', 'system:post:export',         '#', 'superadmin', sysdate(), '', null, '');
-- 字典管理按钮
insert into sys_menu values('1025', '字典查询', '105', '1', '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:query',          '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1026', '字典新增', '105', '2', '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:add',            '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1027', '字典修改', '105', '3', '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:edit',           '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1028', '字典删除', '105', '4', '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:remove',         '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1029', '字典导出', '105', '5', '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:export',         '#', 'superadmin', sysdate(), '', null, '');
-- 参数设置按钮
insert into sys_menu values('1030', '参数查询', '106', '1', '#', '', '', 1, 0, 'F', '0', '0', 'system:config:query',        '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1031', '参数新增', '106', '2', '#', '', '', 1, 0, 'F', '0', '0', 'system:config:add',          '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1032', '参数修改', '106', '3', '#', '', '', 1, 0, 'F', '0', '0', 'system:config:edit',         '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1033', '参数删除', '106', '4', '#', '', '', 1, 0, 'F', '0', '0', 'system:config:remove',       '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1034', '参数导出', '106', '5', '#', '', '', 1, 0, 'F', '0', '0', 'system:config:export',       '#', 'superadmin', sysdate(), '', null, '');
-- 信息管理按钮
insert into sys_menu values('1035', '信息查询', '107', '1', '#', '', '', 1, 0, 'F', '0', '0', 'system:info:query',        '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1036', '信息新增', '107', '2', '#', '', '', 1, 0, 'F', '0', '0', 'system:info:add',          '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1037', '信息修改', '107', '3', '#', '', '', 1, 0, 'F', '0', '0', 'system:info:edit',         '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1038', '信息删除', '107', '4', '#', '', '', 1, 0, 'F', '0', '0', 'system:info:remove',       '#', 'superadmin', sysdate(), '', null, '');
-- 系统附件按钮
insert into sys_menu values('1066', '系统附件查询', '119', '1', '#', '', '', 1, 0, 'F', '0', '0', 'system:attachment:query',   '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1067', '系统附件删除', '119', '2', '#', '', '', 1, 0, 'F', '0', '0', 'system:attachment:remove',  '#', 'superadmin', sysdate(), '', null, '');
-- 操作日志按钮
insert into sys_menu values('1039', '操作查询', '500', '1', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:query',      '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1040', '操作删除', '500', '2', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:remove',     '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1041', '日志导出', '500', '4', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:export',     '#', 'superadmin', sysdate(), '', null, '');
-- 登录日志按钮
insert into sys_menu values('1042', '登录查询', '501', '1', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:query',   '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1043', '登录删除', '501', '2', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:remove',  '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1044', '日志导出', '501', '3', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:export',  '#', 'superadmin', sysdate(), '', null, '');
-- 邮件日志按钮
insert into sys_menu values('1060', '邮件日志查询', '502', '1', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:emaillog:query',   '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1061', '邮件日志删除', '502', '2', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:emaillog:remove',  '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1062', '邮件日志导出', '502', '3', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:emaillog:export',  '#', 'superadmin', sysdate(), '', null, '');
-- 短信日志按钮
insert into sys_menu values('1063', '短信日志查询', '503', '1', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:smslog:query',   '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1064', '短信日志删除', '503', '2', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:smslog:remove',  '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1065', '短信日志导出', '503', '3', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:smslog:export',  '#', 'superadmin', sysdate(), '', null, '');


-- 在线用户按钮
insert into sys_menu values('1045', '在线查询', '109', '1', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:online:query',       '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1046', '批量强退', '109', '2', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:online:batchLogout', '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1047', '单条强退', '109', '3', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:online:forceLogout', '#', 'superadmin', sysdate(), '', null, '');
-- 定时任务按钮
insert into sys_menu values('1048', '任务查询', '110', '1', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:query',          '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1049', '任务新增', '110', '2', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:add',            '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1050', '任务修改', '110', '3', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:edit',           '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1051', '任务删除', '110', '4', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:remove',         '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1052', '状态修改', '110', '5', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:changeStatus',   '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1053', '任务导出', '110', '7', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:export',         '#', 'superadmin', sysdate(), '', null, '');
-- 代码生成按钮
insert into sys_menu values('1054', '生成查询', '115', '1', '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:query',             '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1055', '生成修改', '115', '2', '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:edit',              '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1056', '生成删除', '115', '3', '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:remove',            '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1057', '导入代码', '115', '2', '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:import',            '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1058', '预览代码', '115', '4', '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:preview',           '#', 'superadmin', sysdate(), '', null, '');
insert into sys_menu values('1059', '生成代码', '115', '5', '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:code',              '#', 'superadmin', sysdate(), '', null, '');


-- ----------------------------
-- 6、用户和角色关联表  用户N-1角色
-- ----------------------------
drop table if exists sys_user_role;
create table sys_user_role (
  user_id   bigint not null comment '用户ID',
  role_id   bigint not null comment '角色ID',
  primary key(user_id, role_id)
) engine=innodb comment = '用户和角色关联表';

-- ----------------------------
-- 初始化-用户和角色关联表数据
-- ----------------------------
insert into sys_user_role values ('1', '1');


-- ----------------------------
-- 7、角色和菜单关联表  角色1-N菜单
-- ----------------------------
drop table if exists sys_role_menu;
create table sys_role_menu (
  role_id   bigint not null comment '角色ID',
  menu_id   bigint not null comment '菜单ID',
  primary key(role_id, menu_id)
) engine=innodb comment = '角色和菜单关联表';

-- ----------------------------
-- 初始化-角色和菜单关联表数据
-- ----------------------------
insert into sys_role_menu values ('2', '1');
insert into sys_role_menu values ('2', '2');
insert into sys_role_menu values ('2', '3');
insert into sys_role_menu values ('2', '4');
insert into sys_role_menu values ('2', '100');
insert into sys_role_menu values ('2', '101');
insert into sys_role_menu values ('2', '102');
insert into sys_role_menu values ('2', '103');
insert into sys_role_menu values ('2', '104');
insert into sys_role_menu values ('2', '105');
insert into sys_role_menu values ('2', '106');
insert into sys_role_menu values ('2', '107');
insert into sys_role_menu values ('2', '108');
insert into sys_role_menu values ('2', '109');
insert into sys_role_menu values ('2', '110');
insert into sys_role_menu values ('2', '111');
insert into sys_role_menu values ('2', '112');
insert into sys_role_menu values ('2', '113');
insert into sys_role_menu values ('2', '114');
insert into sys_role_menu values ('2', '115');
insert into sys_role_menu values ('2', '116');
insert into sys_role_menu values ('2', '118');
insert into sys_role_menu values ('2', '119');
insert into sys_role_menu values ('2', '120');
insert into sys_role_menu values ('2', '499');
insert into sys_role_menu values ('2', '500');
insert into sys_role_menu values ('2', '501');
insert into sys_role_menu values ('2', '502');
insert into sys_role_menu values ('2', '503');
insert into sys_role_menu values ('2', '1000');
insert into sys_role_menu values ('2', '1001');
insert into sys_role_menu values ('2', '1002');
insert into sys_role_menu values ('2', '1003');
insert into sys_role_menu values ('2', '1004');
insert into sys_role_menu values ('2', '1005');
insert into sys_role_menu values ('2', '1006');
insert into sys_role_menu values ('2', '1007');
insert into sys_role_menu values ('2', '1008');
insert into sys_role_menu values ('2', '1009');
insert into sys_role_menu values ('2', '1010');
insert into sys_role_menu values ('2', '1011');
insert into sys_role_menu values ('2', '1012');
insert into sys_role_menu values ('2', '1013');
insert into sys_role_menu values ('2', '1014');
insert into sys_role_menu values ('2', '1015');
insert into sys_role_menu values ('2', '1016');
insert into sys_role_menu values ('2', '1017');
insert into sys_role_menu values ('2', '1018');
insert into sys_role_menu values ('2', '1019');
insert into sys_role_menu values ('2', '1020');
insert into sys_role_menu values ('2', '1021');
insert into sys_role_menu values ('2', '1022');
insert into sys_role_menu values ('2', '1023');
insert into sys_role_menu values ('2', '1024');
insert into sys_role_menu values ('2', '1025');
insert into sys_role_menu values ('2', '1026');
insert into sys_role_menu values ('2', '1027');
insert into sys_role_menu values ('2', '1028');
insert into sys_role_menu values ('2', '1029');
insert into sys_role_menu values ('2', '1030');
insert into sys_role_menu values ('2', '1031');
insert into sys_role_menu values ('2', '1032');
insert into sys_role_menu values ('2', '1033');
insert into sys_role_menu values ('2', '1034');
insert into sys_role_menu values ('2', '1035');
insert into sys_role_menu values ('2', '1036');
insert into sys_role_menu values ('2', '1037');
insert into sys_role_menu values ('2', '1038');
insert into sys_role_menu values ('2', '1039');
insert into sys_role_menu values ('2', '1040');
insert into sys_role_menu values ('2', '1041');
insert into sys_role_menu values ('2', '1042');
insert into sys_role_menu values ('2', '1043');
insert into sys_role_menu values ('2', '1044');
insert into sys_role_menu values ('2', '1045');
insert into sys_role_menu values ('2', '1046');
insert into sys_role_menu values ('2', '1047');
insert into sys_role_menu values ('2', '1048');
insert into sys_role_menu values ('2', '1049');
insert into sys_role_menu values ('2', '1050');
insert into sys_role_menu values ('2', '1051');
insert into sys_role_menu values ('2', '1052');
insert into sys_role_menu values ('2', '1053');
insert into sys_role_menu values ('2', '1054');
insert into sys_role_menu values ('2', '1055');
insert into sys_role_menu values ('2', '1056');
insert into sys_role_menu values ('2', '1057');
insert into sys_role_menu values ('2', '1058');
insert into sys_role_menu values ('2', '1059');
insert into sys_role_menu values ('2', '1060');
insert into sys_role_menu values ('2', '1061');
insert into sys_role_menu values ('2', '1062');
insert into sys_role_menu values ('2', '1063');
insert into sys_role_menu values ('2', '1064');
insert into sys_role_menu values ('2', '1065');
insert into sys_role_menu values ('2', '1066');
insert into sys_role_menu values ('2', '1067');

insert into sys_role_menu values ('3', '107');
insert into sys_role_menu values ('3', '498');
insert into sys_role_menu values ('3', '499');
insert into sys_role_menu values ('3', '1035');
insert into sys_role_menu values ('3', '1036');
insert into sys_role_menu values ('3', '1037');
insert into sys_role_menu values ('3', '1038');
insert into sys_role_menu values ('4', '107');
insert into sys_role_menu values ('4', '497');
insert into sys_role_menu values ('4', '498');
insert into sys_role_menu values ('4', '499');
insert into sys_role_menu values ('4', '1035');
insert into sys_role_menu values ('4', '1036');
insert into sys_role_menu values ('4', '1037');
insert into sys_role_menu values ('4', '1038');


-- ----------------------------
-- 8、角色和部门关联表  角色1-N部门
-- ----------------------------
drop table if exists sys_role_dept;
create table sys_role_dept (
  role_id   bigint not null comment '角色ID',
  dept_id   bigint not null comment '部门ID',
  primary key(role_id, dept_id)
) engine=innodb comment = '角色和部门关联表';

-- ----------------------------
-- 初始化-角色和部门关联表数据
-- ----------------------------
insert into sys_role_dept values ('2', '100');
insert into sys_role_dept values ('2', '101');
insert into sys_role_dept values ('2', '105');


-- ----------------------------
-- 9、用户与岗位关联表  用户1-N岗位
-- ----------------------------
drop table if exists sys_user_post;
create table sys_user_post
(
  user_id   bigint not null comment '用户ID',
  post_id   bigint not null comment '岗位ID',
  primary key (user_id, post_id)
) engine=innodb comment = '用户与岗位关联表';

-- ----------------------------
-- 初始化-用户与岗位关联表数据
-- ----------------------------
insert into sys_user_post values ('1', '1');


-- ----------------------------
-- 10、操作日志记录
-- ----------------------------
drop table if exists sys_oper_log;
create table sys_oper_log (
  oper_id           bigint      not null auto_increment    comment '日志主键',
  title             varchar(50)     default ''                 comment '模块标题',
  business_type     int          default 0                  comment '业务类型（0其它 1新增 2修改 3删除）',
  method            varchar(100)    default ''                 comment '方法名称',
  request_method    varchar(10)     default ''                 comment '请求方式',
  operator_type     int          default 0                  comment '操作类别（0其它 1后台用户 2手机端用户）',
  oper_name         varchar(50)     default ''                 comment '操作人员',
  dept_name         varchar(50)     default ''                 comment '部门名称',
  oper_url          varchar(255)    default ''                 comment '请求URL',
  oper_ip           varchar(128)    default ''                 comment '主机地址',
  oper_location     varchar(255)    default ''                 comment '操作地点',
  oper_param        varchar(2000)   default ''                 comment '请求参数',
  json_result       varchar(2000)   default ''                 comment '返回参数',
  status            int          default 0                  comment '操作状态（0正常 1异常）',
  error_msg         varchar(2000)   default ''                 comment '错误消息',
  oper_time         datetime                                   comment '操作时间',
  cost_time         bigint      default 0                  comment '消耗时间',
  primary key (oper_id),
  key idx_sys_oper_log_bt (business_type),
  key idx_sys_oper_log_s  (status),
  key idx_sys_oper_log_ot (oper_time)
) engine=innodb auto_increment=100 comment = '操作日志记录';


-- ----------------------------
-- 11、字典类型表
-- ----------------------------
drop table if exists sys_dict_type;
create table sys_dict_type
(
  dict_id          bigint      not null auto_increment    comment '字典主键',
  dict_name        varchar(100)    default ''                 comment '字典名称',
  dict_type        varchar(100)    default ''                 comment '字典类型',
  status           char(1)         default '0'                comment '状态（0正常 1停用）',
  create_by        varchar(64)     default ''                 comment '创建者',
  create_time      datetime                                   comment '创建时间',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新时间',
  remark           varchar(500)    default null               comment '备注',
  primary key (dict_id),
  unique (dict_type)
) engine=innodb auto_increment=100 comment = '字典类型表';

insert into sys_dict_type values(1,  '用户性别', 'sys_user_sex',        '0', 'superadmin', sysdate(), '', null, '用户性别列表');
insert into sys_dict_type values(2,  '菜单状态', 'sys_show_hide',       '0', 'superadmin', sysdate(), '', null, '菜单状态列表');
insert into sys_dict_type values(3,  '系统开关', 'sys_normal_disable',  '0', 'superadmin', sysdate(), '', null, '系统开关列表');
insert into sys_dict_type values(4,  '任务状态', 'sys_job_status',      '0', 'superadmin', sysdate(), '', null, '任务状态列表');
insert into sys_dict_type values(5,  '任务分组', 'sys_job_group',       '0', 'superadmin', sysdate(), '', null, '任务分组列表');
insert into sys_dict_type values(6,  '系统是否', 'sys_yes_no',          '0', 'superadmin', sysdate(), '', null, '系统是否列表');
insert into sys_dict_type values(7,  '栏目类型', 'sys_info_type',       '0', 'superadmin', sysdate(), '', null, '栏目类型列表');
insert into sys_dict_type values(9,  '操作类型', 'sys_oper_type',       '0', 'superadmin', sysdate(), '', null, '操作类型列表');
insert into sys_dict_type values(10, '系统状态', 'sys_common_status',   '0', 'superadmin', sysdate(), '', null, '登录状态列表');
insert into sys_dict_type values(11, '业务模块', 'sys_biz_model',       '0', 'superadmin', sysdate(), '', null, '业务模块列表');
insert into sys_dict_type values(12, '审批类型', 'sys_approval_type',   '0', 'superadmin', sysdate(), '', null, '审批类型列表');


-- ----------------------------
-- 12、字典数据表
-- ----------------------------
drop table if exists sys_dict_data;
create table sys_dict_data
(
  dict_code        bigint      not null auto_increment    comment '字典编码',
  dict_sort        int          default 0                  comment '字典排序',
  dict_label       varchar(100)    default ''                 comment '字典标签',
  dict_value       varchar(100)    default ''                 comment '字典键值',
  dict_type        varchar(100)    default ''                 comment '字典类型',
  css_class        varchar(100)    default null               comment '样式属性（其他样式扩展）',
  list_class       varchar(100)    default null               comment '表格回显样式',
  is_default       char(1)         default 'N'                comment '是否默认（Y是 N否）',
  status           char(1)         default '0'                comment '状态（0正常 1停用）',
  create_by        varchar(64)     default ''                 comment '创建者',
  create_time      datetime                                   comment '创建时间',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新时间',
  remark           varchar(500)    default null               comment '备注',
  primary key (dict_code)
) engine=innodb auto_increment=100 comment = '字典数据表';

insert into sys_dict_data values(1,  1,  '男',       '0',       'sys_user_sex',        '',   '',        'Y', '0', 'superadmin', sysdate(), '', null, '性别男');
insert into sys_dict_data values(2,  2,  '女',       '1',       'sys_user_sex',        '',   '',        'N', '0', 'superadmin', sysdate(), '', null, '性别女');
insert into sys_dict_data values(3,  3,  '未知',     '2',       'sys_user_sex',        '',   '',        'N', '0', 'superadmin', sysdate(), '', null, '性别未知');
insert into sys_dict_data values(4,  1,  '显示',     '0',       'sys_show_hide',       '',   'primary', 'Y', '0', 'superadmin', sysdate(), '', null, '显示菜单');
insert into sys_dict_data values(5,  2,  '隐藏',     '1',       'sys_show_hide',       '',   'danger',  'N', '0', 'superadmin', sysdate(), '', null, '隐藏菜单');
insert into sys_dict_data values(6,  1,  '正常',     '0',       'sys_normal_disable',  '',   'primary', 'Y', '0', 'superadmin', sysdate(), '', null, '正常状态');
insert into sys_dict_data values(7,  2,  '停用',     '1',       'sys_normal_disable',  '',   'danger',  'N', '0', 'superadmin', sysdate(), '', null, '停用状态');
insert into sys_dict_data values(8,  1,  '正常',     '0',       'sys_job_status',      '',   'primary', 'Y', '0', 'superadmin', sysdate(), '', null, '正常状态');
insert into sys_dict_data values(9,  2,  '暂停',     '1',       'sys_job_status',      '',   'danger',  'N', '0', 'superadmin', sysdate(), '', null, '停用状态');
insert into sys_dict_data values(10, 1,  '默认',     'DEFAULT', 'sys_job_group',       '',   '',        'Y', '0', 'superadmin', sysdate(), '', null, '默认分组');
insert into sys_dict_data values(11, 2,  '系统',     'SYSTEM',  'sys_job_group',       '',   '',        'N', '0', 'superadmin', sysdate(), '', null, '系统分组');
insert into sys_dict_data values(12, 1,  '是',       '1',       'sys_yes_no',          '',   'primary', 'Y', '0', 'superadmin', sysdate(), '', null, '系统默认是');
insert into sys_dict_data values(13, 2,  '否',       '0',       'sys_yes_no',          '',   'danger',  'N', '0', 'superadmin', sysdate(), '', null, '系统默认否');
insert into sys_dict_data values(14, 1,  '新闻',     '1',       'sys_info_type',       '',   'danger',  'Y', '0', 'superadmin', sysdate(), '', null, '新闻');
insert into sys_dict_data values(15, 2,  '公告',     '2',       'sys_info_type',       '',   'success', 'N', '0', 'superadmin', sysdate(), '', null, '公告');
insert into sys_dict_data values(18, 1,  '新增',     '1',       'sys_oper_type',       '',   'info',    'N', '0', 'superadmin', sysdate(), '', null, '新增操作');
insert into sys_dict_data values(19, 2,  '修改',     '2',       'sys_oper_type',       '',   'info',    'N', '0', 'superadmin', sysdate(), '', null, '修改操作');
insert into sys_dict_data values(20, 3,  '删除',     '3',       'sys_oper_type',       '',   'danger',  'N', '0', 'superadmin', sysdate(), '', null, '删除操作');
insert into sys_dict_data values(21, 4,  '授权',     '4',       'sys_oper_type',       '',   'primary', 'N', '0', 'superadmin', sysdate(), '', null, '授权操作');
insert into sys_dict_data values(22, 5,  '导出',     '5',       'sys_oper_type',       '',   'warning', 'N', '0', 'superadmin', sysdate(), '', null, '导出操作');
insert into sys_dict_data values(23, 6,  '导入',     '6',       'sys_oper_type',       '',   'warning', 'N', '0', 'superadmin', sysdate(), '', null, '导入操作');
insert into sys_dict_data values(24, 7,  '强退',     '7',       'sys_oper_type',       '',   'danger',  'N', '0', 'superadmin', sysdate(), '', null, '强退操作');
insert into sys_dict_data values(25, 8,  '生成代码', '8',       'sys_oper_type',       '',   'warning', 'N', '0', 'superadmin', sysdate(), '', null, '生成操作');
insert into sys_dict_data values(26, 9,  '清空数据', '9',       'sys_oper_type',       '',   'danger',  'N', '0', 'superadmin', sysdate(), '', null, '清空操作');
insert into sys_dict_data values(27, 1,  '成功',     '0',       'sys_common_status',   '',   'primary', 'N', '0', 'superadmin', sysdate(), '', null, '正常状态');
insert into sys_dict_data values(28, 2,  '失败',     '1',       'sys_common_status',   '',   'danger',  'N', '0', 'superadmin', sysdate(), '', null, '停用状态');
insert into sys_dict_data values(29, 1,  '信息发布',  'SysInfo', 'sys_biz_model',       '',   '', 'N', '0', 'superadmin', sysdate(), '', null, '信息发布');
insert into sys_dict_data values(30, 1,  '提交',     'COMMIT',  'sys_approval_type',   '',   '', 'N', '0', 'superadmin', sysdate(), '', null, '提交');
insert into sys_dict_data values(31, 2,  '审批通过',  'PASS',    'sys_approval_type',   '',   '', 'N', '0', 'superadmin', sysdate(), '', null, '审批通过');
insert into sys_dict_data values(32, 3,  '审批驳回',  'REJECT',  'sys_approval_type',   '',   '', 'N', '0', 'superadmin', sysdate(), '', null, '审批驳回');
insert into sys_dict_data values(33, 4,  '发布撤销',  'WITHDRAW','sys_approval_type',   '',   '', 'N', '0', 'superadmin', sysdate(), '', null, '已发布的信息被撤销');


-- ----------------------------
-- 13、参数配置表
-- ----------------------------
drop table if exists sys_config;
create table sys_config (
  config_id         int          not null auto_increment    comment '参数主键',
  config_name       varchar(100)    default ''                 comment '参数名称',
  config_key        varchar(100)    default ''                 comment '参数键名',
  config_value      varchar(500)    default ''                 comment '参数键值',
  config_type       char(1)         default 'N'                comment '系统内置（Y是 N否）',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default null               comment '备注',
  primary key (config_id)
) engine=innodb auto_increment=100 comment = '参数配置表';

insert into sys_config values(1, '账号自助-验证码开关',           'sys.account.captchaEnabled',    'true',         '1', 'superadmin', sysdate(), '', null, '是否开启验证码功能（true开启，false关闭）');
insert into sys_config values(2, '账号自助-是否开启用户注册功能',  'sys.account.registerUser',      'false',         '1', 'superadmin', sysdate(), '', null, '是否开启注册用户功能（true开启，false关闭）');
insert into sys_config values(3, '短信发送标识码',               'sys.sms.sendModelID',           '123456',        '1', 'superadmin', sysdate(), '', null, '短信发送标识（向领导申请）');
insert into sys_config values(4, '用户管理-账号初始密码修改',      'sys.user.modifyInitPassword',   '0',        	    '1', 'superadmin', sysdate(), '', null, '初始密码修改（0: 不提示修改；1: 提示修改）' );
insert into sys_config values(5, '用户管理-账号密码更新周期',      'sys.user.modifyPasswordPeriod', '0',            '1', 'superadmin', sysdate(), '', null, '单位为天，账号密码每隔多少天进行修改提示。0表示不提示修改' );
insert into sys_config values(6, '用户管理-账号初始密码',         'sys.user.initPassword',         'rs@123!',      '1', 'superadmin', sysdate(), '', null, '初始化密码 rs@123!' );
insert into sys_config values(7, '系统访问地址',         		   'sys.visit.baseurl',             'http://localhost:1024',      '0', 'superadmin', sysdate(), '', null, '系统访问地址' );

-- ----------------------------
-- 14、系统访问记录
-- ----------------------------
drop table if exists sys_logininfor;
create table sys_logininfor (
  info_id        bigint     not null auto_increment   comment '访问ID',
  user_name      varchar(50)    default ''                comment '用户账号',
  ipaddr         varchar(128)   default ''                comment '登录IP地址',
  login_location varchar(255)   default ''                comment '登录地点',
  browser        varchar(50)    default ''                comment '浏览器类型',
  os             varchar(50)    default ''                comment '操作系统',
  status         char(1)        default '0'               comment '登录状态（0成功 1失败）',
  msg            varchar(255)   default ''                comment '提示消息',
  login_time     datetime                                 comment '访问时间',
  primary key (info_id),
  key idx_sys_logininfor_s  (status),
  key idx_sys_logininfor_lt (login_time)
) engine=innodb auto_increment=100 comment = '系统访问记录';


-- ----------------------------
-- 15、定时任务调度表
-- ----------------------------
drop table if exists sys_job;
create table sys_job (
  job_id              bigint    not null auto_increment    comment '任务ID',
  job_name            varchar(64)   default ''                 comment '任务名称',
  job_group           varchar(64)   default 'DEFAULT'          comment '任务组名',
  invoke_target       varchar(500)  not null                   comment '调用目标字符串',
  cron_expression     varchar(255)  default ''                 comment 'cron执行表达式',
  misfire_policy      varchar(20)   default '3'                comment '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  concurrent          char(1)       default '1'                comment '是否并发执行（0允许 1禁止）',
  status              char(1)       default '0'                comment '状态（0正常 1暂停）',
  create_by           varchar(64)   default ''                 comment '创建者',
  create_time         datetime                                 comment '创建时间',
  update_by           varchar(64)   default ''                 comment '更新者',
  update_time         datetime                                 comment '更新时间',
  remark              varchar(500)  default ''                 comment '备注信息',
  primary key (job_id, job_name, job_group)
) engine=innodb auto_increment=100 comment = '定时任务调度表';

insert into sys_job values(1, '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams',        '0/10 * * * * ?', '3', '1', '1', 'superadmin', sysdate(), '', null, '');
insert into sys_job values(2, '系统默认（有参）', 'DEFAULT', 'ryTask.ryParams(\'ry\')',  '0/15 * * * * ?', '3', '1', '1', 'superadmin', sysdate(), '', null, '');
insert into sys_job values(3, '系统默认（多参）', 'DEFAULT', 'ryTask.ryMultipleParams(\'ry\', true, 2000L, 316.50D, 100)',  '0/20 * * * * ?', '3', '1', '1', 'superadmin', sysdate(), '', null, '');


-- ----------------------------
-- 16、定时任务调度日志表
-- ----------------------------
drop table if exists sys_job_log;
create table sys_job_log (
  job_log_id          bigint     not null auto_increment    comment '任务日志ID',
  job_name            varchar(64)    not null                   comment '任务名称',
  job_group           varchar(64)    not null                   comment '任务组名',
  invoke_target       varchar(500)   not null                   comment '调用目标字符串',
  job_message         varchar(500)                              comment '日志信息',
  status              char(1)        default '0'                comment '执行状态（0正常 1失败）',
  exception_info      varchar(2000)  default ''                 comment '异常信息',
  create_time         datetime                                  comment '创建时间',
  primary key (job_log_id)
) engine=innodb comment = '定时任务调度日志表';

-- ----------------------------
-- 17、信息发布表
-- ----------------------------
drop table if exists sys_info;
create table sys_info (
  info_id     		bigint       not null auto_increment   	   comment '信息id',
  title             varchar(128)     default ''                comment '标题',
  sub_title         varchar(64)      default ''                comment '副标题',
  cover				varchar(256)     default ''                comment '封面',
  summary			varchar(500)     default ''                comment '简介',
  content           text             		               	   comment '内容',
  category_id     	bigint       	 not null                  comment '栏目id',
  source			varchar(24)      default ''                comment '来源',
  author			varchar(16)      default ''                comment '作者',
  set_top			tinyint          default 0                 comment '是否置顶',
  anonymous			tinyint          default 0                 comment '是否匿名访问',
  publish_time 	    datetime                                   comment '发布时间',
  commentable		tinyint          default 0                 comment '是否可评论',
  view_count	    int              default 0                 comment '浏览数',
  praise_count	    int              default 0                 comment '点赞数',
  favor_count	    int              default 0                 comment '收藏数',
  status			varchar(2)		 default '0'		       comment '状态（0: 草稿，1: 待发布审批，2:已发布）',
  disabled			tinyint          default 0				   comment '停用（0: 否，1: 是）',
  create_by         varchar(64)      default ''                comment '创建者',
  create_time 	    datetime                                   comment '创建时间',
  update_by         varchar(64)      default ''                comment '更新者',
  update_time       datetime                                   comment '更新时间',
  primary key (info_id)
) engine=innodb auto_increment=1 comment = '信息发布表';

-- 信息属性
drop table if exists sys_info_prop;
create table sys_info_prop ( 
  prop_id           	bigint      	not null auto_increment   comment '信息属性id',
  info_id     			bigint      	not null				  comment '信息发布id',
  category_prop_id 		bigint      	not null				  comment '栏目属性id',
  prop_val           	text             		              	  comment '属性值',
  remark            	varchar(500)  	default ''                comment '备注信息',
  primary key (prop_id)
) engine=innodb auto_increment=1 comment = '信息属性'; 

-- 信息访问范围
drop table if exists sys_info_range;
create table sys_info_range ( 
  range_id          bigint      not null auto_increment   comment '访问范围id',
  info_id     		bigint      not null				  comment '信息发布id',
  dept_id 			bigint      not null				  comment '部门id',
  primary key (range_id)
) engine=innodb auto_increment=1 comment = '信息访问范围'; 


-- 信息栏目
drop table if exists sys_info_category;
create table sys_info_category (
  category_id     	bigint       	not null auto_increment   comment '栏目id',
  parent_id         bigint      	default 0                 comment '父栏目id',
  ancestors         varchar(50)     default ''                comment '祖级列表',
  category_name     varchar(64)     default ''                comment '栏目名称',
  category_key      varchar(32)     		                  comment '栏目键',
  category_type     varchar(20)     default ''                comment '栏目类型',
  order_num         int          	default 0                 comment '显示顺序',
  disabled			tinyint       	default 0				  comment '停用',
  create_by         varchar(64)     default ''                comment '创建者',
  create_time 	    datetime                                  comment '创建时间',
  update_by         varchar(64)     default ''                comment '更新者',
  update_time       datetime                                  comment '更新时间',
  primary key (category_id),
  constraint uni_sys_info_category_key UNIQUE(category_key)
) engine=innodb auto_increment=1 comment = '信息栏目';

-- 信息栏目属性
drop table if exists sys_info_category_prop;
create table sys_info_category_prop (
  prop_id     		bigint       	not null auto_increment   comment '属性id',
  category_id       bigint      	not null                  comment '栏目id',
  prop_name     	varchar(64)     default ''                comment '属性名称',
  prop_type     	varchar(20)     default ''                comment '属性类型',
  prop_type_val     varchar(32)     default ''                comment '属性字典类型值',
  remark            varchar(500)  	default ''                comment '备注信息',
  create_by         varchar(64)     default ''                comment '创建者',
  create_time 	    datetime                                  comment '创建时间',
  update_by         varchar(64)     default ''                comment '更新者',
  update_time       datetime                                  comment '更新时间',
  
  primary key (prop_id)
) engine=innodb auto_increment=1 comment = '信息栏目属性';

-- 信息发布浏览记录（只记录当天信息）
drop table if exists sys_info_view;
create table sys_info_view (
  view_id          bigint      	not null auto_increment   comment '访问id',
  info_id     	   bigint      	not null				  comment '信息发布id',
  view_date		   date        	not null                  comment '访问日期',
  view_ip		   varchar(15)  not null               comment '访问IP',
  
  primary key (view_id)
) engine=innodb auto_increment=1 comment = '信息发布浏览记录';;
-- ----------------------------
-- 18、代码生成业务表
-- ----------------------------
drop table if exists gen_table;
create table gen_table (
  table_id          bigint      not null auto_increment    comment '编号',
  table_name        varchar(200)    default ''                 comment '表名称',
  table_comment     varchar(500)    default ''                 comment '表描述',
  sub_table_name    varchar(64)     default null               comment '关联子表的表名',
  sub_table_fk_name varchar(64)     default null               comment '子表关联的外键名',
  class_name        varchar(100)    default ''                 comment '实体类名称',
  tpl_category      varchar(200)    default 'crud'             comment '使用的模板（crud单表操作 tree树表操作）',
  package_name      varchar(100)                               comment '生成包路径',
  module_name       varchar(30)                                comment '生成模块名',
  business_name     varchar(30)                                comment '生成业务名',
  function_name     varchar(50)                                comment '生成功能名',
  function_author   varchar(50)                                comment '生成功能作者',
  gen_type          char(1)         default '0'                comment '生成代码方式（0zip压缩包 1自定义路径）',
  gen_path          varchar(200)    default '/'                comment '生成路径（不填默认项目路径）',
  gen_cust_adv      tinyint         default 0                  comment '生成自定义高级查询',
  row_field_count   tinyint         default 2                  comment '每行显示字段数量',
  options           varchar(1000)                              comment '其它生成选项',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time 	    datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default null               comment '备注',
  primary key (table_id)
) engine=innodb auto_increment=1 comment = '代码生成业务表';


-- ----------------------------
-- 19、代码生成业务表字段
-- ----------------------------
drop table if exists gen_table_column;
create table gen_table_column (
  column_id         bigint      not null auto_increment    comment '编号',
  table_id          varchar(64)                                comment '归属表编号',
  column_name       varchar(200)                               comment '列名称',
  column_comment    varchar(500)                               comment '列描述',
  column_type       varchar(100)                               comment '列类型',
  column_length     int                               		   comment '列长度（字符类型）',
  java_type         varchar(500)                               comment 'JAVA类型',
  java_field        varchar(200)                               comment 'JAVA字段名',
  is_pk             char(1)                                    comment '是否主键（1是）',
  is_increment      char(1)                                    comment '是否自增（1是）',
  is_required       char(1)                                    comment '是否必填（1是）',
  is_insert         char(1)                                    comment '是否为插入字段（1是）',
  is_edit           char(1)                                    comment '是否编辑字段（1是）',
  is_list           char(1)                                    comment '是否列表字段（1是）',
  is_query          char(1)                                    comment '是否查询字段（1是）',
  query_type        varchar(200)    default 'EQ'               comment '查询方式（等于、不等于、大于、小于、范围）',
  html_type         varchar(200)                               comment '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  dict_type         varchar(200)    default ''                 comment '字典类型',
  
  rel_table_name        varchar(200)                           comment '关联表名称',
  rel_column_name       varchar(200)                           comment '关联列名称',
  rel_column_comment    varchar(500)                           comment '关联列描述',
  rel_column_type       varchar(100)                           comment '关联列类型',
  rel_java_type         varchar(500)                           comment '关联JAVA类型',
  rel_java_field        varchar(200)                           comment '关联JAVA字段名',
  
  sort              int                                        comment '排序',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time 	    datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  primary key (column_id)
) engine=innodb auto_increment=1 comment = '代码生成业务表字段';

-- ----------------------------
-- 20、邮件日志表
-- ----------------------------
drop table if exists sys_email_log;
create table sys_email_log (
  email_id          bigint        not null auto_increment  comment '邮件id',
  send_to           varchar(512)     default ''                comment '接收人',
  send_cc           varchar(512)     default ''                comment '抄送人',
  send_bcc          varchar(512)     default ''                comment '密送人',
  subject           varchar(256)     default ''                comment '邮件主题',
  content           varchar(4096)    default ''                comment '邮件内容',
  send_date         datetime                                   comment '发送时间',
  send_log          varchar(1024)    default ''                comment '发送日志',
  status            char(1)        default '0'                 comment '发送状态（0正常 1失败）',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time 	    datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  primary key (email_id)
) engine=innodb auto_increment=1 comment = '邮件日志表';

-- ----------------------------
-- 21、短信日志表
-- ----------------------------
drop table if exists sys_sms_log;
create table sys_sms_log (
  sms_id            bigint       not null auto_increment   comment '短信id',
  send_to           varchar(512)     default ''                comment '接收人',
  content           varchar(4096)    default ''                comment '短信内容',
  send_date         datetime                                   comment '发送时间',
  send_log          varchar(1024)    default ''                comment '发送日志',
  status            char(1)          default '0'               comment '发送状态（0正常 1失败）',
  create_by         varchar(64)      default ''                comment '创建者',
  create_time 	    datetime                                   comment '创建时间',
  update_by         varchar(64)      default ''                comment '更新者',
  update_time       datetime                                   comment '更新时间',
  primary key (sms_id)
) engine=innodb auto_increment=1 comment = '短信日志表';

-- ----------------------------
-- 22、业务附件表
-- ----------------------------
drop table if exists sys_attachment;
create table sys_attachment (
  attach_id     	bigint       not null auto_increment   comment '附件id',
  file_name         varchar(128)     default ''                comment '文件名称',
  file_url          varchar(256)     default ''                comment '文件路径',
  file_suffix		varchar(24)      default ''                comment '文件后缀',
  file_size         varchar(128)     default ''                comment '文件大小',
  biz_model         varchar(64)      default ''                comment '业务模块',
  create_by         varchar(64)      default ''                comment '创建者',
  create_time 	    datetime                                   comment '创建时间',
  update_by         varchar(64)      default ''                comment '更新者',
  update_time       datetime                                   comment '更新时间',
  primary key (attach_id)
) engine=innodb auto_increment=1 comment = '业务附件表';

-- ----------------------------
-- 23、审批日志表
-- ----------------------------
drop table if exists sys_approval_log;
create table sys_approval_log ( 
  log_id            bigint      not null auto_increment   comment '日志id',
  biz_model			varchar(64)     default ''                comment '业务模块',
  biz_id     		bigint      not null				  comment '业务id',
  comment 		    varchar(256)    default ''                comment '审批意见',
  operate_type 	    varchar(24)     default ''                comment '操作类型',
  operator          varchar(24)     default ''                comment '操作人',
  operate_time 	    datetime                                  comment '操作时间',
  
  primary key (log_id)
) engine=innodb auto_increment=1 comment = '审批日志表';

-- ----------------------------
-- 24、留言评论表
-- ----------------------------
drop table if exists sys_comment;
create table sys_comment ( 
  comment_id        bigint      not null	auto_increment   comment '评论id',
  biz_model			varchar(64)     default ''                	 comment '业务模块',
  biz_id     		bigint      not null				     comment '业务id',
  from_id		    varchar(64)     default ''                   comment '评论者id',
  comment_date 		datetime    	null             		  	 comment '评论时间',
  content			varchar(1024)   default ''				     comment '评论内容',
  img_url			varchar(1024)   default ''				     comment '评论图片',
  thumb_url         varchar(1024)   default ''				     comment '评论缩略图',
  video_url			varchar(1024)   default ''				     comment '评论视频',
  
  primary key (comment_id)
) engine=innodb auto_increment=1 comment = '留言评论表';

-- ----------------------------
-- 25、留言评论回复表
-- ----------------------------
drop table if exists sys_comment_reply;
create table sys_comment_reply ( 
  reply_id       	bigint      not null 	auto_increment   comment '回复id',
  comment_id        bigint      not null    			     comment '父评论id',
  from_id		    varchar(64)     default ''                   comment '评论者id',
  to_id		    	varchar(64)     default ''                   comment '被评论者id',
  comment_date 		datetime    	null             		     comment '评论时间',
  content			varchar(1024)   default ''				     comment '评论内容',
  img_url			varchar(1024)   default ''				     comment '评论图片',
  thumb_url         varchar(1024)   default ''				     comment '评论缩略图',
  video_url			varchar(1024)   default ''				     comment '评论视频',
  
  primary key (reply_id)
) engine=innodb auto_increment=1 comment = '留言评论回复表';

-- ----------------------------
-- 26、留言点赞表
-- ----------------------------
drop table if exists sys_comment_like;
create table sys_comment_like ( 
  like_id        	bigint      not null	auto_increment   comment '点赞id',
  comment_id        bigint      not null    			     comment '评论id',
  user_id		    varchar(64)     default ''                   comment '评论者id',
  like_date 		datetime    	null             		  	 comment '点赞时间',
  
  primary key (like_id)
) engine=innodb auto_increment=1 comment = '留言点赞表';
