package com.syh4834.chabak.review.recycler;

public class RecyclerReviewData {

    private String writer;
    private int reviewIdx;
    private int star;
    private String content;
    private String[] picture;
    private int likeCnt;
    private String date;
    private boolean userLike;

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public int getReviewIdx() { return reviewIdx; }

    public void setReviewIdx(int reviewIdx) { this.reviewIdx = reviewIdx; }

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

    public String[] getPicture() {
        return picture;
    }

    public void setPicture(String[] picture) {
        this.picture = picture;
    }

    public int getLikeCnt() {
        return likeCnt;
    }

    public void setLikeCnt(int likeCnt) {
        this.likeCnt = likeCnt;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean getUserLike() { return  userLike; }

    public void setUserLike(boolean userLike) { this.userLike = userLike; }
}
