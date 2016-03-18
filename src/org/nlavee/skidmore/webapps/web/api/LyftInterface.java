package org.nlavee.skidmore.webapps.web.api;

import org.json.JSONObject;
import org.nlavee.skidmore.webapps.database.beans.Coords;

public interface LyftInterface {
	
	/**
	 * Method to authenticate using OAuth2 for Lyft API
	 * @return JSONObject containing the authentication data
	 */
	public JSONObject authenticate();
	
	/**
	 * Method to get ride type for a specific coordinates
	 * @param coord
	 * @return
	 */
	public JSONObject getRideType(Coords coord, JSONObject accessTokenJSON);
	
	public JSONObject getETA(Coords coord, JSONObject accessTokenJSON);
	
	public JSONObject getCost(Coords coordStart, Coords coordEnd, JSONObject accessTokenJSON);
	
	public JSONObject getCost(Coords coordStart, Coords coordEnd, String rideType, JSONObject accessTokenJSON);
}
