<?php
// Incluye el archivo de conexión a la base de datos
include 'conexion.php';

// Asigna el valor del parámetro 'cedula_usuario' del formulario GET a la variable $ndoc
// $_GET['cedula_usuario'] obtiene el valor del parámetro 'cedula_usuario' del formulario GET
$ndoc=$_GET['cedula_usuario'];

// Define una consulta SQL para seleccionar el campo 'rol_user' de la tabla 'tb_usuario'
// La consulta busca el rol del usuario cuyo 'cedula_usuario' coincida con el valor de $ndoc
$consulta = ("SELECT rol_user FROM tb_usuario WHERE cedula_usuario='$ndoc'");

// Ejecuta la consulta SQL utilizando el objeto de conexión $conexion
$resultado = $conexion -> query($consulta);

// Recorre los resultados de la consulta
// `fetch_array()` obtiene cada fila del resultado como un array
while($fila=$resultado -> fetch_array()) {
    // Aplica la función `utf8_decode` a cada elemento de la fila para decodificar caracteres UTF-8
    // `array_map('utf8_decode', $fila)` aplica `utf8_decode` a cada valor de $fila
    $datos[] = array_map('utf8_decode', $fila);
}
// Convierte el array $datos a formato JSON y lo imprime
// `json_encode($datos)` convierte el array a JSON para su uso en aplicaciones web
echo json_encode($datos);
// Cierra el resultado de la consulta para liberar los recursos asociados
$resultado -> close();
?>