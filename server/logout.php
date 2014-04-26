<?php
require_once("lib/php/util.php");
$m2mConnect = new M2MConnect();
$m2mConnect->logout();
header("location: index.html");	
?>