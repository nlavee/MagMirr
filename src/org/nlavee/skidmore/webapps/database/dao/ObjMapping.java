package org.nlavee.skidmore.webapps.database.dao;

import org.apache.log4j.Logger;
import org.nlavee.skidmore.webapps.database.backends.DatabaseConnection;
import org.nlavee.skidmore.webapps.database.beans.Message;
import org.nlavee.skidmore.webapps.database.beans.NewUser;
import org.nlavee.skidmore.webapps.database.beans.Password;
import org.nlavee.skidmore.webapps.web.utils.PasswordUtils;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ObjMapping extends AbstractMapper {

	/**
	 * The logger
	 */
	private static final Logger LOG = Logger.getLogger(ObjMapping.class);

	/**
	 * Constructor - no operation
	 */
	public ObjMapping()
	{
		super();
	}

	/**
	 * 
	 * @param pwdObject
	 * @param user 
	 * @return
	 */
	public boolean createUser(Password pwdObject, NewUser user) {
		boolean ret = false;

		String emailAddress = user.getEmail();
		String userName = user.getUserName();

		/*
		 * Check to see whether this email/username has been taken
		 */
		boolean isExisting = this.checkExistingCredentials(emailAddress, userName);

		/*
		 * Persist information is username and email are not existing
		 */
		if(!isExisting)
		{
			boolean success = this.persistUser(pwdObject, user);
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
	private boolean persistUser(Password pwdObject, NewUser user) {
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
			connection = this.getDatabaseConnection();
			stmt = connection.setupPreparedStatement(
					"insert into user (username, first_name, last_name, email, password_id) values (?,?,?,?,?)"
					);
			stmt.setString(1, userName);
			stmt.setString(2, firstName);
			stmt.setString(3, lastName);
			stmt.setString(4, email);
			stmt.setInt(5, id); // just persist even if id remains -1, we'll do an update later.

			connection.runUpdate(stmt);

			ret = true;
			connection.closeResultSet(rs);
		} catch (SQLException e) {
			LOG.error("Fail at persisting user information into user table", e);
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
	private boolean checkExistingCredentials(String emailAddress,
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
				connection = this.getDatabaseConnection();
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
					connection.closeResultSet(rs);
				} catch (SQLException e) {
					LOG.error("Fail to set up prepared stm for username checking", e);
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
	public boolean isMatchingPassword(String pw, String userName) {
		boolean ret = false;

		int pwId = getPasswordID(userName);

		if(pwId != -1)
		{
			DatabaseConnection connection = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;

			try {
				connection = this.getDatabaseConnection();
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
				connection.closeResultSet(rs);
			} catch (SQLException e) {
				LOG.error("Could not retrieve password based on username", e);
			}

		}
		return ret;
	}

	private int getPasswordID(String userName) {
		DatabaseConnection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int pwID = -1;

		try {
			connection = this.getDatabaseConnection();
			stmt = connection.setupPreparedStatement(
					"select password_id from user where username = ?"
					);
			stmt.setString(1, userName);

			rs = connection.runQuery(stmt);


			while(rs.next())
			{
				pwID = rs.getInt("password_id");
			}
			connection.closeResultSet(rs);
			
		} catch (SQLException e) {
			LOG.error("Could not retrieve password based on username", e);
		}

		return pwID;
	}

	/**
	 * Method to get firstName of user
	 * @param userName
	 * @return
	 */
	public String getFirstName(String userName) {
		DatabaseConnection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String firstName = "";
		
		try {
			connection = this.getDatabaseConnection();
			stmt = connection.setupPreparedStatement(
					"select first_name from user where username = ?"
					);
			stmt.setString(1, userName);

			rs = connection.runQuery(stmt);


			while(rs.next())
			{
				firstName = rs.getString("first_name");
			}
			connection.closeResultSet(rs);
		} catch (SQLException e) {
			LOG.error("Could not retrieve first_name based on username", e);
		}

		return firstName;
	}
	
	/**
	 * Method that saves the zipcode that user wants to use for retrieving weather data
	 * @param zipcode
	 * @param userName
	 * @return boolean success if operation is successful, otherwise, returns false.
	 */
	public void saveWeatherLocation(Integer zipcode, String userName)
	{
		DatabaseConnection connection = null;
		PreparedStatement stmt = null;
		
		int Id = queryForID(userName);
		
		try {
			connection = this.getDatabaseConnection();
			stmt = connection.setupPreparedStatement(
					"insert into weather (user_id, zipcode) values (?,?) on duplicate key update zipcode = (?);"
					);
			stmt.setInt(1, Id);
			stmt.setInt(2, zipcode);
			stmt.setInt(3, zipcode);

			connection.runUpdate(stmt);
		} catch (SQLException e) {
			LOG.error("Could not update weather location ", e);
		}
		finally
		{
			if(stmt != null)
			{
				try {
					stmt.close();
				} catch (SQLException e) {
					LOG.error("Could not close statement", e);
				}
			}
			if(connection != null)
			{
				try
				{
					connection.closeStatement(stmt);
				}
				catch(Throwable e)
				{
					LOG.error("Could not close connection ", e);
				}
			}
		}
	}
	
	private int queryForID(String userName) {
		DatabaseConnection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int Id = -1;
		
		try {
			connection = this.getDatabaseConnection();
			stmt = connection.setupPreparedStatement(
			"select id from user where username = ?;"
					);
			stmt.setString(1, userName);
			rs = stmt.executeQuery();
			
			while(rs.next())
			{
				Id = rs.getInt("id");
			}
		}
		catch( SQLException e)
		{
			LOG.error("Cannot query for id", e);
		}
		
		return Id;
		
	}

	public void saveNewsSection(String section, String username) {
		DatabaseConnection connection = null;
		PreparedStatement stmt = null;
		
		int Id = queryForID(username);
		int sectionId = queryForSectionID(section);
		
		clearPreviousSavedSection(Id);
		
		try {
			connection = this.getDatabaseConnection();
			stmt = connection.setupPreparedStatement(
					"insert into news_user (user_id, news_id) values (?,?);"
					);
			stmt.setInt(1, Id);
			stmt.setInt(2, sectionId);

			connection.runUpdate(stmt);
		} catch (SQLException e) {
			LOG.error("Could not update weather location ", e);
		}
		finally
		{
			if(stmt != null)
			{
				try {
					stmt.close();
				} catch (SQLException e) {
					LOG.error("Could not close statement", e);
				}
			}
			if(connection != null)
			{
				try
				{
					connection.closeStatement(stmt);
				}
				catch(Throwable e)
				{
					LOG.error("Could not close connection ", e);
				}
			}
		}
		
	}
	
	private void clearPreviousSavedSection(int userID) {
		DatabaseConnection connection = null;
		PreparedStatement stmt = null;
		
		try{
			connection = this.getDatabaseConnection();
			stmt = connection.setupPreparedStatement(
					"delete from news_user where user_id = ?");
			stmt.setInt(1, userID);
			
			stmt.executeUpdate();
		}
		catch (SQLException e)
		{
			LOG.error("Cannot clear saved news section", e);
		}
		
	}

	private int queryForSectionID(String section) {
		DatabaseConnection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int Id = -1;
		
		try {
			connection = this.getDatabaseConnection();
			stmt = connection.setupPreparedStatement(
			"select id from news where section_name = ?;"
					);
			stmt.setString(1, section);
			rs = stmt.executeQuery();
			
			while(rs.next())
			{
				Id = rs.getInt("id");
			}
		}
		catch( SQLException e)
		{
			LOG.error("Cannot query for id", e);
		}
		
		return Id;
	}

	public static void main(String[] args)
	{
		ObjMapping um = new ObjMapping();
		System.out.println(um.getFirstName("test_nujabes"));
		System.out.println(um.isMatchingPassword("password", "test_nujabes"));
	}

	public boolean registerMirror(String ip, String userName) {
		DatabaseConnection connection = null;
		PreparedStatement stmt = null;
		
		int id = createMirror(ip);
		int userID = queryForID(userName);
		boolean success = false;
		try {
			connection = this.getDatabaseConnection();
			stmt = connection.setupPreparedStatement(
			"insert into mirror_user (mirror_id, user_id, privilege_id) values (?, ?, ?)"
					);
			stmt.setInt(1, id);
			stmt.setInt(2, userID);
			stmt.setInt(3, 1);
			
			stmt.executeUpdate();
			success = true;
		}
		catch( SQLException e)
		{
			LOG.error("Cannot query for id", e);
		}
		finally{
			if(connection != null)
			{
				connection.closeStatement(stmt);
			}
		}
		
		return success;
	}

	private int createMirror(String ip) {
		DatabaseConnection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int Id = -1;
		
		try {
			connection = this.getDatabaseConnection();
			stmt = connection.setupPreparedStatement(
			"select id from mirror where mirror_IP_address = ?;"
					);
			stmt.setString(1, ip);
			rs = stmt.executeQuery();
			
			while(rs.next())
			{
				Id = rs.getInt("id");
			}
		}
		catch( SQLException e)
		{
			LOG.error("Cannot query for mirror id", e);
		}
		
		if(Id == -1)
		{
			try{
			stmt = connection.setupPreparedStatement(
					"insert into mirror (mirror_IP_address) values (?)");
			stmt.setString(1, ip);
			stmt.executeUpdate();
			
			}
			catch( SQLException e)
			{
				LOG.error("Cannot insert mirror id", e);
			}
			
			try {
				connection = this.getDatabaseConnection();
				stmt = connection.setupPreparedStatement(
				"select id from mirror where mirror_IP_address = ?;"
						);
				stmt.setString(1, ip);
				rs = stmt.executeQuery();
				
				while(rs.next())
				{
					Id = rs.getInt("id");
				}
			}
			catch( SQLException e)
			{
				LOG.error("Cannot query for mirror id", e);
			}
			finally
			{
				if(rs != null)
				{
					try {
						rs.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				if(connection != null)
				{
					connection.closeStatement(stmt);
				}
			}
		}
		return Id;
	}

	public boolean saveMessage(Message newMessage, String userName) {
		DatabaseConnection conn = null;
		PreparedStatement stmt = null;
		boolean success = false;
		int id = queryForID(userName);
		
		try {
			conn = getDatabaseConnection();
			String sql = "insert into message (user_id, timestamp, body) values (?,?,?);";
			stmt = conn.setupPreparedStatement(sql);
			stmt.setInt(1, id);
			stmt.setDate(2, new java.sql.Date(newMessage.getTime().getTime()));
			stmt.setString(3, newMessage.getBody());
			
			stmt.executeUpdate();
			success = true;
		}
		catch( SQLException e)
		{
			LOG.error("Cannot save a message", e);
		}
		finally
		{
			if(conn != null)
			{
				conn.closeStatement(stmt);
			}
		}
		return success;
	}

	public boolean persistLyftData(String toSave, String userName) {
		DatabaseConnection conn =  null;
		PreparedStatement stmt = null;
		
		boolean success = false;
		
		int userID = queryForID(userName);
		
		try
		{
			conn = getDatabaseConnection();
			String sql = "insert into lyft_data (user_id, json_object) values (?,?) on duplicate key update json_object = (?)";
			stmt = conn.setupPreparedStatement(sql);
			stmt.setInt(1, userID);
			stmt.setString(2, toSave);
			stmt.setString(3, toSave);
			
			stmt.executeUpdate();
			success = true;
		}
		catch( SQLException e)
		{
			LOG.error("Cannot persis data for lyft", e);
		}
		finally
		{
			if(conn != null)
			{
				conn.closeStatement(stmt);
			}
		}
		return success;
	}



}
