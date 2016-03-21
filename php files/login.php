<?php
define('HOST','localhost');
define('USER','root');
define('PASS','');
define('DB','bookStore');
 
$con = mysqli_connect(HOST,USER,PASS,DB);
 
$userName = $_POST['userName'];
$password = $_POST['password'];
 
$sql = "select * from admin where userName='$userName' and password='$password'";
 
$res = mysqli_query($con,$sql);
 
$check = mysqli_fetch_array($res);
 
if(isset($check)){
echo 'success';
}else{
echo 'failure';
}
 
mysqli_close($con);
?>
