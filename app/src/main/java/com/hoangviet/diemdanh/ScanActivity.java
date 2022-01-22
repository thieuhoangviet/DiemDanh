package com.hoangviet.diemdanh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
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
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.HashMap;
import java.util.Map;

public class ScanActivity extends AppCompatActivity {
CodeScannerView scannerView;
CodeScanner codeScanner;
TextView txtMonhoc;
Button btnDiemdanh;
EditText edtMSSV,edtHoten,edtLop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        scannerView=findViewById(R.id.scan);
        codeScanner=new CodeScanner(this,scannerView);
        txtMonhoc=findViewById(R.id.txtMonhoc);
        btnDiemdanh=findViewById(R.id.btnDiemdanhMa);
        edtMSSV=findViewById(R.id.eInfMSSV);
        edtHoten=findViewById(R.id.eInfHoten);
        edtLop=findViewById(R.id.eInfLop);
        btnDiemdanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diemdanh();
            }
        });
        /*Intent intent=getIntent();
        MSSV=intent.getStringExtra("MSSV");
        Hoten=intent.getStringExtra("Hoten");
        Lop=intent.getStringExtra("Lop");

        txtMSSV=findViewById(R.id.txtInfMSSV);
        txtHoten=findViewById(R.id.txtInfHoten);
        txtLop=findViewById(R.id.txtInfLop);

        txtMSSV.setText(MSSV);
        txtHoten.setText(MSSV);
        txtLop.setText(MSSV);*/

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtMonhoc.setText(result.getText());
                    }
                });
            }
        });

        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeScanner.startPreview();
            }
        });
    }

    private void diemdanh() {
        String sMonhoc=txtMonhoc.getText().toString();
        String sMSSV=edtMSSV.getText().toString();
        String sHoTen=edtHoten.getText().toString();
        String sLop=edtLop.getText().toString();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbxB3fJqssfe127BZjeWiItSn677coz89s1QElVCtERCXMMFQ0tdGaQIRemou4zOAW-YWA/exec", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params=new HashMap<>();
                params.put("action","add");
                params.put("dsMonhoc",sMonhoc);
                params.put("dsMSSV",sMSSV);
                params.put("dsHoten",sHoTen);
                params.put("dsLop",sLop);
                return params;
            }
        };
        int sk=50000;
        RetryPolicy retryPolicy=new DefaultRetryPolicy(sk,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    @Override
    protected void onResume() {
        super.onResume();
        requestForCam();
    }

    private void requestForCam() {
        Dexter.withActivity(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                codeScanner.startPreview();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                Toast.makeText(ScanActivity.this,"Camera permission is required",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();
    }
}