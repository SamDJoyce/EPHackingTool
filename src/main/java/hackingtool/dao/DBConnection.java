package hackingtool.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	// Fields
	private static DBConnection instance;
	private		   Connection 	connection;
	
    private static final String dbUser 			= "root";
    private static final String dbPassword  	= "";
    private static final String dbName      	= "hacking";
    private static final String NODES_TABLE		= "nodes";
    private static final String USERS_TABLE		= "users";
    private static final String ACCOUNTS_TABLE	= "accounts";
    private static final String baseURL     	= "jdbc:mysql://localhost/";
    private static final String fullURL			= baseURL + dbName + "?serverTimezone=America/Toronto";
	private static final String DRIVER_NOT_FOUND = "DB driver not found";
	
    // Constructor
	public DBConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		this.connection = DriverManager.getConnection(fullURL,dbUser,dbPassword);
	}
	
    /**
     * <p>
     * If a connection instance already exists, return it to the caller.
     * Otherwise, create a new instance and return it. 
     * </p>
     * 
     * @return An instance of the database connection.
     * @throws SQLException
     */
    public static synchronized DBConnection getInstance() throws SQLException {
    	if (instance == null) {
    		try {
    			instance = new DBConnection();
    		} catch(ClassNotFoundException e) {
    			throw new SQLException(DRIVER_NOT_FOUND,e);
    		}
    		
    	}
    	return instance;
    }
    /**
     * <p>
     * Makes a connection to the database.
     * </p>
     * 
     * @return A connection to the MySQL database.
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(fullURL, dbUser, dbPassword);
        }
        return connection;
    }

}
