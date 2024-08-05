<?php
include("./conexion.php");

$id=$_POST["cod_usuario_fk"];
$condicion_usuario=$_POST["condicion_usuario"];

$consulta="INSERT INTO tb_condicion_usua (cod_usuario_fk, condicion_usuario) VALUES('".$id."','".$condicion_usuario."')";

mysqli_query($conexion, $consulta) or die ('Error en registrar'.mysqli_error($conexion));
mysqli_close($conexion);
?>