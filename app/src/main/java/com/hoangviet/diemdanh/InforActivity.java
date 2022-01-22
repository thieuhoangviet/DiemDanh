package com.hoangviet.diemdanh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hoangviet.diemdanh.Fragments.CanhanFragment;

import java.util.HashMap;
import java.util.Map;

public class InforActivity extends AppCompatActivity {
EditText edtInfMSSV,edtInfHoten,edtInfLop;
Button btnAddInf;
TextView txtMSSV,txtHoten,txtLop;
private String iMSSV,iHoten,iLop;

public static final String MSSV="MSSV";
public static final String Hoten="Hoten";
public static final String Lop="Lop";
public static final String SHARE_PREFS="sharedPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        setContentView(R.layout.activity_infor);
        edtInfMSSV=findViewById(R.id.edtInfMSSV);
        edtInfHoten=findViewById(R.id.edtInfHoten);
        edtInfLop=findViewById(R.id.edtInfLop);
        btnAddInf=findViewById(R.id.btnAddInf);


        txtHoten=findViewById(R.id.txtInfHoten);
        txtMSSV=findViewById(R.id.txtInfMSSV);
        txtLop=findViewById(R.id.txtInfLop);




        btnAddInf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //addInf();

                sendData();

            }
        });
        loadData();
        update();
    }



    private void update() {
        txtMSSV.setText(iMSSV);
        txtHoten.setText(iHoten);
        txtLop.setText(iLop);

    }

    private void loadData() {

        SharedPreferences sharedPreferences=getSharedPreferences(SHARE_PREFS,MODE_PRIVATE);
        iMSSV=sharedPreferences.getString(MSSV,"");
        iHoten=sharedPreferences.getString(Hoten,"");
        iLop=sharedPreferences.getString(Lop,"");
    }

    private void sendData() {
        txtMSSV.setText(edtInfMSSV.getText().toString());
        txtHoten.setText(edtInfHoten.getText().toString());
        txtLop.setText(edtInfLop.getText().toString());

        SharedPreferences sharedPreferences=getSharedPreferences(SHARE_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(MSSV,txtMSSV.getText().toString());
        editor.putString(Hoten,txtHoten.getText().toString());
        editor.putString(Lop,txtLop.getText().toString());
        editor.apply();
        }

    }

    /*private void addInf() {
        String sMSSV = edtInfMSSV.getText().toString();
        String sTen = edtInfHoten.getText().toString();
        String sLop = edtInfLop.getText().toString();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbzkfDeUKJVEa-2WgvQgcGF3MTi50Nkqrdwn50epA0L1IS6-SkJe0kPPeqviDXSG_7ZN9g/exec", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                startActivity(new Intent(InforActivity.this,MainActivity.class));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params=new HashMap<>();
                params.put("action","addInf");
                params.put("infMSSV",sMSSV);
                params.put("infHoten",sTen);
                params.put("infLop",sLop);
                return params;
            }
        };
        int sk=50000;
        RetryPolicy retryPolicy=new DefaultRetryPolicy(sk,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }*/
//}