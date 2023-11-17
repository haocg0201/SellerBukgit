package com.haocg.ourduan1nhom8.model;

public class LoaiSach {
    private int maLoaiSach;
    private String tenLoaiSach;

    public LoaiSach() {
    }

    public LoaiSach(int maLoaiSach, String tenLoaiSach) {
        this.maLoaiSach = maLoaiSach;
        this.tenLoaiSach = tenLoaiSach;
    }

    public LoaiSach(String tenLoaiSach) {
        this.tenLoaiSach = tenLoaiSach;
    }

    public int getMaLoaiSach() {
        return maLoaiSach;
    }

    public void setMaLoaiSach(int maLoaiSach) {
        this.maLoaiSach = maLoaiSach;
    }

    public String getTenLoaiSach() {
        return tenLoaiSach;
    }

    public void setTenLoaiSach(String tenLoaiSach) {
        this.tenLoaiSach = tenLoaiSach;
    }
}
