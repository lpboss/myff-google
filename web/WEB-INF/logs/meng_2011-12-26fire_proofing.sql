/*
MySQL Data Transfer
Source Host: localhost
Source Database: fire_proofing
Target Host: localhost
Target Database: fire_proofing
Date: 2011/12/26 15:47:22
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for fire_alarms
-- ----------------------------
CREATE TABLE `fire_alarms` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ptz_id` int(11) DEFAULT NULL COMMENT '云台ID',
  `action_date` datetime DEFAULT NULL COMMENT '火警时间',
  `ptz_angle_x` float DEFAULT NULL COMMENT '水平角度',
  `ptz_angle_y` float DEFAULT NULL COMMENT '垂直角度',
  `heat_max` int(11) DEFAULT NULL COMMENT ' 最高热值',
  `heat_min` int(11) DEFAULT NULL,
  `heat_avg` int(11) DEFAULT NULL COMMENT '平均热值',
  `description` text,
  `user_id` int(11) DEFAULT NULL,
  `deal_date` datetime DEFAULT NULL COMMENT '处理时间',
  `is_alarming` tinyint(4) DEFAULT '1' COMMENT '是否正在进行火警广播（起火时播放火警音频）',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `is_locked` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=235 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ignore_areas
-- ----------------------------
CREATE TABLE `ignore_areas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ptz_id` int(11) NOT NULL COMMENT '云台的编号',
  `ptz_angel_x_from` float(6,0) DEFAULT '0' COMMENT '火警时云台的水平角度(始)',
  `ptz_angel_x_to` float DEFAULT '0' COMMENT '火警时云台的水平角度(末)',
  `ptz_angel_y_from` float(6,0) DEFAULT '0' COMMENT '火警时云台的Y角度(始)',
  `ptz_angel_y_to` float DEFAULT '0' COMMENT '火警时云台的Y角度(末)',
  `ccd_area` smallint(6) DEFAULT '0' COMMENT '热成像起火面积值',
  `heat_max` smallint(6) DEFAULT '0' COMMENT '最大热值,如果超出此热值,依然报警',
  `begin_date` datetime DEFAULT NULL COMMENT '火警时间范围',
  `end_date` datetime DEFAULT NULL COMMENT '火警时间范围',
  `is_locked` tinyint(4) DEFAULT '0',
  `updated_at` datetime NOT NULL,
  `created_at` datetime NOT NULL,
  `version` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for privilege_details
-- ----------------------------
CREATE TABLE `privilege_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `privilege_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `sys_controller_id` varchar(255) NOT NULL,
  `sys_action_id` varchar(255) NOT NULL,
  `params` varchar(255) DEFAULT NULL,
  `sub_type` varchar(255) DEFAULT NULL,
  `is_url` int(11) DEFAULT NULL,
  `description` text,
  `sort_id` int(11) DEFAULT '0',
  `is_locked` tinyint(4) DEFAULT '0',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `version` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for privileges
-- ----------------------------
CREATE TABLE `privileges` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `image` varchar(255) DEFAULT NULL,
  `sys_controller_id` int(11) DEFAULT NULL,
  `sys_action_id` int(11) DEFAULT NULL,
  `params` varchar(255) DEFAULT NULL,
  `level` int(11) DEFAULT '0',
  `is_menu` int(11) DEFAULT '0',
  `is_admin` int(11) DEFAULT '0',
  `sub_type` varchar(255) DEFAULT NULL,
  `leaf` varchar(255) NOT NULL DEFAULT 'true',
  `description` text,
  `parent_id` int(11) DEFAULT '0',
  `sort_id` int(11) DEFAULT '0',
  `is_locked` tinyint(4) DEFAULT '0',
  `version` int(11) DEFAULT '0',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ptzs
-- ----------------------------
CREATE TABLE `ptzs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `controll_url` varchar(255) DEFAULT NULL COMMENT '编码器IP',
  `pelcod_command_url` varchar(255) DEFAULT NULL COMMENT '通过串口,发pelcod的ip',
  `visible_camera_url` varchar(255) DEFAULT NULL COMMENT '可见光摄像机地址,模拟请参考controll_url',
  `visible_rtsp_url` varchar(255) DEFAULT NULL COMMENT '可见光RTSP流',
  `visual_user` varchar(255) DEFAULT NULL COMMENT '可见光摄像机的用户名',
  `visual_password` varchar(255) DEFAULT NULL COMMENT '可见光摄像机的密码',
  `play_type` varchar(255) DEFAULT NULL COMMENT '定义前台用海康自己有播放还是用MediaPlay',
  `infrared_rtsp_url` varchar(255) DEFAULT NULL COMMENT '红外RTSP流',
  `infrared_camera_url` varchar(255) DEFAULT '' COMMENT '红外摄像机地址',
  `infrared_circuit_url` varchar(255) DEFAULT NULL COMMENT '红外电路板设备地址',
  `north_migration` float(7,0) DEFAULT '0' COMMENT '摄像机0角度与正北的偏移。顺时针为正。',
  `gis_map_url` varchar(255) DEFAULT NULL COMMENT '地图文件存放位置',
  `visual_angle_x` float DEFAULT '0' COMMENT '红外视角X',
  `visual_angle_y` float DEFAULT '0' COMMENT '红外视角Y',
  `infrared_pixel_x` smallint(6) DEFAULT '0' COMMENT '红外摄像机X方向像素',
  `infrared_pixel_y` smallint(6) DEFAULT '0' COMMENT '红外摄像机Y方向像素',
  `brand_type` varchar(255) DEFAULT NULL COMMENT '品牌与型号',
  `cruise_step` smallint(6) DEFAULT '5' COMMENT '云台巡航步长',
  `cruise_angle_y_step` float(4,0) DEFAULT '10' COMMENT '巡航上扬角度步长',
  `shift_step` smallint(6) DEFAULT '10' COMMENT '云台非巡航状态下默认移动步长',
  `cruise_from_to` varchar(10) DEFAULT 'LR' COMMENT '巡航设置左右边界时，转动方向',
  `cruise_right_limit` smallint(6) DEFAULT '0' COMMENT '巡航右边界',
  `cruise_left_limit` smallint(6) DEFAULT '0' COMMENT '巡航左边界',
  `cruise_up_limit` smallint(6) DEFAULT '90' COMMENT '最大上仰角度',
  `cruise_down_limit` smallint(6) DEFAULT '0' COMMENT '巡航时最大俯角',
  `alarm_heat_value` smallint(6) DEFAULT '30000' COMMENT '报警警戒热值',
  `is_alarm` tinyint(4) DEFAULT '0' COMMENT '当前是否报警',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `is_locked` tinyint(4) DEFAULT NULL COMMENT '启用，停用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for role_ptzs
-- ----------------------------
CREATE TABLE `role_ptzs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `ptz_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for roles
-- ----------------------------
CREATE TABLE `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `ptz_id` int(11) DEFAULT NULL COMMENT '角色默认云台ID',
  `is_locked` tinyint(4) DEFAULT '1',
  `version` int(11) DEFAULT '0',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for roles_privilege_details
-- ----------------------------
CREATE TABLE `roles_privilege_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  `module_id` int(11) NOT NULL,
  `menu_id` int(11) NOT NULL,
  `privilege_detail_id` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_locked` tinyint(4) DEFAULT '1',
  `version` int(11) DEFAULT '0',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_actions
-- ----------------------------
CREATE TABLE `sys_actions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sys_controller_id` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_locked` tinyint(4) DEFAULT '0',
  `version` int(11) DEFAULT '0',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_controllers
-- ----------------------------
CREATE TABLE `sys_controllers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_locked` tinyint(4) DEFAULT '0',
  `version` int(11) DEFAULT '0',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for users
-- ----------------------------
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `department_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `login_id` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `ab` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `fax` varchar(255) DEFAULT NULL,
  `identity_card` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `description` text,
  `is_locked` tinyint(4) DEFAULT '0',
  `version` int(11) DEFAULT '0',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `fire_alarms` VALUES ('25', '1', '2011-11-30 18:38:38', '38', '0', '5831', null, null, null, '1', '2011-11-30 18:38:38', '1', '2011-11-30 15:55:48', '2011-11-30 15:55:48', null, '0');
INSERT INTO `fire_alarms` VALUES ('26', '1', '2011-11-30 18:38:41', '38', '0', '5590', null, null, null, '1', '2011-11-30 18:38:38', '1', '2011-11-30 15:56:13', '2011-11-30 15:56:13', null, '0');
INSERT INTO `fire_alarms` VALUES ('27', '1', '2011-11-30 18:38:44', '50', '38', '7402', null, null, null, '1', '2011-11-30 18:38:38', '1', '2011-11-30 16:50:21', '2011-11-30 16:50:21', null, '0');
INSERT INTO `fire_alarms` VALUES ('28', '1', '2011-12-01 11:20:17', '50', '38', '2530', null, null, null, '1', '2011-12-01 11:20:17', '1', '2011-12-01 11:20:17', '2011-12-01 11:20:17', null, '0');
INSERT INTO `fire_alarms` VALUES ('29', '1', '2011-12-01 11:29:06', '50', '38', '1354', null, null, null, '1', '2011-12-01 11:29:06', '1', '2011-12-01 11:29:06', '2011-12-01 11:29:06', null, '0');
INSERT INTO `fire_alarms` VALUES ('30', '1', '2011-12-01 11:44:01', '50', '38', '3588', null, null, null, '1', '2011-12-01 11:44:01', '1', '2011-12-01 11:44:01', '2011-12-01 11:44:01', null, '0');
INSERT INTO `fire_alarms` VALUES ('31', '1', '2011-12-01 11:45:00', '50', '38', '2495', null, null, null, '1', '2011-12-01 11:45:00', '1', '2011-12-01 11:45:00', '2011-12-01 11:45:00', null, '0');
INSERT INTO `fire_alarms` VALUES ('32', '1', '2011-12-01 15:26:42', '50', '38', '1311', null, null, null, '1', '2011-12-01 15:26:42', '1', '2011-12-01 15:26:42', '2011-12-01 15:26:42', null, '0');
INSERT INTO `fire_alarms` VALUES ('33', '1', '2011-12-01 15:27:11', '50', '38', '1794', null, null, null, '1', '2011-12-01 15:27:11', '1', '2011-12-01 15:27:11', '2011-12-01 15:27:11', null, '0');
INSERT INTO `fire_alarms` VALUES ('34', '1', '2011-12-01 15:27:39', '50', '38', '1798', null, null, null, '1', '2011-12-01 15:27:39', '1', '2011-12-01 15:27:39', '2011-12-01 15:27:39', null, '0');
INSERT INTO `fire_alarms` VALUES ('35', '1', '2011-12-01 15:30:34', '50', '38', '1541', null, null, null, '1', '2011-12-01 15:30:34', '1', '2011-12-01 15:30:34', '2011-12-01 15:30:34', null, '0');
INSERT INTO `fire_alarms` VALUES ('36', '1', '2011-12-01 15:36:45', '50', '38', '1542', null, null, null, '1', '2011-12-01 15:36:45', '1', '2011-12-01 15:36:45', '2011-12-01 15:36:45', null, '0');
INSERT INTO `fire_alarms` VALUES ('37', '1', '2011-12-01 15:37:12', '50', '38', '1303', null, null, null, '1', '2011-12-01 15:37:12', '1', '2011-12-01 15:37:12', '2011-12-01 15:37:12', null, '0');
INSERT INTO `fire_alarms` VALUES ('38', '1', '2011-12-01 15:38:12', '50', '38', '1340', null, null, null, '1', '2011-12-01 15:38:12', '1', '2011-12-01 15:38:12', '2011-12-01 15:38:12', null, '0');
INSERT INTO `fire_alarms` VALUES ('39', '1', '2011-12-01 17:18:00', '50', '38', '3467', null, null, null, null, '2011-12-01 17:18:00', '1', '2011-12-01 17:18:00', '2011-12-01 17:18:00', null, '0');
INSERT INTO `fire_alarms` VALUES ('40', '1', '2011-12-01 17:21:52', '50', '38', '2481', null, null, null, null, '2011-12-01 17:21:52', '1', '2011-12-01 17:21:52', '2011-12-01 17:21:52', null, '0');
INSERT INTO `fire_alarms` VALUES ('41', '1', '2011-12-01 17:26:33', '50', '38', '3626', null, null, null, null, '2011-12-01 17:26:33', '1', '2011-12-01 17:26:33', '2011-12-01 17:26:33', null, '0');
INSERT INTO `fire_alarms` VALUES ('42', '1', '2011-12-02 11:19:40', '50', '38', '2519', null, null, null, null, '2011-12-02 11:19:40', '1', '2011-12-02 11:19:40', '2011-12-02 11:19:40', null, '0');
INSERT INTO `fire_alarms` VALUES ('43', '1', '2011-12-02 13:15:43', '50', '38', '2433', null, null, null, null, '2011-12-02 13:15:43', '1', '2011-12-02 13:15:43', '2011-12-02 13:15:43', null, '0');
INSERT INTO `fire_alarms` VALUES ('44', '1', '2011-12-02 13:20:09', '50', '38', '2435', null, null, null, null, '2011-12-02 13:20:09', '1', '2011-12-02 13:20:09', '2011-12-02 13:20:09', null, '0');
INSERT INTO `fire_alarms` VALUES ('45', '1', '2011-12-02 16:28:18', '50', '38', '5864', null, null, null, null, '2011-12-02 16:28:18', '1', '2011-12-02 16:28:18', '2011-12-02 16:28:18', null, '0');
INSERT INTO `fire_alarms` VALUES ('46', '1', '2011-12-02 16:30:12', '50', '38', '6936', null, null, null, null, '2011-12-02 16:30:12', '1', '2011-12-02 16:30:12', '2011-12-02 16:30:12', null, '0');
INSERT INTO `fire_alarms` VALUES ('47', '1', '2011-12-02 16:59:43', '50', '38', '5802', null, null, null, null, '2011-12-02 16:59:43', '1', '2011-12-02 16:59:43', '2011-12-02 16:59:43', null, '0');
INSERT INTO `fire_alarms` VALUES ('48', '1', '2011-12-02 17:02:06', '50', '38', '6711', null, null, null, null, '2011-12-02 17:02:06', '1', '2011-12-02 17:02:06', '2011-12-02 17:02:06', null, '0');
INSERT INTO `fire_alarms` VALUES ('49', '1', '2011-12-02 17:03:47', '50', '38', '1305', null, null, null, null, '2011-12-02 17:03:47', '1', '2011-12-02 17:03:47', '2011-12-02 17:03:47', null, '0');
INSERT INTO `fire_alarms` VALUES ('50', '1', '2011-12-02 17:06:07', '50', '38', '7146', null, null, null, null, '2011-12-02 17:06:07', '1', '2011-12-02 17:06:07', '2011-12-02 17:06:07', null, '0');
INSERT INTO `fire_alarms` VALUES ('51', '1', '2011-12-02 17:12:46', '6', '4.6', '2746', null, null, null, null, '2011-12-02 17:12:46', '1', '2011-12-02 17:12:46', '2011-12-02 17:12:46', null, '0');
INSERT INTO `fire_alarms` VALUES ('52', '1', '2011-12-02 17:13:19', '6', '4.6', '2074', null, null, null, null, '2011-12-02 17:13:19', '1', '2011-12-02 17:13:19', '2011-12-02 17:13:19', null, '0');
INSERT INTO `fire_alarms` VALUES ('53', '1', '2011-12-05 09:45:40', '6', '4.6', '6055', null, null, null, null, '2011-12-05 09:45:40', '1', '2011-12-05 09:45:40', '2011-12-05 09:45:40', null, '0');
INSERT INTO `fire_alarms` VALUES ('54', '1', '2011-12-05 09:47:13', '6', '4.6', '5369', null, null, null, null, '2011-12-05 09:47:13', '1', '2011-12-05 09:47:13', '2011-12-05 09:47:13', null, '0');
INSERT INTO `fire_alarms` VALUES ('55', '1', '2011-12-05 09:47:58', '6', '4.6', '5505', null, null, null, null, '2011-12-05 09:47:58', '1', '2011-12-05 09:47:58', '2011-12-05 09:47:58', null, '0');
INSERT INTO `fire_alarms` VALUES ('56', '1', '2011-12-05 09:48:00', '6', '4.6', '5488', null, null, null, null, '2011-12-05 09:48:00', '1', '2011-12-05 09:48:00', '2011-12-05 09:48:00', null, '0');
INSERT INTO `fire_alarms` VALUES ('57', '1', '2011-12-05 09:48:04', '6', '4.6', '6379', null, null, null, null, '2011-12-05 09:48:04', '1', '2011-12-05 09:48:04', '2011-12-05 09:48:04', null, '0');
INSERT INTO `fire_alarms` VALUES ('58', '1', '2011-12-05 13:33:36', '6', '4.6', '2700', null, null, null, null, '2011-12-05 13:33:36', '1', '2011-12-05 13:33:36', '2011-12-05 13:33:36', null, '0');
INSERT INTO `fire_alarms` VALUES ('59', '1', '2011-12-05 13:33:48', '6', '4.6', '2861', null, null, null, null, '2011-12-05 13:33:48', '1', '2011-12-05 13:33:48', '2011-12-05 13:33:48', null, '0');
INSERT INTO `fire_alarms` VALUES ('60', '1', '2011-12-05 13:39:11', '6', '4.6', '2978', null, null, null, null, '2011-12-05 13:39:11', '1', '2011-12-05 13:39:11', '2011-12-05 13:39:11', null, '0');
INSERT INTO `fire_alarms` VALUES ('61', '1', '2011-12-05 13:41:58', '6', '4.6', '2989', null, null, null, null, '2011-12-05 13:41:58', '1', '2011-12-05 13:41:58', '2011-12-05 13:41:58', null, '0');
INSERT INTO `fire_alarms` VALUES ('62', '1', '2011-12-05 13:43:12', '6', '4.6', '2719', null, null, null, null, '2011-12-05 13:43:12', '1', '2011-12-05 13:43:12', '2011-12-05 13:43:12', null, '0');
INSERT INTO `fire_alarms` VALUES ('63', '1', '2011-12-05 13:43:37', '6', '4.6', '2750', null, null, null, null, '2011-12-05 13:43:37', '1', '2011-12-05 13:43:37', '2011-12-05 13:43:37', null, '0');
INSERT INTO `fire_alarms` VALUES ('64', '1', '2011-12-05 13:50:53', '6', '4.6', '3070', null, null, null, null, '2011-12-05 13:50:53', '1', '2011-12-05 13:50:53', '2011-12-05 13:50:53', null, '0');
INSERT INTO `fire_alarms` VALUES ('65', '1', '2011-12-05 13:56:13', '6', '4.6', '2756', null, null, null, null, '2011-12-05 13:56:13', '1', '2011-12-05 13:56:13', '2011-12-05 13:56:13', null, '0');
INSERT INTO `fire_alarms` VALUES ('66', '1', '2011-12-05 13:59:47', '6', '4.6', '2550', null, null, null, null, '2011-12-05 13:59:47', '1', '2011-12-05 13:59:47', '2011-12-05 13:59:47', null, '0');
INSERT INTO `fire_alarms` VALUES ('67', '1', '2011-12-05 14:03:02', '6', '4.6', '3171', null, null, null, null, '2011-12-05 14:03:02', '1', '2011-12-05 14:03:02', '2011-12-05 14:03:02', null, '0');
INSERT INTO `fire_alarms` VALUES ('68', '1', '2011-12-05 14:09:08', '6', '4.6', '1803', null, null, null, null, '2011-12-05 14:09:08', '1', '2011-12-05 14:09:08', '2011-12-05 14:09:08', null, '0');
INSERT INTO `fire_alarms` VALUES ('69', '1', '2011-12-05 16:38:43', '6', '4.6', '1707', null, null, null, null, '2011-12-05 16:38:43', '1', '2011-12-05 16:38:43', '2011-12-05 16:38:43', null, '0');
INSERT INTO `fire_alarms` VALUES ('70', '1', '2011-12-05 16:39:54', '6', '4.6', '6075', null, null, null, null, '2011-12-05 16:39:54', '1', '2011-12-05 16:39:54', '2011-12-05 16:39:54', null, '0');
INSERT INTO `fire_alarms` VALUES ('71', '1', '2011-12-05 16:41:06', '6', '4.6', '5363', null, null, null, null, '2011-12-05 16:41:06', '1', '2011-12-05 16:41:06', '2011-12-05 16:41:06', null, '0');
INSERT INTO `fire_alarms` VALUES ('72', '1', '2011-12-05 16:41:13', '6', '4.6', '5328', null, null, null, null, '2011-12-05 16:41:13', '1', '2011-12-05 16:41:13', '2011-12-05 16:41:13', null, '0');
INSERT INTO `fire_alarms` VALUES ('73', '1', '2011-12-05 16:41:15', '6', '4.6', '5337', null, null, null, null, '2011-12-05 16:41:15', '1', '2011-12-05 16:41:15', '2011-12-05 16:41:15', null, '0');
INSERT INTO `fire_alarms` VALUES ('74', '1', '2011-12-05 16:41:23', '6', '4.6', '5320', null, null, null, null, '2011-12-05 16:41:23', '1', '2011-12-05 16:41:23', '2011-12-05 16:41:23', null, '0');
INSERT INTO `fire_alarms` VALUES ('75', '1', '2011-12-05 16:41:25', '6', '4.6', '5351', null, null, null, null, '2011-12-05 16:41:25', '1', '2011-12-05 16:41:25', '2011-12-05 16:41:25', null, '0');
INSERT INTO `fire_alarms` VALUES ('76', '1', '2011-12-05 16:41:28', '6', '4.6', '5374', null, null, null, null, '2011-12-05 16:41:28', '1', '2011-12-05 16:41:28', '2011-12-05 16:41:28', null, '0');
INSERT INTO `fire_alarms` VALUES ('77', '1', '2011-12-05 16:41:30', '6', '4.6', '5330', null, null, null, null, '2011-12-05 16:41:30', '1', '2011-12-05 16:41:30', '2011-12-05 16:41:30', null, '0');
INSERT INTO `fire_alarms` VALUES ('78', '1', '2011-12-05 16:44:39', '6', '4.6', '6096', null, null, null, null, '2011-12-05 16:44:39', '1', '2011-12-05 16:44:39', '2011-12-05 16:44:39', null, '0');
INSERT INTO `fire_alarms` VALUES ('79', '1', '2011-12-05 16:52:29', '6', '4.6', '5505', null, null, null, null, '2011-12-05 16:52:29', '1', '2011-12-05 16:52:29', '2011-12-05 16:52:29', null, '0');
INSERT INTO `fire_alarms` VALUES ('80', '1', '2011-12-05 16:52:37', '6', '4.6', '5510', null, null, null, null, '2011-12-05 16:52:37', '1', '2011-12-05 16:52:37', '2011-12-05 16:52:37', null, '0');
INSERT INTO `fire_alarms` VALUES ('81', '1', '2011-12-05 16:52:45', '6', '4.6', '6647', null, null, null, null, '2011-12-05 16:52:45', '1', '2011-12-05 16:52:45', '2011-12-05 16:52:45', null, '0');
INSERT INTO `fire_alarms` VALUES ('82', '1', '2011-12-05 16:55:45', '6', '4.6', '5504', null, null, null, null, '2011-12-05 16:55:45', '1', '2011-12-05 16:55:45', '2011-12-05 16:55:45', null, '0');
INSERT INTO `fire_alarms` VALUES ('83', '1', '2011-12-05 16:55:48', '6', '4.6', '5509', null, null, null, null, '2011-12-05 16:55:48', '1', '2011-12-05 16:55:48', '2011-12-05 16:55:48', null, '0');
INSERT INTO `fire_alarms` VALUES ('84', '1', '2011-12-05 16:58:22', '6', '4.6', '6421', null, null, null, null, '2011-12-05 16:58:22', '1', '2011-12-05 16:58:22', '2011-12-05 16:58:22', null, '0');
INSERT INTO `fire_alarms` VALUES ('85', '1', '2011-12-05 16:58:43', '6', '4.6', '3132', null, null, null, null, '2011-12-05 16:58:43', '1', '2011-12-05 16:58:43', '2011-12-05 16:58:43', null, '0');
INSERT INTO `fire_alarms` VALUES ('86', '1', '2011-12-05 16:58:57', '6', '4.6', '5466', null, null, null, null, '2011-12-05 16:58:57', '1', '2011-12-05 16:58:57', '2011-12-05 16:58:57', null, '0');
INSERT INTO `fire_alarms` VALUES ('87', '1', '2011-12-05 17:03:28', '6', '4.6', '6609', null, null, null, null, '2011-12-05 17:03:28', '1', '2011-12-05 17:03:28', '2011-12-05 17:03:28', null, '0');
INSERT INTO `fire_alarms` VALUES ('88', '1', '2011-12-05 17:19:01', '6', '4.6', '5562', null, null, null, null, '2011-12-05 17:19:01', '1', '2011-12-05 17:19:01', '2011-12-05 17:19:01', null, '0');
INSERT INTO `fire_alarms` VALUES ('89', '1', '2011-12-05 17:24:23', '6', '4.6', '2960', null, null, null, null, '2011-12-05 17:24:23', '1', '2011-12-05 17:24:23', '2011-12-05 17:24:23', null, '0');
INSERT INTO `fire_alarms` VALUES ('90', '1', '2011-12-06 16:51:30', '6', '4.6', '2310', null, null, null, null, '2011-12-06 16:51:30', '1', '2011-12-06 16:51:30', '2011-12-06 16:51:30', null, '0');
INSERT INTO `fire_alarms` VALUES ('91', '1', '2011-12-06 16:56:23', '6', '4.6', '5954', null, null, null, null, '2011-12-06 16:56:23', '1', '2011-12-06 16:56:23', '2011-12-06 16:56:23', null, '0');
INSERT INTO `fire_alarms` VALUES ('92', '1', '2011-12-06 16:56:28', '6', '4.6', '5968', null, null, null, null, '2011-12-06 16:56:28', '1', '2011-12-06 16:56:28', '2011-12-06 16:56:28', null, '0');
INSERT INTO `fire_alarms` VALUES ('93', '1', '2011-12-06 16:56:43', '6', '4.6', '6052', null, null, null, null, '2011-12-06 16:56:43', '1', '2011-12-06 16:56:43', '2011-12-06 16:56:43', null, '0');
INSERT INTO `fire_alarms` VALUES ('94', '1', '2011-12-06 16:57:13', '6', '4.6', '6716', null, null, null, null, '2011-12-06 16:57:13', '1', '2011-12-06 16:57:13', '2011-12-06 16:57:13', null, '0');
INSERT INTO `fire_alarms` VALUES ('95', '1', '2011-12-06 16:57:52', '6', '4.6', '6174', null, null, null, null, '2011-12-06 16:57:52', '1', '2011-12-06 16:57:52', '2011-12-06 16:57:52', null, '0');
INSERT INTO `fire_alarms` VALUES ('96', '1', '2011-12-06 17:03:52', '6', '4.6', '3742', null, null, null, null, '2011-12-06 17:03:52', '1', '2011-12-06 17:03:52', '2011-12-06 17:03:52', null, '0');
INSERT INTO `fire_alarms` VALUES ('97', '1', '2011-12-06 17:04:23', '6', '4.6', '4002', null, null, null, null, '2011-12-06 17:04:23', '1', '2011-12-06 17:04:23', '2011-12-06 17:04:23', null, '0');
INSERT INTO `fire_alarms` VALUES ('98', '1', '2011-12-06 17:05:09', '6', '4.6', '5837', null, null, null, null, '2011-12-06 17:05:09', '1', '2011-12-06 17:05:09', '2011-12-06 17:05:09', null, '0');
INSERT INTO `fire_alarms` VALUES ('99', '1', '2011-12-07 10:41:30', '6', '4.6', '4833', null, null, null, null, '2011-12-07 10:41:30', '1', '2011-12-07 10:41:30', '2011-12-07 10:41:30', null, '0');
INSERT INTO `fire_alarms` VALUES ('100', '1', '2011-12-07 13:21:45', '6', '4.6', '4971', null, null, null, null, '2011-12-07 13:21:45', '1', '2011-12-07 13:21:45', '2011-12-07 13:21:45', null, '0');
INSERT INTO `fire_alarms` VALUES ('101', '1', '2011-12-07 13:29:50', '6', '4.6', '5292', null, null, null, null, '2011-12-07 13:29:50', '1', '2011-12-07 13:29:50', '2011-12-07 13:29:50', null, '0');
INSERT INTO `fire_alarms` VALUES ('102', '1', '2011-12-07 13:46:34', '6', '4.6', '5382', null, null, null, null, '2011-12-07 13:46:34', '1', '2011-12-07 13:46:34', '2011-12-07 13:46:34', null, '0');
INSERT INTO `fire_alarms` VALUES ('103', '1', '2011-12-07 14:43:34', '6', '4.6', '2137', null, null, null, null, '2011-12-07 14:43:34', '1', '2011-12-07 14:43:34', '2011-12-07 14:43:34', null, '0');
INSERT INTO `fire_alarms` VALUES ('104', '1', '2011-12-07 16:22:43', '6', '4.6', '5091', null, null, null, null, '2011-12-07 16:22:43', '1', '2011-12-07 16:22:43', '2011-12-07 16:22:43', null, '0');
INSERT INTO `fire_alarms` VALUES ('105', '1', '2011-12-07 16:22:59', '6', '4.6', '3178', null, null, null, null, '2011-12-07 16:22:59', '1', '2011-12-07 16:22:59', '2011-12-07 16:22:59', null, '0');
INSERT INTO `fire_alarms` VALUES ('106', '1', '2011-12-07 16:23:42', '6', '4.6', '6141', null, null, null, null, '2011-12-07 16:23:42', '1', '2011-12-07 16:23:42', '2011-12-07 16:23:42', null, '0');
INSERT INTO `fire_alarms` VALUES ('107', '1', '2011-12-07 16:24:23', '6', '4.6', '5421', null, null, null, null, '2011-12-07 16:24:23', '1', '2011-12-07 16:24:23', '2011-12-07 16:24:23', null, '0');
INSERT INTO `fire_alarms` VALUES ('108', '1', '2011-12-07 16:24:52', '6', '4.6', '1923', null, null, null, null, '2011-12-07 16:24:52', '1', '2011-12-07 16:24:52', '2011-12-07 16:24:52', null, '0');
INSERT INTO `fire_alarms` VALUES ('109', '1', '2011-12-07 16:25:45', '6', '4.6', '6037', null, null, null, null, '2011-12-07 16:25:45', '1', '2011-12-07 16:25:45', '2011-12-07 16:25:45', null, '0');
INSERT INTO `fire_alarms` VALUES ('110', '1', '2011-12-07 16:27:37', '6', '4.6', '2998', null, null, null, null, '2011-12-07 16:27:37', '1', '2011-12-07 16:27:37', '2011-12-07 16:27:37', null, '0');
INSERT INTO `fire_alarms` VALUES ('111', '1', '2011-12-07 16:27:53', '6', '4.6', '5538', null, null, null, null, '2011-12-07 16:27:53', '1', '2011-12-07 16:27:53', '2011-12-07 16:27:53', null, '0');
INSERT INTO `fire_alarms` VALUES ('112', '1', '2011-12-07 16:28:35', '6', '4.6', '5598', null, null, null, null, '2011-12-07 16:28:35', '1', '2011-12-07 16:28:35', '2011-12-07 16:28:35', null, '0');
INSERT INTO `fire_alarms` VALUES ('113', '1', '2011-12-07 16:49:00', '6', '4.6', '1981', null, null, null, null, '2011-12-07 16:49:00', '1', '2011-12-07 16:49:00', '2011-12-07 16:49:00', null, '0');
INSERT INTO `fire_alarms` VALUES ('114', '1', '2011-12-08 09:42:03', '6', '4.6', '2550', null, null, null, null, '2011-12-08 09:42:03', '1', '2011-12-08 09:42:03', '2011-12-08 09:42:03', null, '0');
INSERT INTO `fire_alarms` VALUES ('115', '1', '2011-12-08 11:08:14', '6', '4.6', '6770', null, null, null, null, '2011-12-08 11:08:14', '1', '2011-12-08 11:08:14', '2011-12-08 11:08:14', null, '0');
INSERT INTO `fire_alarms` VALUES ('116', '1', '2011-12-09 14:54:22', '6', '4.6', '3201', null, null, null, null, '2011-12-09 14:54:22', '1', '2011-12-09 14:54:22', '2011-12-09 14:54:22', null, '0');
INSERT INTO `fire_alarms` VALUES ('117', '1', '2011-12-15 11:29:10', '6', '4.6', '2540', null, null, null, null, '2011-12-15 11:29:10', '1', '2011-12-15 11:29:10', '2011-12-15 11:29:10', null, '0');
INSERT INTO `fire_alarms` VALUES ('118', '1', '2011-12-15 11:30:25', '6', '4.6', '2734', null, null, null, null, '2011-12-15 11:30:25', '1', '2011-12-15 11:30:25', '2011-12-15 11:30:25', null, '0');
INSERT INTO `fire_alarms` VALUES ('119', '1', '2011-12-15 11:37:28', '6', '4.6', '2831', null, null, null, null, '2011-12-15 11:37:28', '1', '2011-12-15 11:37:28', '2011-12-15 11:37:28', null, '0');
INSERT INTO `fire_alarms` VALUES ('120', '1', '2011-12-15 11:39:56', '6', '4.6', '5294', null, null, null, null, '2011-12-15 11:39:56', '1', '2011-12-15 11:39:56', '2011-12-15 11:39:56', null, '0');
INSERT INTO `fire_alarms` VALUES ('121', '1', '2011-12-15 11:43:20', '6', '4.6', '5502', null, null, null, null, '2011-12-15 11:43:20', '1', '2011-12-15 11:43:20', '2011-12-15 11:43:20', null, '0');
INSERT INTO `fire_alarms` VALUES ('122', '1', '2011-12-15 12:20:01', '6', '4.6', '1393', null, null, null, null, '2011-12-15 12:20:01', '1', '2011-12-15 12:20:01', '2011-12-15 12:20:01', null, '0');
INSERT INTO `fire_alarms` VALUES ('123', '1', '2011-12-15 14:03:58', '6', '4.6', '3213', null, null, null, null, '2011-12-15 14:03:58', '1', '2011-12-15 14:03:58', '2011-12-15 14:03:58', null, '0');
INSERT INTO `fire_alarms` VALUES ('124', '1', '2011-12-15 14:07:10', '6', '4.6', '2933', null, null, null, null, '2011-12-15 14:07:10', '1', '2011-12-15 14:07:10', '2011-12-15 14:07:10', null, '0');
INSERT INTO `fire_alarms` VALUES ('125', '1', '2011-12-15 14:07:36', '6', '4.6', '3734', null, null, null, null, '2011-12-15 14:07:36', '1', '2011-12-15 14:07:36', '2011-12-15 14:07:36', null, '0');
INSERT INTO `fire_alarms` VALUES ('126', '1', '2011-12-15 14:11:05', '6', '4.6', '5334', null, null, null, null, '2011-12-15 14:11:05', '1', '2011-12-15 14:11:05', '2011-12-15 14:11:05', null, '0');
INSERT INTO `fire_alarms` VALUES ('127', '1', '2011-12-15 14:24:20', '6', '4.6', '2856', null, null, null, null, '2011-12-15 14:24:20', '1', '2011-12-15 14:24:20', '2011-12-15 14:24:20', null, '0');
INSERT INTO `fire_alarms` VALUES ('128', '1', '2011-12-15 14:38:19', '6', '4.6', '5605', null, null, null, null, '2011-12-15 14:38:19', '1', '2011-12-15 14:38:19', '2011-12-15 14:38:19', null, '0');
INSERT INTO `fire_alarms` VALUES ('129', '1', '2011-12-15 14:41:07', '6', '4.6', '2089', null, null, null, null, '2011-12-15 14:41:07', '1', '2011-12-15 14:41:07', '2011-12-15 14:41:07', null, '0');
INSERT INTO `fire_alarms` VALUES ('130', '1', '2011-12-15 14:50:35', '6', '4.6', '5172', null, null, null, null, '2011-12-15 14:50:35', '1', '2011-12-15 14:50:35', '2011-12-15 14:50:35', null, '0');
INSERT INTO `fire_alarms` VALUES ('131', '1', '2011-12-15 15:11:48', '6', '4.6', '2423', null, null, null, null, '2011-12-15 15:11:48', '1', '2011-12-15 15:11:48', '2011-12-15 15:11:48', null, '0');
INSERT INTO `fire_alarms` VALUES ('132', '1', '2011-12-15 15:44:21', '6', '4.6', '1438', null, null, null, null, '2011-12-15 15:44:21', '1', '2011-12-15 15:44:21', '2011-12-15 15:44:21', null, '0');
INSERT INTO `fire_alarms` VALUES ('133', '1', '2011-12-15 15:48:45', '6', '4.6', '4208', null, null, null, null, '2011-12-15 15:48:45', '1', '2011-12-15 15:48:45', '2011-12-15 15:48:45', null, '0');
INSERT INTO `fire_alarms` VALUES ('134', '1', '2011-12-15 17:47:08', '6', '4.6', '4632', null, null, null, null, '2011-12-15 17:47:08', '1', '2011-12-15 17:47:08', '2011-12-15 17:47:08', null, '0');
INSERT INTO `fire_alarms` VALUES ('135', '1', '2011-12-16 12:56:35', '99.76', '59.99', '4113', null, null, null, null, '2011-12-16 12:56:35', '1', '2011-12-16 12:56:35', '2011-12-16 12:56:35', null, '0');
INSERT INTO `fire_alarms` VALUES ('136', '1', '2011-12-16 12:57:48', '99.93', '61.27', '1489', null, null, null, null, '2011-12-16 12:57:48', '1', '2011-12-16 12:57:48', '2011-12-16 12:57:48', null, '0');
INSERT INTO `fire_alarms` VALUES ('137', '1', '2011-12-16 12:59:09', '100.05', '61.14', '2698', null, null, null, null, '2011-12-16 12:59:09', '1', '2011-12-16 12:59:09', '2011-12-16 12:59:09', null, '0');
INSERT INTO `fire_alarms` VALUES ('138', '1', '2011-12-16 13:00:21', '100.18', '61.06', '3891', null, null, null, null, '2011-12-16 13:00:21', '1', '2011-12-16 13:00:21', '2011-12-16 13:00:21', null, '0');
INSERT INTO `fire_alarms` VALUES ('139', '1', '2011-12-16 13:00:48', '100.15', '61.11', '2982', null, null, null, null, '2011-12-16 13:00:48', '1', '2011-12-16 13:00:48', '2011-12-16 13:00:48', null, '0');
INSERT INTO `fire_alarms` VALUES ('140', '1', '2011-12-16 13:01:31', '100.04', '61.16', '3927', null, null, null, null, '2011-12-16 13:01:31', '1', '2011-12-16 13:01:31', '2011-12-16 13:01:31', null, '0');
INSERT INTO `fire_alarms` VALUES ('141', '1', '2011-12-16 13:05:27', '100.02', '61.09', '3813', null, null, null, null, '2011-12-16 13:05:27', '1', '2011-12-16 13:05:27', '2011-12-16 13:05:27', null, '0');
INSERT INTO `fire_alarms` VALUES ('142', '1', '2011-12-16 13:07:20', '100.1', '61.13', '2660', null, null, null, null, '2011-12-16 13:07:20', '1', '2011-12-16 13:07:20', '2011-12-16 13:07:20', null, '0');
INSERT INTO `fire_alarms` VALUES ('143', '1', '2011-12-16 13:08:07', '100.1', '61.14', '2385', null, null, null, null, '2011-12-16 13:08:07', '1', '2011-12-16 13:08:07', '2011-12-16 13:08:07', null, '0');
INSERT INTO `fire_alarms` VALUES ('144', '1', '2011-12-16 13:14:17', '103.03', '61.14', '4029', null, null, null, null, '2011-12-16 13:14:17', '1', '2011-12-16 13:14:17', '2011-12-16 13:14:17', null, '0');
INSERT INTO `fire_alarms` VALUES ('145', '1', '2011-12-16 13:14:44', '103.11', '60.39', '4052', null, null, null, null, '2011-12-16 13:14:44', '1', '2011-12-16 13:14:44', '2011-12-16 13:14:44', null, '0');
INSERT INTO `fire_alarms` VALUES ('146', '1', '2011-12-16 13:15:17', '100.12', '60.44', '4336', null, null, null, null, '2011-12-16 13:15:17', '1', '2011-12-16 13:15:17', '2011-12-16 13:15:17', null, '0');
INSERT INTO `fire_alarms` VALUES ('147', '1', '2011-12-16 13:22:27', '102.97', '61.16', '3956', null, null, null, null, '2011-12-16 13:22:27', '1', '2011-12-16 13:22:27', '2011-12-16 13:22:27', null, '0');
INSERT INTO `fire_alarms` VALUES ('148', '1', '2011-12-16 13:22:47', '99.96', '60.41', '4250', null, null, null, null, '2011-12-16 13:22:47', '1', '2011-12-16 13:22:47', '2011-12-16 13:22:47', null, '0');
INSERT INTO `fire_alarms` VALUES ('149', '1', '2011-12-16 13:23:35', '103.05', '61.23', '4146', null, null, null, null, '2011-12-16 13:23:35', '1', '2011-12-16 13:23:35', '2011-12-16 13:23:35', null, '0');
INSERT INTO `fire_alarms` VALUES ('150', '1', '2011-12-16 13:23:57', '99.91', '60.39', '3799', null, null, null, null, '2011-12-16 13:23:57', '1', '2011-12-16 13:23:57', '2011-12-16 13:23:57', null, '0');
INSERT INTO `fire_alarms` VALUES ('151', '1', '2011-12-16 13:24:46', '100.04', '61.26', '1696', null, null, null, null, '2011-12-16 13:24:46', '1', '2011-12-16 13:24:46', '2011-12-16 13:24:46', null, '0');
INSERT INTO `fire_alarms` VALUES ('152', '1', '2011-12-17 11:50:07', '63.82', '59.99', '2119', null, null, null, null, '2011-12-17 11:50:07', '1', '2011-12-17 11:50:07', '2011-12-17 11:50:07', null, '0');
INSERT INTO `fire_alarms` VALUES ('153', '1', '2011-12-19 09:32:25', '63.74', '59.99', '1326', null, null, null, null, '2011-12-19 09:32:25', '1', '2011-12-19 09:32:25', '2011-12-19 09:32:25', null, '0');
INSERT INTO `fire_alarms` VALUES ('154', '1', '2011-12-19 09:36:43', '57.43', '59.98', '3884', null, null, null, null, '2011-12-19 09:36:43', '1', '2011-12-19 09:36:43', '2011-12-19 09:36:43', null, '0');
INSERT INTO `fire_alarms` VALUES ('155', '1', '2011-12-19 09:48:52', '0', '0', '4733', null, null, null, null, '2011-12-19 09:48:52', '1', '2011-12-19 09:48:52', '2011-12-19 09:48:52', null, '0');
INSERT INTO `fire_alarms` VALUES ('156', '1', '2011-12-19 09:55:21', '63.62', '59.99', '3596', null, null, null, null, '2011-12-19 09:55:21', '1', '2011-12-19 09:55:21', '2011-12-19 09:55:21', null, '0');
INSERT INTO `fire_alarms` VALUES ('157', '1', '2011-12-19 09:56:42', '57.38', '59.92', '3929', null, null, null, null, '2011-12-19 09:56:42', '1', '2011-12-19 09:56:42', '2011-12-19 09:56:42', null, '0');
INSERT INTO `fire_alarms` VALUES ('158', '1', '2011-12-19 09:57:05', '57.95', '61.39', '5130', null, null, null, null, '2011-12-19 09:57:05', '1', '2011-12-19 09:57:05', '2011-12-19 09:57:05', null, '0');
INSERT INTO `fire_alarms` VALUES ('159', '1', '2011-12-19 10:04:02', '64.36', '61.16', '2990', null, null, null, null, '2011-12-19 10:04:02', '1', '2011-12-19 10:04:02', '2011-12-19 10:04:02', null, '0');
INSERT INTO `fire_alarms` VALUES ('160', '1', '2011-12-19 10:13:02', '62.1', '60.52', '4966', null, null, null, null, '2011-12-19 10:13:02', '1', '2011-12-19 10:13:02', '2011-12-19 10:13:02', null, '0');
INSERT INTO `fire_alarms` VALUES ('161', '1', '2011-12-19 10:14:05', '62.11', '60.16', '5125', null, null, null, null, '2011-12-19 10:14:05', '1', '2011-12-19 10:14:05', '2011-12-19 10:14:05', null, '0');
INSERT INTO `fire_alarms` VALUES ('162', '1', '2011-12-19 10:43:25', '59.53', '61.16', '4456', null, null, null, null, '2011-12-19 10:43:25', '1', '2011-12-19 10:43:25', '2011-12-19 10:43:25', null, '0');
INSERT INTO `fire_alarms` VALUES ('163', '1', '2011-12-19 10:43:58', '52.32', '60.7', '3120', null, null, null, null, '2011-12-19 10:43:58', '1', '2011-12-19 10:43:58', '2011-12-19 10:43:58', null, '0');
INSERT INTO `fire_alarms` VALUES ('164', '1', '2011-12-19 10:44:32', '52.59', '61.56', '4875', null, null, null, null, '2011-12-19 10:44:32', '1', '2011-12-19 10:44:32', '2011-12-19 10:44:32', null, '0');
INSERT INTO `fire_alarms` VALUES ('165', '1', '2011-12-19 10:45:41', '52.66', '61.41', '1431', null, null, null, null, '2011-12-19 10:45:41', '1', '2011-12-19 10:45:41', '2011-12-19 10:45:41', null, '0');
INSERT INTO `fire_alarms` VALUES ('166', '1', '2011-12-19 10:57:21', '55.91', '61.45', '5408', null, null, null, null, '2011-12-19 10:57:21', '1', '2011-12-19 10:57:21', '2011-12-19 10:57:21', null, '0');
INSERT INTO `fire_alarms` VALUES ('167', '1', '2011-12-19 11:00:39', '52.39', '59.02', '1379', null, null, null, null, '2011-12-19 11:00:39', '1', '2011-12-19 11:00:39', '2011-12-19 11:00:39', null, '0');
INSERT INTO `fire_alarms` VALUES ('168', '1', '2011-12-19 11:01:32', '52.31', '59.66', '5531', null, null, null, null, '2011-12-19 11:01:32', '1', '2011-12-19 11:01:32', '2011-12-19 11:01:32', null, '0');
INSERT INTO `fire_alarms` VALUES ('169', '1', '2011-12-19 14:25:16', '58.36', '59.99', '2535', null, null, null, null, '2011-12-19 14:25:16', '1', '2011-12-19 14:25:16', '2011-12-19 14:25:16', null, '0');
INSERT INTO `fire_alarms` VALUES ('170', '1', '2011-12-19 14:26:11', '54.02', '58.59', '1345', null, null, null, null, '2011-12-19 14:26:11', '1', '2011-12-19 14:26:11', '2011-12-19 14:26:11', null, '0');
INSERT INTO `fire_alarms` VALUES ('171', '1', '2011-12-19 14:27:26', '54.94', '60.84', '4762', null, null, null, null, '2011-12-19 14:27:26', '1', '2011-12-19 14:27:26', '2011-12-19 14:27:26', null, '0');
INSERT INTO `fire_alarms` VALUES ('172', '1', '2011-12-19 14:29:36', '58.58', '59.99', '1749', null, null, null, null, '2011-12-19 14:29:36', '1', '2011-12-19 14:29:36', '2011-12-19 14:29:36', null, '0');
INSERT INTO `fire_alarms` VALUES ('173', '1', '2011-12-19 14:47:18', '52.07', '59.99', '2104', null, null, null, null, '2011-12-19 14:47:18', '1', '2011-12-19 14:47:18', '2011-12-19 14:47:18', null, '0');
INSERT INTO `fire_alarms` VALUES ('174', '1', '2011-12-19 14:49:14', '52.74', '61.71', '4521', null, null, null, null, '2011-12-19 14:49:14', '1', '2011-12-19 14:49:14', '2011-12-19 14:49:14', null, '0');
INSERT INTO `fire_alarms` VALUES ('175', '1', '2011-12-19 14:51:59', '55.57', '61.38', '5330', null, null, null, null, '2011-12-19 14:51:59', '1', '2011-12-19 14:51:59', '2011-12-19 14:51:59', null, '0');
INSERT INTO `fire_alarms` VALUES ('176', '1', '2011-12-19 14:54:48', '55.69', '60.62', '5190', null, null, null, null, '2011-12-19 14:54:48', '1', '2011-12-19 14:54:48', '2011-12-19 14:54:48', null, '0');
INSERT INTO `fire_alarms` VALUES ('177', '1', '2011-12-19 14:55:02', '55.48', '60.71', '5163', null, null, null, null, '2011-12-19 14:55:02', '1', '2011-12-19 14:55:02', '2011-12-19 14:55:02', null, '0');
INSERT INTO `fire_alarms` VALUES ('178', '1', '2011-12-19 14:56:03', '52.54', '60.84', '3341', null, null, null, null, '2011-12-19 14:56:03', '1', '2011-12-19 14:56:03', '2011-12-19 14:56:03', null, '0');
INSERT INTO `fire_alarms` VALUES ('179', '1', '2011-12-19 14:57:41', '55.74', '61.56', '5152', null, null, null, null, '2011-12-19 14:57:41', '1', '2011-12-19 14:57:41', '2011-12-19 14:57:41', null, '0');
INSERT INTO `fire_alarms` VALUES ('180', '1', '2011-12-19 14:59:19', '57.94', '57.99', '1912', null, null, null, null, '2011-12-19 14:59:19', '1', '2011-12-19 14:59:19', '2011-12-19 14:59:19', null, '0');
INSERT INTO `fire_alarms` VALUES ('181', '1', '2011-12-19 15:03:04', '52.23', '59.99', '2797', null, null, null, null, '2011-12-19 15:03:04', '1', '2011-12-19 15:03:04', '2011-12-19 15:03:04', null, '0');
INSERT INTO `fire_alarms` VALUES ('182', '1', '2011-12-19 15:03:52', '52.19', '60.1', '2260', null, null, null, null, '2011-12-19 15:03:52', '1', '2011-12-19 15:03:52', '2011-12-19 15:03:52', null, '0');
INSERT INTO `fire_alarms` VALUES ('183', '1', '2011-12-19 15:05:21', '52.79', '61.66', '5338', null, null, null, null, '2011-12-19 15:05:21', '1', '2011-12-19 15:05:21', '2011-12-19 15:05:21', null, '0');
INSERT INTO `fire_alarms` VALUES ('184', '1', '2011-12-19 15:24:48', '0', '0', '5606', null, null, null, null, '2011-12-19 15:24:48', '1', '2011-12-19 15:24:48', '2011-12-19 15:24:48', null, '0');
INSERT INTO `fire_alarms` VALUES ('185', '1', '2011-12-19 15:32:14', '58.53', '59.99', '2294', null, null, null, null, '2011-12-19 15:32:14', '1', '2011-12-19 15:32:14', '2011-12-19 15:32:14', null, '0');
INSERT INTO `fire_alarms` VALUES ('186', '1', '2011-12-19 15:39:04', '0', '0', '5697', null, null, null, null, '2011-12-19 15:39:04', '1', '2011-12-19 15:39:04', '2011-12-19 15:39:04', null, '0');
INSERT INTO `fire_alarms` VALUES ('187', '1', '2011-12-19 15:48:12', '52.43', '60.95', '4044', null, null, null, null, '2011-12-19 15:48:12', '1', '2011-12-19 15:48:12', '2011-12-19 15:48:12', null, '0');
INSERT INTO `fire_alarms` VALUES ('188', '1', '2011-12-19 16:02:29', '0', '0', '5571', null, null, null, null, '2011-12-19 16:02:29', '1', '2011-12-19 16:02:29', '2011-12-19 16:02:29', null, '0');
INSERT INTO `fire_alarms` VALUES ('189', '1', '2011-12-19 16:05:02', '57.85', '57.99', '2714', null, null, null, null, '2011-12-19 16:05:02', '1', '2011-12-19 16:05:02', '2011-12-19 16:05:02', null, '0');
INSERT INTO `fire_alarms` VALUES ('190', '1', '2011-12-19 16:05:26', '52.43', '60.24', '5411', null, null, null, null, '2011-12-19 16:05:26', '1', '2011-12-19 16:05:26', '2011-12-19 16:05:26', null, '0');
INSERT INTO `fire_alarms` VALUES ('191', '1', '2011-12-19 16:10:10', '52.36', '59.99', '3370', null, null, null, null, '2011-12-19 16:10:10', '1', '2011-12-19 16:10:10', '2011-12-19 16:10:10', null, '0');
INSERT INTO `fire_alarms` VALUES ('192', '1', '2011-12-19 16:10:49', '41.92', '60.03', '5854', null, null, null, null, '2011-12-19 16:10:49', '1', '2011-12-19 16:10:49', '2011-12-19 16:10:49', null, '0');
INSERT INTO `fire_alarms` VALUES ('193', '1', '2011-12-19 16:12:57', '52.58', '61.67', '3809', null, null, null, null, '2011-12-19 16:12:57', '1', '2011-12-19 16:12:57', '2011-12-19 16:12:57', null, '0');
INSERT INTO `fire_alarms` VALUES ('194', '1', '2011-12-19 16:16:23', '52.43', '59.99', '5262', null, null, null, null, '2011-12-19 16:16:23', '1', '2011-12-19 16:16:23', '2011-12-19 16:16:23', null, '0');
INSERT INTO `fire_alarms` VALUES ('195', '1', '2011-12-19 16:16:42', '52.21', '60.11', '2875', null, null, null, null, '2011-12-19 16:16:42', '1', '2011-12-19 16:16:42', '2011-12-19 16:16:42', null, '0');
INSERT INTO `fire_alarms` VALUES ('196', '1', '2011-12-19 16:17:08', '53.11', '63.09', '3115', null, null, null, null, '2011-12-19 16:17:08', '1', '2011-12-19 16:17:08', '2011-12-19 16:17:08', null, '0');
INSERT INTO `fire_alarms` VALUES ('197', '1', '2011-12-19 16:19:03', '52.39', '61.14', '1357', null, null, null, null, '2011-12-19 16:19:03', '1', '2011-12-19 16:19:03', '2011-12-19 16:19:03', null, '0');
INSERT INTO `fire_alarms` VALUES ('198', '1', '2011-12-19 16:22:26', '52.3', '59.99', '5104', null, null, null, null, '2011-12-19 16:22:26', '1', '2011-12-19 16:22:26', '2011-12-19 16:22:26', null, '0');
INSERT INTO `fire_alarms` VALUES ('199', '1', '2011-12-19 16:22:44', '55.55', '61.66', '5271', null, null, null, null, '2011-12-19 16:22:44', '1', '2011-12-19 16:22:44', '2011-12-19 16:22:44', null, '0');
INSERT INTO `fire_alarms` VALUES ('200', '1', '2011-12-19 16:22:50', '55.77', '60.66', '5168', null, null, null, null, '2011-12-19 16:22:50', '1', '2011-12-19 16:22:50', '2011-12-19 16:22:50', null, '0');
INSERT INTO `fire_alarms` VALUES ('201', '1', '2011-12-19 16:22:55', '55.52', '60.7', '5115', null, null, null, null, '2011-12-19 16:22:55', '1', '2011-12-19 16:22:55', '2011-12-19 16:22:55', null, '0');
INSERT INTO `fire_alarms` VALUES ('202', '1', '2011-12-19 16:23:59', '58.41', '59.99', '3964', null, null, null, null, '2011-12-19 16:23:59', '1', '2011-12-19 16:23:59', '2011-12-19 16:23:59', null, '0');
INSERT INTO `fire_alarms` VALUES ('203', '1', '2011-12-19 16:33:31', '52.04', '59.99', '5521', null, null, null, null, '2011-12-19 16:33:31', '1', '2011-12-19 16:33:31', '2011-12-19 16:33:31', null, '0');
INSERT INTO `fire_alarms` VALUES ('204', '1', '2011-12-19 16:35:38', '58.21', '59.99', '1979', null, null, null, null, '2011-12-19 16:35:38', '1', '2011-12-19 16:35:38', '2011-12-19 16:35:38', null, '0');
INSERT INTO `fire_alarms` VALUES ('205', '1', '2011-12-19 16:50:02', '52.02', '59.99', '5349', null, null, null, null, '2011-12-19 16:50:02', '1', '2011-12-19 16:50:02', '2011-12-19 16:50:02', null, '0');
INSERT INTO `fire_alarms` VALUES ('206', '1', '2011-12-19 16:51:19', '58.03', '59.99', '4031', null, null, null, null, '2011-12-19 16:51:19', '1', '2011-12-19 16:51:19', '2011-12-19 16:51:19', null, '0');
INSERT INTO `fire_alarms` VALUES ('207', '1', '2011-12-19 17:00:58', '54.77', '60.13', '5542', null, null, null, null, '2011-12-19 17:00:58', '1', '2011-12-19 17:00:58', '2011-12-19 17:00:58', null, '0');
INSERT INTO `fire_alarms` VALUES ('208', '1', '2011-12-19 17:01:17', '54.95', '60.95', '5618', null, null, null, null, '2011-12-19 17:01:17', '1', '2011-12-19 17:01:17', '2011-12-19 17:01:17', null, '0');
INSERT INTO `fire_alarms` VALUES ('209', '1', '2011-12-19 17:01:57', '55.27', '60.84', '2883', null, null, null, null, '2011-12-19 17:01:57', '1', '2011-12-19 17:01:57', '2011-12-19 17:01:57', null, '0');
INSERT INTO `fire_alarms` VALUES ('210', '1', '2011-12-19 17:02:21', '55.27', '60.6', '2961', null, null, null, null, '2011-12-19 17:02:21', '1', '2011-12-19 17:02:21', '2011-12-19 17:02:21', null, '0');
INSERT INTO `fire_alarms` VALUES ('211', '1', '2011-12-19 17:17:54', '55.27', '60.62', '5436', null, null, null, null, '2011-12-19 17:17:54', '1', '2011-12-19 17:17:54', '2011-12-19 17:17:54', null, '0');
INSERT INTO `fire_alarms` VALUES ('212', '1', '2011-12-19 17:24:03', '58.4', '60.8', '3453', null, null, null, null, '2011-12-19 17:24:03', '1', '2011-12-19 17:24:03', '2011-12-19 17:24:03', null, '0');
INSERT INTO `fire_alarms` VALUES ('213', '1', '2011-12-19 17:35:33', '54.99', '60.06', '5327', null, null, null, null, '2011-12-19 17:35:33', '1', '2011-12-19 17:35:33', '2011-12-19 17:35:33', null, '0');
INSERT INTO `fire_alarms` VALUES ('214', '1', '2011-12-19 17:36:06', '54.99', '60.84', '5509', null, null, null, null, '2011-12-19 17:36:06', '1', '2011-12-19 17:36:06', '2011-12-19 17:36:06', null, '0');
INSERT INTO `fire_alarms` VALUES ('215', '1', '2011-12-19 17:36:16', '55.16', '60.81', '5167', null, null, null, null, '2011-12-19 17:36:16', '1', '2011-12-19 17:36:16', '2011-12-19 17:36:16', null, '0');
INSERT INTO `fire_alarms` VALUES ('216', '1', '2011-12-19 17:36:22', '54.76', '60.66', '5412', null, null, null, null, '2011-12-19 17:36:22', '1', '2011-12-19 17:36:22', '2011-12-19 17:36:22', null, '0');
INSERT INTO `fire_alarms` VALUES ('217', '1', '2011-12-19 17:39:21', '51.6', '60.75', '2884', null, null, null, null, '2011-12-19 17:39:21', '1', '2011-12-19 17:39:21', '2011-12-19 17:39:21', null, '0');
INSERT INTO `fire_alarms` VALUES ('218', '1', '2011-12-19 17:51:56', '57.45', '59.99', '4641', null, null, null, null, '2011-12-19 17:51:56', '1', '2011-12-19 17:51:56', '2011-12-19 17:51:56', null, '0');
INSERT INTO `fire_alarms` VALUES ('219', '1', '2011-12-19 18:07:05', '51.39', '60.09', '4354', null, null, null, null, '2011-12-19 18:07:05', '1', '2011-12-19 18:07:05', '2011-12-19 18:07:05', null, '0');
INSERT INTO `fire_alarms` VALUES ('220', '1', '2011-12-20 10:40:48', '40.34', '90', '2075', null, null, null, null, '2011-12-20 10:40:48', '1', '2011-12-20 10:40:48', '2011-12-20 10:40:48', null, '0');
INSERT INTO `fire_alarms` VALUES ('221', '1', '2011-12-20 15:34:52', '49.96', '19.99', '2450', null, null, null, null, '2011-12-20 15:34:52', '1', '2011-12-20 15:34:52', '2011-12-20 15:34:52', null, '0');
INSERT INTO `fire_alarms` VALUES ('222', '1', '2011-12-20 15:39:31', '19.46', '57.79', '5917', null, null, null, null, '2011-12-20 15:39:31', '1', '2011-12-20 15:39:31', '2011-12-20 15:39:31', null, '0');
INSERT INTO `fire_alarms` VALUES ('223', '1', '2011-12-20 15:41:32', '20.22', '60.55', '4224', null, null, null, null, '2011-12-20 15:41:32', '1', '2011-12-20 15:41:32', '2011-12-20 15:41:32', null, '0');
INSERT INTO `fire_alarms` VALUES ('224', '1', '2011-12-20 15:42:22', '14.15', '60.24', '3756', null, null, null, null, '2011-12-20 15:42:22', '1', '2011-12-20 15:42:22', '2011-12-20 15:42:22', null, '0');
INSERT INTO `fire_alarms` VALUES ('225', '1', '2011-12-20 15:42:30', '17.34', '61.81', '6279', null, null, null, null, '2011-12-20 15:42:30', '1', '2011-12-20 15:42:30', '2011-12-20 15:42:30', null, '0');
INSERT INTO `fire_alarms` VALUES ('226', '1', '2011-12-20 16:03:36', '14.13', '60.84', '2523', null, null, null, null, '2011-12-20 16:03:36', '1', '2011-12-20 16:03:36', '2011-12-20 16:03:36', null, '0');
INSERT INTO `fire_alarms` VALUES ('227', '1', '2011-12-20 16:45:10', '14.6', '61.69', '6002', null, null, null, null, '2011-12-20 16:45:10', '1', '2011-12-20 16:45:10', '2011-12-20 16:45:10', null, '0');
INSERT INTO `fire_alarms` VALUES ('228', '1', '2011-12-20 16:55:15', '20.09', '59.99', '4736', null, null, null, null, '2011-12-20 16:55:15', '1', '2011-12-20 16:55:15', '2011-12-20 16:55:15', null, '0');
INSERT INTO `fire_alarms` VALUES ('229', '1', '2011-12-20 17:14:24', '16.95', '60.34', '6163', null, null, null, null, '2011-12-20 17:14:24', '1', '2011-12-20 17:14:24', '2011-12-20 17:14:24', null, '0');
INSERT INTO `fire_alarms` VALUES ('230', '1', '2011-12-20 17:16:23', '20.23', '59.99', '3281', null, null, null, null, '2011-12-20 17:16:23', '1', '2011-12-20 17:16:23', '2011-12-20 17:16:23', null, '0');
INSERT INTO `fire_alarms` VALUES ('231', '1', '2011-12-20 17:40:36', '16.97', '60.16', '6259', null, null, null, null, '2011-12-20 17:40:36', '1', '2011-12-20 17:40:36', '2011-12-20 17:40:36', null, '0');
INSERT INTO `fire_alarms` VALUES ('232', '1', '2011-12-20 17:42:24', '20.24', '59.99', '3718', null, null, null, null, '2011-12-20 17:42:24', '1', '2011-12-20 17:42:24', '2011-12-20 17:42:24', null, '0');
INSERT INTO `fire_alarms` VALUES ('233', '1', '2011-12-22 10:36:55', '83.15', '59.99', '3014', null, null, null, null, '2011-12-22 10:36:55', '1', '2011-12-22 10:36:55', '2011-12-22 10:36:55', null, '0');
INSERT INTO `fire_alarms` VALUES ('234', '1', '2011-12-23 11:48:57', '66.37', '48.29', '301', null, null, null, null, '2011-12-23 11:48:57', '1', '2011-12-23 11:48:57', '2011-12-23 11:48:57', null, '0');
INSERT INTO `ignore_areas` VALUES ('1', '1', '1', '2', '1', '2', null, '1', '2011-12-26 15:40:59', '2011-12-26 15:41:01', '0', '2011-12-26 15:41:05', '2011-12-26 15:41:05', '0');
INSERT INTO `ignore_areas` VALUES ('2', '1', '5', '6', '5', '6', '3', '5', '2011-12-26 15:43:24', '2011-12-26 15:43:26', '0', '2011-12-26 15:43:52', '2011-12-26 15:43:30', '0');
INSERT INTO `privilege_details` VALUES ('1', '2', '所到所有用户', '1', '1', null, null, null, '', null, '0', '2011-09-14 12:30:16', '2011-09-16 16:21:38', '0');
INSERT INTO `privilege_details` VALUES ('2', '2', '新增用户', '1', '2', null, null, null, '', null, null, '2011-09-14 12:48:15', '2011-09-15 13:58:16', '0');
INSERT INTO `privilege_details` VALUES ('3', '2', '更新用户', '1', '3', null, null, null, '', null, '0', '2011-09-14 13:00:59', '2011-09-15 14:33:14', '0');
INSERT INTO `privilege_details` VALUES ('4', '7', '新增角色', '3', '33', null, null, null, '', '0', '0', '2011-09-19 14:55:00', '2011-09-19 14:55:00', '0');
INSERT INTO `privilege_details` VALUES ('5', '9', 'PTZ', '5', '50', null, null, null, '', '0', '0', '2011-09-30 11:25:57', '2011-09-30 11:25:57', '0');
INSERT INTO `privilege_details` VALUES ('6', '10', '得到所有火警信息', '6', '53', null, null, null, '', '0', '0', '2011-11-16 18:29:28', '2011-11-16 18:29:28', '0');
INSERT INTO `privilege_details` VALUES ('7', '11', '添加云台', '5', '55', null, null, null, '', '0', '0', '2011-11-16 18:30:46', '2011-11-16 18:30:46', '0');
INSERT INTO `privileges` VALUES ('1', '系统设置', null, '3', null, null, '0', '0', '0', null, 'false', null, '0', '2', '0', '0', '2011-09-13 12:10:52', '2011-09-30 10:06:01');
INSERT INTO `privileges` VALUES ('2', '用户设置', null, '1', '24', null, '1', '0', '0', null, 'true', '', '1', '1', '0', '0', '2011-09-13 12:10:44', '2011-11-22 16:06:31');
INSERT INTO `privileges` VALUES ('3', '系统权限', null, '2', '12', null, '1', '0', '0', null, 'true', '', '1', '2', '0', '0', '2011-09-13 12:10:38', '2011-11-22 16:06:31');
INSERT INTO `privileges` VALUES ('7', '角色设置', null, '3', '32', null, '1', null, '0', null, 'true', '', '1', '4', '0', '0', '2011-09-19 14:16:38', '2011-09-19 14:16:38');
INSERT INTO `privileges` VALUES ('8', '防火监控', null, null, null, null, '0', null, '0', null, 'false', '包括PTZ控制，起火点确认，历史记录查询', '0', '1', '0', '0', '2011-09-30 10:05:57', '2011-11-16 18:28:55');
INSERT INTO `privileges` VALUES ('9', '云台信息', null, '5', '50', null, '1', null, '0', null, 'true', '云台信息，通过其中的云台列表，取得控制权', '8', '1', '0', '0', '2011-09-30 10:55:19', '2011-09-30 10:55:19');
INSERT INTO `privileges` VALUES ('10', '历史火警记录', null, '6', '52', null, '1', null, '0', null, 'true', '', '8', '2', '0', '0', '2011-11-16 18:28:16', '2011-11-16 18:28:16');
INSERT INTO `privileges` VALUES ('11', '云台管理', null, '5', '61', null, '1', null, '0', null, 'true', '', '8', '3', '0', '0', '2011-11-16 18:28:55', '2011-11-16 18:28:55');
INSERT INTO `ptzs` VALUES ('1', '测试机，主云台1', 'rtsp://admin:12345@192.168.254.64/h264/ch1/main/av_stream', '192.168.254.65', '192.168.254.63', 'ntrt://192.168.254.63/1/8000/admin/12345', 'admin', '12345', 'HK', 'ntrt://192.168.254.64/1/8000/admin/12345', 'ping', '192.168.1.50', '0', 'map.png', '6', '4.6', '384', '288', 'FY', '6', '4', '10', 'LR', '80', '30', '60', '25', '350', '0', '2011-11-22 15:34:39', '2011-12-23 16:00:22', '0', '0');
INSERT INTO `role_ptzs` VALUES ('1', '1', '1');
INSERT INTO `roles` VALUES ('1', '管理员', '管理员', '1', '0', '12', '2011-09-09 15:25:30', '2011-09-20 17:41:56');
INSERT INTO `roles` VALUES ('2', 'æµè¯', 'æµè¯', null, '0', '0', '2011-09-20 17:46:37', '2011-09-20 17:46:37');
INSERT INTO `roles` VALUES ('3', '测试', '测试', null, '0', '0', '2011-09-20 17:57:03', '2011-09-20 17:57:03');
INSERT INTO `roles_privilege_details` VALUES ('1', '1', '1', '2', '1', null, '0', '0', '2011-09-13 12:11:34', '2011-09-13 12:11:36');
INSERT INTO `roles_privilege_details` VALUES ('2', '1', '1', '3', '2', null, '0', '0', '2011-09-13 12:11:38', '2011-09-13 12:11:41');
INSERT INTO `roles_privilege_details` VALUES ('3', '1', '1', '7', '4', null, '0', '0', '2011-09-19 14:17:36', '2011-09-20 18:17:36');
INSERT INTO `roles_privilege_details` VALUES ('4', '1', '1', '2', '3', null, '0', '0', '2011-09-20 10:48:03', '2011-09-20 10:48:03');
INSERT INTO `roles_privilege_details` VALUES ('5', '3', '1', '7', '4', null, '0', '0', '2011-09-20 17:57:33', '2011-09-20 18:21:03');
INSERT INTO `roles_privilege_details` VALUES ('6', '3', '1', '2', '1', null, '0', '0', '2011-09-20 18:17:20', '2011-09-20 18:17:20');
INSERT INTO `roles_privilege_details` VALUES ('7', '3', '1', '2', '3', null, '0', '0', '2011-09-20 18:17:20', '2011-09-20 18:17:20');
INSERT INTO `roles_privilege_details` VALUES ('8', '1', '8', '9', '5', null, '0', '0', '2011-09-30 11:26:05', '2011-09-30 11:26:05');
INSERT INTO `roles_privilege_details` VALUES ('9', '1', '8', '10', '6', null, '0', '0', '2011-11-16 18:30:54', '2011-11-16 18:30:54');
INSERT INTO `roles_privilege_details` VALUES ('10', '1', '8', '11', '7', null, '0', '0', '2011-11-16 18:30:55', '2011-11-16 18:30:55');
INSERT INTO `sys_actions` VALUES ('1', '4', 'index', null, null, null, '2011-09-16 17:41:35', '2011-09-16 17:41:35');
INSERT INTO `sys_actions` VALUES ('2', '4', 'login', null, null, null, '2011-09-16 17:41:35', '2011-09-16 17:41:35');
INSERT INTO `sys_actions` VALUES ('3', '4', 'logout', null, null, null, '2011-09-16 17:41:35', '2011-09-16 17:41:35');
INSERT INTO `sys_actions` VALUES ('4', '4', 'validateUser', null, null, null, '2011-09-16 17:41:35', '2011-09-16 17:41:35');
INSERT INTO `sys_actions` VALUES ('5', '3', 'getAllRoles', null, null, null, '2011-09-16 17:41:35', '2011-09-16 17:41:35');
INSERT INTO `sys_actions` VALUES ('6', '3', 'getAllSysActions', null, null, null, '2011-09-16 17:41:35', '2011-09-16 17:41:35');
INSERT INTO `sys_actions` VALUES ('7', '3', 'getAllSysControllers', null, null, null, '2011-09-16 17:41:35', '2011-09-16 17:41:35');
INSERT INTO `sys_actions` VALUES ('8', '2', 'getSysPrivilegeChildrenById', null, null, null, '2011-09-16 17:41:35', '2011-09-16 17:41:35');
INSERT INTO `sys_actions` VALUES ('9', '2', 'getAllModules', null, null, null, '2011-09-16 17:41:35', '2011-09-16 17:41:35');
INSERT INTO `sys_actions` VALUES ('10', '2', 'getPrivilegeDetailsById', null, null, null, '2011-09-16 17:41:35', '2011-09-16 17:41:35');
INSERT INTO `sys_actions` VALUES ('11', '2', 'getPrivilegeDetailById', null, null, null, '2011-09-16 17:41:35', '2011-09-16 17:41:35');
INSERT INTO `sys_actions` VALUES ('12', '2', 'systemPrivilege', null, null, null, '2011-09-16 17:41:36', '2011-09-16 17:41:36');
INSERT INTO `sys_actions` VALUES ('13', '2', 'newPrivilegeMenu', null, null, null, '2011-09-16 17:41:36', '2011-09-16 17:41:36');
INSERT INTO `sys_actions` VALUES ('14', '2', 'newPrivilegeDetail', null, null, null, '2011-09-16 17:41:36', '2011-09-16 17:41:36');
INSERT INTO `sys_actions` VALUES ('15', '2', 'editPrivilegeDetail', null, null, null, '2011-09-16 17:41:36', '2011-09-16 17:41:36');
INSERT INTO `sys_actions` VALUES ('16', '2', 'editPrivilegeMenu', null, null, null, '2011-09-16 17:41:36', '2011-09-16 17:41:36');
INSERT INTO `sys_actions` VALUES ('17', '2', 'createSysPrivilegeDetail', null, null, null, '2011-09-16 17:41:36', '2011-09-16 17:41:36');
INSERT INTO `sys_actions` VALUES ('18', '2', 'updateSysPrivilegeDetail', null, null, null, '2011-09-16 17:41:36', '2011-09-16 17:41:36');
INSERT INTO `sys_actions` VALUES ('19', '2', 'privilegeDetailLock', null, null, null, '2011-09-16 17:41:36', '2011-09-16 17:41:36');
INSERT INTO `sys_actions` VALUES ('20', '2', 'getSysPrivilegeById', null, null, null, '2011-09-16 17:41:36', '2011-09-16 17:41:36');
INSERT INTO `sys_actions` VALUES ('21', '2', 'updateSysPrivilege', null, null, null, '2011-09-16 17:41:36', '2011-09-16 17:41:36');
INSERT INTO `sys_actions` VALUES ('22', '1', 'create', null, null, null, '2011-09-16 17:41:36', '2011-09-16 17:41:36');
INSERT INTO `sys_actions` VALUES ('23', '1', 'update', null, null, null, '2011-09-16 17:41:36', '2011-09-16 17:41:36');
INSERT INTO `sys_actions` VALUES ('24', '1', 'userList', null, null, null, '2011-09-16 17:41:36', '2011-09-16 17:41:36');
INSERT INTO `sys_actions` VALUES ('25', '1', 'editPrivilege', null, null, null, '2011-09-16 17:41:36', '2011-09-16 17:41:36');
INSERT INTO `sys_actions` VALUES ('26', '1', 'getAllUsers', null, null, null, '2011-09-16 17:41:36', '2011-09-16 17:41:36');
INSERT INTO `sys_actions` VALUES ('27', '1', 'getUserById', null, null, null, '2011-09-16 17:41:36', '2011-09-16 17:41:36');
INSERT INTO `sys_actions` VALUES ('28', '1', 'deleteUser', null, null, null, '2011-09-16 17:41:36', '2011-09-16 17:41:36');
INSERT INTO `sys_actions` VALUES ('29', '1', 'validateUser', null, null, null, '2011-09-16 17:41:36', '2011-09-16 17:41:36');
INSERT INTO `sys_actions` VALUES ('30', '1', 'newUser', null, null, null, '2011-09-16 17:41:36', '2011-09-16 17:41:36');
INSERT INTO `sys_actions` VALUES ('31', '1', 'editUser', null, null, null, '2011-09-16 17:41:36', '2011-09-16 17:41:36');
INSERT INTO `sys_actions` VALUES ('32', '3', 'rolePrivilege', null, null, null, '2011-09-16 17:57:11', '2011-09-16 17:57:11');
INSERT INTO `sys_actions` VALUES ('33', '3', 'createRole', null, '0', '0', '2011-09-19 14:54:45', '2011-09-19 14:54:45');
INSERT INTO `sys_actions` VALUES ('34', '2', 'sortUp', null, '0', '0', '2011-09-19 14:54:45', '2011-09-19 14:54:45');
INSERT INTO `sys_actions` VALUES ('35', '2', 'sortDown', null, '0', '0', '2011-09-19 14:54:45', '2011-09-19 14:54:45');
INSERT INTO `sys_actions` VALUES ('36', '2', 'editPrivilegeModule', null, '0', '0', '2011-09-19 14:54:45', '2011-09-19 14:54:45');
INSERT INTO `sys_actions` VALUES ('37', '2', 'newPrivilegeModule', null, '0', '0', '2011-09-19 14:54:45', '2011-09-19 14:54:45');
INSERT INTO `sys_actions` VALUES ('38', '2', 'createSysPrivilege', null, '0', '0', '2011-09-19 14:54:45', '2011-09-19 14:54:45');
INSERT INTO `sys_actions` VALUES ('39', '3', 'newRole', null, '0', '0', '2011-09-21 11:04:42', '2011-09-21 11:04:42');
INSERT INTO `sys_actions` VALUES ('40', '3', 'rolePrivilegeDetailLock', null, '0', '0', '2011-09-21 11:04:42', '2011-09-21 11:04:42');
INSERT INTO `sys_actions` VALUES ('41', '3', 'getRolePrivilegeById', null, '0', '0', '2011-09-21 11:04:42', '2011-09-21 11:04:42');
INSERT INTO `sys_actions` VALUES ('42', '3', 'getRolePrivilegeDetailsById', null, '0', '0', '2011-09-21 11:04:42', '2011-09-21 11:04:42');
INSERT INTO `sys_actions` VALUES ('43', '3', 'getRoleById', null, '0', '0', '2011-09-21 11:04:42', '2011-09-21 11:04:42');
INSERT INTO `sys_actions` VALUES ('44', '3', 'roleLock', null, '0', '0', '2011-09-21 11:04:42', '2011-09-21 11:04:42');
INSERT INTO `sys_actions` VALUES ('45', '3', 'editRole', null, '0', '0', '2011-09-21 11:04:42', '2011-09-21 11:04:42');
INSERT INTO `sys_actions` VALUES ('46', '3', 'updateRole', null, '0', '0', '2011-09-21 11:04:42', '2011-09-21 11:04:42');
INSERT INTO `sys_actions` VALUES ('47', '2', 'destroySysPrivilege', null, '0', '0', '2011-09-21 11:04:42', '2011-09-21 11:04:42');
INSERT INTO `sys_actions` VALUES ('48', '2', 'destroySysPrivilegeDetail', null, '0', '0', '2011-09-30 10:03:29', '2011-09-30 10:03:29');
INSERT INTO `sys_actions` VALUES ('49', '5', 'systemPrivilege', null, '0', '0', '2011-09-30 10:07:34', '2011-09-30 10:07:34');
INSERT INTO `sys_actions` VALUES ('50', '5', 'index', null, '0', '0', '2011-09-30 10:45:50', '2011-09-30 10:45:50');
INSERT INTO `sys_actions` VALUES ('51', '5', 'ptzAction', null, '0', '0', '2011-10-08 16:35:25', '2011-10-08 16:35:25');
INSERT INTO `sys_actions` VALUES ('52', '6', 'fireAlarmList', null, '0', '0', '2011-11-16 18:27:37', '2011-11-16 18:27:37');
INSERT INTO `sys_actions` VALUES ('53', '6', 'getAllFireAlarm', null, '0', '0', '2011-11-16 18:27:37', '2011-11-16 18:27:37');
INSERT INTO `sys_actions` VALUES ('54', '4', 'index2', null, '0', '0', '2011-11-16 18:27:37', '2011-11-16 18:27:37');
INSERT INTO `sys_actions` VALUES ('55', '5', 'create', null, '0', '0', '2011-11-16 18:27:37', '2011-11-16 18:27:37');
INSERT INTO `sys_actions` VALUES ('56', '5', 'update', null, '0', '0', '2011-11-16 18:27:37', '2011-11-16 18:27:37');
INSERT INTO `sys_actions` VALUES ('57', '5', 'getAllPTZs', null, '0', '0', '2011-11-16 18:27:37', '2011-11-16 18:27:37');
INSERT INTO `sys_actions` VALUES ('58', '5', 'getPTZById', null, '0', '0', '2011-11-16 18:27:38', '2011-11-16 18:27:38');
INSERT INTO `sys_actions` VALUES ('59', '5', 'editPTZ', null, '0', '0', '2011-11-16 18:27:38', '2011-11-16 18:27:38');
INSERT INTO `sys_actions` VALUES ('60', '5', 'newPTZ', null, '0', '0', '2011-11-16 18:27:38', '2011-11-16 18:27:38');
INSERT INTO `sys_actions` VALUES ('61', '5', 'PTZList', null, '0', '0', '2011-11-16 18:27:38', '2011-11-16 18:27:38');
INSERT INTO `sys_controllers` VALUES ('1', 'user', '用户控制', '0', '0', '2011-09-13 11:42:57', '2011-09-13 11:43:00');
INSERT INTO `sys_controllers` VALUES ('2', 'privilege', '系统权限', '0', '0', '2011-09-13 11:42:49', '2011-09-13 11:42:54');
INSERT INTO `sys_controllers` VALUES ('3', 'admin', '管理员', '0', '0', '2011-09-13 11:47:54', '2011-09-13 11:47:57');
INSERT INTO `sys_controllers` VALUES ('4', 'index', '主控', null, null, '2011-09-15 18:41:32', '2011-09-15 18:41:32');
INSERT INTO `sys_controllers` VALUES ('5', 'ptz', null, '0', '0', '2011-09-30 10:07:34', '2011-09-30 10:07:34');
INSERT INTO `sys_controllers` VALUES ('6', 'firealarm', null, '0', '0', '2011-11-16 18:27:37', '2011-11-16 18:27:37');
INSERT INTO `users` VALUES ('1', null, '1', null, 'jerry', 'jerry', '111111', null, null, null, null, null, null, null, '0', '2', '2011-09-09 15:26:09', '2011-09-13 11:09:37');
INSERT INTO `users` VALUES ('2', null, '1', null, null, '长啸', '111111', null, null, null, null, null, null, null, '0', '0', '2011-09-14 13:40:56', '2011-09-14 13:40:56');
INSERT INTO `users` VALUES ('3', null, '1', null, null, 'ä¸­å½', '123', null, null, null, null, null, null, null, '0', '0', '2011-09-14 14:25:24', '2011-09-14 14:25:24');
INSERT INTO `users` VALUES ('4', null, '1', null, null, 'æ', '123', null, null, null, null, null, null, null, '0', '19', '2011-09-14 14:29:10', '2011-09-14 17:30:25');
INSERT INTO `users` VALUES ('5', null, '1', null, null, 'é????????', '123', null, null, null, null, null, null, null, '0', '0', '2011-09-14 14:31:11', '2011-09-14 14:31:11');
INSERT INTO `users` VALUES ('6', null, '1', null, null, '测试者', '123', null, null, null, null, null, null, null, '0', '4', '2011-09-14 14:32:16', '2011-09-16 09:30:14');
INSERT INTO `users` VALUES ('7', null, '1', null, null, 'è¿äº', '123', null, null, null, null, null, null, null, '0', '2', '2011-09-14 14:41:03', '2011-09-14 17:21:52');
INSERT INTO `users` VALUES ('8', null, '1', null, null, 'adf', '123', null, null, null, null, null, null, null, '0', '28', '2011-09-14 14:42:56', '2011-09-14 16:46:07');
INSERT INTO `users` VALUES ('9', null, '1', null, null, '打哈哈4', '123', null, null, null, null, null, null, null, '0', '2', '2011-09-14 14:47:27', '2011-09-14 15:30:41');
INSERT INTO `users` VALUES ('10', null, '1', null, null, '打哈哈3', '123', null, null, null, null, null, null, null, '0', '2', '2011-09-14 14:48:52', '2011-09-14 15:24:28');
INSERT INTO `users` VALUES ('11', null, '1', null, null, '打哈哈2', '123', null, null, null, null, null, null, null, '0', '2', '2011-09-14 14:50:58', '2011-09-14 15:22:32');
INSERT INTO `users` VALUES ('12', null, '1', null, null, '3123', '123', null, null, null, null, null, null, null, '0', '0', '2011-09-14 17:52:19', '2011-09-14 17:52:19');
INSERT INTO `users` VALUES ('13', null, '1', null, null, 'è±é', '3213123', null, null, null, null, null, null, null, '0', '0', '2011-09-14 17:52:38', '2011-09-14 17:52:38');
INSERT INTO `users` VALUES ('14', null, '1', null, null, '他妈', '123', null, null, null, null, null, null, null, '0', '0', '2011-09-14 17:54:32', '2011-09-14 17:54:32');
