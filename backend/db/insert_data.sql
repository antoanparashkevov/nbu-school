-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: grading_center
-- ------------------------------------------------------
-- Server version	8.0.34

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
-- Dumping data for table `grade`
--

LOCK TABLES `grade` WRITE;
/*!40000 ALTER TABLE `grade` DISABLE KEYS */;
INSERT INTO `grade` VALUES (1,'9B',1),(2,'9A',1);
/*!40000 ALTER TABLE `grade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `headmaster`
--

LOCK TABLES `headmaster` WRITE;
/*!40000 ALTER TABLE `headmaster` DISABLE KEYS */;
INSERT INTO `headmaster` VALUES (2);
/*!40000 ALTER TABLE `headmaster` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `mark`
--

LOCK TABLES `mark` WRITE;
/*!40000 ALTER TABLE `mark` DISABLE KEYS */;
INSERT INTO `mark` VALUES (6,2,10,3,16),(7,2.03,10,2,16);
/*!40000 ALTER TABLE `mark` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `parent`
--

LOCK TABLES `parent` WRITE;
/*!40000 ALTER TABLE `parent` DISABLE KEYS */;
INSERT INTO `parent` VALUES (14);
/*!40000 ALTER TABLE `parent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `programSlot`
--

LOCK TABLES `programSlot` WRITE;
/*!40000 ALTER TABLE `programSlot` DISABLE KEYS */;
/*!40000 ALTER TABLE `programSlot` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `school`
--

LOCK TABLES `school` WRITE;
/*!40000 ALTER TABLE `school` DISABLE KEYS */;
INSERT INTO `school` VALUES (1,'pimen zografski 38','vasil levski',2);
/*!40000 ALTER TABLE `school` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (4, '022222222', 10,1,1);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `student_parents`
--

LOCK TABLES `student_parents` WRITE;
/*!40000 ALTER TABLE `student_parents` DISABLE KEYS */;
INSERT INTO `student_parents` VALUES (10,14);
/*!40000 ALTER TABLE `student_parents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `subject`
--

LOCK TABLES `subject` WRITE;
/*!40000 ALTER TABLE `subject` DISABLE KEYS */;
INSERT INTO `subject` VALUES (2,'MATHEMATICS',1,1,16),(3,'ENGLISH',1,1,16);
/*!40000 ALTER TABLE `subject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `teacher`
--

LOCK TABLES `teacher` WRITE;
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
INSERT INTO `teacher` VALUES (16,1),(21,1);
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (2,'Direktor',_binary '',_binary '',_binary '',_binary '','Direktorov','$2a$10$6.//vizOmjmlDkourdPytuTXtinDs1wTaFiE/vhBUB98t4vX2oxh6','direktor123'),(10,'Student1',_binary '',_binary '',_binary '',_binary '','Studentov1','$2a$10$gAQYkffueF8c3dLkN4EzJ.wh.m1z2wSP1PA3nnWmLdCR/q9p6mvc2','student123'),(14,'Roditel',_binary '',_binary '',_binary '',_binary '','Roditelov','$2a$10$8Ng/dSB8Q5xC7kQyNXFvkOKo5AFDnihPjBYLpAq6swcrRuNEVnAqC','rodi123'),(16,'Uchitelov2',_binary '',_binary '',_binary '',_binary '','Uchitelov','$2a$10$Z38KrWs2DSKIcBK0UGz2deuZfXTJG4QUI9sWNvUvGZSIC/Kb2PJ7G','uchi123'),(21,'Teacher',_binary '',_binary '',_binary '',_binary '','Teacherski','$2a$10$g/QZduF0g0vXKRY5//bmPOL4nHzW/nVv7vbogQ69h6LBE60XpEHxm','teach123');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_authorities`
--

LOCK TABLES `user_authorities` WRITE;
/*!40000 ALTER TABLE `user_authorities` DISABLE KEYS */;
INSERT INTO `user_authorities` VALUES (2,2),(14,3),(10,4),(16,5),(21,5);
/*!40000 ALTER TABLE `user_authorities` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-23 13:29:12
