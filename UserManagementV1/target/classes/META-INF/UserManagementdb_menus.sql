CREATE DATABASE  IF NOT EXISTS `UserManagementdb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `UserManagementdb`;
-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: UserManagementdb
-- ------------------------------------------------------
-- Server version	8.0.22

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
-- Table structure for table `menus`
--

DROP TABLE IF EXISTS `menus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menus` (
  `id` int NOT NULL AUTO_INCREMENT,
  `menuName` varchar(255) DEFAULT NULL,
  `menuType` varchar(255) DEFAULT NULL,
  `menu_link_id` int DEFAULT NULL,
  `menuUrl` varchar(255) DEFAULT NULL,
  `showStatus` char(1) DEFAULT NULL,
  `privilege` varchar(100) DEFAULT NULL,
  `displayOrder` decimal(5,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmolhwc8oe2j9onteul6svj4vn` (`menu_link_id`),
  CONSTRAINT `FKmolhwc8oe2j9onteul6svj4vn` FOREIGN KEY (`menu_link_id`) REFERENCES `menus` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menus`
--

LOCK TABLES `menus` WRITE;
/*!40000 ALTER TABLE `menus` DISABLE KEYS */;
INSERT INTO `menus` VALUES (1,'Home','M',NULL,'/dashboard','1','READ_HOME_AUTHORITY',1.00),(3,'Admin','M',NULL,'#','1','READ_ADMIN_AUTHORITY',2.00),(4,'User Management','S',3,'/userManagement','1','READ_USER_AUTHORITY',2.20),(5,'Prgram Allocation','S',3,'#','1','WRITE_PROGRAM_AUTHORITY',2.10),(6,'Users List','P',4,'/listUsers','0','READ_USER_AUTHORITY',NULL),(7,'Add User','P',4,'/addUser','0','WRITE_USER_AUTHORITY',NULL),(8,'Edit User','P',4,'/editUser','0','WRITE_USER_AUTHORITY',NULL),(9,'Delete User','P',4,'/deleteUser','0','DELETE_USER_AUTHORITY',NULL),(10,'Account','M',NULL,'#','1','READ_ACCOUNT_AUTHORITY',3.00),(11,'Change Password','S',10,'/changePassword','1','CHANGE_PASSWORD_AUTHORITY',3.10),(12,'Logout','S',10,'/logout','1','LOGOUT_AUTHORITY',3.20),(13,'Process Change Password','P',11,'/processChangePassword','0','CHANGE_PASSWORD_AUTHORITY',NULL),(14,'Force Logout User','S',3,'/forceLogoutUser','1','FORCE_LOGOUT_USER',2.30),(15,'Change password Success','P',11,'/changePassSuccess','0','CHANGE_PASSWORD_AUTHORITY',NULL),(16,'Logged In Users','P',14,'/loggedInUsers','0','FORCE_LOGOUT_USER',NULL),(17,'User Role Management','S',3,'/userRoleManagement','1','READ_ROLE_AUTHORITY',2.40),(18,'User Roles List','P',17,'/listUserRoles','0','WRITE_ROLE_AUTHORITY',NULL),(19,'Add User Roles','P',17,'/addUserRole','0','WRITE_ROLE_AUTHORITY',NULL),(20,'Edit User Roles','P',17,'/editUserRole','0','WRITE_ROLE_AUTHORITY',NULL),(21,'Delete User Roles','P',17,'/deleteUserRole','0','DELETE_ROLE_AUTHORITY',NULL),(22,'Access Management','S',3,'/accessManagement','1','ACCESS_MANAGEMENT_AUTHORITY',2.50);
/*!40000 ALTER TABLE `menus` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-04  9:52:44
