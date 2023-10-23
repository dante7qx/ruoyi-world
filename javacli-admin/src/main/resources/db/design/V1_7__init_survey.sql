
-- 问卷调查表
drop table if exists svy_survey;
create table svy_survey (
  survey_id bigint not null auto_increment comment '主键Id',
  survey_type varchar(20) not null default '' comment '调查类型（问卷、考试）',
  title varchar(128) not null default '' comment '标题',
  sub_title varchar(128) not null default '' comment '副标题',
  status tinyint not null default 1 comment '状态（1.设计中 2.进行中 3.结束）',
  remark varchar(500) not null default '' comment '备注',
  create_by varchar(64) default '' comment '创建者',
  create_time datetime comment '创建时间',
  update_by varchar(64) default '' comment '更新者',
  update_time datetime comment '更新时间',
  primary key (survey_id)
) engine=innodb comment = '问卷调查表';

-- 问卷调查属性表
drop table if exists svy_survey_prop;
create table svy_survey_prop (
  prop_id bigint not null auto_increment comment '主键Id',
  survey_id bigint not null comment '问卷调查Id',
  start_time datetime comment '开始时间',
  end_time datetime comment '结束时间',
  capacity int comment '调查完成份数',
  one_page_ques tinyint int comment '一页一题',
  passing_score float comment '及格分数',
  random_subject tinyint int comment '随机选题',
  subject_num int int comment '题目数量',
  create_by varchar(64) default '' comment '创建者',
  create_time datetime comment '创建时间',
  update_by varchar(64) default '' comment '更新者',
  update_time datetime comment '更新时间',
  primary key (prop_id)
) engine=innodb comment = '问卷调查属性表';


-- 问卷调查权限表
drop table if exists svy_survey_acl;
create table svy_survey_acl (
  acl_id bigint not null auto_increment comment '主键Id',
  survey_id bigint not null comment '问卷调查Id',
  acl_type varchar(20) not null default '' comment '权限类型',
  primary key (acl_id)
) engine=innodb comment = '问卷调查权限表';


-- 问卷调查权限用户表
drop table if exists svy_survey_acl_user
create table svy_survey_acl_user (
  acl_user_id  bigint not null auto_increment comment '主键Id',
  acl_id 	   bigint not null comment '权限Id',
  user_id 	   bigint not null comment '用户Id',
  primary key (acl_user_id)
) engine=innodb comment = '问卷调查权限用户表';


