
package com.example.daniel.hcs.utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Daniel on 2/14/2018.
 */

public class VolleyRequestQueue {

    private RequestQueue requestQueue;
    private static VolleyRequestQueue volleyRequestQueue;
    private static Context context;

    private VolleyRequestQueue(Context context) {
        this.context = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized VolleyRequestQueue getInstance(Context context) {
        if (volleyRequestQueue == null) {
            volleyRequestQueue = new VolleyRequestQueue(context);
        }
        return volleyRequestQueue;
    }

    public RequestQueue getRequestQueue() {
        if(requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }
}
