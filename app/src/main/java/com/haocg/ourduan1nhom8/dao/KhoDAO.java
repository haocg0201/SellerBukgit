package com.haocg.ourduan1nhom8.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.haocg.ourduan1nhom8.db.DBHelper;
import com.haocg.ourduan1nhom8.model.Kho;

import java.util.ArrayList;

public class KhoDAO {
    DBHelper dbHelper;

    public KhoDAO(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    public ArrayList<Kho> getAllKho(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<Kho> list = new ArrayList<>();
        Cursor cs = db.rawQuery("SELECT vitri, k.masach, s.tensach, soluong FROM KHO k,SACH s WHERE k.masach = s.masach",null);
        if(cs.getCount()!= 0){
            cs.moveToFirst();
            do {
                list.add(new Kho(
                        cs.getInt(0),
                        cs.getInt(1),
                        cs.getString(2),
                        cs.getInt(3)
                ));
            }while (cs.moveToNext());

        }
        return list;
    }

    public ArrayList<Kho> getAllKhoByIncAndDec(String meow){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<Kho> list = new ArrayList<>();
        String query = "";
        if(meow.equalsIgnoreCase("ASC")){
            query =  "SELECT vitri, k.masach, s.tensach, soluong FROM KHO k,SACH s WHERE k.masach = s.masach ORDER BY k.soluong ASC";
        }else query =  "SELECT vitri, k.masach, s.tensach, soluong FROM KHO k,SACH s WHERE k.masach = s.masach ORDER BY k.soluong DESC";
        Cursor cs = db.rawQuery(query,null);
        if(cs.getCount()!= 0){
            cs.moveToFirst();
            do {
                list.add(new Kho(
                        cs.getInt(0),
                        cs.getInt(1),
                        cs.getString(2),
                        cs.getInt(3)
                ));
            }while (cs.moveToNext());

        }
        return list;
    }

    public Kho getKhoByMaSach(String maSach){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Kho k = new Kho();
        Cursor cs = db.rawQuery("SELECT masach, soluong FROM KHO WHERE masach=?",new String[]{maSach});
        if(cs.getCount()!= 0){
            cs.moveToFirst();
            k = new Kho(
                    cs.getInt(0),
                    cs.getInt(1)
            );
        }
        return k;
    }

    public boolean insertKho(Kho k){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("masach",k.getMaSach());
        values.put("soluong",k.getSoLuong());
        long row = db.insert("KHO",null,values);
        return (row!=-1?true:false);
    }

    public boolean updateKho(Kho k){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("masach",k.getMaSach());
        values.put("soluong",k.getSoLuong());
        long row = db.update("KHO",values,"vitri=?",new String[]{String.valueOf(k.getViTri())});
        return (row!=-1?true:false);
    }

    public int deleteKho(String viTri){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //
        // kiểm tra tồn tại
        //

        long row = db.delete("KHO","vitri=?",new String[]{viTri});
        return (row!=-1?1:0);
    }

    public boolean deleteKhoByMaSach(int maSach){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cs = db.rawQuery("SELECT masach, soluong FROM KHO WHERE masach=?",new String[]{String.valueOf(maSach)});
        if(cs.getCount()!= 0){
            long row = db.delete("KHO","masach=?",new String[]{String.valueOf(maSach)});
            return (row!=-1?true:false);
        }
        return true;
    }

    public boolean updateSoLuongKho(Kho k){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("soluong",k.getSoLuong());
        long row = db.update("KHO",values,"masach=?",new String[]{String.valueOf(k.getMaSach())});
        return (row!=-1?true:false);
    }
}
