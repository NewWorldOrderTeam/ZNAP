package ua.lviv.iot.lmr_cnap.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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
import ua.lviv.iot.lmr_cnap.ClientUtilities.NonSystemMessages;
import ua.lviv.iot.lmr_cnap.ClientUtilities.SystemMessages;
import ua.lviv.iot.lmr_cnap.R;
import ua.lviv.iot.lmr_cnap.Security.AESEncryption;
import ua.lviv.iot.lmr_cnap.ServerUtilities.Services;

/**
 * Created by Andy Blyzniuk on 22.05.2018.
 */

public class SurnameChangeActivity extends AppCompatActivity {
    String OldSurname;
    String NewSurname;
    EditText etOldSurname;
    EditText etNewSurname;
    Button bSend;
    int pushed_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surname_change);
        getSupportActionBar().setTitle(SystemMessages.SURNAME);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewsById();
        hideKeyboardOnTap();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            assert bundle != null;
            int user_id = bundle.getInt(SystemMessages.USER_ID);
            pushed_user_id = user_id;
        }

        bSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OldSurname = etOldSurname.getText().toString();
                NewSurname = etNewSurname.getText().toString();
                setErrorsForFields();
                try {
                    OldSurname = AESEncryption.encrypt_string(OldSurname);
                    NewSurname = AESEncryption.encrypt_string(NewSurname);
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
                requestPatternValidation();
            }

        });
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(SignInActivity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void hideKeyboardOnTap() {
        etOldSurname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        etNewSurname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

    }


    public void setErrorsForFields() {
        if (TextUtils.isEmpty(OldSurname)) {
            etOldSurname.setError(NonSystemMessages.FIELD_MUST_BE_NOT_EMPTY);
        }
        if (TextUtils.isEmpty(NewSurname)){
            etNewSurname.setError(NonSystemMessages.FIELD_MUST_BE_NOT_EMPTY);
        }
    }

    public void requestPatternValidation() {
        SurnameChangeActivity.Request request = new SurnameChangeActivity.Request();
        request.execute();
        Pattern pattern = Pattern.compile("message=.*,");
        Intent recoveryIntent = new Intent(SurnameChangeActivity.this, SignInActivity.class);
        SurnameChangeActivity.this.startActivity(recoveryIntent);
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

    public void findViewsById() {
        etOldSurname = (EditText) findViewById(R.id.etOldSurname);
        etNewSurname = (EditText) findViewById(R.id.etNewSurname);
        bSend = (Button) findViewById(R.id.bSend);

    }

    class Request extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            Services services = new Services();
            Response response = services.SurnameChange(pushed_user_id, OldSurname, NewSurname);
            return response.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            return;
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


    }



