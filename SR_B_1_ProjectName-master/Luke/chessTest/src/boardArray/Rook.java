package boardArray;

import java.util.ArrayList;

/**
 * Gives a concrete class for a Rook Piece in Chess
 * 
 * @author Luke
 *
 */
public class Rook extends Piece {

	/**
	 * Creates a new Rook Piece with the given name, owner, and location
	 * 
	 * @param name
	 *            Name of the Rook, stored as a String
	 * @param owner
	 *            Which player owns the new Rook
	 * @param location
	 *            Which Spot the Rook will be placed in
	 */
	public Rook(String name, Player owner, Spot location) {
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
		// move left
		while (ivar >= 0) {
			// no occupants
			if (board.getBoard()[ivar][j].getOccupant() == null) {
				moves.add(board.getBoard()[ivar][j]);
				ivar--;
			}
			// if an occupant is seen that is not owned by the player
			else if (board.getBoard()[ivar][j].getOccupant().getPlayer() != this.getPlayer()) {
				moves.add(board.getBoard()[ivar][j]);
				// after adding position, exit loop
				ivar = -1;
			}
			// if an occupant is seen that is owned by the player, exit the loop
			else {
				ivar = -1;
			}
		}
		ivar = i + 1;
		// move right
		while (ivar <= 7) {
			// no occupants
			if (board.getBoard()[ivar][j].getOccupant() == null) {
				moves.add(board.getBoard()[ivar][j]);
				ivar++;
			}
			// if an occupant is seen that is not owned by the player
			else if (board.getBoard()[ivar][j].getOccupant().getPlayer() != this.getPlayer()) {
				moves.add(board.getBoard()[ivar][j]);
				// after adding position, exit loop
				ivar = 8;
			}
			// if an occupant is seen that is owned by the player, exit the loop
			else {
				ivar = 8;
			}
		}
		// move up
		while (jvar >= 0) {
			// no occupants
			if (board.getBoard()[i][jvar].getOccupant() == null) {
				moves.add(board.getBoard()[i][jvar]);
				jvar--;
			}
			// if an occupant is seen that is not owned by the player
			else if (board.getBoard()[i][jvar].getOccupant().getPlayer() != this.getPlayer()) {
				moves.add(board.getBoard()[i][jvar]);
				// after adding position, exit loop
				jvar = -1;
			}
			// if an occupant is seen that is owned by the player, exit the loop
			else {
				jvar = -1;
			}
		}
		jvar = j + 1;
		// move down
		while (jvar <= 7) {
			// no occupants
			if (board.getBoard()[i][jvar].getOccupant() == null) {
				moves.add(board.getBoard()[i][jvar]);
				jvar++;
			}
			// if an occupant is seen that is not owned by the player
			else if (board.getBoard()[i][jvar].getOccupant().getPlayer() != this.getPlayer()) {
				moves.add(board.getBoard()[i][jvar]);
				// after adding position, exit loop
				jvar = 8;
			}
			// if an occupant is seen that is owned by the player, exit the loop
			else {
				jvar = 8;
			}
		}
		return moves;
	}

}
