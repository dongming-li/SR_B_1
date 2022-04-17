package chessFiles;

/**
 * Created by Luke on 10/30/2017.
 *
 * Class that simulates a pawn for a single move, before it becomes its original piece again.
 */
public class TempChessPawn extends ChessPawn {

    // the ChessPiece that activated the powerup - hold its info until after the TempChessPawn moves
    private ChessPiece replaced;

    /**
     * Creates a new TempChessPawn on the given location, with the given owner, and the piece for it to temporarily replace.
     * @param location - the location of the tempPawn
     * @param owner - the owner of the tempPawn
     * @param toReplace - the Piece to be replaced by the tempPawn
     */
    public TempChessPawn(ChessSquare location, Player owner, ChessPiece toReplace) {
        super(location, owner);
        replaced = toReplace;
    }

    /**
     * After the piece is moved, this method should be called (instead of a normal movePiece()). It will do
     * the normal move, and then delete the tempPawn and replace it with the original piece again.
     * @param newLocation - the location to move to
     */
    public void specialMovePiece(ChessSquare newLocation) {
        movePiece(newLocation);
        this.getLocation().getBoard().removeFromList(this.getOwner(), this);
        replaced.movePiece(newLocation);
    }
}
