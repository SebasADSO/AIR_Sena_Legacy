<?php
// Incluye el archivo de conexión a la base de datos
include("./conexion.php");

// Asigna el valor del parámetro 'cod_usuario' del formulario POST a la variable $id
// Asigna el valor del parámetro 'estado' del formulario POST a la variable $estado
$id=$_POST["cod_usuario"];
$estado=$_POST["estado"];

// Crea una consulta SQL para actualizar el campo 'estado' en la tabla 'tb_usuario' donde el valor del campo 'cod_usuario' es igual al valor de la variable $id
$consulta="UPDATE tb_usuario SET estado='$estado' WHERE cod_usuario='$id'";

// Ejecuta la consulta SQL usando la conexión establecida en el archivo 'conexion.php', Si ocurre un error durante la ejecución de la consulta, se muestra un mensaje de error
mysqli_query($conexion, $consulta) or die ('Error en registrar'.mysqli_error($conexion));
// Cierra la conexión a la base de datos
mysqli_close($conexion);
?>