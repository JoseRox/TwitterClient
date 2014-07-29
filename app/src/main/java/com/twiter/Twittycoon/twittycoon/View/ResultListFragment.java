package com.twiter.Twittycoon.twittycoon.View;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.twiter.Twittycoon.twittycoon.R;
import com.twiter.Twittycoon.twittycoon.View.CustomLayouts.SpecialList;
import com.twiter.Twittycoon.twittycoon.data.Searches;

public class ResultListFragment extends Fragment {

    private Activity mActivity;
//    private ListView mResultListView;
    private ListView mResultListView;
    private OnFragmentInteractionListener mListener;
    private SpecialList mSpecialListLayout;
    private EditText mSearchEditText;

    public ResultListFragment() {
        // Required empty public constructor
    }

    public void updateResultList(Searches results){
        mSpecialListLayout.setListHeader(mResultListView);
        ResultsListAdapter mAdapter = new ResultsListAdapter(getActivity(), results);
        mResultListView.setAdapter(mAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        View v = inflater.inflate(R.layout.fragment_result_fragment, container, false);
        View v = inflater.inflate(R.layout.fragment_search_layout, container, false);
        mSpecialListLayout = (SpecialList) v.findViewById(R.id.specialListLayout);
        mSpecialListLayout.requestFocus();
//        mResultListView = (ListView) v.findViewById(R.id.ListViewResults2);
        mResultListView = (ListView) v.findViewById(R.id.ListViewResults2);
        mSearchEditText = (EditText) v.findViewById(R.id.editTextSearch);
        mSearchEditText.setOnClickListener(mEditTextClickListener);


        return v;
    }

    View.OnClickListener mEditTextClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((EditText)v).setText("");
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
        public void onFragmentInteraction(Uri uri);
    }

}
