CREATE DATABASE  IF NOT EXISTS `task_0` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `task_0`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: localhost    Database: task_0
-- ------------------------------------------------------
-- Server version	5.6.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `evento`
--

DROP TABLE IF EXISTS `evento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `evento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  `luogo` varchar(255) DEFAULT NULL,
  `data` date DEFAULT NULL,
  `ora` time DEFAULT NULL,
  `posti` int(11) DEFAULT NULL,
  `numero_partecipanti` int(11) DEFAULT '0',
  `latitude_wgs84` double DEFAULT NULL,
  `longitude_wgs84` double DEFAULT NULL,
  `tipologia` varchar(255) DEFAULT NULL,
  `descrizione` varchar(255) DEFAULT NULL,
  `organizzatore` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `organizzatore_idxfk` (`organizzatore`),
  CONSTRAINT `organizzatore_idxfk` FOREIGN KEY (`organizzatore`) REFERENCES `organizzatore` (`id_Organizatore`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evento`
--

LOCK TABLES `evento` WRITE;
/*!40000 ALTER TABLE `evento` DISABLE KEYS */;
/*!40000 ALTER TABLE `evento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organizzatore`
--

DROP TABLE IF EXISTS `organizzatore`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `organizzatore` (
  `id_Organizatore` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  `cognome` varchar(255) DEFAULT NULL,
  `data_nascita` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_Organizatore`),
  UNIQUE KEY `email_UNIQUE1` (`email`),
  KEY `email_idx` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organizzatore`
--

LOCK TABLES `organizzatore` WRITE;
/*!40000 ALTER TABLE `organizzatore` DISABLE KEYS */;
/*!40000 ALTER TABLE `organizzatore` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `partecipa`
--

DROP TABLE IF EXISTS `partecipa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `partecipa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Utente` int(11) DEFAULT NULL,
  `evento` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Utente_idxfk` (`Utente`),
  KEY `evento_idxfk` (`evento`),
  CONSTRAINT `Utente_idxfk` FOREIGN KEY (`Utente`) REFERENCES `partecipante` (`id_Partecipante`),
  CONSTRAINT `evento_idxfk` FOREIGN KEY (`evento`) REFERENCES `evento` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `partecipa`
--

LOCK TABLES `partecipa` WRITE;
/*!40000 ALTER TABLE `partecipa` DISABLE KEYS */;
/*!40000 ALTER TABLE `partecipa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `partecipante`
--

DROP TABLE IF EXISTS `partecipante`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `partecipante` (
  `id_Partecipante` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  `cognome` varchar(255) DEFAULT NULL,
  `data_nascita` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_Partecipante`),
  UNIQUE KEY `email_UNIQUE0` (`email`),
  KEY `email_idx` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `partecipante`
--

LOCK TABLES `partecipante` WRITE;
/*!40000 ALTER TABLE `partecipante` DISABLE KEYS */;
/*!40000 ALTER TABLE `partecipante` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'task_0'
--

--
-- Dumping routines for database 'task_0'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-10-11 20:58:02
