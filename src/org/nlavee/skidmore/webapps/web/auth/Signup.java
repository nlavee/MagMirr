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
import org.nlavee.skidmore.webapps.database.beans.NewUser;
import org.nlavee.skidmore.webapps.database.beans.User;
import org.nlavee.skidmore.webapps.database.interfaces.impl.UserInterfaceImpl;
import org.nlavee.skidmore.webapps.web.VarNames;

public class Signup extends HttpServlet implements VarNames{

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
	private static Logger LOGGER = Logger.getLogger(Signup.class);

	public Signup(){
	}

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
		resp.sendRedirect(req.getContextPath());
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
		signup(req, resp);
	}

	private void signup(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {

		/*
		 * If they didn't fill in everything
		 */
		if(req.getParameter(USER_PARAM_FIELD_NAME) == null || 
				req.getParameter(PASSWORD_PARAM_FIELD_NAME) == null ||
				req.getParameter(EMAIL_PARAM_FIELD_NAME) == null ||
				req.getParameter(FIRST_NAME_PARAM_FIELD_NAME) == null ||
				req.getParameter(LAST_NAME_PARAM_FIELD_NAME) == null
				)
		{
			// TODO need to determine appropriate response here
			req.getRequestDispatcher(LOGIN_JSP).forward(req, resp);
		}
		else
		{

			String userName = req.getParameter(USER_PARAM_FIELD_NAME);
			String password = req.getParameter(PASSWORD_PARAM_FIELD_NAME);
			String email = req.getParameter(EMAIL_PARAM_FIELD_NAME);
			String firstName = req.getParameter(FIRST_NAME_PARAM_FIELD_NAME);
			String lastName = req.getParameter(LAST_NAME_PARAM_FIELD_NAME);

			/*
			 * Check if they leave blank
			 */
			if(userName.trim().isEmpty() || password.trim().isEmpty()
					|| email.trim().isEmpty() || firstName.trim().isEmpty() ||
					lastName.trim().isEmpty())
			{
				// TODO need to determine appropriate response here
				req.getRequestDispatcher(LOGIN_JSP).forward(req, resp);
			}
			else
			{
				NewUser newUser = new NewUser(userName, password, email, firstName, lastName);
				User user = null;
				try {
					user = registerNewUser(newUser);
				} catch (NoSuchAlgorithmException e) {
					LOGGER.error("Fail at password step", e);
				} catch (NoSuchProviderException e) {
					LOGGER.error("Fail at password step", e);
				}

				String fwdPath = MAIN_JSP;
				if(user != null)
				{
					req.getSession().setAttribute(USER_PARAM_FIELD_NAME, user.getUserName());
					req.getSession().setAttribute(PASSWORD_PARAM_FIELD_NAME, user.getPassword());
					req.getSession().setAttribute(FIRST_NAME_PARAM_FIELD_NAME, user.getFirstName());
					req.getSession().setAttribute(EMAIL_PARAM_FIELD_NAME, email);
					req.getSession().setAttribute(AUTHENTICATED_ATTRIBUTES_NAME, true);
					fwdPath = SIGN_UP_RESULT_JSP;
				}
				
				req.getRequestDispatcher(fwdPath).forward(req, resp);
			}
		}
	}

	private User registerNewUser(NewUser newUser) throws NoSuchAlgorithmException, NoSuchProviderException {

		/*
		 * Save in database
		 */
		UserInterfaceImpl userOps = new UserInterfaceImpl();
		User user = userOps.RegisterUser(newUser);

		return user;
	}




}
