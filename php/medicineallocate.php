<?php
require "conn.php";

// Retrieve the JSON data from the request
$data = json_decode(file_get_contents("php://input"), true);

// Check if records exist for the current date
$currentDate = date("Y-m-d");

// Delete records that are not in the current date
$deleteRecordsQuery = "DELETE FROM medicine_timings WHERE Date != '$currentDate'";
mysqli_query($conn, $deleteRecordsQuery);

// Insert new records
foreach ($data as $medicine) {
    $id = $medicine['id'];
    $medicine_name = $medicine['medicine_name'];
    $course_name = $medicine['dose'];
    $course_duration = $medicine['session'];

    // Adjusted the SQL query for insertion
    $sql = "INSERT INTO medicine_timings (id, Medicine_name, Dose, Type, Date)
            VALUES ('$id', '$medicine_name', '$course_name', '$course_duration', '$currentDate')";

    if ($conn->query($sql) === TRUE) {
        $response = array("status" => "success");
    } else {
        $response = array("status" => "error", "message" => $conn->error);
    }
}

// Return the response as JSON
header('Content-Type: application/json');
echo json_encode($response);

// Close the database connection
$conn->close();
?>
