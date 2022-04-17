package coms309.chesssuitev1;

import java.util.ArrayList;

/**
 * Created by Luke on 9/19/2017.
 *
 * This class is the abstract class that all concrete pieces inherit from.
 */
public abstract class ChessPiece {

    // name is for debugging purposes - not used by game logic
    protected String name;
    // the current location of the piece
    private ChessSquare location;
    // the owner of the piece - either Player 1 or Player 2
    private Player owner;
    // whether or not the piece is still alive
    private boolean isAlive;

    /**
     * Parent constructor for all ChessPiece objects. Created with the given ChessSquare location and Player owner.
     * Piece is set with isAlive as true.
     * @param location - starting ChessSquare of the Piece
     * @param owner - the Player that owns the Piece
     */
    public ChessPiece(ChessSquare location, Player owner) {
        this.location = location;
        location.setOccupant(this);
        this.owner = owner;
        isAlive = true;
    }

    /**
     * Get the name of the Piece - the names are determined by the constructor of each concrete class
     * @return - the name of the Piece
     */
    public String getName() {
        return name;
    }

    /**
     * Get the state of the piece - true is alive, false is dead
     * @return  the state of the piece
     */
    public boolean getAlive() {
        return isAlive;
    }

    /**
     * Returns the Player object that owns the Piece
     * @return - the player that owns the Piece
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Sets the isAlive variable to false when called - it kills the piece.
     */
    public void killPiece() {
        isAlive = false;
    }

    /**
     * Returns the ChessSquare object that the Piece is currently in.
     * @return - the current ChessSquare the Piece occupies
     */
    public ChessSquare getLocation() {
        return location;
    }

    /**
     * Moves this piece to the given ChessSquare. Note that calling this method will adjust the ChessSquare objects' information -
     * the old square will no longer have an occupant, and the new square will be occupied by this Piece. This will also trigger a
     * powerup if one is on the newLocation.
     * @param newLocation - the ChessSquare to move to
     */
    public void movePiece(ChessSquare newLocation) {
        // changes the info stored by the ChessSquare object as well
        if (newLocation.getOccupant() != null) {
            takePiece(newLocation.getOccupant());
        }
        location.removeOccupant();
        location = newLocation;
        location.setOccupant(this);

        // activate a powerup if it is landed on
        if (newLocation.getPowerUp() != null) {
            newLocation.getPowerUp().takePowerUp(this);
        }
    }

    /**
     * This method removes the given piece from the board and sets its isAlive variable to false
     * @param toRemove - the piece to remove from the game
     */
    private void takePiece(ChessPiece toRemove) {
        toRemove.getLocation().removeOccupant();
        toRemove.isAlive = false;
    }

    /**
     * Returns an ArrayList of ChessSquares (ArrayList<ChessSquare>) that contains all the ChessSquares the Piece can move to
     * from its current location. Each concrete Piece class must define this method according
     * to that Piece's rules. This list does not account for check in a game of chess - those moves must be removed
     * from this list later.
     */
    public abstract ArrayList<ChessSquare> getMoves();

    /**
     * Helper function that determines if there is a friendly piece already in the given ChessSquare
     * @param toTry - the ChessSquare to check if there is a friendly piece in
     * @return - whether or not the given space if occupied by a friendly piece
     */
    protected boolean canMoveTo(ChessSquare toTry) {
        if (toTry.getOccupant() == null) {
            return true;
        }
        // if the space is occupied by a friendly piece, then the space can't be moved to
        else if (toTry.getOccupant().getOwner() == this.getOwner()){
            return false;
        }
        else {
            return true;
        }
    }
}
