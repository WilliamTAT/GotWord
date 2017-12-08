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
        $group_id = mysqli_insert_id($conn);
        if ($words != null) {
            mysqli_autocommit($conn, false);
            $result_array = array();
            foreach ($words as $word) {
                $GLOBALS['response']['test'] = $words;
                $result_array[] = mysqli_query($conn, "insert into word(word) VALUES ('$word')");
                $GLOBALS['response']['word'][$word] = mysqli_error($conn);
                $result_array[] = mysqli_query($conn, "insert into word_has_group(word_word, word_group_id) VALUES ('$word', '$group_id')");
                $GLOBALS['response']['word'][$word.'1'] = mysqli_error($conn);
            }
            mysqli_commit($conn);
            if (count($result_array) != 0) {
                foreach ($result_array as $result) {
                    if (!$result) {
                        mysqli_rollback($conn);
                        $GLOBALS['response']['result'] = -1;
                        $GLOBALS['response']['error'] = mysqli_error($conn);
                        $GLOBALS['response']['message'] = "插入单词失败";
                    } else {
                        $GLOBALS['response']['result'] = 1;
                    }
                }
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
$words = $_POST['words'];
//$user_id = 1;
//$username = "ninggc";
//$group_name = "test";
//$words = null;

//createGroup(1, "ninggc", "test", null) ;
if ($group_name) {
    createGroup($user_id, $username, $group_name, json_decode($words, true));
} else {
    $response['message'] = "请输入组名";
}

echo json_encode($response);

?>