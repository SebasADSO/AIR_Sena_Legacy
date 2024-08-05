<?php
include("./conexion.php");

$id=$_POST["cod_usuario"];
$nombre=$_POST["nombre"];
$apellido=$_POST["apellido"];
$tipo_doc=$_POST["tipo_documento"];
$n_doc=$_POST["numero_documento"];
$email=$_POST["email"];
$contrasena=$_POST["contrasena"];
$estado=$_POST["estado"];
$rol=$_POST["rol_user"];

$consulta="INSERT INTO tb_usuario (cod_usuario, tipo_docu_usuario, cedula_usuario, nombre_usuario, apell_usuario, email_usuario, pass_user, estado, rol_user) VALUES('".$id."','".$tipo_doc."','".$n_doc."','".$nombre."','".$apellido."','".$email."','".$contrasena."','".$estado."','".$rol."')";

mysqli_query($conexion, $consulta) or die ('Error en registrar'.mysqli_error($conexion));
mysqli_close($conexion);
?>