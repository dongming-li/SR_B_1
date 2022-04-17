package chessFiles;

/**
 * Created by Luke on 10/29/2017.
 *
 * Concrete class to implement a LargeBombPowerUp - which when activated
 * removes all piece in a 2 x 2 block around it, regardless of player. This
 * will remove the piece that takes it.
 */
public class LargeBombPowerUp extends PowerUp {

    /**
     * Constructs a LargeBombPowerUp on the given location. When activated, a large bomb removes
     * all pieces (regardless of player) in a 2 x 2 block around it.
     * @param location
     */
    public LargeBombPowerUp(ChessSquare location) {
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

        // set limits to not leave the board (and go up to two spaces)
        int top = Math.max(0, curRow - 2);
        int bottom = Math.min(maxRow - 1, curRow + 2);
        int left = Math.max(0, curCol - 2);
        int right = Math.min(maxCol - 1, curCol + 2);

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
