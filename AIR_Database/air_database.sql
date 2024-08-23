-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 23-08-2024 a las 23:05:26
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
  `condicion_usuario` varchar(65) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_condicion_usua`
--

INSERT INTO `tb_condicion_usua` (`cod_usuario_fk`, `condicion_usuario`) VALUES
(96, 'Ninguna'),
(96, 'Ninguna'),
(96, 'Ninguna'),
(96, 'Baja visión'),
(96, 'Ninguna'),
(645, 'Ninguna'),
(645, 'Ninguna'),
(645, 'Ninguna'),
(645, 'Ninguna'),
(645, 'Enfermedad de Parkinson'),
(623, 'Daltonismo (deuteranopía, protanopía, tritanopía)'),
(623, 'Baja visión'),
(623, 'Ambliopía (ojo vago)'),
(623, 'Ninguna'),
(623, 'Ninguna'),
(40, 'Ninguna'),
(40, 'Ninguna'),
(40, 'Ninguna'),
(40, 'Daltonismo (deuteranopía, protanopía, tritanopía)'),
(40, 'Ninguna'),
(190, 'Ninguna'),
(190, 'Ninguna'),
(190, 'Ninguna'),
(190, 'Baja visión'),
(190, 'Ninguna'),
(107, 'Retinopatía diabética'),
(107, 'Ninguna'),
(107, 'Daltonismo (deuteranopía, protanopía, tritanopía)'),
(107, 'Ninguna'),
(107, 'Ninguna'),
(507, 'Ninguna'),
(507, 'Ninguna'),
(507, 'Ninguna'),
(507, 'Ceguera total'),
(507, 'Daltonismo (deuteranopía, protanopía, tritanopía)');

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
(645, 'instructor', 'Doctorado', 'Lunes; Martes; Jueves; Sabado; '),
(623, 'funcionario', 'Tecnico', 'Lunes; Martes; Miercoles; Jueves; Viernes; Sabado; '),
(107, 'funcionario', 'Doctorado', 'Lunes; Martes; Miercoles; ');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_programayficha`
--

