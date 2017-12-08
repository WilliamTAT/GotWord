<?php
/**
 * Created by PhpStorm.
 * User: Ning
 * Date: 12/8/2017 0008
 * Time: 2:20 PM
 */

$arr = json_decode("[1,2]", true);

var_dump($arr);

foreach ($arr as $item) {
    echo $item;
}

?>