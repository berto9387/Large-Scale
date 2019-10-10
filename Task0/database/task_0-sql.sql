SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema task_0
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema task_0
-- -----------------------------------------------------

CREATE SCHEMA IF NOT EXISTS `task_0` DEFAULT CHARACTER SET utf8 ;
USE `task_0` ;

DROP TABLE IF EXISTS task_0.Partecipa;

DROP TABLE IF EXISTS task_0.Evento;

DROP TABLE IF EXISTS task_0.Organizzatore;

DROP TABLE IF EXISTS task_0.Partecipante;

-- -----------------------------------------------------
-- Table `task_0`.`Organizzatore`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS task_0.Organizzatore
(
id_Organizatore INTEGER AUTO_INCREMENT,
nome VARCHAR(255),
cognome VARCHAR(255),
data_nascita DATE,
email VARCHAR(255),
password VARCHAR(255),
username VARCHAR(255),
phone VARCHAR(255),
PRIMARY KEY (id_Organizatore)
);

-- -----------------------------------------------------
-- Table `task_0`.`Evento`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS task_0.Evento
(
id INTEGER AUTO_INCREMENT,
nome VARCHAR(255),
luogo VARCHAR(255),
data DATE,
ora TIME,
posti INTEGER,
latitude_wgs84 DOUBLE PRECISION,
longitude_wgs84 DOUBLE PRECISION,
tipologia VARCHAR(255),
descrizione VARCHAR(255),
organizzatore INTEGER,
PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table `task_0`.`Partecipante`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS task_0.Partecipante
(
id_Partecipante INTEGER AUTO_INCREMENT,
nome VARCHAR(255),
cognome VARCHAR(255),
data_nascita DATE,
email VARCHAR(255),
password VARCHAR(255),
username VARCHAR(255),
phone VARCHAR(255),
PRIMARY KEY (id_Partecipante)
);

-- -----------------------------------------------------
-- Table `task_0`.`Partecipa`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS task_0.Partecipa
(
id INTEGER AUTO_INCREMENT,
Utente INTEGER,
evento INTEGER,
PRIMARY KEY (id)
);

ALTER TABLE task_0.Organizzatore ADD INDEX email_idx (email);
ALTER TABLE task_0.Evento ADD FOREIGN KEY organizzatore_idxfk (organizzatore) REFERENCES task_0.Organizzatore (id_Organizatore);

ALTER TABLE task_0.Partecipante ADD INDEX email_idx (email);
ALTER TABLE task_0.Partecipa ADD FOREIGN KEY Utente_idxfk (Utente) REFERENCES task_0.Partecipante (id_Partecipante);

ALTER TABLE task_0.Partecipa ADD FOREIGN KEY evento_idxfk (evento) REFERENCES task_0.Evento (id);
