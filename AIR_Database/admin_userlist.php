<?php
/**

 * Obtiene todos los registros de la tabla tb_usuario y devuelve ellos en formato JSON.

 */
include 'conexion.php';

$consulta = ("SELECT * FROM tb_usuario");

$resultado = $conexion -> query($consulta);

while($fila=$resultado -> fetch_array()) {
    $datos[] = array_map('utf8_encode', $fila);
}
echo json_encode($datos);

$resultado -> close();
?>