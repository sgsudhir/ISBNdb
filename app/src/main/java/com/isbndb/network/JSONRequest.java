package com.isbndb.network;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.isbndb.app.AppController;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sgsudhir on 2/17/2016.
 */

public abstract class JSONRequest {
    Context context;
    JsonObjectRequest jsonObjectRequest;

    public JSONRequest(final Context context, String url, final String msg) {
        this.context = context;

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    onJSONResponse(response.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    onJSONErrorResponse(error.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Accept","application/json");
                return headers;
            }
        };
    }

    protected abstract void onJSONErrorResponse(String response);

    protected abstract void onJSONResponse(String response);

    public void execute(String tag) {
        AppController.getInstance().addToRequestQueue(jsonObjectRequest,tag);
    }

    public void execute() {
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    public static void cancelRequest(String tag) {
        AppController.getInstance().cancelPendingRequests(tag);
    }
}
