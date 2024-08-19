<?php
include("./conexion.php");

$nombre=$_POST["nombre"];
$apellido=$_POST["apellido"];
$doc=$_POST["numero_documento"];
$email=$_POST["email"];

$consulta="UPDATE `tb_usuario` SET `nombre_usuario`='$nombre',`apell_usuario`='$apellido',`email_usuario`='$email' WHERE cedula_usuario = '$doc'";

mysqli_query($conexion, $consulta) or die ('Error en actualizar'.mysqli_error($conexion));
mysqli_close($conexion);
?>