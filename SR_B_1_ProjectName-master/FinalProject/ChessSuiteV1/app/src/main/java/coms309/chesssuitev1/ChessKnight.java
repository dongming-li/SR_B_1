package coms309.chesssuitev1;

import java.util.ArrayList;

/**
 * Created by Luke on 9/24/2017.
 *
 * Concrete class to implement a knight piece in classic chess
 */
public class ChessKnight extends ChessPiece {

    /**
     * Creates a new ChessKnight with the given location and player
     * @param location - the starting ChessSquare of this knight
     * @param owner - the player that owns this knight
     */
    public ChessKnight(ChessSquare location, Player owner) {
        super(location, owner);
        // sets the name of piece for debugging purposes - this is a protected
        // variable in the ChessPiece class
        if (owner == Player.PLAYER_1) {
            name = "wKnight";
        } else {
            name = "bKnight";
        }
    }

    public ArrayList<ChessSquare> getMoves() {
        // list to store valid moves in
        ArrayList<ChessSquare> moves = new ArrayList<ChessSquare>();
        // save current spot of the knight
        ChessSquare current = this.getLocation();
        int curRow = current.getRow();
        int curCol = current.getCol();
        // get the board this knight is on
        ChessBoard board = this.getLocation().getBoard();
        int maxRow = board.getNumRows();
        int maxCol = board.getNumCols();

        // iterate over the 8 possible moves and check boundaries and whether or not the space is occupied by a friendly piece
        if (curCol + 2 < maxCol && curRow - 1 > -1 && canMoveTo(board.getBoard()[curCol + 2][curRow - 1])) {
            moves.add(board.getBoard()[curCol + 2][curRow - 1]);
        }
        if (curCol + 2 < maxCol && curRow + 1 < maxRow && canMoveTo(board.getBoard()[curCol + 2][curRow + 1])) {
            moves.add(board.getBoard()[curCol + 2][curRow + 1]);
        }
        if (curCol - 2 > -1 && curRow - 1 > -1 && canMoveTo(board.getBoard()[curCol - 2][curRow - 1])) {
            moves.add(board.getBoard()[curCol - 2][curRow - 1]);
        }
        if (curCol - 2 > -1 && curRow + 1 < maxRow && canMoveTo(board.getBoard()[curCol - 2][curRow + 1])) {
            moves.add(board.getBoard()[curCol - 2][curRow + 1]);
        }
        if (curCol + 1 < maxCol && curRow - 2 > -1 && canMoveTo(board.getBoard()[curCol + 1][curRow - 2])) {
            moves.add(board.getBoard()[curCol + 1][curRow - 2]);
        }
        if (curCol + 1 < maxCol && curRow + 2 < maxRow && canMoveTo(board.getBoard()[curCol + 1][curRow + 2])) {
            moves.add(board.getBoard()[curCol + 1][curRow + 2]);
        }
        if (curCol - 1 > -1 && curRow - 2 > -1 && canMoveTo(board.getBoard()[curCol - 1][curRow - 2])) {
            moves.add(board.getBoard()[curCol - 1][curRow - 2]);
        }
        if (curCol - 1 > -1 && curRow + 2 < maxRow && canMoveTo(board.getBoard()[curCol - 1][curRow + 2])) {
            moves.add(board.getBoard()[curCol - 1][curRow + 2]);
        }

        return moves;
    }
}
