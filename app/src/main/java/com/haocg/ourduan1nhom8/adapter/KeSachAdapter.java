package com.haocg.ourduan1nhom8.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.haocg.ourduan1nhom8.R;
import com.haocg.ourduan1nhom8.dao.GioHangDAO;
import com.haocg.ourduan1nhom8.model.GioHang;
import com.haocg.ourduan1nhom8.model.Sach;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class KeSachAdapter extends RecyclerView.Adapter<KeSachAdapter.MyViewHolder>{
    private Context context;
    private ArrayList<Sach> dataList;
    private int selectedItem = -1;
    AlertDialog dialog;

    public KeSachAdapter(Context context, ArrayList<Sach> dataList) {
        this.context = context;
        this.dataList = dataList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_rc_ds_sach , parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Sach sach = dataList.get(holder.getAdapterPosition());
        holder.txtTenSach.setText(sach.getTenSach()+"");
        String giaSach = (sach.getGiaBan()==0)?"Miễn phí":String.valueOf(sach.getGiaBan());
        holder.txtGiaSach.setText("4,5 ★   " + giaSach);
        String imagePath = sach.getAnhSach();
        if(imagePath != null && !imagePath.isEmpty()){
            Glide.with(holder.itemView.getContext())
//                    .load("file://" + imagePath)
                    .load(Uri.fromFile(new File(imagePath)))
                    .into(holder.ivAnhSach);
        }else{
            Glide.with(context).load(R.mipmap.avt).into(holder.ivAnhSach);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view = LayoutInflater.from(context).inflate(R.layout.layout_dialog_book_in4,null);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                ImageView imgBook = view.findViewById(R.id.ivAnhSachDSSachItem);
                TextView txtTenSach = view.findViewById(R.id.txtTenSachDSSachItem);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                TextView txtLoaiSach = view.findViewById(R.id.txtLoaiSachDSSachItem);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                TextView txtTenTG = view.findViewById(R.id.txtTenTGSachDSSachItem);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                TextView txtNhaSanXuat = view.findViewById(R.id.txtNhaSXSachDSSachItem);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                TextView txtNamSanXuat = view.findViewById(R.id.txtNamSXSachDSSachItem);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                TextView txtTaiBan = view.findViewById(R.id.txtLanTBSachDSSachItem);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                TextView txtTangSLMua = view.findViewById(R.id.txtTangSoLuongMua);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                TextView txtGiamSLMua = view.findViewById(R.id.txtGiamSoLuongMua);
                EditText edtSoLuongMua = view.findViewById(R.id.edtSoLuongMua);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                TextView txtSLBB = view.findViewById(R.id.txtSoLuongBayBanInfSach);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                TextView txtSoTienCanTT = view.findViewById(R.id.txtTongSoTienThanhToan);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                Button btnMuaNgay =  view.findViewById(R.id.btnMuaNgayInfSach);
                //
                btnMuaNgay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GioHangDAO gioHangDAO = new GioHangDAO(context);
                        // chưa lấy đc mã nhân viên khi đăng nhập để thêm vào
                        GioHang gioHang = gioHangDAO.getGioHangByMaSach(sach.getMaSach());
                        GioHang gioHangNew;
                        if (gioHang != null) {
                            int slMua = gioHang.getSoLuong();
                            int tt = gioHang.getTongTien();
                            gioHangNew = new GioHang(
                                    sach.getMaSach(),
                                    // chưa lấy đc mã nhân viên khi đăng nhập để thêm vào
                                    1,
                                    sach.getTenSach(),
                                    sach.getGiaBan(),
                                    Integer.parseInt(edtSoLuongMua.getText().toString()) + slMua,
                                    Integer.parseInt(txtSoTienCanTT.getText().toString()) + tt,
                                    sach.getAnhSach());
                        } else {
                           gioHangNew = new GioHang(
                                    sach.getMaSach(),
                                    // chưa lấy đc mã nhân viên khi đăng nhập để thêm vào
                                    1,
                                    sach.getTenSach(),
                                    sach.getGiaBan(),
                                    Integer.parseInt(edtSoLuongMua.getText().toString()),
                                    Integer.parseInt(txtSoTienCanTT.getText().toString()),
                                    sach.getAnhSach());
                        }
                        if(gioHangDAO.insertGioHang(gioHangNew)){
                            Toast.makeText(context,"Đã thêm vào giỏ hàng, chọn thêm đi :))",Toast.LENGTH_SHORT).show();
                        }else Toast.makeText(context,"Địa lợi, nhân hòa tuy nhiên không nhân thời :(, chưa thêm vào giỏ hàng",Toast.LENGTH_SHORT).show();
                    }
                });
                String imagePath = sach.getAnhSach();
                if(imagePath != null && !imagePath.isEmpty()){
                    Glide.with(holder.itemView.getContext())
//                    .load("file://" + imagePath)
                            .load(Uri.fromFile(new File(imagePath)))
                            .into(imgBook);
                }else{
                    Glide.with(context).load(R.mipmap.avt).into(imgBook);
                }
                txtTenSach.setText(sach.getTenSach()+"");
                txtLoaiSach.setText(sach.getTenLoai()+"");
                txtTenTG.setText(sach.getTenTacGia()+"");
                txtNhaSanXuat.setText(sach.getTenNhaSanXuat()+"");
                txtNamSanXuat.setText("Năm sản xuất: " + sach.getNamSanXuat()+"");
                txtTaiBan.setText("Tái bản lần thứ: " + sach.getLanTaiBan());
                txtTangSLMua.setText(">");
                txtGiamSLMua.setText("<");
                edtSoLuongMua.setText("1");
                txtSLBB.setText("Số lượng cửa hàng đang bày bán: " + sach.getSoLuongBayBan());
                int tongTienTT = sach.getGiaBan() * Integer.parseInt(edtSoLuongMua.getText().toString());
                txtSoTienCanTT.setText(tongTienTT+"");

                if(edtSoLuongMua.getText().toString().equals("1")){
                    txtGiamSLMua.setVisibility(View.INVISIBLE);
                }
                txtGiamSLMua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(edtSoLuongMua.getText().toString().equals("")){
                            edtSoLuongMua.setText("1");
                        }else {
                            int sl = Integer.parseInt(edtSoLuongMua.getText().toString());
                            if(sl >=1){
                                sl-=1;
                                edtSoLuongMua.setText(sl+"");
                            }
                        }

                    }
                });

                txtTangSLMua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(edtSoLuongMua.getText().toString().equals("")){
                            edtSoLuongMua.setText("1");
                        }else {
                            int sl = Integer.parseInt(edtSoLuongMua.getText().toString());
                            if(sl <= sach.getSoLuongBayBan()){
                                sl+=1;
                                edtSoLuongMua.setText(sl+"");
                            }
                        }

                    }
                });

                edtSoLuongMua.setFilters(new InputFilter[]{new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        try {
                            // Giá trị mới sau khi thêm vào
                            String newVal = dest.toString().substring(0, dstart) + source.toString() + dest.toString().substring(dend);

                            // Kiểm tra giá trị mới
                            int input = Integer.parseInt(newVal);

                            // Giá trị nhỏ nhất là 1
                            if (input >= 1) {
                                return null; // Chấp nhận giá trị
                            }
                            if(input <= sach.getSoLuongBayBan()){
                                return null;
                            }
                        } catch (NumberFormatException ignored) {
                            // Ignore NumberFormatException
                        }
                        // Nếu giá trị không hợp lệ, không chấp nhận
                        return "";
                    }
                }});
                edtSoLuongMua.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(!edtSoLuongMua.getText().toString().equals("")) {
                            if (Integer.parseInt(edtSoLuongMua.getText().toString()) >= 2) {
                                txtGiamSLMua.setVisibility(View.VISIBLE);
                            } else txtGiamSLMua.setVisibility(View.INVISIBLE);
                            int tongTienTT = sach.getGiaBan() * Integer.parseInt(edtSoLuongMua.getText().toString());
                            txtSoTienCanTT.setText(tongTienTT+"");
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if(!edtSoLuongMua.getText().toString().equals("")){
                            if(Integer.parseInt(edtSoLuongMua.getText().toString()) >=2){
                                txtGiamSLMua.setVisibility(View.VISIBLE);
                            }else txtGiamSLMua.setVisibility(View.INVISIBLE);
                            if(Integer.parseInt(edtSoLuongMua.getText().toString()) > sach.getSoLuongBayBan()){
                                txtTangSLMua.setVisibility(View.INVISIBLE);
                                edtSoLuongMua.setText(sach.getSoLuongBayBan()+"");
                            }else txtTangSLMua.setVisibility(View.VISIBLE);
                        }
                    }
                });


                builder.setView(view);
                dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAnhSach;
        TextView txtTenSach, txtGiaSach;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivAnhSach = itemView.findViewById(R.id.ivAnhSachDSSach);
            txtTenSach = itemView.findViewById(R.id.txtTenSachDSSach);
            txtGiaSach = itemView.findViewById(R.id.txtGiaSachDSSach);
        }
    }
}
