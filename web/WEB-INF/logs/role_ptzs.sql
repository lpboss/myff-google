/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50517
Source Host           : localhost:3306
Source Database       : fire_proofing

Target Server Type    : MYSQL
Target Server Version : 50517
File Encoding         : 65001

Date: 2011-11-23 14:48:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `role_ptzs`
-- ----------------------------
DROP TABLE IF EXISTS `role_ptzs`;
CREATE TABLE `role_ptzs` (
  `ptz_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_ptzs
-- ----------------------------
