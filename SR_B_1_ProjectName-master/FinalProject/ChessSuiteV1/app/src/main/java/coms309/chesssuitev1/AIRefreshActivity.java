package coms309.chesssuitev1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

/**
 * Created by Luke on 11/13/2017.
 *
 * This activity is used to refresh the AIMainActivity GUI.
 */
public class AIRefreshActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess__main);
        refresh(findViewById(android.R.id.content));
    }

    /**
     * Called when this activity is started - it refreshes information from the server and recreates the AIMainActivity.
     * @param view - The view that calls this method.
     */
    public void refresh(View view) {
        SharedPreferences pref2 = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = pref2.edit();

        Intent thisIntent = getIntent();
        Bundle b = thisIntent.getExtras();
        String gameStringID = b.getString("gameID");
        String playerString = b.getString("player");
        int gameID = Integer.parseInt(gameStringID);
        GameState state = new GameState("", this, gameID);
        state.updateString();
        String history = "";

        history = pref2.getString("history","");

        Intent intent = new Intent(this, AIMainActivity.class);
        intent.putExtra("history", history);
        intent.putExtra("gameID", gameStringID);
        intent.putExtra("player", playerString);
        Log.d("refresh", history);

        startActivity(intent);
    }
}
