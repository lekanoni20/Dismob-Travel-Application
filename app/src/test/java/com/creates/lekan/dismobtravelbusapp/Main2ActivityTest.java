package com.creates.lekan.dismobtravelbusapp;

import android.widget.Button;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

//This is the test class to check whether the User has entered the correct values for the Input Box and Button.
@RunWith(MockitoJUnitRunner.class)
public class Main2ActivityTest {

    @Mock
    Main2Activity activity;

    @Test
    public void testText() {
        activity = new Main2Activity();
        EditText postCodeInput = (EditText) activity.findViewById(R.id.inputLayout1);
        Button btnAdd = (Button) activity.findViewById(R.id.button);
        postCodeInput.setText("po49ed");
        btnAdd.performClick();
        assertEquals("po49ed",postCodeInput.getText());
    }

    @Test
    public void onCreate() {
    }

    @Test
    public void onClick() {
    }
}