package org.nlavee.skidmore.webapps.web.api.impl;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.nlavee.skidmore.webapps.database.beans.Coords;
import org.nlavee.skidmore.webapps.web.api.UberInterface;

import com.uber.sdk.rides.client.Session;
import com.uber.sdk.rides.client.Session.Environment;
import com.uber.sdk.rides.client.UberRidesSyncService;
import com.uber.sdk.rides.client.model.ProductsResponse;
import com.uber.sdk.rides.client.model.RideRequestParameters;
import com.uber.sdk.rides.client.UberRidesServices;

public class UberAPIWrapper implements UberInterface{

	/**
	 * Logger Instance
	 */
	private static Logger LOGGER = Logger.getLogger(UberAPIWrapper.class);
	
	private UberRidesSyncService uberRidesService;
	
	@Override
	public JSONObject getRides(Coords coord) {

		JSONObject res = new JSONObject();
		/*
		 * User Uber Java SDK here
		 */


		return res;
	}

//	private Session getSession()
//	{
//		// Get the server token for your app from the developer dashboard.
//		Session session = new Session.Builder()
//		.setServerToken("YOUR_SERVER_TOKEN")
//		.setEnvironment(Environment.SANDBOX)
//		.build();
//
//		return session;
//	}
//
//	private String getAvailableProducts(Session session, Coords coord)
//	{
//		uberRidesService = UberRidesServices.createSync(session);
//		
//		// Get a list of products for a specific location in GPS coordinates, example: 37.79f, -122.39f.
//		ProductsResponse productsResponse = uberRidesService.getProducts(coord.getLat(), coord.getLon())
//				.getBody();
//		List <Product> products = productsResponse.getProducts();
//		String productId = products.get(0).getProductId();
//
//		return productId;
//	}
//
//	private String requestARide(String productId, Coords coordPickUp, Coords coordDropOff)
//	{
//		// Request an Uber ride by giving the GPS coordinates for pickup and drop-off.
//		RideRequestParameters rideRequestParameters = new RideRequestParameters.Builder()
//		.setPickupCoordinates(coordPickUp.getLat(), coordPickUp.getLon())
//		.setProductId(productId)
//		.setDropoffCoordinates(coordDropOff.getLat(), coordDropOff.getLon())
//		.build();
//		Ride ride = service.requestRide(rideRequestParameters).getBody();
//		String rideId = ride.getRideId();
//	}
//	
//	private void updateARide()
//	{
//		SandboxRideRequestParameters rideParameters = new SandboxRideRequestParameters.Builder().setStatus(“accepted”).build();
//		Response<Void> response = client.updateSandboxRide(rideId, rideParameters);
//	}
}
