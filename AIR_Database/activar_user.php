<?php
/**

 * Actualiza el estado de un usuario en la base de datos.

 *

 * @param $id El código del usuario que se va a actualizar.

 * @param $estado El nuevo estado del usuario.

 */
include("./conexion.php");

$id=$_POST["cod_usuario"];
$estado=$_POST["estado"];

$consulta="UPDATE tb_usuario SET estado='$estado' WHERE cod_usuario='$id'";

mysqli_query($conexion, $consulta) or die ('Error en registrar'.mysqli_error($conexion));
mysqli_close($conexion);
?>