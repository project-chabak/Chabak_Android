package com.syh4834.chabak;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RecyclerListData {

    private String title;
    private String content;
    private String rateText;
    private String date;
    private String region;
    private int goodCount;
    private int reviewCount;

    private int rateImageView;
    private int contentImageView;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) { this.title = title; }

    public String getRateText() {
        return rateText;
    }

    public void setGetRateText(String rateText) {
        this.rateText = rateText;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) { this.region = region; }

    public int getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(int goodCount) {
        this.goodCount = goodCount;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) { this.date = date; }

    public int getRateImageView() {
        return rateImageView;
    }

    public void setRateImageView(int resId) {
        this.rateImageView = resId;
    }

    public int getContentImageView() {
        return contentImageView;
    }

    public void setContentImageView(int resId) {
        this.contentImageView = resId;
    }
}