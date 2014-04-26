<?php

require_once ("config.php");
require_once ("mysql_to_json.class.php");

class M2MConnect {

	public $connect;

	/*##################### COSTRUCTOR #################################*/
	function __construct() {
		//$this -> connect = mysql_connect('localhost', $GLOBALS['user'], $GLOBALS['pwd']);
		//$db_selected = mysql_select_db($GLOBALS['db_name'], $this -> connect);
	}


	/*
	 * FILE MANAGER
	 * */

	function saveFile($id, $dir, $file, $filename){
		
		$type = $this->getTypeCNC($id);
		
		if($type!=null){
			
			
			if($type == "cnc"){
				$type = "CNC";
			}
			else if($type == "tornio"){
				$type = "TORNIO";
			}
			else{
				$type = "UKNOWN_MACHINE";
			}
			
		
			if (!is_dir("/home/pi/program/" .$type."/".$dir)) {
			    mkdir("/home/pi/program/" .$type."/".$dir, 0777, true);
			}
			
			file_put_contents("/home/pi/program/".$type."/".$dir.$filename, $file);
			
			chmod("/home/pi/program/".$type."/".$dir.$filename, 0777);
			
			return array('status' => "200", 'msg' => "File correctly saved!");
			
		}
		else
			return array('status' => "500", 'msg' => "There isn't machine with id=".$id);
	}
	
	function sendFileToCNC($file, $cncname){
		
		//get cnc ip from cncname		
		$xml = simplexml_load_file('cnc.xml');

		$url = null;
		
		foreach($xml->machine as $machine)
		{
		    if($machine->ipAddress==$cncname){
		    	$url = $machine->ipAddress;
		    }			
		}
		
		if($url == null){
			return array('status' => "404", 'msg' => "No CNC Found!");
		}
		
		
		$url = 'localhost';
		
		$header = array('Content-Type: multipart/form-data');
		$fields = array('file' => '@' . realpath('../../'.$file));
		$token = 'NfxoS9oGjA6MiArPtwg4aR3Cp4ygAbNA2uv6Gg4m';
		 
		//$file_name_with_full_path = realpath('./sample.jpeg');
	
		$resource = curl_init();
		curl_setopt($resource, CURLOPT_URL, $url);
		curl_setopt($resource, CURLOPT_HTTPHEADER, $header);
		curl_setopt($resource, CURLOPT_RETURNTRANSFER, 1);
		curl_setopt($resource, CURLOPT_POST, 1);
		curl_setopt($resource, CURLOPT_POSTFIELDS, $fields);
		curl_setopt($resource, CURLOPT_COOKIE, 'apiToken=' . $token);
		curl_setopt($resource, CURLOPT_CONNECTTIMEOUT ,0); 
		curl_setopt($resource, CURLOPT_TIMEOUT, 10); //timeout in seconds
		$result = json_decode(curl_exec($resource));
		curl_close($resource);
		
		return array('status' => "200", 'msg' => "File correctly send!", 'response' => $result);		
	}
	
	
	
	
	/*
	 * CNC MANAGER
	 * */
	
	function getListOfCNC(){
		
		$xml = simplexml_load_file('cnc.xml');
	
		return $xml;		
	}
	
	function getTypeCNC($id){
		$xml = simplexml_load_file('cnc.xml');
		
		$type = null;
		 
		foreach($xml->machine as $machine)
		{
			if( $machine->id == $id) 
				$type = $machine->type; 						
		}
		
		return $type; 
	}
	
	function saveSettings($data){
		$json = json_decode($data);
		
		$id = $json->id;		
		$name = $json->name;
		$ip = $json->ipAddress;
		$type = $json->type;
	
		$xml = simplexml_load_file('cnc.xml');
		
		if ($this->getTypeCNC($id)==null){
			//ADD CNC
			$machine = $xml->addChild("machine");
  			$machine->addChild("id", $id);
  			$machine->addChild("name", $name);
			$machine->addChild("ip", $ip);
			$machine->addChild("type", $type);
			
			
							
		}
		else{
			//EDIT CNC			
			foreach($xml->machine as $machine)
			{
				if( $machine->id == $id){
					$machine->type = $type;
					$machine->ip = $ip;
					$machine->name = $name;
				} 						
			}				
		}
		
		$xml->saveXML('cnc.xml');
		
		return array('status' => "200", 'msg' => "Settings correctly saved!");
	}
	
	/////////// LOGIN MANAGEMENT ////////////////
	/*
	 Validate Logged User using Php session
	 */
	function validateUser($nickname, $idUser) {
		setcookie("valid", 1, time() + 3600);
		setcookie("nickname", $nickname, time() + 3600);
		setcookie("idUser", $idUser, time() + 3600);
	}

	/*
	 Return ID of Logged User
	 */
	function getIdLoggedUser() {
		//return $_SESSION['idUser'];
		return $_COOKIE['idUser'];
	}

	/*
	 Return nickname of Logged User
	 */
	function getNickLoggedUser() {
		//return $_SESSION['nickname'];
		return $_COOKIE['nickname'];
	}

	/*
	 Check if the user
	 */
	function isLoggedIn() {
		if (isset($_COOKIE['valid']) == "1")
			return true;
		else
			return false;
	}

	/*
	 Logout user
	 */
	function logout() {
		setcookie("valid", "", time() - 3600);
		setcookie("nickname", "", time() - 3600);
		setcookie("idUser", "", time() - 3600);
	}

}
?>