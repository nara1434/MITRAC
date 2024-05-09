<?php
// Establish database connection
require("conn.php");

// Check if the patient ID is provided
if (isset($_POST['patient_id'])) {
    // Get the patient ID
    $patientId = $_POST['patient_id'];

    // Example data
    $dateTaken = date("Y-m-d"); // Current date
    $tabletsTaken = 1; // Replace with 1 or 0 based on tablet intake

    // Check if the patient missed three consecutive days
    $missedDaysQuery = "SELECT COUNT(*) as missed_days FROM streak WHERE id = $patientId AND date >= DATE_SUB(CURDATE(), INTERVAL 3 DAY) AND total_count = 0";
    $result = $conn->query($missedDaysQuery);
    $row = $result->fetch_assoc();
    $missedDays = $row['missed_days'];

    if ($missedDays >= 3) {
        // Reset progress bar by deleting previous records
        $resetQuery = "DELETE FROM streak WHERE id = $patientId";
        $conn->query($resetQuery);
    }

    // Fetch and calculate total streak count
    $totalStreakQuery = "SELECT COUNT(*) as total_streaks FROM streak WHERE id = $patientId AND total_count = '1'";
    $resultTotalStreak = $conn->query($totalStreakQuery);
    $rowTotalStreak = $resultTotalStreak->fetch_assoc();
    $totalStreaks = $rowTotalStreak['total_streaks'];

    // Return the result as JSON
    echo json_encode(['totalStreaks' => $totalStreaks]);
} else {
    // Return an error message as JSON
    echo json_encode(['error' => 'Patient ID is missing']);
}

$conn->close();
?>
