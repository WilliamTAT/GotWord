<?php
/* by http://www.manongjc.com/article/1206.html */
function mysqlinstalled (){
    if (function_exists ("mysql_connect")){
        return true;
    } else {
        return false;
    }
}
function mysqliinstalled (){
    if (function_exists ("mysqli_connect")){
        return true;
    } else {
        return false;
    }
}

if (mysqlinstalled()){
    echo "<p>The mysql extension is installed.</p>";
} else {
    echo "<p>The mysql extension is not installed..</p>";
}
if (mysqliinstalled()){
    echo "<p>The mysqli extension is installed.</p>";
} else {
    echo "<p>The mysqli extension is not installed..</p>";
}


echo phpversion();

phpinfo();
?>