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
-- Table structure for table `articles`
--

DROP TABLE IF EXISTS `articles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `articles` (
  `article_id` int NOT NULL AUTO_INCREMENT,
  `member_id` int NOT NULL COMMENT 'References members table. Only admins can create notices',
  `title` varchar(100) NOT NULL,
  `category` varchar(100) DEFAULT '공지사항' COMMENT 'Category of the notice: 공지사항, 오류, 문의',
  `content` text NOT NULL COMMENT 'Content of the notice',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `overview` int DEFAULT '0',
  PRIMARY KEY (`article_id`),
  KEY `fk_posts_member_id` (`member_id`),
  CONSTRAINT `fk_posts_member_id` FOREIGN KEY (`member_id`) REFERENCES `members` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `articles`
--

LOCK TABLES `articles` WRITE;
/*!40000 ALTER TABLE `articles` DISABLE KEYS */;
INSERT INTO `articles` VALUES (17,5,'서비스 이용 중 오류가 발생했습니다','버그제보','버그발생','2024-11-26 01:10:56','2024-11-26 01:10:56',0),(18,5,'이미지 업로드가 안 돼요','버그제보','...','2024-11-26 01:11:15','2024-11-26 01:11:15',0),(19,5,'계정 비밀번호 변경 관련 질문','문의','...','2024-11-26 01:11:27','2024-11-26 01:11:27',0),(20,5,'회원 탈퇴 절차에 대해 궁금합니다','문의','...','2024-11-26 01:11:53','2024-11-26 01:11:52',0),(21,5,'이용 약관 관련 문의드립니다','문의','...','2024-11-26 01:12:06','2024-11-26 01:12:06',0),(22,5,'개인정보 수정이 되지 않아요','버그제보','...','2024-11-26 01:12:17','2024-11-26 01:12:17',0),(23,5,'로그인 시도 시 무한 로딩 발생','버그제보','...','2024-11-26 01:12:40','2024-11-26 01:12:40',0),(24,5,'이미지 업로드 후 썸네일이 보이지 않음','버그제보','...','2024-11-26 01:12:53','2024-11-26 01:12:53',0),(25,5,'비밀번호 재설정 이메일이 도착하지 않습니다','문의','...','2024-11-26 01:13:14','2024-11-26 01:13:13',0),(26,5,'서비스 이용 중 접속이 자주 끊깁니다','버그제보','...','2024-11-26 01:13:48','2024-11-26 01:13:48',0),(27,5,'프로필 이미지 업데이트 오류','문의','프로필 이미지를 변경하려고 하면 \'파일 업로드 실패\'라는 오류 메시지가 뜨며 이미지가 업데이트되지 않습니다. JPEG 파일로 1MB 미만의 이미지인데도 불구하고 문제가 발생합니다. 이 문제 해결이 가능할까요?\r\n','2024-11-26 02:21:17','2024-11-26 02:21:16',0);
/*!40000 ALTER TABLE `articles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-26 11:38:12
