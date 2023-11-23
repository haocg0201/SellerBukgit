package com.haocg.ourduan1nhom8.model;

public class Kho {
    private int viTri, maSach;
    private String tenSach;
    private int soLuong;

    public Kho() {
    }

    public Kho(int viTri, int maSach, String tenSach, int soLuong) {
        this.viTri = viTri;
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.soLuong = soLuong;
    }

    public Kho(int viTri, int maSach, int soLuong) {
        this.viTri = viTri;
        this.maSach = maSach;
        this.soLuong = soLuong;
    }

    public Kho(int maSach, int soLuong) {
        this.maSach = maSach;
        this.soLuong = soLuong;
    }

    public int getViTri() {
        return viTri;
    }

    public void setViTri(int viTri) {
        this.viTri = viTri;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }
}
