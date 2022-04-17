package boardArray;

import java.util.ArrayList;

/**
 * Concrete class to implement a King Piece
 * 
 * @author Luke
 *
 */
public class King extends Piece {

	/**
	 * Creates a new King Piece with the given name, player, and Spot
	 * 
	 * @param name
	 *            Name of the new King Piece
	 * @param owner
	 *            Player that owns the new King Piece
	 * @param location
	 *            Starting Spot of the given King Piece
	 */
	public King(String name, Player owner, Spot location) {
		super(name, owner, location);
	}

	@Override
	public ArrayList<Spot> getMoves(BoardArray board, Spot currentLocation) {
		ArrayList<Spot> moves = new ArrayList<Spot>();
		String loc = currentLocation.getName();
		char locChar = loc.charAt(0);
		int locNum = Integer.parseInt(loc.replaceAll("\\D", ""));
		// ints i and j are the indices of the Spot double array
		// the ascii value of 'a' is 97
		int i = locChar - 97;
		int j = 8 - locNum;
		
		// sets boundaries on edge of map
		int top = Math.max(0, j - 1);
		int bottom = Math.min(7, j + 1);
		int left = Math.max(0, i - 1);
		int right = Math.min(7, i + 1);
		
		// iterates around the King to add moves
		for (int row = top; row < bottom; row++) {
			for (int col = left; col < right; col++) {
				// excludes the Spot the King is currently in
				if (!(row == j && col == i)) {
					moves.add(board.getBoard()[col][row]);
				}
			}
		}
		return moves;
	}

}
