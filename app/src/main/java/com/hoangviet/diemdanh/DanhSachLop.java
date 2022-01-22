package com.hoangviet.diemdanh;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DanhSachLop extends AppCompatActivity {
    ListView listView;
    SimpleAdapter listAdapter;
    EditText edtDSL_Search,edtKhoa,edtLop;
    Button btnThem;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_lop);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Danh Sách Lớp");
        actionBar.setDisplayHomeAsUpEnabled(true);
        listView=findViewById(R.id.lvDanhSachLop);

        edtKhoa=findViewById(R.id.edtDSLopKhoa);
        edtLop=findViewById(R.id.edtDSLopLop);
        progressDialog=new ProgressDialog(DanhSachLop.this);
        progressDialog.setMessage("Loading...");

        getDSLop();
        edtDSL_Search=findViewById(R.id.edtDSL_Search);
        btnThem=findViewById(R.id.btnThemDSLOp);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themLop();
            }
        });
    }

    private void themLop() {
        String sKhoa = edtKhoa.getText().toString();
        String sLop = edtLop.getText().toString();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbzZrPn5EZT0l-BNsJL9FNDZUfix-cIWv1z-f3quF_eIlEhpij4Bun0WMraRN5BKtgUgYw/exec", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                startActivity(new Intent(getApplicationContext(),DanhSachLop.class));
                progressDialog.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params=new HashMap<>();
                params.put("action","addDSLop");
                params.put("mKhoa",sKhoa);
                params.put("mLop",sLop);
                return params;
            }
        };
        int sk=50000;
        RetryPolicy retryPolicy=new DefaultRetryPolicy(sk,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void getDSLop() {
        StringRequest stringRequest=new StringRequest(Request.Method.GET, "https://script.google.com/macros/s/AKfycbzZrPn5EZT0l-BNsJL9FNDZUfix-cIWv1z-f3quF_eIlEhpij4Bun0WMraRN5BKtgUgYw/exec?action=getDSLop", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseItemts(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        int soketTimeout=50000;
        RetryPolicy retryPolicy=new DefaultRetryPolicy(soketTimeout,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void parseItemts(String jsonResposnce) {
        ArrayList<HashMap<String,String>> list=new ArrayList<>();
        try {
            JSONObject jsonObject=new JSONObject(jsonResposnce);
            JSONArray jsonArray=jsonObject.getJSONArray("DSLop");

            for (int i=0; i<jsonArray.length();i++){
                JSONObject Json=jsonArray.getJSONObject(i);


                String Khoa=Json.getString("Khoa");
                String Lop=Json.getString("Lop");

                HashMap<String,String> DSLop=new HashMap<>();
                DSLop.put("Khoa",Khoa);
                DSLop.put("Lop",Lop);

                list.add(DSLop);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        listAdapter=new SimpleAdapter(this,list,R.layout.list_dslop,
                new String[]{"Khoa","Lop"},new int[]{R.id.txtDSL_khoa,R.id.txtDSL_lop});
        listView.setAdapter(listAdapter);
        edtDSL_Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                DanhSachLop.this.listAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}