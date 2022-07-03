-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: stock
-- ------------------------------------------------------
-- Server version	8.0.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `delete_flag` int NOT NULL,
  `event_day` varchar(255) NOT NULL,
  `name` varchar(64) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `shop` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsbcafejt7kx57hfpvtknd7htp` (`shop`),
  CONSTRAINT `FKsbcafejt7kx57hfpvtknd7htp` FOREIGN KEY (`shop`) REFERENCES `shops` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event`
--

LOCK TABLES `event` WRITE;
/*!40000 ALTER TABLE `event` DISABLE KEYS */;
INSERT INTO `event` VALUES (1,'2022-07-03 09:03:34.286503',1,'2022-07-15','ｊｊｊ','2022-07-03 09:05:24.833071',1),(2,'2022-07-03 09:10:49.234082',0,'2022-07-15','66666','2022-07-03 09:10:49.234082',1),(3,'2022-07-03 09:11:17.904029',0,'2022-07-22','66666','2022-07-03 09:11:17.904029',1);
/*!40000 ALTER TABLE `event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `goods`
--

DROP TABLE IF EXISTS `goods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `goods` (
  `id` int NOT NULL AUTO_INCREMENT,
  `create_day` varchar(255) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `delete_flag` int NOT NULL,
  `name` varchar(64) NOT NULL,
  `picture` varchar(255) NOT NULL,
  `purchase_price` varchar(255) NOT NULL,
  `selling_price` varchar(255) NOT NULL,
  `stock` varchar(255) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `shop` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKj4txuvi0sdlkxef1ha8p94tev` (`shop`),
  CONSTRAINT `FKj4txuvi0sdlkxef1ha8p94tev` FOREIGN KEY (`shop`) REFERENCES `shops` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goods`
--

LOCK TABLES `goods` WRITE;
/*!40000 ALTER TABLE `goods` DISABLE KEYS */;
INSERT INTO `goods` VALUES (1,'2022-07-16','2022-07-03 09:03:01.765592',1,'１','noimage.jpg','10000','100','10','2022-07-03 09:03:10.332018',1),(2,'2022-07-16','2022-07-03 09:03:22.616475',0,'1','noimage.jpg','1000','1000','100','2022-07-03 09:03:22.616475',1);
/*!40000 ALTER TABLE `goods` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lm_ev_goods`
--

DROP TABLE IF EXISTS `lm_ev_goods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lm_ev_goods` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `delete_flag` int NOT NULL,
  `ev_order` varchar(255) NOT NULL,
  `lm_selling_price` varchar(255) NOT NULL,
  `sold_goods` varchar(255) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `event` int NOT NULL,
  `goods` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlgh3h2c88yyoht5ts9895qaxq` (`event`),
  KEY `FKhy9qve9dy1wfr0u1yvw9n4ot6` (`goods`),
  CONSTRAINT `FKhy9qve9dy1wfr0u1yvw9n4ot6` FOREIGN KEY (`goods`) REFERENCES `goods` (`id`),
  CONSTRAINT `FKlgh3h2c88yyoht5ts9895qaxq` FOREIGN KEY (`event`) REFERENCES `event` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lm_ev_goods`
--

LOCK TABLES `lm_ev_goods` WRITE;
/*!40000 ALTER TABLE `lm_ev_goods` DISABLE KEYS */;
INSERT INTO `lm_ev_goods` VALUES (1,'2022-07-03 09:11:33.872345',1,'0','1000','0','2022-07-03 09:11:39.288399',3,2),(2,'2022-07-03 09:11:43.236872',0,'0','1000','0','2022-07-03 09:11:55.861238',3,2);
/*!40000 ALTER TABLE `lm_ev_goods` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shops`
--

DROP TABLE IF EXISTS `shops`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shops` (
  `id` int NOT NULL AUTO_INCREMENT,
  `delete_flag` int NOT NULL,
  `name` varchar(64) NOT NULL,
  `priority_flag` int NOT NULL,
  `user` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgfot3xycuhyua4omnh787lwtu` (`user`),
  CONSTRAINT `FKgfot3xycuhyua4omnh787lwtu` FOREIGN KEY (`user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shops`
--

LOCK TABLES `shops` WRITE;
/*!40000 ALTER TABLE `shops` DISABLE KEYS */;
INSERT INTO `shops` VALUES (1,0,'11',0,1);
/*!40000 ALTER TABLE `shops` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `delete_flag` int NOT NULL,
  `mailaddress` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(64) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_fmlf36r0hao3bc15gpcfp1ls2` (`mailaddress`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'2022-07-03 09:00:58.251009',0,'7@7','田中太郎','F89E4BFDAABB06796BD342DFED16997011D306B377221BE6E6C409FC129F8D8D','2022-07-03 09:00:58.251009');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-07-03 16:37:57
