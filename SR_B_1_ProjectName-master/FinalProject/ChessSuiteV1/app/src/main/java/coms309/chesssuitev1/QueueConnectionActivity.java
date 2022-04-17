package coms309.chesssuitev1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Main activity for entering a queue.
 * This activity connects to the queue server and sends the client's name.
 * The server sends back a game id, player number, and opponent name.
 * The activity ends by opening a game activity with the above info.
 * @author alecl
 */
public class QueueConnectionActivity extends AppCompatActivity {

    /**
     * Method called upon activity creation.
     * Gets the client's name and spawns a thread to handle all server communication.
     * @param savedInstanceState The instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue_connection);

        //grab the client's name
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String clientName = prefs.getString("username", "");

        //use an async process to connect to the server and get the game ID
        //the on post execute method of the async task will connect the client to the game activity
        Object[] params = {clientName, this};
        new QueueAsyncBackgroundWorker().execute(params);
    }

    /**
     * method to load a game activity with the supplied information.
     * @param gameID The game's database id
     * @param playerNum The client's player number, this is supplied by the server
     * @param opponentName This client's opponent's name
     */
    protected void loadGame(String gameID, String playerNum, String opponentName){
        Intent openingGame = new Intent(this, RefreshActivity.class);
        openingGame.putExtra("gameID", gameID);
        openingGame.putExtra("player", playerNum);
        openingGame.putExtra("opponent", opponentName);
        startActivity(openingGame);
    }
}
