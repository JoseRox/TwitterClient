package com.twiter.Twittycoon.twittycoon.TwitterRequests;

import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class TwitterAuthRequest extends StringRequest {

    public TwitterAuthRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        params.put("grant_type", "client_credentials");
        return params;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<String, String>();

        String combined = TwitterRequest.mKey + ":" + TwitterRequest.mSecret;
        String base64EncodedParams = Base64.encodeToString(combined.getBytes(), Base64.NO_WRAP);

        headers.put("Authorization", "Basic " + base64EncodedParams);
        headers.put("Content-Type",  "application/x-www-form-urlencoded;charset=UTF-8");

        return headers;
    }
}
