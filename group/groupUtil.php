<?php
/**
 * Created by PhpStorm.
 * User: Ning
 * Date: 12/8/2017 0008
 * Time: 7:19 PM
 */

require '../util/mysql.php';

function createGroup($user_id, $username, $group_name, $words = array()) {
    $conn = getConn();
    $response = array();

    $mysqli_result = mysqli_query($conn, "insert into word_group(name, user_name, user_id) VALUES ('$group_name', '$username', '$user_id')");

    if (!$mysqli_result) {
        $response['result'] = -1;
        $response['message'] = mysqli_error($conn);
    } else {
        $response['result'] = 1;
        $group_id = mysqli_insert_id($conn);
        if ($words != null) {
            mysqli_autocommit($conn, false);
            $result_array = array();
            foreach ($words as $word) {
                $result_array[] = mysqli_query($conn, "insert into word(word) VALUES ('$word')");
                $response['word_error?'][$word.' insert word'] = mysqli_error($conn);
                $result_array[] = mysqli_query($conn, "insert into word_has_group(word_word, word_group_id) VALUES ('$word', '$group_id')");
                $response['word_error?'][$word.' add to group'] = mysqli_error($conn);
            }
            mysqli_commit($conn);
            if (count($result_array) != 0) {
                foreach ($result_array as $result) {
                    if (!$result) {
                        mysqli_rollback($conn);
                        $response['result'] = -1;
                        $response['error'] = mysqli_error($conn);
                        $response['message'] = "插入单词失败";
                    }
                }
            }
        }
    }

    if ($conn) {
        mysqli_close($conn);
    }

    return $response;
}

/**
 * @param $user_id
 * @return mixed 结果数组
 */
function selectByUserId($user_id) {
    $conn = getConn();
    $response = array();

    if (!$conn) {
        die('不能连接Mysql');
    }

    $mysqli_result = mysqli_query($conn, "select word_group.id, word_group.name, count(word_has_group.word_word) as count, word_group.note
			from word_group, word_has_group 
            where user_id = '$user_id' 
			and word_has_group.word_group_id = word_group.id
			group by word_has_group.word_group_id;
    ");

    if ($mysqli_result) {
        $array = array();
        while($row = mysqli_fetch_assoc($mysqli_result)) {
            $array[] = $row;
        }
        $response['length'] = count($array);
        $response['data'] = $array;
        mysqli_close($conn);
    } else {
        $response['result'] = -1;
        $response['message'] = mysqli_error($conn);
    }

    return $response;
}

//$user_id = 1;
//$username = "ninggc";
//$group_name = "test";
//$words = null;
//
//$response = createGroup(1, "ninggc", "test111", null) ;
//echo json_encode($response);

?>