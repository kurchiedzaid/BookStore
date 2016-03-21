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


$query = mysqli_query($connect, "insert into transaction SELECT * FROM cart ");

mysqli_close($connect);
?>