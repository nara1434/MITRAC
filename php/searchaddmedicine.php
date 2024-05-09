<?php
require_once "conn.php";
header("Content-Type: application/json");

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $q1 = "SELECT patient_id FROM patient_details";
    $result = mysqli_query($conn, $q1);

    $patientList = array(); // Initialize an array to hold patient IDs

    // Fetch data from the database and store it in the array
    while ($row = mysqli_fetch_assoc($result)) {
        $patientID = $row['patient_id'];
        $currentDate = date("Y-m-d");

        // Check if a record already exists for the patient and date
        $checkRecordQuery = "SELECT COUNT(*) as recordCount FROM streak WHERE id = '$patientID' AND date = '$currentDate'";
        $resultCheckRecord = $conn->query($checkRecordQuery);
        $rowCheckRecord = $resultCheckRecord->fetch_assoc();
        $recordCount = $rowCheckRecord['recordCount'];

        // Insert a new record only if a record doesn't already exist for the patient and date
        if ($recordCount == 0) {
            $insertNewRecordQuery = "INSERT INTO streak (id, total_count, date) VALUES ('$patientID', 0, '$currentDate')";

            if ($conn->query($insertNewRecordQuery) === TRUE) {
                echo "Record inserted successfully for patient ID: $patientID\n";
            } else {
                echo "Error inserting record for patient ID: $patientID - " . $conn->error . "\n";
            }
        } else {
            echo "Record already exists for patient ID: $patientID and date: $currentDate\n";
        }

        // Add patient ID to the array
        $patientList[] = $patientID;
    }

    // Convert the PHP array to JSON format
    $jsonPatientList = json_encode($patientList);

    // Output the JSON data
    echo $jsonPatientList;
}
?>
