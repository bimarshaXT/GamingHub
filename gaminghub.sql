-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 18, 2025 at 09:24 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `gaminghub`
--

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `category_id` int(11) NOT NULL,
  `category_name` varchar(100) NOT NULL,
  `category_description` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`category_id`, `category_name`, `category_description`) VALUES
(1, 'Monitor', 'High-resolution and high-refresh-rate gaming monitors'),
(2, 'Gaming Processor', 'High-performance CPUs and processors for gaming rigs'),
(3, 'Keyboard', 'Mechanical and RGB gaming keyboards'),
(4, 'Mouse', 'Gaming mice with high DPI and programmable buttons'),
(5, 'Chair', 'Ergonomic gaming chairs with lumbar support'),
(6, 'Desk', 'Spacious and durable desks designed for gamers');

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `customer_id` int(11) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `dob` date NOT NULL,
  `gender` varchar(10) NOT NULL,
  `email` varchar(100) NOT NULL,
  `number` varchar(15) NOT NULL,
  `password` varchar(100) NOT NULL,
  `imageURL` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`customer_id`, `first_name`, `last_name`, `username`, `dob`, `gender`, `email`, `number`, `password`, `imageURL`) VALUES
(1, 'Bimarsha', 'Raut', 'Bimey', '2000-05-01', 'Male', 'rautbimarsha47@gmail.com', '9876543211', 'Rp+973SnfCNJHLr4idRYc7Mr48aleoCCoitXGLpaUYESH/8BzqH7y/fdjXHH12PQnyGxJ9YEHQ==', 'image.png'),
(9, 'Raja', 'Maharaja', 'Maharaj', '2001-09-11', 'male', 'maharaj@mail.com', '9841231230', '5MmqPYbP5O/bk3K71bR6+eo3xBmVOuf4g1cTir6Pj9SbQL+S0Bd0biwAMNdWCSHvRnKhxhRo86Xl', '1680781668.sidetrackimagedC8V6-untitled-3.jpg'),
(10, 'Bimarsha', 'Raut', 'Bimarsha12', '2000-01-01', 'Male', 'bimarsha@gmail.com', '9812347650', 'b3ia0x8AatskivgPsPU8pPmicj6tOnQ4xbsGG3LHmQqUYXLY7AbOldY5rxWeKMMZAuoVj6GYfA==', 'spongebob.webp'),
(11, 'Ram', 'Thapa', 'Ram', '2000-01-01', 'male', '123@gmail.com', '9800000000', 'X/ZoMa+//5PoeHMJ0HjEtwKygN3syhw1YC+USpwKZ4vHXOB6Lvh/ODD2TAJ6h1NMhvKuXXe+oQ==', NULL),
(13, 'Bimarsha', 'Raut', 'Bimuu', '2000-01-01', 'male', 'bimarsha1@gmail.com', '9876543210', '158ZfVz/u80ag8AbfquJDfUlAMZF1mMyqa/KvwzCzgt5cTjtZ8qHPUkDrgSFDB/CXW0Dz4NLSQ==', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `customer_order`
--

CREATE TABLE `customer_order` (
  `order_id` int(11) NOT NULL,
  `order_date` date NOT NULL,
  `customer_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `total_price` decimal(10,2) NOT NULL,
  `order_status` varchar(20) DEFAULT 'Processing'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customer_order`
--

INSERT INTO `customer_order` (`order_id`, `order_date`, `customer_id`, `product_id`, `quantity`, `total_price`, `order_status`) VALUES
(1, '2025-05-09', 1, 1, 2, 998.00, 'Shipped'),
(2, '2025-05-10', 1, 2, 2, 1359.98, 'Cancelled'),
(3, '2025-05-10', 1, 14, 3, 359.98, 'Shipped'),
(4, '2025-05-12', 9, 9, 13, 6629.89, 'Shipped'),
(5, '2025-05-12', 9, 9, 11, 5609.91, 'Cancelled'),
(6, '2025-05-12', 10, 11, 3, 350.97, 'Processing'),
(7, '2025-05-13', 1, 2, 2, 1359.98, 'Shipped'),
(8, '2025-05-13', 1, 1, 2, 528.00, 'Shipped'),
(9, '2025-05-13', 1, 22, 1, 142.49, 'Cancelled'),
(10, '2025-05-15', 1, 1, 2, 528.00, 'Processing'),
(11, '2025-05-15', 1, 7, 2, 1319.98, 'Processing');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `product_id` int(11) NOT NULL,
  `product_name` varchar(100) DEFAULT NULL,
  `product_description` varchar(255) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `stock_quantity` int(11) DEFAULT NULL,
  `brand` varchar(50) DEFAULT NULL,
  `discount` float DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`product_id`, `product_name`, `product_description`, `price`, `stock_quantity`, `brand`, `discount`, `image_url`, `category_id`) VALUES
(1, 'UltraWide Gaming Monitor', '34-inch ultra-wide curved gaming monitor with 144Hz refresh', 23, 12, 'Samsung', 12, 'view-computer-monitor-display_23-2150757317.webp', 1),
(2, '4K Gaming Monitor', '27-inch 4K monitor with NVIDIA G-Sync support', 799.99, 26, 'LG', 15, 'monitor2.jpg', 1),
(3, 'Curved Gaming Monitor', '32-inch curved gaming monitor with 165Hz refresh rate', 399.99, 60, 'Acer', 5, 'monitor3.webp', 1),
(4, 'Portable Gaming Monitor', '15.6-inch portable gaming monitor with 120Hz refresh rate', 299.99, 40, 'ASUS', 20, 'monitor4.png', 1),
(5, 'OLED Gaming Monitor', 'OLED 27-inch gaming monitor with perfect black levels', 899.99, 5, 'Sony', 25, 'monitor5.jpg', 1),
(6, 'Intel i9 Processor', 'Intel Core i9-11900K 8-core gaming processor', 799.99, 50, 'Intel', 10, 'processor1.jpeg', 2),
(7, 'AMD Ryzen 9', 'AMD Ryzen 9 5900X 12-core processor for gaming', 749.99, 43, 'AMD', 12, 'processor2.jpg', 2),
(8, 'Intel i7 Processor', 'Intel Core i7-10700K 8-core processor for gaming', 499.99, 55, 'Intel', 8, 'processor3.jpg', 2),
(9, 'AMD Ryzen 7', 'AMD Ryzen 7 5800X 8-core gaming processor', 599.99, 16, 'AMD', 15, 'processor4.jpg', 2),
(10, 'Intel Xeon Processor', 'Intel Xeon W-2295 18-core processor for workstations', 1299.99, 20, 'Intel', 5, 'processor5.webp', 2),
(11, 'Mechanical Gaming Keyboard', 'RGB mechanical keyboard with Cherry MX switches', 129.99, 97, 'Razer', 10, 'keyboard1.jpg', 3),
(12, 'Wireless Gaming Keyboard', 'Wireless RGB gaming keyboard with customizable keys', 99.99, 75, 'Corsair', 5, 'keyboard2.jpg', 3),
(13, 'Compact Gaming Keyboard', 'Compact 60% mechanical keyboard with customizable RGB', 89.99, 50, 'Logitech', 15, 'keyboard3.jpg', 3),
(14, 'Ergonomic Gaming Keyboard', 'Ergonomic split keyboard with RGB lighting and wrist rest', 149.99, 27, 'SteelSeries', 20, 'keyboard4.jpg', 3),
(15, 'Budget Gaming Keyboard', 'Affordable mechanical gaming keyboard with RGB backlighting', 59.99, 120, 'Redragon', 8, 'keyboard5.jpg', 3),
(16, 'RGB Gaming Mouse', 'Ergonomic gaming mouse with customizable RGB lighting', 49.99, 150, 'Logitech', 10, 'mouse1.webp', 4),
(17, 'High DPI Gaming Mouse', 'Gaming mouse with 16,000 DPI and programmable buttons', 79.99, 100, 'Razer', 5, 'mouse2.jpg', 4),
(18, 'Wireless Gaming Mouse', 'Wireless gaming mouse with 20,000 DPI and low latency', 99.99, 80, 'Corsair', 12, 'mouse3.jpg', 4),
(19, 'Budget Gaming Mouse', 'Affordable gaming mouse with RGB and 10,000 DPI', 29.99, 200, 'Redragon', 15, 'mouse4.jpg', 4),
(20, 'Ultra-Light Gaming Mouse', 'Ultra-lightweight gaming mouse with 16,000 DPI for speed', 69.99, 90, 'Glorious', 8, 'mouse5.jpg', 4),
(21, 'Ergonomic Gaming Chair', 'Ergonomic gaming chair with lumbar support and adjustable armrests', 199.99, 30, 'SecretLab', 10, 'chair1.jpg', 5),
(22, 'Racing Style Gaming Chair', 'High-back racing style chair with adjustable features', 149.99, 49, 'DXRacer', 5, 'chair2.jpg', 5),
(23, 'Reclining Gaming Chair', 'Reclining gaming chair with footrest and adjustable back', 249.99, 20, 'GT Omega', 15, 'chair3.jpg', 5),
(24, 'Mesh Gaming Chair', 'Breathable mesh ergonomic gaming chair for long sessions', 179.99, 40, 'Herman Miller', 20, 'chair4.avif', 5),
(25, 'Budget Gaming Chair', 'Affordable ergonomic chair with adjustable armrests and lumbar support', 99.99, 60, 'Arozzi', 12, 'chair5.jpg', 5),
(26, 'Large Gaming Desk', 'Spacious gaming desk with cable management and LED lighting', 199.99, 40, 'Arozzi', 10, 'desk1.jpg', 6),
(27, 'Compact Gaming Desk', 'Compact desk with adjustable height for gaming and work', 149.99, 60, 'FlexiSpot', 8, 'desk2.png', 6),
(28, 'L-shaped Gaming Desk', 'L-shaped desk with ample space for multiple monitors and gaming gear', 299.99, 30, 'Atlantic', 5, 'desk3.webp', 6),
(33, 'RGB Keyboard', 'excellent keyboard for the price', 100, 10, 'Razer', 20, 'rgb.jpg', 3);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`category_id`);

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`customer_id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `username_2` (`username`),
  ADD UNIQUE KEY `number` (`number`);

--
-- Indexes for table `customer_order`
--
ALTER TABLE `customer_order`
  ADD PRIMARY KEY (`order_id`),
  ADD KEY `customer_id` (`customer_id`),
  ADD KEY `product_id` (`product_id`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`product_id`),
  ADD KEY `category_id` (`category_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `category_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `customers`
--
ALTER TABLE `customers`
  MODIFY `customer_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `customer_order`
--
ALTER TABLE `customer_order`
  MODIFY `order_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `product_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `customer_order`
--
ALTER TABLE `customer_order`
  ADD CONSTRAINT `customer_order_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`),
  ADD CONSTRAINT `customer_order_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`);

--
-- Constraints for table `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `product_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
