package coms309.chesslogic;

import java.util.ArrayList;

/**
 * Created by Luke on 9/24/2017.
 *
 * This class is a concrete class to implement a King in standard chess
 */
public class ChessKing extends ChessPiece {

    /**
     * Creates a ChessKing piece with the given location and owner
     * @param location - the starting ChessSquare of this piece
     * @param owner - the player that will own this King
     */
    public ChessKing(ChessSquare location, Player owner) {
        super(location, owner);
    }

    public ArrayList<ChessSquare> getMoves() {
        ArrayList<ChessSquare> moves = new ArrayList<ChessSquare>();
        // get the current location of the king
        ChessSquare current = this.getLocation();
        int curRow = current.getRow();
        int curCol = current.getCol();
        // get the board this king is on
        ChessBoard board = this.getLocation().getBoard();
        int maxRow = board.getNumRows();
        int maxCol = board.getNumCols();

        // set limits to not leave the board
        int top = Math.max(0, curRow - 1);
        int bottom = Math.min(maxRow - 1, curRow + 1);
        int left = Math.max(0, curCol - 1);
        int right = Math.min(maxCol - 1, curCol + 1);

        // cycle through all spots around the king
        for (int row = top; row <= bottom; row++) {
            for (int col = left; col <= right; col++) {
                // exclude the current spot
                if (!(row == curRow && col == curCol)) {
                    if (board.getBoard()[col][row].getOccupant() != null && board.getBoard()[col][row].getOccupant().getOwner() == this.getOwner()) {
                        // if the Square is occupied by a friendly piece, you can't move there
                    } else {
                        moves.add(board.getBoard()[col][row]);
                    }
                }
            }
        }
        return moves;
    }
}