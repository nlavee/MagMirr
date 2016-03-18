package org.nlavee.skidmore.webapps.web;

public interface VarNames {
	
	public final static String USER_PARAM_FIELD_NAME = "user_name";
	public final static String PASSWORD_PARAM_FIELD_NAME = "password";
	public final static String REMEMBER_PARAM_FIELD_NAME = "remember";
	public final static String EMAIL_PARAM_FIELD_NAME = "email";
	public final static String FIRST_NAME_PARAM_FIELD_NAME = "first_name";
	public final static String LAST_NAME_PARAM_FIELD_NAME = "last_name";
	public final static String AUTHENTICATED_ATTRIBUTES_NAME = "authenticated";
	public final static String ABOUT_MODE = "about";
	public final static String CONTACT_MODE = "contact";
	public final static String LOGIN_MODE = "login";
	public final static String MAIN_MODE = "main";
	public final static String STATUS_MODE = "status";
	public final static String LOGOUT_MODE = "logout";
	public final static String MAIN_TEXT_BOX ="message_box";
	public final static String MAIN_ZIPCODE = "zipcode";
	public final static String MAIN_NEWS_SELECTION = "news_selection";
	public static final String ZIPCODE_WEATHER = "zipcode_weather";
	public static final String TEMP_WEATHER = "temp_weather";
	public static final String LOGIN_UNSUCCESSFUL = "login_unsuccessful";
	public static final String LYFT_AUTHENTICATED = "lyft_authenticated";
	public static final String LYFT_AUTHENTICATED_TIME_OUT = "lyft_authenticated_time_out";
	
	/**
	 * Different sections available
	 */
	public static final String HOME_SECTION = "home";
	public static final String WORLD_SECTION = "world";
	public static final String NATIONAL_SECTION = "national";
	public static final String POLITICS_SECTION = "politics";
	public static final String NY_REGION_SECTION = "ny region";
	public static final String BUSINESS_SECTION = "business";
	public static final String OPINION_SECTION = "opinion";
	public static final String TECHNOLOGY_SECTION = "technology";
	public static final String SCIENCE_SECTION = "science";
	public static final String HEALTH_SECTION = "health";
	public static final String SPORTS_SECTION = "sports";
	public static final String ARTS_SECTION = "arts";
	public static final String FASHION_SECTION = "fashion";
	public static final String DINING_SECTION = "dining";
	public static final String TRAVEL_SECTION = "travel";
	public static final String MAGAZINE_SECTION = "magazine";
	public static final String REAL_ESTATE_SECTION = "real estate";
	public static final String[] NEWS_SECTIONS = { 
			HOME_SECTION, WORLD_SECTION, NATIONAL_SECTION, POLITICS_SECTION, NY_REGION_SECTION, BUSINESS_SECTION,
			OPINION_SECTION, TECHNOLOGY_SECTION, SCIENCE_SECTION, HEALTH_SECTION, SPORTS_SECTION, ARTS_SECTION, 
			FASHION_SECTION, DINING_SECTION, TRAVEL_SECTION, MAGAZINE_SECTION, REAL_ESTATE_SECTION
			};

	/*
	 * JSP path to login.jsp
	 */
	public final static String LOGIN_JSP = "/WEB-INF/hiddenjsp/login.jsp";

	/*
	 * JSP path to main.jsp
	 */
	public final static String MAIN_JSP = "/WEB-INF/hiddenjsp/main.jsp";
	
	/*
	 * JSP path to signUpResult.jsp
	 */
	public final static String SIGN_UP_RESULT_JSP = "/WEB-INF/hiddenjsp/signUpResult.jsp";
	
	/*
	 * JSP path to status.jsp
	 */
	public final static String STATUS_JSP = "/WEB-INF/hiddenjsp/status.jsp";
	
	/*
	 * JSP path to about.jsp
	 */
	public final static String ABOUT_JSP = "/WEB-INF/hiddenjsp/about.jsp";
	
	/*
	 * JSP path to contact.jsp
	 */
	public final static String CONTACT_JSP = "/WEB-INF/hiddenjsp/contact.jsp";

	/*
	 * JSP path to error.jsp
	 */
	public final static String ERROR_JSP = "/WEB-INF/hiddenjsp/error.jsp";	

}