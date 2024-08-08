<?php
include("./conexion.php");

$id_reporte_fk=$_POST["id_reporte_fk"];
$tipo_peligro=$_POST["tipo_peligro"];
$nivel_peligro=$_POST["nivel_peligro"];
$fecha_revision=$_POST["fecha_revision"];
$estado=$_POST["estado"];

$consulta="INSERT INTO tb_revision (id_reporte_fk, tipo_peligro, nivel_peligro, fecha_revision, estado) VALUES ('$id_reporte_fk','$tipo_peligro','$nivel_peligro','$fecha_revision','$estado')";

mysqli_query($conexion, $consulta) or die ('Error en registrar'.mysqli_error($conexion));
mysqli_close($conexion);
?>