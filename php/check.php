<?php
require("conn.php"); // Include your database connection script

// Check if the request is a POST request
if ($_SERVER["REQUEST_METHOD"] === "POST") {
    // Add any necessary parameters for fetching data
    // For example, you might have a d_id parameter in the POST request
    $d_id = $_POST['d_id'];

    // Fetch data from your database (adjust table name as needed)
    $sql = "SELECT * FROM doctor_profile WHERE d_id='$d_id'";
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
