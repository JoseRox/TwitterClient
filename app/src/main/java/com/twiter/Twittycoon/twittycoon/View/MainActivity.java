package com.twiter.Twittycoon.twittycoon.View;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.twiter.Twittycoon.twittycoon.Presenter.IPresenter;
import com.twiter.Twittycoon.twittycoon.Presenter.TwitterPresenter;
import com.twiter.Twittycoon.twittycoon.R;
import com.twiter.Twittycoon.twittycoon.data.Searches;


public class MainActivity extends FragmentActivity implements ResultListFragment.OnFragmentInteractionListener, ITwitterView {

    private IPresenter mTwitterPresenter;
//    final static String SearchTerm = "from:alexiskold";
    final static String SearchTerm = "#israel";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        mTwitterPresenter = new TwitterPresenter(getApplicationContext(), this);
        mTwitterPresenter.setSearchParam(SearchTerm);
        mTwitterPresenter.pullData();

        if (savedInstanceState == null) {
            ResultListFragment listFragment = new ResultListFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, listFragment, "ListFragment").commit();
        }
	}


    @Override
    public boolean onSearchRequested() {
        return super.onSearchRequested();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void showResultList(Searches results) {
        ResultListFragment ResultListFragment = (ResultListFragment)getSupportFragmentManager().findFragmentByTag("ListFragment");
        ResultListFragment.updateResultList(results);

    }
}
