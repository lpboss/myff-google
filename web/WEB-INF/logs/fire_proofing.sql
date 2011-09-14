/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50515
Source Host           : localhost:3306
Source Database       : fire_proofing

Target Server Type    : MYSQL
Target Server Version : 50515
File Encoding         : 65001

Date: 2011-09-14 13:48:28
*/

SET FOREIGN_KEY_CHECKS=0;

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of privileges
-- ----------------------------
INSERT INTO `privileges` VALUES ('1', '系统设置', null, '3', null, null, '0', '0', '0', null, 'true', null, '0', '0', '0', '0', '2011-09-13 12:10:52', '2011-09-13 12:10:50');
INSERT INTO `privileges` VALUES ('2', '用户设置', null, '1', '4', null, '1', '0', '0', null, 'true', null, '1', '0', '0', '0', '2011-09-13 12:10:44', '2011-09-13 12:10:47');
INSERT INTO `privileges` VALUES ('3', '系统权限', null, '2', '7', null, '1', '0', '0', null, 'true', null, '1', '0', '0', '0', '2011-09-13 12:10:38', '2011-09-13 12:10:41');

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
  `lock_version` int(11) DEFAULT '0',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of privilege_details
-- ----------------------------
INSERT INTO `privilege_details` VALUES ('1', '2', 'å¾æå°æç¨æ·', '1', '1', null, null, null, '', null, null, '0', '2011-09-14 12:30:16', '2011-09-14 12:30:16', null);
INSERT INTO `privilege_details` VALUES ('2', '2', 'æ°å¢ç¨æ·', '1', '2', null, null, null, '', null, null, '0', '2011-09-14 12:48:15', '2011-09-14 12:48:15', null);
INSERT INTO `privilege_details` VALUES ('3', '2', 'æ´æ°ç¨æ·ä¿¡æ¯', '1', '3', null, null, null, '', null, null, '0', '2011-09-14 13:00:59', '2011-09-14 13:00:59', null);

-- ----------------------------
-- Table structure for `roles`
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_locked` tinyint(4) DEFAULT '1',
  `version` int(11) DEFAULT '0',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES ('1', '管理员', null, '1', '0', '2011-09-09 15:25:30', '2011-09-09 15:25:35');

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of roles_privilege_details
-- ----------------------------
INSERT INTO `roles_privilege_details` VALUES ('1', '1', '1', '2', '1', null, '0', '0', '2011-09-13 12:11:34', '2011-09-13 12:11:36');
INSERT INTO `roles_privilege_details` VALUES ('2', '1', '1', '3', '2', null, '0', '0', '2011-09-13 12:11:38', '2011-09-13 12:11:41');

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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_actions
-- ----------------------------
INSERT INTO `sys_actions` VALUES ('1', '1', 'getUsers', null, '0', '0', '2011-09-14 10:08:40', '2011-09-14 10:08:44');
INSERT INTO `sys_actions` VALUES ('2', '1', 'create', null, '0', '0', '2011-09-14 10:08:47', '2011-09-14 10:08:50');
INSERT INTO `sys_actions` VALUES ('3', '1', 'update', null, '0', '0', '2011-09-14 10:08:53', '2011-09-14 10:08:56');
INSERT INTO `sys_actions` VALUES ('4', '1', 'userList', null, '0', '0', '2011-09-14 10:08:59', '2011-09-14 10:09:02');
INSERT INTO `sys_actions` VALUES ('5', '2', 'getSysPrivilegeChildrenById', null, '0', '0', '2011-09-14 10:09:05', '2011-09-14 10:09:10');
INSERT INTO `sys_actions` VALUES ('6', '2', 'getPrivilegeDetailsById', null, '0', '0', '2011-09-14 10:09:13', '2011-09-14 10:09:16');
INSERT INTO `sys_actions` VALUES ('7', '2', 'systemPrivilege', null, '0', '0', '2011-09-14 10:09:19', '2011-09-14 10:09:21');

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_controllers
-- ----------------------------
INSERT INTO `sys_controllers` VALUES ('1', 'user', '用户控制', '0', '0', '2011-09-13 11:42:57', '2011-09-13 11:43:00');
INSERT INTO `sys_controllers` VALUES ('2', 'privilege', '系统权限', '0', '0', '2011-09-13 11:42:49', '2011-09-13 11:42:54');
INSERT INTO `sys_controllers` VALUES ('3', 'admin', '管理员', '0', '0', '2011-09-13 11:47:54', '2011-09-13 11:47:57');

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', null, '1', null, 'jerry', 'jerry', '111111', null, null, null, null, null, null, null, '0', '2', '2011-09-09 15:26:09', '2011-09-13 11:09:37');
INSERT INTO `users` VALUES ('2', null, '1', null, null, 'é¿å¸', '111111', null, null, null, null, null, null, null, '0', '0', '2011-09-14 13:40:56', '2011-09-14 13:40:56');
