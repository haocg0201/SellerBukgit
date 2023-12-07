package com.haocg.ourduan1nhom8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.WindowDecorActionBar;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.haocg.ourduan1nhom8.dao.GioHangDAO;
import com.haocg.ourduan1nhom8.dao.HoaDonChiTietDAO;
import com.haocg.ourduan1nhom8.dao.HoaDonDAO;
import com.haocg.ourduan1nhom8.model.GioHang;
import com.haocg.ourduan1nhom8.model.HoaDon;
import com.haocg.ourduan1nhom8.model.HoaDonChiTiet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ThanhToanActivity extends AppCompatActivity {
    HoaDonDAO hoaDonDAO;
    HoaDonChiTietDAO hoaDonChiTietDAO;
    GioHangDAO gioHangDAO;
    Toolbar toolbarTTMH;
    TextView txtTT1;
    TextView txtTT2;
    TextView txtTT3;
    EditText edtDiaChiNguoiMua;
    Button btnXNMua, btnHuyMua;
    Context context = this;
    ArrayList<Integer> integerArrayList = null;
    ArrayList<GioHang> gioHangArrayList = null;
    SharedPreferences sharedPreferences;
    int maNV = 1;
    String tt1, tt2, tt3;
    int total = 0;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        hoaDonDAO = new HoaDonDAO(context);
        hoaDonChiTietDAO = new HoaDonChiTietDAO(context);
        gioHangDAO = new GioHangDAO(context);
        toolbarTTMH = findViewById(R.id.toolbarThongTinMuaHang);
        txtTT1 = findViewById(R.id.txtHienThiThongtin1);
        txtTT2 = findViewById(R.id.txtHienThiThongtin2);
        txtTT3 = findViewById(R.id.txtHienThiThongtin3);
        edtDiaChiNguoiMua = findViewById(R.id.edtTenNguoiMuaTTMH);
        btnXNMua = findViewById(R.id.btnXacNhanMuaHangTTMH);

        setSupportActionBar(toolbarTTMH);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

//        btnHuyMua = findViewById(R.id.btnHuyMuaHangTTMH);
        sharedPreferences = getSharedPreferences("THONGTIN",MODE_PRIVATE);
        maNV = sharedPreferences.getInt("id",1);
        Bundle bundle = getIntent().getExtras();
        integerArrayList = (ArrayList<Integer>) bundle.get("listCart");
        tt1 = "Số mặt hàng đã chọn: ";
        tt2 = "Đã trừ thuế + giảm giá 0%";
        tt3 = "Tổng số tiền cần thanh toán: ";
        for (int i = 0; i < integerArrayList.size(); i++) {
            System.out.println("=============check listCart : " + integerArrayList.get(i));
        }
        if(!integerArrayList.isEmpty()){
            gioHangArrayList = new ArrayList<>();
            int j = 0;
            for (Integer i : integerArrayList){
                gioHangArrayList.add(gioHangDAO.getGioHangByMaGH(i));
                System.out.println("=========== giỏ hàng " + i + ": " + gioHangArrayList.get(j).getTenSach());
                j++;
            }
            String t1 = "";
            String t3 = "";
//            for (GioHang g : gioHangArrayList){
//                t1+= "\n" + g.getTenSach() + " (" + g.getGia() + " x " + g.getSoLuong() + ")" + ";";
//                t3+= g.getTongTien() + " + ";
//                total += g.getTongTien();
//            }
            int gioHangSize = gioHangArrayList.size();
            for (int i = 0; i < gioHangSize; i++) {
                GioHang g = gioHangArrayList.get(i);
                t1 += "\n" + g.getTenSach() + " (" + g.getGia() + " x " + g.getSoLuong() + ")" + ";";
                t3 += g.getTongTien();
                // ko phải phần tử cuối thì vẫn +
                if (i < gioHangSize - 1) {
                    t3 += " + ";
                }
                total += g.getTongTien();
            }
            t3 += " = " + total + ".";
            //
            tt1+=t1;
            tt3+="\n" + t3;
            txtTT1.setText(tt1+"");
            txtTT2.setText(tt2+"");
            txtTT2.setTextColor(Color.BLUE);
            txtTT3.setText(tt3+"");
        }

        btnXNMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edtDiaChiNguoiMua.getText().toString().equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Bạn đã kiểm tra kĩ thông tin chưa?");
                    builder.setNegativeButton("Xem lại", null);
                    builder.setPositiveButton("Mua", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Date crTime = Calendar.getInstance().getTime();
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                            String ngay = simpleDateFormat.format(crTime);
                            HoaDon hoaDon = new HoaDon(
                                    maNV,
                                    edtDiaChiNguoiMua.getText().toString(),
                                    ngay,
                                    total,
                                    0
                                    );
                            ArrayList<HoaDonChiTiet> hoaDonChiTietArrayList = new ArrayList<>();
                            for(GioHang hang : gioHangArrayList){
                                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet(
                                        hang.getMaSach(),
                                        hang.getSoLuong(),
                                        hang.getGia(),
                                        hang.getGia()
                                );
                                hoaDonChiTietArrayList.add(hoaDonChiTiet);
                                if(hoaDonDAO.insertOneHoaDonAndManyHDCT(hoaDon,hoaDonChiTietArrayList)){
                                    gioHangDAO.deleteGioHangByListMaGH(integerArrayList);
                                    dialog.dismiss();
                                    startActivity(new Intent(ThanhToanActivity.this,NotifActivity.class));
                                    finish();
                                } else Toast.makeText(context, "Tôi thất bại rồi", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }else Toast.makeText(context, "Chưa có tên người mua", Toast.LENGTH_SHORT).show();

            }
        });


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