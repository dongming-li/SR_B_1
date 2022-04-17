package com.acer.android.loginv1;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Acer on 9/28/2017.
 */

public class SignupRequest extends StringRequest {


    private static final String Register_Request_URL = "";
    private Map<String, String> params;

    public SignupRequest(String username, String password, String email, Response.Listener<String> Listener ){
        super(Method.POST, Register_Request_URL, Listener,null);
        params = new HashMap<>();
        params.put("email",email);
        params.put("username",username);
        params.put("password", password);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }



}
