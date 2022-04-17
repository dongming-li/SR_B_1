<?php
/**
 * Created by PhpStorm.
 * User: Acer
 * Date: 10/5/2017
 * Time: 9:52 PM
 */

require_once 'DB_Functions.php';
$db = new DB_Functions();

// json response array
$response = array("error" => FALSE);

if (isset($_POST['email']) && isset($_POST['password'])) {

    // receiving the post params
    $email = $_POST['email'];
    $password = $_POST['password'];

    error_log($email, 0);
    error_log($password, 0);

    // get the user by email and password
    $user = $db->getUser($email, $password);

    if ($user != false) {
        // use is found
        $response["error"] = FALSE;
        $response["user"]["username"] = $user["username"];
        $response["user"]["email"] = $user["email"];
        echo json_encode($response);
    } else {
        // user is not found with the credentials
        $response["error"] = TRUE;
        $response["error_msg"] = "Login credentials are wrong. Please try again!";
        echo json_encode($response);
    }
} else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters email or password is missing!";
    echo json_encode($response);
}



?>
