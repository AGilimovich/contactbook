CREATE DATABASE  IF NOT EXISTS `aleksandr_gilimovich` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `aleksandr_gilimovich`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: aleksandr_gilimovich
-- ------------------------------------------------------
-- Server version	5.7.13-log

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
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `address` (
  `address_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `country` varchar(50) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `street` varchar(50) DEFAULT NULL,
  `house` varchar(20) DEFAULT NULL,
  `apartment` varchar(20) DEFAULT NULL,
  `zip_code` varchar(20) DEFAULT NULL,
  `contact_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`address_id`),
  KEY `contact_id` (`contact_id`),
  CONSTRAINT `address_ibfk_1` FOREIGN KEY (`contact_id`) REFERENCES `contact` (`contact_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (1,'Республика Беларусь','Минск','Селицкого','77','112','220075',1),(2,'Российская Федерация','Москва',NULL,NULL,NULL,NULL,2),(3,NULL,NULL,NULL,NULL,NULL,NULL,3),(4,NULL,NULL,NULL,NULL,NULL,NULL,4),(5,NULL,NULL,NULL,NULL,NULL,NULL,5),(6,'Франция','Париж',NULL,NULL,NULL,NULL,6),(7,'ФРГ','Берлин',NULL,NULL,NULL,NULL,7),(8,'США','Нью-Йорк',NULL,NULL,NULL,NULL,8),(9,'Испания','Мадрид',NULL,NULL,NULL,NULL,9),(10,'Республика Беларусь','Гомель',NULL,NULL,NULL,NULL,10),(11,'Российская Федерация','Омск',NULL,NULL,NULL,NULL,11),(12,'Республика Беларусь','Витебск',NULL,NULL,NULL,NULL,12),(13,'Республика Беларусь','Мозырь',NULL,NULL,NULL,NULL,13),(14,'Греция','Афины',NULL,NULL,NULL,NULL,14),(15,'Польша','Краков',NULL,NULL,NULL,NULL,15),(16,'Украина','Киев',NULL,NULL,NULL,NULL,16),(17,'Мексика','Мехико',NULL,NULL,NULL,NULL,17),(18,'КНДР','Пхеньян',NULL,NULL,NULL,NULL,18),(19,'Ирак','Багдад',NULL,NULL,NULL,NULL,19),(20,'Республика Беларусь','Брест',NULL,NULL,NULL,NULL,20),(21,'Великобритания','Лондон',NULL,NULL,NULL,NULL,21),(22,'Республика Беларусь','Могилев',NULL,NULL,NULL,NULL,22);
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attachment`
--

DROP TABLE IF EXISTS `attachment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attachment` (
  `attach_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `attach_name` varchar(100) DEFAULT NULL,
  `upload_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `comment` text,
  `file` bigint(20) unsigned DEFAULT NULL,
  `contact_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`attach_id`),
  KEY `contact_id` (`contact_id`),
  KEY `file` (`file`),
  CONSTRAINT `attachment_ibfk_1` FOREIGN KEY (`contact_id`) REFERENCES `contact` (`contact_id`) ON DELETE CASCADE,
  CONSTRAINT `attachment_ibfk_2` FOREIGN KEY (`file`) REFERENCES `file` (`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attachment`
--

LOCK TABLES `attachment` WRITE;
/*!40000 ALTER TABLE `attachment` DISABLE KEYS */;
/*!40000 ALTER TABLE `attachment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contact`
--

DROP TABLE IF EXISTS `contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact` (
  `contact_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `surname` varchar(50) NOT NULL,
  `patronymic` varchar(50) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `gender` int(10) unsigned NOT NULL,
  `citizenship` varchar(50) DEFAULT NULL,
  `family_status` int(10) unsigned NOT NULL,
  `website` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `place_of_work` varchar(50) DEFAULT NULL,
  `photo` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`contact_id`),
  KEY `gender` (`gender`),
  KEY `family_status` (`family_status`),
  KEY `photo` (`photo`),
  CONSTRAINT `contact_ibfk_1` FOREIGN KEY (`gender`) REFERENCES `gender` (`gender_id`),
  CONSTRAINT `contact_ibfk_2` FOREIGN KEY (`family_status`) REFERENCES `family_status` (`family_status_id`),
  CONSTRAINT `contact_ibfk_3` FOREIGN KEY (`photo`) REFERENCES `file` (`file_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact`
--

LOCK TABLES `contact` WRITE;
/*!40000 ALTER TABLE `contact` DISABLE KEYS */;
INSERT INTO `contact` VALUES (1,'Александр','Гилимович','Сергеевич','1990-04-12',1,'Белорус',2,NULL,'a.gilimovich1990@gmail.com',NULL,1),(2,'Иван','Иванов','Иванович','1965-09-30',1,NULL,2,NULL,NULL,NULL,2),(3,'Петр','Петров','Петрович','1989-01-15',1,NULL,3,NULL,NULL,NULL,3),(4,'Татьяна','Журавлева','Викторовна','1990-04-12',2,NULL,1,NULL,'a.gilimovich1990@gmail.com',NULL,4),(5,'Антон','Никитин','Алексеевич','1978-03-15',1,NULL,3,NULL,NULL,NULL,5),(6,'Валентин','Урбанович','Геннадьевич','1995-06-19',1,NULL,3,NULL,NULL,NULL,6),(7,'Алексей','Соловьев','Александрович','1955-04-14',3,NULL,3,NULL,NULL,NULL,7),(8,'Максим','Дударев','Юрьевич','2000-02-28',1,NULL,2,NULL,NULL,NULL,8),(9,'Михаил','Ивчин','Анатольевич','1944-05-05',1,NULL,3,NULL,NULL,NULL,9),(10,'Анатолий','Катков','Владимирович','1970-07-23',1,NULL,3,NULL,NULL,NULL,10),(11,'Антон','Льдов','Иванович','1993-05-31',1,NULL,3,NULL,NULL,NULL,11),(12,'Марина','Воронова','Александровна','2005-09-08',2,NULL,3,NULL,NULL,NULL,12),(13,'Ангелина','Яцевич','Алексеевна',NULL,2,NULL,3,NULL,NULL,NULL,13),(14,'Наталья','Кошуба','Ивановна','1977-10-10',2,NULL,3,NULL,NULL,NULL,14),(15,'Людмила','Люцкевич','Витальевна','2017-03-15',2,NULL,3,NULL,NULL,NULL,15),(16,'Олег','Солнцев','Петрович','2000-12-18',1,NULL,3,NULL,NULL,NULL,16),(17,'Светлана','Архипова','Петровна','1944-04-17',2,NULL,3,NULL,NULL,NULL,17),(18,'Анна','Иванова','Ивановна',NULL,3,NULL,3,NULL,NULL,NULL,18),(19,'Кирилл','Залесский','Петрович','1996-03-13',1,NULL,3,NULL,NULL,NULL,19),(20,'Степан','Афанасьев','Александрович',NULL,1,NULL,3,NULL,NULL,NULL,20),(21,'Татьяна','Леонова','Викторовна','2000-08-06',2,NULL,3,NULL,NULL,NULL,21),(22,'Сергеева','Анна','Сергеевна',NULL,2,NULL,3,NULL,NULL,NULL,22);
/*!40000 ALTER TABLE `contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `family_status`
--

DROP TABLE IF EXISTS `family_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `family_status` (
  `family_status_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `family_status_value` varchar(7) DEFAULT NULL,
  PRIMARY KEY (`family_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `family_status`
--

LOCK TABLES `family_status` WRITE;
/*!40000 ALTER TABLE `family_status` DISABLE KEYS */;
INSERT INTO `family_status` VALUES (1,'married'),(2,'single'),(3,'none');
/*!40000 ALTER TABLE `family_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `file`
--

DROP TABLE IF EXISTS `file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `file` (
  `file_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `stored_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`file_id`),
  UNIQUE KEY `stored_name` (`stored_name`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file`
--

LOCK TABLES `file` WRITE;
/*!40000 ALTER TABLE `file` DISABLE KEYS */;
INSERT INTO `file` VALUES (1,NULL,NULL),(2,NULL,NULL),(3,NULL,NULL),(4,NULL,NULL),(5,NULL,NULL),(6,NULL,NULL),(7,NULL,NULL),(8,NULL,NULL),(9,NULL,NULL),(10,NULL,NULL),(11,NULL,NULL),(12,NULL,NULL),(13,NULL,NULL),(14,NULL,NULL),(15,NULL,NULL),(16,NULL,NULL),(17,NULL,NULL),(18,NULL,NULL),(19,NULL,NULL),(20,NULL,NULL),(21,NULL,NULL),(22,NULL,NULL);
/*!40000 ALTER TABLE `file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gender`
--

DROP TABLE IF EXISTS `gender`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gender` (
  `gender_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `gender_value` varchar(6) DEFAULT NULL,
  PRIMARY KEY (`gender_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gender`
--

LOCK TABLES `gender` WRITE;
/*!40000 ALTER TABLE `gender` DISABLE KEYS */;
INSERT INTO `gender` VALUES (1,'male'),(2,'female'),(3,'none');
/*!40000 ALTER TABLE `gender` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phone`
--

DROP TABLE IF EXISTS `phone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `phone` (
  `phone_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `country_code` varchar(10) NOT NULL,
  `operator_code` varchar(10) NOT NULL,
  `phone_number` varchar(15) NOT NULL,
  `phone_type` int(10) unsigned NOT NULL,
  `comment` text,
  `contact_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`phone_id`),
  KEY `contact_id` (`contact_id`),
  KEY `phone_type` (`phone_type`),
  CONSTRAINT `phone_ibfk_1` FOREIGN KEY (`contact_id`) REFERENCES `contact` (`contact_id`) ON DELETE CASCADE,
  CONSTRAINT `phone_ibfk_2` FOREIGN KEY (`phone_type`) REFERENCES `phone_type` (`phone_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phone`
--

LOCK TABLES `phone` WRITE;
/*!40000 ALTER TABLE `phone` DISABLE KEYS */;
INSERT INTO `phone` VALUES (1,'+375','44','7852202',2,NULL,1),(2,'+375','17','3182709',1,NULL,1);
/*!40000 ALTER TABLE `phone` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phone_type`
--

DROP TABLE IF EXISTS `phone_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `phone_type` (
  `phone_type_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `phone_type_value` varchar(6) DEFAULT NULL,
  PRIMARY KEY (`phone_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phone_type`
--

LOCK TABLES `phone_type` WRITE;
/*!40000 ALTER TABLE `phone_type` DISABLE KEYS */;
INSERT INTO `phone_type` VALUES (1,'home'),(2,'mobile'),(3,'none');
/*!40000 ALTER TABLE `phone_type` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-04-12 11:10:04
