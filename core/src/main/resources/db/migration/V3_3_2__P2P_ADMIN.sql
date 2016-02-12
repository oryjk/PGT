# Add type to product
ALTER TABLE `mp`.`PRODUCT` ADD COLUMN `TYPE` INT(11) DEFAULT 1;
UPDATE `mp`.`PRODUCT`
SET `TYPE` = 1;
# Add tender index to product
ALTER TABLE `mp`.`PRODUCT` ADD COLUMN `TENDER_INDEX` INT(11);
# Add status to tender
ALTER TABLE `mp`.`TENDER` ADD COLUMN `STATUS` INT(11);
# Add audit status to tender
ALTER TABLE `mp`.`TENDER` ADD COLUMN `AUDIT_STATUS` INT(11);
# Add comments to pawn ticket
ALTER TABLE `mp`.`PAWN_TICKET` ADD COLUMN `COMMENTS` VARCHAR(1000);
# Add status to pawn ticket
ALTER TABLE `mp`.`PAWN_TICKET` ADD COLUMN `STATUS` INT(11);