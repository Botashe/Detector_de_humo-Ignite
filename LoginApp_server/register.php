<?php
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    if(!empty($_POST['nombre']) && !empty($_POST['email']) && !empty($_POST['password'])){
        $con = mysqli_connect("localhost", "root", "", "login_register_app");
        
        if (mysqli_connect_errno()) {
            echo "Fallo en la conexión a la base de datos: " . mysqli_connect_error();
            exit();
        }
        
        $Nombre = $_POST['nombre'];
        $Correo = $_POST['email'];
        $Password = $_POST['password']; // Sin hashear
        
        // Añadir idtipo_usuario con valor 2
        $sql = "INSERT INTO usuario (nombre, email, password, idtipo_usuario) VALUES ('$Nombre', '$Correo', '$Password', 2)";
        
        if(mysqli_query($con, $sql)){
            echo "Éxito en el registro";
        } else {
            echo "Error en la consulta: " . mysqli_error($con);
        }
        
        mysqli_close($con);
    } else {
        echo "Todos los campos son obligatorios";
    }
} else {
    echo "Acceso denegado";
}
?>
