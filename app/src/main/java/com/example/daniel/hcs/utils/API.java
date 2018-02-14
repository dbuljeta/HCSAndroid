package com.example.daniel.hcs.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.daniel.hcs.interfaces.RequestListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Daniel on 2/14/2018.
 */

public class API {
    private static API APIService;
    private Activity activity;
    private VolleyRequestQueue volleyRequestQueue;

    public static synchronized API getInstance(Activity activity) {
        if (APIService == null) {
            APIService = new API(activity);
        }
        return APIService;
    }

    private API(Activity activity) {
        this.activity = activity;
    }

    public void login(final String name, final String password, final RequestListener requestListener) {
        volleyRequestQueue = VolleyRequestQueue.getInstance(activity);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(AppConstants.KEY_NAME, name);
            jsonObject.put(AppConstants.KEY_PASSWORD, password);
        } catch (JSONException e) {
            Log.e("ERROR Login1", e.getMessage());
            e.printStackTrace();
            requestListener.failed(e.getMessage());
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                AppConstants.API_BASE_URL + AppConstants.ENDPOINT_LOGIN, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String jwt = response.getString(AppConstants.KEY_JWT);

                    if (jwt != null) {
                        SharedPreferences sharedPreferences = activity.getSharedPreferences(AppConstants.SHARED_PREFERENCES_NAME,
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(AppConstants.KEY_JWT, jwt);
                        editor.putString(AppConstants.KEY_NAME, name);
                        editor.putString(AppConstants.KEY_PASSWORD, password);
                        editor.commit();
                        requestListener.finished("success");
                    } else {
                        requestListener.failed("Login4 failed");
                    }
                } catch (Exception e) {
                    Log.e("ERROR Login2", e.getMessage());
                    requestListener.failed("FAILED");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR Login3", String.valueOf(error));
                requestListener.failed("FAILED");
            }
        });

        volleyRequestQueue.addToRequestQueue(jsonObjectRequest);
    }

    public void register(final String name, final String password, final RequestListener requestListener) {
        volleyRequestQueue = VolleyRequestQueue.getInstance(activity);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(AppConstants.KEY_NAME, name);
            jsonObject.put(AppConstants.KEY_PASSWORD, password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                AppConstants.API_BASE_URL + AppConstants.ENDPOINT_REGISTER, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Integer status = response.getInt(AppConstants.KEY_STATUS);
                    if (status == 0) {
                        requestListener.failed("Email is taken");
                    } else {
                        requestListener.finished("success");
                    }
                } catch (Exception e) {
                    requestListener.failed(e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                requestListener.failed("Server error please try again");
            }
        });

        volleyRequestQueue.addToRequestQueue(jsonObjectRequest);
    }

}

