<?php
/**
 * Created by PhpStorm.
 * User: Ning
 * Date: 12/1/2017 0001
 * Time: 9:20 PM
 */


//$s = "select word_word from word_has_group
//            where word_group_id =
//    		any (select id from word_group where user_id = '1')";
//$s = "select id from word_group where user_id = '1'";

//$s = "select * from word where word like 'word_%'";
//$s = "select * from word_group";
//$s = "select * from word_has_group";

//where word_group_id = '1'";

$s = "select * from user";
$conn = mysqli_connect('123.207.244.139', 'ning', 'ninggc');
mysqli_select_db($conn, 'db_got_word');
$mysqli_result = mysqli_query($conn, $s);
//echo mysqli_error($conn);
echo json_encode(mysqli_fetch_array($mysqli_result));

?>