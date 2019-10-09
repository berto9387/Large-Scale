SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `task_0` DEFAULT CHARACTER SET utf8 ;
USE `task_0` ;

CREATE TABLE task_0.Organizzatore
(
id INTEGER,
User VARCHAR(255),
password VARCHAR(255),
email VARCHAR(255),
phone VARCHAR(255),
PRIMARY KEY (id)
);

CREATE TABLE task_0.Evento
(
id INTEGER,
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

CREATE TABLE task_0.Partecipante
(
id INTEGER,
User VARCHAR(255),
password VARCHAR(255),
email VARCHAR(255),
phone VARCHAR(255),
PRIMARY KEY (id)
);

CREATE TABLE task_0.Partecipa
(
id INTEGER,
Utente INTEGER,
evento INTEGER,
PRIMARY KEY (id)
);

CREATE INDEX User_idx ON task_0.Organizzatore (User);
ALTER TABLE task_0.Evento ADD FOREIGN KEY organizzatore_idxfk (organizzatore) REFERENCES task_0.Organizzatore (id);

CREATE INDEX User_idx ON task_0.Partecipante (User);
ALTER TABLE task_0.Partecipa ADD FOREIGN KEY Utente_idxfk (Utente) REFERENCES task_0.Partecipante (id);

ALTER TABLE task_0.Partecipa ADD FOREIGN KEY evento_idxfk (evento) REFERENCES task_0.Evento (id);
