package com.haocg.ourduan1nhom8.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.haocg.ourduan1nhom8.R;
import com.haocg.ourduan1nhom8.adapter.SachAdapter;
import com.haocg.ourduan1nhom8.dao.LoaiSachDAO;
import com.haocg.ourduan1nhom8.dao.SachDAO;
import com.haocg.ourduan1nhom8.model.LoaiSach;
import com.haocg.ourduan1nhom8.model.Sach;
import com.haocg.ourduan1nhom8.util.ImageUtil;
import com.haocg.ourduan1nhom8.util.ItemSachClick;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class SachFragment extends Fragment {
    RecyclerView rcvSach;
    SachDAO sachDAO;
    LoaiSachDAO lsDAO;
    SachAdapter sachAdapter;

    ImageView imgSachQlSachForm;
    Spinner spLoaiSachQLSachForm;
    EditText edtTenSach, edtTenTG, edtGiaMua, edtGiaBan, edtLanTaiBan, edtTenNhaSX, edtNamSanXuat, edtViTriQH, edtSoLuongBayBan;
    Button btnThemSach, btnSuaSach;
    ScrollView scrollViewSach;
    int maLoaiSachInSpinner;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageUtil imageUtil;
    String selectedImagePath = "";
    String savedImagePath = "";
    Sach currentSach = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sach, container, false);
        rcvSach = view.findViewById(R.id.rcvSach);
        imgSachQlSachForm = view.findViewById(R.id.imgSachQLSach);
        spLoaiSachQLSachForm = view.findViewById(R.id.spLoaiSachQLSach);
        edtTenSach = view.findViewById(R.id.edtTenSachQlSach);
        edtTenTG = view.findViewById(R.id.edtTenTGQLSach);
        edtGiaMua = view.findViewById(R.id.edtGiaMuaQLSach);
        edtGiaBan = view.findViewById(R.id.edtGiaBanQLSach);
        edtLanTaiBan = view.findViewById(R.id.edtLanTaiBanQLSach);
        edtTenNhaSX = view.findViewById(R.id.edtTenNhaSanXuatQLSach);
        edtNamSanXuat = view.findViewById(R.id.edtNamSanXuatQLSach);
        edtViTriQH = view.findViewById(R.id.edtViTriQuayHangQLSach);
        edtSoLuongBayBan = view.findViewById(R.id.edtSoLuongBayBanQLSach);
        btnThemSach = view.findViewById(R.id.btnThemSachQLSach);
        btnSuaSach = view.findViewById(R.id.btnSuaSachQLSach);
        scrollViewSach = view.findViewById(R.id.scrollViewSach);
        imageUtil = new ImageUtil(getContext());

        imgSachQlSachForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(),getDataLoaiSach(), android.R.layout.simple_list_item_1,new String[]{"tenloai"},new int[]{android.R.id.text1});
        spLoaiSachQLSachForm.setAdapter(simpleAdapter);
        spLoaiSachQLSachForm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> listLSTemp = (HashMap<String, Object>) spLoaiSachQLSachForm.getSelectedItem();
                maLoaiSachInSpinner = (int)  listLSTemp.get("maloaisach");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getContext(), "Vạn kiếm quy tônggggg", Toast.LENGTH_SHORT).show();
            }
        });

        loadData();

        btnThemSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenSach = edtTenSach.getText().toString();
                String tenTG = edtTenTG.getText().toString();
                String giaMua = edtGiaMua.getText().toString();
                String giaBan = edtGiaBan.getText().toString();
                String lanTaiBan = edtLanTaiBan.getText().toString();
                String tenNhaSanXuat = edtTenNhaSX.getText().toString();
                String namSanXuat = edtNamSanXuat.getText().toString();
                String viTriQH = edtViTriQH.getText().toString();
                String slBayBan = edtSoLuongBayBan.getText().toString();
                if(tenSach.equals("") || tenTG.equals("") || tenNhaSanXuat.equals("") || viTriQH.equals("") || giaMua.equals("0") || giaBan.equals("0") || lanTaiBan.equals("0") || namSanXuat.equals("0") || edtSoLuongBayBan.equals("0")){
                    Toast.makeText(getContext(), "Chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if (sachDAO.isBookExist(tenSach)) {
                    Toast.makeText(getContext(), "Đã có sách này trên gian hàng rồi, thật hiếu học", Toast.LENGTH_SHORT).show();
                } else {
                    saveImageToApp();
                    Sach tempSach = new Sach();
                    if(sachDAO.insertSach(getDataFromFormSach(tempSach))){
                        clearForm();
                        loadData();
                        Toast.makeText(getContext(), "Thêm sách thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        imageUtil.deleteOldImage(savedImagePath);
                        Toast.makeText(getContext(), "Nhất chỉ phá thương khung tuy nhiên thêm sách vẫn thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnSuaSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenSach = edtTenSach.getText().toString();
                String tenTG = edtTenTG.getText().toString();
                String giaMua = edtGiaMua.getText().toString();
                String giaBan = edtGiaBan.getText().toString();
                String lanTaiBan = edtLanTaiBan.getText().toString();
                String tenNhaSanXuat = edtTenNhaSX.getText().toString();
                String namSanXuat = edtNamSanXuat.getText().toString();
                String viTriQH = edtViTriQH.getText().toString();
                String slBayBan = edtSoLuongBayBan.getText().toString();
                if(tenSach.equals("") || tenTG.equals("") || tenNhaSanXuat.equals("") || viTriQH.equals("") || giaMua.equals("0") || giaBan.equals("0") || lanTaiBan.equals("0") || namSanXuat.equals("0") || edtSoLuongBayBan.equals("0")){
                    Toast.makeText(getContext(), "Chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    saveImageToApp();
                    Sach tempSach = getDataFromFormSach(new Sach());
                    tempSach.setMaSach(currentSach.getMaSach());
                    if(sachDAO.updateSach(tempSach) && tempSach.getAnhSach() != currentSach.getAnhSach()){
                        imageUtil.deleteOldImage(currentSach.getAnhSach());
                        clearForm();
                        loadData();
                        Toast.makeText(getContext(), "Đã sửa thông tin sách", Toast.LENGTH_SHORT).show();
                    }else {
                        imageUtil.deleteOldImage(savedImagePath);
                        Toast.makeText(getContext(), "Nhất chỉ phá thương khung tuy nhiên sửa thông tin sách vẫn thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return view;
    }

    public void chooseImage(){
        Intent galleryintent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        );
        startActivityForResult(galleryintent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null){
            //lay url ne ca nha
            Uri selectedImageUri = data.getData();
            Glide.with(getContext()).load(selectedImageUri).into(imgSachQlSachForm);
            selectedImagePath = imageUtil.getPathFromUri(selectedImageUri);
            System.out.println("================================ path : " + selectedImagePath);
        }
    }

    public void saveImageToApp(){
        if(selectedImagePath != null){
            Bitmap selectedImageBitmap = BitmapFactory.decodeFile(selectedImagePath);
            if(selectedImageBitmap != null){
                File saveImageFile = imageUtil.saveImageToApp(selectedImageBitmap);

                savedImagePath = saveImageFile.getAbsolutePath();
                System.out.println("================================ save image path : " + savedImagePath);
            }else{
                // bitmap == null
                Toast.makeText(getContext(), "Chưa có ảnh mới, dùng ảnh cũ (nếu có) hoặc mặc định", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void clearForm(){
        selectedImagePath = "";
        savedImagePath = "";
        edtTenSach.setText("");
        edtTenTG.setText("");
        edtTenNhaSX.setText("");
        edtViTriQH.setText("");
        //
        edtGiaMua.setText("0");
        edtGiaBan.setText("0");
        edtLanTaiBan.setText("0");
        edtNamSanXuat.setText("0");
        edtSoLuongBayBan.setText("0");

    }

    public boolean checkForm(){
        boolean check = false;

        return check;
    }

    public Sach getDataFromFormSach(Sach sachData){
        String tenSach = edtTenSach.getText().toString();
        String tenTG = edtTenTG.getText().toString();
        String giaMua = edtGiaMua.getText().toString();
        String giaBan = edtGiaBan.getText().toString();
        String lanTaiBan = edtLanTaiBan.getText().toString();
        String tenNhaSanXuat = edtTenNhaSX.getText().toString();
        String namSanXuat = edtNamSanXuat.getText().toString();
        String viTriQH = edtViTriQH.getText().toString();
        String slBayBan = edtSoLuongBayBan.getText().toString();


        if(savedImagePath != null){
            sachData = new Sach(
                    maLoaiSachInSpinner,
                    tenSach,
                    tenTG,
                    Integer.parseInt(giaMua),
                    Integer.parseInt(giaBan),
                    Integer.parseInt(lanTaiBan),
                    tenNhaSanXuat,
                    Integer.parseInt(namSanXuat),
                    viTriQH,
                    Integer.parseInt(slBayBan),
                    savedImagePath
            );
        }else {
            sachData = new Sach(
                    maLoaiSachInSpinner,
                    tenSach,
                    tenTG,
                    Integer.parseInt(giaMua),
                    Integer.parseInt(giaBan),
                    Integer.parseInt(lanTaiBan),
                    tenNhaSanXuat,
                    Integer.parseInt(namSanXuat),
                    viTriQH,
                    Integer.parseInt(slBayBan),
                    currentSach.getAnhSach()
            );
        }
        System.out.println("save image path to sqlite: " +  sachData.getAnhSach());
        return sachData;
    }

    public void loadData(){
        rcvSach.setLayoutManager(new LinearLayoutManager(getContext()));
        sachDAO = new SachDAO(getContext());
        ArrayList<Sach> list = sachDAO.getAllSach();
        Glide.with(getContext()).load(R.mipmap.img).into(imgSachQlSachForm);
        edtGiaMua.setText("0");
        edtGiaBan.setText("0");
        edtLanTaiBan.setText("0");
        edtNamSanXuat.setText("0");
        edtSoLuongBayBan.setText("0");

        sachAdapter = new SachAdapter(getContext(), list, new ItemSachClick() {
            @Override
            public void onItemClick(Sach sach) {
                currentSach = sach;

                String imagePath = sach.getAnhSach();
                if(imagePath != null && !imagePath.isEmpty()){
                    Glide.with(getContext())
//                    .load("file://" + imagePath) cũng là 1 cách lấy ảnh nhưng không tuyệt đối đc như dưới
                            .load(Uri.fromFile(new File(imagePath)))
                            .into(imgSachQlSachForm);
                }else{
                    Glide.with(getContext()).load(R.mipmap.avt).into(imgSachQlSachForm);
                }

                edtTenSach.setText(sach.getTenSach());
                edtTenTG.setText(sach.getTenTacGia());
                edtGiaMua.setText(sach.getGiaMua()+"");
                edtGiaBan.setText(sach.getGiaBan()+"");
                edtLanTaiBan.setText(sach.getLanTaiBan()+"");
                edtTenNhaSX.setText(sach.getTenNhaSanXuat());
                edtNamSanXuat.setText(sach.getNamSanXuat()+"");
                edtViTriQH.setText(sach.getViTriQuayHang());
                edtSoLuongBayBan.setText(sach.getSoLuongBayBan()+"");
                scrollViewSach.setOverScrollMode(View.OVER_SCROLL_NEVER);
                scrollViewSach.smoothScrollTo(0,0);

            }
        });
        rcvSach.setAdapter(sachAdapter);
    }

    public ArrayList<HashMap<String, Object>> getDataLoaiSach(){
        ArrayList<HashMap<String, Object>> listDataLoaiSach = new ArrayList<>();
        lsDAO = new LoaiSachDAO(getContext());
        ArrayList<LoaiSach> listLoaiSach = lsDAO.getAllLoaiSach();
        for(LoaiSach ls : listLoaiSach){
            HashMap<String, Object> hmTemp = new HashMap<>();
            hmTemp.put("maloaisach",ls.getMaLoaiSach());
            hmTemp.put("tenloai",ls.getTenLoaiSach());
            listDataLoaiSach.add(hmTemp);
        }
        return listDataLoaiSach;
    }
}