package coms309.chesssuitev1;

/**
 * Created by Luke on 11/6/2017.
 *
 * This interface is meant to be implemented by any activity that acts as the GUI for a chess game.
 */
public interface MainGameActivity {

    /**
     * This method returns the ChessBoard object stored in the Activity.
     * @return - the ChessBoard of this activity
     */
    public ChessBoard getTheBoard();
}
