/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo1;



public class PrelucrareDate {

    SQL_func functiiGestiune= new SQL_func("C://Users/legion/Desktop/IP/Gestionare/BD_Gestiunea");
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

         
            String formule = functiiGestiune.selectFormulaCalc(id_materie);
      
            formule = formule.replaceAll("\\s+", "");
            if (formule.equals(""))
            {
                functiiGestiune.updateNote(id_student, id_materie, note);
                return "Nu se vor face calcule fara formula";

            }


            String[] listFormule = formule.split(";");
              
            for (String formula : listFormule) {
                Calcul calcul = new Calcul(formula);
               System.out.println("he!!");
                calcul.parsareNote(note);
                 System.out.println("h!!");
                calcul.evaluareFormulaPostfixata();
                  System.out.println("h");
                note = calcul.noteUpdatate();
                
            }
             System.out.println("hei!");
            functiiGestiune.updateNote(id_student, id_materie, note);
            
            System.out.println("hei!!");
             
            
            String promovare="";
            criterii = criterii.replaceAll("\\s+", "");
            if (criterii != null && !criterii.isEmpty()) {
                System.out.println("criterii" + criterii);
                boolean promovat=false;
                String[] listCriterii=criterii.split(";");
                for (String criteriu : listCriterii) {
                    Calcul calcul = new Calcul();
                    calcul.formula=new Formula();
                    calcul.formula.setCriteriiPromovare(criteriu);
                    calcul.formula.infixToPostfixCriteriu();
                    calcul.parsareNote(note);
                    calcul.evaluareFormulaPostfixataCriteriu();
                    promovat=calcul.getPromovat();
                    promovare+=promovat+" ";

                }
               
                functiiGestiune.updatePromovare(promovare, id_student, id_materie);
            }
             

   
            Calcul calcul = new Calcul(formula);
            functiiGestiune.updateNote(id_student, id_materie, calcul.parsareNote(note));



            return "Update efectuat";
        }
        return "Solicitarea pentru calcul nu are formatul dorit";

    }



}
