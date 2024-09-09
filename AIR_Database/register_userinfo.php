<?php
// Incluye el archivo de conexión a la base de datos
include("./conexion.php");

// Asigna los valores del formulario POST a las variables correspondientes
// $_POST['cod_usuario'] obtiene el valor del parámetro 'cod_usuario' del formulario POST
// $_POST['nombre'] obtiene el valor del parámetro 'nombre' del formulario POST
// $_POST['apellido'] obtiene el valor del parámetro 'apellido' del formulario POST
// $_POST['tipo_documento'] obtiene el valor del parámetro 'tipo_documento' del formulario POST
// $_POST['numero_documento'] obtiene el valor del parámetro 'numero_documento' del formulario POST
// $_POST['email'] obtiene el valor del parámetro 'email' del formulario POST
// $_POST['contrasena'] obtiene el valor del parámetro 'contrasena' del formulario POST
// $_POST['estado'] obtiene el valor del parámetro 'estado' del formulario POST
// $_POST['rol_user'] obtiene el valor del parámetro 'rol_user' del formulario POST
$id=$_POST["cod_usuario"];
$nombre=$_POST["nombre"];
$apellido=$_POST["apellido"];
$tipo_doc=$_POST["tipo_documento"];
$n_doc=$_POST["numero_documento"];
$email=$_POST["email"];
$contrasena=$_POST["contrasena"];
$estado=$_POST["estado"];
$rol=$_POST["rol_user"];

// Define una consulta SQL para insertar un nuevo registro en la tabla 'tb_usuario'
// La consulta inserta los valores proporcionados en los campos correspondientes de la tabla
$consulta="INSERT INTO tb_usuario (cod_usuario, tipo_docu_usuario, cedula_usuario, nombre_usuario, apell_usuario, email_usuario, pass_user, estado, rol_user) VALUES('".$id."','".$tipo_doc."','".$n_doc."','".$nombre."','".$apellido."','".$email."','".$contrasena."','".$estado."','".$rol."')";

// Ejecuta la consulta SQL utilizando el objeto de conexión $conexion
// Si la consulta falla, se detiene la ejecución y se muestra un mensaje de error
mysqli_query($conexion, $consulta) or die ('Error en registrar'.mysqli_error($conexion));
// Cierra la conexión a la base de datos
mysqli_close($conexion);
?>