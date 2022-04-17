package chessFiles;

/**
 * Created by Luke on 10/30/2017.
 *
 * Concrete class for a ToQueenPowerUp. Turns the piece that activates it into a queen for a single turn.
 */
public class ToQueenPowerUp extends PowerUp {

    /**
     * Creates a new ToQueenPowerUp in the given location.
     * @param location
     */
    public ToQueenPowerUp(ChessSquare location) {
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
            TempChessQueen newTemp = new TempChessQueen(spot, taker.getOwner(), taker);
            spot.getBoard().addToList(taker.getOwner(), newTemp);
        }
    }
}
