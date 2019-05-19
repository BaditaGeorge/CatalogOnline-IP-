/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.sql.SQLException;
import java.sql.*;

/**
 *
 * @author GoguSpoder
 */
public class SQL_func {
    String way;
    SQL_func(String path)
    {
        way=path;
        //way = "BD_Gestiunea";
    }
    SQL_func(){
        way="C://Users/legion/Desktop/IP/Gestionare/BD_Gestiunea";
        //way = "BD_Gestiunea";
    }
    // Conectarea cu baza de date
    private Connection connect() {
        String url = "jdbc:sqlite:" + way;
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    //Selectam toate formulele de calcul din baza de date
    public String selectFormula() {
        String result = "";
        String query = " Select formula_calcul from profesori";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                 result+= rs.getString("formula_calcul") + " | ";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    //Selectam doar o singura formula din baza de date indicata de id-ul materiei
    /*public String selectFormulaCalc(String id) {
        String result = "";
        String query = " Select formula_calcul from profesori where id_materie=";
        query+=id;
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
                result =(rs.getString("formula_calcul") + "\t");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }*/
    public String selectFormulaCalc(String id) {
        String result = "";
        String query = " Select formula_calcul from profesori where id_materie=";
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
    public String selectFormula(String id){
        String result = "";
        String query = " Select formula_calcul,id_materie from profesori where id_profesor=";
        query+=id;
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
             while(rs.next())
                result += (rs.getString("formula_calcul") + " _ " + rs.getString("id_materie") + "~");

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
            result+= rs.getString("id_student") +"  " + rs.getString("id_materie") + " " + rs.getString("valori_note")+ " | ";
        }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
        return result;
    }
    
     public String selectNote(String id_m){
        String result="";
        String query="Select id_materie,id_student,valori_note from materii where id_materie=";
        query+=id_m;
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
            result+= rs.getString("id_student") +"; " + rs.getString("id_materie") + "; " + rs.getString("valori_note")+ " _ ";
        }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
        return result;
    }
    
     public void insertNewStudent(String student)
    {
        String date[]=student.split(",");
        String id = date[0];
        String nume = date[1];
        String prenume = date[2];
        String grupa = date[3];
        String eMail = date[4];
        String query = "Insert into studenti(id_student,nume,prenume,email,grupa) VALUES (?,?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, id);
            pstmt.setString(2, nume);
            pstmt.setString(3, prenume);
            pstmt.setString(4, grupa);
            pstmt.setString(5, eMail);
            pstmt.executeUpdate();
            System.out.println("Succes!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
     String getUsername(String s_id)
    {
        String result = "";
        String query = " Select username from sessions where session_id=";
        query+=s_id;
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while(rs.next())
                result+=(rs.getString("username") + "\t");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    
         void addSession(String username,String session_id,String last_activity)
    {
        String query = "Insert into sessions(session_id,username,last_activity) VALUES (?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, session_id);
            pstmt.setString(2, username);
            pstmt.setString(3, last_activity);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    void deleteSession(String session_id)
    {
        String query = "DELETE FROM sessions WHERE session_id = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, session_id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public int countSession(String session_id)
    {
        int result = 0;
        String query = "select username from sessions where session_id= " + session_id;
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next())
                result ++;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    String getTime(String s_id)
    {
        String result = "";
        String query = " Select last_activity from sessions where session_id=";
        query+=s_id;
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while(rs.next())
                result+=(rs.getString("last_activity") + "\t");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    
    void updateSessionActivity(String s_id,String new_time)
    {
        String query = "Update sessions set last_activity= ? where session_id=?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, new_time);
            pstmt.setString(2, s_id);
            // update
            pstmt.executeUpdate();
            System.out.println("Succes!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public String selectStudentRow(String idSt,String idM){
        String result = "";
        String query="Select denumire_materie,valori_note from materii where id_student="+idSt+" and id_materie="+idM;
        try{
            Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            //while(rs.next()){
                result += rs.getString("denumire_materie") + ":" + rs.getString("valori_note") + " ~ ";
            //}
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public String selectStudentCursuri(String idSt){
        String result = "";
        String query = "Select denumire_materie,id_materie from materii where id_student="+idSt;
        try{
            Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                result += rs.getString("denumire_materie") + ":" + rs.getString("id_materie") + "~";
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    //Selectam notele unui anumit student la o anumita materie
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
    //Facem update-ul formulei din tabela materii pe baza id-ul materiei
    public void updateFormula(String id_m,String formula)
    {
        String query="Update profesori set formula_calcul = ? where id_materie = ?";
        System.out.println(id_m+" "+formula+"\n"+"!!");
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
    //Inseram in tabela materii o intrare noua
    public void insertMaterii(String id_m,String id_s,String nume,String note){
        String query = "Insert into materii(id_materie,id_student,denumire_materie,valori_note) VALUES (?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, id_m);
            pstmt.setString(2, id_s);
            pstmt.setString(3, nume);
            pstmt.setString(4, note);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public String selectCatalog(String id_m, String id_p){
        String result="";
        String query="Select s.id_student,s.nume,s.prenume,s.grupa,m.valori_note from studenti s join materii m on s.id_student = m.id_student join profesori p on m.id_materie = p.id_materie where m.id_materie=" + id_m + " and p.id_profesor=" + id_p;
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                result+= rs.getString("id_student") +" " + rs.getString("nume") + " " + rs.getString("prenume")+ " " + rs.getString("grupa") + " " + rs.getString("valori_note") + " | ";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    public String selectCatalogCSV(String id_m, String id_p){
        String result="";
        String query="Select s.id_student,s.nume,s.prenume,s.grupa,m.valori_note from studenti s join materii m on s.id_student = m.id_student join profesori p on m.id_materie = p.id_materie where m.id_materie=" + id_m + " and p.id_profesor=" + id_p;
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                result+= rs.getString("id_student") +" " + rs.getString("nume") + " " + rs.getString("prenume")+ " " + rs.getString("grupa") + " " + rs.getString("valori_note") + " ~";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    public String selectDenumireMaterii(String id_p){
        String result="";
        String query="Select denumire_materie,id_materie from profesori where id_profesor=" + id_p;
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                result+= rs.getString("denumire_materie")  + ":" + rs.getString("id_materie") +  " ~";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    public String selectCurricula(){
        String result = "";
        String query="Select distinct id_materie,denumire_materie from materii";
        try(Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query)){
            while(rs.next()){
                result += rs.getString("id_materie") + " _ " + rs.getString("denumire_materie") + " ~";
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return result;
    }
    public void insertProfesori(String id_p, String nume,String prenume, String id_m,String den_m,String formula)
    {
        String query = "Insert into profesori(id_profesor,nume,prenume,id_materie,denumire_materie,formula_calcul,antet) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, id_p);
            pstmt.setString(2, nume);
            pstmt.setString(3, prenume);
            pstmt.setString(4, id_m);
            pstmt.setString(5, den_m);
            pstmt.setString(6, formula);
            pstmt.setString(7, "");

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void insertProfesori(String id_p, String nume,String prenume, String id_m,String den_m,String formula,String antet)
    {
        String query = "Insert into profesori(id_profesor,nume,prenume,id_materie,denumire_materie,formula_calcul,antet) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, id_p);
            pstmt.setString(2, nume);
            pstmt.setString(3, prenume);
            pstmt.setString(4, id_m);
            pstmt.setString(5, den_m);
            pstmt.setString(6, formula);
            pstmt.setString(7, antet);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void insertProfesori(String id_p,String den_m)
    {

        String query = "Insert into profesori(id_profesor,nume,prenume,id_materie,denumire_materie,formula_calcul,antet) VALUES (?,?,?,?,?,?,?)";
        String aux=getNumePrenumeProf(id_p);
        System.out.println(aux);
        String[] numePrenume=aux.split(" ");
        String nume=numePrenume[0];
        String prenume = numePrenume[1];
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, id_p);
            pstmt.setString(2, nume);
            pstmt.setString(3, prenume);
            pstmt.setInt(4, getMaxIdMaterie());
            pstmt.setString(5, den_m);
            pstmt.setString(6, "L=sum(L1:L14); T=L+E1+E2");
            pstmt.setString(7, "L1 L2 L3 L4 L5 L6 L7 L8 L9 L10 L11 L12 L13 L14 L E1 E2 T");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //Returneaza nummarul de utilizatori cu acelasi nume din baza de date.
    public int countUsersByName(String name) {
        int result = 0;
        String query = "select username from utilizatori where username like '" + name + "'";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next())
                result ++;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    //Returneaza valoarea din coloana salt_parola a tabelei utilizatori din baza de date.
    public String getSalt(String name) {
        String result = "";
        String query = "select salt_parola from utilizatori where username like '" + name +"'" ;
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                result+= rs.getString("salt_parola")  + " | ";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    //Returneaza numarul de utilizatori cu acelasi mail din baza de date.
    public int countUsersByMail(String mail) {
        int result = 0;
        String query = "select email from utilizatori where email like '" + mail + "'";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next())
                result ++;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    //Returneaza numarul de utilizatori cu acelasi username si cu acelasi password din baza de date.
    public int countUsersByUsernamePass(String username,String pass) {
        int result = 0;
        String query = "select username from utilizatori where username like '%" + username + "' and parola like '%" +pass + "'";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next())
                result ++;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    //Returneaza numarul de rezultate din tabela verificare cu acelasi username si cu acelasi cod din baza de date.
    public int checkAuthCode(String username, String auth_code) {
        int result = 0;
        String query = "select * from verificare where username like '" + username + "' and cod like '" +auth_code + "'";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next())
                result ++;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    public void addNewUser(String username,String email,String pas,String salt ,String auth)
    {
        String query = "Insert into utilizatori(id_utilizator,username,email,parola,salt_parola,numar_telefon,tip_utilizator,verificare) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, getMaxIdUtilizator());
            pstmt.setString(2, username);
            pstmt.setString(3, email);
            pstmt.setString(4, pas);
            pstmt.setString(5, salt);
            pstmt.setString(6, "");
            pstmt.setString(7, "Student");
            pstmt.setString(8, "0");
            pstmt.executeUpdate();
            addNewU(username,auth);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void addNewU(String username,String auth)
    {
        String query="Insert into verificare(username,cod) VALUES (?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1,username);
            pstmt.setString(2,auth);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public int getMaxIdMaterie()
    {
        int result=0;
        String query="Select id_materie as maxID from profesori";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next())
            {
                Integer aux =Integer.parseInt(rs.getString("maxID"));
                if( aux > result)
                    result = aux;
            }
        }
     catch (SQLException e) {
        System.out.println(e.getMessage());
    }
        return result+1;
    }
    public Boolean selectStudAtId(String id){
        String result = "";
        String query = "Select * from studenti where id_student="+id;
        try(Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query)){
            return rs.next();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    public Boolean getIdProfs(String idP){
        String result = "";
        String query = "Select * from profesori where id_profesor="+idP;
        try(Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query)){
                return rs.next();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    public String selectAllProfs(){
        String result = "";
        String query = "Select distinct id_profesor,nume,prenume from profesori";
        try(Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query)){
                while(rs.next()){
                    result += (rs.getString("id_profesor") + " _ " + rs.getString("nume") + " _ " + rs.getString("prenume") + " ~");
                }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return result;
    }
    public String getNumePrenumeProf(String id_p){
        String result="";
        System.out.println(id_p+"SHIT\n");
        String query="Select nume,prenume from profesori where id_profesor="+id_p;
        System.out.println(query);
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
                result+= rs.getString("nume")+ " "  +  rs.getString("prenume");
        } catch (SQLException e) {
           e.printStackTrace();
        }
        System.out.println(result);
        return result;
    }
    public void confirmUser(String username){
        String query="Update utilizatori set verificare = ? where username = ?";
        try ( Connection conn =this.connect();
              PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1,"1");
            pstmt.setString(2,username);
            pstmt.executeUpdate();
            System.out.println("Succes!");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public Integer getMaxIdUtilizator()
    {
            int result=0;
            String query="Select id_utilizator as maxID from utilizatori";
            try (Connection conn = this.connect();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next())
                {
                    Integer aux =Integer.parseInt(rs.getString("maxID"));
                    if( aux > result)
                        result = aux;
                }
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return result+1;
    }
    public boolean verificareLogIn(String username)
    {
        String query="Select verificare from utilizatori where username like '" + username + "'";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if(rs.getInt("verificare") == 0)
                return false;
            else
                return true;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;

    }
    public String selectAntet(String id_m)
    {
        String result = "";
        String query = " Select antet from profesori where id_materie=";
        query+=id_m;
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            result =(rs.getString("antet") + "\t");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    
    String selectCriterii(String id){
        String result = "";
        String query = " Select criteriiPromovare from profesori where id_materie=";
        query+=id;
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            result =(rs.getString("criteriiPromovare") + "\t");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    
     void updateCriterii(String id_m, String criterii){
        String query="Update profesori set criteriiPromovare = ? where id_materie = ?";
        try ( Connection conn =this.connect();
              PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1,criterii);
            pstmt.setString(2,id_m);
            pstmt.executeUpdate();
            System.out.println("Succes!");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public String getAccessLevel(String username)
    {
        String result="None";
        String query="select tip_utilizator from utilizatori where username=? and verificare=1";
        try ( Connection conn =this.connect();
              PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1,username);
            ResultSet rs=pstmt.executeQuery();
            while(rs.next())
            {
                result=rs.getString("tip_utilizator");
            }
            System.out.println("Succes!");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    
     void updatePromovare(String promovare,String id_s,String id_m){
        String query = "Update materii set situatiePromovare= ? where id_student= ? and id_materie= ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, promovare);
            pstmt.setString(2, id_s);
            pstmt.setString(3, id_m);
            // update
            pstmt.executeUpdate();
            System.out.println("Succes!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void updateAntet(String antet,String id_m)
    {
        String query="Update profesori set antet = ? where id_materie = ?";
        try ( Connection conn =this.connect();
              PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1,antet);
            pstmt.setString(2,id_m);
            pstmt.executeUpdate();
            System.out.println("Succes!");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    /*void updatePromovare(String promovare,String id_s,String id_m){
        String query = "Update materii set situatiePromovare= ? where id_student= ? and id_materie= ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, promovare);
            pstmt.setString(2, id_s);
            pstmt.setString(3, id_m);
            // update
            pstmt.executeUpdate();
            System.out.println("Succes!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    String selectCriterii(String id){
        String result = "";
        String query = " Select criteriiPromovare from profesori where id_materie=";
        query+=id;
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            result =(rs.getString("criteriiPromovare") + "\t");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    void updateCriterii(String id_m, String criterii){
        String query="Update profesori set criteriiPromovare = ? where id_materie = ?";
        try ( Connection conn =this.connect();
              PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1,criterii);
            pstmt.setString(2,id_m);
            pstmt.executeUpdate();
            System.out.println("Succes!");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }*/
}
