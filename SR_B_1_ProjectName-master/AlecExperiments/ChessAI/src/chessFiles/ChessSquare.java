package chessFiles;

/**
 * Created by Luke on 9/19/2017.
 * The ChessSquare class forms a simple data type that can be used by the ChessBoard class
 * to implement an entire chess board. Each spot on a board is made by this class, which holds info
 * on what Piece is currently occupying it.
 */
public class ChessSquare {

    // the ChessBoard object this square belongs to
    private ChessBoard board;
    // the occupant of this square - null if it has none
    private ChessPiece occupant;
    // the powerup sitting in this square - null if it has none
    private PowerUp powerUp;
    // the name of this square - used for debugging purposes, not used for game logic
    private String name;
    // the row of the board this square is on
    private int row;
    // the column of the board this square is on
    private int col;

    /**
     * Creates a new ChessSquare on the given ChessBoard, along with a name and a color.
     * It contains the given numbers for a row and a column. Initially it contains no occupant
     * (occupant == null), and no powerup (powerup == null)
     * @param board - the ChessBoard this ChessSquare will be located on
     * @param name - the name of the spot, such as A2 or C4, etc.
     * @param row - the row number on the board this ChessSquare will occupy (zero-indexed)
     * @param col - the column number on the board this ChessSquare will occupy (zero-indexed)
     */
    public ChessSquare(ChessBoard board, String name, int row, int col) {
        this.name = name;
        this.board = board;
        occupant = null;
        this.row = row;
        this.col = col;
        powerUp = null;
    }

    /**
     * Sets the occupant of this ChessSquare to be the given Piece.
     * @param piece - the new Piece to occupy this ChessSquare
     */
    public void setOccupant(ChessPiece piece){
        occupant = piece;
    }

    /**
     * Sets the current occupant of this ChessSquare to null.
     */
    public void removeOccupant() {
        occupant = null;
    }

    /**
     * Returns the ChessPiece that currently occupies this ChessSquare.
     * Note this can return null if no Piece is currently in this ChessSquare.
     * @return - the current ChessPiece that occupies this Square
     */
    public ChessPiece getOccupant() {
        return occupant;
    }

    /**
     * Returns the name of this ChessSqaure
     * @return - the name of this ChessSquare
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the ChessBoard that this ChessSquare belongs to.
     * @return - the ChessBoard that this ChessSquare is in
     */
    public ChessBoard getBoard() {
        return board;
    }

    /**
     * Returns the row of the board this ChessSquare is in
     * @return - the row number of this ChessSquare
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column of the board this ChessSquare is in
     * @return - the column number of this ChessSquare
     */
    public int getCol() {
        return col;
    }

    /**
     * Sets the powerup of this ChessSquare to the given powerup
     * @param newPowerUp
     */
    public void setPowerUp(PowerUp newPowerUp) {
        powerUp = newPowerUp;
    }

    /**
     * Sets the powerup of this ChessSquare to null - effectively removes the powerup
     */
    public void removePowerUp() {
        powerUp = null;
    }

    /**
     * Returns the powerUp that is on the square - is null if there is no power up
     * @return
     */
    public PowerUp getPowerUp() {
        return powerUp;
    }
}
