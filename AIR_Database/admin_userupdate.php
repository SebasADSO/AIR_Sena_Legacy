<?php
// Incluye el archivo de conexión a la base de datos
include("./conexion.php");

// Asigna el valor del parámetro 'cod_usuario' del formulario POST a la variable $id
$id=$_POST["cod_usuario"];
// Asigna el valor del parámetro 'tipo_docu_usuario' del formulario POST a la variable $tipo_docu_usuario
$tipo_docu_usuario=$_POST["tipo_docu_usuario"];
// Asigna el valor del parámetro 'numero_documento' del formulario POST a la variable $doc
$doc=$_POST["numero_documento"];
// Asigna el valor del parámetro 'estado' del formulario POST a la variable $estado
$estado=$_POST["estado"];

// Define una consulta SQL para actualizar los datos del usuario en la tabla 'tb_usuario'
$consulta="UPDATE `tb_usuario` SET `tipo_docu_usuario`='$tipo_docu_usuario',`cedula_usuario`='$doc', `estado`='$estado' WHERE cod_usuario  = '$id'";

// Ejecuta la consulta SQL usando la conexión establecida en el archivo 'conexion.php'
// Si ocurre un error durante la ejecución de la consulta, se muestra un mensaje de error
mysqli_query($conexion, $consulta) or die ('Error en actualizar'.mysqli_error($conexion));
// Cierra la conexión a la base de datos
mysqli_close($conexion);
?>