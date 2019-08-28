<?php

include('dbConn.php');


$resArray=array();
$response=array();

// $sql=$_POST['sql'];
// $sql=" SELECT S.imageUrl,S.location,S.contact,F.name 
//               FROM stores S,foods F,relfoodstore R
//               WHERE R.st "                                   //complete the query

if($stmt=$conn->prepare($sql)){

    $stmt->execute();
    $result=$stmt->get_result();

    $num_of_rows=$result->num_rows;

    while($row=$result->fetch_assoc()){


        $resArray['']=$row[''];
    }
}

