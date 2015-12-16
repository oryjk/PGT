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
INSERT INTO `HELPCENTER` VALUES ('1', '购物流程', '我的订单我的订单我的订单我的订单', '18', null, null);
INSERT INTO `HELPCENTER` VALUES ('2', '订单查询', '我的优惠我的优惠我的优惠我的优惠', '18', null, null);
INSERT INTO `HELPCENTER` VALUES ('3', '支付方式', '我的收藏夹我的收藏夹我的收藏夹我的收藏夹', '18', null, null);
INSERT INTO `HELPCENTER` VALUES ('4', '发票领取', '最近购买最近购买最近购买最近购买最近购买', '18', null, null);
INSERT INTO `HELPCENTER` VALUES ('5', '配送方式和时间', '最近浏览最近浏览最近浏览最近浏览', '19', null, null);
INSERT INTO `HELPCENTER` VALUES ('6', '配送费用和签收', '我的讨论我的讨论我的讨论我的讨论我的讨论', '19', null, null);
INSERT INTO `HELPCENTER` VALUES ('7', '点金子', '我的咨询我的咨询我的咨询我的咨询我的咨询', '20', null, null);
INSERT INTO `HELPCENTER` VALUES ('8', '点金子绝当品', '个人信息个人信息个人信息个人信息个人信息', '20', null, null);
INSERT INTO `HELPCENTER` VALUES ('9', '联系我们', '修改密码修改密码修改密码修改密码修改密码修改密码', '20', null, null);
INSERT INTO `HELPCENTER` VALUES ('10', '招贤纳士', '地址管理地址管理地址管理地址管理地址管理', '20', null, null);
INSERT INTO `HELPCENTER` VALUES ('11', '业务范围', '购物流程购物流程购物流程购物流程购物流程', '20', null, null);
INSERT INTO `HELPCENTER` VALUES ('12', '隐私声明', '支付方式支付方式支付方式支付方式支付方式', '20', null, null);
INSERT INTO `HELPCENTER` VALUES ('13', '退换货政策', '发票制度发票制度发票制度发票制度发票制度', '21', null, null);
INSERT INTO `HELPCENTER` VALUES ('14', '退款说明', '配置说明配置说明配置说明配置说明配置说明', '21', null, null);
INSERT INTO `HELPCENTER` VALUES ('15', '会员等级', '关于我们关于我们关于我们关于我们关于我们关于我们', '22', null, null);
INSERT INTO `HELPCENTER` VALUES ('16', '账号管理', '售后服务售后服务售后服务售后服务售后服务', '22', null, null);
INSERT INTO `HELPCENTER` VALUES ('17', '投诉建议', null, '22', null, null);