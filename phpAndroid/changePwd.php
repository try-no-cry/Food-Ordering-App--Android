<?php

include('dbConn.php');



$email=$_POST['email'];
$pwd=$_POST['pwd'];
$newPwd=$_POST['newPwd'];
// $email="a@a.a";
// $pwd="123";
// $newPwd="1";

$hashedPwd=password_hash($newPwd,PASSWORD_DEFAULT);

$sql="SELECT pwd FROM users WHERE email='$email'";

if($stmt=$conn->prepare($sql)){
    $stmt->execute();
    
    $result=$stmt->get_result();

    $numOfRows=$result->num_rows;

    if($numOfRows>0){
        $row=$result->fetch_assoc();
        $pwdFromDB=$row['pwd'];

        if (password_verify($pwd, $pwdFromDB)) {
            // Success!
           

        //worst way to update data into table
        //improvise it
        $sql="UPDATE users 
            SET pwd='$hashedPwd'
            WHERE email='$email' "; //no two user will have same email id since we had set it as UNIQUE

        if($stmt=$conn->prepare($sql)){
            
            $stmt->execute();
            $result=$stmt->get_result();

            $response["success"]=1;
        }    
            
        $stmt->free_result();
        $stmt->close();

        $response["success"]=1;
        $response["data"]="";
            
        }


        else {
            // Invalid credentials
            $response["success"]=0;
             $response["data"]="";
        }
    }
    else {
        // Invalid credentials
        $response["success"]=0;
        $response["data"]=mysqli_error($conn);
    }
}
else{
    $response["success"]=0;
    $response["data"]=mysqli_error($conn);
}

echo json_encode($response);

