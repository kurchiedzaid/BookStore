<?php
define('HOST','localhost');
define('USER','root');
define('PASS','');
define('DB','bookstore');
 
$con = mysqli_connect(HOST,USER,PASS,DB);
 
$id = $_POST['id'];


$sql = "DELETE from cart";
 
$res = mysqli_query($con,$sql);
 
$check = mysqli_fetch_array($res);
 
if(isset($check)){
echo 'success';
}else{
echo 'failure';
}
 
mysqli_close($con);
?>
