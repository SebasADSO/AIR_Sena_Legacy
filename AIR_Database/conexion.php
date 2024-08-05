<?php
$hostname = 'localhost';
$database = 'air_database';
$username = 'root';
$password = '';

$conexion=new mysqli($hostname,$username,$password,$database);
$conexion->set_charset('utf8');

if ($conexion->connect_errno) {
  printf("Conexión fallida: %s\n", $conexion->connect_error);
} else {
  //echo 'Se establecio conexion con la base de datos';
}
?>