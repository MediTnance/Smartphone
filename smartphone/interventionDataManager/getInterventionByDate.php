<?php
mysql_connect("localhost","root","admin");
mysql_select_db("pa8");
 
$q=mysql_query("SELECT * FROM interventions WHERE begin like '".$_REQUEST['begin']."%' ORDER BY begin");
while($e=mysql_fetch_assoc($q))
        $output[]=$e;
 
print(json_encode($output));
 
mysql_close();
?>
