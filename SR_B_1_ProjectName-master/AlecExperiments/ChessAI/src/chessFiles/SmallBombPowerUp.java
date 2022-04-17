package chessFiles;

/**
 * Holds info for the small bomb power up - removes all pieces within a 1 square radius upon being activated.
 *
 * Created by Luke on 10/29/2017.
 */
public class SmallBombPowerUp extends PowerUp {

    /**
     * Creates a new SmallBombPowerUp on the given ChessSquare
     * @param location
     */
    public SmallBombPowerUp(ChessSquare location) {
        super(location);
    }

    @Override
    public void takePowerUp(ChessPiece taker) {
        super.takePowerUp(taker);
        // get conditions of the board
        int curRow = taker.getLocation().getRow();
        int curCol = taker.getLocation().getCol();
        int maxRow = 8;
        int maxCol = 8;

        // set limits to not leave the board
        int top = Math.max(0, curRow - 1);
        int bottom = Math.min(maxRow - 1, curRow + 1);
        int left = Math.max(0, curCol - 1);
        int right = Math.min(maxCol - 1, curCol + 1);

        // remove all pieces around the spot (including the piece that took the powerup)
        ChessSquare[][] board = taker.getLocation().getBoard().getBoard();
        for (int row = top; row <= bottom; row++) {
            for (int col = left; col <= right; col++) {
                if (board[col][row].getOccupant() != null) {
                    board[col][row].getOccupant().killPiece();
                }
                board[col][row].removeOccupant();
            }
        }
    }
}
