<?php
include("./conexion.php");

$id=$_POST["cod_usuario_fk"];
$cod_programa=$_POST["cod_programa"];
$numero_ficha=$_POST["numero_ficha"];
$nombre_programa=$_POST["nombre_programa"];


$consulta="INSERT INTO tb_programayficha (cod_usuario_fk, cod_programa, numero_ficha, nombre_programa, jornada_programa, fecha_inicio, fecha_final, inicio_produc) VALUES('".$id."','".$cod_programa."','".$numero_ficha."','".$nombre_programa."','".$jornada_programa."','".$fecha_inicio."','".$fecha_final."','".$inicio_produc."')";

mysqli_query($conexion, $consulta) or die ('Error en registrar'.mysqli_error($conexion));
mysqli_close($conexion);
?>