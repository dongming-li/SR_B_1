package coms309.gridviewtest;

/**
 * Created by Luke on 9/19/2017.
 * The ChessSquare class forms a simple data type that can be used by the ChessBoard class
 * to implement an entire chess board. Each spot on a board is made by this class, which holds info
 * on what Piece is currently occupying it.
 */
public class ChessSquare {

    private ChessBoard board;
    private ChessPiece occupant;
    private String name;
    private Color color;
    private int row;
    private int col;

    /**
     * Creates a new ChessSquare on the given ChessBoard, along with a name and a color.
     * It contains the given numbers for a row and a column. Initially it contains no occupant
     * (occupant = null)
     * @param board - the ChessBoard this ChessSquare will be located on
     * @param name - the name of the spot, such as A2 or C4, etc.
     * @param color - white or black, defined for the visual API
     * @param row - the row number on the board this ChessSquare will occupy (zero-indexed)
     * @param col - the column number on the board this ChessSquare will occupy (zero-indexed)
     */
    public ChessSquare(ChessBoard board, String name, Color color, int row, int col) {
        this.name = name;
        this.board = board;
        occupant = null;
        this.color = color;
        this.row = row;
        this.col = col;
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
     * Returns the color that this square is - black or white
     * @return - the color of this Square
     */
    public Color getColor() {
        return color;
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


}
