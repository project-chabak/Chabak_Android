package com.syh4834.chabak.api.response;

import com.syh4834.chabak.api.data.PlaceDetailData;
import com.syh4834.chabak.api.data.PlaceReviewData;
import com.syh4834.chabak.api.data.SigninData;

public class ResponsePlaceReview {
    private final int status;
    private final boolean success;
    private final String message;
    private final PlaceReviewData[] data;

    public ResponsePlaceReview(int status, boolean success, String message, PlaceReviewData[] data) {
        this.status = status;
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public int getStatus() { return status; }
    public boolean getSuccess() { return success; }
    public String getMessage() { return message; }
    public PlaceReviewData[] getData() { return data; }

}
