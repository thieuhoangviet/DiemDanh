package com.hoangviet.diemdanh;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailDSHS extends AppCompatActivity {
    EditText edtDetailMSSV,edtDetailHoten,edtDetailKhoa,edtDetailLop;
    Button btnSua, btnDel;
    String MSSV, HoTen, Khoa, Lop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_dshs);
        ActionBar actionBar=getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);
        Intent intent=getIntent();
        MSSV=intent.getStringExtra("MSSV");
        HoTen=intent.getStringExtra("HoTen");
        Khoa=intent.getStringExtra("Khoa");
        Lop=intent.getStringExtra("Lop");

        edtDetailMSSV=findViewById(R.id.edtDetailMSSV);
        edtDetailHoten=findViewById(R.id.edtDetailHoTen);
        edtDetailKhoa=findViewById(R.id.edtDetailKhoa);
        edtDetailLop=findViewById(R.id.edtDetailLop);

        edtDetailMSSV.setText(MSSV);
        edtDetailHoten.setText(HoTen);
        edtDetailKhoa.setText(Khoa);
        edtDetailLop.setText(Lop);
        btnSua=findViewById(R.id.btnSua);
        addEvent();
    }

    private void addEvent() {
btnSua.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Suadata();
    }
});
    }

    private void Suadata() {


        StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbyvv72d2en65p-KU70l7Qmxl9sFYdrefR4hxY1wXXgnkyYoEnBqrD9SF9qhEiCu0i1KXw/exec", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                startActivity(new Intent(getApplicationContext(),DanhSachHS.class));
                suaData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })

        ;
        int sk=50000;
        RetryPolicy retryPolicy=new DefaultRetryPolicy(sk,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void suaData(String jsonResposnce) {
        try {
            JSONObject jsonObject=new JSONObject(jsonResposnce);
            if (jsonObject!=null){
                jsonObject.getString("result");

               String mMSSV=edtDetailMSSV.getText().toString();
               String mHoTen=edtDetailHoten.getText().toString();
               String mKhoa=edtDetailKhoa.getText().toString();
               String mLop=edtDetailLop.getText().toString();

                    HashMap<String,String> params=new HashMap<>();
                    params.put("action","updateData");
                    params.put("uMSSV",mMSSV);
                    params.put("uHoTen",mHoTen);
                    params.put("uKhoa",mKhoa);
                    params.put("uLop",mLop);

                }

        }catch (JSONException e){
            e.printStackTrace();
        }

    }
}