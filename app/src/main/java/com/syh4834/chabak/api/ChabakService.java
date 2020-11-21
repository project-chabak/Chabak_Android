package com.syh4834.chabak.api;

import com.syh4834.chabak.api.request.RequestSignin;
import com.syh4834.chabak.api.request.RequestSignup;
import com.syh4834.chabak.api.response.ResponsePlaceDetail;
import com.syh4834.chabak.api.response.ResponsePlaceLike;
import com.syh4834.chabak.api.response.ResponsePlaceList;
import com.syh4834.chabak.api.response.ResponsePlaceReview;
import com.syh4834.chabak.api.response.ResponseSignin;
import com.syh4834.chabak.api.response.ResponseSignup;
import com.syh4834.chabak.api.response.ResponseSignupCheckID;
import com.syh4834.chabak.api.response.ResponseUploadReview;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ChabakService {
    final String BASE_RUL = "http://15.164.62.195:3000";

    @GET("/auth/signup/check")
    Call<ResponseSignupCheckID> getCheckID(@Query("id") String id);

    @Headers("Content-Type: application/json")
    @POST("/auth/signup")
    Call<ResponseSignup> signup(@Body RequestSignup requestSignup);

    @Headers("Content-Type: application/json")
    @POST("/auth/login")
    Call<ResponseSignin> signin(@Body RequestSignin requestSignin);

    @Headers("Content-Type: application/json")
    @GET("/place/detail/{placeIdx}")
    Call<ResponsePlaceDetail> getPlaceDetail(@Header("token") String token, @Path("placeIdx") int placeIdx);

    @Headers("Content-Type: application/json")
    @GET("/review/{placeIdx}")
    Call<ResponsePlaceReview> getPlaceReview(@Header("token") String token, @Path("placeIdx") int placeIdx, @Query("order") String order);

    @Multipart
    @POST("/review/write")
    Call<ResponseUploadReview> uploadReview(@Header("token")String token, @Part("placeIdx") RequestBody placeIdx, @Part("content") RequestBody content, @Part("star") RequestBody star, @Part MultipartBody.Part[] imgs);

    @Headers("Content-Type: application/json")
    @GET("/place")
    Call<ResponsePlaceList> getPlaceList(@Header("token") String token);

    @Headers("Content-Type: application/json")
    @GET("/place/like")
    Call<ResponsePlaceLike> getPlaceLike(@Header("token") String token);
}
