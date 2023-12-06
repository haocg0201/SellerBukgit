package com.haocg.ourduan1nhom8.fragment.sellerbuk;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.haocg.ourduan1nhom8.R;
import com.haocg.ourduan1nhom8.adapter.KeSachAdapter;
import com.haocg.ourduan1nhom8.adapter.MyAdapter;
import com.haocg.ourduan1nhom8.dao.LoaiSachDAO;
import com.haocg.ourduan1nhom8.dao.SachDAO;
import com.haocg.ourduan1nhom8.model.LoaiSach;
import com.haocg.ourduan1nhom8.model.Sach;
import com.haocg.ourduan1nhom8.util.ItemNavLSClick;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KeSachFragment extends Fragment {
    SachDAO sachDAO;
    LoaiSachDAO lsDAO;
    RecyclerView rcvDSSach;
    KeSachAdapter keSachAdapter;
    RecyclerView recyclerViewNavLS;
//    List<Sach> list;

    int affectCheck = 0;
    int maLoaiSachNow = -1;
    Toolbar toolbarSellerBukKeSach;
    EditText edtSearch;
    ImageView ivSearch;
    int checkSearch = -1;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ke_sach, container, false);
        rcvDSSach = view.findViewById(R.id.rcvDanhSachSachSellerBuk);
        recyclerViewNavLS = view.findViewById(R.id.rcvHorizontalLoaiSachSellerBuk);
        sachDAO = new SachDAO(getContext());
        lsDAO = new LoaiSachDAO(getContext());
        toolbarSellerBukKeSach = view.findViewById(R.id.toolbarSellerBukKeSach);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbarSellerBukKeSach);
        edtSearch = view.findViewById(R.id.editTextSearchSach);
        ivSearch = view.findViewById(R.id.imageButtonSearchSach);
        ImageView ivCart = view.findViewById(R.id.imageButtonCartSach);
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkSearch = 1;
                loadData();
            }
        });
        ivCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),CartActivity.class));
            }
        });
        loadDataFormNavLS();
        loadData();

        return view;
    }

    public void loadDataFormNavLS(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewNavLS.setLayoutManager(linearLayoutManager);
        MyAdapter adapter = new MyAdapter(getContext(), getDataLoaiSach(), new ItemNavLSClick() {
            @Override
            public void onItemNavLSClick(int maLoaiSach) {
                affectCheck = 1;
                maLoaiSachNow = maLoaiSach;
                System.out.println("==== mã loại sách: " + maLoaiSachNow + " ========= affect checker: " + affectCheck);
                loadData();
            }
        });
        recyclerViewNavLS.setAdapter(adapter);
    }
    public void loadData(){

        // ehehe tự tìm hiểu nhé
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;

        // chia cho chiều roojng mong muốn mỗi cột
        int numberOfColumns = (int) (dpWidth / 100);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),numberOfColumns);
        rcvDSSach.setLayoutManager(layoutManager);

        keSachAdapter = new KeSachAdapter(getContext(),getSach());
        rcvDSSach.setAdapter(keSachAdapter);
    }

    public ArrayList<HashMap<String, Object>> getDataLoaiSach(){
        ArrayList<HashMap<String, Object>> listDataLoaiSach = new ArrayList<>();
        ArrayList<LoaiSach> listLoaiSach = lsDAO.getAllLoaiSach();
        for(LoaiSach ls : listLoaiSach){
            HashMap<String, Object> hmTemp = new HashMap<>();
            hmTemp.put("maloaisach",ls.getMaLoaiSach());
            hmTemp.put("tenloai",ls.getTenLoaiSach());
            listDataLoaiSach.add(hmTemp);
        }
        return listDataLoaiSach;
    }

    public ArrayList<Sach> getSach(){
        ArrayList<Sach> list = new ArrayList<>();
        if(affectCheck != 0){
            list = sachDAO.getAllSachByMaLoaiSach(maLoaiSachNow);
        } else if (!edtSearch.getText().toString().isEmpty() && checkSearch == 1) {
            list = sachDAO.searchSach(edtSearch.getText().toString());
        } else list = sachDAO.getAllSach();
        return list;
    }
}