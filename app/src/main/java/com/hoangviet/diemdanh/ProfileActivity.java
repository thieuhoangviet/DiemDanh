package com.hoangviet.diemdanh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    ImageView ivImage;
    TextView tvName;
    Button btLogout;
    FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ivImage = findViewById(R.id.iv_image);
        tvName = findViewById(R.id.tv_name);
        btLogout = findViewById(R.id.bt_logout);

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null){
            Glide.with(ProfileActivity.this)
                    .load(firebaseUser.getPhotoUrl())
                    .into(ivImage);
            tvName.setText(firebaseUser.getDisplayName());
        }

        googleSignInClient = GoogleSignIn.getClient(ProfileActivity.this
        , GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);

        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){
                            firebaseAuth.signOut();

                            Toast.makeText(getApplicationContext(), "Logout successful", Toast.LENGTH_SHORT).show();

                            finish();
                        }
                    }
                });
            }
        });
    }
}