<?php
/**
 * Created by PhpStorm.
 * User: Ning
 * Date: 12/5/2017 0005
 * Time: 3:41 PM
 */

/**
 * 0 : 同户名已存在
 * 1 : 注册成功
 * 2 : Mysql异常
 */

require '../util/mysql.php';
$response = array();
$response['tip'] = "0 : 同户名已存在; 1 : 注册成功; 2 : Mysql异常";

$username = $_POST['username'];
$password = $_POST['password'];
//$username = 'test';
//$password = 'test';

if ($username && $password) {
    $conn = getConn();
    $mysqli_result = mysqli_query($conn, "select name from user where name = '$username'");
    if (count($mysqli_result) != 0) {
        $response['result']['message'] = '用户名已存在';
        $response['result']['code'] = '0';
    } else {
        $mysqli_result = mysqli_query($conn, "insert into user(name, password) values('$username', '$password')");
        if ($mysqli_result) {
            $response['result']['message'] = '注册成功';
            $response['result']['code'] = '1';
        } else {
            $response['result']['message'] = 'Mysql异常';
            $response['result']['code'] = '2';
            $response['error']['mysql'] = mysqli_error($conn);
        }
    }
} else if (!$username) {
    $response['error'] = "请输入用户名";
} else if (!$password) {
    $response['error'] = "请输入密码";
}

if ($conn) {
    mysqli_close($conn);
}
echo json_encode($response);
?>