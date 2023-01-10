-- ----------------------------
-- 24、留言评论表
-- ----------------------------
drop table if exists sys_comment;
create table sys_comment ( 
  comment_id    bigint(20)      not null	auto_increment   comment '评论id',
  biz_model			varchar(64)     default ''                	 comment '业务模块',
  biz_id     		bigint(20)      not null				     comment '业务id',
  from_id		    varchar(64)     default ''                   comment '评论者id',
  comment_date 	datetime    	  null             		  	 comment '评论时间',
  content			  varchar(1024)   default ''				     comment '评论内容',
  img_url			  varchar(1024)   default ''				     comment '评论图片',
  thumb_url     varchar(1024)   default ''				     comment '评论缩略图',
  video_url			varchar(1024)   default ''				     comment '评论视频',
  
  primary key (comment_id)
) engine=innodb auto_increment=1 comment = '留言评论表';

-- ----------------------------
-- 25、留言评论回复表
-- ----------------------------
drop table if exists sys_comment_reply;
create table sys_comment_reply ( 
  reply_id       	bigint(20)      not null 	auto_increment   comment '回复id',
  comment_id        bigint(20)      not null    			     comment '父评论id',
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
  like_id        	bigint(20)      not null	auto_increment   comment '点赞id',
  comment_id        bigint(20)      not null    			     comment '评论id',
  user_id		    varchar(64)     default ''                   comment '评论者id',
  like_date 		datetime    	null             		  	 comment '点赞时间',
  
  primary key (like_id)
) engine=innodb auto_increment=1 comment = '留言点赞表';