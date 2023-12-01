package com.haocg.ourduan1nhom8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.haocg.ourduan1nhom8.dao.AdminDAO;



public class DangNhap extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);
        EditText edtUser = findViewById(R.id.edtUserName);
        EditText edtPass = findViewById(R.id.edtPass);
        Button btnLogin = findViewById(R.id.btn_login);
        AdminDAO dao = new AdminDAO(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = edtUser.getText().toString();
                String pass = edtPass.getText().toString();
                if(dao.checkLogin(user,pass)){


                    startActivity(new Intent(DangNhap.this,MainActivity.class));

                }else {
                    Toast.makeText(DangNhap.this, "Sai TK or MK", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}