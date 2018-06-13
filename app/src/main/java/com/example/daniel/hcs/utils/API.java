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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

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
        final DatabaseHelper databaseHelper = DatabaseHelper.getInstance(activity);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(AppConstants.KEY_NAME, name);
            jsonObject.put(AppConstants.KEY_PASSWORD, password);
        } catch (JSONException e) {
            Log.e("ERROR Login2", e.getMessage());
            e.printStackTrace();
            requestListener.failed(e.getMessage());
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                AppConstants.API_BASE_URL + AppConstants.ENDPOINT_LOGIN, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String jwt = response.getString(AppConstants.KEY_JWT);
                    Log.e("LOG RESPONSE", String.valueOf(response));
                    if (jwt != null) {
                        SharedPreferences sharedPreferences = activity.getSharedPreferences(AppConstants.SHARED_PREFERENCES_NAME,
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(AppConstants.KEY_JWT, jwt);
                        editor.putString(AppConstants.KEY_NAME, name);
                        editor.putString(AppConstants.KEY_PASSWORD, password);
                        editor.commit();
                        Log.e("JWT token:", jwt);
                        JSONArray pills = response.getJSONArray(AppConstants.KEY_PILLS);
                        Integer pillsLength = pills.length();
                        if (pillsLength != 0) {
                            for (int i = 0; i < pillsLength; ++i) {
                                JSONObject pillObject = (JSONObject) pills.get(i);
                                databaseHelper.addPill(new Pill(
                                        pillObject.getLong(AppConstants.KEY_SERVER_ID),
                                        pillObject.getString(AppConstants.KEY_NAME),
                                        pillObject.getString(AppConstants.KEY_DESCRIPTION),
                                        pillObject.getLong(AppConstants.KEY_NUMBER_OF_INTAKES)
                                ));

                                JSONArray intakes = pillObject.getJSONArray(AppConstants.KEY_INTAKES);
                                Integer intakesLength = intakes.length();
                                if (intakesLength != 0) {
                                    for (int j = 0; j < intakesLength; ++j) {
                                        JSONObject intakeObject = (JSONObject) intakes.get(j);
                                        databaseHelper.addIntake(new Intake(
                                                intakeObject.getLong(AppConstants.KEY_SERVER_ID),
                                                intakeObject.getLong(AppConstants.KEY_PILL_ID),
                                                intakeObject.getString(AppConstants.KEY_TIME_OF_INTAKE)
                                        ));
                                        JSONArray intakeEvents = intakeObject.getJSONArray(AppConstants.KEY_EVENT_INTAKES);
                                        Integer intakeEventLength = intakeEvents.length();
                                        if (intakeEventLength != 0){
                                            for (int k = 0; k < intakeEventLength; k++) {
                                                JSONObject intakeEventObject = (JSONObject) intakeEvents.get(k);
                                                databaseHelper.addIntakeEvent(new IntakeEvent(
                                                        intakeEventObject.getLong(AppConstants.KEY_PILL_ID),
                                                        intakeEventObject.getLong(AppConstants.KEY_INTAKE_ID),
                                                        intakeEventObject.getBoolean(AppConstants.KEY_TAKEN)
                                                ));
                                            }
                                        }
                                    }
                                }
                            }
                        }
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
                Log.e("REG RESPONSE", String.valueOf(response));
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

    public void createPill(final Pill pill, final List<Intake> intakesList, final RequestListener requestListener) {
        volleyRequestQueue = VolleyRequestQueue.getInstance(activity);
        final DatabaseHelper databaseHelper = DatabaseHelper.getInstance(activity);
        JSONObject jsonObject = new JSONObject();
        Log.e("CREATEPILL", "ENTER");
        try {
            Log.e("CREATEPILL", "ENTER");
            JSONArray intakes = new JSONArray();
            for (Intake intake : intakesList) {
                JSONObject jsonIntake = new JSONObject();
                jsonIntake.put(AppConstants.KEY_TIME_OF_INTAKE, intake.getTimeOfIntake());
                intakes.put(jsonIntake);
            }

            jsonObject.put(AppConstants.KEY_NAME, pill.getName());
            jsonObject.put(AppConstants.KEY_DESCRIPTION, pill.getDescription());
            jsonObject.put(AppConstants.KEY_INTAKES, intakes);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("CREATEPILL", "ENTER");
        }
        Log.e("JSON REQEST", String.valueOf(jsonObject));
        CustomJSONAuthObject jsonObjectRequest = new CustomJSONAuthObject(Request.Method.POST,
                AppConstants.API_BASE_URL + AppConstants.ENDPOINT_PILL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("CREATEPILL", "ENTER");
                Log.e("REG RESPONSE", String.valueOf(response));
                try {
                    Integer status = response.getInt(AppConstants.KEY_STATUS);


                    if (status == 0) {
                        requestListener.failed("Email is taken");
                    } else {
                        Long pillServerId = response.getLong(AppConstants.KEY_SERVER_ID);
                        pill.setServerId(pillServerId);

                        databaseHelper.addPill(pill);
                        JSONObject pills = response.getJSONObject("pills");

                        JSONArray intakes = pills.getJSONArray(AppConstants.KEY_INTAKES);
                        for (int j = 0; j < intakes.length(); j++) {
                            JSONObject intake = (JSONObject) intakes.get(j);
                            Intake in = new Intake(
                                    intake.getLong(AppConstants.KEY_SERVER_ID),
                                    intake.getLong(AppConstants.KEY_PILL_ID),
                                    intake.getString(AppConstants.KEY_TIME_OF_INTAKE)
                            );
                            databaseHelper.addIntake(in);
                        }
                    }
                        /*for (Intake intake: intakesList) {
                            intake.setPillId(pillServerId);
                            Log.e("PILLSERVERID", String.valueOf(intake.getPillId()));
                            Log.e("HOUT", String.valueOf(intake.getTimeOfIntake()));

                            databaseHelper.addIntake(intake);
                        }*/
                    requestListener.finished("success");

                } catch (Exception e) {
                    Log.e("REG exp", String.valueOf(e.getMessage()));

                    requestListener.failed(e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("REG RESPONSE", String.valueOf(error));

                requestListener.failed("Server error please try again");
            }
        }, activity);

        volleyRequestQueue.addToRequestQueue(jsonObjectRequest);
    }

    public void deletePill(final Pill pill, final RequestListener requestListener) {
        volleyRequestQueue = VolleyRequestQueue.getInstance(activity);
        final DatabaseHelper databaseHelper = DatabaseHelper.getInstance(activity);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put(AppConstants.KEY_SERVER_ID, pill.getServerId());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("JSON REQEST", String.valueOf(jsonObject));
        CustomJSONAuthObject jsonObjectRequest = new CustomJSONAuthObject(Request.Method.POST,
                AppConstants.API_BASE_URL + AppConstants.ENDPOINT_PILL_DELETE, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("CREATEPILL", "ENTER");
                Log.e("REG RESPONSE", String.valueOf(response));
                try {
                    Integer status = response.getInt(AppConstants.KEY_STATUS);
                    if (status == 0) {
                    } else {
                        databaseHelper.deletePill(pill);
                        requestListener.finished("success");

                    }
                } catch (Exception e) {
                    Log.e("REG exp", String.valueOf(e.getMessage()));

                    requestListener.failed(e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("REG RESPONSE", String.valueOf(error));

                requestListener.failed("Server error please try again");
            }
        }, activity);

        volleyRequestQueue.addToRequestQueue(jsonObjectRequest);
    }

    public void createEventIntake(final IntakeEvent intakeEvent, final RequestListener requestListener) {
        volleyRequestQueue = VolleyRequestQueue.getInstance(activity);
        final DatabaseHelper databaseHelper = DatabaseHelper.getInstance(activity);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put(AppConstants.KEY_PILL_ID, intakeEvent.getPillId());
            jsonObject.put(AppConstants.KEY_INTAKE_ID, intakeEvent.getIntakeId());
            jsonObject.put(AppConstants.KEY_TAKEN, intakeEvent.getTaken());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("JSON REQEST", String.valueOf(jsonObject));
        CustomJSONAuthObject jsonObjectRequest = new CustomJSONAuthObject(Request.Method.POST,
                AppConstants.API_BASE_URL + AppConstants.ENDPOINT_INTAKE_EVENT, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("CREATEPILL", "ENTER");
                Log.e("REG RESPONSE", String.valueOf(response));
                try {
                    Integer status = response.getInt(AppConstants.KEY_STATUS);
                    if (status == 0) {
                        requestListener.failed("Event intake is not created!");
                    } else {
                        databaseHelper.addIntakeEvent(intakeEvent);
                        requestListener.finished("success");

                    }
                } catch (Exception e) {
                    Log.e("REG exp", String.valueOf(e.getMessage()));

                    requestListener.failed(e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("REG RESPONSE", String.valueOf(error));

                requestListener.failed("Server error please try again");
            }
        }, activity);

        volleyRequestQueue.addToRequestQueue(jsonObjectRequest);
    }

}

