package com.haocg.ourduan1nhom8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.haocg.ourduan1nhom8.dao.SachDAO;
import com.haocg.ourduan1nhom8.fragment.sellerbuk.AddlIn4onFragment;
import com.haocg.ourduan1nhom8.fragment.sellerbuk.KeSachFragment;

public class SellerBukMainActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;

    private static final int FRAGMENT_SELLERBUK =  0;
    private static final int FRAGMENT_MORE =  1;
    private int mCurrentFragment = FRAGMENT_SELLERBUK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_buk_main);
        frameLayout = findViewById(R.id.frameLayoutSellerBuk);
        bottomNavigationView = findViewById(R.id.bottomNavSellerBuk);
        replaceFrament(new KeSachFragment());

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.navigation_home){
                    if(mCurrentFragment != FRAGMENT_SELLERBUK){
                        replaceFrament(new KeSachFragment());
                        mCurrentFragment = FRAGMENT_SELLERBUK;
                    }
                } else if (item.getItemId() == R.id.navigation_more) {
                    if(mCurrentFragment != FRAGMENT_MORE){
                        replaceFrament(new AddlIn4onFragment());
                        mCurrentFragment = FRAGMENT_MORE;
                    }

                }else {
                    return true;
                }
                return true;
            }
        });
    }
    public void replaceFrament(Fragment fragment){
        if(fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayoutSellerBuk,fragment)
                    .commit();
        }
    }
}