package org.nlavee.skidmore.webapps.database.interfaces.impl;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.nlavee.skidmore.webapps.database.beans.Message;
import org.nlavee.skidmore.webapps.database.beans.NewUser;
import org.nlavee.skidmore.webapps.database.beans.Password;
import org.nlavee.skidmore.webapps.database.beans.User;
import org.nlavee.skidmore.webapps.database.dao.ObjMapping;
import org.nlavee.skidmore.webapps.database.interfaces.UserInterface;
import org.nlavee.skidmore.webapps.web.utils.PasswordUtils;

public class UserInterfaceImpl implements UserInterface{

	@Override
	public boolean AuthenticateUser(User user) throws NoSuchAlgorithmException, NoSuchProviderException {
		
		String userName = user.getUserName();
		String password = user.getPassword();
		
		ObjMapping um = new ObjMapping();
		boolean matchingPassword = um.isMatchingPassword(password, userName);
		
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
		
		Password pwdObject = PasswordUtils.generateSaltAndHash(pwd);
		ObjMapping um = new ObjMapping();
		boolean success = um.createUser(pwdObject, user);
		
		/*
		 * Create the new User Bean
		 */
		User userReturn = new User(userName, pwd, firstName);
		
		return userReturn;
	}

	@Override
	public boolean saveMessage(String body, Date date, String userName) {
		
		Message newMessage = new Message(body, date);
		
		/*
		 * TODO Save message to database
		 */
		ObjMapping om = new ObjMapping();
		boolean success = om.saveMessage(newMessage, userName);
		
		return success;
	}

	@Override
	public String getFirstName(String userName) {
		ObjMapping um = new ObjMapping();
		return um.getFirstName(userName);
	}

}
