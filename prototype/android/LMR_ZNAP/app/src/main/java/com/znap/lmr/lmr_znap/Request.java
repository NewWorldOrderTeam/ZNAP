package com.znap.lmr.lmr_znap;

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

    @GET("/api/v1.0/znap")
    Call<List<ZnapName>> getZnapNames();

    @GET("/VideoAd/GetOrganisationState?orgKey=28c94bad-f024-4289-a986-f9d79c9d8102")
    Call<List<QueueStateAPI>> getQueue();

    @GET("/QueueService.svc/json_pre_reg/getServiceCenterList?organisationGuid=%7B28c94bad-f024-4289-a986-f9d79c9d8102%7D")
    Call<List<RecordToZnapAPI>> getRecordsToZnap();

    @GET("/QueueService.svc/json_wellcome_point/GetGroupsByCenterId")
    Call<List<TypeOfServiceAPI>> getTypeOfService(@Query("organisationGuid") String organisationID, @Query("serviceCenterId") int znap_id);

    @GET("/QueueService.svc/json_wellcome_point/getServicesByCenterId")
    Call<List<ServiceChooserAPI>> getServices(@Query("organisationGuid") String organisationID, @Query("serviceCenterId")int znap_id, @Query("groupId") int group_id);

    @GET("/QueueService.svc/json_pre_reg/GetDayList")
    Call<List<DateChooserAPI>> getDates(@Query("organisationGuid") String organisationID, @Query("serviceCenterId") int znap_id, @Query("serviceId") int service_id);

    @GET("/QueueService.svc/json_pre_reg/GetTimeList")
    Call<List<HoursChooserAPI>> getHours(@Query("organisationGuid") String organisationID, @Query("serviceCenterId") int znap_id,@Query("serviceId") int service_id, @Query("date") String date);

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


