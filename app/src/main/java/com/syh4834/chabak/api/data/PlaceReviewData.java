package com.syh4834.chabak.api.data;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class PlaceReviewData {
    private DateFormat dateFormat;

    private String nickname;
    private int reviewIdx;
    private String reviewContent;
    private Date reviewDate;
    private String reviewDateString;
    private int reviewStar;
    private int reviewLikeCnt;
    private String[] reviewImg;
    private boolean userLike;

    public String getNickname() { return nickname; }

    public int getReviewIdx() { return reviewIdx; }

    public String getReviewContent() { return reviewContent.replace("\\n", "\n"); }

    public String getReviewDate() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN);
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        //reviewDateString = dateFormat.format(reviewDate);
        Log.e("reviewDate", String.valueOf(reviewDate));

        return dateFormat.format(reviewDate);
    }

    public int getReviewStar() { return reviewStar; }

    public int getReviewLikeCnt() { return reviewLikeCnt; }

    public String[] getReviewImg() { return reviewImg; }

    public boolean getUserLike() {
        return userLike;
    }
}

