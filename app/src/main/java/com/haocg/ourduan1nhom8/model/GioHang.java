package com.haocg.ourduan1nhom8.model;

public class GioHang {
    private int maGioHang, maSach, maNV;
    private String tenSach;
    private int gia, soLuong, tongTien;
    private String anhSanPham;

    public GioHang(int maGioHang, int maSach, int maNV, String tenSach, int gia, int soLuong, int tongTien, String anhSanPham) {
        this.maGioHang = maGioHang;
        this.maSach = maSach;
        this.maNV = maNV;
        this.tenSach = tenSach;
        this.gia = gia;
        this.soLuong = soLuong;
        this.tongTien = tongTien;
        this.anhSanPham = anhSanPham;
    }

    public GioHang() {
    }

    public GioHang(int maSach, int maNV, String tenSach, int gia, int soLuong, int tongTien, String anhSanPham) {
        this.maSach = maSach;
        this.maNV = maNV;
        this.tenSach = tenSach;
        this.gia = gia;
        this.soLuong = soLuong;
        this.tongTien = tongTien;
        this.anhSanPham = anhSanPham;
    }

    public GioHang(int maGioHang, int maSach, int maNV, String tenSach, int gia, int soLuong, int tongTien) {
        this.maGioHang = maGioHang;
        this.maSach = maSach;
        this.maNV = maNV;
        this.tenSach = tenSach;
        this.gia = gia;
        this.soLuong = soLuong;
        this.tongTien = tongTien;
    }

    public GioHang(int maSach, int maNV, String tenSach, int gia, int soLuong, int tongTien) {
        this.maSach = maSach;
        this.maNV = maNV;
        this.tenSach = tenSach;
        this.gia = gia;
        this.soLuong = soLuong;
        this.tongTien = tongTien;
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

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public String getAnhSanPham() {
        return anhSanPham;
    }

    public void setAnhSanPham(String anhSanPham) {
        this.anhSanPham = anhSanPham;
    }
}
