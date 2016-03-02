package org.nlavee.skidmore.webapps.web.mode;

import java.io.IOException;
import java.util.InputMismatchException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.nlavee.skidmore.webapps.web.VarNames;
import org.nlavee.skidmore.webapps.web.api.impl.WeatherAPIWrapper;

public class Weather extends HttpServlet{

	/**
	 * The internal version id of this class
	 */
	private static final long serialVersionUID = 19620501L;

	/**
	 * Servlet version
	 */
	private static final String VERSION = "01.00.00";

	/**
	 * Logger Instance
	 */
	private static Logger LOGGER = Logger.getLogger(Weather.class);

	public Weather(){

	}

	/**
	 * Called by container when servlet instance is created. This method sets-up
	 * the logger and DB connection properties.
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
	 * @see #controller
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
		req.getRequestDispatcher(VarNames.MAIN_MODE).forward(req, resp);
	}

	/**
	 * This method calls the controller method
	 *
	 * @see #controller
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

	private void getWeather(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {

		if(req.getParameter(VarNames.MAIN_ZIPCODE) != null)
		{
			String zipcode = (String) req.getParameter(VarNames.MAIN_ZIPCODE);

			try
			{
				Integer zipcodeInt = Integer.parseInt(zipcode.trim());
				
				/*
				 * Process with Weather API
				 */
				WeatherAPIWrapper weatherAPI = new WeatherAPIWrapper();
				JSONObject currWeather = weatherAPI.getWeather(zipcodeInt);

				JSONObject mainJson = (JSONObject) currWeather.get("main");
				String temp = String.valueOf(Math.round(Float.parseFloat(mainJson.get("temp").toString())));

				/*
				 * Forward currWeather to the other client
				 */

				/*
				 * Show that this request has been processed and was
				 * successful
				 */

				//TODO for now, this goes back to main page without any update.
				req.getSession().setAttribute(VarNames.ZIPCODE_WEATHER, zipcode);
				req.getSession().setAttribute(VarNames.TEMP_WEATHER, temp);
				req.getRequestDispatcher(VarNames.MAIN_MODE).forward(req, resp);
			}
			catch( InputMismatchException e)
			{
				LOGGER.error("Input for zipcode was not parseable for int");
			}

		}
		else
		{
			LOGGER.error("Empty field");
			System.out.println("Empty field for zipcode weather");
		}

	}


}
