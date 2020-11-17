package com.syh4834.chabak.api.request;

public class RequestSignup {
    /**
     * {
     *    "id" : "id",
     *    "nickname" : "닉네임",
     *    "password" : "1234567",
     *    "gender" : "M" or "F",
     *    "birthDate" : "1999-12-31"
     * }
     */

    private String id;
    private String password;
    private String nickname;
    private String gender;
    private String birthDate;

    public RequestSignup(String id, String password, String nickname, String gender, String birthDate) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
        this.gender = gender;
        this.birthDate = birthDate;
    }
}
