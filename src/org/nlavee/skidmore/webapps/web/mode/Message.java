package org.nlavee.skidmore.webapps.web.mode;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.nlavee.skidmore.webapps.database.interfaces.impl.UserInterfaceImpl;
import org.nlavee.skidmore.webapps.web.VarNames;
import org.nlavee.skidmore.webapps.web.api.impl.ConnectorWrapper;

public class Message extends HttpServlet implements VarNames {
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
	private static Logger LOGGER = Logger.getLogger(Message.class);

	public Message(){

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
		LOGGER.info("GET request sent to Message servlet");
		req.getRequestDispatcher(MAIN_JSP).forward(req, resp);
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
		LOGGER.info("POST request sent to Message servlet");
		processMessage(req, resp);
	}

	private void processMessage(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {

		if(req.getParameter(MAIN_TEXT_BOX) != null)
		{
			// escape to html4 straight away after getting parameter
			String messageBody =  StringEscapeUtils.escapeHtml4(StringUtils.trim(req.getParameter(MAIN_TEXT_BOX)));
			Date date = new Date();
			
			/*
			 * Forward this messageBody to database
			 */
			UserInterfaceImpl userOps = new UserInterfaceImpl();
			String userName = (String) req.getSession().getAttribute(USER_PARAM_FIELD_NAME);
			boolean success = userOps.saveMessage(messageBody, date, userName);

			if(success)
			{

				req.getSession().setAttribute(MESSAGE_FORWARDED_STATUS, true);

			}
			else
			{
				LOGGER.error("Can't save message to database");
			}

		}
		else
		{
			LOGGER.error("Message body is null");
		}

		// go back to main screen
		req.getRequestDispatcher(MAIN_MODE).forward(req, resp);
	}
}
