-- 菜单
insert into sys_menu( parent_id, menu_name, order_num, path, is_frame, is_cache, menu_type, visible, status, icon, create_by, create_time)
values(0, '流程管理', 200, 'flowable', '1', '0', 'M', '0', '0', 'cascader', 'fqyczadmin', sysdate());

select @parentId := LAST_INSERT_ID();

insert into sys_menu( parent_id, menu_name, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, icon, perms, create_by, create_time )
values
(@parentId, '流程定义', 1, 'flowDef', 'flowable/definition/index', '', '1', '0', 'C', '0', '0', 'job', '', 'fqyczadmin', sysdate()),
(@parentId, '流程组', 2, 'flowGroup', 'flowable/group/index', '', '1', '0', 'C', '0', '0', 'form', 'flowable:group:list', 'fqyczadmin', sysdate()),
(@parentId, '流程类型', 3, 'flowType', 'flowable/type/index', '', '1', '0', 'C', '0', '0', 'guide', 'flowable:type:list', 'fqyczadmin', sysdate()),
(@parentId, '表单配置', 4, 'flowForm', 'flowable/task/form/index', '', '1', '0', 'C', '0', '0', 'form', 'flowable:form:list', 'fqyczadmin', sysdate());

select @groupId := (select menu_id  from sys_menu where menu_name ='流程组');
insert into sys_menu( parent_id, menu_name, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, icon, perms, create_by, create_time )
values
(@groupId, '新增', '1', '', '', '', 1, 0, 'F', '0', '0', 'flowable:group:add', '#', 'fqyczadmin', sysdate()),
(@groupId, '编辑', '2', '', '', '', 1, 0, 'F', '0', '0', 'flowable:group:edit', '#', 'fqyczadmin', sysdate()),
(@groupId, '删除', '3', '', '', '', 1, 0, 'F', '0', '0', 'flowable:group:remove', '#', 'fqyczadmin', sysdate()),
(@groupId, '查询', '4', '', '', '', 1, 0, 'F', '0', '0', 'flowable:group:query', '#', 'fqyczadmin', sysdate());

select @typeId := (select menu_id  from sys_menu where menu_name ='流程类型');
insert into sys_menu( parent_id, menu_name, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, icon, perms, create_by, create_time )
values
(@typeId, '新增', '1', '', '', '', 1, 0, 'F', '0', '0', 'flowable:type:add', '#', 'fqyczadmin', sysdate()),
(@typeId, '编辑', '2', '', '', '', 1, 0, 'F', '0', '0', 'flowable:type:edit', '#', 'fqyczadmin', sysdate()),
(@typeId, '删除', '3', '', '', '', 1, 0, 'F', '0', '0', 'flowable:type:remove', '#', 'fqyczadmin', sysdate()),
(@typeId, '查询', '4', '', '', '', 1, 0, 'F', '0', '0', 'flowable:type:query', '#', 'fqyczadmin', sysdate());

select @formId := (select menu_id  from sys_menu where menu_name ='表单配置');
insert into sys_menu( parent_id, menu_name, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, icon, perms, create_by, create_time )
values
(@formId, '新增', '1', '', '', '', 1, 0, 'F', '0', '0', 'flowable:form:add', '#', 'fqyczadmin', sysdate()),
(@formId, '编辑', '2', '', '', '', 1, 0, 'F', '0', '0', 'flowable:form:edit', '#', 'fqyczadmin', sysdate()),
(@formId, '删除', '3', '', '', '', 1, 0, 'F', '0', '0', 'flowable:form:remove', '#', 'fqyczadmin', sysdate()),
(@formId, '查询', '4', '', '', '', 1, 0, 'F', '0', '0', 'flowable:form:query', '#', 'fqyczadmin', sysdate());

insert into sys_menu( parent_id, menu_name, order_num, path, is_frame, is_cache, menu_type, visible, status, icon, create_by, create_time)
values(0, '任务管理', 210, 'flowabletask', '1', '0', 'M', '0', '0', 'dict', 'fqyczadmin', sysdate());

select @pid := LAST_INSERT_ID();

insert into sys_menu( parent_id, menu_name, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, icon, create_by, create_time )
values
(@pid, '我的任务', 1, 'myFlowTask', 'flowable/task/mytask', '', '1', '0', 'C', '0', '0', 'guide', 'fqyczadmin', sysdate()),
(@pid, '待办任务', 2, 'flowTodo', 'flowable/task/todo', '', '1', '0', 'C', '0', '0', 'cascader', 'fqyczadmin', sysdate()),
(@pid, '已办任务', 3, 'flowDone', 'flowable/task/done', '', '1', '0', 'C', '0', '0', 'time-range', 'fqyczadmin', sysdate()),
(@pid, '任务监控', 4, 'FlowMonitor', 'flowable/task/monitor', '', '1', '0', 'C', '0', '0', 'online', 'fqyczadmin', sysdate());

-- 数据字典
insert into sys_dict_type( dict_name, dict_type, status, create_by, create_time) 
values 
('流程分类', 'sys_process_category', '0', 'fqyczadmin', sysdate()),
('流程类型', 'sys_process_type', '0', 'fqyczadmin', sysdate()),
('流程状态', 'sys_process_status', '0', 'fqyczadmin', sysdate()),
('流程结果', 'sys_process_result', '0', 'fqyczadmin', sysdate());

insert into sys_dict_data( dict_sort, dict_label, dict_value, dict_type, list_class, status, create_by, create_time)
values
( 1, '考勤', 'KQ', 'sys_process_category', 'default', '0', 'fqyczadmin', sysdate()),
( 1, '请假', 'leave', 'sys_process_type', 'default', '0', 'fqyczadmin', sysdate()),
( 1, '待提交', '1', 'sys_process_status', 'default', '0', 'fqyczadmin', sysdate()),
( 2, '审批中', '2', 'sys_process_status', 'default', '0', 'fqyczadmin', sysdate()),
( 3, '完成', '3', 'sys_process_status', 'default', '0', 'fqyczadmin', sysdate()),
( 1, '通过', '1', 'sys_process_result', 'default', '0', 'fqyczadmin', sysdate()),
( 2, '退回', '0', 'sys_process_result', 'default', '0', 'fqyczadmin', sysdate());
