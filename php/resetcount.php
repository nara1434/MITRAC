<?php
// Include your database connection file
require "conn.php";

// Check if there is existing data in the streak table
$sql_check_streak = "SELECT COUNT(*) AS count FROM streak";
$result_check_streak = mysqli_query($conn, $sql_check_streak);
$row_check_streak = mysqli_fetch_assoc($result_check_streak);
$count = $row_check_streak['count'];

// If no data exists in the streak table, insert data from patient_details
if ($count == 0) {
    // Insert data from patient_details into the streak table
    $sql_insert_streak = "INSERT INTO streak (id, total_count, date)
                          SELECT patient_id, 0, CURDATE()
                          FROM patient_details";

    if (mysqli_query($conn, $sql_insert_streak)) {
        echo "Data inserted into streak table successfully";
    } else {
        echo "Error inserting data into streak table: " . mysqli_error($conn);
    }
} else {
    echo "Data already exists in the streak table";

    // Get the current date
    $currentDate = date('Y-m-d');

    // Retrieve the last date when the count value was added to the table
    $sql_last_date = "SELECT MAX(date) AS last_date FROM streak";
    $result_last_date = $conn->query($sql_last_date);
    $row_last_date = $result_last_date->fetch_assoc();
    $lastDate = $row_last_date['last_date'];

    // If there is no last date (i.e., no data in the streak table), set it to the current date
    if ($lastDate === null) {
        $lastDate = $currentDate;
    }

    // Calculate the date one day after the last date when the count value was added
    $nextDate = date('Y-m-d', strtotime($lastDate . ' +1 day'));

    // Check if there are missing dates between the last date and the current date
    if ($nextDate <= $currentDate) {
        // Generate a list of dates from the last date to the current date
        $dateRange = [];
        $current = strtotime($nextDate);
        $last = strtotime($currentDate);
        while ($current <= $last) {
            $dateRange[] = date('Y-m-d', $current);
            $current = strtotime('+1 day', $current);
        }

        // Insert missing patient IDs into the streak table with total_count = 0 for each date in the range
        foreach ($dateRange as $date) {
            $sql_insert = "INSERT INTO streak (id, total_count, date)
                           SELECT pd.patient_id, 0, '$date'
                           FROM patient_details pd
                           LEFT JOIN streak s ON pd.patient_id = s.id AND s.date = '$date'
                           WHERE s.id IS NULL";
            if ($conn->query($sql_insert) !== TRUE) {
                echo "Error inserting total count values for missing patients on " . $date . ": " . $conn->error . "<br>";
            } else {
                echo "Data inserted successfully " . $date . "<br>";
            } 
        }

        echo "Data inserted successfully<br>";
    } else {
        echo "No missing dates to insert total count values for missing patients<br>";
    }
}

// Close the database connection
mysqli_close($conn);
?>
