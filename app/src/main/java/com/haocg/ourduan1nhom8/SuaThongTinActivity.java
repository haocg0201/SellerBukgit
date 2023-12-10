package com.haocg.ourduan1nhom8;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.haocg.ourduan1nhom8.dao.NhanVienDAO;

public class SuaThongTinActivity extends AppCompatActivity {

//    TextView txtUsernameSuaThongTin;
//    ImageView imgAnhNV;
//    Switch aSwitch;
//    LinearLayout layoutOutOfDMK;
//    EditText edtTenNV, edtEmailNV,edtMatKhauCu, edtMatKhau, edtXacNhanMK;
//    Button btnLuuThongTin, btnHuyResetForm;
//    NhanVienDAO nvDao;
//    Context context = this;
//    SharedPreferences sharedPreferences;
//    String tenDangNhap, user;
//    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_thong_tin);
//        txtUsernameSuaThongTin = findViewById(R.id.txtTenNVSuaThongTin);
//        imgAnhNV = findViewById(R.id.ivAnhNVSuaThongTin);
//        edtTenNV = findViewById(R.id.edtTenNVSuaThongTin);
//        edtEmailNV = findViewById(R.id.edtEmailNVSuaThongTin);
//        layoutOutOfDMK = findViewById(R.id.layoutDoiMatKhau);
//        aSwitch = findViewById(R.id.switchDoiMatKhau);
//        edtMatKhauCu = findViewById(R.id.edtMatKhauCuSuaThongTin);
//        edtMatKhau = findViewById(R.id.edtMatKhauSuaThongTin);
//        edtXacNhanMK = findViewById(R.id.edtXacNhanMatKhauSuaThongTin);
//        btnLuuThongTin = findViewById(R.id.btnHuyLuuNhanVienSuaThongTin);
//        btnHuyResetForm = findViewById(R.id.btnHuyLuuNhanVienSuaThongTin);
//        nvDao = new NhanVienDAO(context);
//        sharedPreferences = getSharedPreferences("THONGTIN",MODE_PRIVATE);
//        tenDangNhap = sharedPreferences.getString("name","");
//        user = sharedPreferences.getString("user","");
//
//        aSwitch.setChecked(false);
//        layoutOutOfDMK.setVisibility(View.GONE);
//        aSwitch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(aSwitch.isChecked()){
//                    layoutOutOfDMK.setVisibility(View.VISIBLE);
//                }
//            }
//        });
    }
}