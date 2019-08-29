<?php
//CAN BE Used for inseert,update,delete
include('dbConn.php');

// $result = array();
  
$resArray = array();
$response = array();


// $sql=$_POST['sql'];

//query to fill recycler view of MyOrder will be fi red from here
$sql="SELECT imageUrl,foodName,orderDate,order_id,orderStatus 
             from orders O,foodcard F
             WHERE O.foodcard_id=F.foodcard_id";

if($stmt=$conn->prepare($sql)){

    $stmt->execute();
    $result=$stmt->get_result();

    /* Get the number of rows */
   $num_of_rows = $result->num_rows;

   while ($row = $result->fetch_assoc()) {

    $resArray['imageUrl']=$row['imageUrl'];
    $resArray['foodName']=$row['foodName'];
    $resArray['orderDate']=$row['orderDate'];
    $resArray['order_id']=$row['order_id'];
    $resArray['orderStatus']=$row['orderStatus'];

     //handle the problem of foreign key to maintain its integrity
     $res[]=$resArray;

   }

   $stmt->free_result();

   /* close statement */
   $stmt->close();

   $response["success"] = 1;
   $response["data"] = $res;
  //  echo json_encode($response);

}
else{
	//Some error while fetching data
	$response["success"] = 0;
  $response["message"] = mysqli_error($conn);
  // echo mysqli_error($conn);
		
	
}

//Display JSON response
 echo json_encode($response);

