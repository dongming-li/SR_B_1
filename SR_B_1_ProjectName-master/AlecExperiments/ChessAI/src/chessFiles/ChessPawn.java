package chessFiles;

import java.util.ArrayList;

/**
 * Created by Luke on 9/24/2017.
 *
 * This class is a concrete class to implement a standard Pawn piece in Chess
 */
public class ChessPawn extends ChessPiece {

    // stores whether the pawn has made a move yet - needed to determine available moves for the pawn
    private boolean hasMoved;
    // stores whether the pawn just moved forward two spaces - needed for en passant calculations
    private boolean justMadeTwoStepMove;

    /**
     * Creates a Pawn piece with the given location and owner - hasMoved and justMadeTwoStepMove are both set to false
     * @param location - the starting ChessSquare of this piece
     * @param owner - the player that owns this piece
     */
    public ChessPawn(ChessSquare location, Player owner) {
        super(location, owner);
        // sets the name of piece for debugging purposes - this is a protected
        // variable in the ChessPiece class
        if (owner == Player.PLAYER_1) {
            name = "wPawn";
        } else {
            name = "bPawn";
        }
        hasMoved = false;
        justMadeTwoStepMove = false;
    }

    /**
     * Runs the same movePiece as the ChessPiece class, and then sets the pawn's hasMoved to true
     */
    @Override
    public void movePiece(ChessSquare newLocation) {
        super.movePiece(newLocation);
        hasMoved = true;
    }

    /**
     * Sets the hasMoved boolean to the given boolean. Designed to be used with the testMoveForCheck method.
     */
    public void setHasMoved(boolean moved) {
        hasMoved = moved;
    }

    /**
     * Returns the hasMoved boolean. Designed to be used with undoMove with testMoveForCheck.
     * @return  Whether or not this pawn has moved
     */
    public boolean getHasMoved() {
        return hasMoved;
    }

    /**
     * Sets the justMadeTwoStepMove boolean to the given boolean - allows the variable to be changed
     * @param given
     */
    public void setJustMadeTwoStepMove(boolean given) {
        justMadeTwoStepMove = given;
    }

    /**
     * Returns the justMadeTwoStepMove boolean - should be set to true if this was done on the most recent turn,
     * otherwise it will be false. This variable will need to be maintained by the game state, as this class
     * does not set it otherwise.
     * @return
     */
    public boolean getJustMadeTwoStepMove() {
        return justMadeTwoStepMove;
    }

