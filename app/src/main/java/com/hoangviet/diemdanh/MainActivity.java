package com.hoangviet.diemdanh;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    SignInButton btSignIn;
    GoogleSignInClient googleSignInClient;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btSignIn = findViewById(R.id.bt_sign_in);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(
               GoogleSignInOptions.DEFAULT_SIGN_IN
        ).requestIdToken("797505227734-f5sj4a5jneb6d67gqfgrp5q198c7civg.apps.googleusercontent.com")
            .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(MainActivity.this
        ,googleSignInOptions);

        btSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = googleSignInClient.getSignInIntent();
                startActivityForResult(intent,100);
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser= firebaseAuth.getCurrentUser();
        if (firebaseUser != null){
            startActivity(new Intent(MainActivity.this
            ,ProfileActivity.class)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100){
            Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn
                    .getSignedInAccountFromIntent(data);
            if (signInAccountTask.isSuccessful()){
                String s = "Google sign in successful" ;
                displayToast(s);
                try {
                    GoogleSignInAccount googleSignInAccount =signInAccountTask
                            .getResult(ApiException.class);
                    if (googleSignInAccount !=null){
                        AuthCredential authCredential = GoogleAuthProvider
                                .getCredential(googleSignInAccount.getIdToken(), null);

                        firebaseAuth.signInWithCredential(authCredential)
                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()){

                                            startActivity(new Intent(MainActivity.this,
                                                    ProfileActivity.class)
                                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

                                            displayToast("Firebase authentication successful");

                                        }else {
                                            displayToast("Authentication Failed : "+ task.getException()
                                            .getMessage());
                                        }
                                    }
                                });
                    }
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void displayToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

    }
}