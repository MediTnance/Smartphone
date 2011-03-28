<?php

echo "zzz";
if(is_writable ("pieces.php"))
	echo 'ok';
else
	echo "nok";
$fichier = fopen('pieces.php','w');
echo $fichier;

$data = "<?php \$data=array('piece' => '".$_REQUEST['piece']."'); print(json_encode(\$data)); ?>";
echo $data;

fwrite($fichier,$data);
//fwrite($fichier,$_REQUEST['piece']."\r\n");
echo $nb;
fclose($fichier);
?>
