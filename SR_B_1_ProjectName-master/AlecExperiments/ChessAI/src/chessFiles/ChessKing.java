package chessFiles;

import java.util.ArrayList;

/**
 * Created by Luke on 9/24/2017.
 *
 * This class is a concrete class to implement a King in standard chess
 */
public class ChessKing extends ChessPiece {

    // stores whether or not the king has moved - this info is needed for castling
    private boolean hasMoved;

    /**
     * Creates a ChessKing piece with the given location and owner, with hasMoved set to false
     * @param location - the starting ChessSquare of this piece
     * @param owner - the player that will own this King
     */
    public ChessKing(ChessSquare location, Player owner) {
        super(location, owner);
        hasMoved = false;
        // sets the name of piece for debugging purposes - this is a protected
        // variable in the ChessPiece class
        if (owner == Player.PLAYER_1) {
           name = "wKing";
        } else {
            name = "bKing";
        }
    }


    /**
     * Runs the same movePiece as the ChessPiece class, and then sets the king's hasMoved to true
     */
    @Override
    public void movePiece(ChessSquare newLocation) {
        super.movePiece(newLocation);
        hasMoved = true;
    }

    /**
     * Returns whether or not the king has moved
     * @return - true if the king has moved, false otherwise
     */
    public boolean getHasMoved() {
        return hasMoved;
    }

    /**
     * Sets the hasMoved variable to the given boolean. Designed to be used with the testMoveForCheck method.
     * @param moved - boolean to set the hasMoved variable to
     */
    public void setHasMoved(boolean moved) {
        hasMoved = moved;
    }

    @Override
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

        // if King hasn't moved and it is not in check, check for castling availablity
        if (this.getOwner() == Player.PLAYER_1) {
            boolean flag = true;
            if (!hasMoved && !board.testCheck(Player.PLAYER_1)) {
                // check to see if rook is at end of board, and that it has not moved (right side)
                if (board.getBoard()[maxCol - 1][curRow].getOccupant() != null && board.getBoard()[maxCol - 1][curRow].getOccupant().getClass() == ChessRook.class && !((ChessRook)board.getBoard()[maxCol - 1][curRow].getOccupant()).getHasMoved()) {
                    // check to see if all spaces in between King and Rook are unoccupied, and that the king would not be in check in any of the spots
                    for (int i = curCol + 1; i < maxCol - 1; i++) {
                        if (board.getBoard()[i][curRow].getOccupant() != null) {
                            flag = false;
                        }
                        if (board.testMoveForCheck(this, board.getBoard()[i][curRow])) {
                            flag = false;
                        }
                    }
                    // if flag is still true, add castling spot as a move
                    if (flag) {
                        moves.add(board.getBoard()[curCol + 2][curRow]);
                    }
                }
                flag = true;
                // check to see if rook is at end of board, and that it has not moved (left side)
                if (board.getBoard()[0][curRow].getOccupant() != null && board.getBoard()[0][curRow].getOccupant().getClass() == ChessRook.class && !((ChessRook)board.getBoard()[0][curRow].getOccupant()).getHasMoved()) {
                    for (int i = curCol - 1; i > 0; i--) {
                        if (board.getBoard()[i][curRow].getOccupant() != null) {
                            flag = false;
                        }
                        if (board.testMoveForCheck(this, board.getBoard()[i][curRow])) {
                            flag = false;
                        }
                    }
                    // if flag is still true, add castling spot as a move
                    if (flag) {
                        moves.add(board.getBoard()[curCol - 2][curRow]);
                    }
                }
            }
        }
        // do the same for player 2
        else {
            boolean flag = true;
            if (!hasMoved && !board.testCheck(Player.PLAYER_2)) {
                // check to see if rook is at end of board, and that it has not moved (right side)
                if (board.getBoard()[maxCol - 1][curRow].getOccupant() != null && board.getBoard()[maxCol - 1][curRow].getOccupant().getClass() == ChessRook.class && !((ChessRook)board.getBoard()[maxCol - 1][curRow].getOccupant()).getHasMoved()) {
                    // check to see if all spaces in between King and Rook are unoccupied, and that the king would not be in check in any of the spots
                    for (int i = curCol + 1; i < maxCol - 1; i++) {
                        if (board.getBoard()[i][curRow].getOccupant() != null) {
                            flag = false;
                        }
                        if (board.testMoveForCheck(this, board.getBoard()[i][curRow])) {
                            flag = false;
                        }
                    }
                    // if flag is still true, add castling spot as a move
                    if (flag) {
                        moves.add(board.getBoard()[curCol + 2][curRow]);
                    }
                }
                flag = true;
                // check to see if rook is at end of board, and that it has not moved (left side)
                if (board.getBoard()[0][curRow].getOccupant() != null && board.getBoard()[0][curRow].getOccupant().getClass() == ChessRook.class && !((ChessRook)board.getBoard()[0][curRow].getOccupant()).getHasMoved()) {
                    for (int i = curCol - 1; i > 0; i--) {
                        if (board.getBoard()[i][curRow].getOccupant() != null) {
                            flag = false;
                        }
                        if (board.testMoveForCheck(this, board.getBoard()[i][curRow])) {
                            flag = false;
                        }
                    }
                    // if flag is still true, add castling spot as a move
                    if (flag) {
                        moves.add(board.getBoard()[curCol - 2][curRow]);
                    }
                }
            }

        }

        return moves;
    }
}