package com.twiter.Twittycoon.twittycoon.View;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.twiter.Twittycoon.twittycoon.R;
import com.twiter.Twittycoon.twittycoon.data.Searches;

public class ResultListFragment extends Fragment {

    private ListView mResultListView;
    private OnFragmentInteractionListener mListener;
    private EditText mSearchEditText;
    private ProgressBar mProgressBar;
    private static final String TWITTER = "  Twitter";

    public ResultListFragment() {
        // Required empty public constructor
    }

    public void updateResultList(Searches results){
        mProgressBar.setVisibility(View.GONE);
        ResultsListAdapter mAdapter = new ResultsListAdapter(getActivity(), results);
        mResultListView.setAdapter(mAdapter);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_layout, container, false);

        mResultListView = (ListView) v.findViewById(R.id.ListViewResults);
        Searches items = new Searches();
        mResultListView.setAdapter(new ResultsListAdapter(getActivity(),items));

        mSearchEditText = (EditText) v.findViewById(R.id.editTextSearch);
        mSearchEditText.setOnClickListener(mEditTextClickListener);
        mSearchEditText.addTextChangedListener(mSearchTextWatcher);

        mProgressBar = (ProgressBar) v.findViewById(R.id.progressBarLoading);

        return v;
    }

    TextWatcher mSearchTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (!charSequence.toString().equals("") && !charSequence.toString().equals("#")) {
                mProgressBar.setVisibility(View.VISIBLE);
                mListener.onSearchRequested(charSequence.toString());
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    View.OnClickListener mEditTextClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mSearchEditText.equals(TWITTER)) {
                mSearchEditText.setText("");
            }
        }
    };


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()+ " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        public void onSearchRequested(String searchTerm);
    }

}
