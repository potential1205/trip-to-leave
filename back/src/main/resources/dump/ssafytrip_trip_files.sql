-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: ssafytrip
-- ------------------------------------------------------
-- Server version	8.0.38

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `trip_files`
--

DROP TABLE IF EXISTS `trip_files`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trip_files` (
  `file_id` int NOT NULL AUTO_INCREMENT,
  `trip_id` int NOT NULL,
  `file_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `file_path` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL,
  `content_type` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `file_type` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'NORMAL',
  `sequence` int DEFAULT NULL,
  PRIMARY KEY (`file_id`),
  KEY `trip_id` (`trip_id`),
  CONSTRAINT `trip_files_ibfk_1` FOREIGN KEY (`trip_id`) REFERENCES `trips` (`trip_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=99 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trip_files`
--

LOCK TABLES `trip_files` WRITE;
/*!40000 ALTER TABLE `trip_files` DISABLE KEYS */;
INSERT INTO `trip_files` VALUES (79,63,'제주도.jpg','C:/uploads/trips/63/제주도.jpg','image/jpeg','COVER',0),(80,64,'경주.jfif','C:/uploads/trips/64/경주.jfif','image/jpeg','COVER',0),(81,65,'여수.jpg','C:/uploads/trips/65/여수.jpg','image/jpeg','COVER',0),(82,66,'부산.jpg','C:/uploads/trips/66/부산.jpg','image/jpeg','COVER',0),(83,67,'강릉.jpg','C:/uploads/trips/67/강릉.jpg','image/jpeg','COVER',0),(84,68,'서울.jpg','C:/uploads/trips/68/서울.jpg','image/jpeg','COVER',0),(85,69,'속초.jpg','C:/uploads/trips/69/속초.jpg','image/jpeg','COVER',0),(86,70,'전주.jpg','C:/uploads/trips/70/전주.jpg','image/jpeg','COVER',0),(87,71,'평창.jpg','C:/uploads/trips/71/평창.jpg','image/jpeg','COVER',0);
/*!40000 ALTER TABLE `trip_files` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-26 11:38:13
