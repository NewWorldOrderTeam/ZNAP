package ua.lviv.iot.lmr_cnap.ServerUtilities;

/**
 * Created by Andy Blyzniuk on 01.11.2017.
 */

import ua.lviv.iot.lmr_cnap.Pojo.User;

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

    Request serviceR = retrofit.create(Request.class);


    public Response SignIn(String email, String password, String imei) {
        Call<User> call = serviceR.signOn(email, password,imei);
        try {
            Response response = call.execute();
            return response;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Response SignUp(String firstName, String lastName, String middleName, String phone, String email, String password, String imei) {
        Call<User> call = serviceR.signUp(firstName, lastName, middleName, phone, email, password,imei);
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
        Call<User> call = serviceR.addrate(user_id, znap_id, description, quality);
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


    public Response PasswordRecovery(String email) {
        Call<User> call = serviceR.passwordRecovery(email);
        try {
            Response response = call.execute();
            return response;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        return null;
    }
    public Response NumberChanging(int user_id, String OldPhoneNumber, String NewPhoneNumber) {
        Call<User> call = serviceR.changePhone(OldPhoneNumber, NewPhoneNumber, user_id);
        try {
            Response response = call.execute();
            return response;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        return null;
    }
}


