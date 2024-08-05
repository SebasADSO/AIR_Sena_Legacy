<?php
include("./conexion.php");

$id=$_POST["cod_usuario_fk"];
$espec_encargado=$_POST["espec_encargado"];
$nivel_formacion=$_POST["nivel_formacion"];
$dia_laboral=$_POST["dia_laboral"];

$consulta="INSERT INTO tb_encargadoyinstructor (cod_usuario_fk, espec_encargado, nivel_formacion, dia_laboral) VALUES('".$id."','".$espec_encargado."','".$nivel_formacion."','".$dia_laboral."')";

mysqli_query($conexion, $consulta) or die ('Error en registrar'.mysqli_error($conexion));
mysqli_close($conexion);
?>