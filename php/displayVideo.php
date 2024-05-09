<?php
require("conn.php");

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    // Check connection
    if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
    }

    // Query to get all video information
    $sql = "SELECT * FROM videos";
    $result = mysqli_query($conn,$sql);

    if ($result->num_rows > 0) {
        $videosArray = array();

        // Fetch all rows and store in an array
        while ($row = $result->fetch_assoc()) {
            $videoItem = array(
              
                'url' => $row['uVideos']
                // Add other video information as needed
            );
            $videosArray[] = $videoItem;
        }

        // Return videos data as JSON response
        header('Content-Type: application/json');
        echo json_encode($videosArray);
    } else {
        echo "No videos found";
    }

    $conn->close();
} else {
    echo "Invalid request";
}
?>
