package com.haocg.ourduan1nhom8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

//        ImageView imgLogo = findViewById(R.id.imgChao);
//        Glide.with(this).load(R.drawable.book).into(imgLogo);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Welcome.this,DangNhap.class);
                startActivity(intent);
            }
        },2500);
    }
}