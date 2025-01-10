package oop2;

import java.io.Serializable;


public class PhongThucHanh implements Serializable{

    private String maPhong;
    private String tenPhongThucHanh;
    private int soLuongMayTinh;
    private String loaiPhongThucHanh;

    public PhongThucHanh(String maPhong, String tenPhongThucHanh, int soLuongMayTinh, String loaiPhongThucHanh) {
        this.maPhong = maPhong;
        this.tenPhongThucHanh = tenPhongThucHanh;
        this.soLuongMayTinh = soLuongMayTinh;
        this.loaiPhongThucHanh = loaiPhongThucHanh;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getTenPhongThucHanh() {
        return tenPhongThucHanh;
    }

    public void setTenPhongThucHanh(String tenPhongThucHanh) {
        this.tenPhongThucHanh = tenPhongThucHanh;
    }

    public int getSoLuongMayTinh() {
        return soLuongMayTinh;
    }

    public void setSoLuongMayTinh(int soLuongMayTinh) {
        this.soLuongMayTinh = soLuongMayTinh;
    }

    public String getLoaiPhongThucHanh() {
        return loaiPhongThucHanh;
    }

    public void setLoaiPhongThucHanh(String loaiPhongThucHanh) {
        this.loaiPhongThucHanh = loaiPhongThucHanh;
    }

    public Object[] toObject(){
        return new Object[]{
            maPhong, tenPhongThucHanh, soLuongMayTinh, loaiPhongThucHanh
        };
    }
}
