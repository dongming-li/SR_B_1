<?php
/**
 * Created by PhpStorm.
 * User: Acer
 * Date: 10/5/2017
 * Time: 11:33 PM
 */
error_reporting(E_ALL);
ini_set('display_errors', 1);

require_once 'DB_Functions.php';
$db = new DB_Functions();

// json response array
$response = array("error" => FALSE);

if (isset($_POST['username']) && isset($_POST['email']) && isset($_POST['password'])) {

    // receiving the post params
    $username = $_POST['username'];
    $email = $_POST['email'];
    $password = $_POST['password'];

    error_log($username, 0);
    error_log($email, 0);
    error_log($password, 0);

    // check if user is already existed with the same email
    if ($db->userExists($email)) {
        // user already existed
        $response["error"] = TRUE;
        $response["error_msg"] = "User already existed with " . $email;
        echo json_encode($response);
    } else {
        // create a new user
        $user = $db->storeUser($username, $email, $password);
        if(!$user){
            error_log("registration failed!");
        }
        if ($user) {
            // user stored successfully
            $response["error"] = FALSE;
            $response["user"]["username"] = $user["username"];
            $response["user"]["email"] = $user["email"];
            echo json_encode($response);
        } else {
            // user failed to store
            $response["error"] = TRUE;
            $response["error_msg"] = "Unknown error occurred in registration!";
            echo json_encode($response);
        }
    }
} else {
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters (name, email or password) is missing!";
    echo json_encode($response);
}
?>
