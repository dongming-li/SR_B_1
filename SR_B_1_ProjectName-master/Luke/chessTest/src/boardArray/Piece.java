package boardArray;

import java.util.ArrayList;

/**
 * Abstract class which defines things all Pieces must have.
 * 
 * @author Luke
 *
 */
public abstract class Piece {

	// name of the Piece
	private String name;

	// which player owns the Piece
	private Player owner;

	// current location of the Piece
	private Spot location;

	/**
	 * Creates a Piece with the given name, Player, and Spot
	 * 
	 * @param name
	 *            The name of the new Piece
	 * @param owner
	 *            The Player that owns the new Piece
	 * @param location
	 *            The Spot where the Piece is
	 */
	public Piece(String name, Player owner, Spot location) {
		this.name = name;
		this.owner = owner;
		this.location = location;
		this.location.setOccupant(this);
	}

	/**
	 * Changes the location value to equal the given Spot
	 * 
	 * @param newLocation
	 *            The spot to move the Piece to
	 */
	public void movePiece(Spot newLocation) {
		location = newLocation;
	}

	/**
	 * Returns the current Spot occupied by this Piece
	 * 
	 * @return The Spot occupied by this Piece
	 */
	public Spot getSpot() {
		return location;
	}

	/**
	 * Gives the player that owns this Piece
	 * 
	 * @return The owner of this Piece
	 */
	public Player getPlayer() {
		return owner;
	}

	/**
	 * Gives the name of this Piece
	 * 
	 * @return The name of this Piece
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns an ArrayList of Spots that the Piece can move to from its current Spot
	 * 
	 * @param board
	 *            The board that this Piece is playing on
	 * @param currentLocation
	 *            The Spot the Piece currently occupies
	 * @return The ArrayList of Spots that the Piece can move to
	 */
	abstract public ArrayList<Spot> getMoves(BoardArray board, Spot currentLocation);

}
