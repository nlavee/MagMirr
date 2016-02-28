package org.nlavee.skidmore.webapps.database.interfaces.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.nlavee.skidmore.webapps.database.beans.Message;
import org.nlavee.skidmore.webapps.database.beans.NewUser;
import org.nlavee.skidmore.webapps.database.beans.User;
import org.nlavee.skidmore.webapps.database.interfaces.UserInterface;

public class UserInterfaceImpl implements UserInterface{

	@Override
	public boolean AuthenticateUser(User user) {
		
		String userName = user.getUserName();
		String password = user.getPassword();
		
		/*
		 * TODO Go to database and fetch pwHash & salt
		 */
		//PasswordObj pwdObj = getPwdObject(userName);
		
		return true;
	}

	@Override
	public User RegisterUser(NewUser user) {
		
		String userName = user.getUserName();
		String pwd = user.getPassword();
		String email = user.getEmail();
		String firstName = user.getFirstName();
		String lastName = user.getLastName();
		
		/*
		 * TODO Save the above to database
		 */
		
		
		
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
