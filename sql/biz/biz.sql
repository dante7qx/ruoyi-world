
drop table if exists t_biz;
create table t_biz (
  biz_id bigint(20) not null auto_increment comment '业务主键ID',
  biz_name varchar(30)default '' comment '业务名称',
  biz_time datetime comment '业务时间',
  attachment varchar(2048) comment '业务附件',
  create_by varchar(64) default '' comment '创建者',
  create_time datetime comment '创建时间',
  update_by varchar(64) default '' comment '更新者',
  update_time datetime comment '更新时间',
  primary key (biz_id)
) engine=innodb auto_increment=200 comment = '业务表';