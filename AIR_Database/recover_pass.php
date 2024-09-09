<?php
// Incluye el archivo de conexión a la base de datos
include("./conexion.php");

// Asigna los valores del formulario POST a las variables correspondientes
// $_POST['email_usuario'] obtiene el valor del parámetro 'email_usuario' del formulario POST
// $_POST['pass_user'] obtiene el valor del parámetro 'pass_user' del formulario POST
$email=$_POST["email_usuario"];
$new_pass=$_POST["pass_user"];

// Define una consulta SQL para actualizar la contraseña del usuario en la tabla 'tb_usuario'
// La consulta actualiza el campo 'pass_user' con el nuevo valor proporcionado
// donde el campo 'email_usuario' coincide con el valor proporcionado
$consulta="UPDATE tb_usuario SET pass_user='$new_pass' WHERE email_usuario='$email'";

// Ejecuta la consulta SQL utilizando el objeto de conexión $conexion
// Si la consulta falla, se detiene la ejecución y se muestra un mensaje de error
mysqli_query($conexion, $consulta) or die ('Error en registrar'.mysqli_error($conexion));
// Cierra la conexión a la base de datos
mysqli_close($conexion);
?>