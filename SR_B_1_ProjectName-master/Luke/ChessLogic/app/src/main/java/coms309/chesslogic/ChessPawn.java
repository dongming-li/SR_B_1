package coms309.chesslogic;

import java.util.ArrayList;

/**
 * Created by Luke on 9/24/2017.
 *
 * This class is a concrete class to implement a standard Pawn piece in Chess
 */
public class ChessPawn extends ChessPiece {

    /**
     * Creates a Pawn piece with the given location and owner
     * @param location - the starting ChessSquare of this piece
     * @param owner - the player that owns this piece
     */
    public ChessPawn(ChessSquare location, Player owner) {
        super(location, owner);
    }

    public ArrayList<ChessSquare> getMoves() {
        ArrayList<ChessSquare> moves = new ArrayList<ChessSquare>();
        // get the current location of the pawn
        ChessSquare current = this.getLocation();
        int curRow = current.getRow();
        int curCol = current.getCol();
        // get the board this pawn is on
        ChessBoard board = this.getLocation().getBoard();
        int maxRow = board.getNumRows();
        int maxCol = board.getNumCols();

        // moves if the player is PLAYER_1
        if(this.getOwner() == Player.PLAYER_1) {
            // pawn can't move any direction but forward
            if(curRow > 0) {
                // move directly forward
                if(board.getBoard()[curCol][curRow - 1].getOccupant() == null) {
                    moves.add(board.getBoard()[curCol][curRow - 1]);
                }
                // move forward and to the left if the pawn can attack
                if(curCol > 0 && board.getBoard()[curCol-1][curRow-1].getOccupant() != null && board.getBoard()[curCol-1][curRow-1].getOccupant().getOwner() == Player.PLAYER_2) {
                    moves.add(board.getBoard()[curCol-1][curRow - 1]);
                }
                // move forward and to the right if the pawn can attack
                if(curCol < maxCol - 1 && board.getBoard()[curCol+1][curRow-1].getOccupant() != null && board.getBoard()[curCol+1][curRow-1].getOccupant().getOwner() == Player.PLAYER_2) {
                    moves.add(board.getBoard()[curCol+1][curRow - 1]);
                }
            }
        }
        // moves if the player is PLAYER_2
        else {
            // pawn can't move any direction but forward (down in this case)
            if(curRow < maxCol - 1) {
                // move directly forward (down)
                if(board.getBoard()[curCol][curRow + 1].getOccupant() == null) {
                    moves.add(board.getBoard()[curCol][curRow + 1]);
                }
                // move forward and to the right if the pawn can attack (down and left)
                if(curCol > 0 && board.getBoard()[curCol-1][curRow+1].getOccupant() != null && board.getBoard()[curCol-1][curRow+1].getOccupant().getOwner() == Player.PLAYER_2) {
                    moves.add(board.getBoard()[curCol-1][curRow + 1]);
                }
                // move forward and to the left if the pawn can attack (down and right)
                if(curCol < maxCol - 1 && board.getBoard()[curCol+1][curRow+1].getOccupant() != null && board.getBoard()[curCol+1][curRow+1].getOccupant().getOwner() == Player.PLAYER_2) {
                    moves.add(board.getBoard()[curCol+1][curRow + 1]);
                }
            }

        }

        return moves;
    }
}
