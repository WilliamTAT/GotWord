<?php
/**
 * Created by PhpStorm.
 * User: Ning
 * Date: 12/1/2017 0001
 * Time: 7:33 PM
 */

require '../util/mysql.php';

/**
 * @param $user_id
 * @return mixed 结果数组
 */
function selectByUserId($user_id) {
    $conn = getConn();

    if (!$conn) {
        die('不能连接Mysql');
    }
    
    $mysqli_result = mysqli_query($conn, "select word_word from word_has_group 
            where word_group_id = 
    		any (select id from word_group where user_id = '$user_id');");

    echo count($mysqli_result);

    if ($mysqli_result) {
        $array = mysqli_fetch_array($mysqli_result, MYSQLI_ASSOC);
        $GLOBALS['response']['length'] = count($array);
        mysqli_close($conn);
        return $array;
    } else {
        $GLOBALS['response']['error'] = mysqli_error($conn);
    }
}

$user_id = $_POST['user_id'];
e_log('user_id: '.$user_id.'<br>');

if ($user_id) {
    $result = selectByUserId($user_id);
    $response['data'] = $result;
}

echo json_encode($response);

?>

