<?php

include('dbConn.php');

// $recData = json_decode(file_get_contents('php://input'));

//Make sure that it is a POST request.
// if(strcasecmp($_SERVER['REQUEST_METHOD'], 'POST') != 0){
//     echo 'Request method must be POST!';
// }
$name=$_POST['name'];
$email=$_POST['email'];
$address=$_POST['address'];
$contact=$_POST['contact'];
$pwd=$_POST['pwd'];




$hashedPwd=password_hash($pwd,PASSWORD_DEFAULT);

//check for already existance of this email address
$sqlCheckDupMail="SELECT user_id FROM users WHERE email='$email'";

if($stmt=$conn->prepare($sqlCheckDupMail)){
    $stmt->execute();

    $result=$stmt->get_result();
    

    if($result->num_rows){
        $response["success"]=0;
        $response["data"]="";
        echo json_encode($response);
        return;
        
    }
   

   
}                  





// $sql="INSERT INTO users(name,email,pwd,address,phone_no)
//                   VALUES(:name,:email,:hashedPwd,:address,:contact)";
    
//worst way to insert data into table
//improvise it
$sql="INSERT INTO users(name,email,pwd,address,phone_no)
                  VALUES('$name','$email','$hashedPwd','$address','$contact')";

if($stmt=$conn->prepare($sql)){
    
    // $stmt->execute(array(':name'=>$name,':email'=>$email,':hashedPwd'=>$hashedPwd, ':address'=>$address, ':contact'=>$contact ));
    $stmt->execute();
    $result=$stmt->get_result();

    $response["success"]=1;
}                  

echo json_encode($response);