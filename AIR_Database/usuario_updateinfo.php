<?php
// Incluye el archivo de conexión a la base de datos
include("./conexion.php");

// Asigna los valores de los parámetros POST a las variables
// $_POST["nombre"] obtiene el valor del parámetro 'nombre' del formulario POST
$nombre=$_POST["nombre"];
// $_POST["apellido"] obtiene el valor del parámetro 'apellido' del formulario POST
$apellido=$_POST["apellido"];
// $_POST["numero_documento"] obtiene el valor del parámetro 'numero_documento' del formulario POST
$doc=$_POST["numero_documento"];
// $_POST["email"] obtiene el valor del parámetro 'email' del formulario POST
$email=$_POST["email"];

// Define una consulta SQL para actualizar la tabla 'tb_usuario'
// La consulta actualiza los campos 'nombre_usuario', 'apell_usuario', y 'email_usuario'
// para el registro donde 'cedula_usuario' coincide con el valor de $doc
$consulta="UPDATE `tb_usuario` SET `nombre_usuario`='$nombre',`apell_usuario`='$apellido',`email_usuario`='$email' WHERE cedula_usuario = '$doc'";
// Ejecuta la consulta SQL utilizando el objeto de conexión $conexion
// Si ocurre un error en la ejecución, el script termina y muestra un mensaje de error
mysqli_query($conexion, $consulta) or die ('Error en actualizar'.mysqli_error($conexion));
// Cierra la conexión a la base de datos
mysqli_close($conexion);
?>