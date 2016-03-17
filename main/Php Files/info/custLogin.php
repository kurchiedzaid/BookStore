<?php
define('HOST','localhost');
define('USER','root');
define('PASS','');
define('DB','bookStore');
 
$con = mysqli_connect(HOST,USER,PASS,DB);
 
$userName = $_POST['email'];
$password = $_POST['password'];
 
$sql = "select * from customers where email='$userName' and password='$password'";
 
$res = mysqli_query($con,$sql);
 
$check = mysqli_fetch_array($res);
 
if(isset($check)){
echo 'success';
}else{
echo 'failure';
}
 
mysqli_close($con);
?>
