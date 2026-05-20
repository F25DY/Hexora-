-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: hexora_db
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.32-MariaDB

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
-- Table structure for table `area_auditable`
--

DROP TABLE IF EXISTS `area_auditable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `area_auditable` (
  `id_area` bigint(20) NOT NULL AUTO_INCREMENT,
  `descripcion` text DEFAULT NULL,
  `nombre` varchar(150) NOT NULL,
  `id_auditoria` bigint(20) NOT NULL,
  PRIMARY KEY (`id_area`),
  KEY `FKoginel0c0ttldlsd5t4ag4yhx` (`id_auditoria`),
  CONSTRAINT `FKoginel0c0ttldlsd5t4ag4yhx` FOREIGN KEY (`id_auditoria`) REFERENCES `auditoria` (`id_auditoria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `area_auditable`
--

LOCK TABLES `area_auditable` WRITE;
/*!40000 ALTER TABLE `area_auditable` DISABLE KEYS */;
/*!40000 ALTER TABLE `area_auditable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auditoria`
--

DROP TABLE IF EXISTS `auditoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auditoria` (
  `id_auditoria` bigint(20) NOT NULL AUTO_INCREMENT,
  `estado` varchar(30) NOT NULL,
  `fecha_fin` date NOT NULL,
  `fecha_inico` date NOT NULL,
  `nombre` varchar(150) NOT NULL,
  `tipo_auditoria` varchar(80) NOT NULL,
  `empresa_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id_auditoria`),
  KEY `FKj0mypnhencx1n0gs4p036s6sg` (`empresa_id`),
  CONSTRAINT `FKj0mypnhencx1n0gs4p036s6sg` FOREIGN KEY (`empresa_id`) REFERENCES `empresa` (`id_empresa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auditoria`
--

LOCK TABLES `auditoria` WRITE;
/*!40000 ALTER TABLE `auditoria` DISABLE KEYS */;
/*!40000 ALTER TABLE `auditoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empresa`
--

DROP TABLE IF EXISTS `empresa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `empresa` (
  `id_empresa` bigint(20) NOT NULL AUTO_INCREMENT,
  `nit` varchar(50) DEFAULT NULL,
  `nombre` varchar(100) NOT NULL,
  `sector` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`id_empresa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empresa`
--

LOCK TABLES `empresa` WRITE;
/*!40000 ALTER TABLE `empresa` DISABLE KEYS */;
/*!40000 ALTER TABLE `empresa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vulnerabilidad`
--

DROP TABLE IF EXISTS `vulnerabilidad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vulnerabilidad` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `confidencialidad` int(11) NOT NULL,
  `cve` varchar(30) DEFAULT NULL,
  `disponibilidad` int(11) NOT NULL,
  `estado` varchar(30) NOT NULL,
  `fecha_descubrimiento` date NOT NULL,
  `herramienta` varchar(100) DEFAULT NULL,
  `host_afectado` varchar(150) DEFAULT NULL,
  `impacto` decimal(4,2) NOT NULL,
  `integridad` int(11) NOT NULL,
  `ip_afectada` varchar(45) DEFAULT NULL,
  `nivel_riesgo` varchar(20) NOT NULL,
  `nombre` varchar(150) NOT NULL,
  `plan_mitigacion` text DEFAULT NULL,
  `probabilidad` int(11) NOT NULL,
  `puerto_afectado` int(11) DEFAULT NULL,
  `servicio` varchar(100) DEFAULT NULL,
  `tipo` varchar(100) NOT NULL,
  `id_area` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKep6e81q3p0ewokyvvxgnosir9` (`id_area`),
  CONSTRAINT `FKep6e81q3p0ewokyvvxgnosir9` FOREIGN KEY (`id_area`) REFERENCES `area_auditable` (`id_area`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vulnerabilidad`
--

LOCK TABLES `vulnerabilidad` WRITE;
/*!40000 ALTER TABLE `vulnerabilidad` DISABLE KEYS */;
/*!40000 ALTER TABLE `vulnerabilidad` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-20 13:17:49
