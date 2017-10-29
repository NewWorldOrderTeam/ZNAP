package com.project.andyblyzniuk.lmr_app;

/**
 * Created by Andy Blyzniuk on 29.10.2017.
 */

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Services {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://znap.pythonanywhere.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    Request service = retrofit.create(Request.class);
    public Response SignOn(String email, String password){
        Call<User> call = service.signOn(email, password);
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
    public Response SignUp(String firstName, String lastName, String email, String password){
        Call<User> call = service.signUp(firstName, lastName, email, password);
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
