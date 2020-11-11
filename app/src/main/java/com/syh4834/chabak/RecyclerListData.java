package com.syh4834.chabak;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RecyclerListData {

    private String title;
    private String content;
    private String rateText;

    private int rateImageView;
    private int contentImageView;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRateText() {
        return rateText;
    }

    public void setGetRateText(String rateText) {
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