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
-- Table structure for table `trip_hashtags`
--

DROP TABLE IF EXISTS `trip_hashtags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trip_hashtags` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'Auto-increment primary key',
  `trip_id` int NOT NULL COMMENT 'References trips table',
  `hashtag_id` int NOT NULL COMMENT 'References hashtags table',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_trip_hashtag` (`trip_id`,`hashtag_id`),
  KEY `fk_trip_hashtags_trip_id` (`trip_id`),
  KEY `fk_trip_hashtags_hashtag_id` (`hashtag_id`),
  CONSTRAINT `fk_trip_hashtags_hashtag_id` FOREIGN KEY (`hashtag_id`) REFERENCES `hashtags` (`hashtag_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_trip_hashtags_trip_id` FOREIGN KEY (`trip_id`) REFERENCES `trips` (`trip_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=140 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trip_hashtags`
--

LOCK TABLES `trip_hashtags` WRITE;
/*!40000 ALTER TABLE `trip_hashtags` DISABLE KEYS */;
INSERT INTO `trip_hashtags` VALUES (93,63,87),(91,63,88),(92,63,89),(103,64,90),(105,64,91),(104,64,99),(102,65,93),(100,65,94),(101,65,99),(97,66,88),(98,66,89),(99,66,95),(108,67,88),(106,67,105),(107,67,106),(110,68,108),(109,68,109),(112,69,110),(113,69,111),(111,69,112),(114,70,113),(115,70,114),(117,71,115),(116,71,116);
/*!40000 ALTER TABLE `trip_hashtags` ENABLE KEYS */;
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
