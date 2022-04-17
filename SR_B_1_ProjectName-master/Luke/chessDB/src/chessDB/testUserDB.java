package chessDB;

/**
 * Test class used to test new methods related to the user database
 * 
 * @author Luke
 *
 */
public class testUserDB {

	public static void main(String[] args) {
		try {
		userDB test = new userDB();
		test.deleteUserAccount("luke");
		} catch (Exception e) {
			System.out.println("Error in connection: " + e.getMessage());
		}

	}

}
