<?php
///CAN BE Used for inseert,update,delete'
include('dbConn.php');


$sql=$_POST['sql'];
  
$result=mysqli_query($conn,$sql);
if ($result) {

    echo "Done.";
}
else echo mysqli_error($conn);  