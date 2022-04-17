package chessFiles;

/**
 * Concrete class for a ExtraTurn powerup. Allows the taker to make another move immediately following the move used to take it.
 * Created by Luke on 10/30/2017.
 */
public class ExtraTurnPowerUp extends PowerUp {

    /**
     * Creates an ExtraTurnPowerUp on the given location.
     * @param location
     */
    public ExtraTurnPowerUp(ChessSquare location) {
        super(location);
    }

    @Override
    public void takePowerUp(ChessPiece taker) {
        super.takePowerUp(taker);
        taker.getLocation().getBoard().takeTurn();
    }
}
