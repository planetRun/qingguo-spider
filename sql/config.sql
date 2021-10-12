/*
 Navicat Premium Data Transfer

 Source Server         : wcw
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : localhost:3306
 Source Schema         : business

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 01/03/2021 13:55:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for config
-- ----------------------------
DROP TABLE IF EXISTS `config`;
CREATE TABLE `config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `param` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `result` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config
-- ----------------------------
INSERT INTO `business`.`config`(`id`, `param`, `result`) VALUES (8, 'schedule_flag', 'false');
INSERT INTO `business`.`config`(`id`, `param`, `result`) VALUES (9, 'validateUrl', 'http://113.140.71.214:88/jwweb/sys/ValidateCode.aspx?t=');
INSERT INTO `business`.`config`(`id`, `param`, `result`) VALUES (10, 'host', '113.140.71.214:88');
INSERT INTO `business`.`config`(`id`, `param`, `result`) VALUES (11, 'login_url', 'http://113.140.71.214:88/jwweb/_data/index_LOGIN.aspx');
INSERT INTO `business`.`config`(`id`, `param`, `result`) VALUES (12, 'score_url', 'http://113.140.71.214:88/jwweb/xscj/Stu_cjfb_rpt.aspx');
INSERT INTO `business`.`config`(`id`, `param`, `result`) VALUES (13, 'school', '13683');
-- ----------------------------
-- Table structure for course_score
-- ----------------------------
DROP TABLE IF EXISTS `course_score`;
CREATE TABLE `course_score`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `xnxq` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `course_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `course_credit` double(5, 2) NOT NULL,
  `course_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `test_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `nature_read` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `score` double(5, 2) NOT NULL,
  `addtime` decimal(16, 0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `xnxq_idx`(`uid`, `xnxq`, `course_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 80 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course_score
-- ----------------------------

-- ----------------------------
-- Table structure for student_score
-- ----------------------------
DROP TABLE IF EXISTS `student_score`;
CREATE TABLE `student_score`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `xnxq` int(6) NOT NULL,
  `xn` int(5) NOT NULL,
  `score_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `addtime` decimal(16, 0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student_score
-- ----------------------------

-- ----------------------------
-- Table structure for student_user
-- ----------------------------
DROP TABLE IF EXISTS `student_user`;
CREATE TABLE `student_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `openId` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `schoolId` int(11) NULL DEFAULT NULL,
  `addtime` decimal(16, 0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student_user
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
