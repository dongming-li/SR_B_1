package com.acer.android.loginv1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Acer on 9/25/2017.
 */

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    ProgressDialog progressDialog;
    private static final String URL_SignUp= "http://proj-309-sr-b-1.cs.iastate.edu/Register.php";


    @InjectView(R.id.input_name)
    EditText _userName;
    @InjectView(R.id.input_email)
    EditText _emailText;
    @InjectView(R.id.input_password)
    EditText _passwordText;
    @InjectView(R.id.btn_signup)
    Button _signupButton;
    @InjectView(R.id.link_login)
    TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

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





    private void signup(final String username,  final String email, final String password) {
        // Tag used to cancel the request
        String cancel_req_tag = "register";

        progressDialog.setMessage("Signing Up ...");
        progressDialog.setIndeterminate(true);
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_SignUp, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {

                        String user = jObj.getJSONObject("user").getString("username");
                        Toast.makeText(getApplicationContext(), "Hi " + user +", You are successfully Added!", Toast.LENGTH_SHORT).show();
                        // Launch login activity
                        Intent intent = new Intent(
                                SignupActivity.this,
                                LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {

                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Error happend", Toast.LENGTH_LONG).show();
                hideDialog();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        // Adding request to request queue
        AppVolley.getInstance(getApplicationContext()).addToRequestQueue(strReq, cancel_req_tag);
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