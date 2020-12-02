package com.syh4834.chabak;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RecyclerListData {

    private boolean userLike;
    private String title;
    private String content;
    private double rateText;
    private int rateImageView;
    private String contentImageView;
    private int placeIdx;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) { this.title = title; }

    public double getRateText() {
        return rateText;
    }

    public void setGetRateText(double rateText) {
        this.rateText = rateText;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRateImageView() {
        return rateImageView;
    }

    public int getPlaceIdx() {
        return placeIdx;
    }

    public void setPlaceIdx(int placeIdx) {
        this.placeIdx = placeIdx;
    }

    public void setRateImageView(int resId) {
        this.rateImageView = resId;
    }

    public String getContentImageView() {
        return contentImageView;
    }

    public void setContentImageView(String resId) {
        this.contentImageView = resId;
    }

    public boolean getUserLike() {
        return userLike;
    }

    public void setUserLike(boolean userLike) {
        this.userLike = userLike;
    }
}