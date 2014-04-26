<?php
require_once("lib/php/util.php");

if(isset($_POST["user"]) && isset($_POST["pwd"]) && $_POST["user"]=="dsg" && $_POST["pwd"]==md5("dsgqwerty2012") )
{
	$m2mConnect = new M2MConnect();
	$m2mConnect->validateUser("dsg",0);
	header("location: m2mHome.php");
}	
else
{
	header("location: index.html");
}	
?>