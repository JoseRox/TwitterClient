package com.twiter.Twittycoon.twittycoon.Presenter;

import com.twiter.Twittycoon.twittycoon.data.Searches;

/**
 * Created by Jose on 18/07/2014.
 */
public interface IReceivedResultsListener {
    public void onResultsReceived(Searches results);
}
