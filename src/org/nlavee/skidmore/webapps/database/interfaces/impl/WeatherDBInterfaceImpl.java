package org.nlavee.skidmore.webapps.database.interfaces.impl;

import org.nlavee.skidmore.webapps.database.dao.UserMapping;
import org.nlavee.skidmore.webapps.database.interfaces.WeatherDBInterface;

public class WeatherDBInterfaceImpl implements WeatherDBInterface{

	@Override
	public boolean saveZipcode(Integer zipcode, String username) {
		
		UserMapping um = new UserMapping();
		um.saveWeatherLocation(zipcode, username);
		return true;
	}

}
