<?php
// define('MYSQL_ASSOC',MYSQLI_ASSOC);

$hostname="localhost";
$username="root";
$pwd="";
$db="newbiz";

   $conn=mysqli_connect($hostname,$username,$pwd,$db);

      
   if(! $conn ) {
    echo ('Could not connect: '.mysqli_error($conn));
 }

