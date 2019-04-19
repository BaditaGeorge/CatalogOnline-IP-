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
    public String selectFormula(String id) {
        String result = "";
        String query = " Select formula_calcul from materii where id_materie=";
        query+=id;
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
    public void updateNote(String id_s, String id_m, String note)
    {
        String query = "Update materii set valori_note= ? where id_student= ? and id_materie= ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, note);
            pstmt.setString(2, id_s);
            pstmt.setString(3, id_m);
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
    //Selectam notele de la o anumita materie pentru un anumit student
    public String selectNote(String id_s,String id_m){
        String result="";
        String query="Select valori_note from materii where id_student=" + id_s+ " and id_materie=" + id_m;
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            result = rs.getString("valori_note");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    //Facem update campului formula_calcul al tabelei materii
    public void updateFormula(String id_m,String formula)
    {
        String query="Update materii set formula_calcul = ? where id_materie = ?";
        try ( Connection conn =this.connect();
              PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1,formula);
            pstmt.setString(2,id_m);
            pstmt.executeUpdate();
            System.out.println("Succes!");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //Inseram o linie in intregime(Toate campurile aferente tabelei materii) in tabela materii.
    public void insert(String id_m,String id_s,String nume,String note,String formula){
        String query = "Insert into materii(id_materie,id_student,denumire_materie,valori_note,formula_calcul) VALUES (?,?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, id_m);
            pstmt.setString(2, id_s);
            pstmt.setString(3, nume);
            pstmt.setString(4, note);
            pstmt.setString(5, formula);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
