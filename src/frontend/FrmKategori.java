package frontend;

import backend.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.event.*;

public class FrmKategori extends javax.swing.JFrame {
     private JTextField txtIdKategori;
    private JTextField txtNama;
    private JButton btnSimpan;
    private JButton btnHapus;
    private JButton btnTambahBaru;
    private JTextField txtCari;
    private JButton btnCari;
    private JTable tblKategori;
    private JScrollPane jScrollPane1;

    private JLabel labelId;
    private JLabel labelNama;

    public FrmKategori() {
        txtIdKategori = new JTextField();
        txtNama = new JTextField();
        btnSimpan = new JButton("Simpan");
        btnHapus = new JButton("Hapus");
        btnTambahBaru = new JButton("Tambah Baru");
        txtCari = new JTextField();
        btnCari = new JButton("Cari");
        tblKategori = new JTable();
        jScrollPane1 = new JScrollPane();

        labelId = new JLabel("ID");
        labelNama = new JLabel("Kategori");

        txtIdKategori.setText("0");
        txtIdKategori.setEnabled(false);

        tblKategori.setModel(new DefaultTableModel(
            new Object [][] {},
            new String [] {"ID", "Kategori"}
        ));
        jScrollPane1.setViewportView(tblKategori);

        getContentPane().setLayout(null);

        labelId.setBounds(15, 15, 100, 20);
        txtIdKategori.setBounds(120, 15, 100, 20);

        labelNama.setBounds(15, 40, 100, 20);
        txtNama.setBounds(120, 40, 300, 20);

        btnSimpan.setBounds(15, 80, 100, 30);
        btnTambahBaru.setBounds(125, 80, 110, 30);
        btnHapus.setBounds(245, 80, 100, 30);

        txtCari.setBounds(375, 85, 150, 25);
        btnCari.setBounds(535, 85, 60, 25);

        jScrollPane1.setBounds(15, 125, 580, 260);

        getContentPane().add(labelId);
        getContentPane().add(txtIdKategori);
        getContentPane().add(labelNama);
        getContentPane().add(txtNama);
        getContentPane().add(btnSimpan);
        getContentPane().add(btnTambahBaru);
        getContentPane().add(btnHapus);
        getContentPane().add(txtCari);
        getContentPane().add(btnCari);
        getContentPane().add(jScrollPane1);

        btnSimpan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnHapus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnTambahBaru.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnTambahBaruActionPerformed(evt);
            }
        });

        btnCari.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        tblKategori.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                tblKategoriMouseClicked(evt);
            }
        });

        setTitle("Form Kategori");
        setSize(620, 440);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tampilkanData();
        kosongkanForm();
    }

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {
        Kategori kat = new Kategori();
        kat.setIdkategori(Integer.parseInt(txtIdKategori.getText()));
        kat.setNama(txtNama.getText());
        kat.setKeterangan("");
        kat.save();

        txtIdKategori.setText(Integer.toString(kat.getIdkategori()));
        tampilkanData();
    }

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {
        DefaultTableModel model = (DefaultTableModel) tblKategori.getModel();
        int row = tblKategori.getSelectedRow();

        Kategori kat = new Kategori().getById(Integer.parseInt(model.getValueAt(row, 0).toString()));
        kat.delete();
        kosongkanForm();
        tampilkanData();
    }

    private void btnTambahBaruActionPerformed(java.awt.event.ActionEvent evt) {
        kosongkanForm();
    }

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {
        cari(txtCari.getText());
    }

    private void tblKategoriMouseClicked(java.awt.event.MouseEvent evt) {
        DefaultTableModel model = (DefaultTableModel) tblKategori.getModel();
        int row = tblKategori.getSelectedRow();

        txtIdKategori.setText(model.getValueAt(row, 0).toString());
        txtNama.setText(model.getValueAt(row, 1).toString());
    }

    public void kosongkanForm() {
        txtIdKategori.setText("0");
        txtNama.setText("");
    }

    public void tampilkanData() {
        String[] kolom = {"ID", "Kategori"};
        ArrayList<Kategori> list = new Kategori().getAll();
        Object rowData[] = new Object[2];

        tblKategori.setModel(new DefaultTableModel(new Object[][] {}, kolom));

        for (Kategori kat : list) {
            rowData[0] = kat.getIdkategori();
            rowData[1] = kat.getNama();

            ((DefaultTableModel) tblKategori.getModel()).addRow(rowData);
        }
    }

    public void cari(String keyword) {
        String[] kolom = {"ID", "Kategori"};
        ArrayList<Kategori> list = new Kategori().search(keyword);
        Object rowData[] = new Object[2];

        tblKategori.setModel(new DefaultTableModel(new Object[][] {}, kolom));

        for (Kategori kat : list) {
            rowData[0] = kat.getIdkategori();
            rowData[1] = kat.getNama();

            ((DefaultTableModel) tblKategori.getModel()).addRow(rowData);
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmKategori().setVisible(true);
            }
        });
    }
}