<?php
//CAN BE Used for inseert,update,delete
include('dbConn.php');

$result = array();
$resArray = array();
$response = array();


$sql=$_POST['sql'];

if($stmt=$conn->prepare($sql)){

    $stmt->execute();
    $result=$stmt->get_result();

    /* Get the number of rows */
   $num_of_rows = $result->num_rows;

   while ($row = $result->fetch_assoc()) {

    $resArray['id']=$row['food_id'];
    $resArray['imageUrl']=$row['imageUrl'];
    $resArray['foodName']=$row['foodName'];
    $resArray['foodPrice']=$row['foodPrice'];
    $resArray['address']=$row['address'];
    $resArray['any_other_info']=$row['any_other_info'];

    //handle the problem of foreign key to maintain its integrity
    $res[]=$resArray;
   
   }

   $stmt->free_result();

   /* close statement */
   $stmt->close();


   $response["success"] = 1;
   $response["data"] = $res;


}

else{
	//Some error while fetching data
	$response["success"] = 0;
  $response["message"] = mysqli_error($conn);
  // echo mysqli_error($conn);
		
	
}

//Display JSON response
 echo json_encode($response);






