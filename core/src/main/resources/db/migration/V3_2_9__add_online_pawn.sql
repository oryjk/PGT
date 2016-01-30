
-- ----------------------------
-- Table structure for ONLINE_PAWN
-- ----------------------------
DROP TABLE IF EXISTS `ONLINE_PAWN`;
CREATE TABLE `ONLINE_PAWN` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CATEGORY_ID` int(11) DEFAULT NULL,
  `DURATION_DATE` int(14) DEFAULT NULL,
  `PAWN_TOTAL` decimal(10,2) DEFAULT NULL,
  `PHONE_NUMBER` varchar(100) DEFAULT NULL,

  PRIMARY KEY (`ID`)
) ENGINE=InnoDB;
