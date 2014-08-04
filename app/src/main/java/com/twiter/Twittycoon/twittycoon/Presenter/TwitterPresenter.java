package com.twiter.Twittycoon.twittycoon.Presenter;

import android.content.Context;

import com.twiter.Twittycoon.twittycoon.R;
import com.twiter.Twittycoon.twittycoon.TwitterRequests.IServerRequest;
import com.twiter.Twittycoon.twittycoon.TwitterRequests.TwitterRequest;
import com.twiter.Twittycoon.twittycoon.View.ITwitterView;
import com.twiter.Twittycoon.twittycoon.data.Searches;

public class TwitterPresenter implements IPresenter {

    private Context mContext;
    private ITwitterView mView;
    private IServerRequest mTwitterSearchRequest;
    private String mKey;
    private String mSecret;
    private String mSearchParam = "";

    public TwitterPresenter(Context context,ITwitterView view) {
        mContext = context;
        mView = view;
        mKey = mContext.getString(R.string.Key);
        mSecret = mContext.getString(R.string.Secret);
        mTwitterSearchRequest = new TwitterRequest(mContext);
    }

    @Override
    public void setSearchParam(String searchParam){
        mSearchParam = searchParam;
    }

    @Override
    public void pullData() {

        if (mTwitterSearchRequest instanceof TwitterRequest) {
            TwitterRequest.AuthState authState = ((TwitterRequest) mTwitterSearchRequest).getAuthenticationState();

            if (authState == TwitterRequest.AuthState.AUTHENTICATED) {
                searchFotTweets();
            } else if (authState == TwitterRequest.AuthState.NOT_AUTHENTICATED){
                mTwitterSearchRequest.requestAuth(mKey, mSecret, new ICompleteAuthListener() {
                    @Override
                    public void onCompleted() {
                        searchFotTweets();
                    }
                });
            }
        }


    }

    private void searchFotTweets() {
        mTwitterSearchRequest.requestSearch(mSearchParam,new ICompletedResultsListener() {
            @Override
            public void onCompletedResults(Searches results) {
                if (mView != null) {
                    mView.showResultList(results);
                }
            }
        });
    }


}
