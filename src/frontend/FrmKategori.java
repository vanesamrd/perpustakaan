package frontend;

import backend.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FrmKategori extends JFrame {

    private JTextField txtIdKategori, txtNama, txtKeterangan, txtCari;
    private JButton btnSimpan, btnHapus, btnTambahBaru, btnCari;
    private JTable tblKategori;
    private JScrollPane jScrollPane1;

    public FrmKategori() {

        // ====== UI IMPROVEMENT ======
        UIManager.put("Button.focus", new Color(0, 0, 0, 0));        // hilangkan garis fokus
        UIManager.put("Table.showGrid", false);                     // tabel lebih clean
        UIManager.put("Table.intercellSpacing", new Dimension(8, 8));

        // ====== INISIALISASI KOMPONEN ======
        JLabel lblId = new JLabel("ID Kategori");
        JLabel lblNama = new JLabel("Nama Kategori");
        JLabel lblKet = new JLabel("Keterangan");

        txtIdKategori = new JTextField();
        txtNama = new JTextField();
        txtKeterangan = new JTextField();
        txtCari = new JTextField();

        btnSimpan = makeButton("Simpan");
        btnHapus = makeButton("Hapus");
        btnTambahBaru = makeButton("Tambah Baru");
        btnCari = makeButton("Cari");

        txtIdKategori.setText("0");
        txtIdKategori.setEnabled(false);

        tblKategori = new JTable();
        jScrollPane1 = new JScrollPane(tblKategori);

        setLayout(null); // manual layout

        // ====== PENGATURAN FONT ======
        Font fLabel = new Font("Segoe UI", Font.PLAIN, 14);
        Font fText = new Font("Segoe UI", Font.PLAIN, 14);
        Font fBtn = new Font("Segoe UI", Font.BOLD, 14);

        lblId.setFont(fLabel);
        lblNama.setFont(fLabel);
        lblKet.setFont(fLabel);

        txtIdKategori.setFont(fText);
        txtNama.setFont(fText);
        txtKeterangan.setFont(fText);
        txtCari.setFont(fText);

        btnSimpan.setFont(fBtn);
        btnHapus.setFont(fBtn);
        btnTambahBaru.setFont(fBtn);
        btnCari.setFont(fBtn);

        tblKategori.setFont(fText);
        tblKategori.setRowHeight(28);

        // ====== POSISI RAPI SESUAI GRID ======
        int xLabel = 30;
        int xField = 160;
        int wField = 360;
        int hField = 28;
        int gapY = 40;

        lblId.setBounds(xLabel, 30, 120, 25);
        txtIdKategori.setBounds(xField, 30, 120, hField);

        lblNama.setBounds(xLabel, 30 + gapY, 120, 25);
        txtNama.setBounds(xField, 30 + gapY, wField, hField);

        lblKet.setBounds(xLabel, 30 + gapY * 2, 120, 25);
        txtKeterangan.setBounds(xField, 30 + gapY * 2, wField, hField);

        btnSimpan.setBounds(xLabel, 30 + gapY * 3, 110, 35);
        btnHapus.setBounds(xLabel + 120, 30 + gapY * 3, 110, 35);
        btnTambahBaru.setBounds(xLabel + 240, 30 + gapY * 3, 140, 35);

        txtCari.setBounds(xLabel + 390, 30 + gapY * 3, 150, 32);
        btnCari.setBounds(xLabel + 545, 30 + gapY * 3, 70, 32);

        jScrollPane1.setBounds(30, 30 + gapY * 4 + 10, 640, 300);

        add(lblId);
        add(txtIdKategori);
        add(lblNama);
        add(txtNama);
        add(lblKet);
        add(txtKeterangan);
        add(btnSimpan);
        add(btnHapus);
        add(btnTambahBaru);
        add(txtCari);
        add(btnCari);
        add(jScrollPane1);

        // ====== EVENT ======
        btnSimpan.addActionListener(e -> simpan());
        btnHapus.addActionListener(e -> hapus());
        btnTambahBaru.addActionListener(e -> kosongkanForm());
        btnCari.addActionListener(e -> cari(txtCari.getText()));
        tblKategori.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                isiFormDariTabel();
            }
        });

        // Frame Setting
        setTitle("Form Kategori");
        setSize(720, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tampilkanData();
        kosongkanForm();
    }

    //   STYLE BUTTON (MENGHILANGKAN GARIS FOKUS, MEMBERI EFEK HALUS)
    private JButton makeButton(String text) {
        JButton b = new JButton(text);
        b.setFocusPainted(false);
        b.setBackground(new Color(200, 220, 240));
        b.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        return b;
    }


    //CRUD
    private void simpan() {
        Kategori kat = new Kategori();
        kat.setIdkategori(Integer.parseInt(txtIdKategori.getText()));
        kat.setNama(txtNama.getText());
        kat.setKeterangan(txtKeterangan.getText());
        kat.save();

        txtIdKategori.setText(String.valueOf(kat.getIdkategori()));
        tampilkanData();
    }

    private void hapus() {
        int row = tblKategori.getSelectedRow();
        if (row >= 0) {
            int id = Integer.parseInt(tblKategori.getValueAt(row, 0).toString());
            Kategori kat = new Kategori().getById(id);
            kat.delete();
            tampilkanData();
            kosongkanForm();
        }
    }

    private void isiFormDariTabel() {
        int row = tblKategori.getSelectedRow();
        txtIdKategori.setText(tblKategori.getValueAt(row, 0).toString());
        txtNama.setText(tblKategori.getValueAt(row, 1).toString());
        txtKeterangan.setText(tblKategori.getValueAt(row, 2).toString());
    }

    private void kosongkanForm() {
        txtIdKategori.setText("0");
        txtNama.setText("");
        txtKeterangan.setText("");
    }

    private void tampilkanData() {
        String[] kolom = {"ID", "Nama Kategori", "Keterangan"};
        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, kolom);

        for (Kategori k : new Kategori().getAll()) {
            model.addRow(new Object[]{
                k.getIdkategori(),
                k.getNama(),
                k.getKeterangan()
            });
        }

        tblKategori.setModel(model);
        tblKategori.getColumnModel().getColumn(0).setPreferredWidth(40);
    }

    private void cari(String keyword) {
        String[] kolom = {"ID", "Nama Kategori", "Keterangan"};
        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, kolom);

        for (Kategori k : new Kategori().search(keyword)) {
            model.addRow(new Object[]{
                k.getIdkategori(),
                k.getNama(),
                k.getKeterangan()
            });
        }

        tblKategori.setModel(model);
    }

    public static void main(String[] args) {
        new FrmKategori().setVisible(true);
    }
}
