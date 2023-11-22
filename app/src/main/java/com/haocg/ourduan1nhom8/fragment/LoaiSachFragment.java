package com.haocg.ourduan1nhom8.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.haocg.ourduan1nhom8.R;
import com.haocg.ourduan1nhom8.adapter.LoaiSachAdapter;
import com.haocg.ourduan1nhom8.dao.LoaiSachDAO;
import com.haocg.ourduan1nhom8.model.LoaiSach;
import com.haocg.ourduan1nhom8.util.ItemLSClick;

import java.util.ArrayList;

public class LoaiSachFragment extends Fragment {
    RecyclerView rcvLS;
    LoaiSachDAO lsDAO;
    TextInputEditText edtTenLS;
    Button btnAddLS,btnUpdateLS;
    int index = -1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_loai_sach, container, false);
        rcvLS = view.findViewById(R.id.rcvDSLS);
        edtTenLS = view.findViewById(R.id.edtTenLS);
        btnAddLS = view.findViewById(R.id.btnAddLS);
        btnUpdateLS = view.findViewById(R.id.btnUpdateLS);
        lsDAO = new LoaiSachDAO(getContext());
        loadData();

        btnAddLS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenLS = edtTenLS.getText().toString();
                if(tenLS.equals("")){
                    Toast.makeText(getContext(),"Vui lòng điền đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                }else{
                    if(lsDAO.insertLoaiSach(new LoaiSach(tenLS))){
                        loadData();
                        edtTenLS.setText("");
                        Toast.makeText(getContext(),"Thêm loại sách thành công",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getContext(),"Thêm loại sách thành bại",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnUpdateLS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenLS = edtTenLS.getText().toString();
                if(tenLS.equals("")){
                    Toast.makeText(getContext(),"Vui lòng điền đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                }else{
                    if(lsDAO.updateLoaiSach(new LoaiSach(index,tenLS))){
                        loadData();
                        edtTenLS.setText("");
                        index = -1;
                        Toast.makeText(getContext(),"Cập nhật thông tin thành công",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getContext(),"Cập nhật thông tin thành bại",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return view;
    }
    public void loadData(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvLS.setLayoutManager(linearLayoutManager);
        ArrayList<LoaiSach> list = lsDAO.getAllLoaiSach();
        LoaiSachAdapter loaiSachAdapter = new LoaiSachAdapter(getContext(), list, new ItemLSClick() {
            @Override
            public void onItemClick(LoaiSach ls) {
                edtTenLS.setText(ls.getTenLoaiSach());
                index = ls.getMaLoaiSach();
            }
        });
        rcvLS.setAdapter(loaiSachAdapter);
    }
}