<?php

include 'conexion.php';

$consulta = ("SELECT tb_reporte.*, tb_revision.estado FROM tb_reporte LEFT JOIN tb_revision ON tb_reporte.id_reporte = tb_revision.id_reporte_fk WHERE tb_revision.id_reporte_fk = (SELECT MIN(id_reporte_fk) FROM tb_revision WHERE id_reporte_fk = tb_reporte.id_reporte) ORDER BY fecha_hora_reporte DESC");

$resultado = $conexion -> query($consulta);

while($fila=$resultado -> fetch_array()) {
    $datos[] = array_map('utf8_encode', $fila);
}
echo json_encode($datos);

$resultado -> close();
?>