package com.haocg.ourduan1nhom8.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.haocg.ourduan1nhom8.db.DBHelper;
import com.haocg.ourduan1nhom8.model.GioHang;

import java.util.ArrayList;
import java.util.List;

public class GioHangDAO {
    DBHelper dbHelper;

    public GioHangDAO(Context context) {
        this.dbHelper = new DBHelper(context);
    }


    public ArrayList<GioHang> getAllGioHangByMaNV(String maNV){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<GioHang> list = new ArrayList<>();
        String query = "";
        Cursor cs;
        if(!maNV.isEmpty() && maNV != null){
            cs = db.rawQuery("SELECT *FROM GIOHANG WHERE manv = ?",new String[]{maNV});
        }else cs = db.rawQuery("SELECT *FROM GIOHANG",null);

        if(cs.getCount()!=0){
            cs.moveToFirst();
            do {
                list.add(new GioHang(
                        cs.getInt(0),
                        cs.getInt(1),
                        cs.getInt(2),
                        cs.getString(3),
                        cs.getInt(4),
                        cs.getInt(5),
                        cs.getInt(6),
                        cs.getString(7)
                ));
            }while (cs.moveToNext());
        }
        return list;
    }

    public boolean isSachExistInGioHang(int masach) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("GIOHANG", null, "masach = ?", new String[]{String.valueOf(masach)}, null, null, null);
        boolean isExist = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return isExist;
    }

    public GioHang getGioHangByMaGH(int magiohang) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM GIOHANG WHERE magiohang=?";
        Cursor cursor = db.rawQuery(query,new String[]{String.valueOf(magiohang)});
//        Cursor cursor = db.query("GIOHANG", null, "masach = ?", new String[]{String.valueOf(maSach)},null,null.null);
        GioHang gioHang = null;
        if (cursor.getCount() != 0 && cursor != null) {
            cursor.moveToFirst();
            gioHang = new GioHang();
            gioHang.setMaGioHang(cursor.getInt(0));
            gioHang.setMaSach(cursor.getInt(1));
            gioHang.setMaNV(cursor.getInt(2));
            gioHang.setTenSach(cursor.getString(3));
            gioHang.setGia(cursor.getInt(4));
            gioHang.setSoLuong(cursor.getInt(5));
            gioHang.setTongTien(cursor.getInt(6));
            gioHang.setAnhSanPham(cursor.getString(7));
        }

        cursor.close();
        db.close();

        return gioHang;
    }

    public GioHang getGioHangByMaSach(int maSach) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM GIOHANG WHERE masach=?";
        Cursor cursor = db.rawQuery(query,new String[]{String.valueOf(maSach)});
//        Cursor cursor = db.query("GIOHANG", null, "masach = ?", new String[]{String.valueOf(maSach)},null,null.null);
        GioHang gioHang = null;
        if (cursor.getCount() != 0 && cursor != null) {
            cursor.moveToFirst();
            gioHang = new GioHang();
            gioHang.setMaGioHang(cursor.getInt(0));
            gioHang.setMaSach(cursor.getInt(1));
            gioHang.setMaNV(cursor.getInt(2));
            gioHang.setTenSach(cursor.getString(3));
            gioHang.setGia(cursor.getInt(4));
            gioHang.setSoLuong(cursor.getInt(5));
            gioHang.setTongTien(cursor.getInt(6));
            gioHang.setAnhSanPham(cursor.getString(7));
        }

        cursor.close();
        db.close();

        return gioHang;
    }

    public GioHang getGioHangByMaSachAndMaNV(int maSach, int maNV) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM GIOHANG WHERE masach=? AND manv=?";
        Cursor cursor = db.rawQuery(query,new String[]{String.valueOf(maSach),String.valueOf(maNV)});
        GioHang gioHang = null;
        if (cursor.getCount() != 0 && cursor != null) {
            cursor.moveToFirst();
            gioHang = new GioHang();
            gioHang.setMaGioHang(cursor.getInt(0));
            gioHang.setMaSach(cursor.getInt(1));
            gioHang.setMaNV(cursor.getInt(2));
            gioHang.setTenSach(cursor.getString(3));
            gioHang.setGia(cursor.getInt(4));
            gioHang.setSoLuong(cursor.getInt(5));
            gioHang.setTongTien(cursor.getInt(6));
            gioHang.setAnhSanPham(cursor.getString(7));
        }

        cursor.close();
        db.close();

        return gioHang;
    }


    public boolean insertGioHang(GioHang gh){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("masach",gh.getMaSach());
        values.put("manv",gh.getMaNV());
        values.put("tensach",gh.getTenSach());
        values.put("gia",gh.getGia());
        values.put("soluong",gh.getSoLuong());
        values.put("tongtien",gh.getTongTien());
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
        values.put("tongtien",gh.getTongTien());
        long row = db.update("GIOHANG",values,"magiohang=?",new String[]{String.valueOf(gh.getMaGioHang())});
        return(row!=-1?true:false);
    }

    public boolean updateGioHangByMaGioHang(int maGiohang,int soLuong, int tongTien){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("soluong",soLuong);
        values.put("tongtien",tongTien);
        long row = db.update("GIOHANG",values,"magiohang=?",new String[]{String.valueOf(maGiohang)});
        return(row!=-1?true:false);
    }

    public boolean updateGioHangByMaSachAndMaNV(int maSach,int maNV,int soLuong, int tongTien){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("soluong",soLuong);
        values.put("tongtien",tongTien);
        long row = db.update("GIOHANG",values,"masach=? AND manv = ?",new String[]{String.valueOf(maSach),String.valueOf(maNV)});
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

    public boolean deleteGioHangByListMaGH(List<Integer> listID){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Long> longList = new ArrayList<>();
        for (Integer i : listID){
            long row = db.delete("GIOHANG","magiohang=?",new String[]{i+""});
            longList.add(row);
        }
        boolean checkRow = true;
        for (Long l : longList){
            if(l == -1){
                checkRow = false;
            }
        }
        return checkRow;
    }
}
