<?php
require("conn.php");
header("Content-Type: application/json");

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $json = file_get_contents('php://input');
    $data = json_decode($json, true);
    print_r($data);

    if (isset($_POST["mail"]) && isset($_POST["password"])) {
        require_once "validate.php";
        $username = validate($_POST["mail"]);
        $password = validate($_POST["password"]);
        $sql = "SELECT * FROM doctor WHERE mail = '$username' AND password = '$password'";
        $result = $conn->query($sql);

        if ($result->num_rows > 0) {
            $response = array('status' => 'success', 'message' => 'Login successful');
        } else {
            $response = array('status' => 'failure', 'message' => 'Invalid login credentials');
        }

        echo json_encode($response);
    } else {
        $response = array('status' => 'failure', 'message' => 'Missing username or password in the request');
        echo json_encode($response);
    }
} else {
    $response = array('status' => 'failure', 'message' => 'Invalid request method');
    echo json_encode($response);
}
?>
