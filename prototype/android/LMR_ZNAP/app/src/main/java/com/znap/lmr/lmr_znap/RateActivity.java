package com.znap.lmr.lmr_znap;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Andy Blyzniuk on 13.11.2017.
 */

public class RateActivity extends AppCompatActivity implements
        OnItemSelectedListener {
    int quality;
    int znap_id;
    int pushed_user_id;
    Button btBad;
    Button btGood;
    Button btLeaveReview;
    String description;
    Spinner spinnerForZnaps;
    EditText etDescription;
    TextView labelForQuality;
    boolean badButtonClickedStatus = false;
    boolean goodButtonClickedStatus = true;
    Context context = this;
    public static Request request;
    List<ZnapName> znapNames;
    List<String> znaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        getSupportActionBar().setTitle(SystemMessages.RATE_TITLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spinnerForZnaps = (Spinner) findViewById(R.id.znaps);
        spinnerForZnaps.setOnItemSelectedListener(this);
        etDescription = (EditText) findViewById(R.id.etDescription);
        btLeaveReview = (Button) findViewById(R.id.btLeaveReview);
        labelForQuality = (TextView) findViewById(R.id.labelForQuality);
        btGood = (Button) findViewById(R.id.btGood);
        btBad = (Button) findViewById(R.id.btBad);
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            assert bundle != null;
            int userid = bundle.getInt(SystemMessages.USER_ID);
            pushed_user_id = userid;
            znapNames = new ArrayList<>();
            znaps = new ArrayList<>();
            request = ZnapUtility.generateRetroRequest();
            RateActivity.getApi().getZnapNames().enqueue(new Callback<List<ZnapName>>() {
                @Override
                public void onResponse(Call<List<ZnapName>> call, Response<List<ZnapName>> response) {
                    znapNames.addAll(response.body());

                    for (int i = 0; i < znapNames.size(); i++) {
                        System.out.println(znapNames.get(i).getName());
                        znaps.add(znapNames.get(i).getName());

                    }
                    final ArrayAdapter<String> a = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, znaps);
                    a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerForZnaps.setAdapter(a);

                }

                @Override
                public void onFailure(Call<List<ZnapName>> call, Throwable t) {
                }
            });
            } else {
            finish();
            }





        btLeaveReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description = etDescription.getText().toString();
                try {
                    description = AESEncryption.encrypt_string(description);
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
                if (TextUtils.isEmpty(description)) {
                    etDescription.setError(NonSystemMessages.FIELD_MUST_BE_NOT_EMPTY);
                    return;
                }
                btGood.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goodButtonClickedStatus = true;
                        badButtonClickedStatus = false;
                    }
                });
                btBad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goodButtonClickedStatus = false;
                        badButtonClickedStatus = true;
                    }
                });
                if (goodButtonClickedStatus) {
                    quality = Integer.parseInt(btGood.getText().toString());
                } else if (badButtonClickedStatus) {
                    quality = Integer.parseInt(btBad.getText().toString());
                }
                new AlertDialog.Builder(context)
                        .setMessage(NonSystemMessages.rateSuccessful)
                        .setCancelable(false)
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                requestPatternValidation();
                                finish();
                            }
                        })
                        .show();

            }
        });

    }

    public static Request getApi() {
        return request;
    }

    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        String sp1 = String.valueOf(spinnerForZnaps.getSelectedItem());
        znap_id = (int) spinnerForZnaps.getItemIdAtPosition(arg2);
        System.out.println(znap_id);
        Toast.makeText(this, sp1, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void requestPatternValidation(){
        RateActivity.RequestForServer request = new RateActivity.RequestForServer();
        request.execute();
        Pattern pattern = Pattern.compile("message=.*,");
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

    class RequestForServer extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            Services services = new Services();
            Response response = services.Rate(pushed_user_id, znap_id, description, quality);
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

