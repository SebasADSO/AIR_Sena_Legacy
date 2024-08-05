<?php
include 'conexion.php';
$usu_tipo=$_POST['tipo'];
$usu_usuario=$_POST['usuario'];
$usu_password=$_POST['password'];

$sentencia=$conexion->prepare("SELECT * FROM tb_usuario WHERE tipo_docu_usuario=? AND cedula_usuario=? AND pass_user=?");
$sentencia->bind_param('sss', $usu_tipo,$usu_usuario,$usu_password);
$sentencia->execute();

$resultado = $sentencia->get_result();
if ($fila = $resultado->fetch_assoc()) {
         echo json_encode($fila,JSON_UNESCAPED_UNICODE);     
}
$sentencia->close();
$conexion->close();
?>