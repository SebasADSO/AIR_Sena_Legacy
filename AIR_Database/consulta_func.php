<?php

include 'conexion.php';

$consulta = ("SELECT * FROM tb_reporte WHERE 1 ORDER BY fecha_hora_reporte DESC");

$resultado = $conexion -> query($consulta);

while($fila=$resultado -> fetch_array()) {
    $datos[] = array_map('utf8_encode', $fila);
}
echo json_encode($datos);

$resultado -> close();
?>