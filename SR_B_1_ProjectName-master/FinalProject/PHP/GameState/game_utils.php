<?php

/**
 *
 */
class Game {
  private $connection;
  private $error = "Error inserting history into database.";
  private $error2 = "Error fetching history from database.";
<<<<<<< HEAD
  private $empty_string = "";
=======
>>>>>>> 8675a0457842ed078793a5487148c302debfd82b

  function __construct(){
    require_once 'DB_Connect.php';

    $data_base = new DB_Connect();
    $this->connection = $data_base->connect();
  }

  function __destruct() {
  }

    // function createGame($user1, $user2){
    //   $query =
    // }

  function updateBoard(){
    $id = "123";
    $query = $this->connection->prepare("SELECT history FROM game_history WHERE id = ?");
    $query->bind_param("s", $id);
    $result = $query->execute();
    $query->close();

    if($result){
        $row = $result->fetch_assoc();
        $sub = substr($row["history"], 6);
        return $sub;
    }
    else {
      return empty_string;
    }
  }

  function updateMoves($move){
    $id = "123";
    $query = $this->connection->prepare("SELECT history FROM game_history WHERE id = ?");
    $query->bind_param("s", $id);
    $result = $query->execute();
    $query->close();

    if($result){
        $row = $result->fetch_assoc();
        $history = $row["history"];
        $history = $history . $move;

        $query2 = $this->connection->prepare("INSERT INTO game_history(history) VALUES($history)");
        $result2 = $query2->execute();
        if($result2){
          return $result;
        }
        else{
          return $error;
        }
      }
    else {
      return $error2;
    }
  }
}

 ?>
