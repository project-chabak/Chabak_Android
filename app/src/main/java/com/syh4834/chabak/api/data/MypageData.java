package com.syh4834.chabak.api.data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MypageData {
    private DateFormat dateFormat;

    private int userIdx;
    private String nickname;
    private String id;
    private Date birthDate;
    private String gender;

    public String getId() {
        return id;
    }

    public String getNickname() { return nickname; }

    public String getBirthDate() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(birthDate); }

    public String getGender() { return gender; }
}
