package com.twiter.Twittycoon.twittycoon.Requests;

import com.twiter.Twittycoon.twittycoon.Presenter.IReceivedResultsListener;

/**
 * Created by Jose on 18/07/2014.
 */
public interface IServerRequest {
//    public void getSearchResults();
    void start(String key, String secret, String searchParam, IReceivedResultsListener receivedResultListener);
}
