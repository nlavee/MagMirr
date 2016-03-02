package org.nlavee.skidmore.webapps.web.mode;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.nlavee.skidmore.webapps.database.beans.Coords;
import org.nlavee.skidmore.webapps.web.VarNames;
import org.nlavee.skidmore.webapps.web.api.impl.UberAPIWrapper;

public class UberOps extends HttpServlet{


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
	private static Logger LOGGER = Logger.getLogger(UberOps.class);
 
	public UberOps(){
		
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
		UberRideProcess(req, resp);
	}

	private void UberRideProcess(HttpServletRequest req,
			HttpServletResponse resp) {
		
		/*
		 * Process to get lat and lon, create Coord Object
		 */
		
		//TODO find a way to get lat and lon from req.
		Double lat = new Double(0.0);
		Double lon = new Double(0.0);
		
		Coords coord = new Coords(lat, lon);
		
		/*
		 * Send to Uber API Wrapper
		 */
		UberAPIWrapper uber = new UberAPIWrapper();
		JSONObject uberRideResponse = uber.getRides(coord);
		
		/*
		 * Manipulate uberRideResponse
		 */
		
	}
}
