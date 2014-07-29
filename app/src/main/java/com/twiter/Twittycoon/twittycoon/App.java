package com.twiter.Twittycoon.twittycoon;

import android.app.Application;


public class App extends Application {
    public final static String TAG = "twitter";
    public final static String TwitterTokenURL = "https://api.twitter.com/oauth2/token";
    public final static String TwitterSearchURL = "https://api.twitter.com/1.1/search/tweets.json?q=";
}
