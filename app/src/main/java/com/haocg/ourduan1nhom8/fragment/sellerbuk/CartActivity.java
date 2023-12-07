package com.haocg.ourduan1nhom8.fragment.sellerbuk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.haocg.ourduan1nhom8.R;
import com.haocg.ourduan1nhom8.ThanhToanActivity;
import com.haocg.ourduan1nhom8.adapter.GioHangAdapter;
import com.haocg.ourduan1nhom8.dao.GioHangDAO;
import com.haocg.ourduan1nhom8.model.GioHang;
import com.haocg.ourduan1nhom8.util.ItemCartSelected;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    Toolbar toolbarCart;
    TextView txtTongThanhToan;
    CheckBox chkThanhToanAllCart;
    RecyclerView rcvCart;
    Button btnXacNhanMuaHang;
    GioHangDAO ghDAO;
    GioHangAdapter gioHangAdapter;
    ArrayList<GioHang> list;

    SharedPreferences sharedPreferences;
    List<Integer> integerList = null;
    int maNV = 1;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        toolbarCart = findViewById(R.id.toolbarCart);
        rcvCart = findViewById(R.id.rcvCart);
        btnXacNhanMuaHang = findViewById(R.id.btnXacNhanMuaHang);
        txtTongThanhToan = findViewById(R.id.txtTongThanhToanAllCart);
        chkThanhToanAllCart = findViewById(R.id.chkBoxSelectAllCart);
        sharedPreferences = getSharedPreferences("THONGTIN",MODE_PRIVATE);
        maNV = sharedPreferences.getInt("id",1);

        ghDAO = new GioHangDAO(this);
        loadData();
        chkThanhToanAllCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chkThanhToanAllCart.isChecked()){
                    int tongThanhToan = 0;
                    for(GioHang gioHang : list){
                        tongThanhToan+= gioHang.getTongTien();
                    }
                    integerList = gioHangAdapter.checkAllItems(true);
                    txtTongThanhToan.setText(tongThanhToan+"");
                }else {
                    integerList = gioHangAdapter.checkAllItems(false);
                    txtTongThanhToan.setText("0");
                }
                System.out.println("============================== listIdCart bên CartActivity khi ấn check all có " + integerList.size() + " đối tượng");
            }
        });
        setSupportActionBar(toolbarCart);

        // Lấy reference của action bar
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        btnXacNhanMuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!integerList.isEmpty()){
                    Intent intent = new Intent(CartActivity.this, ThanhToanActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putIntegerArrayList("listCart",(ArrayList<Integer>) integerList);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }else Toast.makeText(CartActivity.this, "Chưa có sản phẩm được chọn", Toast.LENGTH_SHORT).show();
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

    public void loadData(){
        integerList = new ArrayList<>();
        list = ghDAO.getAllGioHangByMaNV(maNV+"");
        gioHangAdapter = new GioHangAdapter(this, list, new ItemCartSelected() {
            @Override
            public void onChkItemCartSelected(List<Integer> listIdCart) {
                integerList = listIdCart;
                System.out.println("============================== listIdCart bên CartActivity khi ấn bên adapter có " + listIdCart.size() + " đối tượng");
                int tt = 0;
//                int currentMoney = Integer.parseInt(txtTongThanhToan.getText().toString());
                for(GioHang gh : list){
                    for(int i = 0; i < listIdCart.size();i++){
                        if(gh.getMaGioHang() == listIdCart.get(i)){
                            System.out.println("============================== tt before " + tt + " $$");
                            tt += gh.getTongTien();
                            System.out.println("============================== tt after " + tt + " $$");
                        }
                    }
                }
                txtTongThanhToan.setText(tt+"");
            }
        });
        rcvCart.setLayoutManager(new LinearLayoutManager(this));
        rcvCart.setAdapter(gioHangAdapter);
    }
}