package coms309.chesssuitev1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class serves as the main landing screen when the user logs in
 */
public class MainMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SessionManager session;

    public void startChat() {
        Intent i = new Intent(this, ChatMainActivity.class);
        startActivity(i);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageView gImage = (ImageView) findViewById(R.id.chess_game);
        setSupportActionBar(toolbar);
        getStats();


        //to get username and email
        SharedPreferences pref2 = PreferenceManager.getDefaultSharedPreferences(this);
        String username = pref2.getString("username", "");
        String email_txt = pref2.getString("email", "");


        Log.d("username", username + " " + email_txt);



        //handle image clicks
        gImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Chess_Main.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startChat();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //session manager
        session = new SessionManager(getApplicationContext());

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //get the nav_header view
        View headerView = navigationView.getHeaderView(0);
        TextView navEmail = (TextView) headerView.findViewById(R.id.email_txt);
        TextView navUsername = (TextView) headerView.findViewById(R.id.user_name);


        navEmail.setText(email_txt);
        navUsername.setText(username);


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableaIfStatement
        if (id == R.id.action_logout) {
            logoutUser();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the home action
        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(this, profileActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     */
    private void logoutUser() {
        session.setLogin(false);


        // Launching the login activity
        Intent intent = new Intent(MainMenu.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Gets the stats of the user to be ready to show up in the profile activity.
     */
    public void getStats(){
        SharedPreferences pref2 = PreferenceManager.getDefaultSharedPreferences(this);
        final String username = pref2.getString("username", "");
        final String cancel_Stats = "Stats";
        final String URL_FOR_GAMES_LIST = "http://proj-309-sr-b-1.cs.iastate.edu:8008/user/username/" + username;


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL_FOR_GAMES_LIST, null,
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter
                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("getStats", response.toString());

                        try {
                            JSONObject obj = response.getJSONObject("data");
                            String wins = obj.getString("wins");
                            String losses = obj.getString("losses");
                            String draws = obj.getString("draws");
                            String win_loss_ratio = obj.getString("win_loss_ratio");

                            Log.d("wins",wins);
                            Log.d("losses",losses);
                            Log.d("draws",draws);
                            Log.d("win_loss_ratio",win_loss_ratio);

                            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = pref.edit();

                            editor.putString("wins",wins);
                            editor.putString("losses", losses);
                            editor.putString("draws", draws);
                            editor.putString("win_loss_ratio", win_loss_ratio);
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
        AppVolley.getInstance(getApplicationContext()).addToRequestQueue(request,cancel_Stats);

    }
}
