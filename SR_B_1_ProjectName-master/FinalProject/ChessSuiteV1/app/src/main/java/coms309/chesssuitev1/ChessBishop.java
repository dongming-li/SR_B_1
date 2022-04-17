package coms309.chesssuitev1;

import java.util.ArrayList;

/**
 * Created by Luke on 9/24/2017.
 *
 * Concrete class to implement a bishop in classic chess
 */
public class ChessBishop extends ChessPiece {

    /**
     * Creates a new ChessBishop with the given location and player
     * @param location - the starting ChessSquare for this piece
     * @param owner - the owner of this piece
     */
    public ChessBishop(ChessSquare location, Player owner) {
        super(location, owner);
        // sets the name of piece for debugging purposes - this is a protected
        // variable in the ChessPiece class
        if (owner == Player.PLAYER_1) {
            name = "wBishop";
        } else {
            name = "bBishop";
        }
    }

    @Override
    public ArrayList<ChessSquare> getMoves() {
        // list will contain valid moves
        ArrayList<ChessSquare> moves = new ArrayList<ChessSquare>();
        // save the current location
        ChessSquare current = this.getLocation();
        int curRow = current.getRow();
        int curCol = current.getCol();
        // get the board this bishop is on
        ChessBoard board = this.getLocation().getBoard();
        int maxRow = board.getNumRows();
        int maxCol = board.getNumCols();

        int rowVar = curRow - 1;
        int colVar = curCol - 1;

        // move up and left
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
