/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50516
Source Host           : localhost:3306
Source Database       : fire_proofing

Target Server Type    : MYSQL
Target Server Version : 50516
File Encoding         : 65001

Date: 2011-09-19 14:07:40
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of privileges
-- ----------------------------
INSERT INTO `privileges` VALUES ('1', '系统设置', null, '3', null, null, '0', '0', '0', null, 'true', null, '0', '1', '0', '0', '2011-09-13 12:10:52', '2011-09-19 14:07:09');
INSERT INTO `privileges` VALUES ('2', '用户设置', null, '1', '24', null, '1', '0', '0', null, 'true', '', '1', '2', '0', '0', '2011-09-13 12:10:44', '2011-09-19 09:48:41');
INSERT INTO `privileges` VALUES ('3', '系统权限', null, '2', '12', null, '1', '0', '0', null, 'true', '', '1', '1', '0', '0', '2011-09-13 12:10:38', '2011-09-17 11:56:56');
INSERT INTO `privileges` VALUES ('4', '登录日志', null, '1', '24', null, '1', null, '0', null, 'true', '', '1', '3', '0', '0', '2011-09-17 12:55:37', '2011-09-19 09:51:46');
INSERT INTO `privileges` VALUES ('5', '防火设置', null, null, null, null, '0', null, '0', null, 'false', '防火设置', '0', '2', '0', '0', '2011-09-19 11:13:53', '2011-09-19 14:07:07');
INSERT INTO `privileges` VALUES ('6', '角色设置', null, null, null, null, '0', null, '0', null, 'false', '角色设置', '0', '0', '0', '0', '2011-09-19 14:07:04', '2011-09-19 14:07:09');

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of privilege_details
-- ----------------------------
INSERT INTO `privilege_details` VALUES ('1', '2', '所到所有用户', '1', '1', null, null, null, '', null, '0', '2011-09-14 12:30:16', '2011-09-16 16:21:38', null);
INSERT INTO `privilege_details` VALUES ('2', '2', '新增用户', '1', '2', null, null, null, '', null, null, '2011-09-14 12:48:15', '2011-09-15 13:58:16', null);
INSERT INTO `privilege_details` VALUES ('3', '2', '更新用户', '1', '3', null, null, null, '', null, '0', '2011-09-14 13:00:59', '2011-09-15 14:33:14', null);

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
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_controllers
-- ----------------------------
INSERT INTO `sys_controllers` VALUES ('1', 'user', '用户控制', '0', '0', '2011-09-13 11:42:57', '2011-09-13 11:43:00');
INSERT INTO `sys_controllers` VALUES ('2', 'privilege', '系统权限', '0', '0', '2011-09-13 11:42:49', '2011-09-13 11:42:54');
INSERT INTO `sys_controllers` VALUES ('3', 'admin', '管理员', '0', '0', '2011-09-13 11:47:54', '2011-09-13 11:47:57');
INSERT INTO `sys_controllers` VALUES ('4', 'index', '主控', null, null, '2011-09-15 18:41:32', '2011-09-15 18:41:32');

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
