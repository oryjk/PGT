DROP TABLE IF EXISTS `HELP`;

CREATE TABLE `HELPCENTER` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TITLE` varchar(40) DEFAULT NULL,
  `CONTENT` text,
  `REFERENCE_ID` int(11) DEFAULT NULL,
  `TYPE` varchar(45) DEFAULT NULL,
  `PARENT_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of HELPCENTER
-- ----------------------------
INSERT INTO `HELPCENTER` VALUES ('1', '我的订单', '我的订单我的订单我的订单我的订单', '18', null, null);
INSERT INTO `HELPCENTER` VALUES ('2', '我的优惠', '我的优惠我的优惠我的优惠我的优惠', '18', null, null);
INSERT INTO `HELPCENTER` VALUES ('3', '我的收藏夹', '我的收藏夹我的收藏夹我的收藏夹我的收藏夹', '19', null, null);
INSERT INTO `HELPCENTER` VALUES ('4', '最近购买', '最近购买最近购买最近购买最近购买最近购买', '19', null, null);
INSERT INTO `HELPCENTER` VALUES ('5', '最近浏览', '最近浏览最近浏览最近浏览最近浏览', '19', null, null);
INSERT INTO `HELPCENTER` VALUES ('6', '我的讨论', '我的讨论我的讨论我的讨论我的讨论我的讨论', '20', null, null);
INSERT INTO `HELPCENTER` VALUES ('7', '我的咨询', '我的咨询我的咨询我的咨询我的咨询我的咨询', '20', null, null);
INSERT INTO `HELPCENTER` VALUES ('8', '个人信息', '个人信息个人信息个人信息个人信息个人信息', '21', null, null);
INSERT INTO `HELPCENTER` VALUES ('9', '修改密码', '修改密码修改密码修改密码修改密码修改密码修改密码', '21', null, null);
INSERT INTO `HELPCENTER` VALUES ('10', '地址管理', '地址管理地址管理地址管理地址管理地址管理', '21', null, null);
INSERT INTO `HELPCENTER` VALUES ('11', '购物流程', '购物流程购物流程购物流程购物流程购物流程', '22', null, null);
INSERT INTO `HELPCENTER` VALUES ('12', '支付方式', '支付方式支付方式支付方式支付方式支付方式', '22', null, null);
INSERT INTO `HELPCENTER` VALUES ('13', '发票制度', '发票制度发票制度发票制度发票制度发票制度', '22', null, null);
INSERT INTO `HELPCENTER` VALUES ('14', '配置说明', '配置说明配置说明配置说明配置说明配置说明', '22', null, null);
INSERT INTO `HELPCENTER` VALUES ('15', '关于我们', '关于我们关于我们关于我们关于我们关于我们关于我们', '22', null, null);
INSERT INTO `HELPCENTER` VALUES ('16', '售后服务', '售后服务售后服务售后服务售后服务售后服务', '22', null, null);
