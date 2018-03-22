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
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    EditText etPassword;
    EditText etFirstName;
    EditText etLastName;
    EditText etMiddleName;
    EditText etPhoneNumber;
    Button bSignUp;
    TextView tSignOnLink;
    String email;
    String password;
    String firstName;
    String lastName;
    String middleName;
    String phone;
    String emailToShow;
     public String imei;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);
        findViewsById();
        hideKeyboardOnTap();
        imei = getImeiNumber();

        bSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                firstName = etFirstName.getText().toString();
                middleName = etMiddleName.getText().toString();
                lastName = etLastName.getText().toString();
                setErrorsForFields();
                if (etPassword.getText().toString().length() < 8 && !Validations.isValidPassword(etPassword.getText().toString()) ||
                        etFirstName.getText().toString().length() < 3 && !Validations.isValidFirstName(etFirstName.getText().toString()) ||
                        etMiddleName.getText().toString().length() < 3 && !Validations.isValidMiddleName(etMiddleName.getText().toString()) ||
                        etLastName.getText().toString().length() < 3 && !Validations.isValidLastName(etLastName.getText().toString())) {
                    Toast.makeText(getApplicationContext(), NonSystemMessages.FIELD_IS_NOT_ENTERED_CORRECTLY, Toast.LENGTH_LONG).show();
                } else {
                    firstName = etFirstName.getText().toString();
                    middleName = etMiddleName.getText().toString();
                    lastName = etLastName.getText().toString();
                    phone = etPhoneNumber.getText().toString();
                    try {
                        email = AESEncryption.encrypt_string(email);
                        firstName = AESEncryption.encrypt_string(firstName);
                        middleName = AESEncryption.encrypt_string(middleName);
                        lastName = AESEncryption.encrypt_string(lastName);
                        phone = AESEncryption.encrypt_string(phone);
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
                            .setMessage(NonSystemMessages.activateAccount + " " +emailToShow)
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
        etPhoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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

    public void requestPatternValidation(){
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
                Toast.makeText(getApplicationContext(), match, Toast.LENGTH_LONG).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void findViewsById(){
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etMiddleName = (EditText) findViewById(R.id.etMiddleName);
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        bSignUp = (Button) findViewById(R.id.bSignUp);
        tSignOnLink = (TextView) findViewById(R.id.tSignUpLink);
    }


    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(SignUpActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should show an explanation
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

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    imei = getImeiNumber();
                    System.out.println(imei);

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
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
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
            Response response = services.SignUp(firstName, lastName, middleName, phone, email, password,imei);
            System.out.println(response.body());
            return response.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            return;
        }
    }

}