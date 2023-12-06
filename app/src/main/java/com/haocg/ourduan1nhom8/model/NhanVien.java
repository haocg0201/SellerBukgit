package com.haocg.ourduan1nhom8.model;

public class NhanVien {
    private int maNV;
    private String hoTen, taiKhoan, matKhau, email, vaiTro, ngayCap;
    private int trangThaiTK;
    private String anhNV;
    private int luong;

    public NhanVien() {
    }



    public NhanVien(int maNV, String hoTen, String taiKhoan, String matKhau, String email, String vaiTro, String ngayCap, int trangThaiTK, String anhNV, int luong) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.email = email;
        this.vaiTro = vaiTro;
        this.ngayCap = ngayCap;
        this.trangThaiTK = trangThaiTK;
        this.anhNV = anhNV;
        this.luong = luong;
    }

    public NhanVien(String hoTen, String taiKhoan, String matKhau, String email, String vaiTro, String ngayCap, int trangThaiTK, String anhNV, int luong) {
        this.hoTen = hoTen;
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.email = email;
        this.vaiTro = vaiTro;
        this.ngayCap = ngayCap;
        this.trangThaiTK = trangThaiTK;
        this.anhNV = anhNV;
        this.luong = luong;
    }

    public NhanVien(int maNV, String hoTen, String taiKhoan, String matKhau, String email, String vaiTro, String ngayCap, int trangThaiTK, String anhNV) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.email = email;
        this.vaiTro = vaiTro;
        this.ngayCap = ngayCap;
        this.trangThaiTK = trangThaiTK;
        this.anhNV = anhNV;
    }

    public NhanVien(int maNV, String hoTen, String taiKhoan, String matKhau, String email, String vaiTro, String ngayCap, int trangThaiTK) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.email = email;
        this.vaiTro = vaiTro;
        this.ngayCap = ngayCap;
        this.trangThaiTK = trangThaiTK;
    }

    public NhanVien(String hoTen, String taiKhoan, String matKhau, String email, String vaiTro, String ngayCap, int trangThaiTK) {
        this.hoTen = hoTen;
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.email = email;
        this.vaiTro = vaiTro;
        this.ngayCap = ngayCap;
        this.trangThaiTK = trangThaiTK;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

    public String getNgayCap() {
        return ngayCap;
    }

    public void setNgayCap(String ngayCap) {
        this.ngayCap = ngayCap;
    }

    public int getTrangThaiTK() {
        return trangThaiTK;
    }

    public void setTrangThaiTK(int trangThaiTK) {
        this.trangThaiTK = trangThaiTK;
    }

    public String getAnhNV() {
        return anhNV;
    }

    public void setAnhNV(String anhNV) {
        this.anhNV = anhNV;
    }

    public int getLuong() {
        return luong;
    }

    public void setLuong(int luong) {
        this.luong = luong;
    }
}
