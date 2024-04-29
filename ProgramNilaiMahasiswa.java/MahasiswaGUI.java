import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class MahasiswaGUI extends JFrame {
    private JTextField tfNama, tfNIM, tfTugas, tfKuis, tfUTS, tfUAS;
    private JButton btnHitung, btnReset, btnSimpan;
    private JTextArea taOutput;

    public MahasiswaGUI() {
        setTitle("Aplikasi Nilai Mahasiswa");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel inputPanel = new JPanel(new GridLayout(7, 2));
        inputPanel.add(new JLabel("Nama"));
        tfNama = new JTextField();
        inputPanel.add(tfNama);
        inputPanel.add(new JLabel("NIM"));
        tfNIM = new JTextField();
        inputPanel.add(tfNIM);
        inputPanel.add(new JLabel("Nilai Tugas"));
        tfTugas = new JTextField();
        inputPanel.add(tfTugas);
        inputPanel.add(new JLabel("Nilai Kuis"));
        tfKuis = new JTextField();
        inputPanel.add(tfKuis);
        inputPanel.add(new JLabel("Nilai UTS"));
        tfUTS = new JTextField();
        inputPanel.add(tfUTS);
        inputPanel.add(new JLabel("Nilai UAS"));
        tfUAS = new JTextField();
        inputPanel.add(tfUAS);

        btnHitung = new JButton("Hitung");
        btnReset = new JButton("Reset");
        btnSimpan = new JButton("Simpan");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnHitung);
        buttonPanel.add(btnReset);
        buttonPanel.add(btnSimpan);

        taOutput = new JTextArea(10, 30);
        taOutput.setEditable(false);

        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(new JScrollPane(taOutput), BorderLayout.SOUTH);

        btnHitung.addActionListener(e -> hitungNilai());
        btnReset.addActionListener(e -> resetForm());
        btnSimpan.addActionListener(e -> simpanData());
    }

    private void hitungNilai() {
        String nama = tfNama.getText();
        String nim = tfNIM.getText();
        int nilaiTugas = Integer.parseInt(tfTugas.getText());
        int nilaiKuis = Integer.parseInt(tfKuis.getText());
        int nilaiUTS = Integer.parseInt(tfUTS.getText());
        int nilaiUAS = Integer.parseInt(tfUAS.getText());

        double nilaiRata = (nilaiTugas + nilaiKuis + nilaiUTS + nilaiUAS) / 4.0;
        String grade = hitungGrade(nilaiRata);
        String keterangan = (nilaiRata >= 60) ? "Lulus" : "Tidak Lulus";

        String output = String.format("Nama: %s\nNIM: %s\nRata-Rata Nilai: %.2f\nGrade: %s\nKeterangan: %s",
                nama, nim, nilaiRata, grade, keterangan);
        taOutput.setText(output);
    }

    private String hitungGrade(double rataNilai) {
        if (rataNilai >= 80) {
            return "A";
        } else if (rataNilai >= 70) {
            return "B";
        } else if (rataNilai >= 60) {
            return "C";
        } else {
            return "D";
        }
    }

    private void resetForm() {
        tfNama.setText("");
        tfNIM.setText("");
        tfTugas.setText("");
        tfKuis.setText("");
        tfUTS.setText("");
        tfUAS.setText("");
        taOutput.setText("");
    }

    private void simpanData() {
        String nama = tfNama.getText();
        String nim = tfNIM.getText();
        int nilaiTugas = Integer.parseInt(tfTugas.getText());
        int nilaiKuis = Integer.parseInt(tfKuis.getText());
        int nilaiUTS = Integer.parseInt(tfUTS.getText());
        int nilaiUAS = Integer.parseInt(tfUAS.getText());

        try (Connection connection = Koneksi.getKoneksi ();
             PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO mahasiswa (nama, nim, nilai_tugas, nilai_kuis, nilai_uts, nilai_uas) VALUES (?, ?, ?, ?, ?, ?)"
             )) {
            stmt.setString(1, nama);
            stmt.setString(2, nim);
            stmt.setInt(3, nilaiTugas);
            stmt.setInt(4, nilaiKuis);
            stmt.setInt(5, nilaiUTS);
            stmt.setInt(6, nilaiUAS);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan ke database!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal menyimpan data ke database!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MahasiswaGUI app = new MahasiswaGUI();
            app.setVisible(true);
        });
    }
}
