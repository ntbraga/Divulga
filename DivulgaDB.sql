-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `mydb` ;

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Cities`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Cities` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Cities` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `city_name` VARCHAR(45) NOT NULL,
  `city_state` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idCities_UNIQUE` (`id` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Categories`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Categories` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Categories` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `id_city` INT UNSIGNED NOT NULL,
  `cat_name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`, `id_city`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_Categories_Cities1_idx` (`id_city` ASC),
  CONSTRAINT `fk_Categories_Cities1`
    FOREIGN KEY (`id_city`)
    REFERENCES `mydb`.`Cities` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Establishments`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Establishments` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Establishments` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `id_city` INT UNSIGNED NOT NULL,
  `id_cat` INT UNSIGNED NOT NULL,
  `estab_name` VARCHAR(100) NOT NULL,
  `estab_description` TEXT NULL,
  `address_zip` VARCHAR(45) NOT NULL,
  `address_district` VARCHAR(45) NOT NULL,
  `address_street` VARCHAR(45) NOT NULL,
  `address_number` VARCHAR(45) NOT NULL,
  `address_complement` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`, `id_city`, `id_cat`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_Establishment_Cities_idx` (`id_city` ASC),
  INDEX `fk_Establishments_Categories1_idx` (`id_cat` ASC),
  CONSTRAINT `fk_Establishment_Cities`
    FOREIGN KEY (`id_city`)
    REFERENCES `mydb`.`Cities` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Establishments_Categories1`
    FOREIGN KEY (`id_cat`)
    REFERENCES `mydb`.`Categories` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Contacts`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Contacts` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Contacts` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `id_estab` INT UNSIGNED NOT NULL,
  `contact_email` VARCHAR(100) NOT NULL,
  `contact_phone` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`, `id_estab`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_Contacts_Establishments1_idx` (`id_estab` ASC),
  CONSTRAINT `fk_Contacts_Establishments1`
    FOREIGN KEY (`id_estab`)
    REFERENCES `mydb`.`Establishments` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
