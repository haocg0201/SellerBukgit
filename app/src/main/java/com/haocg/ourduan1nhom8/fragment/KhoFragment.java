package com.haocg.ourduan1nhom8.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.haocg.ourduan1nhom8.R;
import com.haocg.ourduan1nhom8.adapter.KhoAdapter;
import com.haocg.ourduan1nhom8.dao.KhoDAO;
import com.haocg.ourduan1nhom8.dao.SachDAO;
import com.haocg.ourduan1nhom8.model.Kho;
import com.haocg.ourduan1nhom8.model.Sach;
import com.haocg.ourduan1nhom8.util.ItemKhoClick;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class KhoFragment extends Fragment {
    RecyclerView rcvKho;
    KhoDAO khoDAO;
    SachDAO sachDAO;
    Spinner spSach;
    TextInputEditText edtSoLuong,edtSachHienTaiKho;
    TextView txtSoLuongBayBan;
    Button btnNhapKho,btnXuatKho;
    int index,idSach;
    boolean checkNhapKho = false;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kho, container, false);
        sachDAO = new SachDAO(getContext());
        rcvKho = view.findViewById(R.id.rcvDSKho);
        spSach = view.findViewById(R.id.spinnerSachKho);
        txtSoLuongBayBan = view.findViewById(R.id.txtSoLuongBayBan);
        edtSachHienTaiKho = view.findViewById(R.id.edtSachHienTaiKho);
        edtSoLuong = view.findViewById(R.id.edtSoluongKho);
        btnNhapKho = view.findViewById(R.id.btnNhapSachKho);
        btnXuatKho = view.findViewById(R.id.btnXuatSachKho);

        loadData();
        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(),getDataSach(), android.R.layout.simple_list_item_1,new String[]{"tensach"},new int[]{android.R.id.text1});
        spSach.setAdapter(simpleAdapter);
        spSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> listHM = (HashMap<String, Object>) spSach.getSelectedItem();
                edtSachHienTaiKho.setText(listHM.get("tensach")+"");
                idSach = (int) listHM.get("masach");
                txtSoLuongBayBan.setText("Số lượng bày bán hiện tại: " + getSoLuongBayBanHienTai(String.valueOf(listHM.get("masach"))));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                edtSachHienTaiKho.setText("Chưa có sách được chọn");
            }
        });

        btnNhapKho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Kho k = khoDAO.getKhoByMaSach(idSach+"");
                if(k.getMaSach() == idSach){
                    checkNhapKho = true;
                }

                 if (edtSoLuong.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Chưa nhập số lượng", Toast.LENGTH_SHORT).show();
                } else if (getSoLuongBayBanHienTai(String.valueOf(idSach))==0) {
                     Toast.makeText(getContext(), "Gian hàng chỉ còn cái nịt thôi, hết sách rồi", Toast.LENGTH_SHORT).show();
                 } else{
                     int soLuongHienTaiNhapKho = Integer.parseInt(edtSoLuong.getText().toString());
                     int soLuongBayBanHienTai = getSoLuongBayBanHienTai(String.valueOf(idSach));
                     int soLuongCoTrongKho = getSoLuongCoTrongKho(String.valueOf(idSach));
                     if(checkNhapKho){
                         if(soLuongHienTaiNhapKho > soLuongBayBanHienTai){
                             int soLuongThemVaoKho = soLuongBayBanHienTai + soLuongCoTrongKho;
                             int soLuongGiamVaoSach = 0;
                             boolean checkKho = khoDAO.updateSoLuongKho(new Kho(idSach,soLuongThemVaoKho));
                             boolean checkSach = sachDAO.updateSoLuongSach(new Sach(idSach,soLuongGiamVaoSach));
                             if(checkKho && checkSach){
                                 loadData();
                                 Toast.makeText(getContext(), "Nhập kho thành công", Toast.LENGTH_SHORT).show();
                             }else{
                                 Toast.makeText(getContext(), "Thất bại rồi, liên hệ để được hoàn tiền ))", Toast.LENGTH_SHORT).show();
                             }
                         }else{
                             int soLuongThemVaoKho = soLuongHienTaiNhapKho + soLuongCoTrongKho;
                             int soLuongGiamVaoSach = soLuongBayBanHienTai - soLuongHienTaiNhapKho;
                             boolean checkKho = khoDAO.updateSoLuongKho(new Kho(idSach,soLuongThemVaoKho));
                             boolean checkSach = sachDAO.updateSoLuongSach(new Sach(idSach,soLuongGiamVaoSach));
                             if(checkKho && checkSach){
                                 loadData();
                                 Toast.makeText(getContext(), "Nhập kho thành công", Toast.LENGTH_SHORT).show();
                             }else{
                                 Toast.makeText(getContext(), "Thất bại rồi, liên hệ để được hoàn tiền ))", Toast.LENGTH_SHORT).show();
                             }
                         }
                     }else {
                         int soLuongThemVaoKho = soLuongHienTaiNhapKho;
                         int soLuongGiamVaoSach = soLuongBayBanHienTai - soLuongHienTaiNhapKho;
                         boolean checkKho = khoDAO.insertKho(new Kho(idSach,soLuongThemVaoKho));
                         boolean checkSach = sachDAO.updateSoLuongSach(new Sach(idSach,soLuongGiamVaoSach));
                         if(checkKho && checkSach){
                             loadData();
                             Toast.makeText(getContext(), "Nhập kho thành công", Toast.LENGTH_SHORT).show();
                         }else{
                             Toast.makeText(getContext(), "Thất bại rồi, liên hệ để được hoàn tiền :((", Toast.LENGTH_SHORT).show();
                         }
                     }
                 }
            }
        });

        btnXuatKho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soLuongBayBanHienTai = getSoLuongBayBanHienTai(String.valueOf(idSach));
                int soLuongCoTrongKho = getSoLuongCoTrongKho(String.valueOf(idSach));
                if (edtSoLuong.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Chưa nhập số lượng", Toast.LENGTH_SHORT).show();
                } else if (soLuongCoTrongKho == 0) {
                    Toast.makeText(getContext(), "Kho còn cái nịt thôi, trong kho hết rồi", Toast.LENGTH_SHORT).show();
                } else {

                    int soLuongHienTaiXuatKho = Integer.parseInt(edtSoLuong.getText().toString());

                    boolean checkKho = false;
                    boolean checkSach = false;
                    int soLuongXuatKhoiKho;
                    int soLuongNhapVaoSach;
                    if(soLuongHienTaiXuatKho > soLuongCoTrongKho){
                        soLuongXuatKhoiKho = soLuongCoTrongKho;
                        soLuongNhapVaoSach = soLuongBayBanHienTai + soLuongXuatKhoiKho;
                        checkKho = khoDAO.updateSoLuongKho(new Kho(idSach,0));
                        checkSach = sachDAO.updateSoLuongSach(new Sach(idSach,soLuongNhapVaoSach));
                        if(checkKho && checkSach){
                            loadData();
                            Toast.makeText(getContext(), "Xuất kho thành công", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getContext(), "Thất bại rồi, liên hệ để được hoàn tiền :((", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        soLuongXuatKhoiKho = soLuongHienTaiXuatKho;
                        soLuongNhapVaoSach = soLuongBayBanHienTai + soLuongXuatKhoiKho;
                        checkKho = khoDAO.updateSoLuongKho(new Kho(idSach,soLuongCoTrongKho-soLuongXuatKhoiKho));
                        checkSach = sachDAO.updateSoLuongSach(new Sach(idSach,soLuongNhapVaoSach));
                        if(checkKho && checkSach){
                            loadData();
                            Toast.makeText(getContext(), "Xuất kho thành công", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getContext(), "Thất bại rồi, liên hệ để được hoàn tiền :((", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }
        });
        return view;
    }

    public void loadData(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvKho.setLayoutManager(linearLayoutManager);
        khoDAO = new KhoDAO(getContext());
        ArrayList<Kho> list = khoDAO.getAllKho();
        Sach s = sachDAO.getSachAndSoLuongBayBanByMaSach(idSach+"");
        txtSoLuongBayBan.setText("Số lượng bày bán hiện tại: " + s.getSoLuongBayBan());
        KhoAdapter khoAdapter = new KhoAdapter(getContext(), list, new ItemKhoClick() {
            @Override
            public void onItemClick(Kho k) {
                SachDAO sachDAO = new SachDAO(getContext());
                Sach s = sachDAO.getSachAndSoLuongBayBanByMaSach(String.valueOf(k.getMaSach()));
                txtSoLuongBayBan.setText("Số lượng bày bán hiện tại: " + s.getSoLuongBayBan());
                edtSachHienTaiKho.setText(k.getTenSach());
                edtSoLuong.setText(String.valueOf(k.getSoLuong()));
                index = k.getViTri();
                idSach = k.getMaSach();
            }
        });

        rcvKho.setAdapter(khoAdapter);
    }

    public ArrayList<HashMap<String, Object>> getDataSach(){
        ArrayList<HashMap<String, Object>> listDataSach = new ArrayList<>();
        SachDAO sachDAO = new SachDAO(getContext());
        ArrayList<Sach> listSach = sachDAO.getAllSach();
        for(Sach s : listSach){
            HashMap<String, Object> hm = new HashMap<>();
            hm.put("masach",s.getMaSach());
            hm.put("tensach",s.getTenSach());
            hm.put("soluongbayban",s.getSoLuongBayBan());
            listDataSach.add(hm);
        }
        return listDataSach;
    }

    public int getSoLuongCoTrongKho(String maSach){
        return khoDAO.getKhoByMaSach(maSach).getSoLuong();
    }

    public int getSoLuongBayBanHienTai(String maSach){
        return sachDAO.getSachAndSoLuongBayBanByMaSach(maSach).getSoLuongBayBan();
    }
}