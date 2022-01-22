package com.hoangviet.diemdanh;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.EnumMap;

public class DiemdanhActivity extends AppCompatActivity {
EditText edtNhap;
Button btnTaoQR;
ImageView imgMaQR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diemdanh);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Điểm danh");
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnTaoQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultiFormatWriter multiFormatWriter=new MultiFormatWriter();
                try {
                    EnumMap<EncodeHintType, Object> hint = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
                    hint.put(EncodeHintType.CHARACTER_SET, "UTF-8");
                    BitMatrix bitMatrix=multiFormatWriter.encode(edtNhap.getText().toString(), BarcodeFormat.QR_CODE,500,500,hint);
                    BarcodeEncoder barcodeFormat=new BarcodeEncoder();
                    Bitmap bitmap=barcodeFormat.createBitmap(bitMatrix);
                    imgMaQR.setImageBitmap(bitmap);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void addControls() {
        edtNhap=findViewById(R.id.edtNhapmaQR);
        btnTaoQR=findViewById(R.id.btnTaoQR);
        imgMaQR=findViewById(R.id.imgMaQR);
    }

    public void xulyQuetma(View view) {
        startActivity(new Intent(DiemdanhActivity.this,ScanActivity.class));
    }
}