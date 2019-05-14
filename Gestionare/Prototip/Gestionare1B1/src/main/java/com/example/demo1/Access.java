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
public class Access {
    public SQL_func funct=new SQL_func();
    public boolean isStudent(String username){
        return funct.getAccessLevel(username).equals("Student");


    }
    public boolean isProfesor(String username){
        return funct.getAccessLevel(username).equals("Profesor");


    }
    public boolean isSecretar(String username){
        return funct.getAccessLevel(username).equals("Secretar");


    }
    public boolean isAdministrator(String username){
        return funct.getAccessLevel(username).equals("Administrator");

    }

}
