package org.nlavee.skidmore.webapps.database.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.nlavee.skidmore.webapps.database.backends.DatabaseConnection;
import org.nlavee.skidmore.webapps.database.beans.Password;
import org.nlavee.skidmore.webapps.database.beans.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserMapping extends AbstractMapper{

	/**
	 * The logger
	 */
	private static final Logger LOG = Logger.getLogger(UserMapping.class);

	/**
	 * Constructor - no operation
	 */
	public UserMapping()
	{

	}

	/**
	 * 
	 * @param pwdObject
	 * @return
	 */
	public static boolean savePassword(Password pwdObject) {
		

		DatabaseConnection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<User> tempList = new ArrayList<User>();

		try
		{
			connection = getDatabaseConnection();
			stmt = connection.setupPreparedStatement("select * from user");
			rs = (ResultSet) connection.runQuery(stmt);

		}
		catch (Throwable throwable)
		{
			LOG.error("Error loading users from the database", throwable);
			throw new IllegalStateException(
				"Unable to retrieve list of users from the database",
				throwable);
		}
		
		return false;
	}

	// TODO Write methods for authentication.
	
	
	
	/**
	 * Retrieve all the users from the database.
	 * @return The array of users
	 */
	public User[] loadAllUsers()
	{
		DatabaseConnection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<User> tempList = new ArrayList<User>();

		try
		{
			connection = getDatabaseConnection();
			stmt = connection.setupPreparedStatement("select * from user");
			rs = (ResultSet) connection.runQuery(stmt);

			while (rs.next())
			{
//				User user = new User();
//				user.setDbKey(rs.getInt("id"));
//				user.setDateOfBirth(rs.getDate("dob"));
//				user.setEmailAddress(rs.getString("email_address"));
//				user.setFirstName(rs.getString("first_name"));
//				user.setLastName(rs.getString("last_name"));
//				tempList.add(user);
			}
			connection.closeResultSet(rs);
		}
		catch (Throwable throwable)
		{
			LOG.error("Error loading users from the database", throwable);
			throw new IllegalStateException(
				"Unable to retrieve list of users from the database",
				throwable);
		}

		return tempList.toArray(new User[tempList.size()]);
	}
}
