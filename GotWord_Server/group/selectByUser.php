<?php
/**
 * Created by PhpStorm.
 * User: Ning
 * Date: 12/1/2017 0001
 * Time: 7:33 PM
 */

require '../util/mysql.php';

$response = array();

/**
 * @param $user_id
 * @return mixed 结果数组
 */
function selectByUserId($user_id) {
    $conn = getConn();

    if (!$conn) {
        die('不能连接Mysql');
    }

    $mysqli_result = mysqli_query($conn, "select word_group.id, word_group.name, count(word_has_group.word_word) as count
			from word_group, word_has_group 
            where user_id = '$user_id' 
			and word_has_group.word_group_id = word_group.id;
    ");

    if ($mysqli_result) {
        $array = array();
        while($row = mysqli_fetch_assoc($mysqli_result)) {
            $array[] = $row;
        }
        $GLOBALS['response']['length'] = count($array);
        mysqli_close($conn);
        return $array;
    } else {
        $GLOBALS['response']['error'] = mysqli_error($conn);
    }
}

//$user_id = $_POST['user_id'];
$user_id = 2;

e_log('user_id: '.$user_id.'<br>');

if ($user_id) {
    $result = selectByUserId($user_id);
    $response['data'] = $result;
}

echo json_encode($response);

?>

