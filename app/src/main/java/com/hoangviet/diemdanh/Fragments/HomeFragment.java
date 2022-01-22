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
import com.hoangviet.diemdanh.DanhSachActivity;
import com.hoangviet.diemdanh.DiemdanhActivity;
import com.hoangviet.diemdanh.InforActivity;
import com.hoangviet.diemdanh.R;
import com.hoangviet.diemdanh.ThongkeActivity;


public class HomeFragment extends Fragment {
    Button btnDanhSach,btnDiemDanh,btnThongke;
    ImageView avatar;
    TextView Ten;
    FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         View view=inflater.inflate(R.layout.fragment_home, container, false);
         btnDanhSach=view.findViewById(R.id.btnDanhsach);
         btnDiemDanh=view.findViewById(R.id.btnDiemDanh);
         btnThongke=view.findViewById(R.id.btnThongke);
         avatar=view.findViewById(R.id.avtarGG);
         avatar.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(view.getContext(), InforActivity.class));
             }
         });
         Ten=view.findViewById(R.id.tenGG);
        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        GoogleSignInAccount signInAccount= GoogleSignIn.getLastSignedInAccount(view.getContext());
        if (firebaseUser != null){
            Glide.with(HomeFragment.this)
                    .load(firebaseUser.getPhotoUrl())
                    .into(avatar);
            Ten.setText(firebaseUser.getDisplayName());
        }
         addEvents();
         return view;
    }

    private void addEvents() {
        btnDanhSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplication(), DanhSachActivity.class));
            }
        });
        btnDiemDanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(getActivity().getApplication(), DiemdanhActivity.class));
            }
        });
        btnThongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplication(), ThongkeActivity.class));
            }
        });
    }
}