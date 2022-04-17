// NOTE: This is not my own work. I am only using this as an example of how java can connect to a mysql database.
// Found the guide and code on http://www.science.smith.edu/dftwiki/index.php/Tutorial:_Accessing_a_MySql_database_in_Java_(Eclipse)

package serverDBTest;

public class Main {

	// following example from science.smith.edu/dftwiki
	public static void main (String[] args) {
		try {
		MySQLAccess db = new MySQLAccess();
		db.readDataBase();
		} catch (Exception e) {
			System.out.println("Error thrown by readDataBase: " + e.getMessage());
		}
	}
}
