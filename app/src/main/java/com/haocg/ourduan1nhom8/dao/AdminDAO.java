package com.haocg.ourduan1nhom8.dao;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;


import com.haocg.ourduan1nhom8.db.DBHelper;

public class AdminDAO {
    SharedPreferences sharedPreferences;
    DBHelper halper;

    public AdminDAO(Context context) {
        halper = new DBHelper(context);
        sharedPreferences = context.getSharedPreferences("THONGTIN",MODE_PRIVATE);
    }
    // hàm đăng nhập
    public boolean checkLogin(String maAD,String MatKhau){
        SQLiteDatabase db = halper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ADMIN WHERE MAAD = ? AND MATKHAU=?",new String[]{maAD,MatKhau});
        if (cursor.getCount()!=0){
            cursor.moveToFirst();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("MAAD",cursor.getString(0));
            editor.putString("LOAITK",cursor.getString(3));


            editor.commit();

            return true;
        }else {
            return false;
        }
    }
    public boolean doiMK(String useName,String oldPass,String newPass){
        SQLiteDatabase db = halper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ADMIN WHERE MAAD = ? AND MATKHAU = ? ", new String[]{useName,oldPass});
        if(cursor.getCount()>0){
            ContentValues values = new ContentValues();
            values.put("MATKHAU",newPass);
            long check = db.update("ADMIN",values,"MAAD = ?",new String[]{useName});
            if(check==-1){
                return false;
            }
            return true;
        }
        return false;

    }
    public boolean addAdmin(String maAdmin,String tenAdmin,String matKhau,String loaiTK){
        SQLiteDatabase db = halper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MAAD",maAdmin);
        values.put("HOTEN",tenAdmin);
        values.put("MATKHAU",matKhau);
        values.put("LOAITK",loaiTK);
        long check = db.insert("ADMIN",null,values);
        if(check==-1) return false;
        return true;

    }
}
