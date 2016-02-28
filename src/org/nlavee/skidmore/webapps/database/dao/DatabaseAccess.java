package org.nlavee.skidmore.webapps.database.dao;

import org.apache.log4j.Logger;
import org.nlavee.skidmore.webapps.database.backends.DatabaseConnection;


public class DatabaseAccess {
	/**
	 * The logger
	 */
	@SuppressWarnings("unused")
	private static final Logger LOG = Logger
		.getLogger(DatabaseAccess.class);

	/**
	 * The database driver
	 */
	private String driver;

	/**
	 * The connection URL for this instance
	 */
	private String connectionUrl;

	/**
	 * The database user id for this instance
	 */
	private String userId;

	/**
	 * The database password for this instance
	 */
	private String password;

	/**
	 * Singleton instance
	 */
	private static DatabaseAccess instance;

	/**
	 * Constructor - private since this is a Singleton class
	 */
	private DatabaseAccess()
	{
//		driver = MaTricksProperties.getInstance()
//			.getValue(ConfigurationProperty.DATABASE_DRIVER);
//		connectionUrl = MaTricksProperties.getInstance()
//			.getValue(ConfigurationProperty.DATABASE_URL);
//		userId = MaTricksProperties.getInstance()
//			.getValue(ConfigurationProperty.DATABASE_USERID);
//		password = MaTricksProperties.getInstance()
//			.getValue(ConfigurationProperty.DATABASE_PASSWORD);
	}

	/**
	 * Get the Singleton instance
	 * @return The instance
	 */
	public static synchronized DatabaseAccess getInstance()
	{
		if (instance == null)
		{
			instance = new DatabaseAccess();
		}

		return instance;
	}

	/**
	 * Get a database connection instance. Each call returns a unique
	 * instance representing a separate connection to the database. Callers
	 * should be sure to cleanup these connections in a timely manner.
	 * @return The database connection instance
	 */
	public DatabaseConnection getConnection()
	{
		return new DatabaseConnection(driver, connectionUrl, userId,
			password);
	}

}
