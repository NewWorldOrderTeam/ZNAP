package com.znap.lmr.lmr_znap.Activities;

/**
 * Created by Andy Blyzniuk on 04.03.2018.
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
import android.widget.Toast;

import com.znap.lmr.lmr_znap.ClientUtilities.NonSystemMessages;
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


public class RecordToTheOfficialPersonActivity extends AppCompatActivity {
 Context context = this;
    EditText etEmail;
    EditText etFirstName;
    EditText etLastName;
    EditText etMiddleName;
    EditText etAddress;
    EditText etTelephoneNumber;
    EditText etYourProblem;
    Button bOfficialPerson;
    String email;
    String firstName;
    String lastName;
    String middleName;
    String address;
    String telephoneNumber;
    String yourProblem;
    String emailToShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_official_person);
        findViewsById();
        hideKeyboardOnTap();

        bOfficialPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etEmail.getText().toString();
                firstName = etFirstName.getText().toString();
                middleName = etMiddleName.getText().toString();
                lastName = etLastName.getText().toString();
                address = etAddress.getText().toString();
                telephoneNumber = etTelephoneNumber.getText().toString();
                yourProblem = etYourProblem.getText().toString();
                setErrorsForFields();
                if (etFirstName.getText().toString().length() < 3 && !Validations.isValidFirstName(etFirstName.getText().toString()) ||
                        etMiddleName.getText().toString().length() < 3 && !Validations.isValidMiddleName(etMiddleName.getText().toString()) ||
                        etLastName.getText().toString().length() < 3 && !Validations.isValidLastName(etLastName.getText().toString())) {
                    Toast.makeText(getApplicationContext(), NonSystemMessages.FIELD_IS_NOT_ENTERED_CORRECTLY, Toast.LENGTH_LONG).show();
                } else {
                    firstName = etFirstName.getText().toString();
                    middleName = etMiddleName.getText().toString();
                    lastName = etLastName.getText().toString();
                    address = etAddress.getText().toString();
                    telephoneNumber = etTelephoneNumber.getText().toString();
                    yourProblem = etYourProblem.getText().toString();
                    try {
                        email = AESEncryption.encrypt_string(email);
                        firstName = AESEncryption.encrypt_string(firstName);
                        middleName = AESEncryption.encrypt_string(middleName);
                        lastName = AESEncryption.encrypt_string(lastName);
                        address = AESEncryption.encrypt_string(address);
                        telephoneNumber = AESEncryption.encrypt_string(telephoneNumber);
                        yourProblem = AESEncryption.encrypt_string(yourProblem);
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
                    emailToShow = email;
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
                etTelephoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            hideKeyboard(v);
                        }
                    }
                });
                etAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            hideKeyboard(v);
                        }
                    }
                });
                etYourProblem.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
                if (TextUtils.isEmpty(address)) {
                    etAddress.setError(NonSystemMessages.FIELD_MUST_BE_NOT_EMPTY);
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

                if (TextUtils.isEmpty(telephoneNumber)) {
                    etTelephoneNumber.setError(NonSystemMessages.FIELD_MUST_BE_NOT_EMPTY);
                }
            }
    public void requestPatternValidation(){
        RecordToTheOfficialPersonActivity.Request request = new RecordToTheOfficialPersonActivity.Request();
        request.execute();
        Pattern pattern = Pattern.compile("message=.*,");
        Intent signInIntent = new Intent(RecordToTheOfficialPersonActivity.this, SignInActivity.class);
        RecordToTheOfficialPersonActivity.this.startActivity(signInIntent);
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
                etEmail = (EditText) findViewById(R.id.etEmail);
                etFirstName = (EditText) findViewById(R.id.etFirstName);
                etMiddleName = (EditText) findViewById(R.id.etMiddleName);
                etLastName = (EditText) findViewById(R.id.etLastName);
                etAddress = (EditText) findViewById(R.id.etAddress);
                etTelephoneNumber = (EditText) findViewById(R.id.etTelephoneNumber);
                etYourProblem = (EditText) findViewById(R.id.etYourProblem);
                bOfficialPerson = (Button) findViewById(R.id.bOfficialPerson);

            }

            class Request extends AsyncTask<Void, Void, String> {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected String doInBackground(Void... params) {
                    Services services = new Services();
                    Response response = services.SignToOfficialPerson(firstName, lastName, middleName, telephoneNumber, email, address, yourProblem);
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


