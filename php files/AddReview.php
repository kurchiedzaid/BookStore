
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

$feedbackName = isset($_POST['feedbackName']) ? $_POST['feedbackName'] : '';

$feedbackDescription = isset($_POST['feedbackDescription']) ? $_POST['feedbackDescription'] : '';

$ratings = isset($_POST['ratings']) ? $_POST['ratings'] : '';


$query = mysqli_query($connect, "insert into feedback(feedbackName,feedbackDescription,ratings) values ('$feedbackName','$feedbackDescription','$ratings') ");

mysqli_close($connect);
?>