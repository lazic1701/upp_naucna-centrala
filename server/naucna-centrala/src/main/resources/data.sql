-- MySQL dump 10.13  Distrib 5.6.23, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: naucnacentrala_milan
-- ------------------------------------------------------
-- Server version	5.7.26-log

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
-- Dumping data for table `_authority`
--

LOCK TABLES `_authority` WRITE;
/*!40000 ALTER TABLE `_authority` DISABLE KEYS */;
INSERT INTO `_authority` (`id`, `name`) VALUES (1,'ROLE_ADMIN'),(2,'ROLE_UREDNIK'),(3,'ROLE_RECENZENT'),(4,'ROLE_AUTOR');
/*!40000 ALTER TABLE `_authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `_naucna_oblast`
--

LOCK TABLES `_naucna_oblast` WRITE;
/*!40000 ALTER TABLE `_naucna_oblast` DISABLE KEYS */;
INSERT INTO `_naucna_oblast` (`id`, `naziv`) VALUES ('no1','Prirodno-matemati?ke nauke'),('no2','Tehni?ko-tehnološke nauke'),('no3','Društveno-humanisti?ke nauke'),('no4','Medicinske nauke'),('no5','Umetnosti');
/*!40000 ALTER TABLE `_naucna_oblast` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `_user`
--

LOCK TABLES `_user` WRITE;
/*!40000 ALTER TABLE `_user` DISABLE KEYS */;
INSERT INTO `_user` (`id`, `active`, `city`, `country`, `email`, `firstname`, `lastname`, `password`, `title`, `username`) VALUES (1,1,'Novi Sad','Serbia','sep.bbf@gmail.com','Administrator','NC','$2a$10$MjqeiRFBUh2n776p/EwRC.l2B7YDZkJKEwzJRF2653BL/Gp6yk8WW',NULL,'demo'),(2,1,'Kragujevac','Srbija','zokofausti@yahoo.com','Lepi','Mile','$2a$10$ITLCO/uenKiGxRwOBFhayOrfhdEUS0TG8R6BNVWkeH.GRYXHViEpG','dr','lepi'),(3,1,'Loznica','Srbija','lazoni.milancello@google.com','Lazar','Milanovic','$2a$10$ITLCO/uenKiGxRwOBFhayOrfhdEUS0TG8R6BNVWkeH.GRYXHViEpG','prof','lazoni'),(4,1,'Sremski Karlovci','Srbija','perunpere@yahoo.com','Perun','Pere','$2a$10$ITLCO/uenKiGxRwOBFhayOrfhdEUS0TG8R6BNVWkeH.GRYXHViEpG','mr','perun'),(5,1,'Zrenjanin','Srbija','lazm1701@gmail.com','Racko','Talin','$2a$10$GGqS65JAjEfyMo32C/w9Bu6PKzjW0hl3OXeHBZW1TtB/UNNWsNPIm','prof','racko'),(6,1,'Lozana','SJEDINJENA ','milanquedinho@gmail.com','Rasa','Karapandza','$2a$10$5byoG0FVgLTn3MFPKgcHm.dAulXy0CfetNy9JXzXEuJSuZQsO8K06','ser','karapandza');
/*!40000 ALTER TABLE `_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `_user_authority`
--

LOCK TABLES `_user_authority` WRITE;
/*!40000 ALTER TABLE `_user_authority` DISABLE KEYS */;
INSERT INTO `_user_authority` (`user_id`, `authority_id`) VALUES (1,1),(2,2),(3,2),(4,2),(5,3),(6,3);
/*!40000 ALTER TABLE `_user_authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `_user_naucnaoblast`
--

LOCK TABLES `_user_naucnaoblast` WRITE;
/*!40000 ALTER TABLE `_user_naucnaoblast` DISABLE KEYS */;
INSERT INTO `_user_naucnaoblast` (`naucnaoblast_id`, `user_id`) VALUES (5,'no3'),(5,'no4'),(6,'no1'),(6,'no2'),(6,'no3'),(6,'no4'),(6,'no5');
/*!40000 ALTER TABLE `_user_naucnaoblast` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-01-17 21:48:23
