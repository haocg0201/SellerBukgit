package com.haocg.ourduan1nhom8.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.haocg.ourduan1nhom8.R;
import com.haocg.ourduan1nhom8.adapter.HoaDonChiTietAdapter;
import com.haocg.ourduan1nhom8.dao.HoaDonChiTietDAO;
import com.haocg.ourduan1nhom8.model.HoaDonChiTiet;

import java.util.ArrayList;

public class HoaDonChiTietDialogFragment extends DialogFragment {
    RecyclerView rcvHDCT;
    HoaDonChiTietDAO hoaDonChiTietDAO;
    private HoaDonChiTietAdapter hoaDonChiTietAdapter;

    public static HoaDonChiTietDialogFragment newInstance(int maHoaDon){
        HoaDonChiTietDialogFragment hoaDonChiTietFragment = new HoaDonChiTietDialogFragment();

        // ừm xem thấy bảo truyền id vào thông qua Bundle , thử xem sao
        Bundle args = new Bundle();
        args.putInt("ID",maHoaDon);
        hoaDonChiTietFragment.setArguments(args);

        return hoaDonChiTietFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hoa_don_chi_tiet, container, false);
        rcvHDCT = view.findViewById(R.id.rcvHDCT);
        hoaDonChiTietDAO = new HoaDonChiTietDAO(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvHDCT.setLayoutManager(linearLayoutManager);
        ArrayList<HoaDonChiTiet> list = hoaDonChiTietDAO.getAllHoaDonChiTietByMaHoaDon(String.valueOf(getArguments().getInt("ID")));
        hoaDonChiTietAdapter = new HoaDonChiTietAdapter(getContext(),list);
        rcvHDCT.setAdapter(hoaDonChiTietAdapter);
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Set layout cho dialog
        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        // may quá lụm được trên mạng )
        return dialog;
    }
}