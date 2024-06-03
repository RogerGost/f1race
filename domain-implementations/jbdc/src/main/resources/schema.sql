-- PHPMYADMIN SQL DUMP
-- VERSION 5.0.1
-- HTTPS://WWW.PHPMYADMIN.NET/
--
-- HOST: 127.0.0.1
-- GENERATION TIME: MAR 24, 2020 AT 06:55 PM
-- SERVER VERSION: 10.4.11-MARIADB
-- PHP VERSION: 7.4.3
DROP DATABASE IF EXISTS FORMULA1;

CREATE DATABASE FORMULA1;

USE FORMULA1;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET TIME_ZONE = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES UTF8MB4 */;

--

--

-- --------------------------------------------------------



CREATE TABLE TEAM (
                      TEAM_ID INT AUTO_INCREMENT PRIMARY KEY,
                      TEAM_NAME VARCHAR(50),
                      PRINCIPAL_NAME VARCHAR(50),
                      HEADQUARTERS VARCHAR(50),
                      SPONSOR_NAME VARCHAR(50)

);

CREATE TABLE DRIVER (
                        DRIVER_ID INT AUTO_INCREMENT PRIMARY KEY,
                        NAME VARCHAR(50),
                        NATIONALITY VARCHAR(50),
                        DATE_OF_BIRTH INT,
                        NUMBER INT,
                        TEAM_ID INT,
                        FOREIGN KEY (TEAM_ID) REFERENCES TEAM(TEAM_ID)
);

CREATE TABLE RACE_RESULT (
                             RESULT_ID INT AUTO_INCREMENT PRIMARY KEY,
                             RACE_ID INT,
                             DRIVER_ID INT,
                             POSITION INT,
                             FASTEST_LAP_TIME VARCHAR(50),
                             POINTS_EARNED INT,
                             FOREIGN KEY (DRIVER_ID) REFERENCES DRIVER(DRIVER_ID)
);

CREATE TABLE CAR (
                     CAR_ID INT AUTO_INCREMENT PRIMARY KEY ,
                     MODEL VARCHAR(50),
                     ENGINE VARCHAR(50),
                     CHASSIS VARCHAR(100),
                     TEAM_ID INT,
                     FOREIGN KEY (TEAM_ID) REFERENCES TEAM(TEAM_ID)
);


CREATE TABLE SPONSOR (
                         SPONSOR_ID INT AUTO_INCREMENT PRIMARY KEY,
                         SPONSOR_NAME VARCHAR(100) ,
                         COUNTRY VARCHAR(50),
                         NUMBER INT,
                         SPONSOR_TYPE VARCHAR(100)
);

CREATE TABLE TEAM_SPONSOR(
                             TEAM_ID INT NOT NULL,
                             SPONSOR_ID INT NOT NULL,
                             PRIMARY KEY (TEAM_ID,SPONSOR_ID),
                             FOREIGN KEY (TEAM_ID) REFERENCES TEAM(TEAM_ID) ON DELETE CASCADE,
                             FOREIGN KEY (SPONSOR_ID) REFERENCES SPONSOR(SPONSOR_ID) ON DELETE CASCADE
);



/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
