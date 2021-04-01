drop table if exists channel_plugin_setting;

create table channel_plugin_setting (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `created_time` datetime NOT NULL COMMENT '创建日期',
  `modify_time` datetime NOT NULL COMMENT '最后修改日期',
	`version` bigint(20) NOT NULL COMMENT 'version',
  `del_flag` int not null default 0 COMMENT '删除标记，1删除，0未删除',

	`channel_plugin_id` VARCHAR(72) NOT NULL COMMENT '插件ID',
  `name` VARCHAR(72) not null COMMENT '名称',
  `domain` int not null COMMENT '领域',
  `template_id` VARCHAR(72) COMMENT '消息模版ID',
  `remark` VARCHAR(4000) COMMENT '描述',
  `enabled` bit(1) not null default b'1' COMMENT '是否启用',
  `attributes` text not null COMMENT '属性配置',
  `timer` varchar(36) not null COMMENT '计时器',
 
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `i_channel_plugin_setting_pid` (`channel_plugin_id`)
) COMMENT='频道插件设置';


drop table if exists cust_channel_plugin_setting;

create table cust_channel_plugin_setting (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `created_time` datetime NOT NULL COMMENT '创建日期',
  `modify_time` datetime NOT NULL COMMENT '最后修改日期',
	`version` bigint(20) NOT NULL COMMENT 'version',
  `del_flag` int not null default 0 COMMENT '删除标记，1删除，0未删除',

	`username` VARCHAR(72) NOT NULL COMMENT '用户账号',
	`channel_plugin_id` VARCHAR(72) NOT NULL COMMENT '插件ID',
  `name` VARCHAR(72) not null COMMENT '频道名称',
  `domain` int not null COMMENT '领域',
  `template_id` VARCHAR(72) COMMENT '消息模版ID',
  `enabled` bit(1) not null default b'1' COMMENT '是否启用',
  `attributes` text not null COMMENT '属性配置',
  `timer` varchar(36) not null COMMENT '计时器',
 
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `i_cust_channel_plugin_setting_un` (`channel_plugin_id`, `username`)
) COMMENT='用户频道插件设置';


drop table if exists news_plugin_setting;

create table news_plugin_setting (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `created_time` datetime NOT NULL COMMENT '创建日期',
  `modify_time` datetime NOT NULL COMMENT '最后修改日期',
	`version` bigint(20) NOT NULL COMMENT 'version',
  `del_flag` int not null default 0 COMMENT '删除标记，1删除，0未删除',

	`news_plugin_id` VARCHAR(72) NOT NULL COMMENT '插件ID',
	`channel_plugin_id` VARCHAR(72) NOT NULL COMMENT '插件ID',
 
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `i_news_plugin_setting_un` (`news_plugin_id`, `channel_plugin_id`)
) COMMENT='新闻频道插件设置';