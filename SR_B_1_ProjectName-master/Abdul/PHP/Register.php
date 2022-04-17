<?php

require_once 'User_Info.php';
$db = new User_Info();

// json response array
$response = array("error" => FALSE);

if (isset($_POST['name']) && isset($_POST['email']) && isset($_POST['password'])) {

    // receiving the post params
    $username = $_POST['name'];
    $email = $_POST['email'];
    $password = $_POST['password'];


    // check if user is already existed with the same email
    if ($db->CheckUserExisting($email)) {
        // user already existed
        $response["error"] = TRUE;
        $response["error_msg"] = "User already existed with " . $email;
        echo json_encode($response);
    } else {
        // create a new user
        $user = $db->GetUserInfo($username, $email, $password);
        if ($user) {
            // user stored successfully
            $response["error"] = FALSE;
            $response["user"]["name"] = $user["name"];
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
    $response["error_msg"] = "Missing parameter";
    echo json_encode($response);
}
?>