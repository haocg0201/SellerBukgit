package com.haocg.ourduan1nhom8.fragment;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.haocg.ourduan1nhom8.R;
import com.haocg.ourduan1nhom8.adapter.NhanVienAdapter;
import com.haocg.ourduan1nhom8.dao.NhanVienDAO;
import com.haocg.ourduan1nhom8.model.NhanVien;
import com.haocg.ourduan1nhom8.util.ImageUtil;
import com.haocg.ourduan1nhom8.util.ItemNhanVienClick;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

public class NhanVienFragment extends DialogFragment {
    RecyclerView rcvNV;
    NhanVienAdapter nvAdapter;
    FloatingActionButton fbtDisplayAddNhanVienForm;
    ScrollView scrollView;
    EditText edtTenNVEditForm,edtEmailEditForm,edtNgayCapTKNVEditForm,edtLuongNVEditForm;
    Button btnLuuNhanVien,btnHuyLuuNhanVien;
    Spinner spVaiTroEditForm;
    ImageView ivAvatarNVEditForm;

    NhanVien nhanVienSuperMan;

    String selectedImagePath = "";
    String savedImagePath = "";
    ImageUtil imageUtil;

    AlertDialog dialog;
    private static final int PICK_IMAGE_REQUEST = 1;
    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nhan_vien, container, false);
        rcvNV = view.findViewById(R.id.rcvNV);
        fbtDisplayAddNhanVienForm = view.findViewById(R.id.fbtDisplayAddNhanVienForm);
        scrollView = view.findViewById(R.id.scrollViewNhanVien);
        ivAvatarNVEditForm = view.findViewById(R.id.imgNhanVienEditForm);
        edtTenNVEditForm = view.findViewById(R.id.edtTenNVEditForm);
        edtEmailEditForm = view.findViewById(R.id.edtEmailNVEditForm);
        spVaiTroEditForm = view.findViewById(R.id.edtVaiTroNVEditForm);
        edtNgayCapTKNVEditForm = view.findViewById(R.id.edtNgayCapTKNVEditForm);
        edtLuongNVEditForm = view.findViewById(R.id.edtLuongNVEditForm);
        btnLuuNhanVien = view.findViewById(R.id.btnLuuNhanVien);
        btnHuyLuuNhanVien = view.findViewById(R.id.btnHuyLuuNhanVien);
        edtNgayCapTKNVEditForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalenderDialog(edtNgayCapTKNVEditForm);
            }
        });

        Glide.with(getContext()).load(R.mipmap.avt).into(ivAvatarNVEditForm);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,spinnerRoleData());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spVaiTroEditForm.setAdapter(adapter);

        imageUtil = new ImageUtil(getContext());
        ivAvatarNVEditForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        loadData();

        btnLuuNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenNV = edtTenNVEditForm.getText().toString();
                String email = edtEmailEditForm.getText().toString();
                String ngayCap = edtNgayCapTKNVEditForm.getText().toString();
                String mucLuong = edtLuongNVEditForm.getText().toString();
                if(tenNV.equals("") || email.equals("") || ngayCap.equals("")){
                    Toast.makeText(getContext(), "Chưa nhập đủ thông tin, cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }else if (email.contains(" ")){
                    Toast.makeText(getContext(), "Email không chứa kí tự trống", Toast.LENGTH_SHORT).show();
                } else if (Integer.parseInt(mucLuong) < 1000) {
                    Toast.makeText(getContext(), "Chủ tiệm sách là kim cổ đệ nhất bại gia, lương lớn hơn 1000$", Toast.LENGTH_SHORT).show();
                }else if (nhanVienSuperMan == null) {
                    Toast.makeText(getContext(), "chưa target đối tượng nào", Toast.LENGTH_SHORT).show();
                } else{
                    saveImageToApp();
                    NhanVien nhanVien = nhanVienSuperMan;
                    if(!savedImagePath.equals(nhanVienSuperMan.getAnhNV())){
                        imageUtil.deleteOldImage(nhanVienSuperMan.getAnhNV());
                        nhanVien.setAnhNV(savedImagePath);
                        System.out.println("================================  path ảnh sau khi tính toán - saveImagePath: " + nhanVien.getAnhNV());
                    }else{
                        System.out.println("================================  path ảnh sau khi tính toán - cũ: " + nhanVien.getAnhNV());
                        nhanVien.setAnhNV(nhanVienSuperMan.getAnhNV());
                    }
                    nhanVien.setHoTen(tenNV);
                    nhanVien.setEmail(email);
                    nhanVien.setLuong(Integer.parseInt(mucLuong));
                    nhanVien.setNgayCap(ngayCap);
                    if ((spVaiTroEditForm.getSelectedItemId() == 0)) {
                        nhanVien.setVaiTro("nv");
                    } else if ((spVaiTroEditForm.getSelectedItemId() == 1)) {
                        nhanVien.setVaiTro("ad");
                    } else {
                        nhanVien.setVaiTro("adssr");
                    }
                    NhanVienDAO nhanVienDAO = new NhanVienDAO(getContext());
                    boolean check = false;
                    if(check = nhanVienDAO.updateNhanVien(nhanVien)){
                        loadData();
                        resetForm();
                        Toast.makeText(getContext(), "Cập nhật nhân viên thành công", Toast.LENGTH_SHORT).show();
                    }else Toast.makeText(getContext(), "Cập nhật nhân viên thành bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        fbtDisplayAddNhanVienForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater layoutInflater = getLayoutInflater();
                View addView = layoutInflater.inflate(R.layout.layout_dialog_them_nv,null);

                EditText edtTenNV = addView.findViewById(R.id.edtTenNVAddForm);
                EditText edtTaiKhoan = addView.findViewById(R.id.edtTKNVAddForm);
                EditText edtMatKhau = addView.findViewById(R.id.edtMatKhauNVAddForm);
                EditText edtEmailNV = addView.findViewById(R.id.edtEmailNVAddForm);
                EditText edtNgayCapTK = addView.findViewById(R.id.edtNgayCapTKNVAddForm);
                EditText edtLuongNV = addView.findViewById(R.id.edtLuongNVAddForm);
                Spinner spRoleNV = addView.findViewById(R.id.spRoleNVAddForm);
                Button btnAddNhanVien = addView.findViewById(R.id.btnThemNhanVien);
                Button btnHuyAddNhanVien = addView.findViewById(R.id.btnHuyThemNhanVien);

                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,spinnerRoleData());
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spRoleNV.setAdapter(spinnerAdapter);

                edtNgayCapTK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showCalenderDialog(edtNgayCapTK);
                    }
                });

                btnAddNhanVien.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String ten = edtTenNV.getText().toString();
                        String tai_khoan = edtTaiKhoan.getText().toString();
                        String mat_khau = edtMatKhau.getText().toString();
                        String email = edtEmailNV.getText().toString();
                        String luong = edtLuongNV.getText().toString();
                        String ngay_cap = edtNgayCapTK.getText().toString();
                        String role= "";
                        if ((spVaiTroEditForm.getSelectedItemId() == 0)) {
                            role = "nv";
                        } else if ((spVaiTroEditForm.getSelectedItemId() == 1)) {
                            role = "ad";
                        } else {
                            role = "adssr";
                        }

                        if(ten.equals("") || tai_khoan.equals("") || mat_khau.equals("") || email.equals("") || ngay_cap.equals("")){
                            Toast.makeText(getContext(), "Chưa điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                        } else if (email.contains(" ")) {
                            Toast.makeText(getContext(), "Email thí chủ thật ảo, bỏ khoảng trống đi", Toast.LENGTH_SHORT).show();
                        } else if (Integer.parseInt(luong) < 1000) {
                            Toast.makeText(getContext(), "Hào sảng lên, lương nhân viên trên 1000$", Toast.LENGTH_SHORT).show();
                        }else {
                            NhanVien addNV = new NhanVien(ten,tai_khoan,mat_khau,email,role,ngay_cap,0,"",Integer.parseInt(luong));
                            NhanVienDAO nvDAO = new NhanVienDAO(getContext());
                            if(nvDAO.insertNhanVien(addNV)){
                                loadData();
                                dialog.dismiss();
                                Toast.makeText(getContext(), "Thêm nhân viên thành công", Toast.LENGTH_SHORT).show();
                            }else Toast.makeText(getContext(), "Khí vận thí chủ hôm nay không tốt, thêm nhân viên thất bại, xem lỗi trong file log", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                btnHuyAddNhanVien.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                builder.setView(addView);
                dialog = builder.create();
                dialog.show();
            }
        });

        return view;
    }

    public void chooseImage() {
        Intent galleryIntent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        );
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode  == PICK_IMAGE_REQUEST && resultCode  == Activity.RESULT_OK && data != null){
            // lấy URI ảnh đc chọn nè
            Uri selectedImageUri = data.getData();
            Glide.with(getContext()).load(selectedImageUri).into(ivAvatarNVEditForm);
            selectedImagePath = imageUtil.getPathFromUri(selectedImageUri);
            System.out.println("================================ path : " + selectedImagePath);
        }
    }

    public void saveImageToApp(){
        if(selectedImagePath != null){
            // Sử dụng BitmapFactory để chuyển đường dẫn thành Bitmap :??

            Bitmap selectedImageBitmap = BitmapFactory.decodeFile(selectedImagePath);
            // Kiểm tra xem selectedImageBitmap có giá trị null không
            if (selectedImageBitmap != null) {
                // Gọi phương thức saveImageToApp để lưu ảnh vào thư mục ứng dụng
                File saveImageFile = imageUtil.saveImageToApp(selectedImageBitmap);

                // Lấy đường dẫn của ảnh đã lưu
                savedImagePath = saveImageFile.getAbsolutePath();
            } else {
                // Xử lý trường hợp selectedImageBitmap là null
                Toast.makeText(getContext(), "Chưa có ảnh mới, dùng ảnh cũ (nếu có) hoặc mặc định", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void resetForm(){
        selectedImagePath = "";
        savedImagePath = "";
        edtTenNVEditForm.setText("");
        edtEmailEditForm.setText("");
        edtNgayCapTKNVEditForm.setText("");
        spVaiTroEditForm.setSelection(0);
        edtLuongNVEditForm.setText("");
        nhanVienSuperMan = null;
        Glide.with(getContext()).load(R.mipmap.avt).into(ivAvatarNVEditForm);
    }



    public void loadData(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvNV.setLayoutManager(linearLayoutManager);
        NhanVienDAO nvDAO = new NhanVienDAO(getContext());
        ArrayList<NhanVien> list = nvDAO.getAllNhanVien();
        nvAdapter = new NhanVienAdapter(getContext(),list);
        nvAdapter = new NhanVienAdapter(getContext(), list, new ItemNhanVienClick() {
            @Override
            public void onNhanVienClick(NhanVien nv) {
                nhanVienSuperMan = nv;
                edtTenNVEditForm.setText(nv.getHoTen());
                edtEmailEditForm.setText(nv.getEmail());
                edtNgayCapTKNVEditForm.setText(nv.getNgayCap());
                spVaiTroEditForm.setSelection(((nv.getVaiTro().equals("adssr"))?2:(nv.getVaiTro().equals("ad"))?1:0));
                edtLuongNVEditForm.setText(nv.getLuong()+"");
                System.out.println("========================== path ảnh trong csdl: " + nv.getAnhNV());

                if(nv.getAnhNV() != null && !nv.getAnhNV().isEmpty()){
                    Glide.with(getContext()).load(Uri.fromFile(new File(nv.getAnhNV()))).into(ivAvatarNVEditForm);
                }else{
                    Glide.with(getContext()).load(R.mipmap.avt).into(ivAvatarNVEditForm);
                }
                scrollView.setOverScrollMode(View.OVER_SCROLL_NEVER);
                scrollView.smoothScrollTo(0,0);

                if(selectedImagePath.equals("")) savedImagePath = nv.getAnhNV();
            }
        });
        rcvNV.setAdapter(nvAdapter);
    }

    public void setHowScroll(){
        // Tạo một Animator với giá trị từ 0 đến chiều cao của ScrollView
        ValueAnimator animator = ValueAnimator.ofInt(0, scrollView.getHeight());

        // Thiết lập thời gian chạy của Animator (ms)
        animator.setDuration(1500); // Chọn thời gian cuộn 1000ms (1 giây)

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // Lấy giá trị của Animator và cuộn ScrollView đến đó
                scrollView.scrollTo(0, (int) animation.getAnimatedValue());
            }
        });
        animator.start();
    }

    public ArrayList<String> spinnerRoleData(){
        ArrayList <String> roles = new ArrayList<>();
        roles.add("Nhân viên");
        roles.add("Quản lý phụ");
        roles.add("Quản lý");
        return roles;
    }

    public void showCalenderDialog(EditText edt){
        Calendar calendar = Calendar.getInstance();
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                edt.setText(String.format("%d/%d/%d",dayOfMonth,month+1,year));
            }
        },year,month,dayOfMonth);
        dialog.show();
    }
}