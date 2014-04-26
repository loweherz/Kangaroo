<?php
require_once ("lib/php/util.php");
$m2mConnect = new M2MConnect();
if (!$m2mConnect -> isLoggedIn()) {
	header('Location: index.html');
	die();
}
?>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title>DSG | M2M</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="">
		<meta name="author" content="">
		<!-- Le styles -->
		<link href="css/bootstrap.css" rel="stylesheet">
		<style type="text/css">
			body {
				padding-top: 60px;
				padding-bottom: 40px;
			}
		</style>
		<link href="css/bootstrap-responsive.css" rel="stylesheet">
		<link rel="shortcut icon" href="http://160.78.28.142:80/dii/decorations/layout/tigris/images/favicon.ico">
		<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
		<!--[if lt IE 9]>
		<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
		<style>
			body {
			padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
			}#map_canvas_popup img {
			               max-width: none;
			           }

			       
		</style>

		<style>
			body {
				padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
			}
			#map_canvas img {
				max-width: none;
			}
		</style>
		<style type="text/css">
			#map_canvas {
				height: 800px
			}
			.phoney {
				background: -webkit-gradient(linear,left top,left bottom,color-stop(0, rgb(112,112,112)),color-stop(0.51, rgb(94,94,94)),color-stop(0.52, rgb(57,57,57)));
				background: -moz-linear-gradient(center top,rgb(112,112,112) 0%,rgb(94,94,94) 51%,rgb(57,57,57) 52%);
			}
			.phoneytext {
				text-shadow: 0 -1px 0 #000;
				color: #fff;
				font-family: Helvetica Neue, Helvetica, arial;
				font-size: 18px;
				line-height: 25px;
				padding: 4px 45px 4px 15px;
				font-weight: bold;
			}
			.phoneytab {
				text-shadow: 0 -1px 0 #000;
				color: #fff;
				font-family: Helvetica Neue, Helvetica, arial;
				font-size: 18px;
				background: rgb(112,112,112) !important;
			}

		</style>
		<script type="text/javascript">
			var script = '<script type="text/javascript" src="http://google-maps-' + 'utility-library-v3.googlecode.com/svn/trunk/infobubble/src/infobubble';
			if(document.location.search.indexOf('compiled') !== -1) {
				script += '-compiled';
			}
			script += '.js"><' + '/script>';
			document.write(script);

		</script>
		<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?sensor=false"></script>
		<script type="text/javascript" src="js/jquery1.7.js"></script>
		<script type="text/javascript" src="js/m2m_map.js"></script>
	</head>
	<body onLoad="initialize()">
		<div class="navbar navbar-fixed-top">
			<div class="navbar-inner">
				<div class="container">
					<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse"> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </a>
					<a class="brand" href="#">DSG | M2M</a>
					<div class="nav-collapse">
						<ul class="nav">
							<li class="active">
								<a href="m2mHome.php">Home</a>
							</li>
							<li class="active">
								<a href="logout.php">Logout</a>
							</li>
						</ul>
					</div><!--/.nav-collapse -->
				</div>
			</div>
		</div>
		<div class="container">
			<!-- Main hero unit for a primary marketing message or call to action -->
			<div class="hero-unit">
				<div id="map_canvas"></div>
			</div>
		</div>
	</body>
</html>
