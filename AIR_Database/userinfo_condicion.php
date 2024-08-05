<?php

include 'conexion.php';

$ndoc=$_GET['cedula_usuario'];

$consulta = ("SELECT * FROM tb_condicion_usua WHERE (SELECT cod_usuario FROM tb_usuario WHERE cedula_usuario = ".$ndoc.") = cod_usuario_fk");

$resultado = $conexion -> query($consulta);

while($fila=$resultado -> fetch_array()) {
    $datos[] = array_map('utf8_decode', $fila);
}
echo json_encode($datos);

$resultado -> close();
?>