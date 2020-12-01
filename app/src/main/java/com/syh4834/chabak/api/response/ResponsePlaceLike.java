package com.syh4834.chabak.api.response;


import com.syh4834.chabak.api.data.PlaceLikeData;

public class ResponsePlaceLike {
    private final int status;
    private final boolean success;
    private final String message;
    private final PlaceLikeData[] data;

    public ResponsePlaceLike(int status, boolean success, String message, PlaceLikeData[] data) {
        this.status = status;
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public int getStatus() { return status; }
    public boolean getSuccess() { return success; }
    public String getMessage() { return message; }
    public PlaceLikeData[] getData() { return data; }
}
