package boardArray;

/**
 * Class designed to be a data structure - It groups a name and an "occupant"
 * together. Thus allowing a space to be shown to be occupied by a single Piece.
 * 
 * @author Luke
 *
 */
public class Spot {

	// name of this Spot
	private String name;
	// Piece which occupies this Spot
	private Piece occupant;

	/**
	 * Creates a new spot on the board - intended only to be used by BoardArray.
	 * Occupant is set to null.
	 * 
	 * @param name
	 *            The name of the spot made, such as a1 or b8, etc.
	 */
	public Spot(String name) {
		this.name = name;
		occupant = null;
	}

	/**
	 * Returns the Piece which is currently in the Spot.
	 * 
	 * @return The Piece in this Spot
	 */
	public Piece getOccupant() {
		return occupant;
	}
	
	/**
	 * Sets the occupant of this Spot to be the given Piece
	 * 
	 * @param newOccupant
	 * 		The Piece to be the new occupant of this Spot
	 */
	public void setOccupant(Piece newOccupant) {
		occupant = newOccupant;
	}

	/**
	 * Sets the occupant variable to null - it no longer has a piece in it
	 */
	public void clearSpot() {
		occupant = null;
	}

	/**
	 * Gives the name of this Spot
	 * 
	 * @return The name of the Spot
	 */
	public String getName() {
		return name;
	}
}
