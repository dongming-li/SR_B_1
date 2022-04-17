package alecl.queuegui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class QueueConnectionActivity extends AppCompatActivity {

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

    protected void loadGame(String gameID, String playerNum, String opponentName){
        Intent openingGame = new Intent(this, RefreshActivity.class);
        openingGame.putExtra("gameID", gameID);
        openingGame.putExtra("player", playerNum);
        openingGame.putExtra("opponent", opponentName);
        startActivity(openingGame);
    }
}
