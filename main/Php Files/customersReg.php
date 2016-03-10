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

$name = isset($_POST['name']) ? $_POST['name'] : '';

$email= isset($_POST['email']) ? $_POST['email'] : '';

$password = isset($_POST['password']) ? $_POST['password'] : '';

$address = isset($_POST['address']) ? $_POST['address'] : '';

$country = isset($_POST['country']) ? $_POST['country'] : '';

$phone= isset($_POST['phone']) ? $_POST['phone'] : '';


$payment= isset($_POST['payment']) ? $_POST['payment'] : '';




$query = mysqli_query($connect, "insert into customers (name,email,password,address,country,phone,payment) values ('$name','$email','$password','$address','$country','$phone', '$payment') ");

mysqli_close($connect);
?>