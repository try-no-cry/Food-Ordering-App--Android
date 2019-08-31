<?php

include('dbConn.php');


$resArray=array();
$response=array();

// $sql=$_POST['sql'];
$searchQuery=$_POST['searchQuery'];
// $searchQuery="food";
$sql=" SELECT F.imageUrl ,S.location,S.contact,F.name AS foodName,S.name AS shopName,F.pricePerKg
              FROM foods F  JOIN relfoodstore R
              ON F.food_id=R.food_id
              JOIN stores S 
               ON R.store_id=S.store_id

               WHERE F.name LIKE  '%$searchQuery%'
                   OR S.name LIKE  '%$searchQuery%'
                   OR S.location LIKE  '%$searchQuery%'
                   OR S.contact LIKE  '%$searchQuery%'
                   OR S.name  LIKE  '%$searchQuery%'
                   OR F.pricePerKg LIKE  '%$searchQuery%'
               
               "; 

               //add any other search property for added columns
           
                  


if($stmt=$conn->prepare($sql)){

    $stmt->execute();
    $result=$stmt->get_result();

    $num_of_rows=$result->num_rows;

    while($row=$result->fetch_assoc()){


        $resArray['imageUrl']=$row['imageUrl'];
        $resArray['location']=$row['location'];
        $resArray['contact']=$row['contact'];
        $resArray['foodName']=$row['foodName'];
        


        $res[]=$resArray;
    }

    $stmt->free_result();
    $stmt->close();

    $response["success"]=1;
    $response["data"]=$res;
}

else {
    $response["success"]=0;
    $response["data"]=mysqli_error($conn);
}

echo json_encode($response);