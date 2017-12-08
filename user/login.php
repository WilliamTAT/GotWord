<?php
    require '../util/mysql.php';

    $response = array();

    $username = $_POST['username'];
    $password = $_POST['password'];

//    $username = "user_2";
//    $password = "123";

    if ($username && $password) {
        $conn = getConn();

        $mysqli_result = mysqli_query($conn, "select password from user where name = '$username'");

        while($row = mysqli_fetch_array($mysqli_result)) {
            if ($row['password'] == $password) {
                $response['result'] = 1;
                $response['data'] = time();
            } else {
                $response['result'] = 0;
                $response['error'] = $row;
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