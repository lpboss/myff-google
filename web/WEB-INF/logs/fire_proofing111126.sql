/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50517
Source Host           : localhost:3306
Source Database       : fire_proofing

Target Server Type    : MYSQL
Target Server Version : 50517
File Encoding         : 65001

Date: 2011-12-01 13:54:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `alarm_ignore_areas`
-- ----------------------------
DROP TABLE IF EXISTS `alarm_ignore_areas`;
CREATE TABLE `alarm_ignore_areas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ptz_id` int(11) NOT NULL COMMENT '云台的编号',
  `ptz_angel_x` smallint(6) DEFAULT '0' COMMENT '火警时云台的水平角度',
  `ptz_angel_y` smallint(6) DEFAULT NULL COMMENT '火警时云台的Y角度',
  `ccd_area` smallint(6) DEFAULT '0' COMMENT '热成像起火面积值',
  `heat_max` smallint(6) DEFAULT '0' COMMENT '最大热值',
  `begin_date` datetime DEFAULT NULL COMMENT '火警时间范围',
  `end_date` datetime DEFAULT NULL COMMENT '火警时间范围',
  `is_locked` tinyint(4) DEFAULT '0',
  `updated_at` datetime NOT NULL,
  `created_at` datetime NOT NULL,
  `version` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of alarm_ignore_areas
-- ----------------------------

-- ----------------------------
-- Table structure for `fire_alarms`
-- ----------------------------
DROP TABLE IF EXISTS `fire_alarms`;
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
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fire_alarms
-- ----------------------------
INSERT INTO `fire_alarms` VALUES ('25', '1', '2011-11-30 18:38:38', '38', '0', '5831', null, null, null, null, '2011-11-30 18:38:38', '1', '2011-11-30 15:55:48', '2011-11-30 15:55:48', null, '0');
INSERT INTO `fire_alarms` VALUES ('26', '1', '2011-11-30 18:38:41', '38', '0', '5590', null, null, null, null, '2011-11-30 18:38:38', '1', '2011-11-30 15:56:13', '2011-11-30 15:56:13', null, '0');
INSERT INTO `fire_alarms` VALUES ('27', '1', '2011-11-30 18:38:44', '50', '38', '7402', null, null, null, null, '2011-11-30 18:38:38', '1', '2011-11-30 16:50:21', '2011-11-30 16:50:21', null, '0');
INSERT INTO `fire_alarms` VALUES ('28', '1', '2011-12-01 11:20:17', '50', '38', '2530', null, null, null, null, '2011-12-01 11:20:17', '1', '2011-12-01 11:20:17', '2011-12-01 11:20:17', null, '0');
INSERT INTO `fire_alarms` VALUES ('29', '1', '2011-12-01 11:29:06', '50', '38', '1354', null, null, null, null, '2011-12-01 11:29:06', '1', '2011-12-01 11:29:06', '2011-12-01 11:29:06', null, '0');
INSERT INTO `fire_alarms` VALUES ('30', '1', '2011-12-01 11:44:01', '50', '38', '3588', null, null, null, null, '2011-12-01 11:44:01', '1', '2011-12-01 11:44:01', '2011-12-01 11:44:01', null, '0');
INSERT INTO `fire_alarms` VALUES ('31', '1', '2011-12-01 11:45:00', '50', '38', '2495', null, null, null, null, '2011-12-01 11:45:00', '1', '2011-12-01 11:45:00', '2011-12-01 11:45:00', null, '0');

-- ----------------------------
-- Table structure for `privileges`
-- ----------------------------
DROP TABLE IF EXISTS `privileges`;
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
-- Records of privileges
-- ----------------------------
INSERT INTO `privileges` VALUES ('1', '系统设置', null, '3', null, null, '0', '0', '0', null, 'false', null, '0', '2', '0', '0', '2011-09-13 12:10:52', '2011-09-30 10:06:01');
INSERT INTO `privileges` VALUES ('2', '用户设置', null, '1', '24', null, '1', '0', '0', null, 'true', '', '1', '1', '0', '0', '2011-09-13 12:10:44', '2011-11-22 16:06:31');
INSERT INTO `privileges` VALUES ('3', '系统权限', null, '2', '12', null, '1', '0', '0', null, 'true', '', '1', '2', '0', '0', '2011-09-13 12:10:38', '2011-11-22 16:06:31');
INSERT INTO `privileges` VALUES ('7', '角色设置', null, '3', '32', null, '1', null, '0', null, 'true', '', '1', '4', '0', '0', '2011-09-19 14:16:38', '2011-09-19 14:16:38');
INSERT INTO `privileges` VALUES ('8', '防火监控', null, null, null, null, '0', null, '0', null, 'false', '包括PTZ控制，起火点确认，历史记录查询', '0', '1', '0', '0', '2011-09-30 10:05:57', '2011-11-16 18:28:55');
INSERT INTO `privileges` VALUES ('9', '云台信息', null, '5', '50', null, '1', null, '0', null, 'true', '云台信息，通过其中的云台列表，取得控制权', '8', '1', '0', '0', '2011-09-30 10:55:19', '2011-09-30 10:55:19');
INSERT INTO `privileges` VALUES ('10', '历史火警记录', null, '6', '52', null, '1', null, '0', null, 'true', '', '8', '2', '0', '0', '2011-11-16 18:28:16', '2011-11-16 18:28:16');
INSERT INTO `privileges` VALUES ('11', '云台管理', null, '5', '61', null, '1', null, '0', null, 'true', '', '8', '3', '0', '0', '2011-11-16 18:28:55', '2011-11-16 18:28:55');

