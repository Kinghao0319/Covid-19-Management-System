/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50018
Source Host           : localhost:3306
Source Database       : patientmanager

Target Server Type    : MYSQL
Target Server Version : 50018
File Encoding         : 65001

Date: 2020-04-17 20:11:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for s_admin
-- ----------------------------
DROP TABLE IF EXISTS `s_admin`;
CREATE TABLE `s_admin` (
  `id` int(5) NOT NULL auto_increment,
  `username` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `status` tinyint(1) NOT NULL default '1',
  PRIMARY KEY  (`id`,`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_admin
-- ----------------------------
INSERT INTO `s_admin` VALUES ('1', 'admin', '123456', '1');

-- ----------------------------
-- Table structure for s_attendance
-- ----------------------------
DROP TABLE IF EXISTS `s_attendance`;
CREATE TABLE `s_attendance` (
  `id` int(5) NOT NULL auto_increment,
  `examination_id` int(5) NOT NULL,
  `patient_id` int(5) NOT NULL,
  `type` varchar(11) NOT NULL,
  `date` varchar(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `attendance_examination_foreign_key` (`examination_id`),
  KEY `attendace_patient_foreign_key` (`patient_id`),
  CONSTRAINT `attendace_patient_foreign_key` FOREIGN KEY (`patient_id`) REFERENCES `s_patient` (`id`),
  CONSTRAINT `attendance_examination_foreign_key` FOREIGN KEY (`examination_id`) REFERENCES `s_examination` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_attendance
-- ----------------------------
INSERT INTO `s_attendance` VALUES ('13', '1', '2', '上午', '2020-04-17');
INSERT INTO `s_attendance` VALUES ('14', '1', '4', '上午', '2020-04-11');
INSERT INTO `s_attendance` VALUES ('15', '2', '2', '上午', '2020-04-12');

-- ----------------------------
-- Table structure for s_doctor
-- ----------------------------
DROP TABLE IF EXISTS `s_doctor`;
CREATE TABLE `s_doctor` (
  `id` int(5) NOT NULL auto_increment,
  `sn` varchar(32) NOT NULL,
  `username` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `hospital_id` int(5) NOT NULL,
  `sex` varchar(5) NOT NULL default '男',
  `mobile` varchar(12) default NULL,
  `qq` varchar(18) default NULL,
  `photo` varchar(255) default NULL,
  PRIMARY KEY  (`id`,`sn`),
  KEY `patient_hospital_id_foreign` (`hospital_id`),
  CONSTRAINT `s_doctor_ibfk_1` FOREIGN KEY (`hospital_id`) REFERENCES `s_hospital` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_doctor
-- ----------------------------
INSERT INTO `s_doctor` VALUES ('9', 'T11528608730648', '张三', '111', '4', '男', '13918655656', '1193284480', null);
INSERT INTO `s_doctor` VALUES ('10', 'T11528609224588', '李四', '111', '4', '男', '13656565656', '123456', null);
INSERT INTO `s_doctor` VALUES ('11', 'T51528617262403', '钟南山', '123456', '5', '男', '18989898989', '1456655565', null);
INSERT INTO `s_doctor` VALUES ('18', 'T11561727746515', '华佗', '123456', '1', '女', '15174857845', '1745854125', '5d447b8b-ec54-4a8e-919a-453aa7b6d33b.jpg');

-- ----------------------------
-- Table structure for s_examination
-- ----------------------------
DROP TABLE IF EXISTS `s_examination`;
CREATE TABLE `s_examination` (
  `id` int(5) NOT NULL auto_increment,
  `name` varchar(32) NOT NULL,
  `doctor_id` int(5) NOT NULL,
  `examination_date` varchar(32) default NULL,
  `selected_num` int(5) NOT NULL default '0',
  `max_num` int(5) NOT NULL default '50',
  `info` varchar(128) default NULL,
  PRIMARY KEY  (`id`),
  KEY `examination_doctor_foreign` (`doctor_id`),
  CONSTRAINT `examination_doctor_foreign` FOREIGN KEY (`doctor_id`) REFERENCES `s_doctor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_examination
-- ----------------------------
INSERT INTO `s_examination` VALUES ('1', '体温检测1', '9', '周三上午8点', '49', '50', '看看你发烧没有');
INSERT INTO `s_examination` VALUES ('2', '核酸检测', '10', '周三上午10点', '4', '50', '哦');
INSERT INTO `s_examination` VALUES ('3', '体温检测3', '11', '周三上午', '3', '50', '别这样');

-- ----------------------------
-- Table structure for s_hospital
-- ----------------------------
DROP TABLE IF EXISTS `s_hospital`;
CREATE TABLE `s_hospital` (
  `id` int(5) NOT NULL auto_increment,
  `name` varchar(32) NOT NULL,
  `info` varchar(128) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_hospital
-- ----------------------------
INSERT INTO `s_hospital` VALUES ('1', '第一零一医院', '不错');
INSERT INTO `s_hospital` VALUES ('4', '第二医院', '嗯，就这样');
INSERT INTO `s_hospital` VALUES ('5', '第一医院', '生生不息');
INSERT INTO `s_hospital` VALUES ('6', '雷神山医院', '非常强势');

-- ----------------------------
-- Table structure for s_leave
-- ----------------------------
DROP TABLE IF EXISTS `s_leave`;
CREATE TABLE `s_leave` (
  `id` int(5) NOT NULL auto_increment,
  `patient_id` int(5) NOT NULL,
  `info` varchar(512) default NULL,
  `status` tinyint(1) NOT NULL default '0',
  `remark` varchar(512) default NULL,
  PRIMARY KEY  (`id`),
  KEY `leave_patient_foreign_key` (`patient_id`),
  CONSTRAINT `leave_patient_foreign_key` FOREIGN KEY (`patient_id`) REFERENCES `s_patient` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_leave
-- ----------------------------
INSERT INTO `s_leave` VALUES ('13', '2', '世界这么大，想去看看', '1', '同意，你很6');

-- ----------------------------
-- Table structure for s_patient
-- ----------------------------
DROP TABLE IF EXISTS `s_patient`;
CREATE TABLE `s_patient` (
  `id` int(5) NOT NULL auto_increment,
  `sn` varchar(32) NOT NULL,
  `username` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `hospital_id` int(5) NOT NULL,
  `sex` varchar(5) NOT NULL default '男',
  `mobile` varchar(12) default NULL,
  `qq` varchar(18) default NULL,
  `photo` varchar(255) default NULL,
  PRIMARY KEY  (`id`,`sn`),
  KEY `patient_hospital_id_foreign` (`hospital_id`),
  CONSTRAINT `patient_hospital_id_foreign` FOREIGN KEY (`hospital_id`) REFERENCES `s_hospital` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_patient
-- ----------------------------
INSERT INTO `s_patient` VALUES ('2', 'S51528202992845', '张三纷', '123456', '1', '女', '13545454548', '1332365656', null);
INSERT INTO `s_patient` VALUES ('4', 'S51528379586807', '王麻子', '111111', '5', '男', '13356565656', '123456', null);
INSERT INTO `s_patient` VALUES ('9', 'S41528633634989', '马冬梅', '1', '5', '男', '13333332133', '131313132323', '');
INSERT INTO `s_patient` VALUES ('10', 'S11587124098000', '曹操', 'caocao', '1', '男', '18888899668', '8888888', '8298571e-d78f-4eca-b3a9-c180732237e9.jpg');

-- ----------------------------
-- Table structure for s_score
-- ----------------------------
DROP TABLE IF EXISTS `s_score`;
CREATE TABLE `s_score` (
  `id` int(5) NOT NULL auto_increment,
  `patient_id` int(5) NOT NULL,
  `examination_id` int(5) NOT NULL,
  `score` double(5,2) NOT NULL,
  `remark` varchar(128) default NULL,
  PRIMARY KEY  (`id`),
  KEY `selected_examination_patient_fk` (`patient_id`),
  KEY `selected_examination_examination_fk` (`examination_id`),
  CONSTRAINT `s_score_ibfk_1` FOREIGN KEY (`examination_id`) REFERENCES `s_examination` (`id`),
  CONSTRAINT `s_score_ibfk_2` FOREIGN KEY (`patient_id`) REFERENCES `s_patient` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_score
-- ----------------------------
INSERT INTO `s_score` VALUES ('67', '4', '2', '78.00', '病情轻微');
INSERT INTO `s_score` VALUES ('68', '9', '1', '50.00', '病情严重');
INSERT INTO `s_score` VALUES ('69', '10', '2', '91.00', '健康');
INSERT INTO `s_score` VALUES ('70', '2', '2', '99.00', '健康');
INSERT INTO `s_score` VALUES ('71', '9', '2', '40.00', '病情严重');

-- ----------------------------
-- Table structure for s_selected_examination
-- ----------------------------
DROP TABLE IF EXISTS `s_selected_examination`;
CREATE TABLE `s_selected_examination` (
  `id` int(5) NOT NULL auto_increment,
  `patient_id` int(5) NOT NULL,
  `examination_id` int(5) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `selected_examination_patient_fk` (`patient_id`),
  KEY `selected_examination_examination_fk` (`examination_id`),
  CONSTRAINT `selected_examination_examination_fk` FOREIGN KEY (`examination_id`) REFERENCES `s_examination` (`id`),
  CONSTRAINT `selected_examination_patient_fk` FOREIGN KEY (`patient_id`) REFERENCES `s_patient` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_selected_examination
-- ----------------------------
INSERT INTO `s_selected_examination` VALUES ('18', '2', '1');
INSERT INTO `s_selected_examination` VALUES ('19', '2', '2');
INSERT INTO `s_selected_examination` VALUES ('20', '2', '3');
INSERT INTO `s_selected_examination` VALUES ('21', '4', '3');
INSERT INTO `s_selected_examination` VALUES ('22', '4', '2');
INSERT INTO `s_selected_examination` VALUES ('24', '9', '1');
