<?php

include 'conn.php';

// Calculate the sum of 'true' and subtract the sum of 'false' for the most recent 10 rows
$sql = "SELECT SUM(`true`) - SUM(`false`) AS result FROM questionaries ORDER BY timestamp_column DESC LIMIT 10";

$result = mysqli_query($conn, $sql);

if ($result) {
    // Fetch the result as an associative array
    $row = mysqli_fetch_assoc($result);

    // Convert the result to JSON format
    $jsonResponse = json_encode(['result' => $row['result']]);

    // Send the JSON response
    header('Content-Type: application/json');
    echo $jsonResponse;
} else {
    // Handle the error if the query fails
    echo json_encode(['error' => mysqli_error($conn)]);
}

?>
