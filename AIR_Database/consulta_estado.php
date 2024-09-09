<?php

// Incluye el archivo de conexión a la base de datos
include 'conexion.php';

// Asigna el valor del parámetro 'cod_usuario' del formulario GET a la variable $id
$id_reporte=$_GET['id_reporte'];

// Define una consulta SQL para seleccionar los datos del usuario en la tabla 'tb_revision'
$consulta = ("SELECT * FROM tb_revision WHERE id_reporte_fk = '$id_reporte'");

// Ejecuta la consulta SQL utilizando el objeto de conexión $conexion
// El resultado de la consulta se almacena en la variable $resultado
$resultado = $conexion -> query($consulta);

// Itera sobre cada fila del conjunto de resultados
// fetch_array() obtiene una fila del resultado como un array en cada iteración del bucle
while($fila=$resultado -> fetch_array()) {
    // Transforma cada fila en un array donde los valores están codificados en UTF-8
    // array_map aplica la función utf8_encode a cada elemento del array $fila
    $datos[] = array_map('utf8_encode', $fila);
}
// Convierte el array $datos a formato JSON y lo imprime
// json_encode convierte el array PHP en una cadena JSON
echo json_encode($datos);

// Cierra el objeto de resultado para liberar los recursos asociados con él
$resultado -> close();
?>
