package com.haocg.ourduan1nhom8.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.haocg.ourduan1nhom8.db.DBHelper;
import com.haocg.ourduan1nhom8.model.HoaDon;
import com.haocg.ourduan1nhom8.model.HoaDonChiTiet;

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

    public ArrayList<HoaDon> getAllHoaDonModified(){
        ArrayList<HoaDon> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cs = db.rawQuery("SELECT mahoadon, hd.manv, nv.hoten, tenkhachmuahang, ngaylap, tongtien, trangthaidonhang FROM HOADON hd, NHANVIEN nv WHERE hd.manv = nv.manv",null);
        if(cs.getCount() != 0){
            cs.moveToFirst();
            do{
                list.add(new HoaDon(
                        cs.getInt(0),
                        cs.getInt(1),
                        cs.getString(2),
                        cs.getString(3),
                        cs.getString(4),
                        cs.getInt(5),
                        cs.getInt(6)
                ));
            }while (cs.moveToNext());
        }
        return list;
    }

    public boolean insertOneHoaDonAndHDCT(HoaDon hd, HoaDonChiTiet hdct){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("manv",hd.getMaNV());
        values.put("tenkhachmuahang",hd.getTenKhachHang());
        values.put("ngaylap",hd.getNgayLap());
        values.put("tongtien",hd.getTongTien());
        values.put("trangthaidonhang",hd.getTrangThaiDonHang());
        long idHD =db.insert("HOADON",null,values);

        ContentValues valuesHDCT = new ContentValues();
        valuesHDCT.put("masach",hdct.getMaSach());
        valuesHDCT.put("mahoadon",idHD);
        valuesHDCT.put("soluong",hdct.getSoLuong());
        valuesHDCT.put("giatien",hdct.getGiaTien());
        valuesHDCT.put("thanhtien",hdct.getThanhTien());
        long rowHDCT =db.insert("HOADONCHITIET",null,valuesHDCT);
        db.close();
        return ((idHD != -1 && rowHDCT != -1)?true:false);
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

        long rowHD =db.delete("HOADON","mahoadon=?",new String[]{String.valueOf(maHoaDon)});
        long rowHDCT =db.delete("HOADONCHITIET","mahoadon=?",new String[]{String.valueOf(maHoaDon)});
        return ((rowHD != -1 && rowHDCT != -1)?1:0);
    }
}
