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

$userName = isset($_POST['userName']) ? $_POST['userName'] : '';

$BookId= isset($_POST['BookId']) ? $_POST['BookId'] : '';

$price= isset($_POST['price']) ? $_POST['price'] : '';

$query = mysqli_query($connect, "insert into cart (userName,BookId,price)values ('$userName','$BookId','$price') ");

mysqli_close($connect);
?>