package com.haocg.ourduan1nhom8.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.haocg.ourduan1nhom8.R;
import com.haocg.ourduan1nhom8.model.HoaDonChiTiet;

import java.util.ArrayList;

public class HoaDonChiTietAdapter extends RecyclerView.Adapter<HoaDonChiTietAdapter.ViewHolder> {
    private Context context;
    ArrayList<HoaDonChiTiet> list;

    public HoaDonChiTietAdapter(Context context, ArrayList<HoaDonChiTiet> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_rc_hdct,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HoaDonChiTiet h = list.get(holder.getAdapterPosition());
        holder.txtMaHDCT.setText("Mã hóa đơn chi tiết: " + h.getMaHDCT());
        holder.txtTenSach.setText("Tên sách: " + h.getTenSach());
        holder.txtMaHD.setText("Mã hóa đơn: " + h.getMaHoaDon());
        holder.txtSoLuong.setText("Số lượng mua: " + h.getSoLuong());
        holder.txtGiaTien.setText("Giá tiền: " + h.getGiaTien());
        holder.txtThanhTien.setText("Thành tiền: " + h.getThanhTien());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtMaHDCT, txtTenSach, txtMaHD, txtSoLuong, txtGiaTien, txtGiamGia, txtThanhTien;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaHDCT = itemView.findViewById(R.id.txtIndexHDCT);
            txtTenSach = itemView.findViewById(R.id.txtTenSachHDCT);
            txtMaHD = itemView.findViewById(R.id.txtMaHDHDCT);
            txtSoLuong = itemView.findViewById(R.id.txtSoLuongHDCT);
            txtGiaTien = itemView.findViewById(R.id.txtGiaTienHDCT);
            txtGiamGia = itemView.findViewById(R.id.txtGiamGiaHDCT);
            txtThanhTien = itemView.findViewById(R.id.txtThanhTienHDCT);
        }
    }
}
