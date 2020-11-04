package com.syh4834.chabak;

import android.net.Uri;

public class RecyclerReviewUploadImgData {
    //나중에 통신하면 string으로 바꾸기(s3-url)
    private Uri uploadImg;

    public Uri getUploadImg() {
        return uploadImg;
    }

    public void setUploadImg(Uri uploadImg) {
        this.uploadImg = uploadImg;
    }
}
