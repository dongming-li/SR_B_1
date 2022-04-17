package coms309.chesslogic;

import java.util.ArrayList;

/**
 * Created by Luke on 9/24/2017.
 *
 * Concrete class to implement a rook in classic Chess
 */
public class ChessRook extends ChessPiece {

    /**
     * Creates a new ChessRook piece with the given location and owner
     * @param location - the starting ChessSquare for this piece
     * @param owner - the Player to own the piece
     */
    public ChessRook(ChessSquare location, Player owner) {
        super(location, owner);
    }

    public ArrayList<ChessSquare> getMoves() {
        ArrayList<ChessSquare> moves = new ArrayList<ChessSquare>();
        ChessSquare current = this.getLocation();
        int curRow = current.getRow();
        int curCol = current.getCol();
        // get the board this rook is on
        ChessBoard board = this.getLocation().getBoard();
        int maxRow = board.getNumRows();
        int maxCol = board.getNumCols();

        int rowVar = curRow - 1;
        int colVar = curCol;

        // move up
        while (rowVar > -1) {
            // no occupants
            if (board.getBoard()[curCol][rowVar].getOccupant() == null) {
                moves.add(board.getBoard()[curCol][rowVar]);
                rowVar--;
            }
            // occupied
            else {
                // occupied by enemy
                if (board.getBoard()[curCol][rowVar].getOccupant().getOwner() != this.getOwner()) {
                    moves.add(board.getBoard()[curCol][rowVar]);
                    rowVar = -1;
                }
                // occupied by ally
                else {
                    rowVar = -1;
                }
            }
        }

        // move down
        rowVar = curRow + 1;
        while (rowVar < maxRow) {
            // no occupants
            if (board.getBoard()[curCol][rowVar].getOccupant() == null) {
                moves.add(board.getBoard()[curCol][rowVar]);
                rowVar++;
            }
            // occupied
            else {
                // occupied by enemy
                if (board.getBoard()[curCol][rowVar].getOccupant().getOwner() != this.getOwner()) {
                    moves.add(board.getBoard()[curCol][rowVar]);
                    rowVar = maxRow;
                }
                // occupied by ally
                else {
                    rowVar = maxRow;
                }
            }
        }

        // move left
        colVar = curCol - 1;
        while (colVar > -1) {
            // no occupants
            if (board.getBoard()[colVar][curRow].getOccupant() == null) {
                moves.add(board.getBoard()[colVar][curRow]);
                colVar--;
            }
            // occupied
            else {
                // occupied by enemy
                if (board.getBoard()[colVar][curRow].getOccupant().getOwner() != this.getOwner()) {
                    moves.add(board.getBoard()[colVar][curRow]);
                    colVar = -1;
                }
                // occupied by ally
                else {
                    colVar = -1;
                }
            }
        }

        // move right
        colVar = curCol + 1;
        while (colVar < maxCol) {
            // no occupants
            if (board.getBoard()[colVar][curRow].getOccupant() == null) {
                moves.add(board.getBoard()[colVar][curRow]);
                colVar++;
            }
            // occupied
            else {
                // occupied by enemy
                if (board.getBoard()[colVar][curRow].getOccupant().getOwner() != this.getOwner()) {
                    moves.add(board.getBoard()[colVar][curRow]);
                    colVar = maxCol;
                }
                // occupied by ally
                else {
                    colVar = maxCol;
                }
            }
        }
        return moves;
    }
}
