-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema db_got_word
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema db_got_word
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `db_got_word` DEFAULT CHARACTER SET utf8 ;
USE `db_got_word` ;

-- -----------------------------------------------------
-- Table `db_got_word`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_got_word`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`, `name`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_got_word`.`word_group`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_got_word`.`word_group` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `user_id` INT NOT NULL,
  `user_name` VARCHAR(45) NOT NULL,
  `count` INT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `fk_word_group_user_idx` (`user_id` ASC, `user_name` ASC),
  CONSTRAINT `fk_word_group_user`
    FOREIGN KEY (`user_id` , `user_name`)
    REFERENCES `db_got_word`.`user` (`id` , `name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_got_word`.`word`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_got_word`.`word` (
  `word` VARCHAR(45) NOT NULL,
  `explains` VARCHAR(100) NOT NULL,
  `us-phonetic` VARCHAR(45) NULL,
  `phonetic` VARCHAR(45) NULL,
  `uk-phonetic` VARCHAR(45) NULL,
  PRIMARY KEY (`word`),
  UNIQUE INDEX `word_UNIQUE` (`word` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_got_word`.`word_has_word_group`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_got_word`.`word_has_word_group` (
  `word_word` VARCHAR(45) NOT NULL,
  `word_group_id` INT NOT NULL,
  PRIMARY KEY (`word_word`, `word_group_id`),
  INDEX `fk_word_has_word_group_word_group1_idx` (`word_group_id` ASC),
  INDEX `fk_word_has_word_group_word1_idx` (`word_word` ASC),
  CONSTRAINT `fk_word_has_word_group_word1`
    FOREIGN KEY (`word_word`)
    REFERENCES `db_got_word`.`word` (`word`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_word_has_word_group_word_group1`
    FOREIGN KEY (`word_group_id`)
    REFERENCES `db_got_word`.`word_group` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
