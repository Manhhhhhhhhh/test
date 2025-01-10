/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package oop2;

import static java.awt.SystemColor.text;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class QLPhong extends javax.swing.JFrame {

    private List<PhongThucHanh> list;
    private DefaultTableModel tm;
    private static int sMa = 0;
    private PhongThucHanh phongThucHanhSua;
    private int rowSua;


    public PhongThucHanh getPhongThucHanhSua() {
        return phongThucHanhSua;
    }

    public void setPhongThucHanhSua(PhongThucHanh phongThucHanhSua) {
        this.phongThucHanhSua = phongThucHanhSua;
    }

    

    public int getRowSua() {
        return rowSua;
    }

    public void setRowSua(int rowSua) {
        this.rowSua = rowSua;
    }
    
    
    /**
     * Creates new form QLMonHoc
     */
    public QLPhong() {
        initComponents();
        setTitle("Quản lý Phòng Thực Hành");
        setLocationRelativeTo(this);
        list = new ArrayList<>();
        initTable();
        btnThem.addActionListener(e -> {
            FormThem formThem = new FormThem(this, true);
            formThem.setVisible(true);
            sMa++;
        });
        
        btnXoa.addActionListener(e -> {
            int row = tbPhongThucHanh.getSelectedRow();
            list.remove(row);
            tm.removeRow(row);
        });
        btnSua.addActionListener(e -> {
            int row = tbPhongThucHanh.getSelectedRow();
            if(row >=0 && row <tbPhongThucHanh.getRowCount()){
                setRowSua(row);
                setPhongThucHanhSua(list.get(row));
                FormSua formSua = new FormSua(this, true);
                formSua.setVisible(true);
            }
            else JOptionPane.showMessageDialog(this, "Chọn dòng để sửa");
            
        });
        
        btnLuu.addActionListener(e -> {
            IOFile.ghi("PHONG.DAT", list);
        });
        
        btnThongKe.addActionListener(e->{
            txtThongKe.setText("");
            Map<String, Integer> map = new LinkedHashMap<>();
            map.put("Thực hành mạng", 0);
            map.put("Thực hành  vi xử  lý", 0);
            map.put("Thực hành lập trình", 0);
            for(Map.Entry<String, Integer> en: map.entrySet()){
                String loai = en.getKey();
                for(PhongThucHanh x: list){
                    if(x.getLoaiPhongThucHanh().equals(loai)){
                        if(x.getSoLuongMayTinh() > en.getValue()){
                            map.put(loai, x.getSoLuongMayTinh());
                        }
                    }
                }
            }
            map.forEach((k,v) ->{
                txtThongKe.append("\n"+ k+ ":" + v);
            });
        });
        
        btnTimKiem.addActionListener(e->{
            String key = txtTim.getText();
            List<PhongThucHanh> l = new ArrayList<>();
            if(key!=null && !key.isEmpty()){
                for(PhongThucHanh x: list){
                    if(x.getTenPhongThucHanh().contains(key) || x.getLoaiPhongThucHanh().contains(key)){
                        l.add(x);
                    }
                }
            }
            if(key == null || key.isEmpty()){
                l = list;
            }
            tm.setRowCount(0);//xpo sach bảng
            
            for(PhongThucHanh i : l){
                tm.addRow(i.toObject());
            }
        });
        
        btnHienThi.addActionListener(e->{
            tm.setRowCount(0);
            for(PhongThucHanh i :list){
                tm.addRow(i.toObject());
            }
        });
        
        cbSapXep.addActionListener(e -> {
            int index = cbSapXep.getSelectedIndex();
            if(index == 0){
                list.sort((PhongThucHanh m1, PhongThucHanh m2) -> m1.getMaPhong().compareToIgnoreCase(m2.getMaPhong()));
            }
            if(index == 1){
                list.sort(new Comparator<PhongThucHanh>() {
                    @Override
                    public int compare(PhongThucHanh o1, PhongThucHanh o2) {
                        return o1.getTenPhongThucHanh().compareToIgnoreCase(o2.getTenPhongThucHanh());
                    }
                });
            }
            if(index == 2){
                list.sort(new Comparator<PhongThucHanh>() {
                    @Override
                    public int compare(PhongThucHanh o1, PhongThucHanh o2) {
                        return o1.getSoLuongMayTinh() - o2.getSoLuongMayTinh();
                    }
                });
            }
            if(index == 3){
                list.sort(new Comparator<PhongThucHanh>(){
                    @Override
                    public int compare(PhongThucHanh o1, PhongThucHanh o2) {
                        return o1.getLoaiPhongThucHanh().compareToIgnoreCase(o2.getLoaiPhongThucHanh());
                    }
                });
            }
            tm.setRowCount(0);
            for(PhongThucHanh x: list){
                tm.addRow(x.toObject());
            }
        });
    }
    
    
    public void initTable(){
        String[] header = {"Mã Phòng", "Tên Phòng", "Số Máy", "Loại"};
        tm = new DefaultTableModel(header, 0);
        tbPhongThucHanh.setModel(tm);
        list = IOFile.doc("PHONG.DAT");
        if(list == null){
            list = new ArrayList<>();
        }
        else {
            for(PhongThucHanh x: list){
                tm.addRow(x.toObject());
            }
            list.sort((PhongThucHanh m1, PhongThucHanh m2) -> {
                return m1.getMaPhong().compareToIgnoreCase(m2.getMaPhong());
            });
            sMa = Integer.parseInt(list.get(list.size() - 1).getMaPhong().substring(5) + 1);
        }
    }
    
    public void themPhong(String tenPhong, int soMay, String loai){
        PhongThucHanh phongThucHanh = new PhongThucHanh( String.format("%05d", sMa), tenPhong, soMay, loai);
        tm.addRow(phongThucHanh.toObject());
        list.add(phongThucHanh);
    }
    
    public void sua(String tenPhong, int soMay, String loai){
        PhongThucHanh phongThucHanh = new PhongThucHanh(String.format("%05d", sMa), tenPhong, soMay, loai);
        list.set(rowSua, phongThucHanh);
        tm.setRowCount(0);
        for(PhongThucHanh x: list){
            tm.addRow(x.toObject());
        }
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tbPhongThucHanh = new javax.swing.JTable();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnLuu = new javax.swing.JButton();
        btnHienThi = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        cbSapXep = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        txtTim = new javax.swing.JTextField();
        btnThongKe = new javax.swing.JButton();
        btnTimKiem = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtThongKe = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tbPhongThucHanh.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbPhongThucHanh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPhongThucHanhMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbPhongThucHanh);

        btnThem.setText("Thêm");

        btnSua.setText("Sửa");

        btnXoa.setText("Xoá");

        btnLuu.setText("Lưu");

        btnHienThi.setText("Hiển thị");

        jLabel1.setText("Sắp xếp");

        cbSapXep.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mã phòng", "Tên phòng", "Số máy", "Loại" }));

        jLabel2.setText("Tìm kiếm");

        btnThongKe.setText("Thống kê");

        btnTimKiem.setText("Tìm Kiếm");

        txtThongKe.setColumns(20);
        txtThongKe.setRows(5);
        jScrollPane2.setViewportView(txtThongKe);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbSapXep, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(jLabel2)
                        .addGap(40, 40, 40)
                        .addComponent(txtTim, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnXoa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLuu))
                    .addComponent(btnHienThi, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnThem)
                        .addGap(18, 18, 18)
                        .addComponent(btnSua))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnTimKiem)
                            .addComponent(btnThongKe))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(21, 21, 21))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnThem)
                            .addComponent(btnSua))
                        .addGap(55, 55, 55)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnXoa)
                            .addComponent(btnLuu))
                        .addGap(52, 52, 52)
                        .addComponent(btnHienThi)))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbSapXep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtTim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiem))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(btnThongKe))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(93, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbPhongThucHanhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPhongThucHanhMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_tbPhongThucHanhMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(QLPhong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QLPhong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QLPhong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QLPhong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QLPhong().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHienThi;
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThongKe;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cbSapXep;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbPhongThucHanh;
    private javax.swing.JTextArea txtThongKe;
    private javax.swing.JTextField txtTim;
    // End of variables declaration//GEN-END:variables
}
