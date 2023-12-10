package com.haocg.ourduan1nhom8.fragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.haocg.ourduan1nhom8.R;
import com.haocg.ourduan1nhom8.adapter.TKDoanhThuAdapter;
import com.haocg.ourduan1nhom8.dao.HoaDonDAO;
import com.haocg.ourduan1nhom8.model.HoaDon;

import java.util.ArrayList;
import java.util.Calendar;

public class ThongKeDoanhThuFragment extends Fragment {
    HoaDonDAO hoaDonDAO;
    RecyclerView recyclerViewTKDT;
    EditText edtFromDate, edtToDate;
    Button btnThongKe;
    TextView txtThongKeResult;
    ArrayList<HoaDon> list;
    TKDoanhThuAdapter tkDoanhThuAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =   inflater.inflate(R.layout.fragment_thong_ke_doanh_thu, container, false);
        recyclerViewTKDT = view.findViewById(R.id.rcvThongKeDisplayHoaDon);
        edtFromDate = view.findViewById(R.id.edtfromDate);
        edtToDate = view.findViewById(R.id.edtToDate);
        btnThongKe = view.findViewById(R.id.btnThongKeDoanhThu);
        txtThongKeResult = view.findViewById(R.id.txtThongKeResult);
        hoaDonDAO = new HoaDonDAO(getContext());
        edtFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalender(edtFromDate);
            }
        });

        edtToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalender(edtToDate);
            }
        });

        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fromDate = edtFromDate.getText().toString();
                String toDate = edtToDate.getText().toString();
                if(!fromDate.equals("") && !toDate.equals("")){
                    loadData(fromDate,toDate);
                    int total = 0;
                    for (HoaDon h : list){
                        System.out.println("hóa đơn số: " + h.getMaHoaDon() + "\n" + "Tổng tiền: " + h.getTongTien());
                        total+=h.getTongTien();
                    }
                    txtThongKeResult.setText("Tổng doanh thu: " + total);
                    txtThongKeResult.setTextColor(Color.RED);
                }else Toast.makeText(getContext(), "Đừng vội, nhập thời gian đã :(", Toast.LENGTH_SHORT).show();
                
            }
        });
        return view;

    }

    public void loadData(String fromDate, String toDate){
        list = hoaDonDAO.getHoaDonByTimeRange(fromDate,toDate);
//        list = hoaDonDAO.getAllHoaDon();
        recyclerViewTKDT.setLayoutManager(new LinearLayoutManager(getContext()));
        tkDoanhThuAdapter = new TKDoanhThuAdapter(getContext(),list);
        recyclerViewTKDT.setAdapter(tkDoanhThuAdapter);
    }

    public void showCalender(EditText editText){
        Calendar calendar = Calendar.getInstance();
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                editText.setText(String.format("%d/%d/%d",dayOfMonth,month+1,year));
            }
        },year,month,dayOfMonth);
        dialog.show();
    }
}