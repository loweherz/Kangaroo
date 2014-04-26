<?php
ini_set("display_errors", 1);
require_once ("../lib/php/util.php");
header('Content-type: application/json');

$m2mConnect = new M2MConnect();
$response = "";


//SE HO UNA RICHIESTA GET VEDO IL COMANDO CHE MI ARRIVA
if ($_SERVER['REQUEST_METHOD'] == "GET") {

	if (isset($_GET['q'])) {

		$req = $_GET['q'];
		 
		if ($req == 'listOfCNC') {
				
			$response = json_encode($m2mConnect -> getListOfCNC());
			
		} else if ($req == 'b') {
			if (isset($_GET['deviceId'])) {
				$deviceId = $_GET['deviceId'];
				$response = $m2mConnect -> getB($deviceId);
			} else
				$response = json_encode(array('status' => "404", 'msg' => "Request Params Error !"));
		
		
		} else
			$response = json_encode(array('status' => "404", 'msg' => "Command not found !"));

	} else
		$response = json_encode(array('status' => "404", 'msg' => "URL parameters ERROR !"));

	echo $response;
} 
else //HO UNA RICHIESTA POST, devo aggiungere nuovi dati
	{
		if(isset($_GET['q'])){
					
			if($_GET['q']=='saveFile'){
					
				$filename = $_GET['n'];
				$dir = $_GET['d']; 
				$id = $_GET['id'];
				$data = file_get_contents('php://input');
				
				//echo $data;
				
				$response = $m2mConnect -> saveFile($id, $dir, $data, $filename);				
			}
			
			else if($_GET['q']=='sendSettings'){
					
				$data = file_get_contents('php://input');
				
				$response = $m2mConnect -> saveSettings($data);				
			}

			else if($_GET['q']=='sendFileToCNC'){
					
				$data = file_get_contents('php://input');
				
				$json = json_decode($data, true);
				
				$cncname = $json['address'];
				$file = $json['file'];				
				
				//echo "\n\n" .$file. "\n\n";
				
				$response = $m2mConnect -> sendFileToCNC($file, $cncname);				
			}
			else
				$response = array('status' => "404", 'msg' => "POST parameter ERROR !");
			
		}//isset[q]
		else{
			$response = array('status' => "404", 'msg' => "Command ERROR !");
		}
		
		echo json_encode($response);
	}
?>