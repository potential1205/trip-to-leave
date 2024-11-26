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
-- Table structure for table `trips`
--

DROP TABLE IF EXISTS `trips`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trips` (
  `trip_id` int NOT NULL AUTO_INCREMENT COMMENT 'Auto-increment primary key',
  `member_id` int NOT NULL COMMENT 'References members table',
  `title` varchar(100) NOT NULL,
  `content` text COMMENT 'Markdown formatted travel plan and review',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Updates automatically on record change',
  `likes` int DEFAULT '0',
  `overview` int DEFAULT '0',
  `start_at` datetime DEFAULT NULL COMMENT 'Start date and time of the trip',
  `end_at` datetime DEFAULT NULL COMMENT 'End date and time of the trip',
  `cover_image` varchar(255) DEFAULT NULL COMMENT 'URL or path to the cover image',
  `headings` text NOT NULL,
  PRIMARY KEY (`trip_id`),
  KEY `fk_trips_member_id` (`member_id`),
  CONSTRAINT `fk_trips_member_id` FOREIGN KEY (`member_id`) REFERENCES `members` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trips`
--

LOCK TABLES `trips` WRITE;
/*!40000 ALTER TABLE `trips` DISABLE KEYS */;
INSERT INTO `trips` VALUES (63,3,'제주에서의 느린 하루','# 제목 1\r\n## 제목 2\r\n### 제목 3','2024-11-25 08:16:39','2024-11-25 08:20:01',0,6,'2024-07-05 00:00:00','2024-07-09 00:00:00',NULL,'[{\"level\":1,\"text\":\"제목 1\",\"id\":\"제목-1\",\"locations\":[]},{\"level\":2,\"text\":\"제목 2\",\"id\":\"제목-2\",\"locations\":[]},{\"level\":3,\"text\":\"제목 3\",\"id\":\"제목-3\",\"locations\":[]}]'),(64,3,'경주의 시간 여행','# 제목 1\r\n## 제목 2\r\n### 제목 3','2024-11-25 08:19:29','2024-11-26 02:18:05',1,6,'2024-05-05 00:00:00','2024-05-10 00:00:00',NULL,'[{\"level\":1,\"text\":\"제목 1\",\"id\":\"제목-1\",\"locations\":[]},{\"level\":2,\"text\":\"제목 2\",\"id\":\"제목-2\",\"locations\":[]},{\"level\":3,\"text\":\"제목 3\",\"id\":\"제목-3\",\"locations\":[]}]'),(65,3,'여수 밤바다, 로맨틱한 야경','# 제목 1\r\n## 제목 2\r\n### 제목 3','2024-11-25 23:48:36','2024-11-26 02:02:09',1,7,'2024-09-12 00:00:00','2024-09-15 00:00:00',NULL,'[{\"level\":1,\"text\":\"제목 1\",\"id\":\"제목-1\",\"locations\":[]},{\"level\":2,\"text\":\"제목 2\",\"id\":\"제목-2\",\"locations\":[]},{\"level\":3,\"text\":\"제목 3\",\"id\":\"제목-3\",\"locations\":[]}]'),(66,3,'부산 도심 속 자연과 문화','# 제목 1\r\n## 제목 2\r\n### 제목 3','2024-11-25 23:51:18','2024-11-25 23:51:55',0,6,'2024-10-10 00:00:00','2024-10-12 00:00:00',NULL,'[{\"level\":1,\"text\":\"제목 1\",\"id\":\"제목-1\",\"locations\":[]},{\"level\":2,\"text\":\"제목 2\",\"id\":\"제목-2\",\"locations\":[]},{\"level\":3,\"text\":\"제목 3\",\"id\":\"제목-3\",\"locations\":[]}]'),(67,3,'강릉의 여름','# 제목 1\r\n## 제목 2\r\n### 제목 3','2024-11-25 23:53:59','2024-11-26 02:13:22',1,1,'2024-07-01 00:00:00','2024-07-05 00:00:00',NULL,'[{\"level\":1,\"text\":\"제목 1\",\"id\":\"제목-1\",\"locations\":[]},{\"level\":2,\"text\":\"제목 2\",\"id\":\"제목-2\",\"locations\":[]},{\"level\":3,\"text\":\"제목 3\",\"id\":\"제목-3\",\"locations\":[]}]'),(68,3,'서울의 숨겨진 골목길','# 제목 1\r\n## 제목 2\r\n### 제목 3','2024-11-25 23:57:30','2024-11-25 23:57:30',0,0,'2024-05-07 00:00:00','2024-05-09 00:00:00',NULL,'[{\"level\":1,\"text\":\"제목 1\",\"id\":\"제목-1\",\"locations\":[]},{\"level\":2,\"text\":\"제목 2\",\"id\":\"제목-2\",\"locations\":[]},{\"level\":3,\"text\":\"제목 3\",\"id\":\"제목-3\",\"locations\":[]}]'),(69,3,'속초의 맛과 멋','# 제목 1\r\n## 제목 2\r\n### 제목 3','2024-11-25 23:58:47','2024-11-26 02:08:28',1,0,'2024-08-09 00:00:00','2024-08-10 00:00:00',NULL,'[{\"level\":1,\"text\":\"제목 1\",\"id\":\"제목-1\",\"locations\":[]},{\"level\":2,\"text\":\"제목 2\",\"id\":\"제목-2\",\"locations\":[]},{\"level\":3,\"text\":\"제목 3\",\"id\":\"제목-3\",\"locations\":[]}]'),(70,3,'전주 한옥마을에서 즐기는 전통','# 제목 1\r\n## 제목 2\r\n### 제목 3','2024-11-26 00:00:10','2024-11-26 02:08:27',1,15,'2024-01-05 00:00:00','2024-01-10 00:00:00',NULL,'[{\"level\":1,\"text\":\"제목 1\",\"id\":\"제목-1\",\"locations\":[]},{\"level\":2,\"text\":\"제목 2\",\"id\":\"제목-2\",\"locations\":[]},{\"level\":3,\"text\":\"제목 3\",\"id\":\"제목-3\",\"locations\":[]}]'),(71,3,'평창의 설경과 낭만','# 제목 1\r\n## 제목 2\r\n### 제목 3','2024-11-26 00:02:05','2024-11-26 01:55:29',1,36,'2024-12-12 00:00:00','2024-12-20 00:00:00',NULL,'[{\"level\":1,\"text\":\"제목 1\",\"id\":\"제목-1\",\"locations\":[]},{\"level\":2,\"text\":\"제목 2\",\"id\":\"제목-2\",\"locations\":[]},{\"level\":3,\"text\":\"제목 3\",\"id\":\"제목-3\",\"locations\":[]}]');
/*!40000 ALTER TABLE `trips` ENABLE KEYS */;
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
