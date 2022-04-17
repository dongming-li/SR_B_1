package boardArray;

import java.util.ArrayList;

/**
 * Gives a concrete class for a Pawn Piece in Chess
 * 
 * @author Luke
 *
 */
public class Pawn extends Piece {

	/**
	 * Creates a new Pawn Piece with the given name, owner, and location
	 * 
	 * @param name
	 *            Name of the Pawn, stored as a String
	 * @param owner
	 *            Which player owns the new Pawn
	 * @param location
	 *            Which Spot the Pawn will be placed in
	 */
	public Pawn(String name, Player owner, Spot location) {
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

		// the pawn's movement depends on the player that owns it
		if (this.getPlayer() == Player.PLAYER_1) {
			// standard case to move forward
			if (j > 0 && board.getBoard()[i][j - 1].getOccupant() == null) {
				moves.add(board.getBoard()[i][j - 1]);
			}
			// enemy Piece is forward and to the left
			if ((j > 0) && (i > 0)) {
				if (board.getBoard()[i - 1][j - 1].getOccupant() != null
						&& board.getBoard()[i - 1][j - 1].getOccupant().getPlayer() == Player.PLAYER_2) {
					moves.add(board.getBoard()[i - 1][j - 1]);
				}
			}
			// enemy Piece is forward and to the right
			if ((j > 0) && (i < 7)) {
				if (board.getBoard()[i + 1][j - 1].getOccupant() != null
						&& board.getBoard()[i + 1][j - 1].getOccupant().getPlayer() == Player.PLAYER_2) {
					moves.add(board.getBoard()[i + 1][j - 1]);
				}
			}
		}
		// otherwise the Piece is owner by player 2
		else {
			// standard case to move forward (perspective of Player 2)
			if (j < 7 && board.getBoard()[i][j + 1].getOccupant() == null) {
				moves.add(board.getBoard()[i][j + 1]);
			}
			// enemy Piece is forward and to the right (perspective of Player 2)
			if ((j < 7) && (i > 0)) {
				if (board.getBoard()[i - 1][j + 1].getOccupant() != null
						&& board.getBoard()[i - 1][j + 1].getOccupant().getPlayer() == Player.PLAYER_1) {
					moves.add(board.getBoard()[i - 1][j + 1]);
				}
			}
			// enemy Piece is forward and to the left (perspective of Player 2)
			if ((j < 7) && (i < 7)) {
				if (board.getBoard()[i + 1][j + 1].getOccupant() != null
						&& board.getBoard()[i + 1][j + 1].getOccupant().getPlayer() == Player.PLAYER_1) {
					moves.add(board.getBoard()[i + 1][j + 1]);
				}
			}
		}
		return moves;
	}

}
