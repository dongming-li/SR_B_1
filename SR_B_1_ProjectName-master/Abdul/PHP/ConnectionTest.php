<?php



$servername = "mysql.cs.iastate.edu";
$username = "dbu309srb1";
$password = "4TqVz2qS";
$dbname = "db309srb1";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);


        if($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
    }else{
        echo "Hello!";
    }



?>