package org.nlavee.skidmore.webapps.web.api.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;
import org.nlavee.skidmore.webapps.web.api.WeatherInterface;

public class WeatherAPI implements WeatherInterface{

	@Override
	public JSONObject getWeather(Integer zipcode) {

		StringBuilder res = new StringBuilder();

		/*
		 * Call to get weather from current zipcode
		 */

		try {

			String urlLocation = "http://api.openweathermap.org/data/2.5/weather?zip=";
			StringBuilder apiRequestURL = new StringBuilder();
			apiRequestURL.append(urlLocation);
			apiRequestURL.append(zipcode.toString());
			apiRequestURL.append(",us&APPID=");
			apiRequestURL.append("4f036b93c98e481dfbddadf07602900f"); // need to revise how this got read.
			apiRequestURL.append("&units=metric");
			
			URL url = new URL(apiRequestURL.toString());
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
			System.out.println("Output from Weather API .... \n");
			while ((output = br.readLine()) != null) {
				res.append(output);
				System.out.println(output);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	    JSONObject jsonObj = new JSONObject(res.toString());

		return jsonObj;
	}

}
