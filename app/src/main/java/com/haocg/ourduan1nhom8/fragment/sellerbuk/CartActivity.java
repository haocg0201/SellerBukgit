package com.haocg.ourduan1nhom8.fragment.sellerbuk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.haocg.ourduan1nhom8.R;
import com.haocg.ourduan1nhom8.adapter.GioHangAdapter;
import com.haocg.ourduan1nhom8.dao.GioHangDAO;
import com.haocg.ourduan1nhom8.model.GioHang;
import com.haocg.ourduan1nhom8.util.ItemCartSelected;

import org.w3c.dom.Text;

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
    ArrayList<Integer> oldListIdCart = new ArrayList<>();

//    SharedPreferences sharedPreferences;
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
//        sharedPreferences = getSharedPreferences("THONGTIN",MODE_PRIVATE);
//        String maNV = sharedPreferences.getString("manv","");
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
                    gioHangAdapter.checkAllItems(true);
                    txtTongThanhToan.setText(tongThanhToan+"");
                }else {
                    gioHangAdapter.checkAllItems(false);
                    txtTongThanhToan.setText("0");
                }
            }
        });
        btnXacNhanMuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        setSupportActionBar(toolbarCart);
        getSupportActionBar();
    }

//    public void setTongThanhToan(){}

    public void loadData(){
        list = ghDAO.getAllGioHangByMaNV("");
        gioHangAdapter = new GioHangAdapter(this, list, new ItemCartSelected() {
            @Override
            public void onChkItemCartSelected(List<Integer> listIdCart) {
                System.out.println("============================== listIdCart có " + listIdCart.size() + " đối tượng");
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