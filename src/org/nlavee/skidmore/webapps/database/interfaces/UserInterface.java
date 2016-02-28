package org.nlavee.skidmore.webapps.database.interfaces;

import java.util.Date;

import org.nlavee.skidmore.webapps.database.beans.NewUser;
import org.nlavee.skidmore.webapps.database.beans.User;

public interface UserInterface {

	public boolean AuthenticateUser(User user);
	public User RegisterUser(NewUser user);
	public boolean saveMessage(String body, Date date);
}
