<?php
// Include the database connection file
include 'conn.php';

// Prepare the message and current date
$message = $_POST["message"];
$currentDate = date("Y-m-d");

// Prepare the SQL query, excluding the ID column
$sql = "INSERT INTO notification (message, date) VALUES ('$message', '$currentDate')";

// Execute the query
if ($conn->query($sql) === TRUE) {
    // Prepare success response
    $response = array(
        "status" => "success",
        "message" => "New record created successfully"
    );
} else {
    // Prepare error response
    $response = array(
        "status" => "error",
        "message" => "Error: " . $sql . "<br>" . $conn->error
    );
}

// Convert response to JSON format
echo json_encode($response);

// Close the connection (optional if you want to close it explicitly)
$conn->close();
?>
