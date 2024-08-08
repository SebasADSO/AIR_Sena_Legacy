<?php

include 'conexion.php';

$id_report=$_GET['id_reporte'];

$consulta = ("SELECT * FROM tb_revision WHERE id_reporte_fk = '$id_report'");

$resultado = $conexion -> query($consulta);

while($fila=$resultado -> fetch_array()) {
    $datos[] = array_map('utf8_decode', $fila);
}
echo json_encode($datos);

$resultado -> close();
?>