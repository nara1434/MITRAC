-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: May 08, 2024 at 02:39 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `mitrac`
--

-- --------------------------------------------------------

--
-- Table structure for table `appointment`
--

CREATE TABLE `appointment` (
  `patient_id` varchar(20) NOT NULL,
  `name` text NOT NULL,
  `date` date NOT NULL,
  `status` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `appointment`
--

INSERT INTO `appointment` (`patient_id`, `name`, `date`, `status`) VALUES
('1434', 'narasimha', '2023-11-25', 'Approved'),
('1434', 'amar', '2023-11-21', 'Approved'),
('1434', 'kar', '2023-11-27', 'Approved'),
('1434', 'ck', '2023-11-27', 'Approved'),
('143', 'kc', '2023-11-25', 'Approved'),
('111', 'mahesh', '2023-11-22', 'Approved'),
('134', 'narasimha', '0000-00-00', 'pending'),
('100001', 'suresh', '2024-01-03', 'Approved'),
('4rtwaesxd', 'erersfse', '2024-02-08', 'pending'),
('1434', 'narasimha', '2024-02-23', 'pending');

-- --------------------------------------------------------

--
-- Table structure for table `caretaker`
--

CREATE TABLE `caretaker` (
  `mail` varchar(50) NOT NULL,
  `password` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `caretaker`
--

INSERT INTO `caretaker` (`mail`, `password`) VALUES
('simha@gmail.com', '1234'),
('', ''),
('', '');

-- --------------------------------------------------------

--
-- Table structure for table `caretaker_profile`
--

CREATE TABLE `caretaker_profile` (
  `c_id` varchar(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `age` varchar(3) NOT NULL,
  `sex` varchar(10) NOT NULL,
  `mobile_number` varchar(12) NOT NULL,
  `qualification` varchar(100) NOT NULL,
  `address` varchar(200) NOT NULL,
  `marital_status` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `caretaker_profile`
--

INSERT INTO `caretaker_profile` (`c_id`, `name`, `age`, `sex`, `mobile_number`, `qualification`, `address`, `marital_status`) VALUES
('1434', 'mm1m', 'mmm', 'mmm', '143', 'mm', 'mmmmm', 'mmm');

-- --------------------------------------------------------

--
-- Table structure for table `doctor`
--

CREATE TABLE `doctor` (
  `mail` varchar(50) NOT NULL,
  `password` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `doctor`
--

INSERT INTO `doctor` (`mail`, `password`) VALUES
('nara@gmail.com', ''),
('nara@gmail.com', ''),
('', ''),
('nara@gmail.com', '1234');

-- --------------------------------------------------------

--
-- Table structure for table `doctor_profile`
--

CREATE TABLE `doctor_profile` (
  `d_id` varchar(10) NOT NULL,
  `name` varchar(20) NOT NULL,
  `designation` varchar(100) NOT NULL,
  `sex` varchar(10) NOT NULL,
  `mobile_number` varchar(11) NOT NULL,
  `specialization` varchar(50) NOT NULL,
  `address` varchar(250) NOT NULL,
  `marital_status` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `doctor_profile`
--

INSERT INTO `doctor_profile` (`d_id`, `name`, `designation`, `sex`, `mobile_number`, `specialization`, `address`, `marital_status`) VALUES
('', '', '', '', '', '', '', ''),
('0001', 'kdkfj', '', 'ml', '5454', 'hjjgg', 'jjkhkjh', 'jhkh'),
('0011', 'hi', 'hi', 'hi', 'hi', 'hi', 'hi', 'hi'),
('1', 'iiii', 'iiii', 'iiiii', 'iiiii', 'iiiiiiiiiiiii', 'iiiiiiiiii', 'iiiiiiiii'),
('11', 'hadjah', 'jshfjshfk', 'male', '545454', 'habdhabsd', 'kadapa', 'single'),
('1100', 'hi', 'hi', 'hi', 'hi', 'hi', 'hi', 'hi'),
('1434', 'sharni', 'mbbs', 'female', '9640303663', 'pschyotheraphy', 'chennai', 'single'),
('2', 'John Doe', 'Doctor', 'Male', '1234567890', 'Cardiology', '123 Main St', 'Single');

-- --------------------------------------------------------

--
-- Table structure for table `dummy`
--

CREATE TABLE `dummy` (
  `id` int(3) NOT NULL,
  `name` varchar(20) NOT NULL,
  `age` int(11) NOT NULL,
  `sex` varchar(10) NOT NULL,
  `mobile_number` varchar(12) NOT NULL,
  `education` varchar(200) NOT NULL,
  `address` varchar(250) NOT NULL,
  `marital_status` varchar(10) NOT NULL,
  `disease_status` varchar(50) NOT NULL,
  `duration` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `dummy`
--

INSERT INTO `dummy` (`id`, `name`, `age`, `sex`, `mobile_number`, `education`, `address`, `marital_status`, `disease_status`, `duration`) VALUES
(1, '', 0, '', '', '', '', '', '', ''),
(2, 'test', 0, '', '', '', '', '', '', ''),
(3, 'Data not inserted', 0, '', '', '', '', '', '', ''),
(4, '$name', 0, '$sex', '$mobile_numb', '$education', '$address', '$marital_s', '$disease_status', '$duration'),
(5, 'Data not inserted', 0, '', '', '', '', '', '', ''),
(6, 'Data not inserted', 0, '', '', '', '', '', '', ''),
(7, 'Data not inserted', 12, 'failure', '6558555', 'failure', 'failure', 'failure', 'failure', 'failure');

-- --------------------------------------------------------

--
-- Table structure for table `medicine_monitoring`
--

CREATE TABLE `medicine_monitoring` (
  `id` varchar(30) NOT NULL,
  `true` varchar(10) NOT NULL,
  `false` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `medicine_monitoring`
--

INSERT INTO `medicine_monitoring` (`id`, `true`, `false`) VALUES
('', '1', ''),
('', '1', '0'),
('', '0', '1'),
('', '0', '1'),
('', '1', '0'),
('', '1', '0'),
('', '0', '1'),
('', '1', '0'),
('', '1', '0');

-- --------------------------------------------------------

--
-- Table structure for table `medicine_timings`
--

CREATE TABLE `medicine_timings` (
  `id` varchar(30) NOT NULL,
  `Medicine_name` varchar(100) NOT NULL,
  `Dose` varchar(50) NOT NULL,
  `Type` varchar(25) NOT NULL,
  `Date` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `medicine_timings`
--

INSERT INTO `medicine_timings` (`id`, `Medicine_name`, `Dose`, `Type`, `Date`) VALUES
('1434', 'ggh', 'ghgjhjh', 'After Food', '2024-03-19'),
('1434', 'ytftf', 'rdfdgffh', 'After Food', '2024-03-19'),
('1434', 'qwerty', 'wertgh', 'Before Food', '2024-03-19');

-- --------------------------------------------------------

--
-- Table structure for table `notification`
--

CREATE TABLE `notification` (
  `id` int(11) NOT NULL,
  `message` text NOT NULL,
  `date` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `notification`
--

INSERT INTO `notification` (`id`, `message`, `date`) VALUES
(6, 'oops! we lost the streak', '2024-02-24'),
(7, 'Hello doctor , patient  id: 1434 missed streak on 2024-02-28', '2024-02-28'),
(8, 'Hello doctor , patient  id: 1000 missed streak on 2024-02-28', '2024-02-28'),
(9, 'Hello doctor , patient  id: 1434 missed streak on 2024-03-04', '2024-03-04'),
(10, 'Hello doctor , patient  id: 1434 missed streak on 2024-03-04', '2024-03-04'),
(11, 'Hello doctor , patient  id: 1434 missed streak on 2024-03-04', '2024-03-04'),
(12, 'Hello doctor , patient  id: 1434 missed streak on 2024-03-07', '2024-03-07'),
(13, 'Hello doctor , patient  id: 1000 missed streak on 2024-03-09', '2024-03-09'),
(14, 'Hello doctor , patient  id: 1434 missed streak on 2024-03-22', '2024-03-22'),
(15, 'Hello doctor , patient  id: 1434 missed streak on 2024-03-22', '2024-03-22');

-- --------------------------------------------------------

--
-- Table structure for table `patient_details`
--

CREATE TABLE `patient_details` (
  `patient_id` varchar(20) NOT NULL,
  `name` varchar(25) NOT NULL,
  `age` int(11) NOT NULL,
  `sex` varchar(11) NOT NULL,
  `education` varchar(100) NOT NULL,
  `mobile_number` varchar(12) NOT NULL,
  `address` varchar(250) NOT NULL,
  `marital_status` varchar(10) NOT NULL,
  `disease_status` varchar(100) NOT NULL,
  `duration` varchar(20) NOT NULL,
  `img` text NOT NULL,
  `insertion_timestamp` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `patient_details`
--

INSERT INTO `patient_details` (`patient_id`, `name`, `age`, `sex`, `education`, `mobile_number`, `address`, `marital_status`, `disease_status`, `duration`, `img`, `insertion_timestamp`) VALUES
('001', 'king', 20, 'm', 'btech', '6665656', 'kadapa', 'single', 'fewer', '2days', '', '2024-01-27 07:59:30'),
('1000', 'narasimha', 20, 'male', 'btech', '6565656', 'kadapa', 'single', 'fewer', '1month', '', '2024-01-27 07:59:30'),
('1434', 'narasi', 22, 'male', 'cse', '5252525', 'kadapa', 'single', 'fewer', '1 month', '', '2024-01-27 07:59:30'),
('111', 'hhjg', 55, 'hgjhg', 'hgj', 'hghjg', 'hgjh', 'hgjh', 'hgjh', 'hghj', '', '2024-01-27 07:59:30'),
('143', 'kishore', 52, 'kk', 'kk', 'kkk', 'kk', 'kk', 'kk', 'kk', '', '2024-01-27 07:59:30'),
('341', 'kk', 55, 'kkk', 'hhhh', 'hhhh', 'hhhh', 'hhhh', 'hhhh', 'hhh', '', '2024-01-27 07:59:30'),
('444', 'hjh', 55, 'gygyu', 'ygyt', 'ytytyut', 'ytyutuy', 'ytuyty', 'ytyutyu', 'ytyut', '', '2024-01-27 07:59:30'),
('481', 'kishore', 22, 'male', 'btech', '55555555', 'kadapa', 'married', 'dengue', '6 months', '', '2024-01-27 07:59:30'),
('1112', 'nandhu', 21, 'female', 'ca', '57258445', 'kadapa', 'single', 'fewer', '1week', 'img/1112.jpg', '2024-01-27 07:59:30'),
('66667', 'n', 8, 'm', 'm', '9640303663', 'm', 'm', 'm', 'm', 'img/66667.jpg', '2024-01-29 04:43:33');

-- --------------------------------------------------------

--
-- Table structure for table `questionaries`
--

CREATE TABLE `questionaries` (
  `Q.NO` int(11) NOT NULL,
  `true` tinyint(1) NOT NULL,
  `false` tinyint(1) NOT NULL,
  `timestamp_column` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `questionaries`
--

INSERT INTO `questionaries` (`Q.NO`, `true`, `false`, `timestamp_column`) VALUES
(62, 1, 0, '2023-11-22 08:04:30');

-- --------------------------------------------------------

--
-- Table structure for table `questions`
--

CREATE TABLE `questions` (
  `patient_id` varchar(30) NOT NULL,
  `selected_column` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `questions`
--

INSERT INTO `questions` (`patient_id`, `selected_column`) VALUES
('1434', 'true');

-- --------------------------------------------------------

--
-- Table structure for table `reasons`
--

CREATE TABLE `reasons` (
  `patient_id` varchar(50) NOT NULL,
  `reasons` varchar(1000) NOT NULL,
  `time1` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `reasons`
--

INSERT INTO `reasons` (`patient_id`, `reasons`, `time1`) VALUES
('1434', 'hello abc hai', '2023-12-14 03:49:20'),
('1434', 'due to heavy dose', '2023-12-14 04:18:13'),
('111', 'it is irritating while taking the medicines daily', '2024-01-25 05:59:56'),
('1000', 'hello dude', '2024-01-25 06:26:23'),
('1434', '', '2024-03-04 08:26:04'),
('1000', 'hai', '2024-03-07 13:43:11'),
('1000', 'hia', '2024-03-07 13:50:40'),
('1000', '', '2024-03-07 13:50:46'),
('1000', 'hai', '2024-03-07 13:53:14'),
('1000', '', '2024-03-07 13:53:16'),
('1000', 'hello', '2024-03-07 13:57:04'),
('1000', 'feeling bad to take medicine', '2024-03-07 14:00:12'),
('1000', 'ok', '2024-03-07 14:00:23'),
('1000', 'fine', '2024-03-07 14:03:55'),
('1000', 'not goog', '2024-03-07 14:18:32'),
('1434', 'good', '2024-03-09 05:42:25'),
('1434', 'feeling bad', '2024-03-09 05:43:26'),
('1434', 'good', '2024-03-09 05:50:55'),
('1000', 'everthing is ok', '2024-03-09 06:24:02'),
('1000', '', '2024-03-09 06:24:51'),
('1434', 'jhjhhkk', '2024-03-22 03:04:05');

-- --------------------------------------------------------

--
-- Table structure for table `relation`
--

CREATE TABLE `relation` (
  `patient_id` varchar(30) NOT NULL,
  `relation` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `relation`
--

INSERT INTO `relation` (`patient_id`, `relation`) VALUES
('444', 'mother'),
('1234567', 'mother'),
('1', 'amma'),
('1', 'faf'),
('1', 'aunty'),
('1434', 'amma'),
('001', 'nanna'),
('1000', 'Guardian'),
('111', 'Aunt');

-- --------------------------------------------------------

--
-- Table structure for table `sample_questions`
--

CREATE TABLE `sample_questions` (
  `patient_id` varchar(100) NOT NULL,
  `totalMarks` varchar(100) NOT NULL,
  `date` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `sample_questions`
--

INSERT INTO `sample_questions` (`patient_id`, `totalMarks`, `date`) VALUES
('false', 'hi', '2023-12-13'),
('false', '1', '2023-12-13'),
('123', 'null', '2023-12-18');

-- --------------------------------------------------------

--
-- Table structure for table `screening`
--

CREATE TABLE `screening` (
  `patient_id` int(11) NOT NULL,
  `q1` varchar(10) NOT NULL,
  `q2` varchar(10) NOT NULL,
  `q3` varchar(10) NOT NULL,
  `q4` varchar(10) NOT NULL,
  `q5` varchar(10) NOT NULL,
  `q6` varchar(10) NOT NULL,
  `q7` varchar(10) NOT NULL,
  `q8` varchar(10) NOT NULL,
  `date` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `screening`
--

INSERT INTO `screening` (`patient_id`, `q1`, `q2`, `q3`, `q4`, `q5`, `q6`, `q7`, `q8`, `date`) VALUES
(1000, 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', ''),
(1434, 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', ''),
(1434, 'Yes', 'Yes', 'Yes', 'Yes', 'Yes', 'Yes', 'Yes', 'Yes', '2024-03-09'),
(1000, 'Yes', 'No', 'Yes', 'No', 'Yes', 'No', 'Yes', 'No', '2024-03-09'),
(1, 'no', 'no', 'no', 'no', 'no', 'no', 'no', 'no', '2024-03-09'),
(1434, 'no', 'no', 'no', 'no', 'no', 'no', 'no', 'no', '2024-03-13'),
(1434, 'no', 'no', 'no', 'no', 'no', 'no', 'no', 'no', '2024-03-21'),
(1000, 'no', 'no', 'no', 'no', 'no', 'no', 'no', 'no', '2024-03-21'),
(1434, 'Yes', 'No', 'No', 'No', 'Yes', 'Yes', 'No', 'Yes', '2024-03-22');

-- --------------------------------------------------------

--
-- Table structure for table `screening2`
--

CREATE TABLE `screening2` (
  `patient_id` int(11) NOT NULL,
  `q1` varchar(20) NOT NULL,
  `q2` varchar(20) NOT NULL,
  `q3` varchar(20) NOT NULL,
  `q4` varchar(20) NOT NULL,
  `q5` varchar(20) NOT NULL,
  `q6` varchar(20) NOT NULL,
  `q7` varchar(20) NOT NULL,
  `q8` varchar(20) NOT NULL,
  `q9` varchar(20) NOT NULL,
  `q10` varchar(20) NOT NULL,
  `date` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `screening2`
--

INSERT INTO `screening2` (`patient_id`, `q1`, `q2`, `q3`, `q4`, `q5`, `q6`, `q7`, `q8`, `q9`, `q10`, `date`) VALUES
(1434, 'Yes', 'no', 'no', 'no', 'no', 'no', 'no', 'Yes', 'no', 'no', '2024-03-13'),
(1434, 'Yes', 'Yes', 'Yes', 'Yes', 'Yes', 'Yes', 'Yes', 'Yes', '', 'Yes', '2024-03-21'),
(1000, 'Yes', 'Yes', 'Yes', 'Yes', 'Yes', 'Yes', 'Yes', 'Yes', 'Yes', 'Yes', '2024-03-21'),
(111, 'no', 'no', 'no', 'no', 'no', 'no', 'no', 'no', 'Yes', 'no', '2024-03-21'),
(1434, 'Yes', 'Yes', 'Yes', 'Yes', 'Yes', 'Yes', 'Yes', 'Yes', 'Yes', 'Yes', '2024-03-22');

-- --------------------------------------------------------

--
-- Table structure for table `streak`
--

CREATE TABLE `streak` (
  `id` varchar(20) NOT NULL,
  `total_count` varchar(20) NOT NULL,
  `date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `streak`
--

INSERT INTO `streak` (`id`, `total_count`, `date`) VALUES
('111', '0', '2024-02-19'),
('143', '0', '2024-02-19'),
('341', '0', '2024-02-19'),
('444', '0', '2024-02-19'),
('481', '0', '2024-02-19'),
('1112', '0', '2024-02-19'),
('66667', '0', '2024-02-19'),
('111', '0', '2024-02-20'),
('143', '0', '2024-02-20'),
('341', '0', '2024-02-20'),
('444', '0', '2024-02-20'),
('481', '0', '2024-02-20'),
('1112', '0', '2024-02-20'),
('66667', '0', '2024-02-20'),
('111', '0', '2024-02-24'),
('143', '0', '2024-02-24'),
('341', '0', '2024-02-24'),
('444', '0', '2024-02-24'),
('481', '0', '2024-02-24'),
('1112', '0', '2024-02-24'),
('66667', '0', '2024-02-24'),
('111', '0', '2024-02-25'),
('143', '0', '2024-02-25'),
('341', '0', '2024-02-25'),
('444', '0', '2024-02-25'),
('481', '0', '2024-02-25'),
('1112', '0', '2024-02-25'),
('66667', '0', '2024-02-25'),
('111', '0', '2024-02-26'),
('143', '0', '2024-02-26'),
('341', '0', '2024-02-26'),
('444', '0', '2024-02-26'),
('481', '0', '2024-02-26'),
('1112', '0', '2024-02-26'),
('66667', '0', '2024-02-26'),
('001', '0', '2024-02-27'),
('111', '0', '2024-02-27'),
('143', '0', '2024-02-27'),
('341', '0', '2024-02-27'),
('444', '0', '2024-02-27'),
('481', '0', '2024-02-27'),
('1112', '0', '2024-02-27'),
('66667', '0', '2024-02-27'),
('001', '0', '2024-02-28'),
('111', '0', '2024-02-28'),
('143', '0', '2024-02-28'),
('341', '0', '2024-02-28'),
('444', '0', '2024-02-28'),
('481', '0', '2024-02-28'),
('1112', '0', '2024-02-28'),
('66667', '0', '2024-02-28'),
('001', '0', '2024-02-29'),
('111', '0', '2024-02-29'),
('143', '0', '2024-02-29'),
('341', '0', '2024-02-29'),
('444', '0', '2024-02-29'),
('481', '0', '2024-02-29'),
('1112', '0', '2024-02-29'),
('66667', '0', '2024-02-29'),
('001', '0', '2024-03-01'),
('111', '0', '2024-03-01'),
('143', '0', '2024-03-01'),
('341', '0', '2024-03-01'),
('444', '0', '2024-03-01'),
('481', '0', '2024-03-01'),
('1112', '0', '2024-03-01'),
('66667', '0', '2024-03-01'),
('001', '0', '2024-03-02'),
('111', '0', '2024-03-02'),
('143', '0', '2024-03-02'),
('341', '0', '2024-03-02'),
('444', '0', '2024-03-02'),
('481', '0', '2024-03-02'),
('1112', '0', '2024-03-02'),
('66667', '0', '2024-03-02'),
('001', '0', '2024-03-03'),
('111', '0', '2024-03-03'),
('143', '0', '2024-03-03'),
('341', '0', '2024-03-03'),
('444', '0', '2024-03-03'),
('481', '0', '2024-03-03'),
('1112', '0', '2024-03-03'),
('66667', '0', '2024-03-03'),
('001', '0', '2024-03-04'),
('111', '0', '2024-03-04'),
('143', '0', '2024-03-04'),
('341', '0', '2024-03-04'),
('444', '0', '2024-03-04'),
('481', '0', '2024-03-04'),
('1112', '0', '2024-03-04'),
('66667', '0', '2024-03-04'),
('001', '0', '2024-03-05'),
('111', '0', '2024-03-05'),
('143', '0', '2024-03-05'),
('341', '0', '2024-03-05'),
('444', '0', '2024-03-05'),
('481', '0', '2024-03-05'),
('1112', '0', '2024-03-05'),
('66667', '0', '2024-03-05'),
('001', '0', '2024-03-06'),
('111', '0', '2024-03-06'),
('143', '0', '2024-03-06'),
('341', '0', '2024-03-06'),
('444', '0', '2024-03-06'),
('481', '0', '2024-03-06'),
('1112', '0', '2024-03-06'),
('66667', '0', '2024-03-06'),
('001', '0', '2024-03-07'),
('111', '0', '2024-03-07'),
('143', '0', '2024-03-07'),
('341', '0', '2024-03-07'),
('444', '0', '2024-03-07'),
('481', '0', '2024-03-07'),
('1112', '0', '2024-03-07'),
('66667', '0', '2024-03-07'),
('001', '0', '2024-03-08'),
('111', '0', '2024-03-08'),
('143', '0', '2024-03-08'),
('341', '0', '2024-03-08'),
('444', '0', '2024-03-08'),
('481', '0', '2024-03-08'),
('1112', '0', '2024-03-08'),
('66667', '0', '2024-03-08'),
('001', '0', '2024-03-09'),
('111', '0', '2024-03-09'),
('143', '0', '2024-03-09'),
('341', '0', '2024-03-09'),
('444', '0', '2024-03-09'),
('481', '0', '2024-03-09'),
('1112', '0', '2024-03-09'),
('66667', '0', '2024-03-09'),
('001', '0', '2024-03-10'),
('1000', '0', '2024-03-10'),
('111', '0', '2024-03-10'),
('143', '0', '2024-03-10'),
('341', '0', '2024-03-10'),
('444', '0', '2024-03-10'),
('481', '0', '2024-03-10'),
('1112', '0', '2024-03-10'),
('66667', '0', '2024-03-10'),
('001', '0', '2024-03-11'),
('1000', '0', '2024-03-11'),
('111', '0', '2024-03-11'),
('143', '0', '2024-03-11'),
('341', '0', '2024-03-11'),
('444', '0', '2024-03-11'),
('481', '0', '2024-03-11'),
('1112', '0', '2024-03-11'),
('66667', '0', '2024-03-11'),
('001', '0', '2024-03-12'),
('1000', '0', '2024-03-12'),
('111', '0', '2024-03-12'),
('143', '0', '2024-03-12'),
('341', '0', '2024-03-12'),
('444', '0', '2024-03-12'),
('481', '0', '2024-03-12'),
('1112', '0', '2024-03-12'),
('66667', '0', '2024-03-12'),
('001', '0', '2024-03-13'),
('1000', '0', '2024-03-13'),
('111', '0', '2024-03-13'),
('143', '0', '2024-03-13'),
('341', '0', '2024-03-13'),
('444', '0', '2024-03-13'),
('481', '0', '2024-03-13'),
('1112', '0', '2024-03-13'),
('66667', '0', '2024-03-13'),
('001', '0', '2024-03-14'),
('1000', '0', '2024-03-14'),
('111', '0', '2024-03-14'),
('143', '0', '2024-03-14'),
('341', '0', '2024-03-14'),
('444', '0', '2024-03-14'),
('481', '0', '2024-03-14'),
('1112', '0', '2024-03-14'),
('66667', '0', '2024-03-14'),
('001', '0', '2024-03-15'),
('1000', '0', '2024-03-15'),
('111', '0', '2024-03-15'),
('143', '0', '2024-03-15'),
('341', '0', '2024-03-15'),
('444', '0', '2024-03-15'),
('481', '0', '2024-03-15'),
('1112', '0', '2024-03-15'),
('66667', '0', '2024-03-15'),
('001', '0', '2024-03-16'),
('1000', '0', '2024-03-16'),
('111', '0', '2024-03-16'),
('143', '0', '2024-03-16'),
('341', '0', '2024-03-16'),
('444', '0', '2024-03-16'),
('481', '0', '2024-03-16'),
('1112', '0', '2024-03-16'),
('66667', '0', '2024-03-16'),
('001', '0', '2024-03-17'),
('1000', '0', '2024-03-17'),
('111', '0', '2024-03-17'),
('143', '0', '2024-03-17'),
('341', '0', '2024-03-17'),
('444', '0', '2024-03-17'),
('481', '0', '2024-03-17'),
('1112', '0', '2024-03-17'),
('66667', '0', '2024-03-17'),
('001', '0', '2024-03-18'),
('1000', '0', '2024-03-18'),
('111', '0', '2024-03-18'),
('143', '0', '2024-03-18'),
('341', '0', '2024-03-18'),
('444', '0', '2024-03-18'),
('481', '0', '2024-03-18'),
('1112', '0', '2024-03-18'),
('66667', '0', '2024-03-18'),
('001', '0', '2024-03-19'),
('1000', '0', '2024-03-19'),
('111', '0', '2024-03-19'),
('143', '0', '2024-03-19'),
('341', '0', '2024-03-19'),
('444', '0', '2024-03-19'),
('481', '0', '2024-03-19'),
('1112', '0', '2024-03-19'),
('66667', '0', '2024-03-19'),
('001', '0', '2024-03-20'),
('1000', '0', '2024-03-20'),
('111', '0', '2024-03-20'),
('143', '0', '2024-03-20'),
('341', '0', '2024-03-20'),
('444', '0', '2024-03-20'),
('481', '0', '2024-03-20'),
('1112', '0', '2024-03-20'),
('66667', '0', '2024-03-20'),
('001', '0', '2024-03-21'),
('1000', '0', '2024-03-21'),
('111', '0', '2024-03-21'),
('143', '0', '2024-03-21'),
('341', '0', '2024-03-21'),
('444', '0', '2024-03-21'),
('481', '0', '2024-03-21'),
('1112', '0', '2024-03-21'),
('66667', '0', '2024-03-21'),
('001', '0', '2024-03-22'),
('1000', '0', '2024-03-22'),
('111', '0', '2024-03-22'),
('143', '0', '2024-03-22'),
('341', '0', '2024-03-22'),
('444', '0', '2024-03-22'),
('481', '0', '2024-03-22'),
('1112', '0', '2024-03-22'),
('66667', '0', '2024-03-22');

-- --------------------------------------------------------

--
-- Table structure for table `videos`
--

CREATE TABLE `videos` (
  `uVideos` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `videos`
--

INSERT INTO `videos` (`uVideos`) VALUES
('videos/d3dcompiler_47.dll'),
('videos/Title 2023-10-31 11-27-51.mp4'),
('videos/Tom_And_Jerry_-_Jerrys_Cousin_Part_2-590332'),
('videos/Tom_And_Jerry_-_Jerrys_Cousin_Part_2-590332'),
('videos/Tom_And_Jerry_-_Jerrys_Cousin_Part_2-590332'),
('videos/Tom_And_Jerry_-_Jerrys_Cousin_Part_2-590332'),
('videos/Tom_And_Jerry_-_Jerrys_Cousin_Part_2-590332'),
('videos/Tom_And_Jerry_-_Jerrys_Cousin_Part_2-590332'),
('videos/Tom_And_Jerry_-_Jerrys_Cousin_Part_2-590332');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `caretaker_profile`
--
ALTER TABLE `caretaker_profile`
  ADD PRIMARY KEY (`c_id`);

--
-- Indexes for table `doctor_profile`
--
ALTER TABLE `doctor_profile`
  ADD PRIMARY KEY (`d_id`);

--
-- Indexes for table `dummy`
--
ALTER TABLE `dummy`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `notification`
--
ALTER TABLE `notification`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `questionaries`
--
ALTER TABLE `questionaries`
  ADD PRIMARY KEY (`Q.NO`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `dummy`
--
ALTER TABLE `dummy`
  MODIFY `id` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `notification`
--
ALTER TABLE `notification`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `questionaries`
--
ALTER TABLE `questionaries`
  MODIFY `Q.NO` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=63;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
