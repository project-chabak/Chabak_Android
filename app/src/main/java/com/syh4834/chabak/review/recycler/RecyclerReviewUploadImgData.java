package com.syh4834.chabak.review.recycler;

import android.net.Uri;

public class RecyclerReviewUploadImgData {
    //나중에 통신하면 string으로 바꾸기(s3-url)
    private String uploadImg;

    public String getUploadImg() {
        return uploadImg;
    }

    public void setUploadImg(String uploadImg) {
        this.uploadImg = uploadImg;
    }
}
