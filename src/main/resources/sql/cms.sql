/*
 Navicat MySQL Data Transfer

 Source Server         : xn
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : localhost:3306
 Source Schema         : cms

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 26/09/2020 18:04:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for config
-- ----------------------------
DROP TABLE IF EXISTS `config`;
CREATE TABLE `config`  (
  `id` bigint(20) NOT NULL,
  `sys_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sys_value` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `status` int(4) NULL DEFAULT NULL,
  `remark` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config
-- ----------------------------
INSERT INTO `config` VALUES (1, 'sys_name', '内容管理系统', 0, NULL);

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `permission_id` bigint(64) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `status` int(11) NOT NULL,
  `perms` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `url` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `icon` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `order_num` int(11) NULL DEFAULT NULL,
  `parent_id` bigint(64) NULL DEFAULT NULL,
  `type` int(11) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`permission_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20305 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, '工作台', 0, 'work', '/work', '工作台', NULL, 1, NULL, 1, '2020-08-19 21:54:19', '2020-08-19 21:54:19');
INSERT INTO `permission` VALUES (2, '权限管理', 0, NULL, NULL, '权限管理', NULL, 1, 1, 1, '2020-08-19 21:54:19', '2020-08-19 21:54:19');
INSERT INTO `permission` VALUES (201, '用户管理', 0, 'admin:users', '/admin/users', '用户管理', NULL, 2, 2, 1, '2020-08-19 21:54:19', '2020-08-19 21:54:19');
INSERT INTO `permission` VALUES (202, '角色管理', 0, 'admin:roles', '/admin/roles', '角色管理', NULL, 2, 2, 1, '2020-08-19 21:54:19', '2020-08-19 21:54:19');
INSERT INTO `permission` VALUES (203, '资源管理', 0, 'admin:permissions', '/admin/permissions', '资源管理', NULL, 2, 2, 1, '2020-08-19 21:54:19', '2020-08-19 21:54:19');
INSERT INTO `permission` VALUES (206, '工具管理', 0, 'admin:utils', '/admin/utils', '工具管理', NULL, 2, 1, 1, '2020-08-19 21:54:19', '2020-08-19 21:54:19');
INSERT INTO `permission` VALUES (20101, '用户列表', 0, 'admin:user:list', '/admin/user/list', '用户列表查询', NULL, 0, 201, 2, '2020-08-19 21:54:19', '2020-08-19 21:54:19');
INSERT INTO `permission` VALUES (20102, '添加用户', 0, 'admin:user:add', '/admin/user/add', '添加用户', NULL, 0, 201, 2, '2020-08-19 21:54:19', '2020-08-19 21:54:19');
INSERT INTO `permission` VALUES (20103, '编辑用户', 0, 'admin:user:edit', '/admin/user/edit', '编辑用户', NULL, 0, 201, 2, '2020-08-19 21:54:19', '2020-08-19 21:54:19');
INSERT INTO `permission` VALUES (20104, '删除用户', 0, 'admin:user:delete', '/admin/user/delete', '删除用户', NULL, 0, 201, 2, '2020-08-19 21:54:19', '2020-08-19 21:54:19');
INSERT INTO `permission` VALUES (20105, '批量删除用户', 0, 'admin:user:batchDelete', '/admin/user/batch/delete', '批量删除用户', NULL, 0, 201, 2, '2020-08-19 21:54:19', '2020-08-19 21:54:19');
INSERT INTO `permission` VALUES (20106, '修改状态', 0, 'admin:user:changeStatus', '/admin/user/change_status', '修改状态', NULL, 0, 201, 2, '2020-08-19 21:54:19', '2020-08-19 21:54:19');
INSERT INTO `permission` VALUES (20107, '重置密码', 0, 'admin:user:reset', '/admin/user/reset', '重置密码', NULL, 0, 201, 2, '2020-08-19 21:54:19', '2020-08-19 21:54:19');
INSERT INTO `permission` VALUES (20201, '角色列表', 0, 'admin:role:list', '/admin/role/list', '角色列表查询', NULL, 0, 202, 2, '2020-08-19 21:54:19', '2020-08-19 21:54:19');
INSERT INTO `permission` VALUES (20202, '添加角色', 0, 'admin:role:add', '/admin/role/add', '添加用户', NULL, 0, 202, 2, '2020-08-19 21:54:19', '2020-08-19 21:54:19');
INSERT INTO `permission` VALUES (20203, '编辑角色', 0, 'admin:role:edit', '/admin/role/edit', '编辑角色', NULL, 0, 202, 2, '2020-08-19 21:54:19', '2020-08-19 21:54:19');
INSERT INTO `permission` VALUES (20204, '删除角色', 0, 'admin:role:delete', '/admin/role/delete', '删除角色', NULL, 0, 202, 2, '2020-08-19 21:54:19', '2020-08-19 21:54:19');
INSERT INTO `permission` VALUES (20205, '批量删除', 0, 'admin:role:batchDelete', '/admin/role/batch/delete', '批量删除角色', NULL, 0, 202, 2, '2020-08-19 21:54:19', '2020-08-19 21:54:19');
INSERT INTO `permission` VALUES (20206, '分配权限', 0, 'admin:role:assignPerms', '/admin/role/assign/permission', '分配权限', NULL, 0, 202, 2, '2020-08-19 21:54:19', '2020-08-19 21:54:19');
INSERT INTO `permission` VALUES (20301, '资源列表', 0, 'admin:permission:list', '/admin/permission/list', '资源列表', NULL, 0, 203, 2, '2020-08-19 21:54:19', '2020-08-19 21:54:19');
INSERT INTO `permission` VALUES (20302, '添加资源', 0, 'admin:permission:add', '/admin/permission/add', '添加资源', NULL, 0, 203, 2, '2020-08-19 21:54:19', '2020-08-19 21:54:19');
INSERT INTO `permission` VALUES (20303, '编辑资源', 0, 'admin:permission:edit', '/admin/permission/edit', '编辑资源', NULL, 0, 203, 2, '2020-08-19 21:54:19', '2020-08-19 21:54:19');
INSERT INTO `permission` VALUES (20304, '删除资源', 0, 'admin:permission:delete', '/admin/permission/delete', '删除资源', NULL, 0, 203, 2, '2020-08-19 21:54:19', '2020-08-19 21:54:19');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `role_id` bigint(64) NOT NULL AUTO_INCREMENT,
  `role_key` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `status` int(10) NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `description` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'admin', '超级管理员', 0, '2020-08-19 21:54:19', '2020-08-19 21:54:25', '超级管理员');
INSERT INTO `role` VALUES (2, 'common', '普通管理员', 0, '2020-08-19 21:54:54', '2020-08-19 15:30:46', '普通管理员');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `role_permission_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `permission_id` bigint(64) NOT NULL,
  `role_id` bigint(64) NOT NULL,
  PRIMARY KEY (`role_permission_id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE,
  INDEX `permission_id`(`permission_id`) USING BTREE,
  CONSTRAINT `role_permission_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `role_permission_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`permission_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 85 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES (75, 1, 2);
INSERT INTO `role_permission` VALUES (76, 2, 2);
INSERT INTO `role_permission` VALUES (77, 201, 2);
INSERT INTO `role_permission` VALUES (78, 20101, 2);
INSERT INTO `role_permission` VALUES (79, 20102, 2);
INSERT INTO `role_permission` VALUES (80, 20103, 2);
INSERT INTO `role_permission` VALUES (81, 20104, 2);
INSERT INTO `role_permission` VALUES (82, 20105, 2);
INSERT INTO `role_permission` VALUES (83, 20106, 2);
INSERT INTO `role_permission` VALUES (84, 20107, 2);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` bigint(64) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `nick_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `salt` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `status` int(10) NOT NULL,
  `img` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `last_login_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `login_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `user_user_name_uindex`(`user_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 42 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '小鸟', '3629fa3178bb9f8b71a34f2fe9980281', 'ff196c1512a47423393f1c651d308743', 0, NULL, '2020-08-19 21:54:19', '2020-08-20 08:32:01', '2020-08-19 21:54:19', '0:0:0:0:0:0:0:1', NULL);
INSERT INTO `user` VALUES (38, 'xiaoniao', '鲁班', '1016f0813bf5e29cac56887e742d6ceb', 'be8806cee341d3507a5a9faea582c663', 0, '{$vo.head_pic|default=\'\'}', '2020-08-19 21:54:19', '2020-08-19 21:54:19', '2020-08-19 21:54:19', '0:0:0:0:0:0:0:1', '');
INSERT INTO `user` VALUES (41, 'luban', '21412412421412', '93be56fbc34eb925c78ab199168f1650', '273d4d397af560f2bab0a478ec7153cd', 0, '{$vo.head_pic|default=\'\'}', '2020-08-19 21:54:19', '2020-08-19 21:54:19', '2020-08-19 21:54:19', '0:0:0:0:0:0:0:1', '');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `user_role_id` bigint(64) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(64) NOT NULL,
  `role_id` bigint(64) NOT NULL,
  PRIMARY KEY (`user_role_id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
