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
import com.haocg.ourduan1nhom8.model.Sach;

import java.util.ArrayList;

public class Top10Adapter extends RecyclerView.Adapter<Top10Adapter.ViewHolder>{
    private Context context;
    private ArrayList<Sach> list;

    public Top10Adapter(Context context, ArrayList<Sach> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_topten,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMaSach.setText("Mã Sách: "+list.get(position).getMaSach());
        holder.txtTenSach.setText("Tên Sách: "+list.get(position).getTenSach());
        holder.txtSoluong.setText("Tên Tác giả: "+list.get(position).getTenTacGia());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMaSach,txtTenSach,txtSoluong;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaSach = itemView.findViewById(R.id.txtMaSachT10);
            txtTenSach = itemView.findViewById(R.id.txtTenSachT10);
            txtSoluong = itemView.findViewById(R.id.txtSoLuongMuon);
        }

    }
}
