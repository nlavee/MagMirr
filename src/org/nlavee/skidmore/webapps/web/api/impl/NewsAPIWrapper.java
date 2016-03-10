package org.nlavee.skidmore.webapps.web.api.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.nlavee.skidmore.webapps.web.APIKEYS;
import org.nlavee.skidmore.webapps.web.VarNames;
import org.nlavee.skidmore.webapps.web.api.NewsInterface;
import org.nlavee.skidmore.webapps.web.model.NewsObj;

public class NewsAPIWrapper implements NewsInterface, VarNames, APIKEYS{

	/**
	 * Logger Instance
	 */
	private static Logger LOGGER = Logger.getLogger(NewsAPIWrapper.class);

	@Override
	public ArrayList<NewsObj> chooseSections(String[] sections) {
		ArrayList<NewsObj> returnedArticles = new ArrayList<>();
		for(String section : sections)
		{
			LOGGER.info("Received the following requested section: " + section);
			ArrayList<NewsObj> returnedEachSection = processOneSection(section);
		}
		if(returnedArticles.size() == 0) return null; 
		return returnedArticles;
	}

	/**
	 * This method makes an API request for one section of news.
	 * @param section
	 * @return 5 articles that appears first on the returned list from the API.
	 */
	private ArrayList<NewsObj> processOneSection(String section) {
		StringBuilder res = new StringBuilder();
		
		String urlBase = "http://api.nytimes.com/svc/topstories/v1/";
		StringBuilder apiRequestURL = new StringBuilder();
		apiRequestURL.append(urlBase);
		apiRequestURL.append(section);
		apiRequestURL.append(".json?api-key=");
		apiRequestURL.append(NEWS_API_KEY);

		URL url;
		try {
			url = new URL(apiRequestURL.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			LOGGER.info("Output from News API .... \n");
			while ((output = br.readLine()) != null) {
				res.append(output);
				LOGGER.info(output);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {
			LOGGER.error("URL formed wrongly in News API", e);
		} catch (IOException e) {
			LOGGER.error("IO Exception in News API", e);
		}

		// convert string to JSONObject
		JSONObject jsonObj = new JSONObject(res.toString());
		
		ArrayList<NewsObj> parsedResult = null;
		try {
			parsedResult = parseSection(jsonObj);
		} catch (ParseException e) {
			LOGGER.error("Unable to parse Published Date", e);
		}
		
		if(parsedResult != null) return parsedResult;
		else return new ArrayList<NewsObj>();
	}

	/**
	 * Parsing JSON Object to get a list of 5 top articles from JSON returned.
	 * @param jsonObj
	 * @return
	 * @throws ParseException 
	 */
	private ArrayList<NewsObj> parseSection(JSONObject jsonObj) throws ParseException {
		
		ArrayList<NewsObj> res = new ArrayList<>();
		
		int numResults = jsonObj.getInt("num_results");
		
		if(numResults > 0)
		{
			JSONArray resultArray = jsonObj.getJSONArray("results");
			
			for(int i = 0 ; i < 5 ; i++)
			{
				JSONObject oneEntry = resultArray.getJSONObject(i);
				String section = oneEntry.getString("section");
				String title = oneEntry.getString("title");
				String newsAbstract = oneEntry.getString("abstract");
				String url = oneEntry.getString("url");
				String dateString = oneEntry.getString("published_date");
				DateFormat df = new SimpleDateFormat("dd MMMM yyyy");
				Date dateFormatted = df.parse(dateString);
				JSONArray tagsList = oneEntry.getJSONArray("des_facet");
				ArrayList<String> tags = new ArrayList<>();
				for(int j = 0 ; j < tagsList.length(); j++)
				{
					String tag = tagsList.getString(j);
					tags.add(tag);
				}
				String[] tagsListArray = (String[]) tags.toArray();
				JSONArray multimedia = oneEntry.getJSONArray("multimedia");
				JSONObject standardMultimediaObject = multimedia.getJSONObject(0);
				String urlMultimedia = standardMultimediaObject.getString("url");
				
				NewsObj newNews = new NewsObj(title, newsAbstract, url, dateFormatted, urlMultimedia, section, tagsListArray);
				res.add(newNews);
			}
			
			return res;
		}
		else return null;
	}


}
