<?php

// Include the database connection file
include 'conn.php';

// Check if data is received via POST
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    // Get data from POST request
    $patientId = $_POST['patient_id'];
    $relation = $_POST['relation'];

    // Check if the patient ID already exists in the table
    $checkQuery = "SELECT * FROM relation WHERE patient_id = ?";
    $checkStmt = $conn->prepare($checkQuery);
    $checkStmt->bind_param("s", $patientId); // Change "i" to "s" for patient ID
    $checkStmt->execute();
    $checkResult = $checkStmt->get_result();

    if ($checkResult->num_rows > 0) {
        // Patient ID already exists, so don't insert new data
        echo "Data already exists for this patient ID";
    } else {
        // Prepare SQL statement to insert data into the relation table
        $insertQuery = "INSERT INTO relation (patient_id, relation) VALUES (?, ?)";
        $insertStmt = $conn->prepare($insertQuery);

        // Bind parameters
        $insertStmt->bind_param("ss", $patientId, $relation); // Change "is" to "ss"

        // Execute the statement
        if ($insertStmt->execute()) {
            echo "Data inserted successfully";
        } else {
            echo "Error: " . $insertQuery . "<br>" . $conn->error;
        }

        // Close statement
        $insertStmt->close();
    }
}

// Close connection
$conn->close();

?>
