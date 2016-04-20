package org.nlavee.skidmore.webapps.web.mode;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.nlavee.skidmore.webapps.database.dao.ObjMapping;
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
		
		String forwardPath = REGISTER_MIRROR_JSP;
		if(req.getParameter(MIRROR_IP) != null)
		{
			String ip = req.getParameter(MIRROR_IP);
			String userName = req.getSession().getAttribute(USER_PARAM_FIELD_NAME).toString();
			
			boolean success = registerMirror(ip, userName);
			if(success)
			{
				req.getSession().setAttribute(MIRROR_ADDED_ATTRIBUTE, "true");
			}
			else
			{
				req.getSession().setAttribute(MIRROR_ADDED_ATTRIBUTE, "false");
			}
		}
		else
		{
			req.getSession().setAttribute(MIRROR_ADDED_ATTRIBUTE, false);
		}
		req.getRequestDispatcher(REGISTER_MIRROR_JSP).forward(req, resp);
	}

	private boolean registerMirror(String ip, String userName) {
		ObjMapping um = new ObjMapping();
		return um.registerMirror(ip, userName);
	}

}
