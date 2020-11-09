package com.syh4834.chabak;

import java.util.ArrayList;

public class RecyclerReviewData {

    private String writer;
    private int star;
    private String content;
    private ArrayList<RecyclerReviewImageData> picture;
    private int goodCount;
    private String date;

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<RecyclerReviewImageData> getPicture() {
        return picture;
    }

    public void setPicture(ArrayList picture) {
        this.picture = picture;
    }

    public int getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(int goodCount) {
        this.goodCount = goodCount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
