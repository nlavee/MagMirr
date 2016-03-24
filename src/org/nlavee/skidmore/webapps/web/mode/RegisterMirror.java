package org.nlavee.skidmore.webapps.web.mode;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.nlavee.skidmore.webapps.web.VarNames;
import org.nlavee.skidmore.webapps.web.model.IPAddress;

public class RegisterMirror extends HttpServlet implements VarNames {
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
	private static Logger LOGGER = Logger.getLogger(RegisterMirror.class);

	public RegisterMirror(){

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
		controller(req, resp);
	}

	/**
	 * This method calls the controller method
	 *
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
		controller(req, resp);
	}

	/**
	 * Method that deals with the registration
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void controller(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String ipAddr = req.getRemoteHost();
		
		String fwdPath = CLIENT_WELCOME_JSP;
		
		IPAddress requestAddr = new IPAddress(ipAddr);
		
		boolean isLegitMirror = checkIPAddress(requestAddr);
		
		if(isLegitMirror)
		{
			req.getSession().setAttribute(MIRROR_ON, true);
			req.getSession().setAttribute(MIRROR_IP, requestAddr);
			/*
			 * Set other attributes here?
			 */
			
			fwdPath = CLIENT_MAIN_JSP;
		}
		else
		{
			// maybe forward to register mirror jsp, where you have to enter a code
			// that links you to your user id
			double generatedNum = 0;
			req.getSession().setAttribute(MIRROR_RANGE_GEN, generatedNum);
		}
		
		req.getRequestDispatcher(fwdPath).forward(req, resp);
	}

	private boolean checkIPAddress(IPAddress requestAddr) {
		
		// TODO Auto-generated method stub
		
		return true;
	}

}
