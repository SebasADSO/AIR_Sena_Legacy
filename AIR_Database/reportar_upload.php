<?php 

include 'conexion.php';

$id_report=$_POST['id_reporte'];
$cod_user=$_POST['cod_usuario_fk'];
$titulo=$_POST['encabezado_reporte'];
$descripcion=$_POST['descripcion_reporte'];
$lugar=$_POST['ubicacion'];
$fecha=$_POST['fecha_hora_reporte'];
$imagen=$_POST['soporte_reporte'];

$path = "img/$id_report.png";

$actualpath = "http://localhost/AIR_Database/$path";

$consulta = "INSERT INTO tb_reporte (id_reporte, cod_usuario_fk, encabezado_reporte, descripcion_reporte, ubicacion, fecha_hora_reporte, soporte_reporte) VALUES ('$id_report', '$cod_user', '$titulo', '$descripcion', '$lugar', '$fecha', '$actualpath')";

if (mysqli_query($conexion,$consulta)) {
    file_put_contents($path, base64_decode($imagen));
    echo 'Se recibio el reporte correctamente';
    mysqli_close($conexion);
} else {
    echo 'Ocurrio un error. Intente nuevamente';
}

?>