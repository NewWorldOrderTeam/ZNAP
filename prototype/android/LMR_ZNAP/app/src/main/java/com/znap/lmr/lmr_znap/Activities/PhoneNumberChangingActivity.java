package com.znap.lmr.lmr_znap.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.znap.lmr.lmr_znap.ClientUtilities.NonSystemMessages;
import com.znap.lmr.lmr_znap.ClientUtilities.SystemMessages;
import com.znap.lmr.lmr_znap.ClientUtilities.Validations;
import com.znap.lmr.lmr_znap.R;
import com.znap.lmr.lmr_znap.Security.AESEncryption;
import com.znap.lmr.lmr_znap.ServerUtilities.Services;

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


public class PhoneNumberChangingActivity extends AppCompatActivity {
    String OldPhoneNumber;
    String NewPhoneNumber;
    String ConfirmNewPhoneNumber;
    EditText etOldPhoneNumber;
    EditText etNewPhoneNumber, etConfirmNewPhoneNumber;
    Button bSend;
    int pushed_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number_changing);
        getSupportActionBar().setTitle(SystemMessages.PHONE_TITLE);
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
                OldPhoneNumber = etOldPhoneNumber.getText().toString();
                NewPhoneNumber = etNewPhoneNumber.getText().toString();
                ConfirmNewPhoneNumber = etConfirmNewPhoneNumber.getText().toString();
                setErrorsForFields();

                if (!Validations.isValidPhoneNumber(NewPhoneNumber) || !ConfirmNewPhoneNumber.equals(NewPhoneNumber)) {
                    if (!Validations.isValidPhoneNumber(NewPhoneNumber)) {
                        etNewPhoneNumber.setError("Номер телефону повинен містити 12 символів!");
                    }
                    if (!ConfirmNewPhoneNumber.equals(NewPhoneNumber)) {
                        etConfirmNewPhoneNumber.setError("Перевірте чи номери телефонів співпадають");

                    }
                    if (!Validations.isValidPhoneNumber(OldPhoneNumber)) {
                        etOldPhoneNumber.setError("Перевірте чи правильно введено телефон");
                    }

                } else {
                    try {
                        OldPhoneNumber = AESEncryption.encrypt_string(OldPhoneNumber);
                        NewPhoneNumber = AESEncryption.encrypt_string(NewPhoneNumber);
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

            }
        });
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(SignInActivity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void hideKeyboardOnTap() {
        etOldPhoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        etNewPhoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        etConfirmNewPhoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
    }


    public void setErrorsForFields() {
        if (TextUtils.isEmpty(OldPhoneNumber)) {
            etOldPhoneNumber.setError(NonSystemMessages.FIELD_MUST_BE_NOT_EMPTY);
            etNewPhoneNumber.setError(NonSystemMessages.FIELD_MUST_BE_NOT_EMPTY);
            etConfirmNewPhoneNumber.setError(NonSystemMessages.FIELD_MUST_BE_NOT_EMPTY);
        }
    }

    public void requestPatternValidation() {
        PhoneNumberChangingActivity.Request request = new PhoneNumberChangingActivity.Request();
        request.execute();
        Pattern pattern = Pattern.compile("message=.*,");
        Intent recoveryIntent = new Intent(PhoneNumberChangingActivity.this, SignInActivity.class);
        PhoneNumberChangingActivity.this.startActivity(recoveryIntent);
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
        etOldPhoneNumber = (EditText) findViewById(R.id.etOldPhoneNumber);
        etNewPhoneNumber = (EditText) findViewById(R.id.etNewPhoneNumber);
        etConfirmNewPhoneNumber = (EditText) findViewById(R.id.etConfirmNewPhoneNumber);
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
            Response response = services.NumberChanging(pushed_user_id, OldPhoneNumber, NewPhoneNumber);
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
