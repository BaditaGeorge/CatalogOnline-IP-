/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo1;

/**
 *
 * @author GoguSpoder
 */
public class User {
    public String id_User;
    public String id_Materie;
    public String note;
    User(){
        
    }
    void set(){
        id_User="A";
        id_Materie="C";
        note="D";
    }
    
    public void setId_User(String id_u){
        id_User = id_u;
    }
    public void setId_Materie(String id_m){
        id_Materie = id_m;
    }
    public void setNote(String note){
        this.note=note;
    }
    public String getId_User(){
        return id_User;
    }
    public String getId_Materie(){
        return id_Materie;
    }
    public String getNote(){
        return note;
    }
}
