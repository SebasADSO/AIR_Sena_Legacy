<?php
// $hostname El nombre del host donde se encuentra la base de datos.
$hostname = 'localhost';
// $database El nombre de la base de datos a la que se va a conectar.
$database = 'air_database';
// $username El nombre de usuario para la conexión a la base de datos.
$username = 'root';
// $password La contraseña para la conexión a la base de datos.
$password = '';

/// Crea una nueva conexión a la base de datos utilizando el objeto mysqli
// El constructor de mysqli recibe el nombre del host, el nombre de usuario, la contraseña y el nombre de la base de datos
$conexion=new mysqli($hostname,$username,$password,$database);
// Establece el conjunto de caracteres de la conexión a 'utf8'
// Esto asegura que los datos se manejen en formato UTF-8, lo cual es importante para la correcta codificación de caracteres especiales
$conexion->set_charset('utf8');

// Verifica si hubo un error en la conexión a la base de datos
// connect_errno contiene el código del error si la conexión falla
if ($conexion->connect_errno) {
  // Si hay un error de conexión, se muestra un mensaje con el error
  // connect_error contiene una descripción del error
  printf("Conexión fallida: %s\n", $conexion->connect_error);
} else {
  //echo 'Se establecio conexion con la base de datos';
}
?>