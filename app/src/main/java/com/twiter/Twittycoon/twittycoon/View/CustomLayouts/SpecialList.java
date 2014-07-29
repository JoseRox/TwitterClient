package com.twiter.Twittycoon.twittycoon.View.CustomLayouts;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.twiter.Twittycoon.twittycoon.R;


public class SpecialList extends RelativeLayout /*implements ViewTreeObserver.OnGlobalLayoutListener*/ {

    private static final int SPACER_VIEW_ID = 7363;
    private ListView mResultList;
    private View mHeaderSpace;

    public SpecialList(Context context) {
        super(context);
        mResultList = (ListView) findViewById(R.id.ListViewResults2);
    }

    public SpecialList(Context context, AttributeSet attrs) {
        super(context, attrs);
        mResultList = (ListView) findViewById(R.id.ListViewResults2);
    }

    public SpecialList(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mResultList = (ListView) findViewById(R.id.ListViewResults2);
    }

    public void setListHeader(ListView list){

        mResultList = list;

        mHeaderSpace = new View(getContext());
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) getResources()
                .getDimension(R.dimen.resultListSpace));
        //TODO what for this is needed
//        mHeaderSpace.setId(SPACER_VIEW_ID);
        mHeaderSpace.setLayoutParams(layoutParams);

        mResultList.addHeaderView(mHeaderSpace, null, false);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (mResultList == null || findViewById(R.id.spacerView) == null) {
            return super.onInterceptTouchEvent(ev);
        } else {

            Rect hitRect = new Rect();
            mHeaderSpace.getHitRect(hitRect);

            if (hitRect.contains((int) ev.getX(), (int) ev.getY())) {
                if (ev.getAction() != MotionEvent.ACTION_MOVE) {
                    findViewById(R.id.spacerView).dispatchTouchEvent(ev);
                } else {
                    mResultList.setEnabled(false);
                }
                return false;
            }

            mResultList.getHitRect(hitRect);

            if (hitRect.contains((int) ev.getX(), (int) ev.getY())) {
                mResultList.setEnabled(true);
                mResultList.dispatchTouchEvent(ev);
                ev.setAction(MotionEvent.ACTION_CANCEL);
                findViewById(R.id.spacerView).dispatchTouchEvent(ev);
            } else {
                ev.setAction(MotionEvent.ACTION_CANCEL);
                findViewById(R.id.spacerView).dispatchTouchEvent(ev);
            }
            return false;
        }

    }

//    @Override
//    public void onGlobalLayout() {
//        getViewTreeObserver().removeGlobalOnLayoutListener(this);
//
//        final int heightPx = getContext().getResources().getDisplayMetrics().heightPixels;
//
//        boolean inversed = false;
//        final int childCount = getChildCount();
//
//        for (int i = 0; i < childCount; i++) {
//            View child = getChildAt(i);
//
//            int[] location = new int[2];
//
//            child.getLocationOnScreen(location);
//
//            if (location[1] > heightPx) {
//                break;
//            }
//
//            if (!inversed) {
//                child.startAnimation(AnimationUtils.loadAnimation(getContext(),
//                        R.anim.slide_up_left));
//            } else {
//                child.startAnimation(AnimationUtils.loadAnimation(getContext(),
//                        R.anim.slide_up_right));
//            }
//
//            inversed = !inversed;
//        }
//    }
}

//TODO I dont know why this is needed





















