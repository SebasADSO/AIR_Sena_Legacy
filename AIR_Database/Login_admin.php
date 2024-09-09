<?php
// Incluye el archivo de conexión a la base de datos
include 'conexion.php';

// Asigna los valores del formulario POST a las variables correspondientes
// $_POST['tipo'] obtiene el valor del parámetro 'tipo' del formulario POST
// $_POST['usuario'] obtiene el valor del parámetro 'usuario' del formulario POST
// $_POST['password'] obtiene el valor del parámetro 'password' del formulario POST
$usu_tipo=$_POST['tipo'];
$usu_usuario=$_POST['usuario'];
$usu_password=$_POST['password'];

// Prepara una sentencia SQL para seleccionar un registro de la tabla 'tb_usuario'
// La consulta busca un usuario que coincida con el 'tipo_docu_usuario', 'cedula_usuario', y 'pass_user'
// También filtra por estado 'ACTIVO' y rol 'admin'
// Los parámetros son reemplazados en tiempo de ejecución para evitar inyecciones SQL
$sentencia=$conexion->prepare("SELECT * FROM tb_usuario WHERE tipo_docu_usuario=? AND cedula_usuario=? AND pass_user=? AND estado='ACTIVO' AND rol_user='admin'");
// Asocia los parámetros de entrada a la sentencia preparada
// 'sss' indica que los tres parámetros son cadenas de texto (strings)
$sentencia->bind_param('sss', $usu_tipo,$usu_usuario,$usu_password);
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
// Cierra la conexión a la base de datos para liberar los recursos asociados
$conexion->close();
?>