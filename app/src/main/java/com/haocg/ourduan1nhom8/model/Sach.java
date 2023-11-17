package com.haocg.ourduan1nhom8.model;

public class Sach {
    private int maSach, maLoai;
    private String tenSach, tenTacGia;
    private int giaMua, giaBan, lanTaiBan;
    private String tenNhaSanXuat;
    private int namSanXuat;
    private String viTriQuayHang;
    private int soLuongBayBan;

    public Sach() {
    }

    public Sach(int maSach, int maLoai, String tenSach, String tenTacGia, int giaMua, int giaBan, int lanTaiBan, String tenNhaSanXuat, int namSanXuat, String viTriQuayHang, int soLuongBayBan) {
        this.maSach = maSach;
        this.maLoai = maLoai;
        this.tenSach = tenSach;
        this.tenTacGia = tenTacGia;
        this.giaMua = giaMua;
        this.giaBan = giaBan;
        this.lanTaiBan = lanTaiBan;
        this.tenNhaSanXuat = tenNhaSanXuat;
        this.namSanXuat = namSanXuat;
        this.viTriQuayHang = viTriQuayHang;
        this.soLuongBayBan = soLuongBayBan;
    }

    public Sach(int maLoai, String tenSach, String tenTacGia, int giaMua, int giaBan, int lanTaiBan, String tenNhaSanXuat, int namSanXuat, String viTriQuayHang, int soLuongBayBan) {
        this.maLoai = maLoai;
        this.tenSach = tenSach;
        this.tenTacGia = tenTacGia;
        this.giaMua = giaMua;
        this.giaBan = giaBan;
        this.lanTaiBan = lanTaiBan;
        this.tenNhaSanXuat = tenNhaSanXuat;
        this.namSanXuat = namSanXuat;
        this.viTriQuayHang = viTriQuayHang;
        this.soLuongBayBan = soLuongBayBan;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getTenTacGia() {
        return tenTacGia;
    }

    public void setTenTacGia(String tenTacGia) {
        this.tenTacGia = tenTacGia;
    }

    public int getGiaMua() {
        return giaMua;
    }

    public void setGiaMua(int giaMua) {
        this.giaMua = giaMua;
    }

    public int getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(int giaBan) {
        this.giaBan = giaBan;
    }

    public int getLanTaiBan() {
        return lanTaiBan;
    }

    public void setLanTaiBan(int lanTaiBan) {
        this.lanTaiBan = lanTaiBan;
    }

    public String getTenNhaSanXuat() {
        return tenNhaSanXuat;
    }

    public void setTenNhaSanXuat(String tenNhaSanXuat) {
        this.tenNhaSanXuat = tenNhaSanXuat;
    }

    public int getNamSanXuat() {
        return namSanXuat;
    }

    public void setNamSanXuat(int namSanXuat) {
        this.namSanXuat = namSanXuat;
    }

    public String getViTriQuayHang() {
        return viTriQuayHang;
    }

    public void setViTriQuayHang(String viTriQuayHang) {
        this.viTriQuayHang = viTriQuayHang;
    }

    public int getSoLuongBayBan() {
        return soLuongBayBan;
    }

    public void setSoLuongBayBan(int soLuongBayBan) {
        this.soLuongBayBan = soLuongBayBan;
    }
}
