package com.creates.lekan.dismobtravelbusapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Main2ActivityTest {
    Main2Activity activity;
    private EditText postCodeInput;

    @Before
    public void setUp() throws Exception {
        activity = new Main2Activity();
        postCodeInput = (EditText) activity.findViewById(R.id.inputLayout1);
    }

    public void testPreconditions() {
        assertNotNull(postCodeInput);
    }

    @Test
    public void testText() {
        activity.runOnUiThread(new Runnable() {
            public void run() {
                postCodeInput.setText("hello");
            }
        });
        assertEquals("po49ed",postCodeInput.getText());
    }

    @Test
    public void onCreate() {
    }

    @Test
    public void onClick() {
    }
}
