package com.creates.lekan.dismobtravelbusapp;

import android.os.Build;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.SyncHttpClient;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE, sdk = Build.VERSION_CODES.LOLLIPOP)
public class Main3ActivityTest {
    @Rule
    public MockWebServer mockWebServer = new MockWebServer();

    //The same http requests run on the three Activities but use different URLs
    @Test
    public void getData_onSuccess_doesSomething() throws InterruptedException {
        ServerCallback callback = mock(ServerCallback.class);
        String baseUrl = "http://transportapi.com/v3/uk/places.json?lat=51.534121&lon=-0.006944" +
                "&type=bus_stop&app_id=bf361ff7&app_key=3ea6fa46f6028793b6592b733ba96ec3";
        Main3Activity.baseUrl = mockWebServer.url("/").toString();
        Main3Activity.client = new SyncHttpClient();
        mockWebServer.enqueue(new MockResponse().setBody("success"));
        Main3Activity.getData(callback);
        verify(callback).onSuccess(200, "success");
    }
}

