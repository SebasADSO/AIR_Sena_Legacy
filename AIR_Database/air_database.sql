-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 30-07-2024 a las 01:10:53
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
-- Base de datos: `air_database`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_condicion_usua`
--

CREATE TABLE `tb_condicion_usua` (
  `cod_usuario_fk` int(11) NOT NULL,
  `condicion_usuario` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_condicion_usua`
--

INSERT INTO `tb_condicion_usua` (`cod_usuario_fk`, `condicion_usuario`) VALUES
(96, 'Ninguna'),
(96, 'Ninguna'),
(96, 'Discapacidad intelectual'),
(96, 'Discapacidad auditiva'),
(96, 'Ninguna'),
(123, 'Discapacidad física'),
(123, 'Discapacidad intelectual'),
(123, 'Discapacidad auditiva'),
(123, 'Discapacidad visual'),
(123, 'Discapacidad psíquica');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_encargadoyinstructor`
--

CREATE TABLE `tb_encargadoyinstructor` (
  `cod_usuario_fk` int(11) NOT NULL,
  `espec_encargado` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `nivel_formacion` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `dia_laboral` varchar(60) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_encargadoyinstructor`
--

INSERT INTO `tb_encargadoyinstructor` (`cod_usuario_fk`, `espec_encargado`, `nivel_formacion`, `dia_laboral`) VALUES
(96, 'instructor', 'Doctorado', 'Lunes; Miercoles; Sabado; '),
(96, 'instructor', 'Doctorado', 'Lunes; Miercoles; Sabado; '),
(123, 'funcionario', 'Especialista', 'Lunes; Martes; Miercoles; '),
(123, 'funcionario', 'Especialista', 'Lunes; Martes; Miercoles; ');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_estado`
--

CREATE TABLE `tb_estado` (
  `id_reporte_fk` int(11) DEFAULT NULL,
  `estado` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_programayficha`
--

CREATE TABLE `tb_programayficha` (
  `cod_usuario_fk` int(11) NOT NULL,
  `cod_programa` int(11) NOT NULL,
  `numero_ficha` int(11) NOT NULL,
  `nombre_programa` varchar(30) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `jornada_programa` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `fecha_inicio` date NOT NULL,
  `fecha_final` date NOT NULL,
  `inicio_produc` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_reporte`
--

CREATE TABLE `tb_reporte` (
  `id_reporte` int(11) NOT NULL,
  `cod_usuario_fk` int(11) NOT NULL,
  `encabezado_reporte` varchar(30) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `descripcion_reporte` varchar(240) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `ubicacion` varchar(30) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `fecha_hora_reporte` timestamp NOT NULL DEFAULT current_timestamp(),
  `soporte_reporte` blob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_revision`
--

CREATE TABLE `tb_revision` (
  `id_reporte_fk` int(11) NOT NULL,
  `tipo_peligro` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `nivel_peligro` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `fecha_revision` timestamp NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_usuario`
--

CREATE TABLE `tb_usuario` (
  `cod_usuario` int(11) NOT NULL,
  `tipo_docu_usuario` enum('Cedula de Ciudadania','Tarjeta de Indetidad','Cedula de Extranjeria','PEP','Permiso por protección Temporal') CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `cedula_usuario` double NOT NULL,
  `nombre_usuario` varchar(15) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `apell_usuario` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `email_usuario` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `pass_user` varbinary(16) NOT NULL,
  `rol_user` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_usuario`
--

INSERT INTO `tb_usuario` (`cod_usuario`, `tipo_docu_usuario`, `cedula_usuario`, `nombre_usuario`, `apell_usuario`, `email_usuario`, `pass_user`, `rol_user`) VALUES
(84, 'Cedula de Ciudadania', 798797878, 'APRENDIZ', 'SENA', 'test@gmail.com', 0x53454e4131323334, ''),
(96, 'Cedula de Ciudadania', 123456789, 'Sena', 'Usuario', 'test@gmail.com', 0x53656e6131323334, 'instructor'),
(123, 'Cedula de Ciudadania', 4651132311, 'Alvaro', 'Uribe', 'firtsparaco@gmail.com', 0x50415241434f3737, '');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `tb_condicion_usua`
--
ALTER TABLE `tb_condicion_usua`
  ADD KEY `cod_usuario_fk` (`cod_usuario_fk`);

--
-- Indices de la tabla `tb_encargadoyinstructor`
--
ALTER TABLE `tb_encargadoyinstructor`
  ADD KEY `cod_usuario_fk` (`cod_usuario_fk`);

--
-- Indices de la tabla `tb_estado`
--
ALTER TABLE `tb_estado`
  ADD KEY `id_reporte_fk` (`id_reporte_fk`);

--
-- Indices de la tabla `tb_programayficha`
--
ALTER TABLE `tb_programayficha`
  ADD KEY `cod_usuario_fk` (`cod_usuario_fk`);

--
-- Indices de la tabla `tb_reporte`
--
ALTER TABLE `tb_reporte`
  ADD PRIMARY KEY (`id_reporte`),
  ADD KEY `cod_usuario_fk` (`cod_usuario_fk`);

--
-- Indices de la tabla `tb_revision`
--
ALTER TABLE `tb_revision`
  ADD KEY `id_reporte_fk` (`id_reporte_fk`);

--
-- Indices de la tabla `tb_usuario`
--
ALTER TABLE `tb_usuario`
  ADD PRIMARY KEY (`cod_usuario`),
  ADD UNIQUE KEY `cedula_usuario` (`cedula_usuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `tb_reporte`
--
ALTER TABLE `tb_reporte`
  MODIFY `id_reporte` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `tb_usuario`
--
ALTER TABLE `tb_usuario`
  MODIFY `cod_usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=124;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `tb_condicion_usua`
--
ALTER TABLE `tb_condicion_usua`
  ADD CONSTRAINT `tb_condicion_usua_ibfk_1` FOREIGN KEY (`cod_usuario_fk`) REFERENCES `tb_usuario` (`cod_usuario`);

--
-- Filtros para la tabla `tb_encargadoyinstructor`
--
ALTER TABLE `tb_encargadoyinstructor`
  ADD CONSTRAINT `tb_encargadoyinstructor_ibfk_1` FOREIGN KEY (`cod_usuario_fk`) REFERENCES `tb_usuario` (`cod_usuario`);

--
-- Filtros para la tabla `tb_estado`
--
ALTER TABLE `tb_estado`
  ADD CONSTRAINT `tb_estado_ibfk_1` FOREIGN KEY (`id_reporte_fk`) REFERENCES `tb_reporte` (`id_reporte`);

--
-- Filtros para la tabla `tb_programayficha`
--
ALTER TABLE `tb_programayficha`
  ADD CONSTRAINT `tb_programayficha_ibfk_1` FOREIGN KEY (`cod_usuario_fk`) REFERENCES `tb_usuario` (`cod_usuario`);

--
-- Filtros para la tabla `tb_reporte`
--
ALTER TABLE `tb_reporte`
  ADD CONSTRAINT `tb_reporte_ibfk_1` FOREIGN KEY (`cod_usuario_fk`) REFERENCES `tb_usuario` (`cod_usuario`);

--
-- Filtros para la tabla `tb_revision`
--
ALTER TABLE `tb_revision`
  ADD CONSTRAINT `tb_revision_ibfk_1` FOREIGN KEY (`id_reporte_fk`) REFERENCES `tb_reporte` (`id_reporte`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
