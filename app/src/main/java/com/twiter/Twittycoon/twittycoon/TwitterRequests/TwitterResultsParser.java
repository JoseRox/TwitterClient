package com.twiter.Twittycoon.twittycoon.TwitterRequests;

import com.android.volley.Response;
import com.twiter.Twittycoon.twittycoon.Presenter.ICompletedResultsListener;
import com.twiter.Twittycoon.twittycoon.data.Search;
import com.twiter.Twittycoon.twittycoon.data.Searches;
import com.twiter.Twittycoon.twittycoon.data.TweetMetadata;
import com.twiter.Twittycoon.twittycoon.data.TwitterUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;


public class TwitterResultsParser implements Response.Listener<JSONObject> {

    private ICompletedResultsListener mResultsListener;

    public TwitterResultsParser(ICompletedResultsListener receivedResultListener) {
        mResultsListener = receivedResultListener;
    }

    @Override
    public void onResponse(JSONObject response) {
        Searches searches = new Searches();
        JSONArray tweetsResult = response.optJSONArray("statuses");

        if (tweetsResult != null) {
            for (int i = 0; i < tweetsResult.length(); i++) {

                Search tweet = new Search();
                JSONObject tweetJson = tweetsResult.optJSONObject(i);

                try {
                    tweet.setDateCreated(tweetJson.getString("created_at"));
                    tweet.setId(tweetJson.getLong("id"));
                    tweet.setIdStr(tweetJson.getString("id_str"));
                    tweet.setText(tweetJson.getString("text"));
                    tweet.setSource(tweetJson.getString("source"));
                    tweet.setIsTruncated(tweetJson.getBoolean("truncated"));

                    tweet.setInReplyToStatusId(parseLong(tweetJson.getString("in_reply_to_status_id")));
                    tweet.setInReplyToStatusIdStr(tweetJson.getString("in_reply_to_status_id_str"));

                    tweet.setInReplyToUserId(parseLong(tweetJson.getString("in_reply_to_user_id")));
                    tweet.setInReplyToUserIdStr(tweetJson.getString("in_reply_to_user_id_str"));
                    tweet.setInReplyToScreenName(tweetJson.getString("in_reply_to_screen_name"));

                    TwitterUser user = new TwitterUser();

                    JSONObject tweetUserJson = tweetJson.getJSONObject("user");
                    user.setName(tweetUserJson.getString("name"));
                    user.setScreenName(tweetUserJson.getString("screen_name"));
                    user.setProfileImageUrl(tweetUserJson.getString("profile_image_url"));
                    tweet.setUser(user);

                    TweetMetadata tweetMeta = new TweetMetadata();

                    JSONObject tweetMetaJson = tweetJson.getJSONObject("metadata");
                    //because all result_type are recents
                    tweetMeta.setResultType(getResultType());
//                    tweetMeta.setResultType(tweetMetaJson.getString("result_type"));

                    if (tweetMetaJson.has("recent_retweets")) {
                        tweetMeta.setRecentRetweets(tweetMetaJson.getInt("recent_retweets"));
                    }

                    tweet.setTweetMetaData(tweetMeta);

                    searches.add(tweet);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
        mResultsListener.onCompletedResults(searches);
    }

    private String getResultType() {
        Random random = new Random();
        if (random.nextInt(6) == 1){
            return "popular";
        }else return "recent";

    }
    private long parseLong(String num){
        if (!num.equals("null")){
            return Long.parseLong(num);
        }return 0;
    }
}
