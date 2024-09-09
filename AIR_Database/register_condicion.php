<?php
// Incluye el archivo de conexión a la base de datos
include("./conexion.php");

// Asigna los valores del formulario POST a las variables correspondientes
// $_POST['cod_usuario_fk'] obtiene el valor del parámetro 'cod_usuario_fk' del formulario POST
// $_POST['condicion_usuario'] obtiene el valor del parámetro 'condicion_usuario' del formulario POST
$id=$_POST["cod_usuario_fk"];
$condicion_usuario=$_POST["condicion_usuario"];

// Define una consulta SQL para insertar un nuevo registro en la tabla 'tb_condicion_usua'
// La consulta inserta los valores proporcionados en los campos correspondientes de la tabla
$consulta="INSERT INTO tb_condicion_usua (cod_usuario_fk, condicion_usuario) VALUES('".$id."','".$condicion_usuario."')";

// Ejecuta la consulta SQL utilizando el objeto de conexión $conexion
// Si la consulta falla, se detiene la ejecución y se muestra un mensaje de error
mysqli_query($conexion, $consulta) or die ('Error en registrar'.mysqli_error($conexion));
// Cierra la conexión a la base de datos
mysqli_close($conexion);
?>