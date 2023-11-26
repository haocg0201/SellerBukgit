package com.haocg.ourduan1nhom8.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haocg.ourduan1nhom8.R;
import com.haocg.ourduan1nhom8.adapter.HoaDonAdapter;
import com.haocg.ourduan1nhom8.dao.HoaDonDAO;
import com.haocg.ourduan1nhom8.model.HoaDon;

import java.util.ArrayList;

public class HoaDonFragment extends Fragment {
    RecyclerView rcvHD;
    HoaDonDAO hdDAO;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hoa_don, container, false);
        hdDAO = new HoaDonDAO(getContext());
        rcvHD = view.findViewById(R.id.rcvDSHD);
        loadData();

        return view;
    }

    public void loadData(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvHD.setLayoutManager(linearLayoutManager);
        ArrayList<HoaDon> list = hdDAO.getAllHoaDonModified();
        HoaDonAdapter hoaDonAdapter = new HoaDonAdapter(getContext(),list);
        rcvHD.setAdapter(hoaDonAdapter);
    }
}