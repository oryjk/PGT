CREATE TABLE `PAWN_PERSON_INFO` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(45) DEFAULT NULL,
  `GENDER` varchar(2) DEFAULT NULL,
  `PHONE_NUMBER` varchar(45) DEFAULT NULL,
  `ADDRESS` varchar(200) DEFAULT NULL,
  `DETAIL_ADDRESS` varchar(200) DEFAULT NULL,
  `PAWN_TYPE` varchar(45) DEFAULT NULL,
  `TYPE` varchar(45) DEFAULT NULL,
  `STATUS` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;