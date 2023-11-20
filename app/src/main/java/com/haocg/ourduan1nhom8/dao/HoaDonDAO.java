package com.haocg.ourduan1nhom8.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.haocg.ourduan1nhom8.db.DBHelper;
import com.haocg.ourduan1nhom8.model.HoaDon;

import java.util.ArrayList;

public class HoaDonDAO {
    DBHelper dbHelper;

    public HoaDonDAO(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    public ArrayList<HoaDon> getAllHoaDon(){
        ArrayList<HoaDon> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cs = db.rawQuery("SELECT * FROM HOADON",null);
        if(cs.getCount() != 0){
            cs.moveToFirst();
            do{
                list.add(new HoaDon(
                        cs.getInt(0),
                        cs.getInt(1),
                        cs.getString(2),
                        cs.getString(3),
                        cs.getInt(4),
                        cs.getInt(5)
                ));
            }while (cs.moveToNext());
        }
        return list;
    }

    public boolean insertHoaDon(HoaDon hd){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("manv",hd.getMaNV());
        values.put("tenkhachmuahang",hd.getTenKhachHang());
        values.put("ngaylap",hd.getNgayLap());
        values.put("tongtien",hd.getTongTien());
        values.put("trangthaidonhang",hd.getTrangThaiDonHang());
        long row =db.insert("HOADON",null,values);
        return (row == -1?false:true);
    }

    public boolean updateHoaDon(HoaDon hd){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("manv",hd.getMaNV());
        values.put("tenkhachmuahang",hd.getTenKhachHang());
        values.put("ngaylap",hd.getNgayLap());
        values.put("tongtien",hd.getTongTien());
        values.put("trangthaidonhang",hd.getTrangThaiDonHang());
        long row =db.update("HOADON",values,"mahoadon=?",new String[]{String.valueOf(hd.getMaHoaDon())});
        return (row == -1?false:true);
    }

    public int deleteHoaDon(String maHoaDon){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //
        //
        //

        long row =db.delete("HOADON","mahoadon=?",new String[]{String.valueOf(maHoaDon)});
        return (row == -1?0:1);
    }
}
