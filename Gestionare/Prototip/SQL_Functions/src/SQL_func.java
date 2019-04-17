import java.sql.*;

public class SQL_func {
    // Conectarea cu baza de date
    private Connection connect() {
        String url = "jdbc:sqlite:B://Faculta/IP/BD_Gestiunea";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    //Selectam toate formulele de calcul din baza de date
    public String selectFormula() {
        String result = "";
        String query = " Select denumire_materie, formula_calcul from materii";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                 result+= rs.getString("denumire_materie") +"  " + rs.getString("formula_calcul") + " | ";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    //Selectam doar o singura formula din baza de date indicata de id-ul materiei
    public String selectFormula(Integer id) {
        String result = "";
        String query = " Select formula_calcul from materii where id_materie=";
        query+=id.toString();
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
                result =(rs.getString("formula_calcul") + "\t");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    //Facem update-ul campului valori_note din baza de date pentru un anumit student la o anumita materie
    public void updateNote(Integer id_s, Integer id_m, String note)
    {
        String query = "Update materii set valori_note= ? where id_student= ? and id_materie= ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, note);
            pstmt.setInt(2, id_s);
            pstmt.setInt(3, id_m);
            // update
            pstmt.executeUpdate();
            System.out.println("Succes!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //Selectam toate notele existente in baza de date 
    public String selectNote(){
        String result="";
        String query="Select id_materie,id_student,valori_note from materii";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                result+= rs.getInt("id_student") +"  " + rs.getInt("id_materie") + " " + rs.getString("valori_note")+ " | ";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}
