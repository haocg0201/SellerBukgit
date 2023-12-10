package com.haocg.ourduan1nhom8.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.haocg.ourduan1nhom8.R;
import com.haocg.ourduan1nhom8.adapter.TKKhoAdapter;
import com.haocg.ourduan1nhom8.dao.KhoDAO;
import com.haocg.ourduan1nhom8.model.Kho;

import java.util.ArrayList;

public class ThongKeSachTonKhoFragment extends Fragment {
    KhoDAO khoDAO;
    RecyclerView rcvTKKho;
    TKKhoAdapter tkKhoAdapter;
    ArrayList<Kho> list;
    Button btnNhieu, btnIt;
    String getByThis = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_ke_sach_ton_kho, container, false);
        khoDAO = new KhoDAO(getContext());
        rcvTKKho = view.findViewById(R.id.rcvDSKhoTKKho);
        btnNhieu = view.findViewById(R.id.btnTKKhoASC);
        btnIt = view.findViewById(R.id.btnTKKhoDESC);
        loadData("");
        btnNhieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData("DESC");
            }
        });

        btnIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData("ASC");
            }
        });
        return view;

    }

    public void loadData(String mew){
        if(mew == null || mew.isEmpty()){
            list = khoDAO.getAllKho();
        }else list = khoDAO.getAllKhoByIncAndDec(mew);
        tkKhoAdapter = new TKKhoAdapter(getContext(),list);
        rcvTKKho.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvTKKho.setAdapter(tkKhoAdapter);
    }
}