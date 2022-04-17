package chessFiles;

/**
 * Created by Luke on 10/30/2017.
 *
 * Concrete class for a ToPawnPowerUp. Turns the piece that activates it into a pawn for a single turn.
 */
public class ToPawnPowerUp extends PowerUp {

    /**
     * Creates a new ToPawnPowerUp in the given location.
     * @param location
     */
    public ToPawnPowerUp(ChessSquare location) {
        super(location);
    }

    @Override
    public void takePowerUp(ChessPiece taker) {
        super.takePowerUp(taker);
        // kings can't turn into different pieces
        if (taker.getClass() != ChessKing.class) {
            ChessSquare spot = taker.getLocation();
            // move the taker to a temporary spot
            taker.movePiece(new ChessSquare(spot.getBoard(), "temp", -1, -1));
            // replace taker with a new temp piece and add it to the list of pieces of the player
            TempChessPawn newTemp = new TempChessPawn(spot, taker.getOwner(), taker);
            spot.getBoard().addToList(taker.getOwner(), newTemp);
        }
    }
}
