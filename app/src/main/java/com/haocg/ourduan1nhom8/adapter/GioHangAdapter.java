package com.haocg.ourduan1nhom8.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.haocg.ourduan1nhom8.R;
import com.haocg.ourduan1nhom8.ThanhToanActivity;
import com.haocg.ourduan1nhom8.dao.GioHangDAO;
import com.haocg.ourduan1nhom8.dao.HoaDonDAO;
import com.haocg.ourduan1nhom8.fragment.sellerbuk.CartActivity;
import com.haocg.ourduan1nhom8.model.GioHang;
import com.haocg.ourduan1nhom8.model.HoaDon;
import com.haocg.ourduan1nhom8.model.HoaDonChiTiet;
import com.haocg.ourduan1nhom8.util.ItemCartSelected;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.ViewHolder> {
    private List<Integer> listIdCart = new ArrayList<>();
    private Context context;
    private List<GioHang> itemList;
    private GioHangDAO ghDAO;
    private ItemCartSelected itemCartSelected;
    AlertDialog dialog;
    SharedPreferences sharedPreferences;

    public GioHangAdapter(Context context, List<GioHang> itemList) {
        this.context = context;
        this.itemList = itemList;
        ghDAO = new GioHangDAO(context);
        sharedPreferences =context.getSharedPreferences("THONGTIN",Context.MODE_PRIVATE);
    }

    public GioHangAdapter(Context context, List<GioHang> itemList, ItemCartSelected itemCartSelected) {
        this.context = context;
        this.itemList = itemList;
        ghDAO = new GioHangDAO(context);
        this.itemCartSelected = itemCartSelected;
        sharedPreferences =context.getSharedPreferences("THONGTIN",Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rc_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        GioHang currentItem = itemList.get(position);
        holder.txtTenSanPham.setText(currentItem.getTenSach()+"");
        holder.txtGiaSanPham.setText("Giá bìa: " + String.valueOf(currentItem.getGia()));
        holder.txtSoLuong.setText(currentItem.getSoLuong()+"");
        holder.txtTongTien.setText(currentItem.getTongTien()+"");
        holder.txtGiamSoLuong.setText("<");
        holder.txtTangSoLuong.setText(">");

        String imagePath = currentItem.getAnhSanPham();
        if(imagePath != null && !imagePath.isEmpty()){
            Glide.with(holder.itemView.getContext())
//                    .load("file://" + imagePath)
                    .load(Uri.fromFile(new File(imagePath)))
                    .into(holder.imageAnhSach);
        }else{
            Glide.with(context).load(R.mipmap.avt).into(holder.imageAnhSach);
        }

        holder.txtGiamSoLuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sl =  Integer.parseInt(holder.txtSoLuong.getText().toString()) - 1;
                if(sl < 1){
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Xoá sản phẩm");
                    builder.setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                           if(ghDAO.deleteGioHang(currentItem.getMaGioHang()+"") == 1){
                               reload();
                               Toast.makeText(context, "Đã loại bỏ", Toast.LENGTH_SHORT).show();
                           }
                        }
                    });
                    builder.setPositiveButton("Huỷ",null);
                    builder.show();
                }else {
                    currentItem.setSoLuong(sl);
                    int tt = currentItem.getGia() * sl;
                    currentItem.setTongTien(tt);
                    ghDAO.updateGioHang(currentItem);
                    reload();
                }
            }
        });

        holder.txtTangSoLuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sl =  Integer.parseInt(holder.txtSoLuong.getText().toString()) + 1;
                currentItem.setSoLuong(sl);
                int tt = currentItem.getGia() * sl;
                currentItem.setTongTien(tt);
                ghDAO.updateGioHang(currentItem);
                reload();
                }
        });

        holder.imageDeleteItemCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Loại bỏ sản phẩm này?");
                builder.setNegativeButton("Loại bỏ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(ghDAO.deleteGioHang(currentItem.getMaGioHang()+"") == 1){
                            reload();
                            Toast.makeText(context, "Đã loại bỏ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setPositiveButton("Huỷ",null);
                builder.show();
            }
        });
        holder.chkMuaNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.chkMuaNgay.isChecked()){
                    listIdCart.add(currentItem.getMaGioHang());
                } else {
                    listIdCart.remove(Integer.valueOf(currentItem.getMaGioHang()));
                }
                itemCartSelected.onChkItemCartSelected(listIdCart);
            }
        });

        holder.chkMuaNgay.setChecked(isAllChecked);

        holder.btnMuaNgayItemCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!listIdCart.isEmpty()){
                    Intent intent = new Intent(context, ThanhToanActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putIntegerArrayList("listCart",(ArrayList<Integer>) listIdCart);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }else Toast.makeText(context, "Chưa có sản phẩm được chọn", Toast.LENGTH_SHORT).show();
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                View view = LayoutInflater.from(context).inflate(R.layout.layout_dialog_them_ttkh,null);
//
//                @SuppressLint({"MissingInflatedId", "LocalSuppress"})
//                EditText edtTenNguoiMua = view.findViewById(R.id.edtTenNguoiMuaItemCart);
//                @SuppressLint({"MissingInflatedId", "LocalSuppress"})
//                Button btnXNMua = view.findViewById(R.id.btnXacNhanMuaItemCart);
//                @SuppressLint({"MissingInflatedId", "LocalSuppress"})
//                Button btnHuy = view.findViewById(R.id.btnHuyItemCart);
//
//                btnXNMua.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        sharedPreferences = context.getSharedPreferences("THONGTIN",Context.MODE_PRIVATE);
//                        int maNV = sharedPreferences.getInt("id",1);
//                        HoaDonDAO hoaDonDAO = new HoaDonDAO(context);
//                        Date crTime = Calendar.getInstance().getTime();
//                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
//                        String ngay = simpleDateFormat.format(crTime);
//                        HoaDon hoaDon = new HoaDon(
//                                maNV,
//                                edtTenNguoiMua.getText().toString(),
//                                ngay,
//                                currentItem.getTongTien(),
//                                0
//                        );
//                        HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet(
//                                currentItem.getMaSach(),
//                                currentItem.getSoLuong(),
//                                currentItem.getGia(),
//                                currentItem.getGia()
//                        );
//                        if(hoaDonDAO.insertOneHoaDonAndHDCT(hoaDon,hoaDonChiTiet)){
//                            if(ghDAO.deleteGioHang(currentItem.getMaGioHang()+"") == 1){
//                                reload();
//                                dialog.dismiss();
//                                Toast.makeText(context, "Đã xác nhận mua, đến quầy tiến hành thanh toán", Toast.LENGTH_SHORT).show();
//                            }else {
//                                reload();
//                                Toast.makeText(context, "Nhân ngoại hữu nhân, thiên ngoại ngăn cản không cho mua, xem log :((", Toast.LENGTH_SHORT).show();
//                            }
//                        }
                    }
                });
