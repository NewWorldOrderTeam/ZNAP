package com.znap.lmr.lmr_znap;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

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
    Call<User> addrate(@Field("user_id") int user_id, @Field("znap_id") int znap_id, @Field("description") String description, @Field("quality") int quality);

    @GET("/api/v1.0/user/{user}/")
    Call<User> getInfo(@Path("user") int userid);

    @GET("/api/v1.0/user/{user}/rate")
    Call<List<Rate>> getRateForUser(@Path("user") int userid);

    @FormUrlEncoded
    @POST("/api/v1.0/registerToQueue/")
    Call<User> regToQueue(@Field("user_id") int user_id, @Field("znap_id") int znap_id, @Field("date") String date, @Field("time") String time,@Field("service") int service);


}


