package coms309.chesssuitev1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * This class is used to control what game to start, playing against who,
 * and moving to the right next screen
 */
public class Chess_Main extends AppCompatActivity {



    private Button two_players;
    private Button AI_player;
    private Button New_Game;
    private Button AICreation;
    private String username;
    static private String player = "";
    static private int ID = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("history", "");
        editor.commit();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess__main);

        getGames();
        getGamesAI();

        two_players = (Button) findViewById(R.id.two_player);
        AI_player = (Button)findViewById(R.id.AI_player);
        New_Game = (Button)findViewById(R.id.two_player_create);
        AICreation = (Button)findViewById(R.id.AICreation);

        SharedPreferences pref2 = PreferenceManager.getDefaultSharedPreferences(this);
            username = pref2.getString("username","");



        two_players.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                        Intent intent = new Intent(getApplicationContext(), GamesMenu.class);
                        startActivity(intent);

            }
        });

        New_Game.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), QueueConnectionActivity.class);
                startActivity(intent);

            }
        });



        AI_player.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                        Intent intent = new Intent(getApplicationContext(), AIGamesMenu.class);
                        startActivity(intent);

            }
        });

        AICreation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), CreateAIGameActivity.class);
                startActivity(intent);

            }
        });


    }


    /**
     * Gets the list of games in which the user is a player against
     * other users
     */
    public void getGames() {
        SharedPreferences pref2 = PreferenceManager.getDefaultSharedPreferences(this);
        final String username = pref2.getString("username", "");
        final String cancel_Games = "Games";
        final String URL_FOR_GAMES_LIST = "http://proj-309-sr-b-1.cs.iastate.edu:8008/games/user/" + username;


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL_FOR_GAMES_LIST, null,
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter
                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("GamesMenu", response.toString());

                        try {
                            boolean error = response.getBoolean("error");
                            Log.d("gamesMenuError", String.valueOf(error));
                            if(error){
                                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor editor = pref.edit();
                                editor.remove("Games_List");
                                editor.remove("ids");
                                editor.remove("who");
                                editor.commit();

                            }
                            JSONArray arr = response.getJSONArray("data");
                            StringBuilder games = new StringBuilder();
                            StringBuilder ids = new StringBuilder();
                            StringBuilder oneOrTwo = new StringBuilder();

                            for (int i = 0; i < arr.length(); i++) {
                                String player1 = arr.getJSONObject(i).getString("player1");
                                String player2 = arr.getJSONObject(i).getString("player2");
                                String gameID = arr.getJSONObject(i).getString("game");
                                String first = "player1";
                                String second = "player2";
                                Log.d("list_player1", player1);
                                Log.d("list_player2", player2);
                                Log.d("game_id", gameID);
                                ids.append(gameID).append(",");

                                if (player1.equals(username)) {
                                    games.append(player2).append(",");
                                    oneOrTwo.append(first).append(",");
                                } else {
                                    games.append(player1).append(",");
                                    oneOrTwo.append(second).append(",");
                                }
                            }
                            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("Games_List", games.toString());
                            editor.putString("ids",ids.toString());
                            editor.commit();
                            editor.putString("who", oneOrTwo.toString());
                            editor.commit();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        );


        // Adding request to request queue
        AppVolley.getInstance(getApplicationContext()).addToRequestQueue(request,cancel_Games);
    }


    /**
     * Gets the list of games in which the user is a player against AI
     */
    public void getGamesAI() {
        SharedPreferences pref2 = PreferenceManager.getDefaultSharedPreferences(this);
        final String username = pref2.getString("username", "");
        final String cancel_Games = "Games";
        final String URL_FOR_GAMES_LIST = "http://proj-309-sr-b-1.cs.iastate.edu:8008/games/AI/" + username;


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL_FOR_GAMES_LIST, null,
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter
                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("AIGamesMenu", response.toString());

                        try {

                            boolean error = response.getBoolean("error");
                            Log.d("gamesMenuError", String.valueOf(error));
                            if(error){
                                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor editor = pref.edit();
                                editor.remove("AI_Games_List");
                                editor.remove("AI_ids");
                                editor.remove("AI_who");
                                editor.commit();

                            }

                            JSONArray arr = response.getJSONArray("data");
                            StringBuilder games = new StringBuilder();
                            StringBuilder ids = new StringBuilder();
                            StringBuilder oneOrTwo = new StringBuilder();

                            for (int i = 0; i < arr.length(); i++) {
                                String player1 = arr.getJSONObject(i).getString("player1");
                                String player2 = arr.getJSONObject(i).getString("player2");
                                String gameID = arr.getJSONObject(i).getString("game");
                                String first = "player1";
                                String second = "player2";
                                Log.d("list_player1", player1);
                                Log.d("list_player2", player2);
                                Log.d("game_id", gameID);
                                ids.append(gameID).append(",");

                                if (player1.equals(username)) {
                                    games.append(player2).append(",");
                                    oneOrTwo.append(first).append(",");
                                } else {
                                    games.append(player1).append(",");
                                    oneOrTwo.append(second).append(",");
                                }
                            }
                            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("AI_Games_List", games.toString());
                            editor.putString("AI_ids",ids.toString());
                            editor.commit();
                            editor.putString("AI_who", oneOrTwo.toString());
                            editor.commit();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        );


        // Adding request to request queue
        AppVolley.getInstance(getApplicationContext()).addToRequestQueue(request,cancel_Games);
    }


}
