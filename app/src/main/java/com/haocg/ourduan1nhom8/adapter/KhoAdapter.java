package com.haocg.ourduan1nhom8.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.haocg.ourduan1nhom8.R;
import com.haocg.ourduan1nhom8.model.Kho;
import com.haocg.ourduan1nhom8.util.ItemKhoClick;

import java.util.ArrayList;

public class KhoAdapter extends RecyclerView.Adapter<KhoAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Kho> list;
    private ItemKhoClick itemKhoClick;

    public KhoAdapter(Context context, ArrayList<Kho> list, ItemKhoClick itemKhoClick) {
        this.context = context;
        this.list = list;
        this.itemKhoClick = itemKhoClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_rc_kho,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Kho k = list.get(holder.getAdapterPosition());
        holder.txtViTri.setText("Vị trí sách: " + String.valueOf(k.getViTri()));
        holder.txtTenSach.setText("Tên sách: " + k.getTenSach());
        holder.txtSoLuong.setText("Số lượng: " + String.valueOf(k.getSoLuong()));
//        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemKhoClick.onItemClick(list.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtViTri,txtTenSach,txtSoLuong;
        ImageView ivDelete,ivEdit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtViTri = itemView.findViewById(R.id.txtIndexKho);
            txtTenSach = itemView.findViewById(R.id.txtTenSachKho);
            txtSoLuong = itemView.findViewById(R.id.txtSoLuongKho);
            ivDelete = itemView.findViewById(R.id.ivDeleteKho);
            ivEdit = itemView.findViewById(R.id.ivEditKho);
        }
    }
}
