package ua.lviv.iot.lmr_cnap.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import ua.lviv.iot.lmr_cnap.Pojo.SuccessRegistrationAPI;
import ua.lviv.iot.lmr_cnap.Pojo.User;
import ua.lviv.iot.lmr_cnap.R;
import ua.lviv.iot.lmr_cnap.Security.AESEncryption;
import ua.lviv.iot.lmr_cnap.ServerUtilities.Request;
import ua.lviv.iot.lmr_cnap.ServerUtilities.ZnapUtility;
import ua.lviv.iot.lmr_cnap.ClientUtilities.SystemMessages;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FinishActivity extends AppCompatActivity {
    Button bFinish;
    int user_id, cnap_id, service_id;
    String hour, day;
    String firstName, lastName, phone, email;
    final Context context = this;
    private static Request request;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_registration);
        getSupportActionBar().setTitle(SystemMessages.REG_TO_QUEUE_TITLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        user_id = bundle.getInt(SystemMessages.USER_ID);
        cnap_id = bundle.getInt(SystemMessages.ZNAP_ID);
        service_id = bundle.getInt(SystemMessages.SERVICE_ID);
        day = bundle.getString("day");
        hour = bundle.getString("hour");
        request = ZnapUtility.generateRetroRequest();
        FinishActivity.getApi().getInfo(user_id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = (User) response.body();
                firstName = user.getFirstName();
                lastName = user.getLastName();
                phone = user.getPhone();
                email = user.getEmail();

                try {
                    firstName = AESEncryption.decrypt_string(firstName);
                    lastName = AESEncryption.decrypt_string(lastName);
                    phone = AESEncryption.decrypt_string(phone);
                    email = AESEncryption.decrypt_string(email);

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

            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }

        });


        bFinish = (Button) findViewById(R.id.finish);
        bFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                alertDialogBuilder.setTitle("Реєстрація на послугу");

                alertDialogBuilder
                        .setMessage("Ви дійсно хочете зареєструватись у чергу ?")
                        .setCancelable(false)
                        .setPositiveButton("Так", new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, int id) {
                                Intent myIntent = new Intent(FinishActivity.this,
                                        MainActivity.class);
                                myIntent.putExtra(SystemMessages.USER_ID, user_id);
                                myIntent.putExtra("znap_id", cnap_id);
                                myIntent.putExtra("service_id", service_id);
                                request = ZnapUtility.QLogicRequest();
                                FinishActivity.getApi().regToQueue(user_id, cnap_id, service_id, day, hour).enqueue(new Callback<SuccessRegistrationAPI>() {
                                    @Override
                                    public void onResponse(Call<SuccessRegistrationAPI> call, Response<SuccessRegistrationAPI> response) {

                                    }

                                    @Override
                                    public void onFailure(Call<SuccessRegistrationAPI> call, Throwable t) {

                                    }
                                });
                                startActivity(myIntent);
                            }
                        })
                        .setNegativeButton("Ні", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    public static Request getApi() {
        return request;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
