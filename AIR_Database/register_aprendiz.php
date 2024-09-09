<?php
// Incluye el archivo de conexión a la base de datos
include("./conexion.php");

// Asigna los valores del formulario POST a las variables correspondientes
// $_POST['cod_usuario_fk'] obtiene el valor del parámetro 'cod_usuario_fk' del formulario POST
// $_POST['cod_programa'] obtiene el valor del parámetro 'cod_programa' del formulario POST
// $_POST['numero_ficha'] obtiene el valor del parámetro 'numero_ficha' del formulario POST
// $_POST['nombre_programa'] obtiene el valor del parámetro 'nombre_programa' del formulario POST
// $_POST['jornada_programa'] obtiene el valor del parámetro 'jornada_programa' del formulario POST
// $_POST['fecha_inicio'] obtiene el valor del parámetro 'fecha_inicio' del formulario POST
// $_POST['fecha_final'] obtiene el valor del parámetro 'fecha_final' del formulario POST
// $_POST['inicio_produc'] obtiene el valor del parámetro 'inicio_produc' del formulario POST
$id=$_POST["cod_usuario_fk"];
$cod_programa=$_POST["cod_programa"];
$numero_ficha=$_POST["numero_ficha"];
$nombre_programa=$_POST["nombre_programa"];
$jornada_programa=$_POST["jornada_programa"];
$fecha_inicio=$_POST["fecha_inicio"];
$fecha_final=$_POST["fecha_final"];
$inicio_produc=$_POST["inicio_produc"];

// Define una consulta SQL para insertar un nuevo registro en la tabla 'tb_programayficha'
// La consulta inserta los valores proporcionados en los campos correspondientes de la tabla
$consulta="INSERT INTO tb_programayficha (cod_usuario_fk, cod_programa, numero_ficha, nombre_programa, jornada_programa, fecha_inicio, fecha_final, inicio_produc) VALUES('".$id."','".$cod_programa."','".$numero_ficha."','".$nombre_programa."','".$jornada_programa."','".$fecha_inicio."','".$fecha_final."','".$inicio_produc."')";

// Ejecuta la consulta SQL utilizando el objeto de conexión $conexion
// Si la consulta falla, se detiene la ejecución y se muestra un mensaje de error
mysqli_query($conexion, $consulta) or die ('Error en registrar'.mysqli_error($conexion));
// Cierra la conexión a la base de datos
mysqli_close($conexion);
?>