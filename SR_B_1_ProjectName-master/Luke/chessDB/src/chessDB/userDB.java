package chessDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/**
 * Provides methods for working with the user database (account_info on
 * db309srb1).
 * 
 * @author Luke
 *
 */
public class userDB {

	private Connection con = null;
	private PreparedStatement preStat = null;
	private ResultSet rSet = null;
	private Statement stat = null;

	final private String host = "mysql.cs.iastate.edu";
	final private String user = "dbu309srb1";
	final private String dbPassword = "4TqVz2qS";

	/**
	 * Inserts a new row into the account_info table in db309srb1 with the given
	 * username, password, and email
	 * 
	 * @param username
	 *            The given username
	 * @param password
	 *            The given password
	 * @param email
	 *            The given email address
	 * @throws Exception
	 *             Exceptions that can arise from connecting to the database
	 */
	public void insertUserAccount(String username, String password, String email) throws Exception {
		try {
			beginConnection();

			// insert the required info into a preparedStatement
			preStat = con.prepareStatement("insert into db309srb1.account_info values (default, ?, ?, ?)");
			preStat.setString(1, username);
			preStat.setString(2, password);
			preStat.setString(3, email);
			// insert the info into the table
			preStat.executeUpdate();

		} catch (Exception e) {
			// pass exception onto the caller of this function
			throw e;
		} finally {
			// close anything that may be open
			closeAll();
		}
	}

	/**
	 * Deletes a row in the table the has a username that matches the given username.
	 * 
	 * @param username
	 * 		The username that matches the row to be deleted
	 * @throws Exception
	 * 		An Exception may be thrown while connecting to the database
	 */
	public void deleteUserAccount(String username) throws Exception {
		try {
			// begin basic connection to the database
			beginConnection();
			
			// insert username into the preparedStatement
			preStat = con.prepareStatement("delete from db309ssrb1.account_info where username= ? ; ");
			preStat.setString(1, username);
			// delete the row with the matching username
			preStat.executeUpdate();
			
		} catch (Exception e) {
			throw e;
		} finally {
			// close anything that may be open
			closeAll();
		}
	}

	/**
	 * Helper method that loads the Driver for the database and creates a connection.
	 * 
	 * @throws Exception
	 * 		Exception that may be thrown while starting a connection to the database.
	 */
	private void beginConnection() throws Exception {
		try {
			// load driver
			Class.forName("com.mysql.jdbc.Driver");

			// start connection
			con = DriverManager
					.getConnection("jdbc:mysql://" + host + "/db309srb1?" + "user=" + user + "&password=" + dbPassword);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Closes all connections that may be open while using functions from the userDB
	 * class. Should be called at the end of every other method in userDB.
	 */
	private void closeAll() {
		try {
			if (rSet != null) {
				rSet.close();
			}
			if (stat != null) {
				stat.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (Exception e) {
			// just allowing the close statements to be run without throwing exception
			// further
		}
	}

}
