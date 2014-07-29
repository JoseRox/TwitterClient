package com.twiter.Twittycoon.twittycoon.Presenter;

import android.content.Context;

import com.twiter.Twittycoon.twittycoon.Requests.IServerRequest;
import com.twiter.Twittycoon.twittycoon.View.ITwitterView;
import com.twiter.Twittycoon.twittycoon.R;
import com.twiter.Twittycoon.twittycoon.Requests.TwitterRequest;
import com.twiter.Twittycoon.twittycoon.data.Searches;

public class TwitterPresenter implements IPresenter, IReceivedResultsListener {

    private Context mContext;
    private ITwitterView mView;
    private IServerRequest mTwitterSearchRequest;
    private String mKey;
    private String mSecret;
    private String mSearchParam = "";

    public TwitterPresenter(Context context,ITwitterView view) {
        mContext = context;
        mView = view;
        mTwitterSearchRequest = new TwitterRequest();
        mKey = mContext.getString(R.string.Key);
        mSecret = mContext.getString(R.string.Secret);
    }

    @Override
    public void setSearchParam(String searchParam){
        mSearchParam = searchParam;
    }

    @Override
    public void pullData() {
        //start server connection
        mTwitterSearchRequest.start(mKey, mSecret, mSearchParam, this);
    }

    @Override
    public void onResultsReceived(Searches results) {
        //get result from server
        mView.showResultList(results);
    }
}
