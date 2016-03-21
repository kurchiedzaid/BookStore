
<?php
define('HOST','localhost');
define('USER','root');
define('PASS','');
define('DB','bookstore');
 
$con = mysqli_connect(HOST,USER,PASS,DB);
 
$sql = "select * from books";
 
$res = mysqli_query($con,$sql);
 
$result = array();
 
while($row = mysqli_fetch_array($res)){
array_push($result,
array('id'=>$row[0],
'title'=>$row[1],
'author'=>$row[2],
'price'=>$row[3],
'stock'=>$row[4],
'description'=>$row[5],
'type'=>$row[6],
'dateAdded'=>$row[7]


));
}
 
echo json_encode(array("result"=>$result));
 
mysqli_close($con);
 
?>
