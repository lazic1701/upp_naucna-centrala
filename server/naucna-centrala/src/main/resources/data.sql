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
-- Dumping data for table `_casopis`
--

LOCK TABLES `_casopis` WRITE;
/*!40000 ALTER TABLE `_casopis` DISABLE KEYS */;
INSERT INTO `_casopis` (`id`, `active`, `is_registered`, `issn`, `nacin_naplate`, `naziv`, `seller_id`, `glavni_urednik_id`) VALUES (1,1,'\0','658915-98121','NAPLACIVANJE_AUTORU','Allegro',0,2),(2,1,'\0','19813523-651321','NAPLACIVANJE_CITAOCU','Scienza delle Costruzioni di Oggi',0,2),(3,1,'','118916-851981','NAPLACIVANJE_CITAOCU','Sciences de l\'éducation',4,2);
/*!40000 ALTER TABLE `_casopis` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `_casopis_naucnaoblast`
--

LOCK TABLES `_casopis_naucnaoblast` WRITE;
/*!40000 ALTER TABLE `_casopis_naucnaoblast` DISABLE KEYS */;
INSERT INTO `_casopis_naucnaoblast` (`casopis_id`, `naucnaoblast_id`) VALUES (1,'no3'),(1,'no5'),(2,'no1'),(2,'no2'),(3,'no3'),(3,'no5');
/*!40000 ALTER TABLE `_casopis_naucnaoblast` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `_casopis_recenzent`
--

LOCK TABLES `_casopis_recenzent` WRITE;
/*!40000 ALTER TABLE `_casopis_recenzent` DISABLE KEYS */;
INSERT INTO `_casopis_recenzent` (`casopis_id`, `user_id`) VALUES (1,5),(1,6),(2,8),(2,9),(3,6),(3,8);
/*!40000 ALTER TABLE `_casopis_recenzent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `_casopis_urednik`
--

LOCK TABLES `_casopis_urednik` WRITE;
/*!40000 ALTER TABLE `_casopis_urednik` DISABLE KEYS */;
INSERT INTO `_casopis_urednik` (`casopis_id`, `user_id`) VALUES (1,4);
/*!40000 ALTER TABLE `_casopis_urednik` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `_clanarina`
--

LOCK TABLES `_clanarina` WRITE;
/*!40000 ALTER TABLE `_clanarina` DISABLE KEYS */;
/*!40000 ALTER TABLE `_clanarina` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `_koautor`
--

LOCK TABLES `_koautor` WRITE;
/*!40000 ALTER TABLE `_koautor` DISABLE KEYS */;
INSERT INTO `_koautor` (`id`, `drzava`, `email`, `grad`, `ime`, `naucni_rad_id`) VALUES (1,'Novi Sad','boris@gmasf.com','Irig','Boris Popovic',1);
/*!40000 ALTER TABLE `_koautor` ENABLE KEYS */;
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
-- Dumping data for table `_naucni_rad`
--

