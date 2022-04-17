package coms309.chesssuitev1;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONObject;



/**
 * Created by Luke on 12/4/2017.
 *
 * This Activity displays while an AIGame is Created. It does not require input.
 */

public class CreateAIGameActivity extends AppCompatActivity {

    private Button waitForServer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_aigame);
        SharedPreferences pref2 = PreferenceManager.getDefaultSharedPreferences(this);
        final String username = pref2.getString("username", "");
        makeGame(username);

        waitForServer = (Button) findViewById(R.id.waitForAIGame);


        waitForServer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), AIGamesMenu.class);
                startActivity(intent);

            }
        });

    }

    private void makeGame(String username){
        final String CANCEL_TAG = "CancelMove";
        final String URL_FOR_makeAIGame = "http://proj-309-sr-b-1.cs.iastate.edu:8008/game/ai/create/" + username;


        JSONObject obj = new JSONObject();

        JsonObjectRequest request  = new JsonObjectRequest(Request.Method.GET, URL_FOR_makeAIGame,obj,
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
        AppVolley.getInstance(getApplicationContext()).addToRequestQueue(request,CANCEL_TAG);
    }

}
