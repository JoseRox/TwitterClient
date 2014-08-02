package com.twiter.Twittycoon.twittycoon.TwitterRequests;

import com.twiter.Twittycoon.twittycoon.Presenter.ICompleteAuthListener;
import com.twiter.Twittycoon.twittycoon.Presenter.ICompletedResultsListener;

public interface IServerRequest {
    void requestAuth(String key, String secret, ICompleteAuthListener receivedResultListener);

    void requestSearch(String searchTerm, ICompletedResultsListener receivedResultListener);


}
