<?php
$conn=new mysqli("localhost","root","","mitrac");
if(!$conn){
    echo mysqli_error($conn);
}
else{
    //echo "connected successfull";
}


?>