LOCK TABLES `_naucni_rad` WRITE;
/*!40000 ALTER TABLE `_naucni_rad` DISABLE KEYS */;
INSERT INTO `_naucni_rad` (`id`, `apstrakt`, `cena`, `file_path`, `kljucni_pojmovi`, `naslov`, `status`, `autor_id`, `casopis_id`, `naucna_oblast_id`) VALUES (1,' Ovaj nau?ni rad ima za cilj da predstavi kako se nekada ?ivelo.',0,'C:\\Users\\lazic\\Documents\\GitHub\\upp-naucna-centrala\\server\\naucna-centrala\\src\\main\\resources\\uploads\\1_Renesansna arhitektura.pdf',' renesansa, arhitektura, italija, firenca, da vinci, leonardo','Renesansna arhitektura','ODOBREN',7,1,'no3'),(2,' Ovaj nau?ni rad opet nešto lepo ima da kaže.',0,'C:\\Users\\lazic\\Documents\\GitHub\\upp-naucna-centrala\\server\\naucna-centrala\\src\\main\\resources\\uploads\\2_Renesansni humanizam.pdf',' humanizam, slikari, pesnici, ?ovek, renesansa, život','Renesansni humanizam','ODOBREN',2,1,'no3'),(3,'Apstrakt kao i svaki drugi Da Vinci genije car.',0,'C:\\Users\\lazic\\Documents\\GitHub\\upp-naucna-centrala\\server\\naucna-centrala\\src\\main\\resources\\uploads\\3_Uticaj Da Vincija na slikarstvo.pdf','da vinci, genije, firenca, italija, slikarstvo, slika, boje','Uticaj Da Vincija na slikarstvo','ODOBREN',2,2,'no5');
/*!40000 ALTER TABLE `_naucni_rad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `_naucni_rad_recenzent`
--

LOCK TABLES `_naucni_rad_recenzent` WRITE;
/*!40000 ALTER TABLE `_naucni_rad_recenzent` DISABLE KEYS */;
INSERT INTO `_naucni_rad_recenzent` (`naucni_rad_id`, `user_id`) VALUES (1,9),(1,8),(2,10),(2,6),(3,11),(3,6);
/*!40000 ALTER TABLE `_naucni_rad_recenzent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `_porudzbina`
--

LOCK TABLES `_porudzbina` WRITE;
/*!40000 ALTER TABLE `_porudzbina` DISABLE KEYS */;
/*!40000 ALTER TABLE `_porudzbina` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `_recenzija`
--

LOCK TABLES `_recenzija` WRITE;
/*!40000 ALTER TABLE `_recenzija` DISABLE KEYS */;
/*!40000 ALTER TABLE `_recenzija` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `_user`
--

LOCK TABLES `_user` WRITE;
/*!40000 ALTER TABLE `_user` DISABLE KEYS */;
INSERT INTO `_user` (`id`, `active`, `city`, `country`, `email`, `firstname`, `lastname`, `password`, `title`, `username`) VALUES (1,1,'Novi Sad','Serbia','sep.bbf@gmail.com','Administrator','NC','$2a$10$MjqeiRFBUh2n776p/EwRC.l2B7YDZkJKEwzJRF2653BL/Gp6yk8WW',NULL,'demo'),(2,1,'Kopaonik','Srbija','zokofausti@yahoo.com','Lepi','Mile','$2a$10$ITLCO/uenKiGxRwOBFhayOrfhdEUS0TG8R6BNVWkeH.GRYXHViEpG','dr','lepi'),(3,1,'Loznica','Srbija','lazoni.milancello@google.com','Lazar','Milanovic','$2a$10$ITLCO/uenKiGxRwOBFhayOrfhdEUS0TG8R6BNVWkeH.GRYXHViEpG','prof','lazoni'),(4,1,'Sremski Karlovci','Srbija','perunpere@yahoo.com','Perun','Pere','$2a$10$ITLCO/uenKiGxRwOBFhayOrfhdEUS0TG8R6BNVWkeH.GRYXHViEpG','mr','perun'),(5,1,'Zrenjanin','Srbija','lazm1701@gmail.com','Racko','Talin','$2a$10$GGqS65JAjEfyMo32C/w9Bu6PKzjW0hl3OXeHBZW1TtB/UNNWsNPIm','prof','racko'),(6,1,'Zemun','Srbija','milanquedinho@gmail.com','Rasa','Prolic','$2a$10$ITLCO/uenKiGxRwOBFhayOrfhdEUS0TG8R6BNVWkeH.GRYXHViEpG','ser','karapandza'),(7,1,'Novi Sad','Srbija','lazic1701@gmail.com','Otore','Garibaldi','$2a$10$W9qWeQA2nXrXrJjoCkmFr.VgkB7MgUs264RAR2bF0Tm0C3VrAYw3u','prof','otore'),(8,1,'Dubrovnik','Hrvatska','lazoni.milancello@gmail.com','Filip','Rastovic','$2a$10$5JWlxczOGg4gN0Aa55YZc.YOeQm3nmQ0R72GLuxsS7mQZPI8cjld6','ser','recen'),(9,1,'Rijeka','Hrvatska','lazicy@gmail.com','Gojko','Ralic','$2a$10$jFiJjsd2zfdh8iIlnvDMGuDpVorTWL/M/iVZM.JOJPXpKSR6s3LAa','King','lebron'),(10,1,'Beograd','Srbija','asfaf@sa','Goran','Radosavljevic','$2a$10$SDZAdDnoIfV72Mea8FdsiOAhmWO3A3hpY1prYtETmOPVtefmuW1eu','ser','gorance'),(11,1,'Ljubljana','Slovenia','asfasf@gaga','Slavoj','Jaka','$2a$10$BQy4vkP8ELhf34OINVxXGe5YmG0Useit1UJEtE8Jt5yTTDlGxnsP6','ser','slavoj');
/*!40000 ALTER TABLE `_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `_user_authority`
--

LOCK TABLES `_user_authority` WRITE;
/*!40000 ALTER TABLE `_user_authority` DISABLE KEYS */;
INSERT INTO `_user_authority` (`user_id`, `authority_id`) VALUES (1,1),(2,4),(3,3),(4,3),(5,3),(6,3),(7,4),(8,3),(9,3),(10,3),(11,3);
/*!40000 ALTER TABLE `_user_authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `_user_naucnaoblast`
--

LOCK TABLES `_user_naucnaoblast` WRITE;
/*!40000 ALTER TABLE `_user_naucnaoblast` DISABLE KEYS */;
INSERT INTO `_user_naucnaoblast` (`naucnaoblast_id`, `user_id`) VALUES (2,'no1'),(2,'no2'),(3,'no3'),(3,'no4'),(4,'no5'),(5,'no3'),(5,'no4'),(6,'no1'),(6,'no2'),(6,'no3'),(6,'no4'),(6,'no5'),(7,'no2'),(7,'no3'),(7,'no5'),(8,'no2'),(8,'no3'),(8,'no4'),(9,'no2'),(9,'no3'),(9,'no5'),(10,'no2'),(10,'no3'),(10,'no4'),(11,'no2'),(11,'no3'),(11,'no4');
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

-- Dump completed on 2020-02-25  2:34:40
