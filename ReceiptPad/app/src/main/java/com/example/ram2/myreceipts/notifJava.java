package com.example.ram2.myreceipts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by RAM2 on 5/11/2017.
 */

public class notifJava extends AppCompatActivity {

    TextView updates;
    LinearLayout imageSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        updates = (TextView) findViewById(R.id.notifModule);
        imageSection = (LinearLayout) findViewById(R.id.imageSection);

        ImageView homeicon = (ImageView) findViewById(R.id.homeicon);
        homeicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ontoHome = new Intent(notifJava.this, homeJava.class);
                startActivity(ontoHome);
                overridePendingTransition(R.anim.slide_in_left, R.anim.exit_right);
            }
        });

        ImageView dashicon = (ImageView) findViewById(R.id.dashicon);
        dashicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick ( View view ) {
                Intent ontoDrawer = new Intent(notifJava.this, drawerJava.class);
                startActivity(ontoDrawer);
                overridePendingTransition(R.anim.slide_in_left, R.anim.exit_right);
            }
        });

    }// end oncreate

    public void setTxtView () {
        updates = (TextView) findViewById(R.id.notifModule);
        String updateMsg = updates.getText().toString() + "/n" + "A new receipt has been added by:";
        updates.setText(updateMsg);
    } // end setTxtView

}// end class
