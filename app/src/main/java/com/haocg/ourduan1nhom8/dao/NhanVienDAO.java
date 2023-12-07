package com.haocg.ourduan1nhom8.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.haocg.ourduan1nhom8.db.DBHelper;
import com.haocg.ourduan1nhom8.model.NhanVien;

import java.util.ArrayList;

public class NhanVienDAO {
    DBHelper dbHelper;

    SharedPreferences sharedPreferences;

    public NhanVienDAO(Context context) {
        this.dbHelper = new DBHelper(context);
        sharedPreferences = context.getSharedPreferences("THONGTIN",Context.MODE_PRIVATE);
    }

    public ArrayList<NhanVien> getAllNhanVien(){
        ArrayList<NhanVien> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cs = db.rawQuery("SELECT * FROM NHANVIEN",null);
        if(cs.getCount() != 0){
            cs.moveToFirst();
            do{
                list.add(new NhanVien(
                       cs.getInt(0),
                       cs.getString(1),
                       cs.getString(2),
                       cs.getString(3),
                        cs.getString(4),
                        cs.getString(5),
                        cs.getString(6),
                        cs.getInt(7),
                        cs.getString(8),
                        cs.getInt(9)
                ));
            }while (cs.moveToNext());
        }
        return list;
    }

    public boolean insertNhanVien(NhanVien nv){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hoten",nv.getHoTen());
        values.put("taikhoan",nv.getTaiKhoan());
        values.put("matkhau",nv.getMatKhau());
        values.put("email",nv.getEmail());
        values.put("vaitro",nv.getVaiTro());
        values.put("ngaycap",nv.getNgayCap());
        values.put("trangthaitk",nv.getTrangThaiTK());
        values.put("anhnv",nv.getAnhNV());
        values.put("luong",nv.getLuong());
        long row =db.insert("NHANVIEN",null,values);
        return (row == -1?false:true);
    }

    public boolean updateNhanVien(NhanVien nv){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hoten",nv.getHoTen());
        values.put("taikhoan",nv.getTaiKhoan());
        values.put("matkhau",nv.getMatKhau());
        values.put("email",nv.getEmail());
        values.put("vaitro",nv.getVaiTro());
        values.put("ngaycap",nv.getNgayCap());
        values.put("trangthaitk",nv.getTrangThaiTK());
        values.put("anhnv",nv.getAnhNV());
        values.put("luong",nv.getLuong());
        long row =db.update("NHANVIEN",values,"manv=?",new String[]{String.valueOf(nv.getMaNV())});
        return (row == -1?false:true);
    }

    public int deleteNhanVien(String tenTaiKhoan){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //
        //
        //

        long row = db.delete("NHANVIEN","taikhoan=?",new String[]{tenTaiKhoan});
        return (row == -1?0:1);
    }

    public int checkLogin(String taiKhoan, String matKhau){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cs = db.rawQuery("SELECT * FROM NHANVIEN WHERE taikhoan = ? AND matkhau = ?",new String[]{taiKhoan,matKhau});
        if(cs.getCount() != 0){
            cs.moveToFirst();
            int status = cs.getInt(7);
            if(status == 1){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("id",cs.getInt(0));
                editor.putString("name",cs.getString(1));
                editor.putString("user",cs.getString(2));
                editor.putString("pass",cs.getString(3));
                editor.putString("role",cs.getString(5));
                editor.putString("image",cs.getString(8));
                editor.apply();
                return 1; // dn
            } else return -1; // locked
        }else{
            return 0; // sai tk or mk
        }
    }

    public int updatePw(String taiKhoan, String matKhauMoi){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "UPDATE NHANVIEN SET matkhau = ? WHERE taikhoan = ?";
        db.execSQL(sql, new String[]{matKhauMoi, taiKhoan});
        int rowsAffected = (int) DatabaseUtils.longForQuery(db, "SELECT changes()", null);
        if (rowsAffected > 0) {
            return 1; // Thành công
        } else {
            return 0; // Không thành công
        }
    }
}
