package com.haocg.ourduan1nhom8.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.haocg.ourduan1nhom8.R;
import com.haocg.ourduan1nhom8.dao.KhoDAO;
import com.haocg.ourduan1nhom8.dao.NhanVienDAO;
import com.haocg.ourduan1nhom8.dao.SachDAO;
import com.haocg.ourduan1nhom8.model.Sach;
import com.haocg.ourduan1nhom8.util.ItemSachClick;

import java.io.File;
import java.util.ArrayList;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Sach> list;
    private ItemSachClick itemSachClick;

    public SachAdapter(Context context, ArrayList<Sach> list, ItemSachClick itemSachClick) {
        this.context = context;
        this.list = list;
        this.itemSachClick = itemSachClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item_rc_sach,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Sach s = list.get(position);
        holder.txtTenSach.setText("Tên sách: " + s.getTenSach());
        holder.txtLoaiSach.setText("Loại sách: " + s.getTenLoai());
        holder.txtTenTG.setText("Tên tác giả: " + s.getTenTacGia());
        holder.txtGiaMua.setText("Giá mua: " + s.getGiaMua());
        holder.txtGiaMua.setTextColor(Color.RED);
        holder.txtGiaBan.setText("Giá bán: " + s.getGiaBan());
        holder.txtGiaBan.setTextColor(Color.BLACK);
        holder.txtLanTaiBan.setText("Tái bản lần thứ: " + s.getLanTaiBan());
        holder.txtTenNhaSanXuat.setText("Nhà sản xuất: " + s.getTenNhaSanXuat());
        holder.txtNamSanXuat.setText("Năm sản xuất: " + s.getNamSanXuat());
        holder.txtViTriQuayHang.setText("Vị trí quầy bán: " + s.getViTriQuayHang());
        holder.txtSoLuongBayBan.setText((s.getSoLuongBayBan()<=0)?"Hàng đã hết, vào kho nhập thêm":"Số lượng bày bán:" + s.getSoLuongBayBan());
        holder.txtSoLuongBayBan.setTextColor(Color.BLACK);
        String imagePath = s.getAnhSach();
        // đặt ảnh vào image nè (thông qua Glide)
        if(imagePath != null && !imagePath.isEmpty()){
            Glide.with(holder.itemView.getContext())
//                    .load("file://" + imagePath) cũng là 1 cách lấy ảnh nhưng không tuyệt đối đc như dưới
                    .load(Uri.fromFile(new File(imagePath)))
                    .into(holder.ivSachImage);
        }else{
            Glide.with(context).load(R.mipmap.avt).into(holder.ivSachImage);
        }

        holder.ivEditSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemSachClick.onItemClick(s);
            }
        });

        holder.ivDeleteSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa Sách");
                builder.setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SachDAO sachDAO = new SachDAO(context);
                        KhoDAO khoDAO = new KhoDAO(context);
                        if(sachDAO.deleteSach(s.getMaSach()) && khoDAO.deleteKhoByMaSach(s.getMaSach())){
                            list.clear();
                            list.addAll(sachDAO.getAllSach());
                            notifyDataSetChanged();
                            Toast.makeText(context, "Đã xóa sách", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, "Xóa thất bại, lỗi xem file log và không hoàn tiền", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setPositiveButton("Hủy",null);
                builder.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTenSach, txtLoaiSach, txtTenTG, txtGiaMua, txtGiaBan, txtLanTaiBan, txtTenNhaSanXuat, txtNamSanXuat, txtViTriQuayHang, txtSoLuongBayBan;
        ImageView ivSachImage, ivEditSach, ivDeleteSach, ivDisplaySach;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenSach = itemView.findViewById(R.id.txtTenSachItemSach);
            txtLoaiSach = itemView.findViewById(R.id.txtLoaiSachItemSach);
            txtTenTG = itemView.findViewById(R.id.txtTenTGItemSach);
            txtGiaMua = itemView.findViewById(R.id.txtGiaMuaItemSach);
            txtGiaBan = itemView.findViewById(R.id.txtGiaBanItemSach);
            txtLanTaiBan = itemView.findViewById(R.id.txtLanTaiBanItemSach);
            txtTenNhaSanXuat = itemView.findViewById(R.id.txtTenNhaSanXuatItemSach);
            txtNamSanXuat = itemView.findViewById(R.id.txtNamSanXuatItemSach);
            txtViTriQuayHang = itemView.findViewById(R.id.txtViTriQuayHangItemSach);
            txtSoLuongBayBan = itemView.findViewById(R.id.txtSoLuongBayBanItemSach);
            ivSachImage = itemView.findViewById(R.id.imgSachItemSach);
            ivEditSach = itemView.findViewById(R.id.ivEditSachItemSach);
            ivDeleteSach = itemView.findViewById(R.id.ivDeleteSachItemSach);
            ivDisplaySach = itemView.findViewById(R.id.ivDisplaySachItemSach);
        }
    }
}
