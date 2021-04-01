drop table if exists users;

create table users (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `created_time` datetime NOT NULL COMMENT '创建日期',
  `modify_time` datetime NOT NULL COMMENT '最后修改日期',
	`version` bigint(20) NOT NULL COMMENT 'version',
  `del_flag` int not null default 0 COMMENT '删除标记，1删除，0未删除',

  `username` VARCHAR(72) NOT NULL COMMENT '用户账号',
  `name` VARCHAR(72) COMMENT '名称',
  `password` VARCHAR(72) NOT NULL COMMENT '用户密码',
  `type` VARCHAR(36) NOT NULL COMMENT '用户类型,admin,user',
  `mobile` VARCHAR(36) COMMENT '手机号码',
  `email` VARCHAR(72) COMMENT '邮箱地址',
  `roles` VARCHAR(128) COMMENT '角色',
  `status` int not null COMMENT '状态,0禁用，1启用',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `i_users_un` (`username`)
) COMMENT='用户';
