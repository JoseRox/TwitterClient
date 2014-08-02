package com.twiter.Twittycoon.twittycoon.View;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.squareup.picasso.Picasso;
import com.twiter.Twittycoon.twittycoon.R;
import com.twiter.Twittycoon.twittycoon.data.Search;
import com.twiter.Twittycoon.twittycoon.data.Searches;
import com.twiter.Twittycoon.twittycoon.data.TweetMetadata;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ResultsListAdapter extends ArrayAdapter {

    private Context mContext;
    private ImageLoader mImageLoader;

    public ResultsListAdapter(Context context,Searches objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
        mContext = context;
    }

    private class ViewHolder{
        private TextView mFullName;
        private TextView mNickName;
        private TextView mTweet;
//        private CircularImageView mUserImage;
        private ImageView mUserImage;
        private TextView mDateCreated;
        private TextView mRetweeted;
        private ImageView mPopularStar;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        final ViewHolder holder;
        Search item = (Search)getItem(position);
        View viewToUse;
        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null){
            viewToUse = mInflater.inflate(R.layout.card_item_layout, null);
            holder = new ViewHolder();

            holder.mFullName = (TextView)viewToUse.findViewById(R.id.textViewFullName);
            holder.mNickName = (TextView)viewToUse.findViewById(R.id.textViewNickName);
            holder.mTweet = (TextView)viewToUse.findViewById(R.id.textViewTweet);
//            holder.mUserImage = (CircularImageView) viewToUse.findViewById(R.id.imageViewUserImage);
            holder.mUserImage = (ImageView) viewToUse.findViewById(R.id.imageViewUserImage);
            holder.mDateCreated = (TextView) viewToUse.findViewById(R.id.textViewCreated);

            holder.mRetweeted = (TextView) viewToUse.findViewById(R.id.textViewRetweeted);
            holder.mPopularStar = (ImageView) viewToUse.findViewById(R.id.imageViewPopularStar);

            viewToUse.setTag(holder);

        } else {
            viewToUse = convertView;
            holder = (ViewHolder) viewToUse.getTag();
        }

        holder.mFullName.setText(item.getUser().getName());
        holder.mNickName.setText(item.getUser().getScreenName());
        holder.mTweet.setText(item.getText());
        holder.mDateCreated.setText(getTimeFromTweetCreation(parseDate(item.getDateCreated())));

//        Log.d("vladi", getTimeFromTweetCreation(parseDate(item.getDateCreated())));
//        Log.d("vladi", "fullName: " + holder.mFullName.getText() + " nick: " + holder.mNickName.getText() + " tweet: " + holder.mTweet.getText()
//                + " date: " + holder.mDateCreated.getText());

        Picasso.with(mContext).load(item.getUser().getProfileFullImageUrl())
                                .placeholder(R.drawable.contact_picture_placeholder)
                                .into(holder.mUserImage);


        TweetMetadata tweetMetadata = item.getTweetMetaData();
        if (tweetMetadata.getRecentRetweets() > 0){
            holder.mRetweeted.setText(tweetMetadata.getRecentRetweets());
        }

        if (tweetMetadata.getResultType().equals("popular")){
            holder.mPopularStar.setImageDrawable(mContext.getResources().getDrawable(R.drawable.star));
        }

        return viewToUse;
    }

    private String getTimeFromTweetCreation(Date tweetDate) {

        Calendar currentCal = Calendar.getInstance();
        Date currentDate = new Date(System.currentTimeMillis());
        currentCal.setTime(currentDate);

        Calendar tweetCal = Calendar.getInstance();
        tweetCal.setTime(tweetDate);

        //check if same date
        if (isMinutesDif(currentCal, tweetCal)){
            //the dif is minutes
            return currentCal.get(Calendar.MINUTE) - tweetCal.get(Calendar.MINUTE) + "m";
        }else if (isHoursDif(currentCal, tweetCal)){
            //the dif is hours
            return currentCal.get(Calendar.HOUR_OF_DAY) - tweetCal.get(Calendar.HOUR_OF_DAY) + "h";
        }else if (isDayDif(currentCal, tweetCal)){
            return currentCal.get(Calendar.DAY_OF_MONTH) - tweetCal.get(Calendar.DAY_OF_MONTH) + "d";
        }
        //ignores months and years.

        return "";

    }

    private boolean isDayDif(Calendar currentCal, Calendar tweetCal) {
        if (currentCal.get(Calendar.MONTH) == tweetCal.get(Calendar.MONTH) &&
                currentCal.get(Calendar.YEAR) == tweetCal.get(Calendar.YEAR)){
            return true;
        }else{
            return false;
        }
    }

    private boolean isHoursDif(Calendar currentCal, Calendar tweetCal) {
        if (currentCal.get(Calendar.DAY_OF_MONTH) == tweetCal.get(Calendar.DAY_OF_MONTH) &&
                currentCal.get(Calendar.MONTH) == tweetCal.get(Calendar.MONTH) &&
                    currentCal.get(Calendar.YEAR) == tweetCal.get(Calendar.YEAR)){
            return true;
        }else{
            return false;
        }
    }

    private boolean isMinutesDif(Calendar currentCal, Calendar tweetCal) {
        if (currentCal.get(Calendar.HOUR_OF_DAY) == tweetCal.get(Calendar.HOUR_OF_DAY) &&
                currentCal.get(Calendar.DAY_OF_MONTH) == tweetCal.get(Calendar.DAY_OF_MONTH) &&
                    currentCal.get(Calendar.MONTH) == tweetCal.get(Calendar.MONTH) &&
                        currentCal.get(Calendar.YEAR) == tweetCal.get(Calendar.YEAR)){
            return true;
        }else{
            return false;
        }
    }

    private Date parseDate(String tweetDate){
        String month;

        tweetDate = tweetDate.substring(4,tweetDate.length());
        month = tweetDate.substring(0,3);
        tweetDate = tweetDate.substring(3,tweetDate.length());

        if (month.equals("Jan")){
            month = "01";
        }else if (month.equals("Feb")){
            month = "02";
        }else if (month.equals("Mar")){
            month = "03";
        }else if (month.equals("Apr")){
            month = "04";
        }else if (month.equals("May")){
            month = "05";
        }else if (month.equals("Jun")){
            month = "06";
        }else if (month.equals("Jul")){
            month = "07";
        }else if (month.equals("Aug")){
            month = "08";
        }else if (month.equals("Sep")){
            month = "09";
        }else if (month.equals("Oct")){
            month = "10";
        }else if (month.equals("Nov")){
            month = "11";
        }else if (month.equals("Dec")){
            month = "12";
        }
        else{
            month = "01";
        }

        String year = tweetDate.substring(tweetDate.length() - 4, tweetDate.length());
        int indexOfTZ = tweetDate.indexOf("+");
        tweetDate = tweetDate.substring(0,indexOfTZ - 1);
        tweetDate +=  " " + year;
        String dateToParse = month + tweetDate;
        //  01 19 15:58:15 2014

        SimpleDateFormat format = new SimpleDateFormat("MM dd HH:mm:ss yyyy");
        Date date = null;
        try {
            date = format.parse(dateToParse);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }



}
