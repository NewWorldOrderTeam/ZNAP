package com.znap.lmr.lmr_znap.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.znap.lmr.lmr_znap.Security.AESEncryption;
import com.znap.lmr.lmr_znap.ClientUtilities.NonSystemMessages;
import com.znap.lmr.lmr_znap.R;
import com.znap.lmr.lmr_znap.ServerUtilities.Services;
import com.znap.lmr.lmr_znap.ClientUtilities.SystemMessages;
import com.znap.lmr.lmr_znap.Pojo.User;
import com.znap.lmr.lmr_znap.ClientUtilities.Validations;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {
    EditText etEmail;
    EditText etPassword;
    Button bSignOn;
    TextView tSignUpLink;
    TextView tPasswordRecoveryLink;
    String email;
    String password;
    int userid;
    ProgressDialog progDailog;
    String imei;
    String error;
    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 999;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        checkInternetConnection();
        setContentView(R.layout.activity_sign_in);
        findViewsById();
        getPermission();
        System.out.println(imei);
        hideKeyboardOnTap();
        bSignOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInternetConnection()) {

                } else {
                    email = etEmail.getText().toString();
                    password = etPassword.getText().toString();
                    imei = getImeiNumber();
                    try {
                        email = AESEncryption.encrypt_string(email);
                        password = AESEncryption.encrypt_string(password);
                        imei = AESEncryption.encrypt_string(imei);
                        checkInternetConnection();
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
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (TextUtils.isEmpty(email)) {
                        etEmail.setError(NonSystemMessages.FIELD_MUST_BE_NOT_EMPTY);
                        return;
                    }
                    if (TextUtils.isEmpty(password)) {
                        etPassword.setError(NonSystemMessages.FIELD_MUST_BE_NOT_EMPTY);
                        return;
                    }
                    checkInternetConnection();
                    requestPatternValidation();
                }

            }
        });
        tSignUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(SignInActivity.this, SignUpActivity.class);
                SignInActivity.this.startActivity(signUpIntent);
            }
        });
        tPasswordRecoveryLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent passwordRecoveryIntent = new Intent(SignInActivity.this, PasswordRecoveryActivity.class);
                SignInActivity.this.startActivity(passwordRecoveryIntent);
            }
        });
        findViewById(R.id.iot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://iot.lviv.ua/")));

            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (progDailog != null && progDailog.isShowing()) {
            progDailog.cancel();
        }
    }


    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(SignInActivity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public  void getPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                        PERMISSIONS_REQUEST_READ_PHONE_STATE);
            } else {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                        PERMISSIONS_REQUEST_READ_PHONE_STATE);
                System.out.println(imei);
                imei = getImeiNumber();
            }
        }
    }

    public void hideKeyboardOnTap() {
        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
    }

    public void requestPatternValidation() {
        Request request = new Request();
        request.execute();
        Pattern pattern = Pattern.compile("message=.*,");
        try {
            Matcher matcher = pattern.matcher(request.get());
            while (matcher.find()) {
                int start = matcher.start() + 8;
                int end = matcher.end() - 1;
                String match = request.get().substring(start, end);
                if (match.equals(SystemMessages.BAD_REQUEST)) {
                    match = error;
                    Toast.makeText(getApplicationContext(), match, Toast.LENGTH_LONG).show();
                }
                if (match.equals(SystemMessages.OK)) {
                    checkInternetConnection();
                    Intent mainIntent = new Intent(SignInActivity.this, MainActivity.class);
                    mainIntent.putExtra(SystemMessages.USER_ID, userid);
                    SignInActivity.this.startActivity(mainIntent);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    public void findViewsById() {
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bSignOn = (Button) findViewById(R.id.bSignIn);
        tSignUpLink = (TextView) findViewById(R.id.tSignUpLink);
        tPasswordRecoveryLink = (TextView) findViewById(R.id.tPasswordRecoveryLink);
    }

    public AlertDialog.Builder buildDialog(Context c) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle(SystemMessages.NO_INERNET_CONNECTION);
        builder.setMessage(NonSystemMessages.ENABLE_INTERNET_REQUEST);
        builder.setPositiveButton(NonSystemMessages.OKAY, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        return builder;
    }

    public boolean checkInternetConnection() {
        if (!Validations.isConnected(SignInActivity.this)) {
            buildDialog(SignInActivity.this).show();
            return true;
        } else {
            setContentView(R.layout.activity_sign_in);
            return false;
        }
    }


    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(SignInActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should show an explanation
            if (ActivityCompat.shouldShowRequestPermissionRationale(SignInActivity.this, permission)) {

                ActivityCompat.requestPermissions(SignInActivity.this, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(SignInActivity.this, new String[]{permission}, requestCode);
            }
        } else {
            imei = getImeiNumber();
            Toast.makeText(this, permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    imei = getImeiNumber();
                } else {

                    Toast.makeText(SignInActivity.this, "You have Denied the Permission", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }


    public String getImeiNumber() {
        final TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
        }
        return telephonyManager.getDeviceId();

    }


    class Request extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progDailog = new ProgressDialog(SignInActivity.this);
            progDailog.setMessage("Loading...");
            progDailog.setIndeterminate(false);
            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDailog.setCancelable(true);
            progDailog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            Services services = new Services();
            Response response = services.SignIn(email, password,imei);
            if(response.isSuccessful()) {
                User user = (User) response.body();
                if (user == null) {
                    return response.toString();
                } else {
                    userid = user.getId();
                }
            } else {
                try {
                        error = response.errorBody().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return response.toString();
        }


        @Override
        protected void onPostExecute(String result) {
            if(progDailog.isShowing()){
                progDailog.dismiss();
            }

        }

    }



}