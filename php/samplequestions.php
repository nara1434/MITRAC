<?php
require("conn.php");
header("Content-Type: application/json");

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Assuming you are sending data as form-urlencoded
    $patientId = $_POST["pid"]; // Change this variable name to match your column name
    $totalMarks = $_POST["totalMarks"];
    
    if (!empty($patientId) && !empty($totalMarks)) {
        // Use the current date in the SQL query
        $sql = "INSERT INTO sample_questions (patient_id, totalMarks, date) VALUES ('$patientId', '$totalMarks', CURRENT_DATE)";

        if (mysqli_query($conn, $sql)) {
            // Assuming you want to send a success message as a response
            $response = array('status' => 'success', 'message' => 'Data processed successfully');
            echo json_encode($response);
        } else {
            $response = array('status' => 'failure', 'message' => 'Failed to execute query', 'details' => mysqli_error($conn));
            echo json_encode($response);
        }
    } else {
        $response = array('status' => 'failure', 'message' => 'Patient ID or Total Marks not received in the request');
        echo json_encode($response);
    }
} else {
    $response = array('status' => 'failure', 'message' => 'Invalid request method');
    echo json_encode($response);
}
?>
