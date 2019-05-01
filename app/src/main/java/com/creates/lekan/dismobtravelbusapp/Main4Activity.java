package com.creates.lekan.dismobtravelbusapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import cz.msebera.android.httpclient.Header;

import static com.loopj.android.http.AsyncHttpClient.log;

public class Main4Activity extends AppCompatActivity {

    TextView textView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        textView1 = findViewById(R.id.paraLabel5);
        String TempHolder = getIntent().getStringExtra("AtcoCodeValue");
        String[] parts = TempHolder.split("-");
        String atcoCode = parts[0].trim();
        String busName = parts[1] + " " + parts[2] + " " + parts[3];
        String urlString = "https://transportapi.com/v3/uk/bus/stop/" + atcoCode
                + "/live.json?app_id=bf361ff7&app_key=3ea6fa46f6028793b6592b733ba96ec3&group=route&nextbuses=yes";
        textView1.setText(busName);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(urlString, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (responseBody != null) {
                    try {
                        JSONObject resultValue =new JSONObject(new String(responseBody));
                        JSONObject results  = resultValue.getJSONObject("departures");
                        final ArrayList<String> busInformation = new ArrayList<>();
                        Iterator<?> keys = results.keys();
                        while(keys.hasNext()) {
                            String key = (String)keys.next();
                            JSONArray results1 = results.getJSONArray(key);
                            for(int i=0;i<results1.length();i++) {
                                JSONObject line = results1.getJSONObject(i);
                                String busTime = line.optString("line", "N/A") + " to "
                                        + line.optString("direction", "N/A") + " arriving at "
                                        + line.optString("best_departure_estimate", "N/A");
                                busInformation.add(busTime);
                            }
                        }
                       ListView myListView = findViewById(R.id.fetcheddata2);
                       ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Main4Activity.this,
                               android.R.layout.simple_list_item_1, busInformation);
                       myListView.setAdapter(arrayAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("Error is: " + statusCode);
            }
        });
    }

}
