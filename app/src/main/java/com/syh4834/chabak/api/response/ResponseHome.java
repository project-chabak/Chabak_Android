package com.syh4834.chabak.api.response;

import com.syh4834.chabak.api.data.HomeData;

public class ResponseHome {
    private final int status;
    private final boolean success;
    private final String message;
    private final HomeData data;

    public ResponseHome(int status, boolean success, String message, HomeData data) {
        this.status = status;
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public HomeData getData() {
        return data;
    }
}
