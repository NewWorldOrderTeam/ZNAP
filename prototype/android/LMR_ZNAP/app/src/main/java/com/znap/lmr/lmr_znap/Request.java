package com.znap.lmr.lmr_znap;

import android.content.ClipData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Andy Blyzniuk on 01.11.2017.
 */

public interface Request {
    @FormUrlEncoded
    @POST("/api/v1.0/register/")
    Call<User> signUp(@Field("first_name") String first_name, @Field("last_name") String last_name, @Field("middle_name") String middle_name, @Field("phone") String telephone_number, @Field("email") String email, @Field("password") String password);
    @FormUrlEncoded
    @POST("/api/v1.0/login/")
    Call<User> signOn(@Field("email") String email, @Field("password") String password);
    @FormUrlEncoded
    @POST("/api/v1.0/addrate/")
    Call<User> addrate(@Field("description") String leaveReview);
    @GET("/api/v1.0/register")
    Response getInfo(@Query("mid") String first_name, String last_name, String middle_name, Callback<Response> callback);
}
