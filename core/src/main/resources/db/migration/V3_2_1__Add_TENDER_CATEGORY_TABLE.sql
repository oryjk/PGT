-- -----------------------------------------------------
-- Table `P2P_INFO`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `TENDER_CATEGORY`;

CREATE TABLE IF NOT EXISTS `TENDER_CATEGORY` (
  `ID`          INT NOT NULL AUTO_INCREMENT,
  `TENDER_ID`   INT NULL,
  `CATEGORY_ID` INT NULL,
  PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB;

