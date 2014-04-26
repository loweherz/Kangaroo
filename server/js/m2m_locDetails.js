var map;
var locId = -1;
var m2mLocation = null;
var deviceList = null;
var graphData = [];
var plot = null;
var updateInterval = 30000;

function initialize() {
	
	locId = getUrlVars()["locId"];

	$.get('api/m2m.php?q=locationInfo&idLocation=' + locId, function(data) {
		
		m2mLocation = data['location'];
		deviceList = data['devices'];

		loadMap(data['location']['lat'],data['location']['lng']);

		$('#locationName').html(m2mLocation['name']);

		for(index in data['devices'])
		{
			createDeviceListElement(data['devices'][index]);
			createM2MDeviceMarker(data['devices'][index]);	
		}
	});
}

function createDeviceListElement(device)
{
	var html='<div><p><h3>Device: '+ device['idDevice'] +' </h3><a class="btn" href="#" onclick="showGraph('+device['idDevice']+')">Show Data &raquo;</a></p></div>';
	
	$('#deviceList').append(html);
	
}

function loadMap(lat,lng)
{
	var myOptions = {
		zoom : 17,
		center : new google.maps.LatLng(lat,lng),
		mapTypeId : google.maps.MapTypeId.SATELLITE
	};
	
	map = new google.maps.Map(document.getElementById('map_canvas'), myOptions);
}

function loadGraphData(deviceId,graphTitle, placeholder, sensorType,min,max) {

	//create dedicated Div
	var html = '<div><h2>'+graphTitle+'</h2><div id="'+placeholder+'" style="width:100%;height:300px;"></div></div>';

	$('#graphList').append(html);

	$.get('api/m2m.php?q=last_hour&deviceId='+deviceId, function(dataArray) {

		var data = dataArray['data'];

		var res = [];
		var avgData = [];
		var xTicksArray = [];
		var maxValue = 0.0;
		var minValue = 100000;

		var average = 0.0;
		var totalSum = 0.0;
		var count = 0;

		for(index in data) {
			var payloadString = data[index]['payload'];
			var payloadObj = JSON.parse(payloadString);
			res.push([index, payloadObj[sensorType]]);

			if(payloadObj[sensorType] > maxValue)
				maxValue = payloadObj[sensorType];

			if(payloadObj[sensorType] < minValue)
				minValue = payloadObj[sensorType];

			if(index % 20 == 0)
				xTicksArray.push([index, data[index]['timestamp']]);
			totalSum += payloadObj[sensorType];
			count++;
		}
		
		graphData = res;
		average = totalSum / count;
		for(var i = 0; i < count; i++) {
			avgData.push([i,average]);
		}

		// setup plot
		var options = {
			series : {
				shadowSize : 1
			}, // drawing is faster without shadows
			yaxis : {
				min : minValue,
				max : parseFloat(maxValue) + (0.10 * maxValue)
			},
			xaxis : {
				show : true,
				ticks : xTicksArray
			},
			lines : {
				show : true
			},
			points : {
				show : true
			}
		};

		// setup plot
		var options2 = {
			series : {
				shadowSize : 1
			}, // drawing is faster without shadows
			yaxis : {
				min : minValue,
				max : parseFloat(maxValue) + (0.10 * maxValue)
			},
			xaxis : {
				show : true,
				ticks : xTicksArray
			},
			lines : {
				show : true
			},
			points : {
				show : true
			}
		};

		//plot = $.plot($('#'+placeholder), [graphData], options);
		
		plot = $.plot($('#' + placeholder), [{
			data : graphData,
			label : "Data"
		}, {
			data : avgData,
			label : "AVG"
		}], options);
		
	});
}

function showGraph(deviceId) {
	
	$.get('api/m2m.php?q=deviceInfo&idDevice='+deviceId, function(data) {
		
		$('#graphList').html("");
		
		var sensorTypes = data['sensorTypes'];
		
		for(index in sensorTypes)
			loadGraphData(deviceId,sensorTypes[index]['name'], sensorTypes[index]['name']+'Placeholder', sensorTypes[index]['name'],sensorTypes[index]['rangeMin'],sensorTypes[index]['rangeMax']);
		
		
	});

}

function getUrlVars() {
	var vars = {};
	var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m, key, value) {
		vars[key] = value;
	});
	return vars;
}

function createM2MDeviceMarker(data) {
	
	var latLng = new google.maps.LatLng(data['lat'], data['lng']);
	var image = 'img/minihotspot.png';

	var marker = new google.maps.Marker({
		position : latLng,
		map : map,
		icon : image,
		title : data['type']
	});

	var infoBubble2 = new InfoBubble({
		map : map,
		content : '<div class="phoneytext">' + data['type'] + '</div>',
		position : new google.maps.LatLng(-35, 151),
		shadowStyle : 1,
		padding : 0,
		backgroundColor : 'rgb(57,57,57)',
		borderRadius : 4,
		arrowSize : 10,
		borderWidth : 1,
		borderColor : '#2c2c2c',
		disableAutoPan : true,
		hideCloseButton : false,
		arrowPosition : 30,
		backgroundClassName : 'phoney',
		arrowStyle : 2
	});

	google.maps.event.addListener(marker, 'click', function() {
		infoBubble2.open(map, marker);
	});
	return marker;
}

function openDetailPage(locationId) {
	q = (document.location.href);
	void (open('m2mLoc.html?locId=' + locationId, '_self', 'resizable,location,menubar,toolbar,scrollbars,status'));
}