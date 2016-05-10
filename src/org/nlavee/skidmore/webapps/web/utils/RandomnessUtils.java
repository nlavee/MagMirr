package org.nlavee.skidmore.webapps.web.utils;

import java.security.SecureRandom;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

public class RandomnessUtils {
	
	/**
	 * Logger Instance
	 */
	private static Logger LOGGER = Logger.getLogger(RandomnessUtils.class);
	
	public RandomnessUtils()
	{
		LOGGER.info("Using Randomness Utils");
	}
	
	public String generateRandomToken() {
		Random random = new SecureRandom();
		int length = 30;
		byte[] randomValue;
		byte[] base64Values;
		
		randomValue = new byte[length];
		random.nextBytes(randomValue);
		
		base64Values = Base64.encodeBase64(randomValue);
		LOGGER.info("base64");
		
		String finalValue = new String(base64Values, 0, length);
		LOGGER.info(finalValue);
		return finalValue;
	}
	
	public static void main(String[] args)
	{
		RandomnessUtils ran = new RandomnessUtils();
		System.out.println(ran.generateRandomToken());
	}
	
}
