package com.znap.lmr.lmr_znap.Activities;

/**
 * Created by Andy Blyzniuk on 01.11.2017.
 */

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lamudi.phonefield.PhoneInputLayout;
import com.znap.lmr.lmr_znap.ClientUtilities.SystemMessages;
import com.znap.lmr.lmr_znap.Security.AESEncryption;
import com.znap.lmr.lmr_znap.ClientUtilities.NonSystemMessages;
import com.znap.lmr.lmr_znap.R;
import com.znap.lmr.lmr_znap.ServerUtilities.Services;
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

public class SignUpActivity extends AppCompatActivity {
    Context context = this;
    EditText etEmail;
    EditText etPassword, etConfirmPassword;
    EditText etFirstName;
    EditText etLastName;
    EditText etMiddleName;
    String phoneNumber;
    Button bSignUp;
    TextView tSignOnLink;
    String email;
    String password;
    String firstName;
    String lastName;
    String middleName;
    String confirmPassword;
    String emailToShow;
    PhoneInputLayout phoneInputLayout;
    public String imei;
    private String error;
    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 999;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);
        findViewsById();
        phoneInputLayout.setDefaultCountry("UA");
        hideKeyboardOnTap();
        getPermission();
        imei = getImeiNumber();
        System.out.println(imei);

        bSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valid = true;
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                firstName = etFirstName.getText().toString();
                middleName = etMiddleName.getText().toString();
                lastName = etLastName.getText().toString();
                password = etPassword.getText().toString();
                confirmPassword = etConfirmPassword.getText().toString();
                phoneNumber = phoneInputLayout.getPhoneNumber();

                setErrorsForFields();
                if (password.length() < 8 || !Validations.isValidPassword(password)){
                    etPassword.setError("Паролі повинні містити більше 8 символів!");
                }
                if (!confirmPassword.equals(password)){
                    etConfirmPassword.setError("Перевірте чи паролі співпадають");
                }
                if (firstName.length() < 1 || !Validations.isValidFirstName(firstName)){
                    etFirstName.setError("Ім'я повинно містити більше 2 символів");

                }
                if (middleName.length() < 3 || !Validations.isValidMiddleName(middleName)){
                    etMiddleName.setError("Більше 3 символів");
                }
                if  (lastName.length() < 3 || !Validations.isValidLastName(lastName) ){
                    etLastName.setError("Більше 3 символів");
                }
                if(!phoneInputLayout.isValid()){
                    phoneInputLayout.setError("Перевірте чи правильно введено телефон");
                }

                if (email.length()<6 || !Validations.isValidEmail(email)){
                    etEmail.setError("Неправильний емейл");
                }

                else {
                    try {
                        email = AESEncryption.encrypt_string(email);
                        firstName = AESEncryption.encrypt_string(firstName);
                        middleName = AESEncryption.encrypt_string(middleName);
                        lastName = AESEncryption.encrypt_string(lastName);
                        phoneNumber = AESEncryption.encrypt_string(phoneNumber);
                        password = AESEncryption.encrypt_string(password);
                        imei = AESEncryption.encrypt_string(imei);
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
                    try {
                        emailToShow = AESEncryption.decrypt_string(email);
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
                    new AlertDialog.Builder(context)
                            .setMessage(NonSystemMessages.activateAccount + " " + emailToShow)
                            .setCancelable(false)
                            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    requestPatternValidation();
                                    finish();
                                }
                            })
                            .show();

                }
            }
        });

        tSignOnLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void getPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                        PERMISSIONS_REQUEST_READ_PHONE_STATE);
            } else {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                        PERMISSIONS_REQUEST_READ_PHONE_STATE);
                imei = getImeiNumber();
            }
        }
    }


    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(SignUpActivity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
        etFirstName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        etMiddleName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        etLastName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
    }

    public void setErrorsForFields() {
        if (TextUtils.isEmpty(email)) {
            etEmail.setError(NonSystemMessages.FIELD_MUST_BE_NOT_EMPTY);
        }
        if (TextUtils.isEmpty(password)) {
            etPassword.setError(NonSystemMessages.FIELD_MUST_BE_NOT_EMPTY);
        }
        if (TextUtils.isEmpty(firstName)) {
            etFirstName.setError(NonSystemMessages.FIELD_MUST_BE_NOT_EMPTY);
        }
        if (TextUtils.isEmpty(middleName)) {
            etMiddleName.setError(NonSystemMessages.FIELD_MUST_BE_NOT_EMPTY);
        }
        if (TextUtils.isEmpty(lastName)) {
            etLastName.setError(NonSystemMessages.FIELD_MUST_BE_NOT_EMPTY);
        }
    }

    public void requestPatternValidation() {
        Request request = new Request();
        request.execute();
        Pattern pattern = Pattern.compile("message=.*,");
        Intent signInIntent = new Intent(SignUpActivity.this, SignInActivity.class);
        SignUpActivity.this.startActivity(signInIntent);
        try {
            Matcher matcher = pattern.matcher(request.get());
            while (matcher.find()) {
                int start = matcher.start() + 8;
                int end = matcher.end() - 1;

                String match = request.get().substring(start, end);
                if (match.equals(SystemMessages.INTERNAL_SERVER_ERROR) || match.equals(SystemMessages.BAD_REQUEST)) {
                    match = error;
                    Toast.makeText(getApplicationContext(), match, Toast.LENGTH_LONG).show();
                }
                if (match.equals(SystemMessages.OK)) {
                    Intent mainIntent = new Intent(SignUpActivity.this, SignInActivity.class);
                    SignUpActivity.this.startActivity(mainIntent);
                }
                Toast.makeText(getApplicationContext(), match, Toast.LENGTH_LONG).show();
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
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etMiddleName = (EditText) findViewById(R.id.etMiddleName);
        bSignUp = (Button) findViewById(R.id.bSignUp);
        tSignOnLink = (TextView) findViewById(R.id.tSignUpLink);
        phoneInputLayout = (PhoneInputLayout) findViewById(R.id.phone_input_layout);
    }


    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(SignUpActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(SignUpActivity.this, permission)) {
                ActivityCompat.requestPermissions(SignUpActivity.this, new String[]{permission}, requestCode);
            } else {
                ActivityCompat.requestPermissions(SignUpActivity.this, new String[]{permission}, requestCode);
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
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    imei = getImeiNumber();
                } else {
                    Toast.makeText(SignUpActivity.this, "You have Denied the Permission", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }


    public String getImeiNumber() {
        final TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

        /*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //getDeviceId() is Deprecated so for android O we can use getImei() method
            return telephonyManager.getImei();
        }
        else {
            return telephonyManager.getDeviceId();
        }
        */

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return "test";
        }

        return telephonyManager.getDeviceId();

    }


    class Request extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            Services services = new Services();
            Response response = services.SignUp(firstName, lastName, middleName, phoneNumber, email, password, imei);
            if (response.isSuccessful()) {
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
            super.onPostExecute(result);
            return;
        }
    }

}