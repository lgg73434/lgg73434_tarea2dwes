-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 03-11-2024 a las 22:22:05
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
  `usuario` varchar(50) NOT NULL,
  `password` varchar(20) NOT NULL
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
(1, 'admin', 'admin');

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
(1, 'Admin', 'admin@vivero.es');

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
('GIRASOL', 'Girasol', 'Girasolus'),
('ROSA', 'Rosa', 'Rosaeum');

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `mensajes`
--
ALTER TABLE `mensajes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `personas`
--
ALTER TABLE `personas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

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
