<?php
require_once 'User_Info';
$db = new User_Info();

// json response array
$response = array("error" => FALSE);

if (isset($_POST['email']) && isset($_POST['password'])) {

    // receiving the post params
    $email = $_POST['email'];
    $password = $_POST['password'];

    // get the user by email and password
    $user = $db->UserAuthentication($email, $password);

    if ($user != false) {
        // use is found
        $response["error"] = FALSE;

        //may need the following line
// $response["uid"] = $user["id"];

        $response["user"]["username"] = $user["username"];
        $response["user"]["email"] = $user["email"];
        echo json_encode($response);
    } else {
        // user is not found with the credentials
        $response["error"] = TRUE;
        $response["error_msg"] = "Please try again!";
        echo json_encode($response);
    }
} else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Email or password is missing!";
    echo json_encode($response);
}
?>