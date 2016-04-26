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
import org.nlavee.skidmore.webapps.database.interfaces.impl.LyftDBInterfaceImpl;
import org.nlavee.skidmore.webapps.web.VarNames;
import org.nlavee.skidmore.webapps.web.api.impl.ConnectorWrapper;
import org.nlavee.skidmore.webapps.web.api.impl.LyftAPIWrapper;

import com.google.gson.JsonObject;

public class LyftOps extends HttpServlet implements VarNames{

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
	private static Logger LOGGER = Logger.getLogger(LyftOps.class);

	public LyftOps() {

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
		LOGGER.info("GET request sent to LyftOps servlet");
		req.getRequestDispatcher(MAIN_MODE).forward(req, resp);
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
		LOGGER.info("POST request sent to LyftOps servlet");
		LyftOperationController(req, resp);
	}

	/**
	 * Method to separate each case of the operation for the user
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void LyftOperationController(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		String pathFwd = MAIN_JSP;
		String userName = (String) req.getSession().getAttribute(USER_PARAM_FIELD_NAME);
		
		if(req.getParameter("mode") != null)
		{
			if(req.getParameter("mode").equals("rideType"))
			{
				getRideType(req, resp);
				pathFwd = MAP_JSP;
			}
			else if(req.getParameter("mode").equals("ETA"))
			{
				getETA(req, resp);
				pathFwd = MAP_JSP;
			}
			else if(req.getParameter("mode").equals("cost"))
			{
				getCost(req, resp);
				pathFwd = MAP_COST_JSP;
			}
			else if(req.getParameter("mode").equals("getRideType"))
			{
				String lat = req.getParameter("lat");
				String lon = req.getParameter("lon");
				Coords coord = new Coords(Double.parseDouble(lat), Double.parseDouble(lon));
				String sessionToken = (String) req.getSession().getAttribute(LYFT_AUTHENTICATED);
				JSONObject lyftResponse = getRideTypeViaAPI(coord, sessionToken);
				
				LyftDBInterfaceImpl lyftDB = new LyftDBInterfaceImpl();
				boolean success = lyftDB.persistRideType(lyftResponse, userName);
				
				if(success)
				{
					req.getSession().setAttribute(LYFT_RIDETYPE_FORWARDED_STATUS, true);
				}
				else
				{
					req.getSession().setAttribute(LYFT_RIDETYPE_FORWARDED_STATUS, false);
				}

				pathFwd = MAIN_JSP;
			}
			else if(req.getParameter("mode").equals("getETA"))
			{
				String lat = req.getParameter("lat");
				String lon = req.getParameter("lon");
				Coords coord = new Coords(Double.parseDouble(lat), Double.parseDouble(lon));
				String sessionToken = (String) req.getSession().getAttribute(LYFT_AUTHENTICATED);
				JSONObject lyftResponse = getETAViaAPI(coord, sessionToken);
				
				LyftDBInterfaceImpl lyftDB = new LyftDBInterfaceImpl();
				boolean success = lyftDB.persistETA(lyftResponse, userName);
				
				if(success)
				{
					req.getSession().setAttribute(LYFT_ETA_FORWARDED_STATUS, true);
				}
				else
				{
					req.getSession().setAttribute(LYFT_ETA_FORWARDED_STATUS, false);
				}

				pathFwd = MAIN_JSP;
			}
			else if(req.getParameter("mode").equals("getCost"))
			{
				String latStart = req.getParameter("latStart");
				String lonStart = req.getParameter("lonStart");
				Coords coordStart = new Coords(Double.parseDouble(latStart), Double.parseDouble(lonStart));
				
				String latEnd = req.getParameter("latEnd");
				String lonEnd = req.getParameter("lonEnd");
				Coords coordEnd = new Coords(Double.parseDouble(latEnd), Double.parseDouble(lonEnd));
				
				String sessionToken = (String) req.getSession().getAttribute(LYFT_AUTHENTICATED);
				JSONObject lyftResponse = getCostViaAPI(coordStart, coordEnd, sessionToken);
				
				LyftDBInterfaceImpl lyftDB = new LyftDBInterfaceImpl();
				boolean success = lyftDB.persistCostEstimate(lyftResponse, userName);
				
				if(success)
				{
					req.getSession().setAttribute(LYFT_COST_FORWARDED_STATUS, true);
				}
				else
				{
					req.getSession().setAttribute(LYFT_COST_FORWARDED_STATUS, false);
				}
				
				pathFwd = MAIN_JSP;
			}
			
		}
		LOGGER.info("path forward: " + pathFwd);
		req.getRequestDispatcher(pathFwd).forward(req, resp);
	}

	/**
	 * Create a JSONObject with session token in it
	 * @param sessionToken
	 * @return
	 */
	public JSONObject createAccessToken(String sessionToken)
	{
		JSONObject token = new JSONObject();
		token.put("access_token", sessionToken);
		return token;
	}
	
	/**
	 * Method that fetch data for cost through Lyft API
	 * @param coord
	 * @param sessionToken 
	 */
	private JSONObject getCostViaAPI(Coords coordStart, Coords coordEnd, String sessionToken) {
		LyftAPIWrapper lyft = new LyftAPIWrapper();
		JSONObject token = createAccessToken(sessionToken);
		JSONObject lyftResponse = lyft.getCost(coordStart, coordEnd, token);
		LOGGER.info("Lyft responded for cost: " + lyftResponse);
		return lyftResponse;
	}

	/**
	 * Method that fetch data for ETA through Lyft API
	 * @param coord
	 * @param sessionToken 
	 */
	private JSONObject getETAViaAPI(Coords coord, String sessionToken) {
		LyftAPIWrapper lyft = new LyftAPIWrapper();
		JSONObject token = createAccessToken(sessionToken);
		JSONObject lyftResponse = lyft.getETA(coord, token);
		LOGGER.info("Lyft responded for ETA: " + lyftResponse);
		return lyftResponse;
	}
	
	/**
	 * Method that fetch data for ride type through Lyft API
	 * @param coord
	 * @param sessionToken 
	 */
	private JSONObject getRideTypeViaAPI(Coords coord, String sessionToken) {
		LyftAPIWrapper lyft = new LyftAPIWrapper();
		JSONObject token = createAccessToken(sessionToken);
		JSONObject lyftResponse = lyft.getRideType(coord, token);
		LOGGER.info("Lyft responded for ride type: " + lyftResponse);
		return lyftResponse;
	}

	/**
	 * Put indicator of cost mode into request
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void getCost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute(LYFT_MODE, LYFT_COST_MODE);
		LOGGER.info("Mode: Cost");
	}

	/**
	 * Put indicator of eta mode into request
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void getETA(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute(LYFT_MODE, LYFT_ETA_MODE);
		LOGGER.info("Mode: ETA");
	}

	/**
	 * Put indicator of ride type mode into request
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void getRideType(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute(LYFT_MODE, LYFT_RIDE_TYPE_MODE);
		LOGGER.info("Mode: Ride Type");
	}
}
