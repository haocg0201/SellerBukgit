package com.haocg.ourduan1nhom8.fragment;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haocg.ourduan1nhom8.R;
import com.haocg.ourduan1nhom8.adapter.HoaDonChiTietAdapter;
import com.haocg.ourduan1nhom8.dao.HoaDonChiTietDAO;
import com.haocg.ourduan1nhom8.model.HoaDonChiTiet;

import java.util.ArrayList;

public class HoaDonChiTietFragment extends DialogFragment {
    RecyclerView rcvHDCT;
    HoaDonChiTietDAO hoaDonChiTietDAO;
    private HoaDonChiTietAdapter hoaDonChiTietAdapter;

//    public static HoaDonChiTietFragment newInstance(int maHoaDon){
//        HoaDonChiTietFragment hoaDonChiTietFragment = new HoaDonChiTietFragment();
//
//        // ừm xem thấy bảo truyền id vào thông qua Bundle , thử xem sao
//        Bundle args = new Bundle();
//        args.putInt("ID",maHoaDon);
//        hoaDonChiTietFragment.setArguments(args);
//
//        return hoaDonChiTietFragment;
//    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hoa_don_chi_tiet, container, false);
        rcvHDCT = view.findViewById(R.id.rcvHDCT);
        hoaDonChiTietDAO = new HoaDonChiTietDAO(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvHDCT.setLayoutManager(linearLayoutManager);
        ArrayList<HoaDonChiTiet> list = hoaDonChiTietDAO.getAllHoaDonChiTietModified();
        hoaDonChiTietAdapter = new HoaDonChiTietAdapter(getContext(),list);
        rcvHDCT.setAdapter(hoaDonChiTietAdapter);
        return view;
    }
}