-- ----------------------------
-- Table structure for `privilege_details`
-- ----------------------------
DROP TABLE IF EXISTS `privilege_details`;
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
-- Records of privilege_details
-- ----------------------------
INSERT INTO `privilege_details` VALUES ('1', '2', '所到所有用户', '1', '1', null, null, null, '', null, '0', '2011-09-14 12:30:16', '2011-09-16 16:21:38', '0');
INSERT INTO `privilege_details` VALUES ('2', '2', '新增用户', '1', '2', null, null, null, '', null, null, '2011-09-14 12:48:15', '2011-09-15 13:58:16', '0');
INSERT INTO `privilege_details` VALUES ('3', '2', '更新用户', '1', '3', null, null, null, '', null, '0', '2011-09-14 13:00:59', '2011-09-15 14:33:14', '0');
INSERT INTO `privilege_details` VALUES ('4', '7', '新增角色', '3', '33', null, null, null, '', '0', '0', '2011-09-19 14:55:00', '2011-09-19 14:55:00', '0');
INSERT INTO `privilege_details` VALUES ('5', '9', 'PTZ', '5', '50', null, null, null, '', '0', '0', '2011-09-30 11:25:57', '2011-09-30 11:25:57', '0');
INSERT INTO `privilege_details` VALUES ('6', '10', '得到所有火警信息', '6', '53', null, null, null, '', '0', '0', '2011-11-16 18:29:28', '2011-11-16 18:29:28', '0');
INSERT INTO `privilege_details` VALUES ('7', '11', '添加云台', '5', '55', null, null, null, '', '0', '0', '2011-11-16 18:30:46', '2011-11-16 18:30:46', '0');

-- ----------------------------
-- Table structure for `ptzs`
-- ----------------------------
DROP TABLE IF EXISTS `ptzs`;
CREATE TABLE `ptzs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `controll_url` varchar(255) DEFAULT NULL COMMENT '编码器IP',
  `pelcod_command_url` varchar(255) DEFAULT NULL COMMENT '通过串口,发pelcod的ip',
  `visible_camera_url` varchar(255) DEFAULT NULL COMMENT '可见光摄像机地址,模拟请参考controll_url',
  `visible_rtsp_url` varchar(255) DEFAULT NULL COMMENT '可见光RTSP流',
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
  `cruise_angle_y_step` tinyint(4) DEFAULT '10' COMMENT '巡航上扬角度步长',
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
-- Records of ptzs
-- ----------------------------
INSERT INTO `ptzs` VALUES ('1', '测试机，主云台1', 'rtsp://admin:12345@192.168.254.64/h264/ch1/main/av_stream', '192.168.254.65', null, 'ntrt://192.168.254.63/1/8000/admin/12345', 'ntrt://192.168.254.64/1/8000/admin/12345', '192.168.1.50', '192.168.1.50', '0', 'map.png', '50', '38', '382', '288', 'FY', '5', '10', '10', 'LR', '80', '60', '80', '20', '1300', '0', '2011-11-22 15:34:39', '2011-12-01 11:46:35', null, '0');

-- ----------------------------
-- Table structure for `roles`
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
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
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES ('1', '管理员', '管理员', null, '0', '12', '2011-09-09 15:25:30', '2011-09-20 17:41:56');
INSERT INTO `roles` VALUES ('2', 'æµè¯', 'æµè¯', null, '0', '0', '2011-09-20 17:46:37', '2011-09-20 17:46:37');
INSERT INTO `roles` VALUES ('3', '测试', '测试', null, '0', '0', '2011-09-20 17:57:03', '2011-09-20 17:57:03');

-- ----------------------------
-- Table structure for `roles_privilege_details`
-- ----------------------------
DROP TABLE IF EXISTS `roles_privilege_details`;
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
-- Records of roles_privilege_details
-- ----------------------------
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

-- ----------------------------
-- Table structure for `role_ptzs`
-- ----------------------------
DROP TABLE IF EXISTS `role_ptzs`;
CREATE TABLE `role_ptzs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `ptz_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_ptzs
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_actions`
-- ----------------------------
DROP TABLE IF EXISTS `sys_actions`;
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
-- Records of sys_actions
-- ----------------------------
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

-- ----------------------------
-- Table structure for `sys_controllers`
-- ----------------------------
DROP TABLE IF EXISTS `sys_controllers`;
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
-- Records of sys_controllers
-- ----------------------------
INSERT INTO `sys_controllers` VALUES ('1', 'user', '用户控制', '0', '0', '2011-09-13 11:42:57', '2011-09-13 11:43:00');
INSERT INTO `sys_controllers` VALUES ('2', 'privilege', '系统权限', '0', '0', '2011-09-13 11:42:49', '2011-09-13 11:42:54');
INSERT INTO `sys_controllers` VALUES ('3', 'admin', '管理员', '0', '0', '2011-09-13 11:47:54', '2011-09-13 11:47:57');
INSERT INTO `sys_controllers` VALUES ('4', 'index', '主控', null, null, '2011-09-15 18:41:32', '2011-09-15 18:41:32');
INSERT INTO `sys_controllers` VALUES ('5', 'ptz', null, '0', '0', '2011-09-30 10:07:34', '2011-09-30 10:07:34');
INSERT INTO `sys_controllers` VALUES ('6', 'firealarm', null, '0', '0', '2011-11-16 18:27:37', '2011-11-16 18:27:37');

-- ----------------------------
-- Table structure for `users`
-- ----------------------------
DROP TABLE IF EXISTS `users`;
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
-- Records of users
-- ----------------------------
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
