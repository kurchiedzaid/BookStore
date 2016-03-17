<?php

$connect = mysqli_connect("localhost","root","","bookstore");

if(mysqli_connect_errno($connect))
{
    echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
else
{
    echo "success";
}

$id = isset($_POST['id']) ? $_POST['id'] : '';
$stock = isset($_POST['stock']) ? $_POST['stock'] : '';

$query= mysqli_query($connect, "UPDATE books SET stock = '$stock' WHERE id = $id");

mysqli_close($connect);
?>