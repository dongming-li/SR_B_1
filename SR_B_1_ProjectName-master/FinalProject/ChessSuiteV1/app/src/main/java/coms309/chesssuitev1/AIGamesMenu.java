package coms309.chesssuitev1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.Arrays;


/**
 * This class is used to present the list of games which the
 * user is currently enrolled in against AI
 */
public class AIGamesMenu extends AppCompatActivity {

    ListView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aigames_menu);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        //get the list object
        mList = (ListView) findViewById(R.id.AI_games_list);

        // Defined Array values to show in ListView;
        String[] gamesList = pref.getString("AI_Games_List", "").split(",");
        final String[] ids  = pref.getString("AI_ids","").split(",");
        final String [] who = pref.getString("AI_who","").split(",");
        Log.d("AI_games_string", Arrays.toString(gamesList));
        Log.d("AI_ids",Arrays.toString(ids));
        Log.d("AI_who", Arrays.toString(who));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, gamesList);


        // Assign adapter to ListView
        mList.setAdapter(adapter);

        // ListView Item Click Listener
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                //get index
                String gameID = ids[position];
                Log.d("which_game_id", ""+ gameID);

                //get player1 or player2
                String oneOrTwo = who[position];
                Log.d("oneOrTwo", oneOrTwo);

                //start game
                Intent intent = new Intent(getApplicationContext(), AIRefreshActivity.class);
                intent.putExtra("gameID", gameID);
                intent.putExtra("player", oneOrTwo);

                Log.d("help", intent.getExtras().toString());
                startActivity(intent);

            }

        });


    }

}
