package boardArray;

import java.util.ArrayList;

/**
 * Concrete class to implement a Queen Piece
 * 
 * @author Luke
 *
 */
public class Queen extends Piece {

	/**
	 * Creates a new Queen Piece with the given name, player, and Spot
	 * 
	 * @param name
	 *            Name of the new new Queen Piece
	 * @param owner
	 *            Player to own the new Queen Piece
	 * @param location
	 *            Starting Spot of the new Queen Piece
	 */
	public Queen(String name, Player owner, Spot location) {
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

		// moves side to side (rook moves)
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

		// moves diagonally (bishop moves)
		ivar = i - 1;
		jvar = j - 1;
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
