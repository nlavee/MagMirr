package org.nlavee.skidmore.webapps.web.mode;

import java.io.IOException;
import java.util.InputMismatchException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.routines.IntegerValidator;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.nlavee.skidmore.webapps.database.interfaces.impl.WeatherDBInterfaceImpl;
import org.nlavee.skidmore.webapps.web.VarNames;
import org.nlavee.skidmore.webapps.web.api.impl.WeatherAPIWrapper;

public class Weather extends HttpServlet implements VarNames {

	/**
	 * The internal version id of this class
	 */
	private static final long serialVersionUID = 19620501L;

	/**
	 * Servlet version
	 */
	private static final String VERSION = "01.00.00";

	/**
	 * LOGGER.error Instance
	 */
	private static Logger LOGGER = Logger.getLogger(Weather.class);

	public Weather(){

	}

	/**
	 * Called by container when servlet instance is created. This method sets-up
	 * the LOGGER.error and DB connection properties.
	 *
	 * @param config
	 *            The servlet configuration
	 */
	public void init(ServletConfig config) {
		LOGGER.warn("Servlet init.  Version: " + VERSION);
	}

	/**
	 * This method just redirect get request back to the
	 * initial form now
	 *
	 * @param req
	 *            The request
	 * @param resp
	 *            The response
	 *
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		LOGGER.info("GET request sent to LOGIN servlet");
		req.getRequestDispatcher(MAIN_MODE).forward(req, resp);
	}

	/**
	 * This method calls the controller method
	 *
	 * @param req
	 *            The request
	 * @param resp
	 *            The response
	 *
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		LOGGER.info("POST request sent to LOGIN servlet");
		getWeather(req, resp);
	}

	/**
	 * Method that actually handles the weather request. Focus on parsing value entered from user
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void getWeather(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {

		if(req.getParameter(MAIN_ZIPCODE) != null && !req.getParameter(MAIN_ZIPCODE).equals(""))
		{
			Integer zipcodeInt = IntegerValidator.getInstance().validate(req.getParameter(MAIN_ZIPCODE));
			
			if(zipcodeInt != null)
			{
				String userName = (String) req.getSession().getAttribute(USER_PARAM_FIELD_NAME);
				String temp = processWeatherRequest(zipcodeInt, userName);
				
				/*
				 * Forward currWeather to the other client
				 */

				/*
				 * Show that this request has been processed and was
				 * successful
				 */

				//TODO for now, this goes back to main page without any update.
				req.getSession().setAttribute(ZIPCODE_WEATHER, zipcodeInt);
				req.getSession().setAttribute(TEMP_WEATHER, temp);
				req.getRequestDispatcher(MAIN_MODE).forward(req, resp);
			}
			else
			{
				LOGGER.error("Input for zipcode was not parseable for int");
			}

		}
		else
		{
			LOGGER.error("Empty field");
			LOGGER.error("Empty field for zipcode weather");
			req.getRequestDispatcher(MAIN_JSP).forward(req, resp);
		}

	}

	/**
	 * Method that process saving to database and getting the initial data for returning in resp
	 * @param zipcodeInt
	 * @param userName
	 * @return String which should be the current temperature in Celcius.
	 */
	private String processWeatherRequest(Integer zipcodeInt, String userName) {
		/*
		 * Save to db
		 */
		WeatherDBInterfaceImpl weatherDBOps = new WeatherDBInterfaceImpl();
		weatherDBOps.saveZipcode(zipcodeInt, userName);
		
		/*
		 * Process with Weather API
		 */
		WeatherAPIWrapper weatherAPI = new WeatherAPIWrapper();
		JSONObject currWeather = weatherAPI.getWeather(zipcodeInt);

		JSONObject mainJson = (JSONObject) currWeather.get("main");
		String temp = String.valueOf(Math.round(Float.parseFloat(mainJson.get("temp").toString())));
		
		return temp;
	}


}
