package coms309.chesslogic;

import java.util.ArrayList;

/**
 * Created by Luke on 9/24/2017.
 *
 * A concrete class for implementing a queen piece in classic chess
 */
public class ChessQueen extends ChessPiece {

    /**
     * Creates a new ChessQueen with the given location and player
     * @param location - the starting ChessSquare of the queen
     * @param owner - the owner of the queen
     */
    public ChessQueen(ChessSquare location, Player owner) {
        super(location, owner);
    }

    public ArrayList<ChessSquare> getMoves() {
        ArrayList<ChessSquare> moves = new ArrayList<ChessSquare>();
        ChessSquare current = this.getLocation();
        int curRow = current.getRow();
        int curCol = current.getCol();
        // get the board this queen is on
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

        // move up and left
        rowVar = curRow - 1;
        colVar = curCol - 1;
        while (rowVar > -1 && colVar > -1) {
            // no occupants
            if (board.getBoard()[colVar][rowVar].getOccupant() == null) {
                moves.add(board.getBoard()[colVar][rowVar]);
                rowVar--;
                colVar--;
            }
            // occupied
            else {
                // occupied by enemy
                if (board.getBoard()[colVar][rowVar].getOccupant().getOwner() != this.getOwner()) {
                    moves.add(board.getBoard()[colVar][rowVar]);
                    rowVar = -1;
                }
                // occupied by ally
                else {
                    rowVar = -1;
                }
            }
        }

        // move down and left
        rowVar = curRow + 1;
        colVar = curCol - 1;
        while (rowVar < maxRow && colVar > -1) {
            // no occupants
            if (board.getBoard()[colVar][rowVar].getOccupant() == null) {
                moves.add(board.getBoard()[colVar][rowVar]);
                rowVar++;
                colVar--;
            }
            // occupied
            else {
                // occupied by enemy
                if (board.getBoard()[colVar][rowVar].getOccupant().getOwner() != this.getOwner()) {
                    moves.add(board.getBoard()[colVar][rowVar]);
                    rowVar = maxRow;
                }
                // occupied by ally
                else {
                    rowVar = maxRow;
                }
            }
        }

        // move up and right
        colVar = curCol + 1;
        rowVar = curRow - 1;
        while (rowVar > -1 && colVar < maxCol) {
            // no occupants
            if (board.getBoard()[colVar][rowVar].getOccupant() == null) {
                moves.add(board.getBoard()[colVar][rowVar]);
                colVar++;
                rowVar--;
            }
            // occupied
            else {
                // occupied by enemy
                if (board.getBoard()[colVar][rowVar].getOccupant().getOwner() != this.getOwner()) {
                    moves.add(board.getBoard()[colVar][rowVar]);
                    rowVar = -1;
                }
                // occupied by ally
                else {
                    rowVar = -1;
                }
            }
        }

        // move down and right
        colVar = curCol + 1;
        rowVar = curRow + 1;
        while (colVar < maxCol && rowVar < maxRow) {
            // no occupants
            if (board.getBoard()[colVar][rowVar].getOccupant() == null) {
                moves.add(board.getBoard()[colVar][rowVar]);
                colVar++;
                rowVar++;
            }
            // occupied
            else {
                // occupied by enemy
                if (board.getBoard()[colVar][rowVar].getOccupant().getOwner() != this.getOwner()) {
                    moves.add(board.getBoard()[colVar][rowVar]);
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
