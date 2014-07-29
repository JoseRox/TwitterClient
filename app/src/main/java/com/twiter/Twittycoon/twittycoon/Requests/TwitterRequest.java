package com.twiter.Twittycoon.twittycoon.Requests;

import android.os.AsyncTask;
import android.util.Base64;

import com.google.gson.Gson;
import com.twiter.Twittycoon.twittycoon.App;
import com.twiter.Twittycoon.twittycoon.Presenter.IReceivedResultsListener;
import com.twiter.Twittycoon.twittycoon.data.Authenticated;
import com.twiter.Twittycoon.twittycoon.data.SearchResults;
import com.twiter.Twittycoon.twittycoon.data.Searches;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class TwitterRequest extends AsyncTask<String, Void, String> implements IServerRequest {
    private String mKey = null;
    private String mSecret = null;
    private IReceivedResultsListener mReceivedResultListener;

    public TwitterRequest() {}

    @Override
    protected String doInBackground(String... searchTerms) {
        String result = null;

        if (searchTerms.length > 0) {
            result = getSearchStream(searchTerms[0]);
        }
        return result;
    }

    // onPostExecute convert the JSON results into a Twitter object (which is an Array list of tweets
    @Override
    protected void onPostExecute(String result) {
        //TODO if result = BadRequest
        Searches searches = jsonToSearches(result);
        mReceivedResultListener.onResultsReceived(searches);
    }

    // download twitter searches after first checking to see if there is a network connection

    // converts a string of JSON data into a SearchResults object
    private Searches jsonToSearches(String result) {
        Searches searches = null;
        if (result != null && result.length() > 0) {
            try {
                Gson gson = new Gson();
                // bring back the entire search object
                SearchResults sr = gson.fromJson(result, SearchResults.class);
                // but only pass the list of tweets found (called statuses)
                searches = sr.getStatuses();
            } catch (IllegalStateException ex) {
                // just eat the exception for now, but you'll need to add some handling here
            }
        }
        return searches;
    }

    // convert a JSON authentication object into an Authenticated object
    private Authenticated jsonToAuthenticated(String rawAuthorization) {
        Authenticated auth = null;
        if (rawAuthorization != null && rawAuthorization.length() > 0) {
            try {
                Gson gson = new Gson();
                auth = gson.fromJson(rawAuthorization, Authenticated.class);
            } catch (IllegalStateException ex) {
                // just eat the exception for now, but you'll need to add some handling here
            }
        }
        return auth;
    }

    private String getResponseBody(HttpRequestBase request) {
        StringBuilder sb = new StringBuilder();
        try {

            DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
            HttpResponse response = httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            String reason = response.getStatusLine().getReasonPhrase();

            if (statusCode == 200) {

                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();

                BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                String line = null;
                while ((line = bReader.readLine()) != null) {
                    sb.append(line);
                }
            } else {
                sb.append(reason);
            }
        } catch (UnsupportedEncodingException ex) {
        } catch (ClientProtocolException ex1) {
        } catch (IOException ex2) {
        }
        return sb.toString();
    }

    private String getStream(String url) {
        String results = null;

        // Step 1: Encode consumer key and secret
        try {
            // URL encode the consumer key and secret
            String urlApiKey = URLEncoder.encode(mKey, "UTF-8");
            String urlApiSecret = URLEncoder.encode(mSecret, "UTF-8");

            // Concatenate the encoded consumer key, a colon character, and the encoded consumer secret
            String combined = urlApiKey + ":" + urlApiSecret;

            // Base64 encode the string
            String base64Encoded = Base64.encodeToString(combined.getBytes(), Base64.NO_WRAP);

            // Step 2: Obtain a bearer token
            HttpPost httpPost = new HttpPost(App.TwitterTokenURL);
            httpPost.setHeader("Authorization", "Basic " + base64Encoded);
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            httpPost.setEntity(new StringEntity("grant_type=client_credentials"));
            String rawAuthorization = getResponseBody(httpPost);
            Authenticated auth = jsonToAuthenticated(rawAuthorization);

            // Applications should verify that the value associated with the
            // token_type key of the returned object is bearer
            if (auth != null && auth.getToken_type().equals("bearer")) {

                // Step 3: Authenticate API requests with bearer token
                HttpGet httpGet = new HttpGet(url);

                // construct a normal HTTPS request and include an Authorization
                // header with the value of Bearer <>
                httpGet.setHeader("Authorization", "Bearer " + auth.getAccess_token());
                httpGet.setHeader("Content-Type", "application/json");
                // update the results with the body of the response
                results = getResponseBody(httpGet);
            }
        } catch (UnsupportedEncodingException ex) {
        } catch (IllegalStateException ex1) {
        }
        return results;
    }

    private String getSearchStream(String searchTerm) {
        String results = null;
        try {
            String encodedUrl = URLEncoder.encode(searchTerm, "UTF-8");
            results = getStream(App.TwitterSearchURL + encodedUrl);
        } catch (UnsupportedEncodingException ex) {
        } catch (IllegalStateException ex1) {
        }
        return results;
    }


    @Override
    public void start(String key, String secret, String searchParam, IReceivedResultsListener receivedResultListener) {
        mKey = key;
        mSecret = secret;
        mReceivedResultListener = receivedResultListener;
        this.execute(searchParam);
    }


}

