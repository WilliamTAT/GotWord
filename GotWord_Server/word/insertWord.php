<?php
/**
 * Created by PhpStorm.
 * User: Ning
 * Date: 12/5/2017 0005
 * Time: 4:07 PM
 */

require '../util/mysql.php';

$conn = getConn();

$word = $_POST['word'];
$group = [''];

if (!$conn) {
    die('不能连接Mysql');
}
$mysqli_result = mysqli_query($conn, "insert into word(word, explains, text) VALUES ('$word', '$explains', '$all')");
if (!$mysqli_result) {
    echo '插入'.$word.'失败<br>';
}

mysqli_query($conn, "insert into word_has_group(word_word, word_group_id) VALUES()");


/** 结束 */

if ($conn) {
    mysqli_close($conn);
}
echo json_encode($response);
?>