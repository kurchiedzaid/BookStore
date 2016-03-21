
<?php
define('HOST','localhost');
define('USER','root');
define('PASS','');
define('DB','bookstore');
 
$con = mysqli_connect(HOST,USER,PASS,DB);
 
$sql = "select * from customers";
 
$res = mysqli_query($con,$sql);
 
$result = array();
 
while($row = mysqli_fetch_array($res)){
array_push($result,
array('id'=>$row[0],
'name'=>$row[1],
'email'=>$row[2],
'password'=>$row[3],
'address'=>$row[4],
'country'=>$row[5],
'phone'=>$row[6],
'payment'=>$row[7]


));
}
 
echo json_encode(array("result"=>$result));
 
mysqli_close($con);
 
?>
