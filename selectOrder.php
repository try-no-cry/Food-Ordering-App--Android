<?php
//CAN BE Used for inseert,update,delete
include('dbConn.php');

$result = array();
$resArray = array();
$response = array();


$sql=$_POST['sql'];

//   $sql=
//  "INSERT INTO orders(foodcard_id,users_id,quantity,supplyAddress,totalPrice,orderStatus)
//   VALUES(22,23,1.0,'chembur me kar de',100.0,'on the way')";
// $sql="SELECT * 15FROM orders ";
// $sql="SELECT * FROM orders";

if($stmt=$conn->prepare($sql)){

  $stmt->execute();
  $result=$stmt->get_result();

   /* Get the number of rows */
   $num_of_rows = $result->num_rows;
  

   while ($row = $result->fetch_assoc()) {

    $resArray['id']=$row['order_id'];
    $resArray['foodcard_id']=$row['foodcard_id'];
    $resArray['users_id']=$row['users_id'];
    $resArray['quantity']=$row['quantity'];
    $resArray['supplyAddress']=$row['supplyAddress'];
    $resArray['totalPrice']=$row['totalPrice'];
    $resArray['orderStatus']=$row['orderStatus'];
    $resArray['orderDate']=$row['orderDate'];
    $resArray['orderTime']=$row['orderTime'];

///deleted 'foodName' from orders Table..check if any error du to this action
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



   
