package com.haocg.ourduan1nhom8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.haocg.ourduan1nhom8.dao.NhanVienDAO;
import com.haocg.ourduan1nhom8.fragment.DoiMatKhauFragment;
import com.haocg.ourduan1nhom8.fragment.HoaDonChiTietFragment;
import com.haocg.ourduan1nhom8.fragment.HoaDonFragment;
import com.haocg.ourduan1nhom8.fragment.HomeFragment;
import com.haocg.ourduan1nhom8.fragment.KhoFragment;
import com.haocg.ourduan1nhom8.fragment.LoaiSachFragment;
import com.haocg.ourduan1nhom8.fragment.NhanVienFragment;
import com.haocg.ourduan1nhom8.fragment.SachFragment;
import com.haocg.ourduan1nhom8.fragment.SuaThongTinTaiKhoanFragment;
import com.haocg.ourduan1nhom8.fragment.ThongKeDoanhThuFragment;
import com.haocg.ourduan1nhom8.fragment.ThongKeSachTonKhoFragment;
import com.haocg.ourduan1nhom8.fragment.ThongKeTopDauSachFragment;
import com.haocg.ourduan1nhom8.model.NhanVien;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    SharedPreferences sharedPreferences;

    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_QLSACH = 1;
    private static final int FRAGMENT_QLLOAISACH = 2;
    private static final int FRAGMENT_QLKHO = 3;
    private static final int FRAGMENT_QLHOADON = 4;
    private static final int FRAGMENT_QLHDCT = 5;
    private static final int FRAGMENT_QLNV = 6;
    private static final int FRAGMENT_TKDOANHTHU = 7;
    private static final int FRAGMENT_TKDAUSACH = 8;
    private static final int FRAGMENT_TKTONKHO = 9;
    private static final int FRAGMENT_SUATTTK = 10;
    private static final int FRAGMENT_DOIMK = 11;

    private int mCurrentFragment = FRAGMENT_HOME;
    String currentUserName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbarMa);
        FrameLayout frameLayout = findViewById(R.id.frameLayoutMa);
        NavigationView navigationView = findViewById(R.id.navigationViewMa);
        drawerLayout = findViewById(R.id.drawerLayout);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_popup);

        // full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ActionBarDrawerToggle drawerToggle =new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        //
        drawerLayout.addDrawerListener(drawerToggle);
        replaceFragment(new HomeFragment());

        View headerView = navigationView.getHeaderView(0);
        TextView txtCurrentUserName = headerView.findViewById(R.id.txtMainUserName);
        ImageView avtImg = headerView.findViewById(R.id.avtImg);
        Glide.with(this).load(R.mipmap.avt).into(avtImg);

        sharedPreferences = getSharedPreferences("THONGTIN",MODE_PRIVATE);
        currentUserName = sharedPreferences.getString("name","");
        txtCurrentUserName.setText("Xin chào anh đz \n" + currentUserName);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment;
                int id = item.getItemId();
                if(id == R.id.mHome){
                    toolbar.setTitle("Home");
                    if(mCurrentFragment != FRAGMENT_HOME){
                        replaceFragment(new HomeFragment());
                        mCurrentFragment = FRAGMENT_HOME;
                    }
                }else if(id == R.id.mQLSach) {
                    toolbar.setTitle("Quản lý Sách");
                    if (mCurrentFragment != FRAGMENT_QLSACH) {
                        replaceFragment(new SachFragment());
                        mCurrentFragment = FRAGMENT_QLSACH;
                    }
                }else if(id == R.id.mQLLS) {
                    toolbar.setTitle("Quản lý Loại sách");
                    if (mCurrentFragment != FRAGMENT_QLLOAISACH) {
                        replaceFragment(new LoaiSachFragment());
                        mCurrentFragment = FRAGMENT_QLLOAISACH;
                    }
                }else if(id == R.id.mQLKho) {
                    toolbar.setTitle("Quản lý Kho");
                    if (mCurrentFragment != FRAGMENT_QLKHO) {
                        replaceFragment(new KhoFragment());
                        mCurrentFragment = FRAGMENT_QLKHO;
                    }
                }else if(id == R.id.mQLHD) {
                    toolbar.setTitle("Quản lý Hóa đơn");
                    if (mCurrentFragment != FRAGMENT_QLHOADON) {
                        replaceFragment(new HoaDonFragment());
                        mCurrentFragment = FRAGMENT_QLHOADON;
                    }
                }else if(id == R.id.mQLHDCT) {
                    toolbar.setTitle("Quản lý Hóa đơn chi tiết");
                    if (mCurrentFragment != FRAGMENT_QLHDCT) {
                        replaceFragment(new HoaDonChiTietFragment());
                        mCurrentFragment = FRAGMENT_QLHDCT;
                    }
                }else if(id == R.id.mQLNV) {
                    toolbar.setTitle("Quản lý Nhân viên");
                    if (mCurrentFragment != FRAGMENT_QLNV) {
                        replaceFragment(new NhanVienFragment());
                        mCurrentFragment = FRAGMENT_QLNV;
                    }
                }else if(id == R.id.mTKDT) {
                    toolbar.setTitle("Thống kê Doanh thu");
                    if (mCurrentFragment != FRAGMENT_TKDOANHTHU) {
                        replaceFragment(new ThongKeDoanhThuFragment());
                        mCurrentFragment = FRAGMENT_TKDOANHTHU;
                    }
                }else if(id == R.id.mTKTOP) {
                    toolbar.setTitle("Thống kê top sách");
                    if (mCurrentFragment != FRAGMENT_TKDAUSACH) {
                        replaceFragment(new ThongKeTopDauSachFragment());
                        mCurrentFragment = FRAGMENT_QLNV;
                    }
                }else if(id == R.id.mTKTonKho) {
                    toolbar.setTitle("Thống kê Tồn kho");
                    if (mCurrentFragment != FRAGMENT_TKTONKHO) {
                        replaceFragment(new ThongKeSachTonKhoFragment());
                        mCurrentFragment = FRAGMENT_TKTONKHO;
                    }
                }else if(id == R.id.mSuaTTTK) {
                    toolbar.setTitle("Sửa thông tin");
                    if (mCurrentFragment != FRAGMENT_SUATTTK) {
                        replaceFragment(new SuaThongTinTaiKhoanFragment());
                        mCurrentFragment = FRAGMENT_SUATTTK;
                    }
                }else if(id == R.id.mDoiMK) {
                    toolbar.setTitle("Đổi mật khẩu");
                    if (mCurrentFragment != FRAGMENT_DOIMK) {
                        replaceFragment(new DoiMatKhauFragment());
                        mCurrentFragment = FRAGMENT_DOIMK;
                    }
                }else if(id == R.id.mDangXuat) {
                    toolbar.setTitle("");
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Đăng xuất khỏi " + currentUserName + " ?");
                    builder.setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent1 = new Intent(MainActivity.this,DangNhap.class);
                            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent1);
                        }
                    });
                    builder.setNegativeButton("Hủy",null);
                    builder.show();
                }else{
                    toolbar.setTitle("Chưa làm đâu ae :((");
                    replaceFragment(new HomeFragment());
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

//        String role = sharedPreferences.getString("role","");
//        if(!role.equals("ad")){
//            Menu menu = navigationView.getMenu();
//            menu.findItem(R.id.).setVisible(false);
//            menu.findItem(R.id.).setVisible(false);
//            menu.findItem(R.id.).setVisible(false);
//        }

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else if(!drawerLayout.isDrawerVisible(GravityCompat.START) && mCurrentFragment != FRAGMENT_HOME){
            replaceFragment(new HomeFragment());
            toolbar.setTitle("Trang chủ");
            mCurrentFragment = FRAGMENT_HOME;
        } else if (!drawerLayout.isDrawerVisible(GravityCompat.START)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Thoát chương trình ?");
            builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setPositiveButton("Hủy",null);
            builder.show();
        }else {
            super.onBackPressed();
        }
    }

    public void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutMa,fragment);
        fragmentTransaction.commit();
    }
}