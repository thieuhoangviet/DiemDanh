package com.hoangviet.diemdanh;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

public class ThongkeActivity extends AppCompatActivity {
    ListView listView;
    SimpleAdapter listAdapter;
    EditText edtTimkiem,edtTimLop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongke);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Thống kê");
        listView=findViewById(R.id.lvThongke);
        edtTimkiem=findViewById(R.id.edtTim);
        edtTimLop=findViewById(R.id.edtTimLop);
        getDSDD();
    }

    private void getDSDD() {
        StringRequest stringRequest=new StringRequest(Request.Method.GET, "https://script.google.com/macros/s/AKfycbzElYIhsCsurw-Ne7nuBkH1Rr6WEs432X2dpgMzGblgyrLKSCP2QMRVFIe3jDPaRz3iAA/exec?action=getDSDD", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseDSDM(response);
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

    private void parseDSDM(String Response) {
        ArrayList<HashMap<String,String>> list=new ArrayList<>();
        try {
            JSONObject jsonObject=new JSONObject(Response);
            JSONArray jsonArray=jsonObject.getJSONArray("DSDiemdanh");

            for (int i=0; i<jsonArray.length();i++){
                JSONObject Json=jsonArray.getJSONObject(i);

                String Monhoc=Json.getString("Monhoc");
                String MSSV=Json.getString("MSSV");
                String HoTen=Json.getString("Hoten");
                String Lop=Json.getString("Lop");
                String Time=Json.getString("Time");

                HashMap<String,String> DSDD=new HashMap<>();
                DSDD.put("Monhoc",Monhoc);
                DSDD.put("MSSV",MSSV);
                DSDD.put("Hoten",HoTen);
                DSDD.put("Lop",Lop);
                DSDD.put("Time",Time);

                list.add(DSDD);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        listAdapter=new SimpleAdapter(this,list,R.layout.list_dsdd,
                new String[]{"Monhoc","MSSV","Hoten","Lop","Time"},new int[]{R.id.txtDSMon,R.id.txtDSMSSV,R.id.txtDSHoTen,R.id.txtDSLop,R.id.txtTime});
        listView.setAdapter(listAdapter);
        edtTimkiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            ThongkeActivity.this.listAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtTimLop.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ThongkeActivity.this.listAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
