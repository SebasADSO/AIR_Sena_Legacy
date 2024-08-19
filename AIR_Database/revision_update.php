<?php
include("./conexion.php");

$id_reporte_fk=$_GET['id_reporte_fk'];
$tipo_peligro=$_POST["tipo_peligro"];
$nivel_peligro=$_POST["nivel_peligro"];
$fecha_revision=$_POST["fecha_revision"];
$estado=$_POST["estado"];
$cod_usuario_fk=$_POST["cod_usuario_fk"];

$consulta = "UPDATE `tb_revision` SET tipo_peligro = ?, nivel_peligro = ?, fecha_revision = ?, estado = ?, cod_usuario_fk=? WHERE id_reporte_fk = ?";

$stmt = mysqli_prepare($conexion, $consulta);
mysqli_stmt_bind_param($stmt, "ssssss", $tipo_peligro, $nivel_peligro, $fecha_revision, $estado, $cod_usuario_fk, $id_reporte_fk);

if (mysqli_stmt_execute($stmt)) {
    echo "Bien";
} else {
    echo "Error: " . mysqli_stmt_error($stmt);
}

mysqli_stmt_close($stmt);
?>