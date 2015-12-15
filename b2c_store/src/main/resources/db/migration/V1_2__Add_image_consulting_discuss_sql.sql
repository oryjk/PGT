-- ----------------------------
--   Table structure for `DISCUSS`
-- ----------------------------
DROP TABLE IF EXISTS `DISCUSS`;
CREATE TABLE `DISCUSS` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT,
  `CREATE_DATE` DATETIME DEFAULT NULL,
  `CONTENT` VARCHAR(255) DEFAULT NULL,
  `IP` VARCHAR(255) DEFAULT NULL,
  `IS_SHOW` BIT(1) DEFAULT NULL,
  `USER_ID` INT(11) DEFAULT NULL,
  `PRODUCT_ID` INT(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=UTF8;

-- ----------------------------
-- Table structure for `CONSULTING`
-- ----------------------------
DROP TABLE IF EXISTS `CONSULTING`;
CREATE TABLE `CONSULTING` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT,
  `CREATE_DATE` DATETIME DEFAULT NULL,
  `CONTENT` VARCHAR(255) DEFAULT NULL,
  `IP` VARCHAR(255) DEFAULT NULL,
  `IS_SHOW` bit(1) DEFAULT NULL,
  `USER_ID` INT(11) DEFAULT NULL,
  `PRODUCT_ID` INT(11) DEFAULT NULL,
  `PARENT_CONSULATING_ID` INT(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=UTF8;

-- ----------------------------
-- Table structure for `IMAGE`
-- ----------------------------
DROP TABLE IF EXISTS `IMAGE`;
CREATE TABLE `IMAGE` (
  `IMAGE_ID` INT(11) NOT NULL AUTO_INCREMENT,
  `CREATE_DATE` DATETIME DEFAULT NULL,
  `END_DATE` DATETIME DEFAULT NULL,
  `LOCATION` VARCHAR(45) DEFAULT NULL,
  `URL` VARCHAR(255) DEFAULT NULL,
  `PATH` VARCHAR(255) DEFAULT NULL,
  `TYPE` VARCHAR(45) DEFAULT NULL,
  `TITLE` VARCHAR(45) DEFAULT NULL,
  PRIMARY KEY (`IMAGE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=UTF8;
