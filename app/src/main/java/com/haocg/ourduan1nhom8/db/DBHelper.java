package com.haocg.ourduan1nhom8.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {


    public DBHelper(Context context) {
        super(context, "SellerBuk", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Admin
//        String createTabletbAdmin = "CREATE TABLE ADMIN(MAAD TEXT PRIMARY KEY,HOTEN TEXT,MATKHAU TEXT, LOAITK TEXT )";
//        db.execSQL(createTabletbAdmin);
        // NhanVien
        String createTableNhanVien = "create table NHANVIEN(manv INTEGER PRIMARY KEY AUTOINCREMENT,hoten TEXT,taikhoan TEXT, matkhau TEXT,email TEXT, vaitro TEXT, ngaycap TEXT,trangthaitk INTEGER,anhnv TEXT,luong INTEGER)";
        db.execSQL(createTableNhanVien);

        // Sach
        String createTableSach = "create table SACH(masach INTEGER PRIMARY KEY AUTOINCREMENT,maloai INTEGER REFERENCES LOAISACH(maloaisach) ,tensach TEXT, tentacgia TEXT, giamua INTEGER, giaban INTEGER," +
                " lantaiban INTEGER,tennhasanxuat TEXT, namsanxuat INTEGER, vitriquayhang TEXT, soluongbayban INTEGER,anhsach TEXT)";
        db.execSQL(createTableSach);

        // loai sach
        String createTableLoaiSach = "create table LOAISACH(maloaisach INTEGER PRIMARY KEY AUTOINCREMENT,tenloai TEXT)";
        db.execSQL(createTableLoaiSach);

        // Kho
        String createTableKho = "create table KHO(vitri INTEGER PRIMARY KEY AUTOINCREMENT,masach INTEGER REFERENCES SACH(masach), soluong INTEGER)";
        db.execSQL(createTableKho);

        // Hoadon
        String createTableHoaDon = "create table HOADON(mahoadon INTEGER PRIMARY KEY AUTOINCREMENT,manv INTEGER REFERENCES NHANVIEN(manv),tenkhachmuahang TEXT, ngaylap TEXT, tongtien INTEGER, trangthaidonhang INTEGER)";
        db.execSQL(createTableHoaDon);

        // Hoadonchitiet
        String createTableHoaDonChiTiet = "create table HOADONCHITIET(mahdct INTEGER PRIMARY KEY AUTOINCREMENT,masach INTEGER REFERENCES SACH(masach),mahoadon INTEGER REFERENCES HOADON(mahoadon), soluong INTEGER, giatien INTEGER, thanhtien INTEGER)";
        db.execSQL(createTableHoaDonChiTiet);

        //Giohang
        String createTableGioHang = "create table GIOHANG(magiohang INTEGER PRIMARY KEY AUTOINCREMENT,masach INTEGER,manv INTEGER, tensach TEXT, gia INTEGER, soluong INTEGER, tongtien INTEGER, anhsanpham TEXT)";
        db.execSQL(createTableGioHang);

        // insert mẫu

//        db.execSQL("insert into ADMIN VALUES('nhanvien1','NGHIÊM VAN THỊNH','12345','Admin')");

        db.execSQL("INSERT INTO NHANVIEN VALUES(1,'Nhân Viên A','nhanviena','123','littlechic43@gmail.com','nv','11/15/2023',1,'',1000)," +
                                                "(2,'Quản lý Thịnh','admin','123','nghiemthinh1712@gmail.com','ad','11/15/2023',1,'',1000)," +
                "(3,'Quản lý boss','adminssr','123','admin@gmail.com','adssr','11/15/2023',1,'',1000)," +
                "(4,'Cao Vi Vu','vivu','123','admin@gmail.com','nv','11/15/2023',0,'',1000)," +
                "(5,'Cao Vi Vu','v','123','admin@gmail.com','nv','11/15/2023',1,'',1000)");

        db.execSQL("INSERT INTO SACH VALUES(1,1,'Doraemon','F.J.F',3000,3500,3,'Nhà xuất bản Kim Đồng',2018,'Quầy Thiếu nhi',3000,'')," +
                                            "(2,2,'Java for kid 3+','F.Frank',3000,3500,3,'Nhà xuất bản Kim Đồng',2018,'Quầy Thiếu nhi',3000,'')");

        db.execSQL("INSERT INTO LOAISACH VALUES(1,'Truyện tranh'),(2,'Sách lập trình'),(3,'Sách giáo khoa'),(4,'Sách Vẽ')");

        db.execSQL("INSERT INTO KHO VALUES(1,1,1000),(2,2,1000)");

        db.execSQL("INSERT INTO HOADON VALUES(1,1,'Nguyễn Văn Vừa lòng em chưa','11/11/2023',7000,0)");

        db.execSQL("INSERT INTO HOADONCHITIET VALUES(1,1,1,1,3500,3500),(2,2,1,1,3500,3500)");

        db.execSQL("INSERT INTO GIOHANG VALUES(1,1,1,'Doraemon',3500,1,3500,''),(2,2,1,'Java for kid 3+',3500,1,3500,'')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            String dropTableNhanVien = "drop table if exists NHANVIEN";
            db.execSQL(dropTableNhanVien);
            String dropTableSach = "drop table if exists SACH";
            db.execSQL(dropTableSach);
            String dropTableLoaiSach = "drop table if exists LOAISACH";
            db.execSQL(dropTableLoaiSach);
            String dropTableKho = "drop table if exists KHO";
            db.execSQL(dropTableKho);
            String dropTableHoaDon = "drop table if exists HOADON";
            db.execSQL(dropTableHoaDon);
            String dropTableHoaDonCT = "drop table if exists HOADONCHITIET";
            db.execSQL(dropTableHoaDonCT);
            String dropTableGioHang = "drop table if exists GIOHANG";
            db.execSQL(dropTableGioHang);
            // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            onCreate(db);
        }
    }
}
