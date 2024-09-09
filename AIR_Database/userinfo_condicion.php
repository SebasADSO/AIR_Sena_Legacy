<?php
// Incluye el archivo de conexión a la base de datos
include 'conexion.php';
// Asigna el valor del parámetro 'cedula_usuario' del formulario GET a la variable $ndoc
// $_GET['cedula_usuario'] obtiene el valor del parámetro 'cedula_usuario' del formulario GET
$ndoc=$_GET['cedula_usuario'];
// Define una consulta SQL para seleccionar todos los campos de la tabla 'tb_condicion_usua'
// La consulta utiliza una subconsulta para obtener el 'cod_usuario' de la tabla 'tb_usuario'
// basado en el valor de 'cedula_usuario'
// La subconsulta devuelve el 'cod_usuario' correspondiente a la 'cedula_usuario' proporcionada
$consulta = ("SELECT * FROM tb_condicion_usua WHERE cod_usuario_fk = (SELECT cod_usuario FROM tb_usuario WHERE cedula_usuario = '$ndoc')");
// Ejecuta la consulta SQL utilizando el objeto de conexión $conexion
$resultado = $conexion -> query($consulta);
// Recorre los resultados de la consulta
// `fetch_array()` obtiene cada fila del resultado como un array
while($fila=$resultado -> fetch_array()) {
    // Aplica la función `utf8_encode` a cada elemento de la fila para asegurar que los caracteres especiales sean codificados en UTF-8
    // `array_map('utf8_encode', $fila)` aplica `utf8_encode` a cada valor de $fila
    $datos[] = array_map('utf8_encode', $fila);
}
// Convierte el array $datos a formato JSON y lo imprime
// `json_encode($datos)` convierte el array a JSON para su uso en aplicaciones web
echo json_encode($datos);
// Cierra el resultado de la consulta para liberar los recursos asociados
$resultado -> close();
?>