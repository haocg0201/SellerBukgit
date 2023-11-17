package com.haocg.ourduan1nhom8.model;

public class GioHang {
    private int maGioHang, maSach, maNV;
    private String tenSach;
    private int gia, soLuong;

    public GioHang() {
    }

    public GioHang(int maGioHang, int maSach, int maNV, String tenSach, int gia, int soLuong) {
        this.maGioHang = maGioHang;
        this.maSach = maSach;
        this.maNV = maNV;
        this.tenSach = tenSach;
        this.gia = gia;
        this.soLuong = soLuong;
    }

    public GioHang(int maSach, int maNV, String tenSach, int gia, int soLuong) {
        this.maSach = maSach;
        this.maNV = maNV;
        this.tenSach = tenSach;
        this.gia = gia;
        this.soLuong = soLuong;
    }

    public int getMaGioHang() {
        return maGioHang;
    }

    public void setMaGioHang(int maGioHang) {
        this.maGioHang = maGioHang;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
