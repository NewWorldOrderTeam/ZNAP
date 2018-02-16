package com.znap.lmr.lmr_znap;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    String email;
    String password;
    int userid;
    ProgressDialog progDailog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        checkInternetConnection();
        setContentView(R.layout.activity_sign_in);
        findViewsById();
        hideKeyboardOnTap();
        bSignOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInternetConnection()) {

                } else{
                    email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                    try {
                        email = AESEncryption.encrypt_string(email);
                        System.out.println(email);
                        password = AESEncryption.encrypt_string(password);
                        System.out.println(password);
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
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        if ( progDailog!=null && progDailog.isShowing() ){
            progDailog.cancel();
        }
    }


    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(SignInActivity.INPUT_METHOD_SERVICE);
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
                    match = NonSystemMessages.FIELD_IS_NOT_ENTERED_CORRECTLY;
                    Toast.makeText(getApplicationContext(), match, Toast.LENGTH_LONG).show();
                }
                if (match.equals(SystemMessages.OK)) {
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
            Response response = services.SignIn(email, password);
            /*System.out.println(response);*/
            User user = (User) response.body();
            if (user == null) {
                System.out.println(response.body());
                return response.toString();
            } else {
                userid = user.getId();
            }
            return response.toString();

        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

        }

    }

}