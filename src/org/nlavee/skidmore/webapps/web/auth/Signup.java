package org.nlavee.skidmore.webapps.web.auth;

import java.io.IOException;

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

public class Signup extends HttpServlet{
	
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
		
		String userName = req.getParameter(VarNames.USER_PARAM_FIELD_NAME);
		String password = req.getParameter(VarNames.PASSWORD_PARAM_FIELD_NAME);
		String email = req.getParameter(VarNames.EMAIL_PARAM_FIELD_NAME);
		
		NewUser newUser = new NewUser(userName, password, email);
		User user = registerNewUser(newUser);
		
		req.getSession().setAttribute(VarNames.USER_PARAM_FIELD_NAME, user.getUserName());
		req.getSession().setAttribute(VarNames.PASSWORD_PARAM_FIELD_NAME, user.getPassword());
		req.getSession().setAttribute(VarNames.EMAIL_PARAM_FIELD_NAME, email);
		req.getSession().setAttribute(VarNames.AUTHENTICATED_ATTRIBUTES_NAME, true);
		
		req.getRequestDispatcher(VarNames.SIGN_UP_RESULT_JSP).forward(req, resp);
	}

	private User registerNewUser(NewUser newUser) {
		
		/*
		 * Save in database
		 */
		UserInterfaceImpl userOps = new UserInterfaceImpl();
		User user = userOps.RegisterUser(newUser);
		
		return user;
	}
	
	
	
	
}
