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

public class PrelucrareDate {

    public SQL_func functiiGestiune= new SQL_func();
    String mesajPentruFront="";

    public String primesteMesajFront(String mesajDeLaFront) {
        int i = 2;
        if (mesajDeLaFront.charAt(0) == 'f' || mesajDeLaFront.charAt(0) == 'a') {
            PaginaFormula paginaFormula = new PaginaFormula(this);

            String id_materie = "";
            while (mesajDeLaFront.charAt(i) == ' ') i++;
            while (mesajDeLaFront.charAt(i) != ';') {
                id_materie += mesajDeLaFront.charAt(i);
                i++;
            }
            i++;
            if (mesajDeLaFront.charAt(0) == 'f') {
                String formula = "";
                while (mesajDeLaFront.charAt(i) == ' ') i++;
                while (i != mesajDeLaFront.length()) {
                    formula += mesajDeLaFront.charAt(i);
                    i++;
                }
                return paginaFormula.generareFormula(id_materie, formula);

            } else if (mesajDeLaFront.charAt(0) == 'a') {
                String antet = "";
                while (mesajDeLaFront.charAt(i) == ' ') i++;
                while (i != mesajDeLaFront.length()) {
                    antet += mesajDeLaFront.charAt(i);
                    i++;
                }
                return paginaFormula.generareAntet(id_materie, antet);
            }
        } else if (mesajDeLaFront.charAt(0) == 'n') {
            String id_student = "", id_materie = "", note = "";
            while (mesajDeLaFront.charAt(i) == ' ') i++;
            while (mesajDeLaFront.charAt(i) != ';') {
                id_student += mesajDeLaFront.charAt(i);
                i++;
            }
            i++;
            while (mesajDeLaFront.charAt(i) == ' ') i++;
            while (mesajDeLaFront.charAt(i) != ';') {
                id_materie += mesajDeLaFront.charAt(i);
                i++;
            }
            i++;
            while (mesajDeLaFront.charAt(i) == ' ') i++;
            while (i != mesajDeLaFront.length()) {
                note += mesajDeLaFront.charAt(i);
                i++;
            }

            String formula=functiiGestiune.selectFormula(id_materie);
            if(formula.equals(""))
                return "Nu se vor face calcule fara formula";

            Calcul calcul = new Calcul(formula);
            functiiGestiune.updateNote(id_student, id_materie, calcul.parsareNote(note));


            return "Update efectuat";
        }
        return "Solicitarea pentru calcul nu are formatul dorit";

    }



}
