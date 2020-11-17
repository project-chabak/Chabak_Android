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

public class PlaceReviewData implements Parcelable {
    private DateFormat dateFormat;

    private String nickname;
    private int reviewIdx;
    private String reviewContent;
    private Date reviewDate;//date로 수정?
    private String reviewDateString;
    private int reviewStar;
    private int reviewLikeCnt;
    private String[] reviewImg;
    private boolean userLike;
    private int userLikeInt;

    protected PlaceReviewData(Parcel in) {
//        Log.e("PlaceReviewData", "parcel");
//
//        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREAN);
//        dateFormat.setTimeZone(tz);
//        reviewDateString = dateFormat.format(reviewDate);

        this.nickname = in.readString();
        this.reviewIdx = in.readInt();
        this.reviewContent = in.readString();
        this.reviewDateString = in.readString();
        this.reviewStar =in.readInt();
        this.reviewLikeCnt = in.readInt();
        this.reviewImg = in.createStringArray();
//        String[] images = in.readStringArray(reviewImg);
        this.userLikeInt = in.readInt();
//        userLike = in.readBoolean();
    }

    @SuppressWarnings("rawTypes")
    public static final Creator<PlaceReviewData> CREATOR = new Creator<PlaceReviewData>() {
        @Override
        public PlaceReviewData createFromParcel(Parcel in) {
            return new PlaceReviewData(in);
        }

        @Override
        public PlaceReviewData[] newArray(int size) {
            return new PlaceReviewData[size];
        }
    };

    public String getNickname() { return nickname; }

    public int getReviewIdx() { return reviewIdx; }

    public String getReviewContent() { return reviewContent.replace("\\n", "\n"); }

    public String getReviewDate() {
        Log.e("getDate", String.valueOf(reviewDate));
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN);
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        reviewDateString = dateFormat.format(reviewDate);

        return reviewDateString;
    }

    public String getReviewDateString() { return reviewDateString; }

    public int getReviewStar() { return reviewStar; }

    public int getReviewLikeCnt() { return reviewLikeCnt; }

    public String[] getReviewImg() { return reviewImg; }

    public int getUserLikeInt() {
        if(userLike) {
            userLikeInt = 1;
        } else {
            userLikeInt = 0;
        }
        return userLikeInt; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
//        Log.e("writeToParcel", "writeToParcel");
//        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREAN);
//        dateFormat.setTimeZone(tz);
//        reviewDateString = dateFormat.format(reviewDate);

        parcel.writeString(this.nickname);
        parcel.writeInt(this.reviewIdx);
        parcel.writeString(this.reviewContent);
        parcel.writeString(this.reviewDateString);
        parcel.writeInt(this.reviewStar);
        parcel.writeInt(this.reviewLikeCnt);
        parcel.writeStringArray(this.reviewImg);
        parcel.writeInt(this.userLikeInt);
//        parcel.writeBoolean(this.userLike);
    }
}

