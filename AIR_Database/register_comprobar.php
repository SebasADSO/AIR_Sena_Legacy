<?php
include 'conexion.php';
$usu_usuario=$_POST['numero_documento'];
$usu_email=$_POST['email'];

$sentencia=$conexion->prepare("SELECT * FROM tb_usuario WHERE cedula_usuario=? OR email_usuario=?");
$sentencia->bind_param('ss', $usu_usuario,$usu_email);
$sentencia->execute();

$resultado = $sentencia->get_result();
if ($fila = $resultado->fetch_assoc()) {
         echo json_encode($fila,JSON_UNESCAPED_UNICODE);     
}
$sentencia->close();
$conexion->close();
?>