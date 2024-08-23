<?php

include 'conexion.php';

$ndoc=$_GET['cedula_usuario'];

$consulta = ("SELECT tb_reporte.*, tb_revision.estado FROM tb_reporte LEFT JOIN tb_revision ON tb_reporte.id_reporte = tb_revision.id_reporte_fk WHERE tb_revision.id_reporte_fk = (SELECT MIN(id_reporte_fk) FROM tb_revision WHERE id_reporte_fk = tb_reporte.id_reporte) AND tb_reporte.cod_usuario_fk = (SELECT cod_usuario FROM tb_usuario WHERE cedula_usuario = '$ndoc')");

$resultado = $conexion -> query($consulta);

while($fila=$resultado -> fetch_array()) {
    $datos[] = array_map('utf8_decode', $fila);
}
echo json_encode($datos);

$resultado -> close();
?>