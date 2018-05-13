package ua.lviv.iot.lmr_cnap.ServerUtilities;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Zava on 12.12.2017.
 */

public class ZnapUtility {
    private static Retrofit retrofit;
    private static Request request;

    public static Request generateRetroRequest() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://znap.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        request = retrofit.create(Request.class);
        return request;
    }

    public static Request QLogicRequest() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://znap.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        request = retrofit.create(Request.class);
        return request;
    }

}

