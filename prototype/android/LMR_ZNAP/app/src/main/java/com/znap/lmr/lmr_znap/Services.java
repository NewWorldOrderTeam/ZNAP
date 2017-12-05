package com.znap.lmr.lmr_znap;

/**
 * Created by Andy Blyzniuk on 01.11.2017.
 */

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Services {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://znap.pythonanywhere.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    Request service = retrofit.create(Request.class);

    public Response SignIn(String email, String password) {
        Call<User> call = service.signOn(email, password);
        try {
            Response response = call.execute();
            User user = (User) response.body();
            Integer userId = user.getId();
            System.out.println(userId);
            // call.execute();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Response SignUp(String firstName, String lastName, String middleName, String phone, String email, String password) {
        Call<User> call = service.signUp(firstName, lastName, middleName, phone, email, password);
        try {
            Response response = call.execute();
            // call.execute();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Response Rate(int user_id, int znap_id, String description, int quality) {
        Call<User> call = service.addrate(user_id,znap_id,description,quality);
        try {
            Response response = call.execute();
            // call.execute();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return null;
    }
}