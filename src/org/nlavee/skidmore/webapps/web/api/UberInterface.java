package org.nlavee.skidmore.webapps.web.api;

import org.json.JSONObject;
import org.nlavee.skidmore.webapps.database.beans.Coords;

public interface UberInterface {
	
	public JSONObject getRides(Coords coord);
}
