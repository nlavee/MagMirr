package org.nlavee.skidmore.webapps.web.auth;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.nlavee.skidmore.webapps.database.beans.User;
import org.nlavee.skidmore.webapps.database.interfaces.impl.UserInterfaceImpl;
import org.nlavee.skidmore.webapps.web.VarNames;

public class Login extends HttpServlet {
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
		System.out.println("We get into login class");

		if(req.getSession().getAttribute(VarNames.AUTHENTICATED_ATTRIBUTES_NAME) == null)
		{
			System.out.println("authentication is null");
			String pathForwarded = VarNames.LOGIN_JSP; 

			if( req.getParameter(VarNames.USER_PARAM_FIELD_NAME) != null && 
					req.getParameter(VarNames.PASSWORD_PARAM_FIELD_NAME) != null)
			{
				String userName = req.getParameter(VarNames.USER_PARAM_FIELD_NAME);
				String password = req.getParameter(VarNames.PASSWORD_PARAM_FIELD_NAME);

				User userInvalidated = new User(userName, password);

				boolean isExistingUser = authenticate(userInvalidated);

				if(req.getParameter(VarNames.REMEMBER_PARAM_FIELD_NAME) != null)
				{
					// do something to remember, possibly put into session
					
				}

				if( isExistingUser )
				{
					System.out.println("this is an existing user");
					pathForwarded = VarNames.MAIN_JSP;
					req.getSession().setAttribute(VarNames.USER_PARAM_FIELD_NAME, userName);
					req.getSession().setAttribute(VarNames.AUTHENTICATED_ATTRIBUTES_NAME, true);
				}
			}
			else
			{
				System.out.println("Nothing in input fields");
			}

			req.getRequestDispatcher(pathForwarded).forward(req, resp);
		}
		else
		{
			System.out.println("authentication is not null");
			req.getRequestDispatcher(VarNames.MAIN_JSP).forward(req, resp);
		}

	}

	/**
	 * Method that uses interface to authenticate user. 
	 * @param An user bean filled with user name and password
	 * @return boolean true means it's a valid user, false means invalid user
	 */
	private boolean authenticate(User user) {
		UserInterfaceImpl userOps = new UserInterfaceImpl();
		return userOps.AuthenticateUser(user);
	}

}
