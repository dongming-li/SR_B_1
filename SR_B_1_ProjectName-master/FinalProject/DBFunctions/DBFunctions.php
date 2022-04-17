<?php
private class Connect {
  private connection;

  //This function connects to the MySQL Database
  function connect(){
    require_once 'config.php';
    $this->connection = new mysqli(HOST, USER, PASS, SCHEMA);

    return $this->connection
  }
}

class HelperMethods{
    private connection;

    function __construct(){
      $database =  new Connect();
      $this->connection = database->connect();
    }

    function __destruct(){
    }

    public function createUser($username, $email, $password){

      $hash = $this->hash("sha256", $password);
      $query = $this->connection->prepare("INSERT INTO user_accounts(username, password, email, created_at) VALUES(?, ?, ?, NOW())");
      $query->bind_param("sss", $username, $hash, $email);
      $return_msg = $query->execute();
      $query->close();

      if($return_msg){
        $query = $this->connection->prepare("SELECT * FROM user_accounts WHERE username = ?");
        $query->bind_param("s", $username);
        $query->execute();
        $user = $query->get_result()->fetch_assoc();
        $query->close();

        return $user;
      }
      else {
        return false;
      }
    }

    public function getUserByUsernamePassword($username, $password) {
      $query = $this->conn->prepare("SELECT * FROM user_accounts WHERE username = ?");

          $stmt->bind_param("s", $username);

          if ($query->execute()) {
              $user = $query->get_result()->fetch_assoc();
              $query->close();

              // verifying user password
              $hashed_password = $user["password"]
              $hash = $this->hash("sha256", $password);

              //Checks to see if passwords are the same if they are the user
              //details are returned
              if ($encrypted_password == $hash) {
                  return $user;
              }
          } else {
              return NULL;
          }
    }

    public function getUserByEmailPassword($email, $password){
      $query = $this->conn->prepare("SELECT * FROM user_accounts WHERE email = ?");

          $stmt->bind_param("s", $email);

          if ($query->execute()) {
              $user = $query->get_result()->fetch_assoc();
              $query->close();

              // verifying user password
              $hashed_password = $user["password"]
              $hash = $this->hash("sha256", $password);

              // check for password equality
              if ($encrypted_password == $hash) {
                  // user authentication details are correct
                  return $user;
              }
          } else {
              return NULL;
          }
    }

    public function userExist($email) {
        $query = $this->conn->prepare("SELECT email from user_accounts WHERE email = ?");
        $query->bind_param("s", $email);
        $query->execute();
        $query->store_result();

        //Checks to see if there is a user and returns true if there is false otherwise
        if ($query->num_rows > 0) {
            $query->close();
            return true;
        }
        else {
            $query->close();
            return false;
        }
    }
}
 ?>
