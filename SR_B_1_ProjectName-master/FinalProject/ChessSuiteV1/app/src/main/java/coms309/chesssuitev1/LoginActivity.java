package coms309.chesssuitev1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * This class is responsible for logging the user in
 */
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final String URL_FOR_LOGIN = "http://proj-309-sr-b-1.cs.iastate.edu:8008/user/login/email";
    ProgressDialog progressDialog;
    private SessionManager session;


    EditText _emailText;
    EditText _passwordText;
    Button _loginButton;
    TextView _signupLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        //IDs
        _emailText = (EditText) findViewById(R.id.input_email);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _loginButton = (Button) findViewById(R.id.btn_login);
        _signupLink = (TextView) findViewById(R.id.link_signup);



        //session manager
        session = new SessionManager(getApplicationContext());
        session.editor.clear();
        session.editor.commit();


         //Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }



        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String email = _emailText.getText().toString();
                final String password = _passwordText.getText().toString();

                if(validate()){
                    login(email, password);
                }else{
                    Toast.makeText(getApplicationContext(),
                            "Please enter your correct info!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });

    }


    //login method

    /**
     * Used to log in the user
     * @param email user's email
     * @param password  user's password
     */
    private void login(final String email, final String password) {
        // Tag used to cancel the request
        String cancel_tag = "login";
        progressDialog.setMessage("Logging in...");
        progressDialog.setIndeterminate(true);
        showDialog();



        JSONObject obj = new JSONObject();

        try {
            obj.put("email", email);
            obj.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request  = new JsonObjectRequest(Request.Method.POST, URL_FOR_LOGIN,obj,
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter
                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            Log.d("json", response.toString());
                            boolean error = response.getBoolean("error");
                            hideDialog();

                            if (!error){

                                JSONObject obj = response.getJSONObject("data");

                                String user = response.getString("username");

                                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("username", user);
                                editor.putString("email", email);
                                editor.commit();

                                Intent intent = new Intent(
                                        LoginActivity.this,
                                        MainMenu.class);
                                startActivity(intent);
                                finish();
                            }else {
                                String errorMsg = response.getString("cause");
                                 hideDialog();
                                Toast.makeText(getApplicationContext(),
                                        errorMsg, Toast.LENGTH_LONG).show();
                            }

                        }
                        // Try and catch are included to handle any errors due to JSON
                        catch (JSONException e) {
                            // If an error occurs, this prints the error to the log
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
                        Log.e("Volley", "Error");
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_LONG).show();
                        hideDialog();
                    }
                }
        );

        // Adding request to request queue


        AppVolley.getInstance(getApplicationContext()).addToRequestQueue(request,cancel_tag);
    }



    //to show the dialog
    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    //to hide the dialog
    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }


    // check if the info is valid

    /**
     * check if the user input is valid or not
     * @return true or false
     */
    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() ) {
            _passwordText.setError("enter a valid password ");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }


}
