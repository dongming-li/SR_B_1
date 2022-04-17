
<<?php

        require_once 'DB_Connect.php';
        // connecting to database
        $db = new Db_Connect();
        $conn = $db->connect();
        //var_dump($conn);

        if($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
    }else{
        echo "Hello!";
    }


    // destructor
    function __destruct() {

    }

// //public function newUser($username, $email,$password){
//         $hash = $this->hashFunction($password);
//         $pass = $hash["encrypted"]; //encrypted password
//         $salt = $hash["salt"];  //salt
    // receiving the post params
         //$stmt = $conn;
    $name = $_POST['username'];
    $email = $_POST['email'];
    $password = $_POST['password'];
        $stmt = "INSERT INTO user_accounts(username, email,password) VALUES('$name', '$email', '$password')";
        //var_dump($stmt);
        if(mysqli_query($conn, $stmt)){
    echo "Records inserted successfully.";
} else{
    echo "ERROR: Could not able to execute $sql. ";
}


mysqli_close($conn);

        // $result = $stmt->execute();
        // $stmt->close();


        // // check for successful store
        // if ($result) {
        //     $stmt = $conn->prepare("SELECT * FROM user_accounts WHERE email = ?");
        //     $stmt->bind_param("s", $email);
        //     $stmt->execute();
        //     $user = $stmt->get_result()->fetch_assoc();
        //     $stmt->close();

    //         return $user;
    //     } else {
    //         return false;
    //     }
    // }

    ?>