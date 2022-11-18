-- ----------------------------
-- 流程表单表
-- ----------------------------
drop table if exists `sys_form`;
create table `sys_form` (
  `form_id` bigint(20) not null AUTO_INCREMENT COMMENT '表单主键',
  `form_name` varchar(50) default null COMMENT '表单名称',
  `form_content` longtext COMMENT '表单内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人员',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人员',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  primary key (`form_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 default CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='流程表单';

-- ----------------------------
-- 流程实例关联表单
-- ----------------------------
drop table if exists sys_deploy_form;
create table sys_deploy_form (
  id bigint(20) not null AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) default null COMMENT '表单主键',
  deploy_id varchar(50) default null COMMENT '流程实例主键',
  primary key (id) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 default CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='流程实例关联表单';

-- ----------------------------
-- 流程任务关联表单表
-- ----------------------------
drop table if exists sys_task_form;
create table sys_task_form (
  id bigint(20) not null AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) default null COMMENT '表单主键',
  task_id varchar(50) default null COMMENT '所属任务',
  primary key (id) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 default CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='流程任务关联表单';

-- ----------------------------
-- 流程审批组
-- ----------------------------
drop table if exists sys_flow_group;
create table sys_flow_group (
  group_id          bigint(20)      not null auto_increment    comment '审批组id',
  group_name        varchar(30)     default ''                 comment '审批组名称',
  remark        	varchar(500)    default ''                 comment '备注',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time 	    datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  primary key (group_id)
) engine=innodb auto_increment=1 comment = '流程审批组表';

-- ----------------------------
-- 流程审批组人关联
-- ----------------------------
drop table if exists sys_flow_group_user;
create table sys_flow_group_user (
  id          		bigint(20)      not null auto_increment    comment 'id',
  group_id          bigint(20)      not null     			   comment '审批组id',
  user_id           bigint(20)      not null     			   comment '用户id',
  remark        	varchar(500)    default ''                 comment '备注',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time 	    datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  primary key (id)
) engine=innodb auto_increment=1 comment = '流程审批组人关联表';


-- ----------------------------
-- 业务流程监控表
-- ----------------------------
drop table if exists sys_flow_biz_monitor;
create table sys_flow_biz_monitor (
  monitor_id        bigint(20)      not null auto_increment    comment 'Id',
  biz_uid           varchar(64)     not null                   comment '业务UID',
  proc_inst_id	    varchar(64)     not null                   comment '流程实例Id',
  status        	varchar(24)     default ''                 comment '当前状态',
  passed        	tinyint(1)      default 1                  comment '审核通过（0: 驳回 1: 通过）',
  finished			tinyint(1)      default 0                  comment '流程结束（0: 未结束 1: 结束）',
  commited        	tinyint(1)      default 0                  comment '待提交申请',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time 	    datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  primary key (monitor_id)
) engine=innodb auto_increment=1 comment = '业务流程监控表';

-- ----------------------------
-- 流程任务转办表
-- ----------------------------
drop table if exists sys_flow_assign;
create table sys_flow_assign (
  flow_assign_id    bigint(20)      not null auto_increment    comment 'id',
  proc_inst_id      varchar(64)     not null                   comment '流程实例Id',
  task_id			varchar(64)     not null                   comment '流程任务Id',
  keep_todo			tinyint(1)      default 0                  comment '保留我的待办',
  comment        	varchar(100)    default ''                 comment '转办说明',
  owner_id          bigint(20)      not null                   comment '任务所有者Id',
  owner_name        varchar(32)     not null                   comment '任务所有者姓名',
  owner_dept        varchar(32)     default ''                 comment '任务所有者部门名称',
  assignee_id       bigint(20)      not null                   comment '任务接收者Id',
  assignee_name     varchar(32)     not null                   comment '任务接收者姓名',
  assignee_dept     varchar(32)     default ''                 comment '任务接收者部门名称',
  create_time 	    datetime                                   comment '创建时间',
  primary key (flow_assign_id)
) engine=innodb auto_increment=1 comment = '流程任务转办表';

-- ----------------------------
-- 业务流程示例
-- ----------------------------
drop table if exists t_flow_demo;
create table t_flow_demo (
  demo_id bigint(20) not null auto_increment comment '业务主键ID',
  leave_user_id bigint(20) comment '请假申请人Id',
  uid varchar(64) not null comment '业务UID',
  start_time datetime comment '开始时间',
  end_time datetime comment '结束时间',
  leave_reason varchar(50) comment '请假原因',
  create_by varchar(64) default '' comment '创建者',
  create_time datetime comment '创建时间',
  update_by varchar(64) default '' comment '更新者',
  update_time datetime comment '更新时间',
  primary key (demo_id)
) engine=innodb auto_increment=1 comment = '业务流程示例';

insert into sys_user values(2,  100, 'test', '测试用户', '00', 'test@test.com', '15888888999', '1', '', 'chaOivnBHUglDuv6lCRq2fOgzqR90NkiF8MAntOl8pZOjcHKShZ3qIBrN/JLsZnXZqghL34uJP2PglQplVDGyQlsrwV3LTFHqJe0PKAzJVFJNfun7OXXHr5ZdkPPRigi5dYNF9toQ0rlfNQCNjML1WeL/NnwvhW6eG2FTAU4LX4=', '0', '0', '127.0.0.1', sysdate(), 'fqyczadmin', sysdate(), '', null, '管理员');
insert into sys_user values(3,  100, 'spuser1', '审批用户1', '00', 'spuser1@test.com', '15888888977', '1', '', 'chaOivnBHUglDuv6lCRq2fOgzqR90NkiF8MAntOl8pZOjcHKShZ3qIBrN/JLsZnXZqghL34uJP2PglQplVDGyQlsrwV3LTFHqJe0PKAzJVFJNfun7OXXHr5ZdkPPRigi5dYNF9toQ0rlfNQCNjML1WeL/NnwvhW6eG2FTAU4LX4=', '0', '0', '127.0.0.1', sysdate(), 'fqyczadmin', sysdate(), '', null, '管理员');
insert into sys_user values(4,  100, 'spuser2', '审批用户2', '00', 'spuser2@test.com', '15888888966', '1', '', 'chaOivnBHUglDuv6lCRq2fOgzqR90NkiF8MAntOl8pZOjcHKShZ3qIBrN/JLsZnXZqghL34uJP2PglQplVDGyQlsrwV3LTFHqJe0PKAzJVFJNfun7OXXHr5ZdkPPRigi5dYNF9toQ0rlfNQCNjML1WeL/NnwvhW6eG2FTAU4LX4=', '0', '0', '127.0.0.1', sysdate(), 'fqyczadmin', sysdate(), '', null, '管理员');

insert into sys_user_role values ('2', '2');
insert into sys_user_role values ('3', '2');
insert into sys_user_role values ('4', '2');

insert into sys_menu values ('20', '流程管理', '0', '200', 'flowable', NULL, '', 1, 0, 'M', '0', '0', '', 'cascader', 'fqyczadmin', sysdate(), '', NULL, '流程管理目录');
insert into sys_menu values ('200', '流程定义', '20', '1', 'definition', 'flowable/definition/index', '', 1, 0, 'C', '0', '0', '', 'job', 'fqyczadmin', sysdate(), '', NULL, '流程定义菜单');
insert into sys_menu values ('201', '任务管理', '20', '2', 'task', NULL, '', 1, 0, 'M', '0', '0', '', 'dict', 'fqyczadmin', sysdate(), '', NULL, '任务管理目录');
insert into sys_menu values ('202', '待办任务', '201', '1', 'todo', 'flowable/task/todo', '', 1, 0, 'C', '0', '0', '', 'cascader', 'fqyczadmin', sysdate(), '', NULL, '待办任务菜单');
insert into sys_menu values ('203', '已办任务', '201', '2', 'done', 'flowable/task/done', '', 1, 0, 'C', '0', '0', '', 'time-range', 'fqyczadmin', sysdate(), '', NULL, '已办任务菜单');
insert into sys_menu values ('204', '办结任务', '201', '3', 'finish', 'flowable/task/finish', '', 1, 0, 'C', '0', '0', NULL, 'lock', 'fqyczadmin', sysdate(), '', NULL, '办结任务菜单');
insert into sys_menu values ('205', '表单配置', '20', '3', 'form', 'flowable/task/form/index', '', 1, 0, 'C', '0', '0', 'flowable:form:list', 'form', 'fqyczadmin', sysdate(), '', NULL, '表单配置菜单');
insert into sys_menu values ('206', '审批人管理', '20', '4', 'flowgroup', 'flowable/group/index', '', 1, 0, 'C', '0', '0', 'flowable:group:list', 'peoples', 'fqyczadmin', sysdate(), '', NULL, '审批人菜单');
insert into sys_menu values ('2000', '查询', '205', '1', '', NULL, '', 1, 0, 'F', '0', '0', 'flowable:form:query', '#', 'fqyczadmin', sysdate(), '', NULL, '');
insert into sys_menu values ('2001', '新增', '205', '2', '', NULL, '', 1, 0, 'F', '0', '0', 'flowable:form:add', '#', 'fqyczadmin', sysdate(), '', NULL, '');
insert into sys_menu values ('2002', '编辑', '205', '3', '', NULL, '', 1, 0, 'F', '0', '0', 'flowable:form:edit', '#', 'fqyczadmin', sysdate(), '', NULL, '');
insert into sys_menu values ('2003', '删除', '205', '4', '', NULL, '', 1, 0, 'F', '0', '0', 'flowable:form:remove', '#', 'fqyczadmin', sysdate(), '', NULL, '');
insert into sys_menu values ('2007', '新增', '206', '1', '', NULL, '', 1, 0, 'F', '0', '0', 'flowable:group:add', '#', 'fqyczadmin', sysdate(), '', NULL, '');
insert into sys_menu values ('2008', '编辑', '206', '2', '', NULL, '', 1, 0, 'F', '0', '0', 'flowable:group:edit', '#', 'fqyczadmin', sysdate(), '', NULL, '');
insert into sys_menu values ('2009', '删除', '206', '3', '', NULL, '', 1, 0, 'F', '0', '0', 'flowable:group:remove', '#', 'fqyczadmin', sysdate(), '', NULL, '');
insert into sys_menu values ('2010', '查询', '206', '4', '', NULL, '', 1, 0, 'F', '0', '0', 'flowable:group:query', '#', 'fqyczadmin', sysdate(), '', NULL, '');
insert into sys_menu values ('207', '请假示例', '20', '5', 'leavedemo', 'flowable/demo/index', '', 1, 0, 'C', '0', '0', '', 'job', 'fqyczadmin', sysdate(), '', NULL, '请假示例菜单');

insert into sys_role_menu values ('2', '20');
insert into sys_role_menu values ('2', '200');
insert into sys_role_menu values ('2', '201');
insert into sys_role_menu values ('2', '202');
insert into sys_role_menu values ('2', '203');
insert into sys_role_menu values ('2', '204');
insert into sys_role_menu values ('2', '205');
insert into sys_role_menu values ('2', '206');
insert into sys_role_menu values ('2', '207');
insert into sys_role_menu values ('2', '2000');
insert into sys_role_menu values ('2', '2001');
insert into sys_role_menu values ('2', '2002');
insert into sys_role_menu values ('2', '2003');
insert into sys_role_menu values ('2', '2007');
insert into sys_role_menu values ('2', '2008');
insert into sys_role_menu values ('2', '2009');
insert into sys_role_menu values ('2', '2010');

insert into sys_dict_type values (100, '流程类型', 'sys_process_category', '0', 'fqyczadmin', sysdate(), '', null, '流程类型列表');
insert into sys_dict_data values(100, 1, '请假',  '请假', 'sys_process_category', '','', 'N', '0', 'fqyczadmin', sysdate(), '', null, '请假流程');

insert into sys_flow_group values (1, '一级审批组', '一级审批组', 'fqyczadmin', sysdate(), '', null);
insert into sys_flow_group values (2, '二级审批组', '二级审批组', 'fqyczadmin', sysdate(), '', null);
insert into sys_flow_group_user values (1, 1, 3, '', 'fqyczadmin', sysdate(), '', null);
insert into sys_flow_group_user values (2, 2, 4, '', 'fqyczadmin', sysdate(), '', null);
commit;
