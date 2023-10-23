-- ----------------------------
-- 流程表单表
-- ----------------------------
drop table if exists `sys_form`;
create table `sys_form` (
  `form_id` bigint not null AUTO_INCREMENT COMMENT '表单主键',
  `form_name` varchar(50) default null COMMENT '表单名称',
  `form_content` longtext COMMENT '表单内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint DEFAULT NULL COMMENT '创建人员',
  `update_by` bigint DEFAULT NULL COMMENT '更新人员',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  primary key (`form_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 default CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='流程表单';

-- ----------------------------
-- 流程实例关联表单
-- ----------------------------
drop table if exists sys_deploy_form;
create table sys_deploy_form (
  id bigint not null AUTO_INCREMENT COMMENT '主键',
  form_id bigint default null COMMENT '表单主键',
  deploy_id varchar(50) default null COMMENT '流程实例主键',
  primary key (id) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 default CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='流程实例关联表单';

-- ----------------------------
-- 流程任务关联表单表
-- ----------------------------
drop table if exists sys_task_form;
create table sys_task_form (
  id bigint not null AUTO_INCREMENT COMMENT '主键',
  form_id bigint default null COMMENT '表单主键',
  task_id varchar(50) default null COMMENT '所属任务',
  primary key (id) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 default CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='流程任务关联表单';

-- ----------------------------
-- 流程组
-- ----------------------------
drop table if exists sys_flow_group;
create table sys_flow_group (
  group_id          bigint      not null auto_increment    comment '流程组id',
  group_name        varchar(30)     default ''                 comment '流程组名称',
  group_key         varchar(30)     default ''                 comment '流程组Key',
  remark        	  varchar(500)    default ''                 comment '备注',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time 	    datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  primary key (group_id)
) engine=innodb auto_increment=1 comment = '流程组表';
alter table sys_flow_group add constraint sys_fg_uk_group_key unique (group_key);


-- ----------------------------
-- 流程组人关联
-- ----------------------------
drop table if exists sys_flow_group_user;
create table sys_flow_group_user (
  id          		  bigint      not null auto_increment    comment 'id',
  group_id          bigint      not null     			   comment '流程组id',
  user_id           bigint      not null     			   comment '用户id',
  remark        	  varchar(500)    default ''                 comment '备注',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time 	    datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  primary key (id)
) engine=innodb auto_increment=1 comment = '流程组人关联表';

-- ----------------------------
-- 流程类型
-- ----------------------------
drop table if exists sys_flow_type;
create table sys_flow_type (
  type_id           bigint      not null auto_increment    comment '类型id',
  type_name        	varchar(32)     default ''                 comment '类型名称',
  flow_group_id     bigint      not null                   comment '流程组id',
  flow_def_key		  varchar(64)     default ''                 comment '流程定义Key',
  order_num			    int		    default 1                  comment '排序',
  remark        	  varchar(200)    default ''                 comment '备注',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time 	    datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  primary key (type_id)
) engine=innodb auto_increment=1 comment = '流程类型表';

-- ----------------------------
-- 流程跟踪表
-- ----------------------------
drop table if exists sys_flow_trace;
create table sys_flow_trace (
  trace_id        	bigint      not null auto_increment    comment 'Id',
  flow_num			    bigint 		not null 				   comment '流程序号',
  flow_date_num	    bigint 		not null 				   comment '流程日期序号',
  flow_type         varchar(30)     not null 				   comment '流程类型',
  biz_uid           varchar(64)     not null                   comment '业务UID',
  biz_desc          varchar(512)    not null                   comment '业务UID',
  commit_user_id 	  bigint      not null                   comment '提交人Id',
  commit_time 	    datetime        null                   	   comment '提交时间',
  proc_inst_id	    varchar(64)     null                       comment '流程实例Id',
  proc_def_id	      varchar(64)     not null                   comment '流程定义Id',
  task_id			      varchar(64) 	default ''                 comment '当前任务Id',
  task_def_id		    varchar(64) 	default ''                 comment '当前任务定义Id',
  task_def_name		  varchar(64) 	default ''                 comment '当前任务定义名称',
  task_def_desc		  varchar(64) 	default ''                 comment '当前任务定义Key',
  flow_status   	  tinyint		default 2                  comment '状态（1 待提交 2 审批中 3 完成）',
  flow_result 		  tinyint 		default 1              	   comment '审批结果（1 通过 0 驳回）',
  first_task 		    tinyint 		default 0 				   comment '第一个任务（1 是 0 否）',
  
  primary key (trace_id),
  key idx_sys_flow_trace_bu (biz_uid),
  key idx_sys_flow_trace_ft (flow_type),
  key idx_sys_flow_trace_ct (commit_time)
) engine=innodb auto_increment=1 comment = '流程跟踪表';

-- 流程序号表
drop table if exists sys_flow_seq;
create table sys_flow_seq (
  id bigint not null auto_increment comment 'Id',
  seq_num bigint not null comment '自然序号',
  date_num bigint not null comment '日期序号',
  primary key (id)
) engine=innodb auto_increment=1 comment = '流程序号表';

-- ----------------------------
-- 流程记录表
-- ----------------------------
drop table if exists sys_flow_record;
create table sys_flow_record (
  record_id        	bigint      not null auto_increment    comment 'Id',
  biz_uid           varchar(64) not null                   comment '业务UID',
  user_id 			    bigint      null                       comment '处理用户Id',
  group_id 			    bigint      null                   	   comment '处理组Id',
  start_time 		    datetime 		not null 				   comment '开始时间',
  end_time 			    datetime  		null 				       comment '结束时间',
  task_id			      varchar(64) 	default ''                 comment '处理任务Id',
  task_def_id		    varchar(64) 	default ''                 comment '处理任务定义Id',
  task_def_name		  varchar(64) 	default ''                 comment '处理任务定义名称',
  task_def_desc		  varchar(64) 	default ''                 comment '处理任务定义Key',
  flow_result 		  tinyint 		null              	   	   comment '审批结果（1 通过 0 驳回）',
  comment 			    varchar(200)    default ''                 comment '审批意见',
  attachment 		    varchar(2048)   null 					   comment '审批附件',
 
  primary key (record_id),
  key idx_sys_flow_record_bu (biz_uid),
  key idx_sys_flow_record_user (user_id),
  key idx_sys_flow_record_st (start_time)
) engine=innodb auto_increment=1 comment = '流程记录表';

-- ----------------------------
-- 流程任务转办表
-- ----------------------------
drop table if exists sys_flow_assign;
create table sys_flow_assign (
  flow_assign_id    bigint      not null auto_increment    comment 'id',
  proc_inst_id      varchar(64)     not null                   comment '流程实例Id',
  task_id			      varchar(64)     not null                   comment '流程任务Id',
  keep_todo			    tinyint      default 0                  comment '保留我的待办',
  comment        	  varchar(100)    default ''                 comment '转办说明',
  owner_id          bigint      not null                   comment '任务所有者Id',
  owner_name        varchar(32)     not null                   comment '任务所有者姓名',
  owner_dept        varchar(32)     default ''                 comment '任务所有者部门名称',
  assignee_id       bigint      not null                   comment '任务接收者Id',
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
  demo_id bigint not null auto_increment comment '业务主键ID',
  user_id bigint comment '申请人Id',
  uid varchar(64) not null comment '业务UID',
  start_time datetime comment '开始时间',
  end_time datetime comment '结束时间',
  appl_reason varchar(50) comment '申请原因',
  appl_attachment varchar(2048) comment '补充材料',
  create_by varchar(64) default '' comment '创建者',
  create_time datetime comment '创建时间',
  update_by varchar(64) default '' comment '更新者',
  update_time datetime comment '更新时间',
  primary key (demo_id)
) engine=innodb auto_increment=1 comment = '业务流程示例';


-- 初始化数据
-- 菜单
insert into sys_menu( parent_id, menu_name, order_num, path, is_frame, is_cache, menu_type, visible, status, icon, create_by, create_time)
values(0, '流程管理', 200, 'flowable', '1', '0', 'M', '0', '0', 'cascader', 'superadmin', sysdate());

select @parentId := LAST_INSERT_ID();

insert into sys_menu( parent_id, menu_name, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, icon, perms, create_by, create_time )
values
(@parentId, '流程定义', 1, 'flowDef', 'flowable/definition/index', '', '1', '0', 'C', '0', '0', 'job', '', 'superadmin', sysdate()),
(@parentId, '流程组', 2, 'flowGroup', 'flowable/group/index', '', '1', '0', 'C', '0', '0', 'form', 'flowable:group:list', 'superadmin', sysdate()),
(@parentId, '流程类型', 3, 'flowType', 'flowable/type/index', '', '1', '0', 'C', '0', '0', 'guide', 'flowable:type:list', 'superadmin', sysdate()),
(@parentId, '表单配置', 4, 'flowForm', 'flowable/task/form/index', '', '1', '0', 'C', '0', '0', 'form', 'flowable:form:list', 'superadmin', sysdate());

select @groupId := (select menu_id  from sys_menu where menu_name ='流程组');
insert into sys_menu( parent_id, menu_name, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, icon, perms, create_by, create_time )
values
(@groupId, '新增', '1', '', '', '', 1, 0, 'F', '0', '0', 'flowable:group:add', '#', 'superadmin', sysdate()),
(@groupId, '编辑', '2', '', '', '', 1, 0, 'F', '0', '0', 'flowable:group:edit', '#', 'superadmin', sysdate()),
(@groupId, '删除', '3', '', '', '', 1, 0, 'F', '0', '0', 'flowable:group:remove', '#', 'superadmin', sysdate()),
(@groupId, '查询', '4', '', '', '', 1, 0, 'F', '0', '0', 'flowable:group:query', '#', 'superadmin', sysdate());

select @typeId := (select menu_id  from sys_menu where menu_name ='流程类型');
insert into sys_menu( parent_id, menu_name, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, icon, perms, create_by, create_time )
values
(@typeId, '新增', '1', '', '', '', 1, 0, 'F', '0', '0', 'flowable:type:add', '#', 'superadmin', sysdate()),
(@typeId, '编辑', '2', '', '', '', 1, 0, 'F', '0', '0', 'flowable:type:edit', '#', 'superadmin', sysdate()),
(@typeId, '删除', '3', '', '', '', 1, 0, 'F', '0', '0', 'flowable:type:remove', '#', 'superadmin', sysdate()),
(@typeId, '查询', '4', '', '', '', 1, 0, 'F', '0', '0', 'flowable:type:query', '#', 'superadmin', sysdate());

select @formId := (select menu_id  from sys_menu where menu_name ='表单配置');
insert into sys_menu( parent_id, menu_name, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, icon, perms, create_by, create_time )
values
(@formId, '新增', '1', '', '', '', 1, 0, 'F', '0', '0', 'flowable:form:add', '#', 'superadmin', sysdate()),
(@formId, '编辑', '2', '', '', '', 1, 0, 'F', '0', '0', 'flowable:form:edit', '#', 'superadmin', sysdate()),
(@formId, '删除', '3', '', '', '', 1, 0, 'F', '0', '0', 'flowable:form:remove', '#', 'superadmin', sysdate()),
(@formId, '查询', '4', '', '', '', 1, 0, 'F', '0', '0', 'flowable:form:query', '#', 'superadmin', sysdate());

insert into sys_menu( parent_id, menu_name, order_num, path, is_frame, is_cache, menu_type, visible, status, icon, create_by, create_time)
values(0, '任务管理', 210, 'flowabletask', '1', '0', 'M', '0', '0', 'dict', 'superadmin', sysdate());

select @pid := LAST_INSERT_ID();

insert into sys_menu( parent_id, menu_name, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, icon, create_by, create_time )
values
(@pid, '我的任务', 1, 'myFlowTask', 'flowable/task/mytask', '', '1', '0', 'C', '0', '0', 'guide', 'superadmin', sysdate()),
(@pid, '待办任务', 2, 'flowTodo', 'flowable/task/todo', '', '1', '0', 'C', '0', '0', 'cascader', 'superadmin', sysdate()),
(@pid, '已办任务', 3, 'flowDone', 'flowable/task/done', '', '1', '0', 'C', '0', '0', 'time-range', 'superadmin', sysdate()),
(@pid, '任务监控', 4, 'FlowMonitor', 'flowable/task/monitor', '', '1', '0', 'C', '0', '0', 'online', 'superadmin', sysdate());

-- 数据字典
insert into sys_dict_type( dict_name, dict_type, status, create_by, create_time) 
values 
('流程分类', 'sys_process_category', '0', 'superadmin', sysdate()),
('流程类型', 'sys_process_type', '0', 'superadmin', sysdate()),
('流程状态', 'sys_process_status', '0', 'superadmin', sysdate()),
('流程结果', 'sys_process_result', '0', 'superadmin', sysdate());

insert into sys_dict_data( dict_sort, dict_label, dict_value, dict_type, list_class, status, create_by, create_time)
values
( 1, '考勤', 'KQ', 'sys_process_category', 'default', '0', 'superadmin', sysdate()),
( 1, '请假', 'leave', 'sys_process_type', 'default', '0', 'superadmin', sysdate()),
( 1, '待提交', '1', 'sys_process_status', 'default', '0', 'superadmin', sysdate()),
( 2, '审批中', '2', 'sys_process_status', 'default', '0', 'superadmin', sysdate()),
( 3, '完成', '3', 'sys_process_status', 'default', '0', 'superadmin', sysdate()),
( 1, '通过', '1', 'sys_process_result', 'default', '0', 'superadmin', sysdate()),
( 2, '退回', '0', 'sys_process_result', 'default', '0', 'superadmin', sysdate());

