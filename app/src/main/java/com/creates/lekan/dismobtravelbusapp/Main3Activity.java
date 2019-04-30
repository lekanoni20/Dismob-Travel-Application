package com.creates.lekan.dismobtravelbusapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Intent intent = getIntent();
        String latLong = intent.getStringExtra("LatLong");
        String latValue = "";
        String longValue = "";
        try {
            JSONObject geometry =new JSONObject(latLong);
            latValue = geometry.getString("lat");
            longValue = geometry.getString("lng");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String urlString = "http://transportapi.com/v3/uk/places.json?lat=" + latValue
                + "&lon=" + longValue + "&type=bus_stop&app_id=bf361ff7&app_key=3ea6fa46f6028793b6592b733ba96ec3";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(urlString, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (responseBody != null) {
                    try {
                        JSONObject resultValue =new JSONObject(new String(responseBody));
                        JSONArray results = resultValue.getJSONArray("member");
                        final ArrayList<String> busInformation = new ArrayList<>();
                        for(int i=0;i<results.length();i++) {
                            JSONObject line = results.getJSONObject(i);
                            String busStopName = line.optString("atcocode","N/A") + " - "
                                    + line.optString("name","N/A");
                            busInformation.add(busStopName);
                        }
                        ListView myListView = findViewById(R.id.fetcheddata);
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Main3Activity.this,
                                android.R.layout.simple_list_item_1, busInformation);
                        myListView.setAdapter(arrayAdapter);
                        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                String tempListView = busInformation.get(i).toString();
                                Intent intent = new Intent(Main3Activity.this, Main4Activity.class);
                                intent.putExtra("AtcoCodeValue", tempListView);
                                startActivity(intent);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("Error is here: " + statusCode);
            }
        });
    }
}
