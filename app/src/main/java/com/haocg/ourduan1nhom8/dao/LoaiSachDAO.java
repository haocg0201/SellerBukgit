package com.haocg.ourduan1nhom8.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.haocg.ourduan1nhom8.db.DBHelper;
import com.haocg.ourduan1nhom8.model.LoaiSach;

import java.util.ArrayList;

public class LoaiSachDAO {
    DBHelper dbHelper;

    public LoaiSachDAO(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    public ArrayList<LoaiSach> getAllLoaiSach (){
        ArrayList<LoaiSach> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cs = db.rawQuery("SELECT * FROM LOAISACH",null);
        if(cs.getCount() != 0){
            cs.moveToFirst();
            do{
                list.add(new LoaiSach(
                     cs.getInt(0),
                     cs.getString(1)
                ));
            }while (cs.moveToNext());
        }
        return list;
    }

    public boolean insertLoaiSach(LoaiSach ls){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenloai",ls.getTenLoaiSach());
        long row = db.insert("LOAISACH",null,values);
        return (row!=-1?true:false);
    }

    public boolean updateLoaiSach(LoaiSach ls){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenloai",ls.getTenLoaiSach());
        long row = db.update("LOAISACH",values,"maloaisach=?",new String[]{String.valueOf(ls.getMaLoaiSach())});
        return (row!=-1?true:false);
    }

    public int deleteLoaiSach(String maLoaiSach){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //
        //
        //

        long row = db.delete("LOAISACH","maloaisach=?",new String[]{maLoaiSach});
        return (row!=-1?1:0);
    }
}
