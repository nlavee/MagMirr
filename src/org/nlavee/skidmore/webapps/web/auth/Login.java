package org.nlavee.skidmore.webapps.web.auth;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.nlavee.skidmore.webapps.database.beans.User;
import org.nlavee.skidmore.webapps.database.interfaces.impl.UserInterfaceImpl;
import org.nlavee.skidmore.webapps.web.VarNames;

public class Login extends HttpServlet implements VarNames {
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
	private static Logger LOGGER = Logger.getLogger(Login.class);

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
	 * The constructor - no operations carried out here
	 */
	public Login() {
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
		login(req,resp);
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
		login(req, resp);
	}

	/**
	 * This method (controller) determines the requested mode and sets-up the
	 * proper beans (model) before forwarding to the appropriate JSP (view).
	 *
	 * @param req
	 *            The request
	 * @param resp
	 *            The response
	 *
	 * @throws ServletException
	 * @throws IOException
	 */
	private void login(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		LOGGER.info("Processing login request");
		LOGGER.info("We get into login class");

		if(req.getSession().getAttribute(AUTHENTICATED_ATTRIBUTES_NAME) == null)
		{
			LOGGER.info("authentication is null");
			String pathForwarded = LOGIN_JSP; 

			if( req.getParameter(USER_PARAM_FIELD_NAME) != null && 
					req.getParameter(PASSWORD_PARAM_FIELD_NAME) != null)
			{
				String userName = req.getParameter(USER_PARAM_FIELD_NAME);
				String password = req.getParameter(PASSWORD_PARAM_FIELD_NAME);

				User userInvalidated = new User(userName, password);

				boolean isExistingUser = false;
				try {
					isExistingUser = authenticate(userInvalidated);
				} 
				catch (NoSuchAlgorithmException | NoSuchProviderException e) 
				{
					LOGGER.error("Problem verifying password", e);
					req.setAttribute(LOGIN_UNSUCCESSFUL, true);
				}

				if(req.getParameter(REMEMBER_PARAM_FIELD_NAME) != null)
				{
					// do something to remember, possibly put into session

				}

				if( isExistingUser )
				{
					LOGGER.info("This is an existing user");

					/*
					 * Get firstName
					 */
					String firstName = getFirstName(userName);
					LOGGER.info("User's first name: " + userName);
					pathForwarded = MAIN_JSP;
					req.getSession().setAttribute(FIRST_NAME_PARAM_FIELD_NAME, firstName);
					req.getSession().setAttribute(USER_PARAM_FIELD_NAME, userName);
					req.getSession().setAttribute(AUTHENTICATED_ATTRIBUTES_NAME, true);
				}
				else
				{
					req.setAttribute(LOGIN_UNSUCCESSFUL, true);
				}
			}
			else
			{
				LOGGER.info("Nothing in input fields");
				req.setAttribute(LOGIN_UNSUCCESSFUL, true);
			}

			req.getRequestDispatcher(pathForwarded).forward(req, resp);
		}
		else
		{
			LOGGER.info("authentication is not null");
			req.getRequestDispatcher(MAIN_JSP).forward(req, resp);
		}

	}

	/**
	 * Method that uses interface to get firstName
	 * @param userName
	 * @return String firstName of user
	 */
	private String getFirstName(String userName) {
		UserInterfaceImpl userOps = new UserInterfaceImpl();
		return userOps.getFirstName(userName);
	}

	/**
	 * Method that uses interface to authenticate user. 
	 * @param An user bean filled with user name and password
	 * @return boolean true means it's a valid user, false means invalid user
	 * @throws NoSuchProviderException 
	 * @throws NoSuchAlgorithmException 
	 */
	private boolean authenticate(User user) throws NoSuchAlgorithmException, NoSuchProviderException {
		UserInterfaceImpl userOps = new UserInterfaceImpl();
		return userOps.AuthenticateUser(user);
	}

}
