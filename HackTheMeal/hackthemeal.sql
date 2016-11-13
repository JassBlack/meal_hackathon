-- MySQL dump 10.13  Distrib 5.7.16, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: mealv485_hackathon
-- ------------------------------------------------------
-- Server version	5.7.16-0ubuntu0.16.04.1

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
-- Table structure for table `diner`
--

DROP TABLE IF EXISTS `diner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `diner` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fname` varchar(45) COLLATE utf8_swedish_ci DEFAULT NULL,
  `lname` varchar(45) COLLATE utf8_swedish_ci DEFAULT NULL,
  `energyneed` int(11) DEFAULT NULL,
  `L` tinyint(1) NOT NULL DEFAULT '0',
  `G` tinyint(1) NOT NULL DEFAULT '0',
  `M` tinyint(1) NOT NULL DEFAULT '0',
  `VL` tinyint(1) NOT NULL DEFAULT '0',
  `V` tinyint(1) NOT NULL DEFAULT '0',
  `status` int(11) DEFAULT NULL,
  `allergy` varchar(20) COLLATE utf8_swedish_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diner`
--

LOCK TABLES `diner` WRITE;
/*!40000 ALTER TABLE `diner` DISABLE KEYS */;
INSERT INTO `diner` VALUES (1,'Hank','Hacker',2200,0,1,0,0,0,NULL,NULL),(2,'Helen','Hipster',1900,1,1,1,1,1,NULL,'Nuts'),(3,'Keijo','Karppaaja',2100,0,0,0,0,0,NULL,NULL),(4,'Donald','Duck',1200,1,1,1,0,1,NULL,'Fish'),(5,'Teuvo','Testaaja',2200,0,0,0,0,0,NULL,NULL),(6,'Tiina','Testaaja',2000,0,0,0,0,0,NULL,NULL),(7,'Risto','Ruokailija',2150,0,0,0,0,0,NULL,NULL),(8,'Teija','Rautiainen',1980,0,0,0,1,0,NULL,'Nuts');
/*!40000 ALTER TABLE `diner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dish`
--

DROP TABLE IF EXISTS `dish`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dish` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dispname` varchar(60) CHARACTER SET utf8 NOT NULL,
  `energy` int(11) NOT NULL,
  `protein` int(11) DEFAULT NULL,
  `fat` int(11) DEFAULT NULL,
  `carbon` int(11) DEFAULT NULL,
  `L` tinyint(1) NOT NULL DEFAULT '0',
  `G` tinyint(1) NOT NULL DEFAULT '0',
  `M` tinyint(1) NOT NULL DEFAULT '0',
  `VL` tinyint(1) NOT NULL DEFAULT '0',
  `V` tinyint(1) NOT NULL DEFAULT '0',
  `status` int(11) DEFAULT NULL,
  `allergy` varchar(20) COLLATE utf8_swedish_ci DEFAULT NULL,
  `origin` varchar(45) COLLATE utf8_swedish_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dish`
--

LOCK TABLES `dish` WRITE;
/*!40000 ALTER TABLE `dish` DISABLE KEYS */;
INSERT INTO `dish` VALUES (1,'Broiler file',110,23,2,1,0,0,0,0,0,NULL,NULL,'Kauhava'),(2,'Risotto Verde',142,3,7,16,1,1,0,1,1,NULL,'Nuts','Livorno'),(3,'Roast beef',157,31,4,0,1,1,1,1,0,NULL,NULL,'Brasil'),(4,'Salmon casserole',129,6,6,12,1,1,0,1,0,NULL,'Fish','Puumala'),(5,'Paella',101,7,3,12,0,1,0,0,0,NULL,'Shrimp','Valencia'),(6,'Meatballs',260,16,17,8,1,0,1,1,0,NULL,NULL,'Finland'),(7,'Cooked potatoes',76,2,0,15,1,1,1,1,1,NULL,NULL,'Finland'),(8,'Country-style potato wedges',85,1,7,4,1,1,1,1,1,NULL,NULL,NULL),(9,'Minced meat patties',220,13,12,13,1,0,1,1,0,NULL,NULL,NULL),(10,'Pepper sauce',35,0,2,3,1,1,0,1,0,NULL,NULL,NULL),(11,'Vegetable ball',131,4,6,15,1,1,1,1,1,NULL,NULL,NULL),(12,'Sour cream wiht chili',98,2,5,13,1,1,0,1,0,NULL,NULL,NULL),(13,'Cocos-broiler',107,8,6,5,1,1,1,1,0,NULL,NULL,NULL),(14,'Tofu with leek sauce',116,4,9,3,1,1,1,1,1,NULL,NULL,NULL),(15,'Wholegrain rice',64,1,0,13,1,1,1,1,1,NULL,NULL,NULL);
/*!40000 ALTER TABLE `dish` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `portion`
--

DROP TABLE IF EXISTS `portion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `portion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dinerid` int(11) NOT NULL,
  `dishid` int(11) NOT NULL,
  `weight` int(11) NOT NULL,
  `moment` datetime NOT NULL,
  `energy` int(11) DEFAULT NULL,
  `protein` int(11) DEFAULT NULL,
  `fat` int(11) DEFAULT NULL,
  `carbon` int(11) DEFAULT NULL,
  `mealname` varchar(60) COLLATE utf8_swedish_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `portion`
--

LOCK TABLES `portion` WRITE;
/*!40000 ALTER TABLE `portion` DISABLE KEYS */;
INSERT INTO `portion` VALUES (1,1,1,250,'2016-11-02 12:31:45',275,58,5,3,'Broiler file'),(2,1,2,185,'2016-11-02 12:32:25',263,6,13,30,'Risotto Verde'),(3,2,2,235,'2016-11-08 12:13:14',334,7,16,38,'Risotto Verde'),(4,1,1,335,'2016-11-09 12:13:14',369,77,7,3,'Broiler file'),(5,1,5,120,'2016-11-09 12:13:14',121,8,4,14,'Paella'),(6,8,7,105,'2016-08-26 13:14:00',80,2,0,16,'Cooked potatoes');
/*!40000 ALTER TABLE `portion` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-11-11 15:51:02
