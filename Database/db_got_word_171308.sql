/*
Navicat MySQL Data Transfer

Source Server         : ning
Source Server Version : 50173
Source Host           : 123.207.244.139:3306
Source Database       : db_got_word

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2017-12-08 14:04:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`id`,`name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'ninggc', ' ');
INSERT INTO `user` VALUES ('2', 'user_2', '123');
INSERT INTO `user` VALUES ('3', 'test', 'test');
INSERT INTO `user` VALUES ('4', 'test', 'test');
INSERT INTO `user` VALUES ('5', 'test', 'test');
INSERT INTO `user` VALUES ('6', 'test', 'test');

-- ----------------------------
-- Table structure for word
-- ----------------------------
DROP TABLE IF EXISTS `word`;
CREATE TABLE `word` (
  `word` varchar(45) NOT NULL,
  `explains` varchar(100) NOT NULL,
  `us-phonetic` varchar(45) DEFAULT NULL,
  `phonetic` varchar(100) DEFAULT NULL,
  `uk-phonetic` varchar(45) DEFAULT NULL,
  `text` text,
  PRIMARY KEY (`word`),
  UNIQUE KEY `word_UNIQUE` (`word`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of word
-- ----------------------------
INSERT INTO `word` VALUES ('advance', 'n. 发展；前进；增长；预付款\r\nvt. 提出；预付；使……前进；将……提前\r\nvi. 前进；进展；上涨\r\nadj. 预先的；先行的', null, '英 [əd\'vɑːns]  美 [əd\'væns]', null, null);
INSERT INTO `word` VALUES ('bound', 'adj. 有义务的；必定的；受约束的；装有封面的\r\nvt. 束缚；使跳跃\r\nn. 范围；跳跃\r\nvi. 限制；弹起', null, '英 [baʊnd]  美 [baʊnd]', null, null);
INSERT INTO `word` VALUES ('confront', 'vt. 面对；遭遇；比较', null, '英 [kən\'frʌnt]  美 [kən\'frʌnt]', null, null);
INSERT INTO `word` VALUES ('consumption', 'n. 消费；消耗；肺痨', null, '英 [kən\'sʌm(p)ʃ(ə)n]  美 [kən\'sʌmpʃən]', null, null);
INSERT INTO `word` VALUES ('dispute', 'vt. 辩论；怀疑；阻止；抗拒\r\nvi. 争论\r\nn. 辩论；争吵', null, '英 [dɪ\'spjuːt; \'dɪspjuːt]  美 [\'dɪs\'pjʊt]', null, null);
INSERT INTO `word` VALUES ('endless', 'adj. 无止境的；连续的；环状的；漫无目的的', null, '英 [\'endlɪs]  美 [ˈɛndlɪs]', null, null);
INSERT INTO `word` VALUES ('predict', 'vt. 预报，预言；预知\r\nvi. 作出预言；作预料，作预报', null, '英 [prɪ\'dɪkt]  美 [prɪ\'dɪkt]', null, null);
INSERT INTO `word` VALUES ('raise', 'vt. 提高；筹集；养育；升起\r\nvi. 上升\r\nn. 高地；上升；加薪\r\nn. (Raise)人名；(英)雷兹', null, '英 [reɪz]  美 [rez]', null, null);
INSERT INTO `word` VALUES ('sign', 'n. 迹象；符号；记号；手势；指示牌\r\nvi. 签署；签名\r\nvt. 签署；示意', null, '英 [saɪn]  美 [saɪn]', null, null);
INSERT INTO `word` VALUES ('transition', 'n. 过渡；转变；[分子生物] 转换；变调', null, '英 [træn\'zɪʃ(ə)n; trɑːn-; -\'sɪʃ-]  美 [træn\'zɪʃən]', null, null);
INSERT INTO `word` VALUES ('trend', 'n. 趋势，倾向；走向\r\nvi. 趋向，伸向\r\nvt. 使…趋向', null, '英 [trend]  美 [trɛnd]', null, null);

-- ----------------------------
-- Table structure for word_group
-- ----------------------------
DROP TABLE IF EXISTS `word_group`;
CREATE TABLE `word_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `user_id` int(11) NOT NULL,
  `user_name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_word_group_user_idx` (`user_id`,`user_name`),
  CONSTRAINT `fk_word_group_user` FOREIGN KEY (`user_id`, `user_name`) REFERENCES `user` (`id`, `name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of word_group
-- ----------------------------
INSERT INTO `word_group` VALUES ('1', 'CET6_2017/12.1', '1', 'ninggc');
INSERT INTO `word_group` VALUES ('2', 'group_2', '2', 'user_2');
INSERT INTO `word_group` VALUES ('5', 'group_3', '2', 'user_2');

-- ----------------------------
-- Table structure for word_has_group
-- ----------------------------
DROP TABLE IF EXISTS `word_has_group`;
CREATE TABLE `word_has_group` (
  `word_word` varchar(45) NOT NULL,
  `word_group_id` int(11) NOT NULL,
  PRIMARY KEY (`word_word`,`word_group_id`),
  KEY `fk_word_has_word_group_word_group1_idx` (`word_group_id`),
  KEY `fk_word_has_word_group_word1_idx` (`word_word`),
  CONSTRAINT `fk_word_has_word_group_word_group1` FOREIGN KEY (`word_group_id`) REFERENCES `word_group` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_word_has_word_group_word1` FOREIGN KEY (`word_word`) REFERENCES `word` (`word`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of word_has_group
-- ----------------------------
INSERT INTO `word_has_group` VALUES ('advance', '1');
INSERT INTO `word_has_group` VALUES ('bound', '1');
INSERT INTO `word_has_group` VALUES ('confront', '1');
INSERT INTO `word_has_group` VALUES ('consumption', '1');
INSERT INTO `word_has_group` VALUES ('dispute', '1');
INSERT INTO `word_has_group` VALUES ('endless', '1');
INSERT INTO `word_has_group` VALUES ('predict', '1');
INSERT INTO `word_has_group` VALUES ('raise', '1');
INSERT INTO `word_has_group` VALUES ('sign', '1');
INSERT INTO `word_has_group` VALUES ('transition', '1');
INSERT INTO `word_has_group` VALUES ('trend', '1');
INSERT INTO `word_has_group` VALUES ('advance', '2');
