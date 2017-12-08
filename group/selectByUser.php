<?php
/**
 * Created by PhpStorm.
 * User: Ning
 * Date: 12/1/2017 0001
 * Time: 7:33 PM
 */

require 'groupUtil.php';

//$user_id = $_POST['user_id'];
$user_id = 1;

//e_log('user_id: '.$user_id.'<br>');

if ($user_id) {
    $response = selectByUserId($user_id);
} else {
    $response['message'] = "请输入用户id";
}

echo json_encode($response);

?>

