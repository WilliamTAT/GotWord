<?php
/**
 * Created by PhpStorm.
 * User: Ning
 * Date: 12/8/2017 0008
 * Time: 10:45 AM
 */

require 'groupUtil.php';


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
    $response = createGroup($user_id, $username, $group_name, json_decode($words, true));
} else {
    $response['message'] = "请输入组名";
}

echo json_encode($response);

?>