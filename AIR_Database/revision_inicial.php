<?php
// Incluye el archivo de conexión a la base de datos
include("./conexion.php");

// Asigna los valores del formulario POST a las variables correspondientes
// $_POST['id_reporte_fk'] obtiene el valor del parámetro 'id_reporte_fk' del formulario POST
// $_POST['tipo_peligro'] obtiene el valor del parámetro 'tipo_peligro' del formulario POST
// $_POST['nivel_peligro'] obtiene el valor del parámetro 'nivel_peligro' del formulario POST
// $_POST['fecha_revision'] obtiene el valor del parámetro 'fecha_revision' del formulario POST
// $_POST['estado'] obtiene el valor del parámetro 'estado' del formulario POST
$id_reporte_fk=$_POST["id_reporte_fk"];
$tipo_peligro=$_POST["tipo_peligro"];
$nivel_peligro=$_POST["nivel_peligro"];
$fecha_revision=$_POST["fecha_revision"];
$estado=$_POST["estado"];

// Define una consulta SQL para insertar un nuevo registro en la tabla 'tb_revision'
// La consulta incluye los valores proporcionados en los campos correspondientes de la tabla
// Se asigna NULL a 'cod_usuario_fk', lo que puede indicar que no se proporciona un valor para este campo en esta inserción
$consulta="INSERT INTO tb_revision (id_reporte_fk, tipo_peligro, nivel_peligro, fecha_revision, estado, cod_usuario_fk) VALUES ('$id_reporte_fk','$tipo_peligro','$nivel_peligro','$fecha_revision','$estado', NULL)";

// Ejecuta la consulta SQL utilizando el objeto de conexión $conexion
// Si la consulta falla, se muestra un mensaje de error que incluye el detalle del error
mysqli_query($conexion, $consulta) or die ('Error en registrar'.mysqli_error($conexion));
// Cierra la conexión a la base de datos
mysqli_close($conexion);
?>