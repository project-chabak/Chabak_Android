package com.syh4834.chabak.api.response;

import com.syh4834.chabak.api.data.PlaceLikeData;
import com.syh4834.chabak.api.data.PlaceListData;


public class ResponsePlaceList {
    private final int status;
    private final boolean success;
    private final String message;
    private final PlaceListData data;

    public ResponsePlaceList(int status, boolean success, String message, PlaceListData data) {
        this.status = status;
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public int getStatus() { return status; }

    public boolean getSuccess() { return success; }

    public String getMessage() { return message; }

    public PlaceListData getData() { return data; }

}
