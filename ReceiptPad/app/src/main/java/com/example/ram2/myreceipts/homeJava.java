
package com.example.ram2.myreceipts;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;



/**
 * Created by RAM2 on 5/11/2017.
 */

public class homeJava extends AppCompatActivity {

    ImageView codePopup;
    ImageView notificon;
    ImageView dashicon;
    ImageView homeicon;

    StorageReference mStorage;
    StorageReference filePath;

    ImageView currentReceipt;
    ImageView entireReceipt;

    String inputString;
    Toast notValidCode;
    private int REQUEST_CODE = 1;
    String[] arr = new String[3];
    int whichModuletoDisplay;

    TextView begin;

    /*TextView textView;
    SurfaceView cameraView;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;
    StringBuilder stringBuilder;
    TextRecognizer textRecognizer;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
            break;
        }
    } // end onRequestPermissionsResult */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        codePopup = (ImageView) findViewById(R.id.enterCode);
        notificon = (ImageView) findViewById(R.id.notificon);
        dashicon = (ImageView) findViewById(R.id.dashicon);
        homeicon = (ImageView) findViewById(R.id.homeicon);

        begin = (TextView) findViewById(R.id.begin);


        entireReceipt = (ImageView) findViewById(R.id.entireReceipt);
        notValidCode = Toast.makeText(getApplicationContext(), "Not a Valid Code", Toast.LENGTH_LONG);

        arr[0] = "F123";
        arr[1] = "F456";
        arr[2] = "F789";

        whichModuletoDisplay = 1;

        /*cameraView = (SurfaceView) findViewById(R.id.surfaceView);
        textView = (TextView) findViewById(R.id.txtView);


        cameraView.setVisibility(View.VISIBLE);
        textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if (!textRecognizer.isOperational()) {
            Log.w("MainActivity", "Detector Dependencies are not yet available.");
        } else {
            cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 1024)
                    .setRequestedFps(2.0f)
                    .setAutoFocusEnabled(true)
                    .build();
        }


        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {

                try {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(homeJava.this,
                                new String[]{Manifest.permission.CAMERA},
                                RequestCameraPermissionID);
                        return;
                    }
                    cameraSource.start(cameraView.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        }); */

    }// end onCreate method



    @Override
    protected void onStart() {
        super.onStart();

        mStorage = FirebaseStorage.getInstance().getReference();


        dashicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ontoDrawer = new Intent(homeJava.this, drawerJava.class);
                startActivity(ontoDrawer);
                overridePendingTransition(R.anim.slide_in_left, R.anim.exit_right);
            }
        });

        codePopup.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toPopup = new Intent(homeJava.this, Pop.class);
                startActivityForResult(toPopup, REQUEST_CODE);
            }
        }); // end clickListener for refresh button


        homeicon.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entireReceipt.setVisibility(View.INVISIBLE);
                codePopup.setVisibility(View.VISIBLE);
            }
        }); // end clickListener for refresh button

    }// end onStart


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode == REQUEST_CODE ) {
            if (resultCode == RESULT_OK) {
                inputString = data.getStringExtra("code");

                int checkIfValid = 0;
                for ( int i = 0; i < arr.length; i++ ){
                    if ( inputString.equalsIgnoreCase(arr[i]) ) {
                        checkIfValid++;
                        final String whichReceipt = Integer.toString(i+1);
                        int currR = getResources().getIdentifier("receipt" + whichModuletoDisplay, "id", getPackageName());
                        currentReceipt = (ImageView) findViewById(currR);
                        whichModuletoDisplay++;

                        //file path for overview image
                        filePath = mStorage.child("receipt" + whichReceipt + "/" + "bg_home11.jpg");
                        Glide.with(homeJava.this)
                                .using(new FirebaseImageLoader())
                                .load(filePath)
                                .into(currentReceipt);
                        currentReceipt.setScaleType(ImageView.ScaleType.FIT_XY);

                        begin.setVisibility(View.INVISIBLE);

                        //entire receipt image
                        currentReceipt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                filePath = mStorage.child("receipt"+ whichReceipt +"/entirer1.jpg");
                                entireReceipt.setVisibility(View.VISIBLE);
                                entireReceipt.setScaleType(ImageView.ScaleType.FIT_XY);
                                Glide.with(homeJava.this)
                                        .using(new FirebaseImageLoader())
                                        .load(filePath)
                                        .into(entireReceipt);
                                codePopup.setVisibility(View.INVISIBLE);
                            }
                        }); // end Listener for entireReceipt
                    } // end if
                } // end for

                if ( checkIfValid == 0 )
                    notValidCode.show();

            }// end if
        }// end if

    }// end method






} // end class




