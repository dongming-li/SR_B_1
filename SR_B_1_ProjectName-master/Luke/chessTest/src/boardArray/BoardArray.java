package boardArray;

/**
 * Class containing utility methods to generate a chess board.
 * 
 * @author Luke
 *
 */
public class BoardArray {

	// double array of Spots that represents the board
	private Spot[][] board;

	/**
	 * Creates a new empty BoardArray, an 8 by 8 grid. Each Spot is empty (has not
	 * occupants), and is named according to chess standards.
	 */
	public BoardArray() {
		board = new Spot[8][8];
		for (int i = 0; i < 8; i++) {
			int num = 8 - i;
			board[0][i] = new Spot("a" + num);
		}
		for (int i = 0; i < 8; i++) {
			int num = 8 - i;
			board[1][i] = new Spot("b" + num);
		}
		for (int i = 0; i < 8; i++) {
			int num = 8 - i;
			board[2][i] = new Spot("c" + num);
		}
		for (int i = 0; i < 8; i++) {
			int num = 8 - i;
			board[3][i] = new Spot("d" + num);
		}
		for (int i = 0; i < 8; i++) {
			int num = 8 - i;
			board[4][i] = new Spot("e" + num);
		}
		for (int i = 0; i < 8; i++) {
			int num = 8 - i;
			board[5][i] = new Spot("f" + num);
		}
		for (int i = 0; i < 8; i++) {
			int num = 8 - i;
			board[6][i] = new Spot("g" + num);
		}
		for (int i = 0; i < 8; i++) {
			int num = 8 - i;
			board[7][i] = new Spot("h" + num);
		}
	}
	
	/**
	 * Returns the double array of Spots stored by BoardArray
	 * @return
	 * 		The Spot double array which defines the board
	 */
	public Spot[][] getBoard() {
		return board;
	}

	/**
	 * Utility function, prints the Spot names of the BoardArray in the console
	 */
	public void printBoardName() {
		for (int j = 0; j < 8; j++) {
			for (int i = 0; i < 8; i++) {
				System.out.print(board[i][j].getName());
				System.out.print(" ");
			}
			System.out.println("");
		}
	}

}
