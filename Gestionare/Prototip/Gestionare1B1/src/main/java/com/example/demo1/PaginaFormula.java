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
public class PaginaFormula {

    AntetMaterie antet;
    String id_materie;
    String mesajPentruFront;
    private PrelucrareDate prelucrareDate;

    public PaginaFormula(PrelucrareDate prelucrareDate) {
        this.prelucrareDate = prelucrareDate;
    }

    String generareFormula(String id_materie, String formula) {

<<<<<<< HEAD
        String antet = prelucrareDate.functiiGestiune.selectAntet(id_materie);
        if (antet.equals("")) {
            mesajPentruFront = "Eroare: Antetul nu a fost definit";
            return mesajPentruFront;
        }
        antet = antet.replaceAll("\\s+", " ");
        System.out.println(antet);
        String[] campuriAntet = antet.split(" ");
        AntetMaterie antetMaterie = new AntetMaterie(campuriAntet);
       
        
        String[] list = formule.split(";");
        Formula[] listFormule=new Formula[list.length];
        int index=0;
        for (String s : list) {
            Formula formula = new Formula(s);
            listFormule[index++]=formula;
            formula.parsareFormula();
            mesajPentruFront = formula.getMesajPentruFront();
            if (!mesajPentruFront.equals("Formula este valida"))
                return mesajPentruFront;
            else {
                formula.verificareVariabileFormula(antetMaterie);
                mesajPentruFront = formula.getMesajPentruFront();
                if (!mesajPentruFront.equals("Formula este valida"))
                    return mesajPentruFront;
            }
        }
        String feedback=verificareOrdineFormule(listFormule,list.length);
        if(feedback.charAt(0)=='E') mesajPentruFront=feedback;
        else
        {
            feedback=feedback.replaceAll("\\s+", " ");
            String[] numbers = feedback.split(" ");
            int nr;
            formuleReturnate="";
            for(String number: numbers){
                nr=0;
                for(int i=number.length()-1; i>=0; i--){
                    nr=nr*10+number.charAt(i)-'0';
                }

                formuleReturnate+=(list[nr]+ "; ");

            }
        }
=======
        String antet=prelucrareDate.functiiGestiune.selectAntet(id_materie);
        if(antet.equals(""))
            return "Eroare: Antetul nu a fost definit";
>>>>>>> 0bb654169dee5ca819153b6b545173719cdf30af



        Formula formulaNoua=new Formula(formula);
        formulaNoua.parsareFormula();
        mesajPentruFront=formulaNoua.getMesajPentruFront();
        if(!mesajPentruFront.equals("Formula este valida"))
            return mesajPentruFront;

        String[] campuriAntet=antet.split(" ");
        AntetMaterie antetMaterie=new AntetMaterie(campuriAntet);
        formulaNoua.verificareVariabileFormula(antetMaterie);
        mesajPentruFront=formulaNoua.getMesajPentruFront();
        if(mesajPentruFront.equals("Formula este valida"))
            this.prelucrareDate.functiiGestiune.updateFormula(id_materie,formula);

        return mesajPentruFront;

    }



    String generareAntet(String id_materie, String antet) {

        String[] sir = antet.split(" ");
        AntetMaterie antetNou = new AntetMaterie(sir);
        antetNou.verificareAntetMaterie();
        mesajPentruFront = antetNou.getMesajPentruFront();

<<<<<<< HEAD
        if (mesajPentruFront.equals("Antetul este valid"))
            this.prelucrareDate.functiiGestiune.updateAntet(antet,id_materie);
=======
        if(mesajPentruFront.equals("Antetul este valid"))
            this.prelucrareDate.functiiGestiune.updateAntet(id_materie,antet);
>>>>>>> 0bb654169dee5ca819153b6b545173719cdf30af

        return mesajPentruFront;

    }

    void schimbareAntet(String id_materie, String antet) {

    }


    void schimbareFormula(String id_materie, String formula) {

    }


    void generareCriterii(String id_materie) {

    }

    void schimbareCriterii(String id_materie) {

    }

    void parsareFormule(String formule) {


    }


}
