<?php

include('dbConn.php');

$name=$_POST['name'];
$email=$_POST['email'];
$address=$_POST['address'];
$contact=$_POST['contact'];
$pwd=$_POST['pwd'];

$hashedPwd=password_hash($pwd,PASSWORD_DEFAULT);

//check for already existance of this email address
$sql="INSERT INTO users(name,email,pwd,address,phone_no)
                  VALUES(:name,:email,:hashedPwd,:address,:contact);";

if($stmt->prepare($sql)){
    $stmt->execute(array(':name'=>$name,':email'=>$email,':hashedPwd'=>$hashedPwd, ':address'=>$address, ':contact'=>$contact ));

    $result=$stmt->get_result();

    echo    $result;
}                  