package org.nlavee.skidmore.webapps.web.mode;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
			String messageBody = StringUtils.trim(req.getParameter(MAIN_TEXT_BOX));
			Date date = new Date();
			/*
			 * Forward this messageBody to database
			 */
			UserInterfaceImpl userOps = new UserInterfaceImpl();
			boolean success = userOps.saveMessage(messageBody, date);

			if(success)
			{
				/*
				 * Forward this message to the mirror
				 */
				ConnectorWrapper connector = new ConnectorWrapper();
				boolean mirrorFetch = connector.forwardMessage(messageBody);
				
				/*
				 * Return response saying that it's successful
				 */
				if(mirrorFetch)
				{
					req.getSession().setAttribute(MESSAGE_FORWARDED_STATUS, true);
				}
				else
				{
					// TODO consider deleting the message from db if we cannot send it to mirror?
					LOGGER.error("Can't forward to mirror");
					req.getSession().setAttribute(MESSAGE_FORWARDED_STATUS, false);
				}
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
