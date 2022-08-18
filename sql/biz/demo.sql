drop table if exists t_demo;
create table t_demo (
  demo_id bigint(20) not null auto_increment comment '业务主键ID',
  demo_name varchar(30)default '' comment '业务名称',
  demo_time datetime comment '业务时间',
  demo_image varchar(2048) comment '业务图片',
  attachment varchar(2048) comment '业务附件',
  demo_content text comment '业务名称',
  create_by varchar(64) default '' comment '创建者',
  create_time datetime comment '创建时间',
  update_by varchar(64) default '' comment '更新者',
  update_time datetime comment '更新时间',
  primary key (demo_id)
) engine=innodb auto_increment=200 comment = '业务表';