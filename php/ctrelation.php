<?php

// Include the database connection file
include 'conn.php';

// Check if data is received via POST
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    // Get the patient ID from the POST request
    $patientId = $_POST['patient_id'];

    // Prepare SQL statement to fetch patient relation
    $sql = "SELECT relation FROM relation WHERE patient_id = ?";

    // Prepare and bind parameters
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("s", $patientId); // Assuming patient_id is a string, change "i" to "s"

    // Execute the statement
    if ($stmt->execute()) {
        // Bind result variables
        $stmt->bind_result($patientRelation);

        // Fetch the result
        if ($stmt->fetch()) {
            // Patient relation found, create JSON response
            $response = array("relation" => $patientRelation);
            echo json_encode($response);
        } else {
            // Patient not found
            echo "Patient not found";
        }
    } else {
        // Error executing SQL statement
        echo "Error executing SQL statement: " . $stmt->error;
    }

    // Close statement
    $stmt->close();
}

// Close connection
$conn->close();

?>
