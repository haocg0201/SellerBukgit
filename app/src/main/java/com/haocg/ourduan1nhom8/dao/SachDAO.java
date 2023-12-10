package com.haocg.ourduan1nhom8.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.haocg.ourduan1nhom8.db.DBHelper;
import com.haocg.ourduan1nhom8.model.Kho;
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
        Cursor cs = db.rawQuery("SELECT masach,maloai,l.tenloai,tensach,tentacgia,giamua,giaban,lantaiban,tennhasanxuat,namsanxuat,vitriquayhang,soluongbayban,anhsach FROM SACH s,LOAISACH l where s.maloai = l.maloaisach",null);
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
                        cs.getInt(11),
                        cs.getString(12)
                ));
            }while (cs.moveToNext());
        }
        return list;
    }

    public ArrayList<Sach> getAllSachBanChay(){
        ArrayList<Sach> list = new ArrayList<>();
        String query = "SELECT SACH.tensach, SUM(HOADONCHITIET.soluong) AS soluongmua, SACH.anhsach " +
                "FROM SACH " +
                "INNER JOIN HOADONCHITIET ON SACH.masach = HOADONCHITIET.masach " +
                "INNER JOIN HOADON ON HOADONCHITIET.mahoadon = HOADON.mahoadon " +
                "WHERE HOADON.trangthaidonhang = 1 " +
                "GROUP BY SACH.tensach, SACH.anhsach " +
                "ORDER BY soluongmua DESC " +
                "LIMIT 10";
        String query_new = " SELECT s.tensach , SUM(hdct.soluong) AS soluongmua, s.anhsach " +
                "FROM SACH s, HOADONCHITIET hdct, HOADON hd " +
                "WHERE s.masach = hdct.masach AND hd.mahoadon = hdct.mahoadon AND hd.trangthaidonhang = 1 " +
                "GROUP BY s.tensach, s.anhsach " +
                "ORDER BY soluongmua DESC " +
                "LIMIT 10";

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query_new, null);

        if (cursor != null && cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                @SuppressLint("Range")
                String tenSach = cursor.getString(0);
                @SuppressLint("Range")
                int soLuongMua = cursor.getInt(1);
                @SuppressLint("Range")
                String anhSach = cursor.getString(2);
                list.add(new Sach(tenSach,anhSach,soLuongMua));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public ArrayList<Sach> getAllSachByMaLoaiSach(int maLoaiSach){
        ArrayList<Sach> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cs = db.rawQuery("SELECT masach,maloai,l.tenloai,tensach,tentacgia,giamua,giaban,lantaiban,tennhasanxuat,namsanxuat,vitriquayhang,soluongbayban,anhsach FROM SACH s,LOAISACH l where s.maloai = l.maloaisach AND s.maloai = ?",new String[]{String.valueOf(maLoaiSach)});
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
                        cs.getInt(11),
                        cs.getString(12)
                ));
            }while (cs.moveToNext());
        }
        return list;
    }

    public ArrayList<Sach> searchSach(String keyword) {
        ArrayList<Sach> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT masach,maloai,l.tenloai,tensach,tentacgia,giamua,giaban,lantaiban,tennhasanxuat,namsanxuat,vitriquayhang,soluongbayban,anhsach FROM SACH s,LOAISACH l " +
                "WHERE s.maloai = l.maloaisach " +
                "AND (tensach LIKE ? OR tentacgia LIKE ?)";

        Cursor cs = db.rawQuery(query, new String[]{"%" + keyword + "%", "%" + keyword + "%"});

        if (cs.getCount() != 0) {
            cs.moveToFirst();
            do {
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
                        cs.getInt(11),
                        cs.getString(12)
                ));
            } while (cs.moveToNext());
        }
        return list;
    }

    public Sach getSachAndSoLuongBayBanByMaSach(String maSach){
        Sach s = new Sach();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cs = db.rawQuery("SELECT masach,soluongbayban FROM SACH  where masach=?",new String[]{maSach});
        if(cs.getCount() != 0){
            cs.moveToFirst();
            s = new Sach(
                    cs.getInt(0),
                    cs.getInt(1)
            );
        }
        return s;
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
        values.put("anhsach",s.getAnhSach());
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
        values.put("anhsach",s.getAnhSach());
        long row =db.update("SACH",values,"masach=?",new String[]{String.valueOf(s.getMaSach())});
        return (row == -1?false:true);
    }

    public boolean deleteSach(int maSach){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //
        //
        //

        long row =db.delete("SACH","masach=?",new String[]{(String.valueOf(maSach))});
        return (row == -1?false:true);
    }

    public boolean updateSoLuongSach(Sach s){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("soluongbayban",s.getSoLuongBayBan());
        long row = db.update("SACH",values,"masach=?",new String[]{String.valueOf(s.getMaSach())});
        return (row!=-1?true:false);
    }

    public boolean isBookExist(String bookName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM SACH WHERE tensach = ?", new String[]{bookName});
        cursor.moveToFirst();
        int count = cursor.getInt(0);

        cursor.close();
        db.close();

        return count > 0;
    }

}
