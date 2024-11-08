-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 08-11-2024 a las 14:19:09
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `tarea2dwes`
--
CREATE DATABASE IF NOT EXISTS `tarea2dwes` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `tarea2dwes`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `credenciales`
--

CREATE TABLE `credenciales` (
  `id` int(11) NOT NULL,
  `usuario` varchar(15) NOT NULL,
  `password` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- RELACIONES PARA LA TABLA `credenciales`:
--   `id`
--       `personas` -> `id`
--

--
-- Volcado de datos para la tabla `credenciales`
--

INSERT INTO `credenciales` (`id`, `usuario`, `password`) VALUES
(1, 'admin', 'admin'),
(2, 'sara1988', 'sara1988'),
(3, 'mamerto', 'mamerto'),
(4, 'gertrudis', 'lagertru');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ejemplares`
--

CREATE TABLE `ejemplares` (
  `id` int(11) NOT NULL,
  `nombre` varchar(25) NOT NULL,
  `idPlanta` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- RELACIONES PARA LA TABLA `ejemplares`:
--   `idPlanta`
--       `plantas` -> `codigo`
--

--
-- Volcado de datos para la tabla `ejemplares`
--

INSERT INTO `ejemplares` (`id`, `nombre`, `idPlanta`) VALUES
(1, 'BAMBU_1', 'BAMBU'),
(2, 'GIRASOL_2', 'GIRASOL'),
(3, 'LAVANDA_3', 'LAVANDA'),
(4, 'LIRIO_4', 'LIRIO'),
(5, 'ROSA_5', 'ROSA'),
(6, 'GIRASOL_6', 'GIRASOL'),
(7, 'GIRASOL_7', 'GIRASOL'),
(8, 'GIRASOL_8', 'GIRASOL'),
(9, 'HIBISCO_9', 'HIBISCO'),
(10, 'HIBISCO_10', 'HIBISCO'),
(11, 'LIRIO_11', 'LIRIO'),
(12, 'ROSA_12', 'ROSA'),
(13, 'LAVANDA_13', 'LAVANDA'),
(14, 'ORQUIDEA_14', 'ORQUIDEA'),
(15, 'TULIPAN_15', 'TULIPAN'),
(16, 'TULIPAN_16', 'TULIPAN'),
(17, 'ROSA_17', 'ROSA'),
(18, 'GIRASOL_18', 'GIRASOL'),
(19, 'HIBISCO_19', 'HIBISCO'),
(20, 'BAMBU_20', 'BAMBU'),
(21, 'BAMBU_21', 'BAMBU'),
(22, 'GIRASOL_22', 'GIRASOL'),
(23, 'HIBISCO_23', 'HIBISCO'),
(24, 'LAVANDA_24', 'LAVANDA'),
(25, 'LIRIO_25', 'LIRIO'),
(26, 'ORQUIDEA_26', 'ORQUIDEA'),
(27, 'ROSA_27', 'ROSA'),
(28, 'TULIPAN_28', 'TULIPAN'),
(29, 'TULIPAN_29', 'TULIPAN'),
(30, 'ROSA_30', 'ROSA');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mensajes`
--

CREATE TABLE `mensajes` (
  `id` int(11) NOT NULL,
  `fechaHora` datetime NOT NULL,
  `mensaje` varchar(500) NOT NULL,
  `idPersona` int(11) NOT NULL,
  `idEjemplar` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- RELACIONES PARA LA TABLA `mensajes`:
--   `idEjemplar`
--       `ejemplares` -> `id`
--   `idPersona`
--       `personas` -> `id`
--

--
-- Volcado de datos para la tabla `mensajes`
--

INSERT INTO `mensajes` (`id`, `fechaHora`, `mensaje`, `idPersona`, `idEjemplar`) VALUES
(1, '2024-11-08 14:04:36', 'Ejemplar registrado por: admin a las 08/11/2024 14:04:36', 1, 1),
(2, '2024-11-08 14:04:43', 'Ejemplar registrado por: admin a las 08/11/2024 14:04:43', 1, 2),
(3, '2024-11-08 14:05:27', 'Ejemplar registrado por: sara1988 a las 08/11/2024 14:05:27', 2, 3),
(4, '2024-11-08 14:05:39', 'Ejemplar registrado por: sara1988 a las 08/11/2024 14:05:39', 2, 4),
(5, '2024-11-08 14:05:56', 'Ejemplar registrado por: sara1988 a las 08/11/2024 14:05:56', 2, 5),
(6, '2024-11-08 14:07:11', 'Se escapó un oso panda del vecino y le pegó un bocao', 2, 1),
(7, '2024-11-08 14:07:46', 'Hoy está más amarillo que ayer', 2, 2),
(8, '2024-11-08 14:09:54', 'Ejemplar registrado por: mamerto a las 08/11/2024 14:09:54', 3, 6),
(9, '2024-11-08 14:09:58', 'Ejemplar registrado por: mamerto a las 08/11/2024 14:09:58', 3, 7),
(10, '2024-11-08 14:10:00', 'Ejemplar registrado por: mamerto a las 08/11/2024 14:10:00', 3, 8),
(11, '2024-11-08 14:10:04', 'Ejemplar registrado por: mamerto a las 08/11/2024 14:10:04', 3, 9),
(12, '2024-11-08 14:10:07', 'Ejemplar registrado por: mamerto a las 08/11/2024 14:10:07', 3, 10),
(13, '2024-11-08 14:10:12', 'Ejemplar registrado por: mamerto a las 08/11/2024 14:10:12', 3, 11),
(14, '2024-11-08 14:10:15', 'Ejemplar registrado por: mamerto a las 08/11/2024 14:10:15', 3, 12),
(15, '2024-11-08 14:10:52', 'Ejemplar registrado por: gertrudis a las 08/11/2024 14:10:52', 4, 13),
(16, '2024-11-08 14:10:55', 'Ejemplar registrado por: gertrudis a las 08/11/2024 14:10:55', 4, 14),
(17, '2024-11-08 14:10:58', 'Ejemplar registrado por: gertrudis a las 08/11/2024 14:10:58', 4, 15),
(18, '2024-11-08 14:11:02', 'Ejemplar registrado por: gertrudis a las 08/11/2024 14:11:02', 4, 16),
(19, '2024-11-08 14:11:12', 'Ejemplar registrado por: gertrudis a las 08/11/2024 14:11:12', 4, 17),
(20, '2024-11-08 14:11:17', 'Ejemplar registrado por: gertrudis a las 08/11/2024 14:11:17', 4, 18),
(21, '2024-11-08 14:11:22', 'Ejemplar registrado por: gertrudis a las 08/11/2024 14:11:22', 4, 19),
(22, '2024-11-08 14:11:43', 'Ejemplar registrado por: sara1988 a las 08/11/2024 14:11:43', 2, 20),
(23, '2024-11-08 14:11:48', 'Ejemplar registrado por: sara1988 a las 08/11/2024 14:11:48', 2, 21),
(24, '2024-11-08 14:11:51', 'Ejemplar registrado por: sara1988 a las 08/11/2024 14:11:51', 2, 22),
(25, '2024-11-08 14:11:54', 'Ejemplar registrado por: sara1988 a las 08/11/2024 14:11:54', 2, 23),
(26, '2024-11-08 14:11:56', 'Ejemplar registrado por: sara1988 a las 08/11/2024 14:11:56', 2, 24),
(27, '2024-11-08 14:12:02', 'Ejemplar registrado por: sara1988 a las 08/11/2024 14:12:02', 2, 25),
(28, '2024-11-08 14:12:04', 'Ejemplar registrado por: sara1988 a las 08/11/2024 14:12:04', 2, 26),
(29, '2024-11-08 14:12:06', 'Ejemplar registrado por: sara1988 a las 08/11/2024 14:12:06', 2, 27),
(30, '2024-11-08 14:12:08', 'Ejemplar registrado por: sara1988 a las 08/11/2024 14:12:08', 2, 28),
(31, '2024-11-08 14:12:10', 'Ejemplar registrado por: sara1988 a las 08/11/2024 14:12:10', 2, 29),
(32, '2024-11-08 14:12:19', 'Ejemplar registrado por: sara1988 a las 08/11/2024 14:12:19', 2, 30),
(33, '2024-11-08 14:14:57', 'Esta rosa es menos rosa que la ROSA_30', 2, 5),
(34, '2024-11-08 14:15:13', 'Regado hoy', 2, 8),
(35, '2024-11-08 14:15:37', 'Estaba un poco mustio. Se le añadieron nutrientes', 2, 22),
(36, '2024-11-08 14:15:51', 'Un hibisco muy hibisco', 2, 23),
(37, '2024-11-08 14:16:13', 'Ejemplar con un aroma muy intenso', 2, 3),
(38, '2024-11-08 14:16:45', 'Muy lustroso', 2, 14);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `personas`
--

CREATE TABLE `personas` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- RELACIONES PARA LA TABLA `personas`:
--

--
-- Volcado de datos para la tabla `personas`
--

INSERT INTO `personas` (`id`, `nombre`, `email`) VALUES
(1, 'Admin', 'admin@vivero.es'),
(2, 'Sara', 'sara@vivero.com'),
(3, 'Mamerto', 'mamerto@gmail.com'),
(4, 'Gertrudis', 'lagertru@hotmail.com');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `plantas`
--

CREATE TABLE `plantas` (
  `codigo` varchar(50) NOT NULL,
  `nombreComun` varchar(100) NOT NULL,
  `nombreCientifico` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- RELACIONES PARA LA TABLA `plantas`:
--

--
-- Volcado de datos para la tabla `plantas`
--

INSERT INTO `plantas` (`codigo`, `nombreComun`, `nombreCientifico`) VALUES
('BAMBU', 'Bambú', 'Bambusoideae'),
('GIRASOL', 'Girasol', 'Helianthus annuus'),
('HIBISCO', 'Hibisco', 'Hibiscus rosa sinensis'),
('LAVANDA', 'Lavanda', 'Lavandula angustifolia'),
('LIRIO', 'Lirio', 'Lilium spp'),
('ORQUIDEA', 'Orquídea', 'Orchidaceae'),
('ROSA', 'Rosa', 'Rosa spp'),
('TULIPAN', 'Tulipán', 'Tulipa spp');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `credenciales`
--
ALTER TABLE `credenciales`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `usuario` (`usuario`);

--
-- Indices de la tabla `ejemplares`
--
ALTER TABLE `ejemplares`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_planta` (`idPlanta`);

--
-- Indices de la tabla `mensajes`
--
ALTER TABLE `mensajes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_personaMensaje` (`idPersona`),
  ADD KEY `fk_ejemplarMensaje` (`idEjemplar`);

--
-- Indices de la tabla `personas`
--
ALTER TABLE `personas`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indices de la tabla `plantas`
--
ALTER TABLE `plantas`
  ADD PRIMARY KEY (`codigo`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `ejemplares`
--
ALTER TABLE `ejemplares`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT de la tabla `mensajes`
--
ALTER TABLE `mensajes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- AUTO_INCREMENT de la tabla `personas`
--
ALTER TABLE `personas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `credenciales`
--
ALTER TABLE `credenciales`
  ADD CONSTRAINT `fk_idPersona` FOREIGN KEY (`id`) REFERENCES `personas` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `ejemplares`
--
ALTER TABLE `ejemplares`
  ADD CONSTRAINT `fk_planta` FOREIGN KEY (`idPlanta`) REFERENCES `plantas` (`codigo`);

--
-- Filtros para la tabla `mensajes`
--
ALTER TABLE `mensajes`
  ADD CONSTRAINT `fk_ejemplarMensaje` FOREIGN KEY (`idEjemplar`) REFERENCES `ejemplares` (`id`),
  ADD CONSTRAINT `fk_personaMensaje` FOREIGN KEY (`idPersona`) REFERENCES `personas` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
