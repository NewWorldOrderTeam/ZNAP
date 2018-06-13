package ua.lviv.iot.lmr_cnap.ServerUtilities;

import ua.lviv.iot.lmr_cnap.Pojo.DateChooserAPI;
import ua.lviv.iot.lmr_cnap.Pojo.QueueStateAPI;
import ua.lviv.iot.lmr_cnap.Pojo.Rate;
import ua.lviv.iot.lmr_cnap.Pojo.RecordToZnapAPI;
import ua.lviv.iot.lmr_cnap.Pojo.ServiceChooserAPI;
import ua.lviv.iot.lmr_cnap.Pojo.SuccessRegistrationAPI;
import ua.lviv.iot.lmr_cnap.Pojo.TypeOfServiceAPI;
import ua.lviv.iot.lmr_cnap.Pojo.User;
import ua.lviv.iot.lmr_cnap.Pojo.ZnapName;

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
    Call<User> signUp(@Field("first_name") String first_name, @Field("last_name") String last_name, @Field("middle_name") String middle_name, @Field("phone") String telephone_number, @Field("email") String email, @Field("password") String password, @Field("imei") String imei);

    @FormUrlEncoded
    @POST("/api/v1.0/login/")
    Call<User> signOn(@Field("email") String email, @Field("password") String password,@Field("imei") String imei);

    @FormUrlEncoded
    @POST("/api/v1.0/addrate/")
    Call<User> addrate(@Field("user_id") int user_id, @Field("znap_id") int znap_id, @Field("description") String description, @Field("quality") int quality);


    @FormUrlEncoded
    @POST("/api/v1.0/reset_password/")
    Call<User>passwordRecovery(@Field("email") String email);

    @FormUrlEncoded
    @POST("/api/v1.0/cnap/register/")
    Call<SuccessRegistrationAPI> regToQueue(@Field("user_id") int user_id, @Field("cnap_id") int znap_id,@Field("service_id") int service_id,@Field("day") String day, @Field("hour") String hour);

    @FormUrlEncoded
    @POST("/api/v1.0/")
    Call<User> changePhone(@Field("old_phone_number") String OldPhoneNumber, @Field("new_phone_number") String NewPhoneNumber, @Field("user_id") int pushed_user_id);

    @FormUrlEncoded
    @POST("/api/v1.0/")
    Call<User> changeSurname(@Field("old_surname") String OldSurname, @Field("new_surname") String NewSurname, @Field("user_id") int pushed_user_id);

    @GET("/api/v1.0/user/{user}/")
    Call<User> getInfo(@Path("user") int userid);

    @GET("/api/v1.0/user/{user}/rate")
    Call<List<Rate>> getRateForUser(@Path("user") int userid);

    @GET("/api/v1.0/znap")
    Call<List<ZnapName>> getZnapNames();

    @GET("/api/v1.0/queue/")
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


