<?php
class User_Info {


 private $conn;

    // constructor
    function __construct() {
        require_once 'DB_Connect.php';
        // connecting to database
        $db = new DB_Connect();
        $this->conn = $db->connect();
    }

    // destructor
    function __destruct() {

    }

//used to store user info when signing up
    public function GetUserInfo($username, $email, $password) {
        $hash = $this->hashFunction($password);
        $encrypted_password = $hash["encrypted"]; // encrypted password
        $salt = $hash["salt"]; // salt

        $stmt = $this->conn->prepare("INSERT INTO user_accounts(username, encrypted_password, email, salt) VALUES(?, ?, ?, ?)");
        $stmt->bind_param("ssss", $username, $encrypted_password,$email);
        $result = $stmt->execute();
        $stmt->close();

        // check for successful store
        if ($result) {
            $stmt = $this->conn->prepare("SELECT name,encrypted_password, email, salt FROM user_accounts WHERE email = ?");
            $stmt->bind_param("s", $email);
            $stmt->execute();
            $stmt-> bind_result($token2,$token3,$token4, $token5);

            while ( $stmt-> fetch() ) {
               $user["username"] = $token2;
               $user["email"] = $token4;

            }
            $stmt->close();
            return $user;
        } else {
          return false;
        }
    }


//encode user password
    public function hashFunction($password) {

        $salt = sha1(rand());
        $salt = substr($salt, 0, 10);
        $encrypted = base64_encode(sha1($password . $salt, true) . $salt);
        $hash = array("salt" => $salt, "encrypted" => $encrypted);
        return $hash;
    }


//Authenticate user login
    public function UserAuthentication($email, $password) {

        $stmt = $this->conn->prepare("SELECT name, encrypted_password, email  FROM user_accounts WHERE email = ?");

        $stmt->bind_param("s", $email);

        if ($stmt->execute()) {
            $stmt-> bind_result($token2,$token3,$token4,$token5);

            while ( $stmt-> fetch() ) {
               $user["name"] = $token2;
               $user["email"] = $token4;
               $user["encrypted_password"] = $token3;
               $user["salt"] = $token5;
            }

            $stmt->close();

            // verifying user password
            $salt = $token5;
            $encrypted_password = $token3;
            $hash = $this->CheckHashFunction($salt, $password);
            // check for password equality
            if ($encrypted_password == $hash) {
                // user authentication details are correct
                return $user;
            }
        } else {
            return NULL;
        }
    }


//check if the hashed password is correct
public function checkHashFunction($salt, $password) {
        $hash = base64_encode(sha1($password . $salt, true) . $salt);
        return $hash;
    }


//check if the user exists in the system
public function CheckUserExisting($email) {
        $stmt = $this->conn->prepare("SELECT email from user_accounts WHERE email = ?");

        $stmt->bind_param("s", $email);

        $stmt->execute();

        $stmt->store_result();

        if ($stmt->num_rows > 0) {
            // user existed
            $stmt->close();
            return true;
        } else {
            // user not existed
            $stmt->close();
            return false;
        }
    }

}

?>