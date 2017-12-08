/*
Navicat MySQL Data Transfer

Source Server         : ning
Source Server Version : 50173
Source Host           : 123.207.244.139:3306
Source Database       : db_got_word

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2017-12-01 20:59:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`name`  varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`password`  varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
PRIMARY KEY (`id`, `name`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=3

;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES ('1', 'user_1', '123'), ('2', 'user_2', '123');
COMMIT;

-- ----------------------------
-- Table structure for word
-- ----------------------------
DROP TABLE IF EXISTS `word`;
CREATE TABLE `word` (
`word`  varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`explains`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`us-phonetic`  varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`phonetic`  varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`uk-phonetic`  varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`text`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
PRIMARY KEY (`word`),
UNIQUE INDEX `word_UNIQUE` USING BTREE (`word`) 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of word
-- ----------------------------
BEGIN;
INSERT INTO `word` VALUES ('word_1', '1', null, null, null, null), ('word_2', '', null, null, null, null), ('word_3', '', null, null, null, null);
COMMIT;

-- ----------------------------
-- Table structure for word_group
-- ----------------------------
DROP TABLE IF EXISTS `word_group`;
CREATE TABLE `word_group` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`name`  varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`user_id`  int(11) NOT NULL ,
`user_name`  varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`count`  int(11) NULL DEFAULT 0 ,
PRIMARY KEY (`id`),
FOREIGN KEY (`user_id`, `user_name`) REFERENCES `user` (`id`, `name`) ON DELETE CASCADE ON UPDATE CASCADE,
INDEX `fk_word_group_user_idx` USING BTREE (`user_id`, `user_name`) 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=5

;

-- ----------------------------
-- Records of word_group
-- ----------------------------
BEGIN;
INSERT INTO `word_group` VALUES ('1', 'group_1', '1', 'user_1', '2'), ('2', 'group_2', '2', 'user_2', '0');
COMMIT;

-- ----------------------------
-- Table structure for word_has_group
-- ----------------------------
DROP TABLE IF EXISTS `word_has_group`;
CREATE TABLE `word_has_group` (
`word_word`  varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`word_group_id`  int(11) NOT NULL ,
PRIMARY KEY (`word_word`, `word_group_id`),
FOREIGN KEY (`word_group_id`) REFERENCES `word_group` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY (`word_word`) REFERENCES `word` (`word`) ON DELETE CASCADE ON UPDATE CASCADE,
INDEX `fk_word_has_word_group_word_group1_idx` USING BTREE (`word_group_id`) ,
INDEX `fk_word_has_word_group_word1_idx` USING BTREE (`word_word`) 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of word_has_group
-- ----------------------------
BEGIN;
INSERT INTO `word_has_group` VALUES ('word_1', '1'), ('word_2', '1'), ('word_3', '1'), ('word_3', '2');
COMMIT;

-- ----------------------------
-- Auto increment value for user
-- ----------------------------
ALTER TABLE `user` AUTO_INCREMENT=3;

-- ----------------------------
-- Auto increment value for word_group
-- ----------------------------
ALTER TABLE `word_group` AUTO_INCREMENT=5;
