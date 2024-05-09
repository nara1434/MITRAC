<?php
require_once "conn.php";
require("conn.php");

// SQL query to fetch patient information
$sql = "SELECT patient_id, name, sex, mobile_number, img FROM patient_details";

$result = $conn->query($sql);

if ($result->num_rows > 0) {
    $data = array();
    while ($row = $result->fetch_assoc()) {
        $data[] = $row;
    }
    echo json_encode($data); // Return JSON response 
} else {
    echo "0 results";
}
$conn->close();

?>