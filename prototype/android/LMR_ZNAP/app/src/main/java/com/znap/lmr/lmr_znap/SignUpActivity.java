package com.znap.lmr.lmr_znap;

/**
 * Created by Andy Blyzniuk on 01.11.2017.
 */

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    EditText etEmail;
    EditText etPassword;
    EditText etFirstName;
    EditText etLastName;
    EditText etMiddleName;
    EditText etTelephoneNumber;
    Button bSignUp;
    TextView tSignOnLink;
    String email;
    String password;
    String firstName;
    String lastName;
    String middleName;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etMiddleName = (EditText)findViewById(R.id.etMiddleName);
        etTelephoneNumber =(EditText) findViewById(R.id.etTelephoneNumber);
        bSignUp = (Button) findViewById(R.id.bSignUp);
        tSignOnLink = (TextView) findViewById(R.id.tSignUpLink);


        bSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                if(etPassword.getText().toString().length()<8 &&!isValidPassword(etPassword.getText().toString())||
                        etFirstName.getText().toString().length()<3 &&!isValidFirstName(etFirstName.getText().toString())||
                        etLastName.getText().toString().length()<3 &&!isValidLastName(etLastName.getText().toString())||
                        etMiddleName.getText().toString().length()<3 &&!isValidMiddleName(etMiddleName.getText().toString())){
                    System.out.println("Not Valid");
                    Toast.makeText(getApplicationContext(), "Some data is not entered correctly", Toast.LENGTH_LONG).show();
                }else {
                    System.out.println("Valid");
                    middleName = etMiddleName.getText().toString();
                    lastName = etLastName.getText().toString();
                    phone = etTelephoneNumber.getText().toString();
                    Request request = new Request();
                    request.execute();
                    Pattern pattern = Pattern.compile("message=.*,");
                    Intent signInIntent = new Intent(SignUpActivity.this, SignInActivity.class);
                    SignUpActivity.this.startActivity(signInIntent);
                    try {
                        Matcher matcher = pattern.matcher(request.get());
                        while (matcher.find()){
                            int start = matcher.start()+8;
                            int end = matcher.end()-1;
                            String match = request.get().substring(start, end);
                            Toast.makeText(getApplicationContext(), match, Toast.LENGTH_LONG).show();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }

                firstName = etFirstName.getText().toString();
            }
        });
        tSignOnLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
            return ;
        }
    }

    public static boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "[a-zA-Z0-9]{8,24}";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }
    public static boolean isValidFirstName(final String first_name) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "[А-Яа-я]";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(first_name);
        return matcher.matches();
    }
    public static boolean isValidLastName(final String last_name) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "[А-Яа-я]";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(last_name);
        return matcher.matches();
    }
    public static boolean isValidMiddleName(final String middle_name) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "[А-Яа-я]";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(middle_name);
        return matcher.matches();
    }

}
