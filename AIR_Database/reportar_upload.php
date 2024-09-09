<?php 

// Incluye el archivo de conexión a la base de datos
include 'conexion.php';

// Asigna los valores del formulario POST a las variables correspondientes
// $_POST['id_reporte'] obtiene el valor del parámetro 'id_reporte' del formulario POST
// $_POST['cod_usuario_fk'] obtiene el valor del parámetro 'cod_usuario_fk' del formulario POST
// $_POST['encabezado_reporte'] obtiene el valor del parámetro 'encabezado_reporte' del formulario POST
// $_POST['descripcion_reporte'] obtiene el valor del parámetro 'descripcion_reporte' del formulario POST
// $_POST['ubicacion'] obtiene el valor del parámetro 'ubicacion' del formulario POST
// $_POST['fecha_hora_reporte'] obtiene el valor del parámetro 'fecha_hora_reporte' del formulario POST
// $_POST['soporte_reporte'] obtiene el valor del parámetro 'soporte_reporte' (base64) del formulario POST
$id_report=$_POST['id_reporte'];
$cod_user=$_POST['cod_usuario_fk'];
$titulo=$_POST['encabezado_reporte'];
$descripcion=$_POST['descripcion_reporte'];
$lugar=$_POST['ubicacion'];
$fecha=$_POST['fecha_hora_reporte'];
$imagen=$_POST['soporte_reporte'];

// Define la ruta del archivo donde se almacenará la imagen
// El archivo se guardará en la carpeta 'img' con el nombre basado en 'id_reporte' y extensión '.png'
$path = "img/$id_report.png";

// Define la URL completa del archivo de imagen para almacenarlo en la base de datos
// La URL apunta al archivo dentro del directorio 'AIR_Database' en el servidor local
$actualpath = "http://localhost/AIR_Database/$path";

// Define una consulta SQL para insertar un nuevo registro en la tabla 'tb_reporte'
// La consulta incluye los valores proporcionados en los campos correspondientes de la tabla
$consulta = "INSERT INTO tb_reporte (id_reporte, cod_usuario_fk, encabezado_reporte, descripcion_reporte, ubicacion, fecha_hora_reporte, soporte_reporte) VALUES ('$id_report', '$cod_user', '$titulo', '$descripcion', '$lugar', '$fecha', '$actualpath')";

// Ejecuta la consulta SQL utilizando el objeto de conexión $conexion
if (mysqli_query($conexion,$consulta)) {
    // Si la consulta es exitosa, decodifica la imagen en base64 y la guarda en el archivo especificado en $path
    file_put_contents($path, base64_decode($imagen));
    // Imprime un mensaje indicando que el reporte se recibió correctamente
    echo 'Se recibio el reporte correctamente';
    // Cierra la conexión a la base de datos
    mysqli_close($conexion);
} else {
    // Si ocurre un error durante la ejecución de la consulta, imprime un mensaje de error
    echo 'Ocurrio un error. Intente nuevamente';
}

?>