package com.twiter.Twittycoon.twittycoon.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jose on 02/08/2014.
 */
public class TweetMetadata {

    @SerializedName("result_type")
    private String resultType;

    @SerializedName("recent_retweets")
    private int recentRetweets;


    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public int getRecentRetweets() {
        return recentRetweets;
    }

    public void setRecentRetweets(int recentRetweets) {
        this.recentRetweets = recentRetweets;
    }
}
