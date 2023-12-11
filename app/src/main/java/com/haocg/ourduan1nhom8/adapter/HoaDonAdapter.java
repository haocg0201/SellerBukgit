package com.haocg.ourduan1nhom8.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.haocg.ourduan1nhom8.R;
import com.haocg.ourduan1nhom8.dao.HoaDonChiTietDAO;
import com.haocg.ourduan1nhom8.dao.HoaDonDAO;
import com.haocg.ourduan1nhom8.dao.SachDAO;
import com.haocg.ourduan1nhom8.fragment.HoaDonChiTietDialogFragment;
import com.haocg.ourduan1nhom8.model.HoaDon;
import com.haocg.ourduan1nhom8.model.HoaDonChiTiet;
import com.haocg.ourduan1nhom8.model.Sach;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.ViewHolder> {
    private Context context;
    private ArrayList<HoaDon> list;
    private AlertDialog dialog;
    private ArrayList<HoaDonChiTiet> listHDCT;
    private HoaDonChiTietDAO hoaDonChiTietDAO;
    private SachDAO sachDAO;

    public HoaDonAdapter(Context context, ArrayList<HoaDon> list) {
        this.context = context;
        this.list = list;
        hoaDonChiTietDAO = new HoaDonChiTietDAO(context);
        sachDAO = new SachDAO(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_rc_hd,parent,false);
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
        if(h.getTrangThaiDonHang()==1){
            holder.btnDoiTrangThaiHoaDon.setVisibility(View.GONE);
            holder.btnHuyHoaDon.setVisibility(View.GONE);
        } else if (h.getTrangThaiDonHang()==2) {
            holder.btnDoiTrangThaiHoaDon.setVisibility(View.GONE);
            holder.btnHuyHoaDon.setVisibility(View.GONE);
            holder.ivEdit.setVisibility(View.GONE);
        }else {
            holder.btnDoiTrangThaiHoaDon.setVisibility(View.VISIBLE);
            holder.btnHuyHoaDon.setVisibility(View.VISIBLE);
            holder.ivEdit.setVisibility(View.VISIBLE);
        }
        // 0 : chờ thanh toán
        // 1 : đã thanh toán
        // 2 : đã hủy
        holder.btnDoiTrangThaiHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HoaDonDAO hoaDonDAO = new HoaDonDAO(context);
                if(hoaDonDAO.updateHoaDonByMaHoaDon(h.getMaHoaDon(),1)){
                    listHDCT = hoaDonChiTietDAO.getAllHoaDonChiTietByMaHoaDon(String.valueOf(h.getMaHoaDon()));
                    for (HoaDonChiTiet h : listHDCT){
                        Sach s = sachDAO.getSachAndSoLuongBayBanByMaSach(String.valueOf(h.getMaSach()));
                        s.setSoLuongBayBan(s.getSoLuongBayBan()-h.getSoLuong());
                        sachDAO.updateSoLuongSach(s);
                    }
                    list.clear();
                    list.addAll(hoaDonDAO.getAllHoaDonModified());
                    notifyDataSetChanged();
                    showResult("Đã đổi trạng thái thành thanh toán");
                }else showResult("Thật diệu kì, lại lỗi gì nữa đây huhu");
            }
        });

        holder.btnHuyHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HoaDonDAO hoaDonDAO = new HoaDonDAO(context);
                if(hoaDonDAO.updateHoaDonByMaHoaDon(h.getMaHoaDon(),2)){
                    list.clear();
                    list.addAll(hoaDonDAO.getAllHoaDonModified());
                    notifyDataSetChanged();
                    showResult("Đã hủy hóa đơn");
                }else showResult("Thật diệu kì, lại lỗi gì nữa đây huhu");
            }
        });
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Bạn có chắc muốn xóa hóa đơn này không ?" +
                        " (những hóa đơn chi tiết có liên quan cũng sẽ đi tong cùng hóa đon này)");
                builder.setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HoaDonDAO hoaDonDAO = new HoaDonDAO(context);

                        int chechHD = hoaDonDAO.deleteHoaDon((list.get(holder.getAdapterPosition()).getMaHoaDon()+""));
                        if(chechHD == 1){
                            list.clear();
                            list.addAll(hoaDonDAO.getAllHoaDon());
                            notifyDataSetChanged();
                            Toast.makeText(context,"Đã xóa thành công",Toast.LENGTH_SHORT).show();
                        }else Toast.makeText(context,"Đã xóa thành bại, liên hệ để hoàn tiền :(( ",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setPositiveButton("Hủy",null);
                builder.show();
            }
        });

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(list.get(holder.getAdapterPosition()));
            }
        });
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

    private void showResult(String someText){
        Toast.makeText(context,someText,Toast.LENGTH_SHORT).show();
    }

    private void showDialog (HoaDon hd){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.layout_dialog_sua_hd,null);
        EditText edtTenNguoiMua = view.findViewById(R.id.edtTenNguoiMuaHoaDon);
        EditText edtNgayLapHoaDon = view.findViewById(R.id.edtNgayLapHoaDon);
        Button btnLuuHD = view.findViewById(R.id.btnLuuHoaDon);
        Button btnHuyLuuHD = view.findViewById(R.id.btnHuyLuuHoaDon);

        edtNgayLapHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edtNgayLapHoaDon.setText(String.format("%d/%d/%d",dayOfMonth,month+1,year));
                    }
                },year,month,dayOfMonth);
                dialog.show();
            }
        });

        btnLuuHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HoaDonDAO hoaDonDAO = new HoaDonDAO(context);
                hd.setTenKhachHang(edtTenNguoiMua.getText().toString());
                hd.setNgayLap(edtNgayLapHoaDon.getText().toString());
                if(edtTenNguoiMua.getText().toString().equals("")){
                    Toast.makeText(context,"Chưa nhập thông tin người mua",Toast.LENGTH_SHORT).show();
                }else if(hoaDonDAO.updateHoaDon(hd)){
                    list.clear();
                    list.addAll(hoaDonDAO.getAllHoaDon());
                    notifyDataSetChanged();
                    dialog.dismiss();
                    Toast.makeText(context,"Đã cập nhật hóa đơn",Toast.LENGTH_SHORT).show();
                }else Toast.makeText(context,"Đã cập nhật hóa đơn nhưng thất bại, liên hệ không ai cả để được hoàn tiền",Toast.LENGTH_SHORT).show();
            }
        });

        btnHuyLuuHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtMaHD, txtMaNV, txtTenKhachMua, txtNgayLap, txtTongTien, txtTrangThaiDonHang;
        ImageView ivDelete,ivEdit, ivDisplayHDCT;
        Button btnDoiTrangThaiHoaDon,btnHuyHoaDon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaHD = itemView.findViewById(R.id.txtIndexHD);
            txtMaNV = itemView.findViewById(R.id.txtMaNVHD);
            txtTenKhachMua = itemView.findViewById(R.id.txtTenKhachHangHD);
            txtNgayLap = itemView.findViewById(R.id.txtNgayLapHD);
            txtTongTien = itemView.findViewById(R.id.txtTongTienHD);
            txtTrangThaiDonHang = itemView.findViewById(R.id.txtTrangThaiDonHangHD);
            ivDelete = itemView.findViewById(R.id.ivDeleteHoaDon);
            ivEdit = itemView.findViewById(R.id.ivEditHoaDon);
            ivDisplayHDCT = itemView.findViewById(R.id.ivDisplayHDCT);
            btnDoiTrangThaiHoaDon = itemView.findViewById(R.id.btnChangeHDStatus);
            btnHuyHoaDon = itemView.findViewById(R.id.btnChangeHDStatusToHuy);
        }
    }
}
