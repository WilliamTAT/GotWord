<?php
/**
 * Created by PhpStorm.
 * User: Ning
 * Date: 12/1/2017 0001
 * Time: 5:44 PM
 */

function getConn() {
    $conn = mysqli_connect("123.207.244.139", "ning", "ninggc");
    mysqli_select_db($conn, "db_got_word");
    return $conn;
}

$conn = getConn();
$result = mysqli_query($conn, "select * from user");

while($row = mysqli_fetch_assoc($result)) {
    var_dump($row);
    echo '<br>';
}

echo 'mysql connect success';

?>