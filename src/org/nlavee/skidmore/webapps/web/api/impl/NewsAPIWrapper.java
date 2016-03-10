package org.nlavee.skidmore.webapps.web.api.impl;

import org.apache.log4j.Logger;
import org.nlavee.skidmore.webapps.web.APIKEYS;
import org.nlavee.skidmore.webapps.web.VarNames;
import org.nlavee.skidmore.webapps.web.api.NewsInterface;

public class NewsAPIWrapper implements NewsInterface, VarNames, APIKEYS{

	/**
	 * Logger Instance
	 */
	private static Logger LOGGER = Logger.getLogger(NewsAPIWrapper.class);

	@Override
	public boolean chooseSections(String[] sections) {

		String urlBase = "http://api.nytimes.com/svc/topstories/v1/";
		StringBuilder apiRequestURL = new StringBuilder();
		apiRequestURL.append(urlBase);
		apiRequestURL.append("");
		apiRequestURL.append(".json?api-key=");
		apiRequestURL.append(NEWS_API_KEY);

		for(String section : sections)
		{
			LOGGER.info("Received the following requested section: " + section);
		}




		return false;
	}

}
