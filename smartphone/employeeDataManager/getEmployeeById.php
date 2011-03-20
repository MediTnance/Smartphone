<?php
mysql_connect("localhost","root","admin");
mysql_select_db("pa8");
 
$q=mysql_query("SELECT firstName, lastName FROM employees WHERE id = '".$_REQUEST['id']."'");
while($e=mysql_fetch_assoc($q))
        $output[]=$e;
 
print(json_encode($output));
 
mysql_close();
?>
