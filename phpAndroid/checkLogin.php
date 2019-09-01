<?php
include('dbConn.php');


$email=$_POST['email'];
$pwd=$_POST['pwd'];
    

$response=array();
$resArray=array();




$sql="SELECT * FROM users WHERE email='$email'";

if($stmt=$conn->prepare($sql)){
    $stmt->execute();
    
    $result=$stmt->get_result();

    $numOfRows=$result->num_rows;

    if($numOfRows>0){
        $row=$result->fetch_assoc();
        $pwdFromDB=$row['pwd'];

        if (password_verify($pwd, $pwdFromDB)) {
            // Success!
            $resArray['name']=$row['name'];
            $resArray['email']=$email;
            $resArray['pwd']=$pwd;
            $resArray['address']=$row['address'];
            $resArray['contact']=$row['phone_no'];  

            $res[]=$resArray;

            
        $stmt->free_result();
        $stmt->close();

        $response["success"]=1;
        $response["data"]=$res;
            
        }


        else {
            // Invalid credentials
            $response["success"]=0;
             $response["data"]=mysqli_error($conn);
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