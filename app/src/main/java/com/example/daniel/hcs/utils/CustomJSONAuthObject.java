package com.example.daniel.hcs.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Daniel on 2/14/2018.
 */

public class CustomJSONAuthObject extends JsonObjectRequest {

    private Context context;

    public CustomJSONAuthObject(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener, Context context) {
        super(method, url, jsonRequest, listener, errorListener);
        this.context = context;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> params = new HashMap<String, String>();
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(AppConstants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
            String jwt = sharedPreferences.getString(AppConstants.KEY_JWT, "");
            String applicationAuthorization = "Bearer " + jwt;
//            params.put("Content-Type", "application/json");
            params.put(AppConstants.APPLICATION_AUTHORIZATION, applicationAuthorization);
        }
        return params;
    }

    @Override
    public RetryPolicy getRetryPolicy() {
        return new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    }
}
