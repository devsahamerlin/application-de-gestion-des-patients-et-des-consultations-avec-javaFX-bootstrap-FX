-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Feb 24, 2025 at 12:21 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `DB_CABINET`
--

-- --------------------------------------------------------

--
-- Table structure for table `CONSULTATIONS`
--

CREATE TABLE `CONSULTATIONS` (
  `ID_CONSULTATION` int(11) NOT NULL,
  `DATE_CONSULTATION` date NOT NULL,
  `DESCRIPTION` text NOT NULL,
  `ID_PATIENT` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `PATIENTS`
--

CREATE TABLE `PATIENTS` (
  `ID_PATIENT` int(11) NOT NULL,
  `NOM` varchar(50) NOT NULL,
  `PRENOM` varchar(50) NOT NULL,
  `DATE_NAISSANCE` date NOT NULL,
  `ADRESSE` varchar(100) NOT NULL,
  `TELEPHONE` varchar(30) NOT NULL,
  `EMAIL` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `CONSULTATIONS`
--
ALTER TABLE `CONSULTATIONS`
  ADD PRIMARY KEY (`ID_CONSULTATION`);

--
-- Indexes for table `PATIENTS`
--
ALTER TABLE `PATIENTS`
  ADD PRIMARY KEY (`ID_PATIENT`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `CONSULTATIONS`
--
ALTER TABLE `CONSULTATIONS`
  MODIFY `ID_CONSULTATION` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `PATIENTS`
--
ALTER TABLE `PATIENTS`
  MODIFY `ID_PATIENT` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
