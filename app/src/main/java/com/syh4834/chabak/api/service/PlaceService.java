package com.syh4834.chabak.api.service;

import com.syh4834.chabak.api.request.RequestSignin;
import com.syh4834.chabak.api.request.RequestSignup;
import com.syh4834.chabak.api.response.ResponsePlaceDetail;
import com.syh4834.chabak.api.response.ResponseSignin;
import com.syh4834.chabak.api.response.ResponseSignup;
import com.syh4834.chabak.api.response.ResponseSignupCheckID;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PlaceService {
    final String BASE_RUL = "http://15.164.62.195:3000";

    @Headers("Content-Type: application/json")
    @GET("/place/detail/{placeIdx}")
    Call<ResponsePlaceDetail> getCheckID(@Path("placeIdx") String id);
}
