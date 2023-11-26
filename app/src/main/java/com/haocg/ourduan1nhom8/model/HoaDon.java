package com.haocg.ourduan1nhom8.model;

public class HoaDon {
    private int maHoaDon, maNV;
    private String tenNV;
    private String tenKhachHang, ngayLap;
    private int tongTien, trangThaiDonHang;

    public HoaDon() {
    }

    public HoaDon(int maHoaDon, int maNV, String tenNV, String tenKhachHang, String ngayLap, int tongTien, int trangThaiDonHang) {
        this.maHoaDon = maHoaDon;
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.tenKhachHang = tenKhachHang;
        this.ngayLap = ngayLap;
        this.tongTien = tongTien;
        this.trangThaiDonHang = trangThaiDonHang;
    }

    public HoaDon(int maHoaDon, int maNV, String tenKhachHang, String ngayLap, int tongTien, int trangThaiDonHang) {
        this.maHoaDon = maHoaDon;
        this.maNV = maNV;
        this.tenKhachHang = tenKhachHang;
        this.ngayLap = ngayLap;
        this.tongTien = tongTien;
        this.trangThaiDonHang = trangThaiDonHang;
    }

    public HoaDon(int maNV, String tenKhachHang, String ngayLap, int tongTien, int trangThaiDonHang) {
        this.maNV = maNV;
        this.tenKhachHang = tenKhachHang;
        this.ngayLap = ngayLap;
        this.tongTien = tongTien;
        this.trangThaiDonHang = trangThaiDonHang;
    }

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(String ngayLap) {
        this.ngayLap = ngayLap;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public int getTrangThaiDonHang() {
        return trangThaiDonHang;
    }

    public void setTrangThaiDonHang(int trangThaiDonHang) {
        this.trangThaiDonHang = trangThaiDonHang;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }
}
