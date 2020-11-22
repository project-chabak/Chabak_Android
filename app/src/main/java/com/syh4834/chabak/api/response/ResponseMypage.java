package com.syh4834.chabak.api.response;

import com.syh4834.chabak.api.data.MypageData;
import com.syh4834.chabak.api.data.SigninData;

public class ResponseMypage {
    private final int status;
    private final boolean success;
    private final String message;
    private final MypageData data;

    public ResponseMypage(int status, boolean success, String message, MypageData data) {
        this.status = status;
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public int getStatus() { return status; }
    public boolean getSuccess() { return success; }
    public String getMessage() { return message; }
    public MypageData getData() { return data; }
}
