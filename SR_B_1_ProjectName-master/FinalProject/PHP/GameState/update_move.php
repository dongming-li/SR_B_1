
<?php


require_once 'DB_Connect.php';
$db = new Db_Connect();
$conn = $db->connect();


if (isset($_POST['id'])) {

	$id = $_POST['id'];



	$stmt = $conn->prepare("SELECT history FROM game_history WHERE id = ?");
	$stmt->bind_param("s", $id);


	if ($stmt->execute()){

	$stmt-> bind_result($token2);

	while ( $stmt-> fetch() ) {
		$response["user"]["history"] = $token2;
	}
	$stmt->close();
}

	echo json_encode($response);

}else {
    // required post params is missing
	$response["error"] = TRUE;
	$response["error_msg"] = "Required parameters email or password is missing!";
	echo json_encode($response);

}

?>