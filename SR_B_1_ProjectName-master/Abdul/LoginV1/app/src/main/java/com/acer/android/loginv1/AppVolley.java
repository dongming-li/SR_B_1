package com.acer.android.loginv1;

/**
 * Created by Acer on 10/2/2017.
 */

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AppVolley {
    private static AppVolley mAppSingletonInstance;
    private RequestQueue mRequestQueue;
    private static Context mContext;

    private AppVolley(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized AppVolley getInstance(Context context) {
        if (mAppSingletonInstance == null) {
            mAppSingletonInstance = new AppVolley(context);
        }
        return mAppSingletonInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            //get the state of the app
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req,String tag) {
        req.setTag(tag);
        getRequestQueue().add(req);
    }
}