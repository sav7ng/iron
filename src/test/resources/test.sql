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
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `customer_id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `last_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `company` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `address` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `city` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `country` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `postal_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `fax` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `email` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `support_repld` int(11) DEFAULT NULL,
  PRIMARY KEY (`customer_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of customer
-- ----------------------------
BEGIN;
INSERT INTO `customer` VALUES (1, '1', '2', '北京', '北京', '北京', 1, '北京', '123456', '18316260997', '1', '853029827@qq.com', 1);
COMMIT;

-- ----------------------------
-- Table structure for invoice
-- ----------------------------
DROP TABLE IF EXISTS `invoice`;
CREATE TABLE `invoice` (
  `invoice_id` int(11) NOT NULL AUTO_INCREMENT,
  `invoice_date` date DEFAULT NULL,
  `billing_address` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `billing_city` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `billing_state` int(11) DEFAULT NULL,
  `billing_country` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `billing_postalCode` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `total` decimal(10,2) DEFAULT NULL,
  `customer_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`invoice_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of invoice
-- ----------------------------
BEGIN;
INSERT INTO `invoice` VALUES (1, NULL, '1', 'b', NULL, '2', NULL, 5.00, 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sys_user_id` varchar(128) NOT NULL COMMENT 'UUID',
  `sys_user_name` varchar(128) NOT NULL COMMENT '用户名',
  `sys_user_password` varchar(128) NOT NULL COMMENT '密码\n',
  `sys_user_email` varchar(128) DEFAULT NULL COMMENT '邮箱',
  `sys_user_phone` varchar(64) DEFAULT NULL COMMENT '手机号码',
  `sys_role_id` int(11) DEFAULT NULL COMMENT '权限角色',
  `sys_create_time` timestamp NOT NULL COMMENT '创建时间\n',
  `sys_nick_name` varchar(64) NOT NULL COMMENT '昵称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (1, 'be28341c48ff4209883941adcfbc6022', 'leemus', '76be06d65ef0d2cba8feced0a9e26908', NULL, NULL, NULL, '2019-08-17 11:35:08', 'leemus');
INSERT INTO `sys_user` VALUES (2, '1b1b56e6fa3e4aa49e258f611f971cda', 'leemus', '76be06d65ef0d2cba8feced0a9e26908', NULL, NULL, NULL, '2019-08-17 11:38:28', 'leemus');
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `nick_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `sex` int(1) DEFAULT NULL,
  `register_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1, '89921218@qq.com', '1ee04e0b1cb5af7367c80c22e42efd8b', '土豆', 1, '2017-06-23 14:24:23');
INSERT INTO `user` VALUES (2, '2@qq.com', '1ee04e0b1cb5af7367c80c22e42efd8b', '土豆-2', 1, '2017-06-23 14:24:23');
INSERT INTO `user` VALUES (3, '3@qq.com', '1ee04e0b1cb5af7367c80c22e42efd8b', '土豆-3', 1, '2017-06-23 14:24:23');
INSERT INTO `user` VALUES (4, '4@qq.com', '1ee04e0b1cb5af7367c80c22e42efd8b', '土豆-4', 1, '2017-06-23 14:24:23');
INSERT INTO `user` VALUES (5, '5@qq.com', '1ee04e0b1cb5af7367c80c22e42efd8b', '土豆-5', 1, '2017-06-23 14:24:23');
INSERT INTO `user` VALUES (6, '6@qq.com', '1ee04e0b1cb5af7367c80c22e42efd8b', '土豆-6', 1, '2017-06-23 14:24:23');
INSERT INTO `user` VALUES (7, '7@qq.com', '1ee04e0b1cb5af7367c80c22e42efd8b', '土豆-7', 1, '2017-06-23 14:24:23');
INSERT INTO `user` VALUES (8, '8@qq.com', '1ee04e0b1cb5af7367c80c22e42efd8b', '土豆-8', 1, '2017-06-23 14:24:23');
INSERT INTO `user` VALUES (9, '9@qq.com', '1ee04e0b1cb5af7367c80c22e42efd8b', '土豆-9', 1, '2017-06-23 14:24:23');
INSERT INTO `user` VALUES (10, '10@qq.com', '1ee04e0b1cb5af7367c80c22e42efd8b', '土豆-10', 1, '2017-06-23 14:24:23');
INSERT INTO `user` VALUES (12, 'aquan', '123', 'aquan', 1, '2019-08-10 16:38:56');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
