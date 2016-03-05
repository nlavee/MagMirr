package org.nlavee.skidmore.webapps.database.interfaces.impl;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.nlavee.skidmore.webapps.database.beans.Message;
import org.nlavee.skidmore.webapps.database.beans.NewUser;
import org.nlavee.skidmore.webapps.database.beans.Password;
import org.nlavee.skidmore.webapps.database.beans.User;
import org.nlavee.skidmore.webapps.database.dao.UserMapping;
import org.nlavee.skidmore.webapps.database.interfaces.UserInterface;
import org.nlavee.skidmore.webapps.web.utils.PasswordUtils;

public class UserInterfaceImpl implements UserInterface{

	@Override
	public boolean AuthenticateUser(User user) throws NoSuchAlgorithmException, NoSuchProviderException {
		
		String userName = user.getUserName();
		String password = user.getPassword();
		
		/*
		 * TODO Go to database and fetch pwHash & salt
		 */
		boolean matchingPassword = UserMapping.isMatchingPassword(password, userName);
		
		if(!matchingPassword) return false;
		else return true;
	}

	@Override
	public User RegisterUser(NewUser user) throws NoSuchAlgorithmException, NoSuchProviderException {
		
		String userName = user.getUserName();
		String pwd = user.getPassword();
		String email = user.getEmail();
		String firstName = user.getFirstName();
		String lastName = user.getLastName();
		
		/*
		 * TODO Save the above to database
		 */
		Password pwdObject = PasswordUtils.generateSaltAndHash(pwd);
		boolean success = UserMapping.createUser(pwdObject, user);
		
		/*
		 * Create the new User Bean
		 */
		User userReturn = new User(userName, pwd, firstName);
		
		return userReturn;
	}

	@Override
	public boolean saveMessage(String body, Date date) {
		
		SimpleDateFormat dtFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
		String dateFormatted = dtFormat.format(date);
		
		Message newMessage = new Message(body, dateFormatted);
		
		/*
		 * TODO Save message to database
		 */
		
		return true;
	}

}
