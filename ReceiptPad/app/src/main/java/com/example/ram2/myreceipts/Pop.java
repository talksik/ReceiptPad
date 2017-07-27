package com.example.ram2.myreceipts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Dhananjaya on 5/22/2017.
 */

public class Pop extends Activity {

    EditText inputCode;
    Button retreiveR;
    String inputString = "";
    Toast noInput;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        inputCode = (EditText) findViewById(R.id.inputCode);
        retreiveR = (Button) findViewById(R.id.retreiveR);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.7), (int)(height*.6));

        noInput = Toast.makeText(getApplicationContext(), "Please input a code", Toast.LENGTH_LONG);

    } // end onCreate

    @Override
    protected void onStart() {
        super.onStart();

        retreiveR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputString = inputCode.getText().toString();
                if ( inputString.equals("") )
                    noInput.show();
                else {
                    Intent intent = new Intent(Pop.this, homeJava.class);
                    intent.putExtra("code", inputString);
                    setResult(RESULT_OK, intent);
                    finish();
                }// end else
            }
        }); // end listener

    } // end onStart


}

