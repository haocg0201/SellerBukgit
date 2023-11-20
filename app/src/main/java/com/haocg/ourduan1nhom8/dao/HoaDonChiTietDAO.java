package com.haocg.ourduan1nhom8.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.haocg.ourduan1nhom8.db.DBHelper;
import com.haocg.ourduan1nhom8.model.HoaDonChiTiet;

import java.util.ArrayList;

public class HoaDonChiTietDAO {
    DBHelper dbHelper;

    public HoaDonChiTietDAO(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    public ArrayList<HoaDonChiTiet> getAllHoaDonChiTiet(){
        ArrayList<HoaDonChiTiet> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cs = db.rawQuery("SELECT * FROM HOADONCHITIET",null);
        if(cs.getCount() != 0){
            cs.moveToFirst();
            do{
                list.add(new HoaDonChiTiet(
                        cs.getInt(0),
                        cs.getInt(1),
                        cs.getInt(2),
                        cs.getInt(3),
                        cs.getInt(4),
                        cs.getInt(5)
                ));
            }while (cs.moveToNext());
        }
        return list;
    }

    public boolean insertHoaDonChiTiet(HoaDonChiTiet hdct){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("masach",hdct.getMaSach());
        values.put("mahoadon",hdct.getMaHoaDon());
        values.put("soluong",hdct.getSoLuong());
        values.put("giatien",hdct.getGiaTien());
        values.put("thanhtien",hdct.getThanhTien());
        long row =db.insert("HOADON",null,values);
        return (row == -1?false:true);
    }

    public boolean updateHoaDonChiTiet(HoaDonChiTiet hdct){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("masach",hdct.getMaSach());
        values.put("mahoadon",hdct.getMaHoaDon());
        values.put("soluong",hdct.getSoLuong());
        values.put("giatien",hdct.getGiaTien());
        values.put("thanhtien",hdct.getThanhTien());
        long row =db.update("HOADONCHITIET",values,"mahdct=?",new String[]{String.valueOf(hdct.getMaHDCT())});
        return (row == -1?false:true);
    }

    public int deleteHoaDonChiTiet(String maHoaDon){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //
        //
        //

        long row =db.delete("HOADONCHITIET","mahoadon=?",new String[]{String.valueOf(maHoaDon)});
        return (row == -1?0:1);
    }
}
