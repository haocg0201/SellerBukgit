package com.haocg.ourduan1nhom8.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haocg.ourduan1nhom8.R;
import com.haocg.ourduan1nhom8.adapter.Top10Adapter;
import com.haocg.ourduan1nhom8.dao.SachDAO;
import com.haocg.ourduan1nhom8.model.Sach;

import java.util.ArrayList;

public class ThongKeTopDauSachFragment extends Fragment {
    SachDAO sachDAO;
    RecyclerView rcvTopSach;
    ArrayList<Sach> list;
    Top10Adapter top10Adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thong_ke_top_dau_sach, container, false);
        rcvTopSach = view.findViewById(R.id.rcvTopSach);

        sachDAO = new SachDAO(getContext());
        list = sachDAO.getAllSachBanChay();
        for (Sach s: list) {
            System.out.println("Ten sach: " + s.getTenSach() + " - so luong mua: " + s.getSoLuongMua() + " - anh sach: " + s.getAnhSach());
        }
        top10Adapter = new Top10Adapter(getContext(),list);
        rcvTopSach.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvTopSach.setAdapter(top10Adapter);

        return view;
    }
}