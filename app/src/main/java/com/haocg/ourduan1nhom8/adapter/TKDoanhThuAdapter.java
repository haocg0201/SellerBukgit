package com.haocg.ourduan1nhom8.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.haocg.ourduan1nhom8.R;
import com.haocg.ourduan1nhom8.fragment.HoaDonChiTietDialogFragment;
import com.haocg.ourduan1nhom8.model.HoaDon;

import java.util.ArrayList;

public class TKDoanhThuAdapter extends RecyclerView.Adapter<TKDoanhThuAdapter.ViewHolder> {
    private Context context;
    private ArrayList<HoaDon> list;

    public TKDoanhThuAdapter(Context context, ArrayList<HoaDon> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rc_tk_dt,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HoaDon h = list.get(holder.getAdapterPosition());
        holder.txtMaHD.setText("Mã hóa đơn: " + String.valueOf(h.getMaHoaDon()));
        holder.txtMaNV.setText("Nhân viên đứng bán: " + h.getTenNV());
        holder.txtTenKhachMua.setText("Khách mua: " + h.getTenKhachHang());
        holder.txtNgayLap.setText("Ngày lập hóa đơn: " + h.getNgayLap());
        holder.txtTongTien.setText("Tổng tiền mua hàng: " + h.getTongTien());
        String trangThai = h.getTrangThaiDonHang()==1?"Đã thanh toán":h.getTrangThaiDonHang()==2?"Đã hủy":"Chờ thanh toán";
        holder.txtTrangThaiDonHang.setText("Trạng thái đơn hàng: " + trangThai);
        holder.txtTrangThaiDonHang.setTextColor(h.getTrangThaiDonHang()==1? Color.BLUE:h.getTrangThaiDonHang()==2?Color.RED:Color.BLACK);

        holder.ivDisplayHDCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int maHoaDon = h.getMaHoaDon();
                showHoaDonChiTietDialog(maHoaDon);
            }
        });
    }

    private void showHoaDonChiTietDialog(int maHoaDon){
        FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
        HoaDonChiTietDialogFragment hoaDonChiTietFragment = HoaDonChiTietDialogFragment.newInstance(maHoaDon);
        hoaDonChiTietFragment.show(fragmentManager,"HoaDonChiTietFragment");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtMaHD, txtMaNV, txtTenKhachMua, txtNgayLap, txtTongTien, txtTrangThaiDonHang;
        ImageView ivDisplayHDCT;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaHD = itemView.findViewById(R.id.txtIndexHDTKDT);
            txtMaNV = itemView.findViewById(R.id.txtMaNVHDTKDT);
            txtTenKhachMua = itemView.findViewById(R.id.txtTenKhachHangHDTKDT);
            txtNgayLap = itemView.findViewById(R.id.txtNgayLapHDTKDT);
            txtTongTien = itemView.findViewById(R.id.txtTongTienHDTKDT);
            txtTrangThaiDonHang = itemView.findViewById(R.id.txtTrangThaiDonHangHDTKDT);
            ivDisplayHDCT = itemView.findViewById(R.id.ivTKDTDisplayHDCT);
        }
    }

}
