<?php

include('dbConn.php');


$name=$_POST['name'];
$email=$_POST['email'];
$address=$_POST['address'];
$contact=$_POST['contact'];
$pwd=$_POST['pwd'];

               

// $response["success"]=0;
// $response["data"]="";
// echo json_encode($response);




//worst way to update data into table
//improvise it
$sql="UPDATE users 
     SET name='$name', address='$address', phone_no='$contact'
     WHERE email='$email' "; //no two user will have same email id since we had set it as UNIQUE

if($stmt=$conn->prepare($sql)){
    
    // $stmt->execute(array(':name'=>$name,':email'=>$email,':hashedPwd'=>$hashedPwd, ':address'=>$address, ':contact'=>$contact ));
    $stmt->execute();
    $result=$stmt->get_result();

    $response["success"]=1;
}                  

echo json_encode($response);