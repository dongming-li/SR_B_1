
<?php


require_once 'DB_Connect.php';
$db = new Db_Connect();
$conn = $db->connect();


if (isset($_POST['move'])) {

$move = $_POST['move'];
error_log($move,0);

$id = "33";


$stmt = "INSERT INTO game_history(id, history) VALUES('$id', '$move')";

if(mysqli_query($conn, $stmt)){
    echo "Records inserted successfully.";
} else{
    echo "ERROR";
}

mysqli_close($conn);
// $stmt = $conn->prepare("INSERT INTO game_history(id, history) VALUES($id, ?)");
// $stmt->bind_param("s",$move);
// $result = $stmt->execute();
// $stmt->close();

}else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters email or password is missing!";
    mysqli_close($conn);

}

?>