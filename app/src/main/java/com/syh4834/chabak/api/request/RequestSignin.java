package com.syh4834.chabak.api.request;

public class RequestSignin {
    /**
     * {
     *    "id" : "abc@gmail.com",
     *    "password" : "1234567"
     * }
     */

    private String id;
    private String password;

    public RequestSignin(String id, String password) {
        this.id = id;
        this.password = password;
    }
}


