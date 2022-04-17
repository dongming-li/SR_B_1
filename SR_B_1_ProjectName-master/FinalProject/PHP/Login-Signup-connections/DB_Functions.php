<?php
/**
 * Created by PhpStorm.
 * User: Acer
 * Date: 10/5/2017
 * Time: 9:41 PM
 */

class DB_Functions {

    private $conn;

    // constructor
    function __construct() {
        require_once 'DB_Connect.php';
        // connecting to database

        /**
         * create new connection to database
         */
        $db = new Db_Connect();
        $this->conn = $db->connect();
    }

    // destructor
    function __destruct() {

    }

    /**
     * Storing new user
     * returns user details
     */
    public function storeUser($name, $email, $password) {
        // $hash = $this->hashFunction($password);
        // $encrypted_password = $hash["encrypted"]; // encrypted password
        // $salt = $hash["salt"]; // salt

        $stmt = $this->conn->prepare("INSERT INTO user_accounts (username ,email, password) VALUES (?, ?, ?)");
        $stmt->bind_param("sss",$name, $email, $password);
        $result = $stmt->execute();
        $stmt->close();

        // check for successful store
        if ($result) {
            $stmt = $this->conn->prepare("SELECT username, email FROM user_accounts WHERE email = ?");
            $stmt->bind_param("s", $email);
            $stmt->execute();
            $stmt-> bind_result($token2,$token3);

            while ( $stmt-> fetch() ) {
               $user["username"] = $token2;
               $user["email"] = $token3;
            }
            $stmt->close();

            return $user;
        } else {
            return false;
        }
    }

    /**
     * Get user by email and password
     */
    public function getUser($email, $password) {

        $stmt = $this->conn->prepare("SELECT username, email, password, salt FROM user_accounts WHERE email = ?");

        $stmt->bind_param("s", $email);

        if ($stmt->execute()) {
            $stmt-> bind_result($token2,$token3, $token4, $token5);

            while ( $stmt-> fetch() ) {
               $user["username"] = $token2;
               $user["email"] = $token3;
               $user["password"] = $token4;
               $user["salt"] = $token5;

            }
            $stmt->close();

            // verifying user password
           // $salt = $token5;
            //$encrypted_password = $token4;
           // $hash = $this->checkHashFunction($salt, $password);
            // check for password equality
            //if ($encrypted_password == $hash) {
                // user authentication details are correct
                return $user;
            //}
        } else {
            return false;
        }
    }

    /**
     * Check user is existed or not
     */
    public function userExists($email) {
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


    public function hashFunction($password) {

        $salt = sha1(rand());
        $salt = substr($salt, 0, 10);
        $encrypted = base64_encode(sha1($password . $salt, true) . $salt);
        $hash = array("salt" => $salt, "encrypted" => $encrypted);
        return $hash;
    }

    public function checkHashFunction($salt, $password) {

        $hash = base64_encode(sha1($password . $salt, true) . $salt);

        return $hash;
    }

}
?>
