<?php
// Incluye el archivo de conexión a la base de datos
include("./conexion.php");

// Asigna los valores del formulario GET y POST a las variables correspondientes
// $_GET['id_reporte_fk'] obtiene el valor del parámetro 'id_reporte_fk' del formulario GET
// $_POST['tipo_peligro'] obtiene el valor del parámetro 'tipo_peligro' del formulario POST
// $_POST['nivel_peligro'] obtiene el valor del parámetro 'nivel_peligro' del formulario POST
// $_POST['fecha_revision'] obtiene el valor del parámetro 'fecha_revision' del formulario POST
// $_POST['estado'] obtiene el valor del parámetro 'estado' del formulario POST
// $_POST['cod_usuario_fk'] obtiene el valor del parámetro 'cod_usuario_fk' del formulario POST
$id_reporte_fk=$_GET['id_reporte_fk'];
$tipo_peligro=$_POST["tipo_peligro"];
$nivel_peligro=$_POST["nivel_peligro"];
$fecha_revision=$_POST["fecha_revision"];
$estado=$_POST["estado"];
$cod_usuario_fk=$_POST["cod_usuario_fk"];

// Define una consulta SQL para actualizar un registro en la tabla 'tb_revision'
// Utiliza placeholders (?) para los valores que se actualizarán
$consulta = "UPDATE `tb_revision` SET tipo_peligro = ?, nivel_peligro = ?, fecha_revision = ?, estado = ?, cod_usuario_fk=? WHERE id_reporte_fk = ?";

// Prepara la consulta SQL utilizando la función mysqli_prepare
// Esto ayuda a evitar inyecciones SQL al usar sentencias preparadas
$stmt = mysqli_prepare($conexion, $consulta);
// Asocia los parámetros de entrada a la sentencia preparada
// 'ssssss' indica que los seis parámetros son cadenas de texto (strings)
// El último parámetro corresponde al valor en la cláusula WHERE
mysqli_stmt_bind_param($stmt, "ssssss", $tipo_peligro, $nivel_peligro, $fecha_revision, $estado, $cod_usuario_fk, $id_reporte_fk);

// Ejecuta la sentencia SQL preparada
// Si la ejecución es exitosa, imprime 'Bien'
// Si ocurre un error, imprime el mensaje de error utilizando mysqli_stmt_error
if (mysqli_stmt_execute($stmt)) {
    echo "Bien";
} else {
    echo "Error: " . mysqli_stmt_error($stmt);
}
// Cierra la sentencia preparada para liberar los recursos asociados
mysqli_stmt_close($stmt);
?>