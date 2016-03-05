package org.nlavee.skidmore.webapps.web.utils;

/**
 * This class is used to generate 
 * Adapted from : http://howtodoinjava.com/security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/#md5-salt
 */


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import org.apache.log4j.Logger;
import org.nlavee.skidmore.webapps.database.beans.Password;

public class PasswordUtils {

	/**
	 * Logger Instance
	 */
	private static Logger LOGGER = Logger.getLogger(PasswordUtils.class);
	
	public PasswordUtils(){
		LOGGER.info("PasswordUtils constructed.");
	}

	/**
	 * Method that do the salt and hash of given password
	 * @param pwd
	 * @return Password object
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 */
	public static Password generateSaltAndHash(String pwd) throws NoSuchAlgorithmException, NoSuchProviderException
	{
		LOGGER.info("Starting to generate Salt and Hash for given password");
		String salt = getSalt();
		String hash = getSecurePassword(pwd, salt);
		
		Password pwdObject = new Password(hash, salt);
		
		return pwdObject;
	}
	
	/**
	 * Method to check an input string password against what we have in the db.
	 * @param input
	 * @param db
	 * @return boolean true or false
	 */
	public static boolean checkPassword(String input, Password db)
	{
		LOGGER.info("Checking the two Password object");
		
		boolean valid = false;
		if(getSecurePassword(input, db.getSalt()).equals(db.getPwdHash()))
		{
			valid = true;
		}
		
		return valid;
	}

	/**
	 * Generate password based on original password and salt
	 * @param passwordToHash
	 * @param salt
	 * @return
	 */
	private static String getSecurePassword(String passwordToHash, String salt)
	{
		String generatedPassword = null;
		try {
			// Create MessageDigest instance for MD5
			MessageDigest md = MessageDigest.getInstance("MD5");
			//Add password bytes to digest
			md.update(salt.getBytes());
			//Get the hash's bytes 
			byte[] bytes = md.digest(passwordToHash.getBytes());
			//This bytes[] has bytes in decimal format;
			//Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			for(int i=0; i< bytes.length ;i++)
			{
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			//Get complete hashed password in hex format
			generatedPassword = sb.toString();
		} 
		catch (NoSuchAlgorithmException e) {
			LOGGER.error("Unable to get secure password", e);
		}
		return generatedPassword;
	}

	/**
	 * Method to add salt
	 * @return String salt
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 */
	private static String getSalt() throws NoSuchAlgorithmException, NoSuchProviderException
	{
		//Always use a SecureRandom generator
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
		//Create array for salt
		byte[] salt = new byte[16];
		//Get a random salt
		sr.nextBytes(salt);
		//return salt
		return salt.toString();
	}
	
	/**
	 * Test with a password
	 * @param args
	 */
	public static void main(String[] args) 
	{
		String passwordToHash = "password";
		String salt = null;
		try {
			salt = PasswordUtils.getSalt();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(salt);
		String securePassword = PasswordUtils.getSecurePassword(passwordToHash, salt);
		System.out.println(securePassword); //Prints 83ee5baeea20b6c21635e4ea67847f66

		String regeneratedPassowrdToVerify = PasswordUtils.getSecurePassword(passwordToHash, salt);
		System.out.println(regeneratedPassowrdToVerify); //Prints 83ee5baeea20b6c21635e4ea67847f66
	}
}
