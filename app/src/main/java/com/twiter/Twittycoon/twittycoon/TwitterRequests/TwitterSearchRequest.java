package com.twiter.Twittycoon.twittycoon.TwitterRequests;


import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TwitterSearchRequest extends JsonObjectRequest {


    public TwitterSearchRequest(int method, String url,Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, null, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<String, String>();

        headers.put("Authorization", "Bearer " + TwitterRequest.mAccessToken);
        headers.put("Content-Type", "application/json");

        return headers;
    }

}
