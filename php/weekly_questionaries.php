<?php

include 'conn.php';

$inputJSON = file_get_contents('php://input');
$input = json_decode($inputJSON, true);

if (isset($input['submit'])) {
    $q1 = $input['q1'];
    $q2 = $input['q2'];

    // Convert the integer to a boolean
    $trueValue = ($q1 == 1);
    $falseValue = ($q2 == 1);

    $sql = "INSERT INTO questionaries (`true`, `false`) VALUES ('$trueValue', '$falseValue')";
    
    $result = mysqli_query($conn, $sql);

    if ($result) {
        echo json_encode(['message' => 'inserted successfully']);
    } else {
        echo json_encode(['error' => mysqli_error($conn)]);
    }
}


?>
