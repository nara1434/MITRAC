<?php
// Include your connection file
include 'conn.php';

// Initialize response array
$response = array();

// Check if patient_id and q1 to q8 values are received via POST
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    // Assuming you have received patient_id as a POST parameter
    $patient_id = $_POST['patient_id'];

    // Get today's date
    $today_date = date('Y-m-d');

    // Initialize an array to store the columns and values to be updated
    $update_values = array();

    // Assuming q1 to q8 values are received as POST parameters
    if (isset($_POST['q1'])) {
        $q1_value = $_POST['q1'];
        $update_values[] = "q1 = '$q1_value'";
    }

    if (isset($_POST['q2'])) {
        $q2_value = $_POST['q2'];
        $update_values[] = "q2 = '$q2_value'";
    }

    if (isset($_POST['q3'])) {
        $q3_value = $_POST['q3'];
        $update_values[] = "q3 = '$q3_value'";
    }

    if (isset($_POST['q4'])) {
        $q4_value = $_POST['q4'];
        $update_values[] = "q4 = '$q4_value'";
    }

    if (isset($_POST['q5'])) {
        $q5_value = $_POST['q5'];
        $update_values[] = "q5 = '$q5_value'";
    }

    if (isset($_POST['q6'])) {
        $q6_value = $_POST['q6'];
        $update_values[] = "q6 = '$q6_value'";
    }

    if (isset($_POST['q7'])) {
        $q7_value = $_POST['q7'];
        $update_values[] = "q7 = '$q7_value'";
    }

    if (isset($_POST['q8'])) {
        $q8_value = $_POST['q8'];
        $update_values[] = "q8 = '$q8_value'";
    }
    if (isset($_POST['q9'])) {
        $q9_value = $_POST['q9'];
        $update_values[] = "q9 = '$q9_value'";
    }
    if (isset($_POST['q10'])) {
        $q10_value = $_POST['q10'];
        $update_values[] = "q10 = '$q10_value'";
    }

    // Combine the update values into a string
    $update_string = implode(', ', $update_values);

    // Update the columns in your table for the specific patient and today's date
    $sql = "UPDATE screening2 SET $update_string WHERE patient_id = '$patient_id' AND `date` = '$today_date'";

    if (mysqli_query($conn, $sql)) {
        // Return success response
        $response['success'] = true;
        $response['message'] = "Records updated successfully for patient ID: $patient_id and today's date: $today_date";
    } else {
        // Return error response
        $response['success'] = false;
        $response['message'] = "Error updating records: " . mysqli_error($conn);
    }

    // Close the connection
    mysqli_close($conn);
} else {
    // If request method is not POST
    $response['success'] = false;
    $response['message'] = "Invalid request method";
}

// Return JSON response
header('Content-Type: application/json');
echo json_encode($response);
?>
