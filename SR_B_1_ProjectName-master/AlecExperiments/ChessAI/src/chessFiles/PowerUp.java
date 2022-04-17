package chessFiles;

/**
 * Abstract class used to group the different power up classes.
 * Created by Luke on 10/29/2017.
 */
public abstract class PowerUp {

    // holds the location the powerup is
    private ChessSquare location;

    /**
     * Creates a new PowerUp with the given location
     * @param location
     */
    public PowerUp(ChessSquare location) {
        this.location = location;
        location.setPowerUp(this);
    }

    /**
     * Moves the powerup to the given ChessSquare - designed to be used to temporarily move the powerup
     * to avoid hitting it with a move in testing.
     * @param toMove
     */
    public void setLocation(ChessSquare toMove) {
        location.removePowerUp();
        location = toMove;
        toMove.setPowerUp(this);
    }

    /**
     * Removes the powerup from the location without activating it.
     */
    public void removePowerUp() {
        location.removePowerUp();
        location = null;
    }

    /**
     * Meant to be called after a piece lands on the ChessSquare occupied by the powerup. Removes connections between the ChessSquare and powerup.
     * Actual implementations of the powerup should be in the concrete powerup classes.
     * @param taker - the ChessPiece to take the powerup
     */
    public void takePowerUp(ChessPiece taker) {
        this.removePowerUp();
    }
}
