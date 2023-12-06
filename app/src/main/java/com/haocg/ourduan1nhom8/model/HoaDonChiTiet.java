package com.haocg.ourduan1nhom8.model;

public class HoaDonChiTiet {
    private int maHDCT, maSach;
    private String tenSach;
    private int maHoaDon, soLuong, giaTien, thanhTien;

    public HoaDonChiTiet() {
    }

    public HoaDonChiTiet(int maSach,  int soLuong, int giaTien, int thanhTien) {
        this.maSach = maSach;
        this.soLuong = soLuong;
        this.giaTien = giaTien;
        this.thanhTien = thanhTien;
    }

    public HoaDonChiTiet(int maHDCT, int maSach, int maHoaDon, int soLuong, int giaTien, int thanhTien) {
        this.maHDCT = maHDCT;
        this.maSach = maSach;
        this.maHoaDon = maHoaDon;
        this.soLuong = soLuong;
        this.giaTien = giaTien;
        this.thanhTien = thanhTien;
    }

    public HoaDonChiTiet(int maSach, int maHoaDon, int soLuong, int giaTien, int thanhTien) {
        this.maSach = maSach;
        this.maHoaDon = maHoaDon;
        this.soLuong = soLuong;
        this.giaTien = giaTien;
        this.thanhTien = thanhTien;
    }

    public HoaDonChiTiet(int maHDCT, int maSach, String tenSach, int maHoaDon, int soLuong, int giaTien, int thanhTien) {
        this.maHDCT = maHDCT;
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.maHoaDon = maHoaDon;
        this.soLuong = soLuong;
        this.giaTien = giaTien;
        this.thanhTien = thanhTien;
    }

    public int getMaHDCT() {
        return maHDCT;
    }

    public void setMaHDCT(int maHDCT) {
        this.maHDCT = maHDCT;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(int giaTien) {
        this.giaTien = giaTien;
    }

    public int getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(int thanhTien) {
        this.thanhTien = thanhTien;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }
}
