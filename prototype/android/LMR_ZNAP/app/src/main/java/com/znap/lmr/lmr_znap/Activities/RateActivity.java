package com.znap.lmr.lmr_znap.Activities;

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
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.znap.lmr.lmr_znap.Security.AESEncryption;
import com.znap.lmr.lmr_znap.ClientUtilities.NonSystemMessages;
import com.znap.lmr.lmr_znap.R;
import com.znap.lmr.lmr_znap.ServerUtilities.Request;
import com.znap.lmr.lmr_znap.ServerUtilities.Services;
import com.znap.lmr.lmr_znap.ClientUtilities.SystemMessages;
import com.znap.lmr.lmr_znap.Pojo.ZnapName;
import com.znap.lmr.lmr_znap.ServerUtilities.ZnapUtility;

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
import tr.xip.widget.simpleratingview.SimpleRatingView;



public class RateActivity extends AppCompatActivity implements OnItemSelectedListener {
    int quality;
    int znap_id;
    int pushed_user_id;
    Button btLeaveReview;
    String description;
    Spinner spinnerForZnaps;
    EditText etDescription;
    TextView labelForQuality;
    TextView rating;
    TextView appearance,goodmood,proffesionality,rate;
    RatingBar ratingBar,ratingBar2,ratingBar3,ratingBar4;
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
        rating = (TextView) findViewById(R.id.rating);
        appearance=(TextView) findViewById(R.id.appearance);
        goodmood=(TextView) findViewById(R.id.goodmood);
        proffesionality=(TextView) findViewById(R.id.proffesionality);
        rate=(TextView) findViewById(R.id.rate);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar1);
        ratingBar2=(RatingBar) findViewById(R.id.ratingBar2);
        ratingBar3=(RatingBar) findViewById(R.id.ratingBar3);
        ratingBar4=(RatingBar) findViewById(R.id.ratingBar4);
        ratingBar.setNumStars(5);
        ratingBar2.setNumStars(5);
        ratingBar3.setNumStars(5);
        ratingBar4.setNumStars(5);
        rate=(TextView) findViewById(R.id.rate);
        final SimpleRatingView mSimpleRatingView = (SimpleRatingView) findViewById(R.id.simple_rating_view);

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
                        znaps.add(znapNames.get(i).getName());

                    }
                    final ArrayAdapter<String> a = new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, znaps);
                    a.setDropDownViewResource(R.layout.spinner_item);
                    spinnerForZnaps.setAdapter(a);

                }

                @Override
                public void onFailure(Call<List<ZnapName>> call, Throwable t) {
                }
            });
        } else {
            finish();
        }

        mSimpleRatingView.setOnRatingChangedListener(new SimpleRatingView.OnRatingChangeListener() {
            @Override
            public void onRatingChanged(SimpleRatingView.Rating ratingType) {
                if (mSimpleRatingView.getSelectedRating() == SimpleRatingView.Rating.NEGATIVE) {
                    rating.setText("Я незадоволений");
                }
                if (mSimpleRatingView.getSelectedRating() == SimpleRatingView.Rating.POSITIVE) {
                    rating.setText("Я задоволений");
                }
                if (mSimpleRatingView.getSelectedRating() == SimpleRatingView.Rating.NEUTRAL) {
                    rating.setText("Нейтрально");
                }
                switch (ratingType) {
                    case POSITIVE:
                        goodButtonClickedStatus = true;
                        break;
                    case NEUTRAL:
                        badButtonClickedStatus = false;
                        break;
                    case NEGATIVE:
                        badButtonClickedStatus = false;
                        break;
                }
            }
        });


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
                if (goodButtonClickedStatus) {
                    quality = 1;
                } else if (badButtonClickedStatus) {
                    quality = 0;
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
        Toast.makeText(this, sp1, Toast.LENGTH_SHORT).show();
        znap_id += 1;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void requestPatternValidation() {
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

