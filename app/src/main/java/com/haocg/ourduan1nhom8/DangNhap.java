package com.haocg.ourduan1nhom8;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.haocg.ourduan1nhom8.dao.AdminDAO;
import com.haocg.ourduan1nhom8.dao.NhanVienDAO;


public class DangNhap extends AppCompatActivity {
    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
    Button btnLogin;
    EditText edtUser, edtPassword;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);

        edtUser = findViewById(R.id.edtUserNameLoginForm);
        edtPassword = findViewById(R.id.edtPasswordLoginForm);
        btnLogin = findViewById(R.id.btn_login_login_form);
        AdminDAO dao = new AdminDAO(this);
        NhanVienDAO nvDAO = new NhanVienDAO(this);
        setLoginFormIn4Re();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN",MODE_PRIVATE);
                String user = edtUser.getText().toString();
                String pass = edtPassword.getText().toString();
                if (user.equals("") || pass.equals("")){
                    Toast.makeText(DangNhap.this, "khong de trong", Toast.LENGTH_SHORT).show();
                }else {
                    if(nvDAO.checkLogin(user,pass) == 1){
                        Toast.makeText(DangNhap.this, "Dang nhap thanh cong", Toast.LENGTH_SHORT).show();
                        remember(user,pass);
                        String role = sharedPreferences.getString("role","");
                        System.out.println("================================ role: " + role);
                        if(role.equalsIgnoreCase("nv")){
                            startActivity(new Intent(DangNhap.this, SellerBukMainActivity.class));
                            finish();
                        }else {
                            startActivity(new Intent(DangNhap.this,MainActivity.class));
                            finish();
                        }
                    }else if(nvDAO.checkLogin(user,pass) == -1){
                        Toast.makeText(DangNhap.this, "Tai khoan da bi khoa", Toast.LENGTH_SHORT).show();
                    }else Toast.makeText(DangNhap.this, "Sai Tk hoac Mk", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void remember(String user, String pass){
        SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.apply();
    }

    public void setLoginFormIn4Re(){
        SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN",MODE_PRIVATE);
        String user = sharedPreferences.getString("user","");
        String pass = sharedPreferences.getString("pass","");
            edtUser.setText(user);
            edtPassword.setText(pass);
    }
}