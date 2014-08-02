package com.twiter.Twittycoon.twittycoon.TwitterRequests;


import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.twiter.Twittycoon.twittycoon.App;
import com.twiter.Twittycoon.twittycoon.Presenter.ICompleteAuthListener;
import com.twiter.Twittycoon.twittycoon.Presenter.ICompletedResultsListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class TwitterRequest implements IServerRequest{

    public static String mKey = "TwaAiAVvgCId5RMyAW0S2HRvh";
    public static String mSecret = "bPWykT0szg8Oo9drxIZt8T4y1OS4VNV6Qtsonp2nYUfzMgUAsA";
    public static String mAccessToken = "";

    public final static String TwitterTokenURL = "https://api.twitter.com/oauth2/token";
    public final static String TwitterSearchURL = "https://api.twitter.com/1.1/search/tweets.json?q=";

    public final static String TwitterGeoURL = "http://api.twitter.com/1/geo/search.json?query=israel";
    public final static String TwitterPopularURL = "http://search.twitter.com/search.json?q=";


    private Context mContext;
    private RequestQueue mRequestQueue;

    private AuthState mAuthState = AuthState.NOT_AUTHENTICATED;
    public enum AuthState { NOT_AUTHENTICATED, AUTHENTICATED, AUTHENTICATING};


    public TwitterRequest(Context context) {
        mContext = context;
        mRequestQueue = VolleySingelton.getInstance(mContext).getRequestQueue();
    }

    @Override
    public void requestAuth(String key, String secret, ICompleteAuthListener completeAuthListener) {

        mAuthState = AuthState.AUTHENTICATING;

        TwitterAuthRequest authReq = new TwitterAuthRequest(Request.Method.POST,TwitterTokenURL,
                tokenSuccessListener(completeAuthListener),
                tokenErrorListener());

        mRequestQueue.add(authReq);
    }

    @Override
    public void requestSearch(String searchTerm, ICompletedResultsListener receivedResultListener) {

        if (!mAccessToken.equals("")){
            String encodedUrl = null;
            try {
                encodedUrl = URLEncoder.encode(searchTerm, "UTF-8");

                TwitterSearchRequest search = new TwitterSearchRequest(Request.Method.GET,
                        TwitterSearchURL + encodedUrl,
                        new TwitterResultsParser(receivedResultListener),
                        searchErrorListener());


                mRequestQueue.add(search);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
    }



    private Response.ErrorListener searchErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("vladi", "search error: " + error);
            }
        };
    }

    private Response.Listener<String> tokenSuccessListener(final ICompleteAuthListener completeAuthListener) {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("vladi", "response: " + response);

                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    if (object.optString("token_type").equals("bearer")) {
                        Log.d("vladi", "access_token: " + object.optString("access_token"));
                        mAccessToken = object.optString("access_token");
                        completeAuthListener.onCompleted();
                        mAuthState = AuthState.AUTHENTICATED;
                    }else{
                        mAuthState = AuthState.NOT_AUTHENTICATED;
                        Log.d(App.TAG,"Authentication error");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private Response.ErrorListener tokenErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("vladi", "error: " + error);
            }
        };
    }

    public AuthState getAuthenticationState(){
        return mAuthState;
    }



}
