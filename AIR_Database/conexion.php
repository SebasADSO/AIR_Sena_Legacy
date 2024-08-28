<?php
/**

 * Establece una conexión a una base de datos MySQL utilizando la clase mysqli.

 *

 * @param $hostname El nombre del host donde se encuentra la base de datos.

 * @param $database El nombre de la base de datos a la que se va a conectar.

 * @param $username El nombre de usuario para la conexión a la base de datos.

 * @param $password La contraseña para la conexión a la base de datos.

 */
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