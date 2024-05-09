<?php
require("conn.php"); // Include your database connection script

// Check if the request is a POST request
if ($_SERVER["REQUEST_METHOD"] === "POST") {
    // Add any necessary parameters for fetching data
    // For example, you might have a d_id parameter in the POST request
    $c_id = $_POST['c_id'];

    // Fetch data from your database (adjust table name as needed)
    $sql = "SELECT * FROM caretaker_profile WHERE c_id='$c_id'";
    $result = $conn->query($sql);

    if ($result->num_rows > 0) {
        // Fetch and return data as JSON
        $row = $result->fetch_assoc();
        header('Content-Type: application/json');   
        echo json_encode($row);
    } else {
        // No data found
        $response = array('status' => 'Error', 'message' => 'No data found');
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
