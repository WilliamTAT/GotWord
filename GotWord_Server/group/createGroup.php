<?php
/**
 * Created by PhpStorm.
 * User: Ning
 * Date: 12/8/2017 0008
 * Time: 10:45 AM
 */

require '../util/mysql.php';

function createGroup($user_id, $username, $group_name, $words = array()) {
    $conn = getConn();

    $mysqli_result = mysqli_query($conn, "insert into word_group(name, user_name, user_id) VALUES ('$group_name', '$username', '$user_id')");

    if (!$mysqli_result) {
        $GLOBALS['response']['result'] = -1;
        $GLOBALS['response']['message'] = mysqli_error($conn);
    } else {
        $GLOBALS['response']['result'] = 1;
        $GLOBALS['response']['group_id'] = mysqli_insert_id($conn);
        if ($words != null) {
            mysqli_query($conn, "");
            mysqli_autocommit($conn, false);
            foreach ($words as $word) {
                mysqli_query($conn, "insert into word_has_group(word_word, word_group_id) VALUES ($word, group)");
                mysqli_rollback($conn);
            }
        }
    }

    if ($conn) {
        mysqli_close($conn);
    }
}

$user_id = $_POST['user_id'];
$username = $_POST['username'];
$group_name = $_POST['group_name'];
$words = $_POST['words'] or die (null);

//createGroup(1, "ninggc", "test", null) ;
if ($group_name) {
    createGroup($user_id, $username, $group_name, json_decode($words, true));
} else {
    $response['message'] = "请输入组名";
}
echo json_encode($response);

?>