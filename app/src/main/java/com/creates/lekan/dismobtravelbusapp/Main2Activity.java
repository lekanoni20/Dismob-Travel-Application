package com.creates.lekan.dismobtravelbusapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Log;

import com.google.gson.Gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.json.*;


import org.json.JSONException;

import java.lang.reflect.Field;
import java.util.*;

import cz.msebera.android.httpclient.Header;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    EditText postCodeInput;
    String singleParsed = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Button click = (Button) findViewById(R.id.button);
        assert click != null;
        click.setOnClickListener(this);
    }

    @Override
    public void onClick(final View view) {
        view.setEnabled(false);
        postCodeInput = (EditText) findViewById(R.id.inputLayout1);
        String postCodeInputString = postCodeInput.getText().toString().trim().toLowerCase();
        if (postCodeInputString != null) {
            AsyncHttpClient client = new AsyncHttpClient();
            client.get("https://api.opencagedata.com/geocode/v1/json?q=" +
                    postCodeInputString + "&key=2d5add7673ac43c4953fe8b9fb0fa872&language=en&pretty=1",
                    new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (responseBody != null) {
                        try {
                            JSONObject resultValue = new JSONObject(new String(responseBody));
                            JSONArray results = resultValue.getJSONArray("results");
                            JSONObject geomoetry = results.getJSONObject(0);
                            singleParsed = geomoetry.getString("geometry");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
                    intent.putExtra("LatLong", singleParsed);
                    startActivity(intent);
                    view.setEnabled(true);
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    view.setEnabled(true);
                }
            });
        }
    }
}
