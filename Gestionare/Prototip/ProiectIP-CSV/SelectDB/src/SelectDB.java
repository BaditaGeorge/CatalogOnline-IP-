// clasa ce va selecta date din baza de date si le va returna ca si stringuri
// momentan: prototip; se lucreaza la conectarea cu sqlite
import java.sql.*;

public class SelectDB {

    private Connection connect() {
        String url = "jdbc:sqlite:/Users/andi/Desktop/IP/gm/test.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
}

    public void selectAll() {
        String sql = "SELECT name FROM COMPANY";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println(rs.getString("name") + "\t");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /*public void getCapacityGreaterThan(double capacity){
        String sql = "SELECT id, name, capacity " + "FROM warehouses WHERE capacity > ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1,capacity);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t" +
                                    rs.getString("name") + "\t" +
                                    rs.getDouble("capacity"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    } */

    public static void main(String[] args) {
        SelectDB app = new SelectDB();
        app.selectAll();
    }
}
