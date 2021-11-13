package com.hoangviet.diemdanh.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hoangviet.diemdanh.LoginActivity;
import com.hoangviet.diemdanh.R;


public class CanhanFragment extends Fragment {
    ImageView avtGG;
    TextView tenMail,ten;
    Button btnDangXuat;
    FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view=inflater.inflate(R.layout.fragment_canhan, container, false);
         avtGG=view.findViewById(R.id.avtGG);
         tenMail=view.findViewById(R.id.tenMail);
         ten=view.findViewById(R.id.ten);
         btnDangXuat=view.findViewById(R.id.btnDangxuat);
         firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        GoogleSignInAccount signInAccount= GoogleSignIn.getLastSignedInAccount(view.getContext());
        if (firebaseUser != null){
            Glide.with(CanhanFragment.this)
                    .load(firebaseUser.getPhotoUrl())
                    .into(avtGG);
            tenMail.setText(firebaseUser.getEmail());
            ten.setText(firebaseUser.getDisplayName());
        }
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(view.getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

         return view;
    }

}