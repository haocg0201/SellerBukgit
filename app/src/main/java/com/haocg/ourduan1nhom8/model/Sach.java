package com.haocg.ourduan1nhom8.model;

public class Sach {
    private int maSach, maLoai;
    private String tenLoai,tenSach, tenTacGia;
    private int giaMua, giaBan, lanTaiBan;
    private String tenNhaSanXuat;
    private int namSanXuat;
    private String viTriQuayHang;
    private int soLuongBayBan;
    private String anhSach;
    private int soLuongMua;

    public Sach(String tenSach, String anhSach, int soLuongMua) {
        this.tenSach = tenSach;
        this.anhSach = anhSach;
        this.soLuongMua = soLuongMua;
    }

    public Sach() {
    }

    public Sach(int maSach, int maLoai, String tenLoai, String tenSach, String tenTacGia, int giaMua, int giaBan, int lanTaiBan, String tenNhaSanXuat, int namSanXuat, String viTriQuayHang, int soLuongBayBan, String anhSach) {
        this.maSach = maSach;
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
        this.tenSach = tenSach;
        this.tenTacGia = tenTacGia;
        this.giaMua = giaMua;
        this.giaBan = giaBan;
        this.lanTaiBan = lanTaiBan;
        this.tenNhaSanXuat = tenNhaSanXuat;
        this.namSanXuat = namSanXuat;
        this.viTriQuayHang = viTriQuayHang;
        this.soLuongBayBan = soLuongBayBan;
        this.anhSach = anhSach;
    }

    public Sach(int maLoai, String tenSach, String tenTacGia, int giaMua, int giaBan, int lanTaiBan, String tenNhaSanXuat, int namSanXuat, String viTriQuayHang, int soLuongBayBan, String anhSach) {
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
        this.anhSach = anhSach;
    }

    public Sach(int maSach, int maLoai, String tenLoai, String tenSach, String tenTacGia, int giaMua, int giaBan, int lanTaiBan, String tenNhaSanXuat, int namSanXuat, String viTriQuayHang, int soLuongBayBan) {
        this.maSach = maSach;
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
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

    public Sach(int maSach, int soLuongBayBan) {
        this.maSach = maSach;
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

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public String getAnhSach() {
        return anhSach;
    }

    public void setAnhSach(String anhSach) {
        this.anhSach = anhSach;
    }

    public int getSoLuongMua() {
        return soLuongMua;
    }

    public void setSoLuongMua(int soLuongMua) {
        this.soLuongMua = soLuongMua;
    }
}
