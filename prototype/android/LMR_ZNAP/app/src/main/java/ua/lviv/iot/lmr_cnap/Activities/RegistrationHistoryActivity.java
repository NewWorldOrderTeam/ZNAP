package ua.lviv.iot.lmr_cnap.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.lviv.iot.lmr_cnap.ClientUtilities.SystemMessages;
import ua.lviv.iot.lmr_cnap.Pojo.RegistrationToCnap;
import ua.lviv.iot.lmr_cnap.R;
import ua.lviv.iot.lmr_cnap.ServerUtilities.Request;
import ua.lviv.iot.lmr_cnap.ServerUtilities.ZnapUtility;

public class RegistrationHistoryActivity extends AppCompatActivity{
    private static Request request;
    List<RegistrationToCnap> registrations;
    private int userid;
    WebView webView;
    String html;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Історія записів");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_registration_history);
        webView = (WebView) findViewById(R.id.webView);
        Bundle bundle = getIntent().getExtras();
        html = "";
        if (bundle != null) {
            assert bundle != null;
            userid = bundle.getInt(SystemMessages.USER_ID);
            registrations = new ArrayList<>();
            request = ZnapUtility.generateRetroRequest();
            RegistrationHistoryActivity.getApi().getHistory(userid).enqueue(new Callback<List<RegistrationToCnap>>() {
                @Override
                public void onResponse(Call<List<RegistrationToCnap>> call, Response<List<RegistrationToCnap>> response) {
                    registrations.addAll(response.body());
                    for (int i = registrations.size() - 1; i > -1; i--) {
                        html = html+registrations.get(i).getHtml();
                        webView.loadData(html, "text/html; charset=UTF-8", null);
                    }
                }

                @Override
                public void onFailure(Call<List<RegistrationToCnap>> call, Throwable t) {
                }
            });
        } else {
            finish();
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

    public static Request getApi() {
        return request;
    }
}
