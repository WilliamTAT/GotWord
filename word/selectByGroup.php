<?php
/**
 * Created by PhpStorm.
 * User: Ning
 * Date: 12/1/2017 0001
 * Time: 7:33 PM
 */

require '../util/mysql.php';

/**
 * @param $word_group_id
 * @return mixed 单词结果数组
 */
function selectByWordGroupId($word_group_id) {
    $conn = getConn();

    if (!$conn) {
        die('不能连接Mysql');
    }


    $mysqli_result = mysqli_query($conn, "select word_word from word_has_group where word_group_id = '$word_group_id' ");
//    $mysqli_result = $conn -> query("select * from word_group where word_group_id = '$word_group_id'");
    if ($mysqli_result) {
        $array = array();
        while($row = mysqli_fetch_row($mysqli_result)) {
            $array[] = $row;
        }
        $GLOBALS['response']['length'] = count($array);
        mysqli_close($conn);
        return $array;
    } else {
        $GLOBALS['response']['error'] = mysqli_error($conn);
    }
}

$word_group_id = $_POST['word_group_id'];
e_log('word_group_id: '.$word_group_id.'<br>');


if ($word_group_id) {
    $result = selectByWordGroupId($word_group_id);
    $response['data'] = $result;
}

echo json_encode($response);

?>

