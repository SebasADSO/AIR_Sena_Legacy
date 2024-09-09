<?php
// Incluye el archivo de conexión a la base de datos
include 'conexion.php';

// Asigna el valor del parámetro 'cedula_usuario' del formulario GET a la variable $ndoc
// $_GET['cedula_usuario'] obtiene el valor del parámetro 'cedula_usuario' de la URL
$ndoc=$_GET['cedula_usuario'];

// Define una consulta SQL que selecciona todos los campos de la tabla 'tb_reporte'
// y el campo 'estado' de la tabla 'tb_revision'
// La consulta utiliza una combinación LEFT JOIN para combinar registros de ambas tablas
// La condición de la JOIN asegura que se obtengan todos los registros de 'tb_reporte'
// y solo los registros coincidentes de 'tb_revision'
// La consulta también filtra los resultados para que solo se incluyan los reportes
// cuyo 'id_reporte' es el mínimo 'id_reporte_fk' para cada 'id_reporte'
// y cuyo 'cod_usuario_fk' coincide con el código de usuario asociado al 'cedula_usuario'
// Finalmente, los resultados se ordenan por 'fecha_hora_reporte' en orden descendente
$consulta = ("SELECT tb_reporte.*, tb_revision.estado FROM tb_reporte LEFT JOIN tb_revision ON tb_reporte.id_reporte = tb_revision.id_reporte_fk WHERE tb_revision.id_reporte_fk = (SELECT MIN(id_reporte_fk) FROM tb_revision WHERE id_reporte_fk = tb_reporte.id_reporte) AND tb_reporte.cod_usuario_fk = (SELECT cod_usuario FROM tb_usuario WHERE cedula_usuario = '$ndoc')");

// Ejecuta la consulta SQL utilizando el objeto de conexión $conexion
// El resultado de la consulta se almacena en la variable $resultado
$resultado = $conexion -> query($consulta);

// Itera sobre cada fila del conjunto de resultados
// fetch_array() obtiene una fila del resultado como un array en cada iteración del bucle
while($fila=$resultado -> fetch_array()) {
    // Transforma cada fila en un array donde los valores están codificados en UTF-8
    // array_map aplica la función utf8_decode a cada elemento del array $fila
    $datos[] = array_map('utf8_decode', $fila);
}
// Convierte el array $datos a formato JSON y lo imprime
// json_encode convierte el array PHP en una cadena JSON
echo json_encode($datos);

// Cierra el objeto de resultado para liberar los recursos asociados con él
// Esto es una buena práctica para evitar fugas de memoria y liberar recursos del servidor
$resultado -> close();
?>