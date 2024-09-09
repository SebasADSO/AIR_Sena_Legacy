<?php
// Incluye el archivo de conexión a la base de datos
include 'conexion.php';

// Asigna el valor del parámetro 'cedula_usuario' del formulario GET a la variable $ndoc
// $_GET['cedula_usuario'] obtiene el valor del parámetro 'cedula_usuario' de la URL
$ndoc=$_GET['cedula_usuario'];

// Define una consulta SQL para seleccionar el campo 'cod_usuario' de la tabla 'tb_usuario'
// La consulta filtra los resultados para que solo se devuelvan los registros con 'cedula_usuario' igual al valor proporcionado
$consulta = ("SELECT cod_usuario FROM tb_usuario WHERE cedula_usuario='$ndoc'");

// Ejecuta la consulta SQL utilizando el objeto de conexión $conexion
$resultado = $conexion -> query($consulta);

while($fila=$resultado -> fetch_array()) {
    // Itera sobre los resultados de la consulta
    // fetch_array() obtiene una fila del conjunto de resultados como un array
    $datos[] = array_map('utf8_decode', $fila);
}
// Convierte el array $datos a formato JSON y lo imprime
// json_encode($datos) convierte el array a una cadena JSON
echo json_encode($datos);

// Cierra el conjunto de resultados para liberar recursos
$resultado -> close();
?>