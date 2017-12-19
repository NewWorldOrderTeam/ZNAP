package com.znap.lmr.lmr_znap;

/**
 * Created by Andy Blyzniuk on 01.11.2017.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);
        findViewsById();
        hideKeyboardOnTap();

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
                    emailToShow = email;
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

    class Request extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            Services services = new Services();
            Response response = services.SignUp(firstName, lastName, middleName, phone, email, password);
            System.out.println(response.toString());
            return response.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            return;
        }
    }

}