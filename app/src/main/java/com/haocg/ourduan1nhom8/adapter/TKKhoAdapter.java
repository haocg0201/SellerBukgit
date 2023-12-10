package com.haocg.ourduan1nhom8.adapter;

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

import java.util.ArrayList;

public class TKKhoAdapter extends RecyclerView.Adapter<TKKhoAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Kho> list;

    public TKKhoAdapter(Context context, ArrayList<Kho> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rc_kho_tk,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Kho k = list.get(holder.getAdapterPosition());
        holder.txtViTri.setText("Vị trí sách: " + String.valueOf(k.getViTri()));
        holder.txtTenSach.setText("Tên sách: " + k.getTenSach());
        holder.txtSoLuong.setText("Số lượng: " + String.valueOf(k.getSoLuong()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtViTri,txtTenSach,txtSoLuong;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtViTri = itemView.findViewById(R.id.txtIndexKhoTKKho);
            txtTenSach = itemView.findViewById(R.id.txtTenSachKhoTKKho);
            txtSoLuong = itemView.findViewById(R.id.txtSoLuongKhoTKKHo);
        }
    }
}
