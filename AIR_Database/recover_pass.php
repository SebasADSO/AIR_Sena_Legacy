<?php
include("./conexion.php");

$email=$_POST["email_usuario"];
$new_pass=$_POST["pass_user"];

$consulta="UPDATE tb_usuario SET pass_user='$new_pass' WHERE email_usuario='$email'";

mysqli_query($conexion, $consulta) or die ('Error en registrar'.mysqli_error($conexion));
mysqli_close($conexion);
?>