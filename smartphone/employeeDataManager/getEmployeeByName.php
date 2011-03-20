<?php
mysql_connect("localhost","root","admin");
mysql_select_db("pa8");
 
$q=mysql_query("SELECT * FROM employees WHERE firstName = '".$_REQUEST['firstName']."' AND lastName = '".$_REQUEST['lastName']."'");
while($e=mysql_fetch_assoc($q))
        $output[]=$e;
 
print(json_encode($output));
 
mysql_close();
?>
