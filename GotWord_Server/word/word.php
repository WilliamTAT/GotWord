<?php
/**
 * Created by PhpStorm.
 * User: Ning
 * Date: 12/1/2017 0001
 * Time: 6:08 PM
 */

require '../util/mysql.php';
$response = array();

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

function insertWordToWordGroup($word, $word_group) {
    $conn = getConn();

    if (!$conn) {
        die('不能连接Mysql');
    }

    $mysqli_result = mysqli_query($conn, "insert into word_has_word_group(word_word, word_word_group_id) VALUES ('$word', (select id from db_got_word.word_group where name = '$word_group'))");
    if (!$mysqli_result) {
        echo '插入'.$word.' to '.$word_group.' 失败<br>';
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

$word_group = $_POST['word_group'] ;
$word = $_POST['word'];
$explains = $_POST['explains'];
$text = $_POST['text'];


echo '$word_group: '.$word_group;
echo '<br>';
echo '$word: '.$word;
echo '<br>';
echo '$explains: '.$explains;
echo '<br>';
echo '$text: '.$text;
echo '<br>';

if ($word) {
    $selectWord = selectWord($word);
    if (!$selectWord) {
        $response['at']['message'] = '添加新单词';
        insertWord($word, $explains, $text);
    } else {
        $response['data']['word'] = $selectWord;
    }
    if ($word_group) {
        insertWordToWordGroup($word, $word_group);
    }
}

echo json_encode($response);
?>