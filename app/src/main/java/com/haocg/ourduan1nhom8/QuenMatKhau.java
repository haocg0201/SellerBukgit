package com.haocg.ourduan1nhom8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.haocg.ourduan1nhom8.dao.NhanVienDAO;
import com.haocg.ourduan1nhom8.model.NhanVien;
import com.haocg.ourduan1nhom8.util.EmailSender;

import java.util.Random;

public class QuenMatKhau extends AppCompatActivity {
    Toolbar toolbarQMK;
    EditText edtUsername, edtEmail, edtXacNhanMa;
    TextView txtMaXacNhan;
    Button btnGuiMK;
    NhanVienDAO nhanVienDAO ;
    String maXN = "";
    EmailSender emailSender;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quen_mat_khau);
        nhanVienDAO = new NhanVienDAO(this);

        toolbarQMK = findViewById(R.id.toolbarQMK);
        edtUsername = findViewById(R.id.edtTenTaiKhoanQMK);
        edtEmail = findViewById(R.id.edtEmailQMK);
        edtXacNhanMa = findViewById(R.id.edtXacNhanMaQMK);
        txtMaXacNhan = findViewById(R.id.txtMaXacNhanQMK);
        btnGuiMK = findViewById(R.id.btnGuiMK);
        maXN = generateRandomCode();
        txtMaXacNhan.setText("Mã xác nhận: " + maXN);
        emailSender = new EmailSender(this);

        setSupportActionBar(toolbarQMK);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        btnGuiMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edtUsername.getText().toString();
                String email = edtEmail.getText().toString();
                String xn = edtXacNhanMa.getText().toString();
                if(user.equals("") || email.equals("")){
                    Toast.makeText(QuenMatKhau.this, "Chưa có thông tin", Toast.LENGTH_SHORT).show();
                } else if (!xn.equals(maXN)) {
                    Toast.makeText(QuenMatKhau.this, "Mã OTP chưa chính xác", Toast.LENGTH_SHORT).show();
                }else {
                    NhanVien nv = nhanVienDAO.getNhanVienByCheckExist(user);
                    System.out.println("NV: " + nv.getHoTen() + " - Email: " + nv.getEmail());
                    if(nv != null&& nv.getEmail() != null){
                        if(nv.getEmail().equalsIgnoreCase(email)){
                            String recipientEmail = nv.getEmail();
                            String subject = "THÔNG TIN TỐI MẬT FBI <!>";
                            String messageContent =
                                    "Đây là thông tin tài khoản và mật khẩu, đề nghị xem xong giữ yên lặng, mọi hành vi và lời nói của bạn sẽ trở thành bằng chứng chống lại bạn trước tòa \n" +
                                            "Username: " + nv.getHoTen() + "\n" +
                                            "Password: " + nv.getMatKhau();

                            emailSender.sendEmail(recipientEmail,subject,messageContent);
                            clearForm();
                        }else {
                            Toast.makeText(QuenMatKhau.this, "Email không chính xác", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(QuenMatKhau.this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void clearForm(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                edtUsername.setText("");
                edtEmail.setText("");
                edtXacNhanMa.setText("");
                maXN = generateRandomCode();
                txtMaXacNhan.setText("Mã xác nhận: " + maXN);
            }
        },3500);
    }

    public static String generateRandomCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int randomNumber = random.nextInt(10); // Sinh số ngẫu nhiên từ 0 đến 9 nhé
            code.append(randomNumber);
        }
        return code.toString();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}