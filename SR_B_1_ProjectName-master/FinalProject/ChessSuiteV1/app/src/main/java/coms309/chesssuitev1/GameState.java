package coms309.chesssuitev1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Abdelrahman on 10/8/2017.
 *
 * This class is used to keep track of the current game state
 * ,to get the history of a game, and to send new moves to
 * the database
 */

public class GameState {
    private static final String TAG1 = "GameState";
    private static final String TAG2 = "SendMove";

    private String state;
    private Context context;
    private int gameID;


    //constructor
    public GameState(String history, Context con, int gameID) {
        this.gameID = gameID;
        state = history;
        context = con.getApplicationContext();
    }

    /**
     * get the current state of the game
     * @return the current state
     */
    public String getString() {
        return state;
    }

    /**
     * Gets the updated history of the current game
     */
    public void updateString(  ) {
        final String comment = "myuser";
        final String cancel_UPDATE_MOVE = "CancelMove";
        final String URL_FOR_SENDMOVE = "http://proj-309-sr-b-1.cs.iastate.edu:8008/game/board/" + gameID;


        JsonObjectRequest request  = new JsonObjectRequest(Request.Method.GET, URL_FOR_SENDMOVE,null,
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter
                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", response.toString());

                        try {
                            boolean error = response.getBoolean("error");

                           if (!error){
                                String newmoves = response.getString("history");

                                Log.d("newmoves", newmoves);

                                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("history", newmoves);
                                editor.commit();

                                Log.d("newmoves2", pref.getString("history", ""));
                            }

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
        AppVolley.getInstance(context.getApplicationContext()).addToRequestQueue(request,cancel_UPDATE_MOVE);

    }


    /**
     * send the new made moves to the database and
     * updates the game history
     * @param newmove
     */
    public void sendMove(String newmove){
        final String CANCEL_TAG = "CancelMove";
        final String moved = newmove;
        final String URL_FOR_SENDMOVE = "http://proj-309-sr-b-1.cs.iastate.edu:8008/game/board/update";


        JSONObject obj = new JSONObject();

        try {
            obj.put("move", moved);
            obj.put("gameID", gameID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request  = new JsonObjectRequest(Request.Method.POST, URL_FOR_SENDMOVE,obj,
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter
                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("Response", response.toString());


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
        AppVolley.getInstance(context.getApplicationContext()).addToRequestQueue(request,CANCEL_TAG);
    }


    /**
     * Send game results to the database and end the game
     * @param winner winner of the game
     * @param loser loser of the game
     * @param gameid game id
     * @param draw draw
     */
    public void endGame(String winner, String loser,int gameid, boolean draw ){

        final String CANCEL_TAG = "CancelMove";

        final String URL_FOR_ENDGAME = "http://proj-309-sr-b-1.cs.iastate.edu:8008/game/end";


        JSONObject obj = new JSONObject();

        try {
            obj.put("gameID", gameid);
            obj.put("winner",winner);
            obj.put("loser", loser);
            obj.put("draw", draw);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request  = new JsonObjectRequest(Request.Method.POST, URL_FOR_ENDGAME,obj,
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter
                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("Response", response.toString());


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
        AppVolley.getInstance(context.getApplicationContext()).addToRequestQueue(request,CANCEL_TAG);

    }

}
