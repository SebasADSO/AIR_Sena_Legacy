<?php
include("./conexion.php");
/**

 * Actualiza los datos de un usuario en la base de datos.

 *

 * @param $id El código del usuario que se va a actualizar.

 * @param $tipo_docu_usuario El tipo de documento del usuario.

 * @param $doc El número de documento del usuario.

 * @param $estado El estado del usuario.

 */
$id=$_POST["cod_usuario"];
$tipo_docu_usuario=$_POST["tipo_docu_usuario"];
$doc=$_POST["numero_documento"];
$estado=$_POST["estado"];

$consulta="UPDATE `tb_usuario` SET `tipo_docu_usuario`='$tipo_docu_usuario',`cedula_usuario`='$doc', `estado`='$estado' WHERE cod_usuario  = '$id'";

mysqli_query($conexion, $consulta) or die ('Error en actualizar'.mysqli_error($conexion));
mysqli_close($conexion);
?>