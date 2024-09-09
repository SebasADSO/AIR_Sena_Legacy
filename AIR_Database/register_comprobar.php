<?php
// Incluye el archivo de conexión a la base de datos
include 'conexion.php';
// Asigna los valores del formulario POST a las variables correspondientes
// $_POST['numero_documento'] obtiene el valor del parámetro 'numero_documento' del formulario POST
// $_POST['email'] obtiene el valor del parámetro 'email' del formulario POST
$usu_usuario=$_POST['numero_documento'];
$usu_email=$_POST['email'];

// Prepara una sentencia SQL para seleccionar un registro de la tabla 'tb_usuario'
// La consulta busca un usuario que coincida con el 'cedula_usuario' o el 'email_usuario'
// Los parámetros son reemplazados en tiempo de ejecución para evitar inyecciones SQL
$sentencia=$conexion->prepare("SELECT * FROM tb_usuario WHERE cedula_usuario=? OR email_usuario=?");
// Asocia los parámetros de entrada a la sentencia preparada
// 'ss' indica que los dos parámetros son cadenas de texto (strings)
$sentencia->bind_param('ss', $usu_usuario,$usu_email);
// Ejecuta la sentencia SQL preparada
$sentencia->execute();

// Obtiene el resultado de la ejecución de la sentencia
// get_result() devuelve el resultado como un objeto de tipo mysqli_result
$resultado = $sentencia->get_result();
// Verifica si se ha obtenido alguna fila del resultado
// fetch_assoc() devuelve una fila del resultado como un array asociativo
if ($fila = $resultado->fetch_assoc()) {
    // Convierte la fila obtenida a formato JSON y la imprime
    // JSON_UNESCAPED_UNICODE asegura que los caracteres Unicode se codifiquen correctamente en la salida JSON
    echo json_encode($fila,JSON_UNESCAPED_UNICODE);     
}
// Cierra la sentencia preparada para liberar recursos asociados con ella
$sentencia->close();
// Cierra la conexión a la base de datos
$conexion->close();
?>