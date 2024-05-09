<?php
// Assume you have a valid database connection stored in $conn
require("conn.php");
header("Content-Type: application/json");

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $answer = $_POST["answer"];
    $patientId = $_POST["patient_id"];

    // Convert the answer to a string
    $answer = $answer ? 'true' : 'false';

    // Insert values into the database
    $sql = "INSERT INTO questions (patient_id, selected_column) VALUES ('$patientId', '$answer')";

    if ($conn->query($sql) === TRUE) {
        echo "Record inserted successfully";
    } else {
        echo "Error: " . $sql . "<br>" . $conn->error;
    }
}
?>
