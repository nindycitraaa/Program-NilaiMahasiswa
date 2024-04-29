import java.sql.Connection; // Perbaiki impor paket menjadi java.sql.Connection
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {
    private static final String url = "jdbc:mysql://localhost:3306/nilaimahasiswa";
    private static final String username = "username";
    private static final String password = "password";

    public static Connection getKoneksi() throws SQLException {
        return DriverManager.getConnection(url, username, password); // Perbaiki pemanggilan metode
    }
}

