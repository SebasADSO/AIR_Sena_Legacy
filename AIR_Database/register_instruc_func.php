<?php
// Incluye el archivo de conexión a la base de datos
include("./conexion.php");

// Asigna los valores del formulario POST a las variables correspondientes
// $_POST['cod_usuario_fk'] obtiene el valor del parámetro 'cod_usuario_fk' del formulario POST
// $_POST['espec_encargado'] obtiene el valor del parámetro 'espec_encargado' del formulario POST
// $_POST['nivel_formacion'] obtiene el valor del parámetro 'nivel_formacion' del formulario POST
// $_POST['dia_laboral'] obtiene el valor del parámetro 'dia_laboral' del formulario POST
$id=$_POST["cod_usuario_fk"];
$espec_encargado=$_POST["espec_encargado"];
$nivel_formacion=$_POST["nivel_formacion"];
$dia_laboral=$_POST["dia_laboral"];

// Define una consulta SQL para insertar un nuevo registro en la tabla 'tb_encargadoyinstructor'
// La consulta inserta los valores proporcionados en los campos correspondientes de la tabla
$consulta="INSERT INTO tb_encargadoyinstructor (cod_usuario_fk, espec_encargado, nivel_formacion, dia_laboral) VALUES('".$id."','".$espec_encargado."','".$nivel_formacion."','".$dia_laboral."')";

// Ejecuta la consulta SQL utilizando el objeto de conexión $conexion
// Si la consulta falla, se detiene la ejecución y se muestra un mensaje de error
mysqli_query($conexion, $consulta) or die ('Error en registrar'.mysqli_error($conexion));
// Cierra la conexión a la base de datos
mysqli_close($conexion);
?>