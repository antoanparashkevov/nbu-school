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
INSERT INTO `headmaster` VALUES (2),(25);
/*!40000 ALTER TABLE `headmaster` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `mark`
--

LOCK TABLES `mark` WRITE;
/*!40000 ALTER TABLE `mark` DISABLE KEYS */;
INSERT INTO `mark` VALUES (6,2,10,3,16),(11,5,10,6,26);
/*!40000 ALTER TABLE `mark` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `parent`
--

LOCK TABLES `parent` WRITE;
/*!40000 ALTER TABLE `parent` DISABLE KEYS */;
INSERT INTO `parent` VALUES (14),(24);
/*!40000 ALTER TABLE `parent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `program_slot`
--

LOCK TABLES `program_slot` WRITE;
/*!40000 ALTER TABLE `program_slot` DISABLE KEYS */;
INSERT INTO `program_slot` VALUES (1,'MONDAY',2,2),(2,'MONDAY',1,3),(3,'MONDAY',3,2),(4,'MONDAY',4,4),(5,'MONDAY',5,4),(6,'MONDAY',6,5),(31,'TUESDAY',1,2),(32,'TUESDAY',2,3),(33,'TUESDAY',3,8),(34,'TUESDAY',4,8),(35,'TUESDAY',5,9),(36,'TUESDAY',6,10),(37,'WEDNESDAY',1,11),(38,'WEDNESDAY',2,11),(39,'WEDNESDAY',3,2),(40,'WEDNESDAY',4,13),(41,'WEDNESDAY',5,7),(42,'WEDNESDAY',6,7),(43,'THURSDAY',1,7),(44,'THURSDAY',2,6),(45,'THURSDAY',3,5),(46,'THURSDAY',4,5),(47,'THURSDAY',5,3),(48,'THURSDAY',6,10),(49,'FRIDAY',1,10),(50,'FRIDAY',2,11),(51,'FRIDAY',3,11),(52,'FRIDAY',4,12),(53,'FRIDAY',5,5),(54,'FRIDAY',6,3);
/*!40000 ALTER TABLE `program_slot` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_HEADMASTER'),(3,'ROLE_PARENT'),(4,'ROLE_STUDENT'),(5,'ROLE_TEACHER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
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
INSERT INTO `student` VALUES (4,'142778899',10,1,1),(0,'0141166778',23,1,1);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `student_parents`
--

LOCK TABLES `student_parents` WRITE;
/*!40000 ALTER TABLE `student_parents` DISABLE KEYS */;
INSERT INTO `student_parents` VALUES (10,14),(10,24);
/*!40000 ALTER TABLE `student_parents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `subject`
--

LOCK TABLES `subject` WRITE;
/*!40000 ALTER TABLE `subject` DISABLE KEYS */;
INSERT INTO `subject` VALUES (2,'MATHEMATICS',1,1,16),(3,'ENGLISH',1,1,16),(4,'LITERATURE',1,1,26),(5,'GEOGRAPHY',1,1,26),(6,'DRAWING',1,1,26),(7,'MUSIC',1,1,26),(8,'BULGARIAN',1,1,16),(9,'RUSSIAN',1,1,16),(10,'PHYSICS',1,1,24),(11,'CHEMISTRY',1,1,24),(12,'SPANISH',1,1,24),(13,'FRENCH',1,1,24);
/*!40000 ALTER TABLE `subject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `teacher`
--

LOCK TABLES `teacher` WRITE;
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
INSERT INTO `teacher` VALUES (16,1),(21,1),(24,1),(26,1);
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'adminFirstName',_binary '',_binary '',_binary '',_binary '','adminLastName','$2a$10$r/J9fjOmbOXPXcZxwIVs6uofuwiTjjVP1NGoT.VilfStcSTRC305O','admin'),(2,'Direktor',_binary '',_binary '',_binary '',_binary '','Direktorov','$2a$10$6.//vizOmjmlDkourdPytuTXtinDs1wTaFiE/vhBUB98t4vX2oxh6','direktor123'),(10,'Student1',_binary '',_binary '',_binary '',_binary '','Studentov1','$2a$10$gAQYkffueF8c3dLkN4EzJ.wh.m1z2wSP1PA3nnWmLdCR/q9p6mvc2','student123'),(14,'Roditellll',_binary '',_binary '',_binary '',_binary '','Roditelovvvv','$2a$10$8Ng/dSB8Q5xC7kQyNXFvkOKo5AFDnihPjBYLpAq6swcrRuNEVnAqC','rodi123'),(16,'Uchitelov2',_binary '',_binary '',_binary '',_binary '','Uchitelov','$2a$10$Z38KrWs2DSKIcBK0UGz2deuZfXTJG4QUI9sWNvUvGZSIC/Kb2PJ7G','uchi123'),(21,'Teacher',_binary '',_binary '',_binary '',_binary '','Teacherski','$2a$10$g/QZduF0g0vXKRY5//bmPOL4nHzW/nVv7vbogQ69h6LBE60XpEHxm','teach123'),(23,'Student2',_binary '',_binary '',_binary '',_binary '','Studentov2','$2a$10$SlsYXq35ZymZdYtJX85ZDOYh036HVI7wBAVO28/9oD75WJwMwtTM6','student1234'),(24,'Teacher',_binary '',_binary '',_binary '',_binary '','Parentov','$2a$10$6.//vizOmjmlDkourdPytuTXtinDs1wTaFiE/vhBUB98t4vX2oxh6','pareacher'),(25,'Direktor2',_binary '',_binary '',_binary '',_binary '','Direktorov2','$2a$10$04rwkkXk8W7D1Hm0RW/oUu60Ohn41quQq5FwWWdoqssQKLSttCRCm','direktor77'),(26,'Tests Teacher',_binary '',_binary '',_binary '',_binary '','Test Teacherov','$2a$10$J81cCTtJHNpPbu/bjAgwQus49zpCqRxNOxZI4/G2Dic88OrME1LmK','testTeacher');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_authorities`
--

LOCK TABLES `user_authorities` WRITE;
/*!40000 ALTER TABLE `user_authorities` DISABLE KEYS */;
INSERT INTO `user_authorities` VALUES (1,1),(2,2),(25,2),(14,3),(24,3),(10,4),(23,4),(16,5),(21,5),(24,5),(26,5);
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

-- Dump completed on 2025-07-22 19:02:08
