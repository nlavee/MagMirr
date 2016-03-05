package org.nlavee.skidmore.webapps.database.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.nlavee.skidmore.webapps.database.backends.DatabaseConnection;
import org.nlavee.skidmore.webapps.database.beans.NewUser;
import org.nlavee.skidmore.webapps.database.beans.Password;
import org.nlavee.skidmore.webapps.database.beans.User;
import org.nlavee.skidmore.webapps.web.utils.PasswordUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapping extends AbstractMapper {

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
	 * @param user 
	 * @return
	 */
	public static boolean createUser(Password pwdObject, NewUser user) {
		boolean ret = false;

		String emailAddress = user.getEmail();
		String userName = user.getUserName();

		/*
		 * Check to see whether this email/username has been taken
		 */
		boolean isExisting = checkExistingCredentials(emailAddress, userName);

		/*
		 * Persist information is username and email are not existing
		 */
		if(!isExisting)
		{
			boolean success = persistUser(pwdObject, user);
			if(success)
			{
				ret = true;
			}
		}

		return ret;
	}

	/**
	 * Method to persist user information into db.
	 * @param pwdObject
	 * @param user
	 * @return boolean true for a successful process, false o/w
	 */
	private static boolean persistUser(Password pwdObject, NewUser user) {
		boolean ret = false;

		DatabaseConnection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		/*
		 * Persist pwd
		 */
		String pwdHash = pwdObject.getPwdHash();
		String pwdSalt = pwdObject.getSalt();
		Integer id = -1;

		try {
			connection = getDatabaseConnection();
			stmt = connection.setupPreparedStatement(
					"insert into password (pw_hash, salt) values (?,?)"	
					);
			stmt.setString(1, pwdHash);
			stmt.setString(2, pwdSalt);

			connection.runUpdate(stmt);

			stmt = connection.setupPreparedStatement(
					"select id from password where pw_hash = ? and salt = ?"
					);
			stmt.setString(1, pwdHash);
			stmt.setString(2, pwdSalt);

			rs = connection.runQuery(stmt);

			while(rs.next())
			{
				id = rs.getInt(1);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			LOG.error("Fail at persisting password for new user", e);
		}

		/*
		 * Persist user information
		 */
		String userName = user.getUserName();
		String email = user.getEmail();
		String firstName = user.getFirstName();
		String lastName = user.getLastName();

		try {
			connection = getDatabaseConnection();
			stmt = connection.setupPreparedStatement(
					"insert into user (username, first_name, last_name, email, password_id) values (?,?,?,?)"
					);
			stmt.setString(1, userName);
			stmt.setString(2, firstName);
			stmt.setString(3, lastName);
			stmt.setInt(4, id); // just persist even if id remains -1, we'll do an update later.

			connection.runUpdate(stmt);

			ret = true;

		} catch (SQLException e) {
			LOG.error("Fail at persisting user information into user table", e);
		}
		finally
		{
			connection.closeResultSet(rs);
		}

		return ret;
	}

	/**
	 * Method to check whether the credentials the user entered are existing in db. i.e. 
	 * one email, username can be only be associated with one person
	 * @param emailAddress
	 * @param userName
	 * @return boolean true for credentials already existing, false o/w.
	 */
	private static boolean checkExistingCredentials(String emailAddress,
			String userName) {
		boolean ret = false;

		DatabaseConnection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		if(emailAddress.isEmpty() || userName.isEmpty())
		{
			LOG.error("Empty email address or username while persisting to database.");
			ret = true;
		}
		else
		{
			// check existing email
			try {
				connection = getDatabaseConnection();
				stmt = connection.setupPreparedStatement(
						"select username from user where email = ?"
						);
				stmt.setString(1, emailAddress);
				rs = connection.runQuery(stmt);

				String userNameExisting = "";
				while(rs.next())
				{
					userNameExisting = rs.getString("username");
				}

				// if the rs returns an existing username
				if(!userNameExisting.isEmpty())
				{
					ret = true;
				}
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				LOG.error("Fail to set up prepared stm for email checking", e);
			}
			finally
			{
				// check existing username, no matter what the check for email goes through
				try {
					stmt = connection.setupPreparedStatement(
							"select username from user where username = ?"
							);
					stmt.setString(1, userName);
					rs = connection.runQuery(stmt);

					String userNameExisting = "";
					while(rs.next())
					{
						userNameExisting = rs.getString("username");
					}

					// if the rs returns an existing username
					if(!userNameExisting.isEmpty())
					{
						ret = true;
					}
				} catch (SQLException e) {
					LOG.error("Fail to set up prepared stm for username checking", e);
				}
				finally
				{
					connection.closeResultSet(rs);
				}
			}
		}
		return ret;
	}

	/**
	 * Method to check password against what is in the db
	 * @param pwdObject
	 * @param userName
	 * @return true if matching, else o/w
	 */
	public static boolean isMatchingPassword(String pw, String userName) {
		boolean ret = false;

		int pwId = getPasswordID(userName);

		if(pwId != -1)
		{
			DatabaseConnection connection = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;

			try {
				connection = getDatabaseConnection();
				stmt = connection.setupPreparedStatement(
						"select pw_hash, salt from password where id = ?"
						);
				stmt.setInt(1, pwId);

				rs = connection.runQuery(stmt);


				while(rs.next())
				{
					String pwHash = rs.getString("pw_hash");
					String pwSalt = rs.getString("salt");
					Password pwdObject = new Password(pwHash, pwSalt);
					ret = PasswordUtils.checkPassword(pw, pwdObject);
				}
			} catch (SQLException e) {
				LOG.error("Could not retrieve password based on username", e);
			}
			finally
			{
				connection.closeResultSet(rs);
			}

		}
		return ret;
	}

	private static int getPasswordID(String userName) {
		DatabaseConnection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int pwID = -1;

		try {
			connection = getDatabaseConnection();
			stmt = connection.setupPreparedStatement(
					"select password_id from user where username = ?"
					);
			stmt.setString(1, userName);

			rs = connection.runQuery(stmt);


			while(rs.next())
			{
				pwID = rs.getInt("password_id");
			}
		} catch (SQLException e) {
			LOG.error("Could not retrieve password based on username", e);
		}
		finally
		{
			connection.closeResultSet(rs);
		}

		return pwID;
	}

}
