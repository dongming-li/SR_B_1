package coms309.chesssuitev1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;



/**
 * Created by Abdelrahman on 9/25/2017.
 *
 * This class is responsible for signing up for the up
 */

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private SessionManager session;
    ProgressDialog progressDialog;
    private static final String URL_SignUp= "http://proj-309-sr-b-1.cs.iastate.edu:8008/user";



    EditText _userName;
    EditText _emailText;
    EditText _passwordText;
    Button _signupButton;
    TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        //IDs
        _userName = (EditText) findViewById(R.id.input_name);
        _emailText = (EditText) findViewById(R.id.input_email);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _signupButton = (Button) findViewById(R.id.btn_signup);
        _loginLink = (TextView) findViewById(R.id.link_login);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(SignupActivity.this,
                    MainMenu.class);
            startActivity(intent);
            finish();
        }

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = _userName.getText().toString();
                final String email = _emailText.getText().toString();
                final String password = _passwordText.getText().toString();

                if(validate()){
                    signup(username,email,password);
                }else{
                    Toast.makeText(getApplicationContext(),
                            "Please enter your info!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });


        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go to login activity
                finish();
            }
        });
    }


    /**
     * Used to register a new account
     * @param username account username
     * @param email account email
     * @param password account password
     */
    private void signup(final String username, final String email, final String password) {
        // Tag used to cancel the request
        String cancel_req_tag = "register";

        progressDialog.setMessage("Signing Up ...");
        progressDialog.setIndeterminate(true);
        showDialog();


        JSONObject obj = new JSONObject();

        try {
            obj.put("username", username);
            obj.put("email", email);
            obj.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request  = new JsonObjectRequest(Request.Method.POST, URL_SignUp,obj,
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter
                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONObject object = response;
                            //response.getJSONObject("user");

                            Log.d("signupjson", response.toString());
                            boolean error = object.getBoolean("error");
                            hideDialog();

                            if (!error){
                                Intent intent = new Intent(
                                        SignupActivity.this,
                                        LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }else {
                                String errorMsg = object.getString("cause");
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
        AppVolley.getInstance(getApplicationContext()).addToRequestQueue(request, cancel_req_tag);
    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    //check for a valid input

    /**
     * check if the use input is valid
     *
     * @return true or false
     */
    public boolean validate() {
        boolean valid = true;

        String name = _userName.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _userName.setError("at least 3 characters");
            valid = false;
        } else {
            _userName.setError(null);
        }


        //check the email
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 3) {
            _passwordText.setError("at least 3 characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}