//                btnHuy.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });
//                builder.setView(view);
//                dialog = builder.create();
//                dialog.show();
//            }
//        });
    }

    private boolean isAllChecked = false;
    public List<Integer> checkAllItems(boolean isChecked) {
        isAllChecked = isChecked;
        if(isAllChecked){
            for (GioHang item : itemList){
                listIdCart.add(item.getMaGioHang());
            }
        }else listIdCart.clear();
        System.out.println("============================== listIdCart bên adapter có " + listIdCart.size() + " đối tượng");
        notifyDataSetChanged();
        return listIdCart;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
    
    public void reload(){
        itemList.clear();
        int maNV = sharedPreferences.getInt("id",1);
        itemList.addAll(ghDAO.getAllGioHangByMaNV(maNV+""));
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageAnhSach, imageDeleteItemCart;
        CheckBox chkMuaNgay;
        Button btnMuaNgayItemCart;

        TextView txtTenSanPham, txtGiaSanPham, txtSoLuong, txtGiamSoLuong, txtTangSoLuong, txtTongTien;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageAnhSach = itemView.findViewById(R.id.ivAnhSachItemCart);
            txtTenSanPham = itemView.findViewById(R.id.txtTenSachItemCart);
            txtGiaSanPham = itemView.findViewById(R.id.txtGiaSachItemCart);
            txtSoLuong = itemView.findViewById(R.id.txtSoLuongMuaItemCart);
            txtGiamSoLuong = itemView.findViewById(R.id.txtGiamSoLuongMuaItemCart);
            txtTangSoLuong = itemView.findViewById(R.id.txtTangSoLuongMuaItemCart);
            txtTongTien = itemView.findViewById(R.id.txtTongTienItemCart);
            imageDeleteItemCart = itemView.findViewById(R.id.ivDeleteItemCart);
            chkMuaNgay = itemView.findViewById(R.id.chkBoxSelectCart);
            btnMuaNgayItemCart = itemView.findViewById(R.id.btnMuaHangNgayItemCart);
        }
    }
}
