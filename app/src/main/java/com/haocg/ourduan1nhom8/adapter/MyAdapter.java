package com.haocg.ourduan1nhom8.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.haocg.ourduan1nhom8.R;
import com.haocg.ourduan1nhom8.util.ItemNavLSClick;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private List<HashMap<String,Object>> dataList;
    private int selectedItem = -1;
    private ItemNavLSClick itemNavLSClick;

    public MyAdapter(Context context, List<HashMap<String, Object>> dataList, ItemNavLSClick itemNavLSClick) {
        this.context = context;
        this.dataList = dataList;
        this.itemNavLSClick = itemNavLSClick;
    }

    public MyAdapter(Context context, ArrayList<HashMap<String,Object>> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rc_ls_search , parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        HashMap<String, Object> item = dataList.get(position);
        holder.textView.setText((String) item.get("tenloai"));
        holder.textView.setTextColor(selectedItem == position ? Color.BLUE : Color.GRAY);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textViewLsSearch);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int previousItem = selectedItem;
                    selectedItem = getAdapterPosition();

                    // Cập nhật giao diện người dùng nè
                    notifyItemChanged(previousItem);
                    notifyItemChanged(selectedItem);
                    HashMap<String, Object> item = dataList.get(selectedItem);
                    if (itemNavLSClick != null) {
                        itemNavLSClick.onItemNavLSClick((int) item.get("maloaisach"));
                    }
                    // Xử lý khi mục được chọn // ...
                }
            });
        }
    }
}
