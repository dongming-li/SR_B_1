package boardArray;

import java.util.ArrayList;

/**
 * Concrete class to implement the Bishop Piece
 * 
 * @author Luke
 *
 */
public class Bishop extends Piece {

	/**
	 * Creates a new Bishop Piece with the given name, player, and Spot
	 * 
	 * @param name
	 *            Name of the new Bishop Piece
	 * @param owner
	 *            Which player owns the new Bishop
	 * @param location
	 *            Which Spot the Bishop resides in
	 */
	public Bishop(String name, Player owner, Spot location) {
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

		int ivar = i - 1;
		int jvar = j - 1;

		// move up and left
		while (ivar >= 0 && jvar >= 0) {
			// no occupants
			if (board.getBoard()[ivar][jvar].getOccupant() == null) {
				moves.add(board.getBoard()[ivar][jvar]);
				ivar--;
				jvar--;
			}
			// Spot occupied by Piece of other player
			else if (board.getBoard()[ivar][jvar].getOccupant().getPlayer() != this.getPlayer()) {
				moves.add(board.getBoard()[ivar][jvar]);
				// exit loop after adding Spot
				ivar = -1;
			}
			// exit loop otherwise
			else {
				ivar = -1;
			}
		}
		ivar = i + 1;
		jvar = j - 1;
		// move up and right
		while (ivar <= 7 && jvar >= 0) {
			// no occupants
			if (board.getBoard()[ivar][jvar].getOccupant() == null) {
				moves.add(board.getBoard()[ivar][jvar]);
				ivar++;
				jvar--;
			}
			// Spot occupied by Piece of other player
			else if (board.getBoard()[ivar][jvar].getOccupant().getPlayer() != this.getPlayer()) {
				moves.add(board.getBoard()[ivar][jvar]);
				// exit loop after adding Spot
				ivar = 8;
			}
			// exit loop otherwise
			else {
				ivar = 8;
			}
		}
		ivar = i - 1;
		jvar = j + 1;
		// move down and left
		while (ivar >= 0 && jvar <= 7) {
			// no occupants
			if (board.getBoard()[ivar][jvar].getOccupant() == null) {
				moves.add(board.getBoard()[ivar][jvar]);
				ivar--;
				jvar++;
			}
			// Spot occupied by Piece of other player
			else if (board.getBoard()[ivar][jvar].getOccupant().getPlayer() != this.getPlayer()) {
				moves.add(board.getBoard()[ivar][jvar]);
				// exit loop after adding Spot
				ivar = -1;
			}
			// exit loop otherwise
			else {
				ivar = -1;
			}
		}
		ivar = i + 1;
		jvar = j + 1;
		// move down and right
		while (ivar <= 7 && jvar <= 7) {
			// no occupants
			if (board.getBoard()[ivar][jvar].getOccupant() == null) {
				moves.add(board.getBoard()[ivar][jvar]);
				ivar++;
				jvar++;
			}
			// Spot occupied by Piece of other player
			else if (board.getBoard()[ivar][jvar].getOccupant().getPlayer() != this.getPlayer()) {
				moves.add(board.getBoard()[ivar][jvar]);
				// exit loop after adding Spot
				ivar = 8;
			}
			// exit loop otherwise
			else {
				ivar = 8;
			}
		}
		return moves;
	}

}