CREATE TABLE `tb_programayficha` (
  `cod_usuario_fk` int(11) NOT NULL,
  `cod_programa` int(11) NOT NULL,
  `numero_ficha` int(11) NOT NULL,
  `nombre_programa` varchar(30) NOT NULL,
  `jornada_programa` varchar(20) CHARACTER SET latin1 COLLATE latin1_general_cs NOT NULL,
  `fecha_inicio` date NOT NULL,
  `fecha_final` date NOT NULL,
  `inicio_produc` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Volcado de datos para la tabla `tb_programayficha`
--

INSERT INTO `tb_programayficha` (`cod_usuario_fk`, `cod_programa`, `numero_ficha`, `nombre_programa`, `jornada_programa`, `fecha_inicio`, `fecha_final`, `inicio_produc`) VALUES
(96, 2333311, 2673125, 'ADSO', 'Diurna', '2022-12-12', '2023-01-22', '2023-02-01'),
(40, 877841, 798465, 'ADSO', 'Nocturna', '2024-08-13', '2024-08-13', '2024-08-13'),
(190, 3213223, 123123, 'ADSO', 'Manana', '2024-08-14', '2024-08-14', '2024-08-14'),
(507, 7898797, 7897978, 'ADSO', 'Tarde', '2024-08-20', '2024-08-20', '2024-08-20');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_reporte`
--

CREATE TABLE `tb_reporte` (
  `id_reporte` int(11) NOT NULL,
  `cod_usuario_fk` int(11) NOT NULL,
  `encabezado_reporte` varchar(30) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `descripcion_reporte` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `ubicacion` varchar(30) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `fecha_hora_reporte` timestamp NOT NULL DEFAULT current_timestamp(),
  `soporte_reporte` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_reporte`
--

INSERT INTO `tb_reporte` (`id_reporte`, `cod_usuario_fk`, `encabezado_reporte`, `descripcion_reporte`, `ubicacion`, `fecha_hora_reporte`, `soporte_reporte`) VALUES
(360, 190, 'Auida', 'Prueba de reporte', 'SENA', '2024-08-14 12:58:07', 'http://localhost/AIR_Database/img/360.png'),
(412, 40, 'Se cayo el porton', 'Jesse se cayo el porton', 'Sena CIMI', '2024-08-13 17:13:25', 'http://localhost/AIR_Database/img/412.png'),
(517, 507, 'Auida x2', 'Se cayo el porton', 'Sena CIMI', '2024-08-20 12:52:30', 'http://localhost/AIR_Database/img/517.png'),
(558, 623, 'Mesa inclinada', 'Hay una mesa inclinada que podria caerse y lastimar alguien, auidaaaaa pls', 'Fabrica de software', '2024-08-23 13:44:26', 'http://localhost/AIR_Database/img/558.png'),
(568, 40, 'we', 'we', 'we', '2024-08-16 13:52:05', 'http://localhost/AIR_Database/img/568.png'),
(592, 96, 'Gotera cerca toma corriente', 'Hay una gotera en el techo que da contacto directo a un toma corriente', 'Fabrica de Sotfware', '2024-08-08 12:54:23', 'http://localhost/AIR_Database/img/592.png'),
(650, 623, 'Prueba', 'Me quieren agitar\nMe incitan a gritar\nSoy como una roca\nPalabras no me tocan\nAdentro hay un volcán\nQue pronto va a estallar\nYo quiero estar tranquilo\nEs mi situación\nUna desolación\nSoy como un lamento\nLamento boliviano\nQue un día empezó\nY no va a terminar\nY a nadie hace daño', 'CIMI', '2024-08-13 06:55:12', 'http://localhost/AIR_Database/img/650.png'),
(709, 40, 'Panal de abejas', 'Hay un panal de abejas en la cafeteria', 'Cafeteria', '2024-08-16 13:52:02', 'http://localhost/AIR_Database/img/709.png'),
(755, 96, 'Ataque de oso', 'Hay un oso chaval, pero esta siendo controlado por un misterioso hombre que se hace llmar veggeta777', 'Cafeteria', '2024-08-08 12:22:25', 'http://localhost/AIR_Database/img/755.png');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_revision`
--

CREATE TABLE `tb_revision` (
  `id_reporte_fk` int(11) NOT NULL,
  `tipo_peligro` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `nivel_peligro` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `fecha_revision` timestamp NULL DEFAULT current_timestamp(),
  `estado` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `cod_usuario_fk` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_revision`
--

INSERT INTO `tb_revision` (`id_reporte_fk`, `tipo_peligro`, `nivel_peligro`, `fecha_revision`, `estado`, `cod_usuario_fk`) VALUES
(592, 'ALTO', 'FISICO', '2024-08-22 15:06:00', 'REVISADO', 107),
(755, 'bajo', 'fisico', '2024-08-23 13:24:05', 'REVISADO', 623),
(412, 'Alto', 'Fisico', '2024-08-23 13:50:06', 'REVISADO', 623),
(650, 'ALTO', 'PSICOLOGICO', '2024-08-20 12:04:16', 'REVISADO', 623),
(360, 'DESCONOCIDO', 'DESCONOCIDO', '0000-00-00 00:00:00', 'PENDIENTE', NULL),
(568, 'MEDIO', 'FISICO', '2024-08-16 13:30:07', 'REVISADO', 623),
(709, 'ALTO', 'FISICO', '2024-08-16 13:22:11', 'REVISADO', 623),
(517, 'alto', 'FISICO', '2024-08-23 12:53:32', 'REVISADO', 623),
(558, 'DESCONOCIDO', 'DESCONOCIDO', '0000-00-00 00:00:00', 'PENDIENTE', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_usuario`
--

CREATE TABLE `tb_usuario` (
  `cod_usuario` int(11) NOT NULL,
  `tipo_docu_usuario` enum('Cedula de Ciudadania','Tarjeta de Indetidad','Cedula de Extranjeria','PEP','Permiso por protección Temporal') CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `cedula_usuario` bigint(20) NOT NULL,
  `nombre_usuario` varchar(15) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `apell_usuario` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `email_usuario` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `pass_user` varbinary(16) NOT NULL,
  `estado` enum('ACTIVO','INACTIVO') NOT NULL,
  `rol_user` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_usuario`
--

INSERT INTO `tb_usuario` (`cod_usuario`, `tipo_docu_usuario`, `cedula_usuario`, `nombre_usuario`, `apell_usuario`, `email_usuario`, `pass_user`, `estado`, `rol_user`) VALUES
(40, 'Permiso por protección Temporal', 456798205125, 'Sara', 'Hernandez', 'sarita@gmail.com', 0x53656e6131323334, 'INACTIVO', 'aprendiz'),
(96, 'Cedula de Ciudadania', 79846777, 'Sebas', 'Ruiz', 'sebas@gmail.com', 0x53656e6131323334, 'INACTIVO', 'aprendiz'),
(107, 'Cedula de Ciudadania', 123789456123, 'Andres', 'Cardenas', 'andres@gmail.com', 0x53656e6131323334, 'ACTIVO', 'funcionario'),
(190, 'Cedula de Ciudadania', 564554642313, 'Usuario', 'Sena', 'Sena3@gmail.com', 0x53656e6131323334, 'ACTIVO', 'aprendiz'),
(507, 'Cedula de Ciudadania', 456798205, 'Alfredo', 'ZZZ', 'zzz@gmail.com', 0x53656e6131323334, 'ACTIVO', 'aprendiz'),
(623, 'Cedula de Ciudadania', 6446647, 'Carlos', 'Hernandez', 'carlos@gmail.com', 0x73456e6131323334, 'INACTIVO', 'funcionario'),
(645, 'Cedula de Ciudadania', 32656566, 'Arturo', 'Mendoza', 'mendoza@gmail.com', 0x73656e4131323334, 'ACTIVO', 'instructor'),
(1000, 'Cedula de Ciudadania', 6549742, 'ADMIN', 'SENA', 'air_admin@gmail.com', 0x41646d696e333231, 'ACTIVO', 'admin');

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
  ADD KEY `id_reporte_fk` (`id_reporte_fk`),
  ADD KEY `cod_usuario_fk` (`cod_usuario_fk`);

--
-- Indices de la tabla `tb_usuario`
--
ALTER TABLE `tb_usuario`
  ADD PRIMARY KEY (`cod_usuario`),
  ADD UNIQUE KEY `cedula_usuario` (`cedula_usuario`);

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
  ADD CONSTRAINT `tb_revision_ibfk_1` FOREIGN KEY (`id_reporte_fk`) REFERENCES `tb_reporte` (`id_reporte`),
  ADD CONSTRAINT `tb_revision_ibfk_2` FOREIGN KEY (`cod_usuario_fk`) REFERENCES `tb_usuario` (`cod_usuario`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
