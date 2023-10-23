-- ----------------------------
-- 内部消息队列异常消息
-- ----------------------------
drop table if exists sys_inner_mq_exception;
create table sys_inner_mq_exception ( 
  id        		bigint(20)      not null	auto_increment   comment 'id',
  consumer_class    varchar(64)     not null    			     comment '消费处理类',
  biz_msg		    varchar(4096)   default ''                   comment '业务消息',
  create_time 	    datetime                                     comment '创建时间',
  
  primary key (id)
) engine=innodb auto_increment=1 comment = '内部消息队列异常消息';


insert into sys_menu values('121',  '消息队列补偿', '2',   '8', 'innermqList','monitor/mq/inner',  '', 1, 0, 'C', '0', '0', 'monitor:innermq:list','row', 'superadmin', sysdate(), '', null, '消息队列补偿菜单');