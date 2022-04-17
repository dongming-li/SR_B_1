// NOTE: This is not my own work. I am only using this as an example of how java can connect to a mysql database.
// Found the guide and code on http://www.science.smith.edu/dftwiki/index.php/Tutorial:_Accessing_a_MySql_database_in_Java_(Eclipse)

package serverDBTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

// following example on science.smith.edu/dftwiki
public class MySQLAccess {

	private Connection connection = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	final private String host = "mysql.cs.iastate.edu";
	final private String user = "dbu309srb1";
	final private String password = "4TqVz2qS";

	public void readDataBase() throws Exception {
		try {
			// this loads the MySQL Driver - each db has its own driver
			Class.forName("com.mysql.jdbc.Driver");

			// Setup connection with db
			connection = DriverManager
					.getConnection("jdbc:mysql://" + host + "/db309srb1?" + "user=" + user + "&password=" + password);

			// Statements allow issuing sql queries to db
			statement = connection.createStatement();

			// Result set get the result of the SQL query
			resultSet = statement.executeQuery("select * from db309srb1.comments");
			writeResultSet(resultSet);

			// PreparedStatements can use variables and are more efficient
			preparedStatement = connection
					.prepareStatement("insert into db309srb1.comments values (default, ?, ?, ?, ?, ?, ?)");
			// "myuser, webpage, datum, summary, COMMENTS from feedback.comments");
			// Parameters start with 1
			preparedStatement.setString(1, "Test");
			preparedStatement.setString(2, "TestEmail");
			preparedStatement.setString(3, "TestWebpage");
			preparedStatement.setDate(4, new java.sql.Date(2017, 03, 03));
			preparedStatement.setString(5, "TestSummary");
			preparedStatement.setString(6, "TestComment");
			preparedStatement.executeUpdate();

			preparedStatement = connection
					.prepareStatement("SELECT myuser, mywebpage, datum, summary, COMMENTS from db309srb1.comments");
			resultSet = preparedStatement.executeQuery();
			writeResultSet(resultSet);

			// remove the inserted row in the table
			preparedStatement = connection.prepareStatement("delete from db309srb1.comments where myuser= ? ; ");
			preparedStatement.setString(1, "Test");
			preparedStatement.executeUpdate();

			resultSet = statement.executeQuery("select * from db309srb1.comments");
			writeMetaData(resultSet);

		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}
	}

	private void writeMetaData(ResultSet resultSet) throws SQLException {
		// get metadata from the db
		// Result Set gets the results of the sql query

		System.out.println("The columns in the table are: ");

		System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
		for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
			System.out.println("Column " + i + " " + resultSet.getMetaData().getColumnName(i));

		}
	}

	private void writeResultSet(ResultSet resultSet) throws SQLException {
		// ResultSet is initially before the first data set
		while (resultSet.next()) {
			// possible to get columns via name
			// also possible to get columns via column number which starts at 1
			// e.g. resultSet.getString(a number)
			String user = resultSet.getString("myuser");
			String website = resultSet.getString("mywebpage");
			String summary = resultSet.getString("summary");
			Date date = resultSet.getDate("datum");
			String comment = resultSet.getString("comments");
			System.out.println("User: " + user);
			System.out.println("Website: " + website);
			System.out.println("Summary: " + summary);
			System.out.println("Date: " + date);
			System.out.println("Comment: " + comment);
		}
	}

	// need to close the resultSet
	private void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {

		}
	}
}
