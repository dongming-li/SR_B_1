package coms309.chesssuitev1;

import java.util.ArrayList;

/**
 * Created by Luke on 10/31/2017.
 *
 * Concrete class to implement a ConverterPowerUp - when activated it turns all adjacent
 * enemy pieces to friendly pieces, excluding the king.
 */
public class ConverterPowerUp extends PowerUp {

    /**
     * Creates a ConverterPowerUp on the given location
     * @param location
     */
    public ConverterPowerUp(ChessSquare location) {
        super(location);
    }

    /**
     * Helper method to return an identical piece except it has the opposite player
     * @param toReplace - the piece to replace
     * @param location - the spot of the piece to replace
     * @param owner - the owner of the new piece
     * @return - the piece that replaces the given piece
     */
    private ChessPiece convertPiece(ChessPiece toReplace, ChessSquare location, Player owner) {
        if (toReplace.getClass() == ChessPawn.class || toReplace.getClass() == TempChessPawn.class) {
            return new ChessPawn(location, owner);
        }
        else if (toReplace.getClass() == ChessKnight.class) {
            return new ChessKnight(location, owner);
        }
        else if (toReplace.getClass() == ChessBishop.class) {
            return new ChessBishop(location, owner);
        }
        else if (toReplace.getClass() == ChessRook.class) {
            return new ChessRook(location, owner);
        }
        else if (toReplace.getClass() == ChessQueen.class || toReplace.getClass() == TempChessQueen.class) {
            return new ChessQueen(location, owner);
        }
        // should never return null, just here to make function return happy
        return null;
    }

    @Override
    public void takePowerUp(ChessPiece taker) {
        super.takePowerUp(taker);

        ArrayList<ChessPiece> adjacent = new ArrayList<ChessPiece>();
        // get the current location of the powerup
        ChessSquare current = taker.getLocation();
        int curRow = current.getRow();
        int curCol = current.getCol();
        // get the board this powerup is on
        ChessBoard board = taker.getLocation().getBoard();
        int maxRow = board.getNumRows();
        int maxCol = board.getNumCols();

        // set limits to not leave the board
        int top = Math.max(0, curRow - 1);
        int bottom = Math.min(maxRow - 1, curRow + 1);
        int left = Math.max(0, curCol - 1);
        int right = Math.min(maxCol - 1, curCol + 1);

        // cycle through all spots around the powerup
        for (int row = top; row <= bottom; row++) {
            for (int col = left; col <= right; col++) {
                // if the spot is occupied by another player's piece, then "convert" them
                if (board.getBoard()[col][row].getOccupant() != null && board.getBoard()[col][row].getOccupant().getOwner() != taker.getOwner()) {
                    // can't convert a king, so skip them if they are nearby
                    if (board.getBoard()[col][row].getOccupant().getClass() != ChessKing.class) {
                        // if the taker of the powerup is player 1
                        if (taker.getOwner() == Player.PLAYER_1) {
                            // move current occupant of adjacent square to a dummy square
                            ChessPiece temp = board.getBoard()[col][row].getOccupant();
                            ChessSquare tempSquare = new ChessSquare(taker.getLocation().getBoard(), "temp", -1, -1);
                            temp.movePiece(tempSquare);
                            // create a new piece with the same class, just different player
                            ChessPiece newPiece = convertPiece(temp, board.getBoard()[col][row], Player.PLAYER_1);
                            // move them to the tempsquare to remove old piece
                            newPiece.movePiece(tempSquare);
                            newPiece.movePiece(board.getBoard()[col][row]);
                            // add new piece to list of pieces
                            board.addToList(Player.PLAYER_1, newPiece);
                        }
                        // otherwise player is player 2 - do the same for the other player
                        else {
                            ChessPiece temp = board.getBoard()[col][row].getOccupant();
                            ChessSquare tempSquare = new ChessSquare(taker.getLocation().getBoard(), "temp", -1, -1);
                            temp.movePiece(tempSquare);
                            ChessPiece newPiece = convertPiece(temp, board.getBoard()[col][row], Player.PLAYER_2);
                            newPiece.movePiece(tempSquare);
                            newPiece.movePiece(board.getBoard()[col][row]);
                            board.addToList(Player.PLAYER_2, newPiece);
                        }
                    }
                }
            }
        }
    }
}
