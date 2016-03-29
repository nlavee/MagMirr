package org.nlavee.skidmore.webapps.web.mode;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.nlavee.skidmore.webapps.database.beans.Coords;
import org.nlavee.skidmore.webapps.web.VarNames;
import org.nlavee.skidmore.webapps.web.api.impl.LyftAPIWrapper;

public class LyftAuthentication extends HttpServlet implements VarNames {

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
	private static Logger LOGGER = Logger.getLogger(LyftAuthentication.class);

	public LyftAuthentication(){

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
		LOGGER.info("POST request sent to LOGIN servlet");
		LyftRideProcess(req, resp);
	}

	private void LyftRideProcess(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * Authenticate to Lyft
		 */
		LyftAPIWrapper lyft = new LyftAPIWrapper();
		JSONObject accessTokenJSON = lyft.authenticate();

		req.getSession().setAttribute(LYFT_AUTHENTICATED, accessTokenJSON.getString("access_token"));
		LOGGER.info("access token: " + accessTokenJSON.getString("access_token"));
		
		int timeOut = accessTokenJSON.getInt("expires_in");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, timeOut / 60);
		SimpleDateFormat sdf = new SimpleDateFormat("mm/dd-HH:mm:ss");
		String expiredTime = sdf.format(cal.getTime());
		req.getSession().setAttribute(LYFT_AUTHENTICATED_TIME_OUT, expiredTime);
		

		LOGGER.info("Logged in with expired time: " + expiredTime);
		
		req.getRequestDispatcher(MAIN_JSP).forward(req, resp);

	}
}
