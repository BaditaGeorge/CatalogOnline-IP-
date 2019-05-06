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

        String antet=prelucrareDate.functiiGestiune.selectAntet(id_materie);
        if(antet.equals(""))
            return "Eroare: Antetul nu a fost definit";



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

        if(mesajPentruFront.equals("Antetul este valid"))
            this.prelucrareDate.functiiGestiune.updateAntet(id_materie,antet);

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
