package chessFiles;

/**
 * Created by Luke on 10/30/2017.
 *
 * Class that simulates a queen for a single move, before it becomes its original piece again.
 */
public class TempChessQueen extends ChessQueen {

    // the ChessPiece that activated the powerup - hold its info until after the TempChessQueen moves
    private ChessPiece replaced;

    /**
     * Creates a new TempChessQueen on the given location, with the given owner, and the piece for it to temporarily replace.
     * @param location - the location of the tempQueen
     * @param owner - the owner of the tempQueen
     * @param toReplace - the Piece to be replaced by the tempQueen
     */
    public TempChessQueen(ChessSquare location, Player owner, ChessPiece toReplace) {
        super(location, owner);
        replaced = toReplace;
    }

    /**
     * After the piece is moved, this method should be called (instead of a normal movePiece()). It will do
     * the normal move, and then delete the tempQueen and replace it with the original piece again.
     * @param newLocation - the location to move to
     */
    public void specialMovePiece(ChessSquare newLocation) {
        movePiece(newLocation);
        this.getLocation().getBoard().removeFromList(this.getOwner(), this);
        replaced.movePiece(newLocation);
    }
}