    @Override
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
            // can move two spots forward if it hasn't moved yet
            if(!hasMoved) {
                if (board.getBoard()[curCol][curRow - 1].getOccupant() == null && board.getBoard()[curCol][curRow - 2].getOccupant() == null) {
                    moves.add(board.getBoard()[curCol][curRow - 2]);
                }
            }
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
            // en passant is available if player two just made a move two steps forward with one of their pawns
            if (curRow == 3) {
                // pawn could potentially en passant either direction
                if (curCol > 0 && curCol < 7) {
                    // pawn can en passant left
                    if (board.getBoard()[curCol - 1][curRow].getOccupant() != null && board.getBoard()[curCol - 1][curRow].getOccupant().getOwner() == Player.PLAYER_2 && board.getBoard()[curCol - 1][curRow].getOccupant().getClass() == ChessPawn.class) {
                        if (((ChessPawn)(board.getBoard()[curCol - 1][curRow].getOccupant())).getJustMadeTwoStepMove()) {
                            moves.add(board.getBoard()[curCol - 1][curRow - 1]);
                        }
                    }
                    // pawn can en passant right
                    if (board.getBoard()[curCol + 1][curRow].getOccupant() != null && board.getBoard()[curCol + 1][curRow].getOccupant().getOwner() == Player.PLAYER_2 && board.getBoard()[curCol + 1][curRow].getOccupant().getClass() == ChessPawn.class) {
                        if (((ChessPawn)(board.getBoard()[curCol + 1][curRow].getOccupant())).getJustMadeTwoStepMove()) {
                            moves.add(board.getBoard()[curCol + 1][curRow - 1]);
                        }
                    }
                }
                // pawn can only en passant right
                else if (curCol == 0) {
                    if (board.getBoard()[curCol + 1][curRow].getOccupant() != null && board.getBoard()[curCol + 1][curRow].getOccupant().getOwner() == Player.PLAYER_2 && board.getBoard()[curCol + 1][curRow].getOccupant().getClass() == ChessPawn.class) {
                        if (((ChessPawn)(board.getBoard()[curCol + 1][curRow].getOccupant())).getJustMadeTwoStepMove()) {
                            moves.add(board.getBoard()[curCol + 1][curRow - 1]);
                        }
                    }
                }
                // pawn can only en passant left
                else if (curCol == 7) {
                    if (board.getBoard()[curCol - 1][curRow].getOccupant() != null && board.getBoard()[curCol - 1][curRow].getOccupant().getOwner() == Player.PLAYER_2 && board.getBoard()[curCol - 1][curRow].getOccupant().getClass() == ChessPawn.class) {
                        if (((ChessPawn)(board.getBoard()[curCol - 1][curRow].getOccupant())).getJustMadeTwoStepMove()) {
                            moves.add(board.getBoard()[curCol - 1][curRow - 1]);
                        }
                    }
                }

            }
        }
        // moves if the player is PLAYER_2
        else {
            // can move two spots forward if it hasn't moved yet
            if(!hasMoved) {
                if (board.getBoard()[curCol][curRow + 1].getOccupant() == null && board.getBoard()[curCol][curRow + 2].getOccupant() == null) {
                    moves.add(board.getBoard()[curCol][curRow + 2]);
                }
            }
            // pawn can't move any direction but forward (down in this case)
            if(curRow < maxCol - 1) {
                // move directly forward (down)
                if(board.getBoard()[curCol][curRow + 1].getOccupant() == null) {
                    moves.add(board.getBoard()[curCol][curRow + 1]);
                }
                // move forward and to the right if the pawn can attack (down and left)
                if(curCol > 0 && board.getBoard()[curCol-1][curRow+1].getOccupant() != null && board.getBoard()[curCol-1][curRow+1].getOccupant().getOwner() == Player.PLAYER_1) {
                    moves.add(board.getBoard()[curCol-1][curRow + 1]);
                }
                // move forward and to the left if the pawn can attack (down and right)
                if(curCol < maxCol - 1 && board.getBoard()[curCol+1][curRow+1].getOccupant() != null && board.getBoard()[curCol+1][curRow+1].getOccupant().getOwner() == Player.PLAYER_1) {
                    moves.add(board.getBoard()[curCol+1][curRow + 1]);
                }
            }
            // en passant is available if player two just made a move two steps forward with one of their pawns
            if (curRow == 4) {
                // pawn could potentially en passant either direction
                if (curCol > 0 && curCol < 7) {
                    // pawn can en passant left
                    if (board.getBoard()[curCol - 1][curRow].getOccupant() != null && board.getBoard()[curCol - 1][curRow].getOccupant().getOwner() == Player.PLAYER_1 && board.getBoard()[curCol - 1][curRow].getOccupant().getClass() == ChessPawn.class) {
                        if (((ChessPawn)(board.getBoard()[curCol - 1][curRow].getOccupant())).getJustMadeTwoStepMove()) {
                            moves.add(board.getBoard()[curCol - 1][curRow + 1]);
                        }
                    }
                    // pawn can en passant right
                    if (board.getBoard()[curCol + 1][curRow].getOccupant() != null && board.getBoard()[curCol + 1][curRow].getOccupant().getOwner() == Player.PLAYER_1 && board.getBoard()[curCol + 1][curRow].getOccupant().getClass() == ChessPawn.class) {
                        if (((ChessPawn)(board.getBoard()[curCol + 1][curRow].getOccupant())).getJustMadeTwoStepMove()) {
                            moves.add(board.getBoard()[curCol + 1][curRow + 1]);
                        }
                    }
                }
                // pawn can only en passant right
                 else if (curCol == 0) {
                    if (board.getBoard()[curCol + 1][curRow].getOccupant() != null && board.getBoard()[curCol + 1][curRow].getOccupant().getOwner() == Player.PLAYER_1 && board.getBoard()[curCol + 1][curRow].getOccupant().getClass() == ChessPawn.class) {
                        if (((ChessPawn)(board.getBoard()[curCol + 1][curRow].getOccupant())).getJustMadeTwoStepMove()) {
                            moves.add(board.getBoard()[curCol + 1][curRow + 1]);
                        }
                    }
                }
                // pawn can only en passant left
                else if (curCol == 7) {
                    if (board.getBoard()[curCol - 1][curRow].getOccupant() != null && board.getBoard()[curCol - 1][curRow].getOccupant().getOwner() == Player.PLAYER_1 && board.getBoard()[curCol - 1][curRow].getOccupant().getClass() == ChessPawn.class) {
                        if (((ChessPawn)(board.getBoard()[curCol - 1][curRow].getOccupant())).getJustMadeTwoStepMove()) {
                            moves.add(board.getBoard()[curCol - 1][curRow + 1]);
                        }
                    }
                }

            }

        }

        return moves;
    }
}
