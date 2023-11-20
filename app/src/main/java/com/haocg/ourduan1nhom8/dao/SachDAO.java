package com.haocg.ourduan1nhom8.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.haocg.ourduan1nhom8.db.DBHelper;
import com.haocg.ourduan1nhom8.model.NhanVien;
import com.haocg.ourduan1nhom8.model.Sach;

import java.util.ArrayList;

public class SachDAO {
    DBHelper dbHelper;
    public SachDAO(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    public ArrayList<Sach> getAllSach(){
        ArrayList<Sach> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cs = db.rawQuery("SELECT masach,maloai,l.tenloai,tensach,tentacgia,giamua,giaban,lantaiban,tennhasanxuat,namsanxuat,vitriquayhang,soluongbayban FROM SACH s,LOAISACH l where s.maloai = l.maloaisach",null);
        if(cs.getCount() != 0){
            cs.moveToFirst();
            do{
                list.add(new Sach(
                        cs.getInt(0),
                        cs.getInt(1),
                        cs.getString(2),
                        cs.getString(3),
                        cs.getString(4),
                        cs.getInt(5),
                        cs.getInt(6),
                        cs.getInt(7),
                        cs.getString(8),
                        cs.getInt(9),
                        cs.getString(10),
                        cs.getInt(11)
                ));
            }while (cs.moveToNext());
        }
        return list;
    }

    public boolean insertSach(Sach s){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maloai",s.getMaLoai());
        values.put("tensach",s.getTenSach());
        values.put("tentacgia",s.getTenTacGia());
        values.put("giamua",s.getGiaMua());
        values.put("giaban",s.getGiaBan());
        values.put("lantaiban",s.getLanTaiBan());
        values.put("tennhasanxuat",s.getTenNhaSanXuat());
        values.put("namsanxuat",s.getNamSanXuat());
        values.put("vitriquayhang",s.getViTriQuayHang());
        values.put("soluongbayban",s.getSoLuongBayBan());
        long row =db.insert("SACH",null,values);
        return (row == -1?false:true);
    }

    public boolean updateSach(Sach s){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maloai",s.getMaLoai());
        values.put("tensach",s.getTenSach());
        values.put("tentacgia",s.getTenTacGia());
        values.put("giamua",s.getGiaMua());
        values.put("giaban",s.getGiaBan());
        values.put("lantaiban",s.getLanTaiBan());
        values.put("tennhasanxuat",s.getTenNhaSanXuat());
        values.put("namsanxuat",s.getNamSanXuat());
        values.put("vitriquayhang",s.getViTriQuayHang());
        values.put("soluongbayban",s.getSoLuongBayBan());
        long row =db.update("SACH",values,"masach=?",new String[]{String.valueOf(s.getMaSach())});
        return (row == -1?false:true);
    }

    public boolean deleteSach(String maSach){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //
        //
        //

        long row =db.delete("SACH","masach=?",new String[]{(maSach)});
        return (row == -1?false:true);
    }

}
