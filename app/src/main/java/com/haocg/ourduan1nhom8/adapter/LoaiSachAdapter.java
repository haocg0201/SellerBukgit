package com.haocg.ourduan1nhom8.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.haocg.ourduan1nhom8.R;
import com.haocg.ourduan1nhom8.dao.LoaiSachDAO;
import com.haocg.ourduan1nhom8.model.LoaiSach;
import com.haocg.ourduan1nhom8.util.ItemLSClick;

import java.util.ArrayList;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.ViewHolder> {
    private Context context;
    private ArrayList<LoaiSach> list;
    private ItemLSClick itemLSClick;

    public LoaiSachAdapter(Context context, ArrayList<LoaiSach> list, ItemLSClick itemLSClick) {
        this.context = context;
        this.list = list;
        this.itemLSClick = itemLSClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_rc_ls,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMaLoai.setText("Mã loại sách: " + String.valueOf(list.get(position).getMaLoaiSach()));
        holder.txtTenLoai.setText("Tên loại sách: " + list.get(position).getTenLoaiSach());
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa loại sách này ?");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoaiSachDAO lsDAO = new LoaiSachDAO(context);
                        int check = lsDAO.deleteLoaiSach(String.valueOf(list.get(holder.getAdapterPosition()).getMaLoaiSach()));
                        switch (check){
                            case 1:
                                list.clear();
                                list.addAll(lsDAO.getAllLoaiSach());
                                notifyDataSetChanged();
                                Toast.makeText(context,"Đã xóa",Toast.LENGTH_SHORT).show();
                                break;
                            case 0:
                                Toast.makeText(context,"Xóa thất bại",Toast.LENGTH_SHORT).show();
                                break;
                            case -1:
                                Toast.makeText(context,"Không thể xóa lúc này",Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }
                    }
                });
                builder.setNegativeButton("Hủy",null);
                builder.show();
            }
        });
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemLSClick.onItemClick(list.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtMaLoai,txtTenLoai;
        ImageView ivDelete,ivEdit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaLoai = itemView.findViewById(R.id.txtIdLS);
            txtTenLoai = itemView.findViewById(R.id.txtTenLS);
            ivDelete = itemView.findViewById(R.id.ivDeleteLS);
            ivEdit = itemView.findViewById(R.id.ivEditLS);
        }
    }
}
