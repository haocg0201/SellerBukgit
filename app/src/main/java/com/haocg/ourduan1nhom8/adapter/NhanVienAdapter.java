package com.haocg.ourduan1nhom8.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.haocg.ourduan1nhom8.R;
import com.haocg.ourduan1nhom8.dao.NhanVienDAO;
import com.haocg.ourduan1nhom8.model.NhanVien;
import com.haocg.ourduan1nhom8.util.ItemNhanVienClick;

import java.io.File;
import java.util.ArrayList;

public class NhanVienAdapter extends RecyclerView.Adapter<NhanVienAdapter.ViewHolder> {
    private Context context;
    private ArrayList<NhanVien> list;
    AlertDialog dialog;
    private ItemNhanVienClick itemNhanVienClick;

    public NhanVienAdapter(Context context, ArrayList<NhanVien> list, ItemNhanVienClick itemNhanVienClick) {
        this.context = context;
        this.list = list;
        this.itemNhanVienClick = itemNhanVienClick;
    }

    public NhanVienAdapter(Context context, ArrayList<NhanVien> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_rc_nv,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NhanVien nv = list.get(holder.getAdapterPosition());
        holder.txtTenNV.setText("Tên nhân viên: " + nv.getHoTen());
        holder.txtEmail.setText("Email: " + nv.getEmail());
        holder.txtVaiTro.setText("Vai trò: " + ((nv.getVaiTro().equals("adssr"))?"Boss":(nv.getVaiTro().equals("ad"))?"Quản lý phụ":"Nhân Viên"));
        holder.txtNgayCapTK.setText("Ngày cấp: " + nv.getNgayCap());
        holder.txtLuong.setText("Mức lương: " + nv.getLuong());
        holder.txtTrangThaiTK.setText("Trạng thái: " + ((nv.getTrangThaiTK()==1)?"Đã kích hoạt":"Chưa kích hoạt"));
        if(nv.getTrangThaiTK()==0){
            holder.txtTrangThaiTK.setTextColor(Color.RED);
        }else {
            holder.txtTrangThaiTK.setTextColor(Color.parseColor("#5841E6"));
        }
        if(nv.getVaiTro().equals("adssr")){
            holder.txtVaiTro.setTextColor(Color.BLACK);
        } else if (nv.getVaiTro().equals("ad")) {
            holder.txtVaiTro.setTextColor(Color.GRAY);
        }
        if(nv.getTrangThaiTK()==0){
            holder.btnLockNV.setBackgroundColor(Color.parseColor("#71D3FD"));
            holder.btnLockNV.setText("Mở tài khoản");
        }else {
            holder.btnLockNV.setBackgroundColor(Color.RED);
            holder.btnLockNV.setText("Khoá tài khoản");
        }

        //
        String imagePath = nv.getAnhNV();
        // đặt ảnh vào image nè (thông qua Glide)
        if(imagePath != null && !imagePath.isEmpty()){
            Glide.with(holder.itemView.getContext())
//                    .load("file://" + imagePath)
                    .load(Uri.fromFile(new File(imagePath)))
                    .into(holder.ivAvatarNV);
        }else{
            Glide.with(context).load(R.mipmap.avt).into(holder.ivAvatarNV);
        }
        //

        holder.btnLockNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NhanVienDAO nvDAO = new NhanVienDAO(context);
                if(nv.getTrangThaiTK()==1){
                    nv.setTrangThaiTK(0);
                    nvDAO.updateNhanVien(nv);
                    list.clear();
                    list.addAll(nvDAO.getAllNhanVien());
                    notifyDataSetChanged();
                }else{
                    nv.setTrangThaiTK(1);
                    nvDAO.updateNhanVien(nv);
                    list.clear();
                    list.addAll(nvDAO.getAllNhanVien());
                    notifyDataSetChanged();
                }
            }
        });
        holder.ivEditNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemNhanVienClick != null){
                    itemNhanVienClick.onNhanVienClick(nv);
                }
            }
        });

        holder.ivDeleteNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa nhân viên");
                builder.setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NhanVienDAO nhanVienDAO = new NhanVienDAO(context);
                        if(nhanVienDAO.deleteNhanVien(nv.getTaiKhoan()) == 1){
                            list.clear();
                            list.addAll(nhanVienDAO.getAllNhanVien());
                            notifyDataSetChanged();
                            Toast.makeText(context, "Đã xóa nhân viên", Toast.LENGTH_SHORT).show();
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
        TextView txtTenNV, txtEmail, txtVaiTro, txtNgayCapTK,txtLuong, txtTrangThaiTK;
        ImageView ivAvatarNV, ivEditNhanVien, ivDeleteNhanVien, ivDisplayNhanVien;
        Button btnLockNV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenNV = itemView.findViewById(R.id.txtTenNhanVien);
            txtEmail = itemView.findViewById(R.id.txtEmailNhanVien);
            txtVaiTro = itemView.findViewById(R.id.txtRoleNhanVien);
            txtNgayCapTK = itemView.findViewById(R.id.txtNgayCapTKNV);
            txtLuong = itemView.findViewById(R.id.txtLuongNV);
            txtTrangThaiTK = itemView.findViewById(R.id.txtTrangThaiTKNV);
            ivAvatarNV = itemView.findViewById(R.id.imgNhanVien);
            ivEditNhanVien = itemView.findViewById(R.id.ivEditNhanVien);
            ivDeleteNhanVien = itemView.findViewById(R.id.ivDeleteNhanVien);
            ivDisplayNhanVien = itemView.findViewById(R.id.ivDisplayNhanVien);
            btnLockNV = itemView.findViewById(R.id.btnLockNhanVien);
        }
    }
}
