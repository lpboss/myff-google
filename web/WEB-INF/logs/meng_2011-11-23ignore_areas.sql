/*
MySQL Data Transfer
Source Host: localhost
Source Database: fire_proofing
Target Host: localhost
Target Database: fire_proofing
Date: 2011/11/23 10:38:53
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for ignore_areas
-- ----------------------------
CREATE TABLE `ignore_areas` (
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
  `version` int(11) DEFAULT '0' COMMENT '本版',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `ignore_areas` VALUES ('1', '1', '1', '1', '11', '11', '2011-11-01 17:00:31', '2011-11-30 17:00:36', '1', '2011-11-02 17:00:43', '2011-11-04 17:00:48', '1');
INSERT INTO `ignore_areas` VALUES ('2', '3', '3', '3', '3', '3', '2011-11-01 10:35:11', '2011-11-02 10:35:13', null, '2011-11-22 10:35:17', '2011-11-22 10:35:17', '3');
