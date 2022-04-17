<?php
/**
 *
 */
class DB_ChatFunctions{
  private $connection;

  function __construct(argument){
    require_once './Login-Signup-connections/DB_Connect.php';
    $data_base = new DB_Connect();
    $this.connection = $data_base->connect();
  }

  function __destruct(){

  }

  public function userChats($user){
    $query = $this->connection->prepare("SELECT user1, user2 FROM chat_logs WHERE user1 = "?" OR user2 = "?"");
    $query->bind_param("ss", $user, $user);

    if($query->execute()){
      while($query->fetch()){
        $query->bind_result($user1, $user2);
        if($user == $user1){
          $chat_partner = $user2;
        }
        else {
          $chat_partner = $user1;
        }
        $chats[] = $chat_partner;
      }
      $query->close();
      return $chats;
    }
    else {
      return $emptyArray;
    }
  }

  public function userLogs($user1, $user2){

  }

  public function appendChat($user1, $user2, $msg){

  }
}

 ?>
