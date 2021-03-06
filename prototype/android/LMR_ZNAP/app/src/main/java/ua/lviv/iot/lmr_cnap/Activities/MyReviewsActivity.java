package ua.lviv.iot.lmr_cnap.Activities;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import ua.lviv.iot.lmr_cnap.Security.AESEncryption;
import ua.lviv.iot.lmr_cnap.R;
import ua.lviv.iot.lmr_cnap.Pojo.Rate;
import ua.lviv.iot.lmr_cnap.ServerUtilities.Request;
import ua.lviv.iot.lmr_cnap.ClientUtilities.SystemMessages;
import ua.lviv.iot.lmr_cnap.ServerUtilities.ZnapUtility;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyReviewsActivity extends AppCompatActivity {
    private static Request request;
    List<Rate> rates;
    private int userid;
    ListView list;
    List<String> ratesOfUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(SystemMessages.MY_REVIEWS);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_my_reviews);
        list = (ListView) findViewById(R.id.ReviewsList);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            assert bundle != null;
            userid = bundle.getInt(SystemMessages.USER_ID);
            rates = new ArrayList<>();
            ratesOfUsers = new ArrayList<>();
            request = ZnapUtility.generateRetroRequest();
            MyReviewsActivity.getApi().getRateForUser(userid).enqueue(new Callback<List<Rate>>() {
                @Override
                public void onResponse(Call<List<Rate>> call, Response<List<Rate>> response) {
                    rates.addAll(response.body());
                    for (int i = rates.size() - 1; i > -1; i--) {
                        try {
                            ratesOfUsers.add(AESEncryption.decrypt_string(rates.get(i).getDescription()));
                        } catch (InvalidKeyException e) {
                            e.printStackTrace();
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        } catch (NoSuchPaddingException e) {
                            e.printStackTrace();
                        } catch (InvalidAlgorithmParameterException e) {
                            e.printStackTrace();
                        } catch (IllegalBlockSizeException e) {
                            e.printStackTrace();
                        } catch (BadPaddingException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    list.setAdapter(new ArrayAdapter<>(MyReviewsActivity.this, android.R.layout.simple_list_item_1, ratesOfUsers));
                }

                @Override
                public void onFailure(Call<List<Rate>> call, Throwable t) {
                }
            });
        } else {
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public static Request getApi() {
        return request;
    }
}
