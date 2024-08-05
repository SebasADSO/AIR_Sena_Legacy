<?php

include 'conexion.php';

$ndoc=$_GET['cedula_usuario'];

$consulta = ("SELECT rol_user FROM tb_usuario WHERE cedula_usuario='$ndoc'");

$resultado = $conexion -> query($consulta);

while($fila=$resultado -> fetch_array()) {
    $datos[] = array_map('utf8_decode', $fila);
}
echo json_encode($datos);

$resultado -> close();
?>