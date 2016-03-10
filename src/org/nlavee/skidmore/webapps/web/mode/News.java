package org.nlavee.skidmore.webapps.web.mode;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.nlavee.skidmore.webapps.web.VarNames;
import org.nlavee.skidmore.webapps.web.api.impl.NewsAPIWrapper;
import org.nlavee.skidmore.webapps.web.model.NewsObj;

public class News extends HttpServlet implements VarNames {
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
	private static Logger LOGGER = Logger.getLogger(News.class);
 
	public News(){
		
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
		req.getRequestDispatcher(MAIN_MODE).forward(req, resp);
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
		getNews(req, resp);
	}

	/**
	 * Method that processes getting news and send a response to the user.
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void getNews(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String fwdPath = ERROR_JSP;
		ArrayList<String> sectionsRequested = new ArrayList<>();
		for(String sectionValue : VarNames.NEWS_SECTIONS)
		{
			if(req.getParameter(sectionValue) != null)
			{
				String sectionSelected = req.getParameter(sectionValue);
				sectionsRequested.add(sectionSelected.trim());
				LOGGER.info("Requested section: " + sectionSelected);
			}
		}
		
		ArrayList<NewsObj> returnedTop5s = getNewsSection(sectionsRequested);
		
		if(returnedTop5s != null)
		{
			// forward this to the other client
			// do something with returnedTop5s.
			for(int i = 0 ; i < returnedTop5s.size(); i += 5)
			{
				NewsObj newsArticle = returnedTop5s.get(i);
				String varNames = ("top5-" + i);
				req.setAttribute(varNames, newsArticle.getTitle());
				req.setAttribute("i", i);
			}
			fwdPath = MAIN_JSP;
		}
		else 
		{
			// gives errors
			LOGGER.error("Failed to get top 5s. Returned null");
			fwdPath = ERROR_JSP;
		}
		req.getRequestDispatcher(fwdPath).forward(req, resp);
		
	}

	/**
	 * Method that requires the use of API.
	 * @param sectionsRequested
	 * @return boolean true if the process was successful, else returns false
	 */
	private ArrayList<NewsObj> getNewsSection(ArrayList<String> sectionsRequested) {
		NewsAPIWrapper newsAPI = new NewsAPIWrapper();
		
		ArrayList<NewsObj> returnedList = newsAPI.chooseSections(sectionsRequested.toArray(new String[0]));
		
		
		if(returnedList == null) return null;
		else return returnedList;
	}
}
