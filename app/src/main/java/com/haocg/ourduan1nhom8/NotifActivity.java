package com.haocg.ourduan1nhom8;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class NotifActivity extends AppCompatActivity {
    ImageView imgLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        LinearLayout layout = findViewById(R.id.linearNotif);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NotifActivity.this, SellerBukMainActivity.class));
                finish();
            }
        });

    }
}