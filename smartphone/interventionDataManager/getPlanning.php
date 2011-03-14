<?php
$input = file_get_contents('php://input');
 
print(json_encode($intput));
 
mysql_close();
?>
