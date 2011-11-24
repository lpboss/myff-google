/*
MySQL Data Transfer
Source Host: localhost
Source Database: fire_proofing
Target Host: localhost
Target Database: fire_proofing
Date: 2011/11/23 10:38:29
*/

SET FOREIGN_KEY_CHECKS=0;
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
  `shift_step` smallint(6) DEFAULT '10' COMMENT '云台非巡航状态下默认移动步长',
  `cruise_right_limit` smallint(6) DEFAULT '0' COMMENT '巡航右边界',
  `cruise_left_limit` smallint(6) DEFAULT '0' COMMENT '巡航左边界',
  `cruise_up_limit` smallint(6) DEFAULT '90' COMMENT '最大上仰角度',
  `cruise_down_limit` smallint(6) DEFAULT '0' COMMENT '巡航时最大俯角',
  `alarm_heat_value` smallint(6) DEFAULT '30000' COMMENT '报警警戒热值',
  `is_alarm` tinyint(4) DEFAULT '0' COMMENT '当前是否报警',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL COMMENT '本版',
  `is_locked` tinyint(4) DEFAULT NULL COMMENT '启用，停用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `ptzs` VALUES ('1', '测试机1', 'rtsp://admin:12345@192.168.254.64/h264/ch1/main/av_stream', '192.168.254.65', '2', '2', '2', '192.168.1.50', '192.168.1.50', '2', '2', '50', '38', '382', '288', 'FY', '5', '10', '2', '2', '90', '2', '30000', '0', '2011-11-23 15:33:39', '2011-11-16 15:33:43', '2', '0');
INSERT INTO `ptzs` VALUES ('2', '11', '11', '11', '11', '11', '11', '11', '11', '11', '11', '11', '11', '11', '11', '亚安', '11', '10', '11', '11', '11', '11', '30000', '0', '2011-11-18 15:17:48', '2011-11-18 15:17:48', '11', '11');
INSERT INTO `ptzs` VALUES ('3', '33', '33', '33', '33', '33', '33', '33', '33', '33', '33', '33', '33', '33', '33', '飞越', '33', '10', null, null, null, null, '30000', '0', '2011-11-18 15:34:45', '2011-11-18 15:34:45', '33', null);
INSERT INTO `ptzs` VALUES ('4', '44', '44', '44', '44', '44', '44', '44', '44', '44', '44', '44', '44', '44', '44', '飞越', '44', '10', '44', '44', '44', '44', '30000', '0', '2011-11-18 15:39:48', '2011-11-18 15:39:48', '44', null);
INSERT INTO `ptzs` VALUES ('5', '55', '55', '55', '55', '55', '55', '55', '55', '55', '55', '55', '55', '55', '55', '亚安', '55', '10', '55', '55', '55', '55', '30000', '0', '2011-11-22 10:11:41', '2011-11-22 10:11:41', '55', null);
INSERT INTO `ptzs` VALUES ('6', '66', '66', '66', '66', '66', '66', '66', '66', '66', '66', '66', '66', '66', '66', '飞越', '66', '10', '66', '66', '66', '66', '30000', '0', '2011-11-22 10:35:55', '2011-11-22 10:35:55', '66', null);
