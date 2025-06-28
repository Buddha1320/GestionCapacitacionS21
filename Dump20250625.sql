-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: efip21
-- ------------------------------------------------------
-- Server version	8.0.13

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
-- Table structure for table `alumnos`
--

DROP TABLE IF EXISTS `alumnos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alumnos` (
  `id_alumno` int(11) NOT NULL AUTO_INCREMENT,
  `dni` varchar(20) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `apellido` varchar(100) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `telefono` varchar(50) DEFAULT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  PRIMARY KEY (`id_alumno`),
  UNIQUE KEY `dni` (`dni`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alumnos`
--

LOCK TABLES `alumnos` WRITE;
/*!40000 ALTER TABLE `alumnos` DISABLE KEYS */;
INSERT INTO `alumnos` VALUES (1,'10234234','Juan Carlos','Rodriguez','jcrodri@gmail.com','3513223344','1980-05-04'),(2,'33567654','Maria La','Olmos','lauraolmos@gmail.com','351322478984','1998-02-08'),(3,'32879345','Laura','Richi','laurar@gmail.com','3513223355','1989-02-03'),(4,'40234445','Romina','Golt','rominita@gmail.com','351436623344','2001-02-11'),(5,'31666999','Cesar','Peters','elgato@gmail.com','3853223344','1999-02-01'),(6,'56565778','Carlos','Fillman','fill@gmail.com','355541555','1999-02-05'),(7,'73457566','Kurt','Benedetto','kurtb@gmail.com','545651852','1999-02-12'),(8,'34564565','Walter','Zuniga','wzuniga@gmail.com','8554525888','1999-02-21'),(9,'98644544','Belen','Mareco','belubelu@gmail.com','652554522','1999-04-13'),(10,'23455666','Paula','Santamaria','polysan@gmail.com','482121883','2001-02-07'),(11,'65554433','Fernando','Palacios','vampiro@gmail.com','355852255','2001-02-09'),(12,'56753221','Ines','Robertson','ine5566@gmail.com','852525555','2001-02-27'),(13,'13243678','Pamela','Ochoa','pamelita@gmail.com','854223588','2001-09-14'),(14,'45467575','Sergio','Ramos','misterramos@gmail.com','454365465','2001-09-14'),(15,'66565654','Noam','Chomsky','Chomsky@gmail.com','65676544','2001-09-14'),(16,'56565489','Teodoro','Talles','TTalles@gmail.com','577546546','2001-09-14'),(17,'33546466','Charly','Paez','CPerez@gmail.com','565465467','2001-09-14'),(18,'87787744','Henry','Fallol','fallolH@gmail.com','787643322','2001-09-14'),(19,'87665444','Peter','Drucker','peter@gmail.com','797553222','2001-09-14'),(20,'14647732','Marilyn','Deboua','mari@gmail.com','365475878','2001-09-14');
/*!40000 ALTER TABLE `alumnos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `alumnos_carreras`
--

DROP TABLE IF EXISTS `alumnos_carreras`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alumnos_carreras` (
  `id_alumnos_carreras` varchar(7) NOT NULL,
  `fk_id_carrera` int(11) DEFAULT NULL,
  `fk_id_alumno` int(11) DEFAULT NULL,
  `fecha_inscripcion` datetime DEFAULT NULL,
  `fk_estado_carrera` int(11) DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  PRIMARY KEY (`id_alumnos_carreras`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alumnos_carreras`
--

LOCK TABLES `alumnos_carreras` WRITE;
/*!40000 ALTER TABLE `alumnos_carreras` DISABLE KEYS */;
INSERT INTO `alumnos_carreras` VALUES ('BIGD001',102030,1,'2025-06-01 00:00:00',2,'2025-06-23 01:34:22'),('BIGD002',102030,2,'2025-06-01 00:00:00',1,'2025-06-01 00:00:00'),('BIGD003',102030,3,'2025-06-01 00:00:00',1,'2025-06-01 00:00:00'),('BIGD004',102030,4,'2025-06-01 00:00:00',1,'2025-06-01 00:00:00'),('BIGD005',102030,5,'2025-06-01 00:00:00',1,'2025-06-01 00:00:00'),('COMS001',102050,9,'2025-06-01 00:00:00',3,'2025-06-24 23:26:20'),('COMS002',102050,10,'2025-06-01 00:00:00',1,'2025-06-01 00:00:00'),('RRHH001',102040,6,'2025-06-01 00:00:00',2,'2025-06-25 02:01:19'),('RRHH002',102040,7,'2025-06-01 00:00:00',2,'2025-06-26 22:02:22'),('RRHH003',102040,8,'2025-06-01 00:00:00',2,'2025-06-25 02:44:55');
/*!40000 ALTER TABLE `alumnos_carreras` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `alumnos_cursos`
--

DROP TABLE IF EXISTS `alumnos_cursos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alumnos_cursos` (
  `id_inscripcion` int(11) NOT NULL AUTO_INCREMENT,
  `id_alumno` int(11) DEFAULT NULL,
  `id_curso` int(11) DEFAULT NULL,
  `id_carrera` int(11) DEFAULT NULL,
  `fecha_inscripcion` datetime DEFAULT CURRENT_TIMESTAMP,
  `nota` int(11) DEFAULT NULL,
  `estado` varchar(50) DEFAULT NULL,
  `fk_estado_id` int(11) DEFAULT NULL,
  `fecha_update` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_inscripcion`),
  KEY `id_alumno` (`id_alumno`),
  KEY `id_curso` (`id_curso`),
  KEY `id_carrera` (`id_carrera`),
  CONSTRAINT `alumnos_cursos_ibfk_1` FOREIGN KEY (`id_alumno`) REFERENCES `alumnos` (`id_alumno`),
  CONSTRAINT `alumnos_cursos_ibfk_2` FOREIGN KEY (`id_curso`) REFERENCES `cursos` (`id_curso`),
  CONSTRAINT `alumnos_cursos_ibfk_3` FOREIGN KEY (`id_carrera`) REFERENCES `carreras` (`id_carrera`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alumnos_cursos`
--

LOCK TABLES `alumnos_cursos` WRITE;
/*!40000 ALTER TABLE `alumnos_cursos` DISABLE KEYS */;
INSERT INTO `alumnos_cursos` VALUES (21,5,1095,102030,'2025-06-22 00:41:01',6,'cursando',5,'2025-06-23 01:15:53'),(22,7,1090,102040,'2025-06-22 01:34:25',9,'cursando',4,'2025-06-22 01:36:48'),(23,6,1090,102040,'2025-06-22 01:35:27',6,'cursando',5,'2025-06-22 01:37:01'),(24,7,1090,102040,'2025-06-22 01:35:35',9,'cursando',4,'2025-06-22 01:36:48'),(25,7,1091,102040,'2025-06-22 02:25:05',4,'cursando',5,'2025-06-22 02:27:57'),(26,7,1092,102040,'2025-06-22 02:25:14',NULL,'cursando',2,'2025-06-22 02:25:14'),(27,8,1092,102040,'2025-06-22 02:26:29',9,'cursando',4,'2025-06-24 23:04:51'),(28,1,1095,102030,'2025-06-23 01:33:35',NULL,'cursando',2,'2025-06-23 01:33:35'),(29,8,1091,102040,'2025-06-25 02:00:11',NULL,'cursando',2,'2025-06-25 02:00:11'),(30,8,1090,102040,'2025-06-25 02:28:43',NULL,'cursando',2,'2025-06-25 02:28:43');
/*!40000 ALTER TABLE `alumnos_cursos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `carreras`
--

DROP TABLE IF EXISTS `carreras`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `carreras` (
  `id_carrera` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `descripcion` text,
  `duracion_semanas` int(11) DEFAULT NULL,
  `codigo_carrera` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`id_carrera`)
) ENGINE=InnoDB AUTO_INCREMENT=103001 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carreras`
--

LOCK TABLES `carreras` WRITE;
/*!40000 ALTER TABLE `carreras` DISABLE KEYS */;
INSERT INTO `carreras` VALUES (102030,'Big Data','Diplomatura en Big Data',24,'BIGD'),(102040,'RRHH','Diplomatura en RRHH',24,'RRHH'),(102050,'Comunicacion Social','Tecnicatura en Comunicacion Social',12,'COMS'),(102060,'Derecho Comercial','Diplomatura en Derecho Comercial',36,'DERC'),(102070,'Seguridad','Diplomatuea en Seguridad e Higiene',36,'SECU'),(102080,'Project Manager','Diplomatura en Project Manager',12,'PROM'),(102090,'Marketing','Diplomatura en Marketing',36,'MKT'),(103000,'Periodismo','Tecnicatura en Periodismo',24,'PER');
/*!40000 ALTER TABLE `carreras` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cursos`
--

DROP TABLE IF EXISTS `cursos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cursos` (
  `id_curso` int(11) NOT NULL AUTO_INCREMENT,
  `nombre_materia` varchar(255) NOT NULL,
  `id_carrera` int(11) DEFAULT NULL,
  `fecha_inicio` date DEFAULT NULL,
  `fecha_fin` date DEFAULT NULL,
  `hora_inicio` time DEFAULT NULL,
  `hora_fin` time DEFAULT NULL,
  `dia_semana` varchar(100) DEFAULT NULL,
  `capacidad_maxima` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_curso`),
  KEY `id_carrera` (`id_carrera`),
  CONSTRAINT `cursos_ibfk_1` FOREIGN KEY (`id_carrera`) REFERENCES `carreras` (`id_carrera`)
) ENGINE=InnoDB AUTO_INCREMENT=1100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cursos`
--

LOCK TABLES `cursos` WRITE;
/*!40000 ALTER TABLE `cursos` DISABLE KEYS */;
INSERT INTO `cursos` VALUES (1090,'Administracion',102040,'2025-06-05','2025-11-11','10:00:00','12:00:00','Lunes',20),(1091,'Control',102040,'2025-06-05','2025-11-11','10:00:00','12:00:00','Lunes',20),(1092,'Gestion Humana',102040,'2025-06-05','2025-11-11','10:00:00','12:00:00','Martes',20),(1093,'Base de Datos',102030,'2025-06-05','2025-11-11','10:00:00','12:00:00','Miercoles',20),(1094,'Redes',102030,'2025-06-09','2025-11-11','10:00:00','12:00:00','Jueves',20),(1095,'Programacion 1',102030,'2025-06-01','2025-11-11','10:00:00','12:00:00','Viernes',20),(1096,'Machine Learning',102030,'2025-06-25','2025-11-11','08:00:00','10:00:00','Lunes',20),(1097,'Comunicacion 1',102050,'2025-06-05','2025-11-11','08:00:00','10:00:00','Jueves',20),(1098,'Codigo Aduanero ',102050,'2025-06-05','2025-11-11','08:00:00','10:00:00','Martes',20),(1099,'Trabajo en Altura',102050,'2025-06-05','2025-11-11','08:00:00','10:00:00','Miercoles',20);
/*!40000 ALTER TABLE `cursos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estado_carrera`
--

DROP TABLE IF EXISTS `estado_carrera`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estado_carrera` (
  `id_estado_carrera` int(11) NOT NULL,
  `descripcion_estado_carrera` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_estado_carrera`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estado_carrera`
--

LOCK TABLES `estado_carrera` WRITE;
/*!40000 ALTER TABLE `estado_carrera` DISABLE KEYS */;
INSERT INTO `estado_carrera` VALUES (1,'preinscripto'),(2,'regular'),(3,'egresado'),(4,'libre');
/*!40000 ALTER TABLE `estado_carrera` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estado_materia`
--

DROP TABLE IF EXISTS `estado_materia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estado_materia` (
  `id_estado_materia` int(11) NOT NULL,
  `descripcion_estado_materia` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_estado_materia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estado_materia`
--

LOCK TABLES `estado_materia` WRITE;
/*!40000 ALTER TABLE `estado_materia` DISABLE KEYS */;
INSERT INTO `estado_materia` VALUES (1,'preinscripto'),(2,'cursando'),(3,'regular'),(4,'aprobada'),(5,'libre');
/*!40000 ALTER TABLE `estado_materia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notas`
--

DROP TABLE IF EXISTS `notas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notas` (
  `id_notas` int(11) NOT NULL,
  `notas` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_notas`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notas`
--

LOCK TABLES `notas` WRITE;
/*!40000 ALTER TABLE `notas` DISABLE KEYS */;
INSERT INTO `notas` VALUES (1,0),(2,1),(3,2),(4,3),(5,4),(6,5),(7,6),(8,7),(9,8),(10,9),(11,10);
/*!40000 ALTER TABLE `notas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id_usuarios` int(11) NOT NULL,
  `email_usuarios` varchar(45) DEFAULT NULL,
  `psw_usuarios` varchar(45) DEFAULT NULL,
  `tipo_usuario` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_usuarios`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'admin@admin.com','admin123',1),(2,'rrhh@admin.com','rrhh123',2),(3,'rrhh@admin.com','rrhh456',2);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-27  0:36:52
