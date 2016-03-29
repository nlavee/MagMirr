var marker1, marker2;
var poly, geodesicPoly;

function initMap() {
	var map = new google.maps.Map(document.getElementById('map'), {
		zoom: 4,
		center: {lat: 40.736, lng: -73.991}
	});

	map.controls[google.maps.ControlPosition.TOP_CENTER].push(
			document.getElementById('info'));

	var content1 = "Starting Point";
	var infowindow1 = new google.maps.InfoWindow({
		    content: content1
		  });
	marker1 = new google.maps.Marker({
		map: map,
		draggable: true,
		position: {lat: 40.700, lng: -74.014},
		label:"S"
	});
	marker1.addListener('click', function() {
	    infowindow1.open(map, marker1);
	  });
	
	var content2 = "Ending Point";
	var infowindow2 = new google.maps.InfoWindow({
	    content: content2
	  });
	marker2 = new google.maps.Marker({
		map: map,
		draggable: true,
		position: {lat: 40.771, lng: -73.886},
		label:"E"
	});
	marker2.addListener('click', function() {
	    infowindow2.open(map, marker2);
	  });

	var bounds = new google.maps.LatLngBounds(
			marker1.getPosition(), marker2.getPosition());
	map.fitBounds(bounds);

	google.maps.event.addListener(marker1, 'position_changed', update);
	google.maps.event.addListener(marker2, 'position_changed', update);

	poly = new google.maps.Polyline({
		strokeColor: '#FF0000',
		strokeOpacity: 1.0,
		strokeWeight: 3,
		map: map,
	});

	geodesicPoly = new google.maps.Polyline({
		strokeColor: '#CC0099',
		strokeOpacity: 1.0,
		strokeWeight: 3,
		geodesic: true,
		map: map
	});

	update();
}

function update() {
	var path = [marker1.getPosition(), marker2.getPosition()];
	poly.setPath(path);
	geodesicPoly.setPath(path);
	var heading = google.maps.geometry.spherical.computeHeading(path[0], path[1]);
	document.getElementById('heading').value = heading;
	document.getElementById('origin').value = path[0].toString();
	document.getElementById('destination').value = path[1].toString();
	
	document.getElementById('current').innerHTML = '<p>Marker: Point 1 Lat: ' + marker1.getPosition().lat().toFixed(3) + ' Current Lng: ' + marker1.getPosition().lng().toFixed(3) + '</p><p>Marker: Point 2 Lat: ' + marker2.getPosition().lat().toFixed(3) + ' Current Lng: ' + marker2.getPosition().lng().toFixed(3) + '</p><form action="lyftRideType" method="post" id="auth" class="auth"><input type="hidden" name="mode" value="getCost"/><input type="hidden" name="latStart" value="'+marker1.getPosition().lat().toFixed(3)+'"/><input type="hidden" name="lonStart" value="'+marker1.getPosition().lng().toFixed(3)+'"/><input type="hidden" name="latEnd" value="'+marker2.getPosition().lat().toFixed(3)+'"/><input type="hidden" name="lonEnd" value="'+marker2.getPosition().lng().toFixed(3)+'"/><input type="submit" value="Confirm Location for Cost"/></form>';
}
