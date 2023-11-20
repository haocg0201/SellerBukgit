package com.haocg.ourduan1nhom8.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.haocg.ourduan1nhom8.db.DBHelper;
import com.haocg.ourduan1nhom8.model.GioHang;

import java.util.ArrayList;

public class GioHangDAO {
    DBHelper dbHelper;

    public GioHangDAO(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    public ArrayList<GioHang> getAllGioHangByMaNV(String maNV){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<GioHang> list = new ArrayList<>();
        Cursor cs = db.rawQuery("SELECT *FROM GIOHANG WHERE manv = ?",new String[]{maNV});
        if(cs.getCount()!=0){
            cs.moveToFirst();
            do {
                list.add(new GioHang(
                        cs.getInt(0),
                        cs.getInt(1),
                        cs.getInt(2),
                        cs.getString(3),
                        cs.getInt(4),
                        cs.getInt(5)
                ));
            }while (cs.moveToNext());
        }
        return list;
    }

    public boolean insertGioHang(GioHang gh){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("masach",gh.getMaSach());
        values.put("manv",gh.getMaNV());
        values.put("tensach",gh.getTenSach());
        values.put("gia",gh.getGia());
        values.put("soluong",gh.getSoLuong());
        long row = db.insert("GIOHANG",null,values);
        return(row!=-1?true:false);
    }

    public boolean updateGioHang(GioHang gh){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("masach",gh.getMaSach());
        values.put("manv",gh.getMaNV());
        values.put("tensach",gh.getTenSach());
        values.put("gia",gh.getGia());
        values.put("soluong",gh.getSoLuong());
        long row = db.update("GIOHANG",values,"magiohang=?",new String[]{String.valueOf(gh.getMaGioHang())});
        return(row!=-1?true:false);
    }

    public int deleteGioHang(String maGioHang){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //
        //
        //

        long row = db.delete("GIOHANG","magiohang=?",new String[]{maGioHang});
        return(row!=-1?1:0);
    }
}
