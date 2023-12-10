package com.haocg.ourduan1nhom8.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.haocg.ourduan1nhom8.R;
import com.haocg.ourduan1nhom8.model.Sach;

import java.io.File;
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
        holder.txtTenSach.setText("Tên Sách: "+list.get(position).getTenSach());
        holder.txtSoluongMua.setText("Số lượng mua: "+list.get(position).getSoLuongMua());
        //
        String imagePath = list.get(position).getAnhSach();
        // đặt ảnh vào image nè (thông qua Glide)
        if(imagePath != null && !imagePath.isEmpty()){
            Glide.with(holder.itemView.getContext())
//                    .load("file://" + imagePath)
                    .load(Uri.fromFile(new File(imagePath)))
                    .into(holder.imageViewAnhSach);
        }else{
            Glide.with(context).load(R.mipmap.avt).into(holder.imageViewAnhSach);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenSach,txtSoluongMua;
        ImageView imageViewAnhSach;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenSach = itemView.findViewById(R.id.txtTenSachTop10);
            txtSoluongMua = itemView.findViewById(R.id.txtSoLuongMuaTop10);
            imageViewAnhSach = itemView.findViewById(R.id.ivSachItemTop10);
        }

    }
}
