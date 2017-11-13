-- MySQL dump 10.13  Distrib 5.7.12, for Win32 (AMD64)
--
-- Host: localhost    Database: znapdb
-- ------------------------------------------------------
-- Server version	5.7.16-log

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
-- Table structure for table `adminapi_admin`
--

DROP TABLE IF EXISTS `adminapi_admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `adminapi_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(254) NOT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `adminapi_admin`
--

LOCK TABLES `adminapi_admin` WRITE;
/*!40000 ALTER TABLE `adminapi_admin` DISABLE KEYS */;
/*!40000 ALTER TABLE `adminapi_admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auth_group`
--

DROP TABLE IF EXISTS `auth_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auth_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(80) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_group`
--

LOCK TABLES `auth_group` WRITE;
/*!40000 ALTER TABLE `auth_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `auth_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auth_group_permissions`
--

DROP TABLE IF EXISTS `auth_group_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auth_group_permissions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `auth_group_permissions_group_id_0cd325b0_uniq` (`group_id`,`permission_id`),
  KEY `auth_group_permissi_permission_id_84c5c92e_fk_auth_permission_id` (`permission_id`),
  CONSTRAINT `auth_group_permissi_permission_id_84c5c92e_fk_auth_permission_id` FOREIGN KEY (`permission_id`) REFERENCES `auth_permission` (`id`),
  CONSTRAINT `auth_group_permissions_group_id_b120cbf9_fk_auth_group_id` FOREIGN KEY (`group_id`) REFERENCES `auth_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_group_permissions`
--

LOCK TABLES `auth_group_permissions` WRITE;
/*!40000 ALTER TABLE `auth_group_permissions` DISABLE KEYS */;
/*!40000 ALTER TABLE `auth_group_permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auth_permission`
--

DROP TABLE IF EXISTS `auth_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auth_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `content_type_id` int(11) NOT NULL,
  `codename` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `auth_permission_content_type_id_01ab375a_uniq` (`content_type_id`,`codename`),
  CONSTRAINT `auth_permissi_content_type_id_2f476e4b_fk_django_content_type_id` FOREIGN KEY (`content_type_id`) REFERENCES `django_content_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_permission`
--

LOCK TABLES `auth_permission` WRITE;
/*!40000 ALTER TABLE `auth_permission` DISABLE KEYS */;
INSERT INTO `auth_permission` VALUES (1,'Can add log entry',1,'add_logentry'),(2,'Can change log entry',1,'change_logentry'),(3,'Can delete log entry',1,'delete_logentry'),(4,'Can add permission',2,'add_permission'),(5,'Can change permission',2,'change_permission'),(6,'Can delete permission',2,'delete_permission'),(7,'Can add user',3,'add_user'),(8,'Can change user',3,'change_user'),(9,'Can delete user',3,'delete_user'),(10,'Can add group',4,'add_group'),(11,'Can change group',4,'change_group'),(12,'Can delete group',4,'delete_group'),(13,'Can add content type',5,'add_contenttype'),(14,'Can change content type',5,'change_contenttype'),(15,'Can delete content type',5,'delete_contenttype'),(16,'Can add session',6,'add_session'),(17,'Can change session',6,'change_session'),(18,'Can delete session',6,'delete_session'),(19,'Can add user',7,'add_userprofile'),(20,'Can change user',7,'change_userprofile'),(21,'Can delete user',7,'delete_userprofile'),(22,'Can add Token',8,'add_token'),(23,'Can change Token',8,'change_token'),(24,'Can delete Token',8,'delete_token'),(25,'Can add admin',9,'add_admin'),(26,'Can change admin',9,'change_admin'),(27,'Can delete admin',9,'delete_admin'),(28,'Can add rate',10,'add_rate'),(29,'Can change rate',10,'change_rate'),(30,'Can delete rate',10,'delete_rate'),(31,'Can add dialog',11,'add_dialog'),(32,'Can change dialog',11,'change_dialog'),(33,'Can delete dialog',11,'delete_dialog'),(34,'Can add history of records',12,'add_historyofrecords'),(35,'Can change history of records',12,'change_historyofrecords'),(36,'Can delete history of records',12,'delete_historyofrecords'),(37,'Can add info about cnap',13,'add_infoaboutcnap'),(38,'Can change info about cnap',13,'change_infoaboutcnap'),(39,'Can delete info about cnap',13,'delete_infoaboutcnap'),(40,'Can add type of cnap',14,'add_typeofcnap'),(41,'Can change type of cnap',14,'change_typeofcnap'),(42,'Can delete type of cnap',14,'delete_typeofcnap'),(43,'Can add cnap with type',15,'add_cnapwithtype'),(44,'Can change cnap with type',15,'change_cnapwithtype'),(45,'Can delete cnap with type',15,'delete_cnapwithtype');
/*!40000 ALTER TABLE `auth_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auth_user`
--

DROP TABLE IF EXISTS `auth_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auth_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `password` varchar(128) NOT NULL,
  `last_login` datetime(6) DEFAULT NULL,
  `is_superuser` tinyint(1) NOT NULL,
  `username` varchar(150) NOT NULL,
  `first_name` varchar(30) NOT NULL,
  `last_name` varchar(30) NOT NULL,
  `email` varchar(254) NOT NULL,
  `is_staff` tinyint(1) NOT NULL,
  `is_active` tinyint(1) NOT NULL,
  `date_joined` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_user`
--

LOCK TABLES `auth_user` WRITE;
/*!40000 ALTER TABLE `auth_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `auth_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auth_user_groups`
--

DROP TABLE IF EXISTS `auth_user_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auth_user_groups` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `auth_user_groups_user_id_94350c0c_uniq` (`user_id`,`group_id`),
  KEY `auth_user_groups_group_id_97559544_fk_auth_group_id` (`group_id`),
  CONSTRAINT `auth_user_groups_group_id_97559544_fk_auth_group_id` FOREIGN KEY (`group_id`) REFERENCES `auth_group` (`id`),
  CONSTRAINT `auth_user_groups_user_id_6a12ed8b_fk_auth_user_id` FOREIGN KEY (`user_id`) REFERENCES `auth_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_user_groups`
--

LOCK TABLES `auth_user_groups` WRITE;
/*!40000 ALTER TABLE `auth_user_groups` DISABLE KEYS */;
/*!40000 ALTER TABLE `auth_user_groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auth_user_user_permissions`
--

DROP TABLE IF EXISTS `auth_user_user_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auth_user_user_permissions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `auth_user_user_permissions_user_id_14a6b632_uniq` (`user_id`,`permission_id`),
  KEY `auth_user_user_perm_permission_id_1fbb5f2c_fk_auth_permission_id` (`permission_id`),
  CONSTRAINT `auth_user_user_perm_permission_id_1fbb5f2c_fk_auth_permission_id` FOREIGN KEY (`permission_id`) REFERENCES `auth_permission` (`id`),
  CONSTRAINT `auth_user_user_permissions_user_id_a95ead1b_fk_auth_user_id` FOREIGN KEY (`user_id`) REFERENCES `auth_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_user_user_permissions`
--

LOCK TABLES `auth_user_user_permissions` WRITE;
/*!40000 ALTER TABLE `auth_user_user_permissions` DISABLE KEYS */;
/*!40000 ALTER TABLE `auth_user_user_permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authtoken_token`
--

DROP TABLE IF EXISTS `authtoken_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authtoken_token` (
  `key` varchar(40) NOT NULL,
  `created` datetime(6) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`key`),
  UNIQUE KEY `user_id` (`user_id`),
  CONSTRAINT `authtoken_token_user_id_35299eff_fk_auth_user_id` FOREIGN KEY (`user_id`) REFERENCES `auth_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authtoken_token`
--

LOCK TABLES `authtoken_token` WRITE;
/*!40000 ALTER TABLE `authtoken_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `authtoken_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `django_admin_log`
--

DROP TABLE IF EXISTS `django_admin_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `django_admin_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `action_time` datetime(6) NOT NULL,
  `object_id` longtext,
  `object_repr` varchar(200) NOT NULL,
  `action_flag` smallint(5) unsigned NOT NULL,
  `change_message` longtext NOT NULL,
  `content_type_id` int(11) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `django_admin__content_type_id_c4bce8eb_fk_django_content_type_id` (`content_type_id`),
  KEY `django_admin_log_user_id_c564eba6_fk_auth_user_id` (`user_id`),
  CONSTRAINT `django_admin__content_type_id_c4bce8eb_fk_django_content_type_id` FOREIGN KEY (`content_type_id`) REFERENCES `django_content_type` (`id`),
  CONSTRAINT `django_admin_log_user_id_c564eba6_fk_auth_user_id` FOREIGN KEY (`user_id`) REFERENCES `auth_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `django_admin_log`
--

LOCK TABLES `django_admin_log` WRITE;
/*!40000 ALTER TABLE `django_admin_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `django_admin_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `django_content_type`
--

DROP TABLE IF EXISTS `django_content_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `django_content_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_label` varchar(100) NOT NULL,
  `model` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `django_content_type_app_label_76bd3d3b_uniq` (`app_label`,`model`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `django_content_type`
--

LOCK TABLES `django_content_type` WRITE;
/*!40000 ALTER TABLE `django_content_type` DISABLE KEYS */;
INSERT INTO `django_content_type` VALUES (1,'admin','logentry'),(9,'adminapi','admin'),(4,'auth','group'),(2,'auth','permission'),(3,'auth','user'),(8,'authtoken','token'),(5,'contenttypes','contenttype'),(15,'queueapi','cnapwithtype'),(12,'queueapi','historyofrecords'),(13,'queueapi','infoaboutcnap'),(14,'queueapi','typeofcnap'),(11,'rateapi','dialog'),(10,'rateapi','rate'),(6,'sessions','session'),(7,'userapi','userprofile');
/*!40000 ALTER TABLE `django_content_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `django_migrations`
--

DROP TABLE IF EXISTS `django_migrations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `django_migrations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `applied` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `django_migrations`
--

LOCK TABLES `django_migrations` WRITE;
/*!40000 ALTER TABLE `django_migrations` DISABLE KEYS */;
INSERT INTO `django_migrations` VALUES (1,'contenttypes','0001_initial','2017-11-10 12:28:57.026000'),(2,'auth','0001_initial','2017-11-10 12:29:20.041000'),(3,'admin','0001_initial','2017-11-10 12:29:24.493000'),(4,'admin','0002_logentry_remove_auto_add','2017-11-10 12:29:24.578000'),(5,'adminapi','0001_initial','2017-11-10 12:29:25.397000'),(6,'adminapi','0002_queue','2017-11-10 12:29:26.235000'),(7,'adminapi','0003_delete_queue','2017-11-10 12:29:26.792000'),(8,'contenttypes','0002_remove_content_type_name','2017-11-10 12:29:29.835000'),(9,'auth','0002_alter_permission_name_max_length','2017-11-10 12:29:31.337000'),(10,'auth','0003_alter_user_email_max_length','2017-11-10 12:29:32.708000'),(11,'auth','0004_alter_user_username_opts','2017-11-10 12:29:32.809000'),(12,'auth','0005_alter_user_last_login_null','2017-11-10 12:29:33.725000'),(13,'auth','0006_require_contenttypes_0002','2017-11-10 12:29:33.772000'),(14,'auth','0007_alter_validators_add_error_messages','2017-11-10 12:29:33.872000'),(15,'auth','0008_alter_user_username_max_length','2017-11-10 12:29:35.508000'),(16,'authtoken','0001_initial','2017-11-10 12:29:37.491000'),(17,'authtoken','0002_auto_20160226_1747','2017-11-10 12:29:38.913000'),(18,'queueapi','0001_initial','2017-11-10 12:29:47.527000'),(19,'queueapi','0002_auto_20171110_0403','2017-11-10 12:29:51.283000'),(20,'queueapi','0003_auto_20171110_0404','2017-11-10 12:29:55.982000'),(21,'rateapi','0001_initial','2017-11-10 12:30:01.015000'),(22,'rateapi','0002_auto_20171108_2142','2017-11-10 12:30:01.503000'),(23,'rateapi','0003_auto_20171110_0403','2017-11-10 12:30:01.931000'),(24,'rateapi','0004_auto_20171110_0403','2017-11-10 12:30:02.346000'),(25,'rateapi','0005_auto_20171110_0404','2017-11-10 12:30:02.600000'),(26,'rateapi','0006_auto_20171110_1428','2017-11-10 12:30:02.931000'),(27,'sessions','0001_initial','2017-11-10 12:30:04.181000'),(28,'userapi','0001_initial','2017-11-10 12:30:06.720000'),(29,'userapi','0002_auto_20171102_2125','2017-11-10 12:30:25.247000'),(30,'userapi','0003_auto_20171102_2126','2017-11-10 12:30:25.544000'),(31,'userapi','0004_auto_20171108_1504','2017-11-10 12:30:27.596000'),(32,'userapi','0005_auto_20171108_1529','2017-11-10 12:30:30.740000'),(33,'userapi','0006_auto_20171108_1540','2017-11-10 12:30:35.878000');
/*!40000 ALTER TABLE `django_migrations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `django_session`
--

DROP TABLE IF EXISTS `django_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `django_session` (
  `session_key` varchar(40) NOT NULL,
  `session_data` longtext NOT NULL,
  `expire_date` datetime(6) NOT NULL,
  PRIMARY KEY (`session_key`),
  KEY `django_session_de54fa62` (`expire_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `django_session`
--

LOCK TABLES `django_session` WRITE;
/*!40000 ALTER TABLE `django_session` DISABLE KEYS */;
/*!40000 ALTER TABLE `django_session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `queueapi_cnapwithtype`
--

DROP TABLE IF EXISTS `queueapi_cnapwithtype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `queueapi_cnapwithtype` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `peopleCounter` int(11) NOT NULL,
  `type_id` int(11) NOT NULL,
  `znap_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `queueapi_cnapwithtype_beef7dc8` (`type_id`),
  KEY `queueapi_cnapwithtype_e2bab324` (`znap_id`),
  CONSTRAINT `queueapi_cnapwitht_znap_id_528a8d52_fk_queueapi_infoaboutcnap_id` FOREIGN KEY (`znap_id`) REFERENCES `queueapi_infoaboutcnap` (`id`),
  CONSTRAINT `queueapi_cnapwithtype_type_id_db07b954_fk_queueapi_typeofcnap_id` FOREIGN KEY (`type_id`) REFERENCES `queueapi_typeofcnap` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `queueapi_cnapwithtype`
--

LOCK TABLES `queueapi_cnapwithtype` WRITE;
/*!40000 ALTER TABLE `queueapi_cnapwithtype` DISABLE KEYS */;
/*!40000 ALTER TABLE `queueapi_cnapwithtype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `queueapi_historyofrecords`
--

DROP TABLE IF EXISTS `queueapi_historyofrecords`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `queueapi_historyofrecords` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time` datetime(6) NOT NULL,
  `history_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `queueapi_history_history_id_52a8b8a4_fk_queueapi_cnapwithtype_id` (`history_id`),
  KEY `queueapi_historyofrecords_user_id_4a013e8c_fk_auth_user_id` (`user_id`),
  CONSTRAINT `queueapi_history_history_id_52a8b8a4_fk_queueapi_cnapwithtype_id` FOREIGN KEY (`history_id`) REFERENCES `queueapi_cnapwithtype` (`id`),
  CONSTRAINT `queueapi_historyofrecords_user_id_4a013e8c_fk_auth_user_id` FOREIGN KEY (`user_id`) REFERENCES `auth_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `queueapi_historyofrecords`
--

LOCK TABLES `queueapi_historyofrecords` WRITE;
/*!40000 ALTER TABLE `queueapi_historyofrecords` DISABLE KEYS */;
/*!40000 ALTER TABLE `queueapi_historyofrecords` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `queueapi_infoaboutcnap`
--

DROP TABLE IF EXISTS `queueapi_infoaboutcnap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `queueapi_infoaboutcnap` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(220) NOT NULL,
  `adress` varchar(250) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `queueapi_infoaboutcnap`
--

LOCK TABLES `queueapi_infoaboutcnap` WRITE;
/*!40000 ALTER TABLE `queueapi_infoaboutcnap` DISABLE KEYS */;
/*!40000 ALTER TABLE `queueapi_infoaboutcnap` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `queueapi_typeofcnap`
--

DROP TABLE IF EXISTS `queueapi_typeofcnap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `queueapi_typeofcnap` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(220) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `queueapi_typeofcnap`
--

LOCK TABLES `queueapi_typeofcnap` WRITE;
/*!40000 ALTER TABLE `queueapi_typeofcnap` DISABLE KEYS */;
/*!40000 ALTER TABLE `queueapi_typeofcnap` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rateapi_dialog`
--

DROP TABLE IF EXISTS `rateapi_dialog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rateapi_dialog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `message` varchar(200) NOT NULL,
  `timeStamp` datetime(6) NOT NULL,
  `dialog_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `rateapi_dialog_d8de0298` (`dialog_id`),
  CONSTRAINT `rateapi_dialog_dialog_id_34b1106d_fk_rateapi_rate_id` FOREIGN KEY (`dialog_id`) REFERENCES `rateapi_rate` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rateapi_dialog`
--

LOCK TABLES `rateapi_dialog` WRITE;
/*!40000 ALTER TABLE `rateapi_dialog` DISABLE KEYS */;
/*!40000 ALTER TABLE `rateapi_dialog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rateapi_rate`
--

DROP TABLE IF EXISTS `rateapi_rate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rateapi_rate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `quality` int(11) NOT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(1) NOT NULL,
  `is_closed` tinyint(1) NOT NULL,
  `admin_id` int(11) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `rateapi_rate_admin_id_2c5c4063_fk_adminapi_admin_id` (`admin_id`),
  KEY `rateapi_rate_user_id_93789156_fk_auth_user_id` (`user_id`),
  CONSTRAINT `rateapi_rate_admin_id_2c5c4063_fk_adminapi_admin_id` FOREIGN KEY (`admin_id`) REFERENCES `adminapi_admin` (`id`),
  CONSTRAINT `rateapi_rate_user_id_93789156_fk_auth_user_id` FOREIGN KEY (`user_id`) REFERENCES `auth_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rateapi_rate`
--

LOCK TABLES `rateapi_rate` WRITE;
/*!40000 ALTER TABLE `rateapi_rate` DISABLE KEYS */;
/*!40000 ALTER TABLE `rateapi_rate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userapi_userprofile`
--

DROP TABLE IF EXISTS `userapi_userprofile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userapi_userprofile` (
  `user_ptr_id` int(11) NOT NULL,
  `phone` varchar(15) NOT NULL,
  `middle_name` varchar(50) NOT NULL,
  PRIMARY KEY (`user_ptr_id`),
  CONSTRAINT `userapi_userprofile_user_ptr_id_62c721df_fk_auth_user_id` FOREIGN KEY (`user_ptr_id`) REFERENCES `auth_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userapi_userprofile`
--

LOCK TABLES `userapi_userprofile` WRITE;
/*!40000 ALTER TABLE `userapi_userprofile` DISABLE KEYS */;
/*!40000 ALTER TABLE `userapi_userprofile` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-13 17:55:30
