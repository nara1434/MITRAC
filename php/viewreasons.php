<?php
require("conn.php");

// Check if the request is a POST request
if ($_SERVER["REQUEST_METHOD"] === "POST") {
    // Assuming you receive the patient_id via POST
    $patient_id = $_POST['patient_id'];

    // Fetch data from your database for the specified patient ID, ordered by the most recent date
    $sql = "SELECT reasons FROM reasons WHERE patient_id = '$patient_id' ORDER BY time1 DESC LIMIT 1";
    $result = $conn->query($sql);

    if ($result->num_rows > 0) {
        // Fetch and return data as JSON
        $row = $result->fetch_assoc();
        header('Content-Type: application/json');
        echo json_encode($row);
    } else {
        // No data found
        $response = array('status' => 'Error', 'message' => 'No reasons found for the specified patient ID');
        header('Content-Type: application/json');
        echo json_encode($response);
    }
} else {
    // Invalid request method
    $response = array('status' => 'Error', 'message' => 'Invalid request method');
    header('Content-Type: application/json');
    echo json_encode($response);
}
?>
