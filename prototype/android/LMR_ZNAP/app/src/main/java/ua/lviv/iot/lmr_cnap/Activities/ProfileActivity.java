package ua.lviv.iot.lmr_cnap.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ua.lviv.iot.lmr_cnap.Security.AESEncryption;
import ua.lviv.iot.lmr_cnap.R;
import ua.lviv.iot.lmr_cnap.ServerUtilities.Request;
import ua.lviv.iot.lmr_cnap.ClientUtilities.SystemMessages;
import ua.lviv.iot.lmr_cnap.Pojo.User;
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


public class ProfileActivity extends AppCompatActivity {
    TextView firstNameText;
    TextView lastNameText;
    TextView emailText, phoneText;
    private static Request request;
    private int user_id;
    List<User> users;
    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 999;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle(SystemMessages.PROFILE_TITLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        firstNameText = (TextView) findViewById(R.id.user_profile_name);
        lastNameText = (TextView) findViewById(R.id.user_profile_surname);
        emailText = (TextView) findViewById(R.id.email);
        phoneText = (TextView) findViewById(R.id.phone);
        Bundle bundle = getIntent().getExtras();


        users = new ArrayList<>();

        if (bundle != null) {
            assert bundle != null;
            user_id = bundle.getInt(SystemMessages.USER_ID);
            findViewById(R.id.myreviews).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent openMyReviewsActivity = new Intent(ProfileActivity.this, MyReviewsActivity.class);
                    openMyReviewsActivity.putExtra(SystemMessages.USER_ID, user_id);
                    startActivity(openMyReviewsActivity);
                }
            });
            findViewById(R.id.profilesettings).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent openSettingsActivity = new Intent(ProfileActivity.this, SettingsActivity.class);
                    openSettingsActivity.putExtra(SystemMessages.USER_ID, user_id);
                    startActivity(openSettingsActivity);
                }
            });
            findViewById(R.id.history).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent openRegistrationHistoryActivity = new Intent(ProfileActivity.this, RegistrationHistoryActivity.class);
                    openRegistrationHistoryActivity.putExtra(SystemMessages.USER_ID, user_id);
                    startActivity(openRegistrationHistoryActivity);
                }
            });

            request = ZnapUtility.generateRetroRequest();
            ProfileActivity.getApi().getInfo(user_id).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User user = (User) response.body();
                    String firstName = user.getFirstName();
                    String lastName = user.getLastName();
                    String phone = user.getPhone();
                    String email = user.getEmail();
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
                    firstNameText.setText(String.valueOf(firstName));
                    lastNameText.setText(String.valueOf(lastName));
                    emailText.setText(String.valueOf("Email : " + email));
                    phoneText.setText(String.valueOf("Phone : " + phone));
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
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
