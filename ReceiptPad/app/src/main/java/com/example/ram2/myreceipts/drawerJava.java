package com.example.ram2.myreceipts;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by RAM2 on 5/17/2017.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.net.URI;


public class drawerJava extends AppCompatActivity {

        private FirebaseAuth mAuth;
        private FirebaseAuth.AuthStateListener mAuthListener;

        private GoogleApiClient mGoogleApiClient;

        TextView currentUserName;
        ImageView profPic;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_drawer);

            currentUserName = (TextView) findViewById(R.id.currentUserName);
            profPic = (ImageView) findViewById(R.id.profPic);

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();

            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this /* FragmentActivity */,new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                            Toast.makeText(drawerJava.this, "Authorization Denied", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();


            mAuth = FirebaseAuth.getInstance();
            mAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if(firebaseAuth.getCurrentUser() == null) {
                        Intent ontoWelcome = new Intent(drawerJava.this, WelcomePage.class);
                        startActivity(ontoWelcome);
                    }
                }
            };


            Button logOut = (Button) findViewById(R.id.logOut);
            logOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // completely logout user
                    mAuth.signOut();
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                            new ResultCallback<Status>() {
                                @Override
                                public void onResult(@NonNull Status status) {
                                }
                            });
                }
            });


            Button exit = (Button) findViewById(R.id.exit);
            exit.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick ( View view ) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    startActivity(intent);

                }
            });

            Button back = (Button) findViewById(R.id.back);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent back = new Intent(drawerJava.this, homeJava.class);
                    startActivity(back);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.exit_left);
                }
            });
        } // end onCreate

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String name = user.getDisplayName();
        currentUserName.setText(name);

        Uri photoUrl = user.getPhotoUrl();
        Glide.with(drawerJava.this)
                .load(photoUrl)
                .into(profPic);
    } // end onStart

    @Override
    public void onBackPressed() {
        Intent back = new Intent(drawerJava.this, homeJava.class);
        startActivity(back);
        overridePendingTransition(R.anim.slide_in_right, R.anim.exit_left);
        return;
    }

}// end class
