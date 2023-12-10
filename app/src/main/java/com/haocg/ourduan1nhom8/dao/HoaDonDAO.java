package com.haocg.ourduan1nhom8.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.haocg.ourduan1nhom8.db.DBHelper;
import com.haocg.ourduan1nhom8.model.HoaDon;
import com.haocg.ourduan1nhom8.model.HoaDonChiTiet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    public String convertDateFormat(String inputDate) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
            Date date = inputFormat.parse(inputDate);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<HoaDon> getHoaDonByTimeRange(String fromDate, String toDate){
        ArrayList<HoaDon> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //yyyy/MM/dd
        String fromDateConverted = convertDateFormat(fromDate);
        String toDateConverted = convertDateFormat(toDate);
        fromDateConverted = fromDateConverted.replace("/","");
        toDateConverted = toDateConverted.replace("/","");
//
//        String query = "SELECT * FROM HOADON WHERE strftime('%d/%m/%Y', ngaylap) BETWEEN ? AND ?";
//        Cursor cs = db.rawQuery(query, new String[]{fromDateConverted, toDateConverted});
        String query = "SELECT mahoadon, hd.manv, nv.hoten, tenkhachmuahang, ngaylap, tongtien, trangthaidonhang FROM HOADON hd, NHANVIEN nv WHERE hd.manv = nv.manv AND trangthaidonhang = 1 AND substr(ngaylap,7)||substr(ngaylap,4,2)||substr(ngaylap,1,2) BETWEEN ? AND ?";
        Cursor cs = db.rawQuery(query, new String[]{fromDateConverted, toDateConverted});

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
            } while (cs.moveToNext());
        }
        cs.close();
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

    public boolean insertOneHoaDonAndManyHDCT(HoaDon hd, ArrayList<HoaDonChiTiet> hoaDonChiTietArrayList ){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("manv",hd.getMaNV());
        values.put("tenkhachmuahang",hd.getTenKhachHang());
        values.put("ngaylap",hd.getNgayLap());
        values.put("tongtien",hd.getTongTien());
        values.put("trangthaidonhang",hd.getTrangThaiDonHang());
        long idHD =db.insert("HOADON",null,values);

        List<Long> longArrayList = new ArrayList<>();
        long rowHDCT = -1;
        for(HoaDonChiTiet hdct : hoaDonChiTietArrayList){
            ContentValues valuesHDCT = new ContentValues();
            valuesHDCT.put("masach",hdct.getMaSach());
            valuesHDCT.put("mahoadon",idHD);
            valuesHDCT.put("soluong",hdct.getSoLuong());
            valuesHDCT.put("giatien",hdct.getGiaTien());
            valuesHDCT.put("thanhtien",hdct.getThanhTien());
            rowHDCT =db.insert("HOADONCHITIET",null,valuesHDCT);
            longArrayList.add(rowHDCT);
        }
        int checkRow = -1;
        for(Long l : longArrayList){
            if(l == -1){
                break;
            }else checkRow = 1;
        }

        db.close();
        return ((idHD != -1 && checkRow != -1)?true:false);
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

    public boolean updateHoaDonByMaHoaDon(int maHD,int status){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("trangthaidonhang",status);
        long row =db.update("HOADON",values,"mahoadon=?",new String[]{String.valueOf(maHD)});
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
