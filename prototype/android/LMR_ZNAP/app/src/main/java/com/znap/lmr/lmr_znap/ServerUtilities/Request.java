package com.znap.lmr.lmr_znap.ServerUtilities;

import com.znap.lmr.lmr_znap.Pojo.DateChooserAPI;
import com.znap.lmr.lmr_znap.Pojo.HoursChooserAPI;
import com.znap.lmr.lmr_znap.Pojo.QueueStateAPI;
import com.znap.lmr.lmr_znap.Pojo.Rate;
import com.znap.lmr.lmr_znap.Pojo.RecordToZnapAPI;
import com.znap.lmr.lmr_znap.Pojo.ServiceChooserAPI;
import com.znap.lmr.lmr_znap.Pojo.SuccessRegistrationAPI;
import com.znap.lmr.lmr_znap.Pojo.TypeOfServiceAPI;
import com.znap.lmr.lmr_znap.Pojo.User;
import com.znap.lmr.lmr_znap.Pojo.ZnapName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface Request {
    @FormUrlEncoded
    @POST("/api/v1.0/register/")
    Call<User> signUp(@Field("first_name") String first_name, @Field("last_name") String last_name, @Field("middle_name") String middle_name, @Field("phone") String telephone_number, @Field("email") String email, @Field("password") String password,@Field("imei") String imei);

    @FormUrlEncoded
    @POST("/api/v1.0/login/")
    Call<User> signOn(@Field("email") String email, @Field("password") String password,@Field("imei") String imei);

    @FormUrlEncoded
    @POST("/api/v1.0/addrate/")
    Call<User> addrate(@Field("user_id") int user_id, @Field("znap_id") int znap_id, @Field("description") String description, @Field("quality") int quality);

    @FormUrlEncoded
    @POST("/api/v1.0/")
    Call<User>signToOfficialPerson(@Field("user_id") int user_id, @Field("address") String address, @Field("your_problem") String yourProblem);

    @FormUrlEncoded
    @POST("/api/v1.0/")
    Call<User>passwordRecovery(@Field("email") String email);

    @FormUrlEncoded
    @POST("/api/v1.0/cnap/register/")
    Call<SuccessRegistrationAPI> regToQueue(@Field("user_id") int user_id,@Field("cnap_id") int znap_id,@Field("service_id") int service_id,@Field("day") String day, @Field("hour") String hour);

    @GET("/api/v1.0/user/{user}/")
    Call<User> getInfo(@Path("user") int userid);

    @GET("/api/v1.0/user/{user}/rate")
    Call<List<Rate>> getRateForUser(@Path("user") int userid);

    @GET("/api/v1.0/znap")
    Call<List<ZnapName>> getZnapNames();

    @GET("/Chart/ChartByNow?orgKey=28c94bad-f024-4289-a986-f9d79c9d8102")
    Call<List<QueueStateAPI>> getQueue();

    @GET("/api/v1.0/cnap/")
    Call<List<RecordToZnapAPI>> getRecordsToZnap();

    @GET("/api/v1.0/cnap/{znap_id}/group/")
    Call<List<TypeOfServiceAPI>> getTypeOfService(@Path("znap_id") int znap_id);

    @GET("/api/v1.0/cnap/{znap_id}/group/{group_id}/service/")
    Call<List<ServiceChooserAPI>> getServices(@Path("znap_id")int znap_id, @Path("group_id") int group_id);

    @GET("/api/v1.0/cnap/{znap_id}/service/{service_id}/time/")
    Call<List<DateChooserAPI>> getDates( @Path("znap_id") int znap_id, @Path("service_id") int service_id);

    @GET( "/QueueService.svc/json_pre_reg/RegCustomerEx")
    Call<SuccessRegistrationAPI> getResult(@Query("organisationGuid") String organisationID,
                                           @Query("serviceCenterId") int znap_id,
                                           @Query("serviceId") int service_id,
                                           @Query("date") String date,
                                           @Query("phone") String phone,
                                           @Query("email") String email,
                                           @Query("name") String name,
                                           @Query("langId") int language);

}


