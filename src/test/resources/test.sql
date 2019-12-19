/*
 Navicat Premium Data Transfer

 Source Server         : leemus
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : localhost:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 19/08/2019 20:11:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `nick_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `roles` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `sex` int(1) DEFAULT NULL,
  `register_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1, '89921218@qq.com', '1ee04e0b1cb5af7367c80c22e42efd8b', '土豆', 'DEV,PM', 1, '2017-06-23 14:24:23');
INSERT INTO `user` VALUES (2, '2@qq.com', '1ee04e0b1cb5af7367c80c22e42efd8b', '土豆-2', 'DEV,PM', 1, '2017-06-23 14:24:23');
INSERT INTO `user` VALUES (3, '3@qq.com', '1ee04e0b1cb5af7367c80c22e42efd8b', '土豆-3', 'DEV,PM', 1, '2017-06-23 14:24:23');
INSERT INTO `user` VALUES (4, '4@qq.com', '1ee04e0b1cb5af7367c80c22e42efd8b', '土豆-4', 'DEV,PM', 1, '2017-06-23 14:24:23');
INSERT INTO `user` VALUES (5, '5@qq.com', '1ee04e0b1cb5af7367c80c22e42efd8b', '土豆-5', 'DEV,PM', 1, '2017-06-23 14:24:23');
INSERT INTO `user` VALUES (6, '6@qq.com', '1ee04e0b1cb5af7367c80c22e42efd8b', '土豆-6', 'DEV,PM', 1, '2017-06-23 14:24:23');
INSERT INTO `user` VALUES (7, '7@qq.com', '1ee04e0b1cb5af7367c80c22e42efd8b', '土豆-7', 'DEV,PM', 1, '2017-06-23 14:24:23');
INSERT INTO `user` VALUES (8, '8@qq.com', '1ee04e0b1cb5af7367c80c22e42efd8b', '土豆-8', 'DEV,PM', 1, '2017-06-23 14:24:23');
INSERT INTO `user` VALUES (9, '9@qq.com', '1ee04e0b1cb5af7367c80c22e42efd8b', '土豆-9', 'DEV,PM', 1, '2017-06-23 14:24:23');
INSERT INTO `user` VALUES (10, '10@qq.com', '1ee04e0b1cb5af7367c80c22e42efd8b', '土豆-10', 'DEV,PM', 1, '2017-06-23 14:24:23');
INSERT INTO `user` VALUES (12, 'aquan', '123', 'aquan', 'DEV,PM', 1, '2019-08-10 16:38:56');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
