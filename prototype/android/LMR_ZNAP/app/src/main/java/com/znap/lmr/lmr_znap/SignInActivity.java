package com.znap.lmr.lmr_znap;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
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

public class SignInActivity extends AppCompatActivity {

    EditText etEmail;
    EditText etPassword;
    Button bSignOn;
    TextView tSignUpLink;
    String email;
    String password;
    int userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (!isConnected(SignInActivity.this)) buildDialog(SignInActivity.this).show();
        else {
            setContentView(R.layout.activity_sign_in);
        }
        setContentView(R.layout.activity_sign_in);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bSignOn = (Button) findViewById(R.id.bSignIn);
        tSignUpLink = (TextView) findViewById(R.id.tSignUpLink);


        bSignOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    etEmail.setError(NonSystemMessages.fieldMustBeNotEmpty);
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    etPassword.setError(NonSystemMessages.fieldMustBeNotEmpty);
                    return;
                }
                Request request = new Request();
                request.execute();
                Pattern pattern = Pattern.compile("message=.*,");
                try {
                    Matcher matcher = pattern.matcher(request.get());
                    while (matcher.find()) {
                        int start = matcher.start() + 8;
                        int end = matcher.end() - 1;
                        String match = request.get().substring(start, end);
                        if (match.equals("Bad Request")) {
                            match = "Неправильно введені дані!";
                            Toast.makeText(getApplicationContext(), match, Toast.LENGTH_LONG).show();
                        }
                        if (match.equals("OK")) {
                            Intent mainIntent = new Intent(SignInActivity.this, MainActivity.class);
                            mainIntent.putExtra("userid", userid);
                            SignInActivity.this.startActivity(mainIntent);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
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

    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
            else return false;
        } else
            return false;
    }

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle(SystemMessages.noInternetConnection);
        builder.setMessage(NonSystemMessages.enableInternetRequest);
        builder.setPositiveButton(NonSystemMessages.okay, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        return builder;
    }

    class Request extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            Services services = new Services();
            Response response = services.SignIn(email, password);
            System.out.println(response);
            User user = (User) response.body();
            if(user==null){
                return response.toString();
            }else {
                userid = user.getId();
                System.out.println(userid);
                return response.toString();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            return;
        }


    }

}