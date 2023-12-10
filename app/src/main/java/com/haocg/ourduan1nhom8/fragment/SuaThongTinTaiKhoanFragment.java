package com.haocg.ourduan1nhom8.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.haocg.ourduan1nhom8.DangKy;
import com.haocg.ourduan1nhom8.DangNhap;
import com.haocg.ourduan1nhom8.R;
import com.haocg.ourduan1nhom8.dao.NhanVienDAO;
import com.haocg.ourduan1nhom8.model.NhanVien;
import com.haocg.ourduan1nhom8.util.ImageUtil;

import java.io.File;

public class SuaThongTinTaiKhoanFragment extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 1;
    String selectedImagePath = "";
    String savedImagePath = "";
    ImageUtil imageUtil;
    TextView txtUsernameSuaThongTin;
    ImageView imgAnhNV;
    Switch aSwitch;
    LinearLayout layoutOutOfDMK;
    EditText edtTenNV, edtEmailNV,edtMatKhauCu, edtMatKhau, edtXacNhanMK;
    Button btnLuuThongTin, btnHuyResetForm;
    NhanVienDAO nvDao;
    SharedPreferences sharedPreferences;
    String tenDangNhap, user, image;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sua_thong_tin_tai_khoan, container, false);

        txtUsernameSuaThongTin = view.findViewById(R.id.txtTenNVSuaThongTin);
        imgAnhNV = view.findViewById(R.id.ivAnhNVSuaThongTin);
        edtTenNV = view.findViewById(R.id.edtTenNVSuaThongTin);
        edtEmailNV = view.findViewById(R.id.edtEmailNVSuaThongTin);
        layoutOutOfDMK = view.findViewById(R.id.layoutDoiMatKhau);
        aSwitch = view.findViewById(R.id.switchDoiMatKhau);
        edtMatKhauCu = view.findViewById(R.id.edtMatKhauCuSuaThongTin);
        edtMatKhau = view.findViewById(R.id.edtMatKhauSuaThongTin);
        edtXacNhanMK = view.findViewById(R.id.edtXacNhanMatKhauSuaThongTin);
        btnLuuThongTin = view.findViewById(R.id.btnLuuNhanVienSuaThongTin);
        btnHuyResetForm = view.findViewById(R.id.btnHuyLuuNhanVienSuaThongTin);

        loadData();
        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(aSwitch.isChecked()){
                    layoutOutOfDMK.setVisibility(View.VISIBLE);
                }else layoutOutOfDMK.setVisibility(View.INVISIBLE);
            }
        });

        imgAnhNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        btnLuuThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = edtTenNV.getText().toString();
                String email = edtEmailNV.getText().toString();
                String mkCu = edtMatKhauCu.getText().toString();
                String mkMoi = edtMatKhau.getText().toString();
                String xnMK = edtXacNhanMK.getText().toString();
                NhanVien nv = nvDao.getNhanVienByCheckExist(user);
                String currentPassword = nv.getMatKhau();
                boolean check = false;
                if(ten.equals("") || email.equals("")){
                    Toast.makeText(getContext(), "Nếu bạn có lòng thì tôi có dạ, xin hãy nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if (email.contains(" ") || !email.contains("@") || !email.contains(".com")) {
                    Toast.makeText(getContext(), "Nếu bạn có lòng thì tôi có dạ, email không đúng định dạng", Toast.LENGTH_SHORT).show();
                } else if (aSwitch.isChecked()) {
                    if(mkCu.equals("") || mkMoi.equals("") || xnMK.equals("")){
                        Toast.makeText(getContext(), "Nếu bạn có lòng thì tôi có dạ, xin hãy nhập đầy đủ thông tin mật khẩu", Toast.LENGTH_SHORT).show();
                    } else if (!mkCu.equalsIgnoreCase(nv.getMatKhau())) {
                        Toast.makeText(getContext(), "Nếu bạn có lòng thì tôi có dạ, mật khẩu của bạn không chính xác", Toast.LENGTH_SHORT).show();
                    } else if (mkMoi.contains(" ")) {
                        Toast.makeText(getContext(), "Nếu bạn có lòng thì tôi có dạ, mật khẩu không được chức khoảng trống", Toast.LENGTH_SHORT).show();
                    } else if (!mkMoi.equalsIgnoreCase(xnMK)) {
                        Toast.makeText(getContext(), "Nếu bạn có lòng thì tôi có dạ, xác nhận mật khẩu thất bại do không trùng khớp", Toast.LENGTH_SHORT).show();
                    }else {
                        nv.setMatKhau(mkMoi);
                        check = true;
                    }
                }else {
                    check = true;
                }
                if(check){
                    saveImageToApp();
                    if(!savedImagePath.equals(nv.getAnhNV())){
                        imageUtil.deleteOldImage(nv.getAnhNV());
                        nv.setAnhNV(savedImagePath);
                        System.out.println("================================  path ảnh sau khi tính toán - saveImagePath: " + nv.getAnhNV());
                    }else{
                        System.out.println("================================  path ảnh sau khi tính toán - cũ: " + nv.getAnhNV());
                        nv.setAnhNV(nv.getAnhNV());
                    }
                    nv.setHoTen(ten);
                    nv.setEmail(email);
                    if(nvDao.updateNhanVien(nv)) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("name", nv.getHoTen());
                        editor.putString("user", nv.getTaiKhoan());
                        editor.putString("image", nv.getAnhNV());
                        editor.apply();
                        if (!currentPassword.equalsIgnoreCase(nv.getMatKhau())) {
                            Toast.makeText(getContext(), "Cập nhật thông tin thành công, về đăng nhập", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getContext(), DangNhap.class));
                            getActivity().finish();
                        } else {
                            loadData();
                            Toast.makeText(getContext(), "Hậu sanh khả úy, cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        btnHuyResetForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });

        return view;
    }

    public void loadData(){
        nvDao = new NhanVienDAO(getContext());
        sharedPreferences = getContext().getSharedPreferences("THONGTIN",Context.MODE_PRIVATE);
        tenDangNhap = sharedPreferences.getString("name","");
        user = sharedPreferences.getString("user","");
        image = sharedPreferences.getString("image","");
        imageUtil = new ImageUtil(requireContext());
        if(image != null && !image.isEmpty()){
            Glide.with(getContext()).load(Uri.fromFile(new File(image))).into(imgAnhNV);
        }else{
            Glide.with(getContext()).load(R.mipmap.avt).into(imgAnhNV);
        }

        txtUsernameSuaThongTin.setText("Xin chào " + tenDangNhap + ",");
        aSwitch.setChecked(false);
        layoutOutOfDMK.setVisibility(View.INVISIBLE);
        edtTenNV.setText("");
        edtEmailNV.setText("");
        edtMatKhauCu.setText("");
        edtMatKhau.setText("");
        edtXacNhanMK.setText("");
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
            Glide.with(getContext()).load(selectedImageUri).into(imgAnhNV);
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
}