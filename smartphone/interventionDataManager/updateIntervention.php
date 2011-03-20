<?php
mysql_connect("localhost","root","admin");
mysql_select_db("pa8");
 
$q=mysql_query("UPDATE interventions SET cost = '".$_REQUEST['cost']."', annotations = '".$_REQUEST['annotations']."' WHERE id = '".$_REQUEST['id']."'");
$data = array('retour' => $q);
print(json_encode($data));
 
mysql_close();
?>
