<?php
/**
 * Created by PhpStorm.
 * User: Ning
 * Date: 12/1/2017 0001
 * Time: 6:08 PM
 */

require '../util/mysql.php';


function insertWord($word, $explains, $all = '') {
    $conn = getConn();

    if (!$conn) {
        die('不能连接Mysql');
    }
    $mysqli_result = mysqli_query($conn, "insert into word(word, explains, text) VALUES ('$word', '$explains', '$all')");
    if (!$mysqli_result) {
        echo '插入'.$word.'失败<br>';
    }
    mysqli_close($conn);
}

function insertWordToGroup($word, $group) {
    $conn = getConn();

    if (!$conn) {
        die('不能连接Mysql');
    }

    $mysqli_result = mysqli_query($conn, "insert into word_has_group(word_word, word_group_id) VALUES ('$word', (select id from db_got_word.group where name = '$group'))");
    if (!$mysqli_result) {
        echo '插入'.$word.' to '.$group.' 失败<br>';
    }
    mysqli_close($conn);
}

function selectWord($word) {
    $conn = getConn();

    if (!$conn) {
        die('不能连接Mysql');
    }

    $mysqli_result = mysqli_query($conn, "select * from word where word = '$word'");
    $mysqli_fetch_row = mysqli_fetch_row($mysqli_result);
    if ($mysqli_fetch_row) {
        echo '查询-'.$word.'-无结果<br>';
    }
    mysqli_close($conn);
    return $mysqli_fetch_row;
}

$group = $_POST['group'] ;
echo '<br>';
$word = $_POST['word'];
echo '<br>';
$explains = $_POST['explains'];
echo '<br>';
$text = $_POST['text'];
echo '<br>';


echo '$group: '.$group;
echo '$word: '.$word;
echo '$explains: '.$explains;
echo '$text: '.$text;

if ($word) {
    if (!selectWord($word)) {
        insertWord($word, $explains, $text);
    }
    if ($group) {
        insertWordToGroup($word, $group);
    }
}

echo 'Finish';
?>