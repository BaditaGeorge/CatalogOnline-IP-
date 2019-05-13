package com.example.demo1;

public class PrelucrareDate {

    SQL_func functiiGestiune= new SQL_func();
    //MockFunctiiGestiune functiiGestiune = new MockFunctiiGestiune();
    String mesajPentruFront = "";

    /**
     * Functia va fi apelata de modulul Gestiune.
     *
     * @param mesajDeLaFront Acesta este un String ce poate avea trei formate:<br>
     *                       - "f: id_materie; formula" - va fi instaintiat un obiect PaginaForumla si apelata functia generareFormula(id_materie, formula) <br>
     *                       - "a: id_materie; antet" - va fi instantiat un obiect PaginaFormula si apeleata functia generareAntet(id_materie, antet)<br>
     *                       - "n: id_student; id_materie; note" - va fi apelata functia String selectFormula(String id_materie) si instantiat un obiect Calcul cu stringul primit drept formula. Apoi va fi apelata functia parsareNote din Calcul ce va returna notele updatate si in final va fi apelata updateNote(String id_s, String id_m, String note) in functiiGestiune.
     *                       - "c: id_materie; criterii" va fi asemanator cu primirea mesajului cu formula,  se instantiaza un obiect PaginaFormula si se apeleaza functia generareCriterii
     * @return "Update efectuat" - daca formatul este corect <br>
     * "Solicitarea pentru calcul nu are formatul dorit" - daca formatul este incorect <br>
     * "Nu se vor face calcule fara formula" - daca formula lipseste
     */


    public String primesteMesajFront(String mesajDeLaFront) {
        int i = 2;
        if (mesajDeLaFront.charAt(0) == 'f' || mesajDeLaFront.charAt(0) == 'a' || mesajDeLaFront.charAt(0) == 'c') {
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

            } else if (mesajDeLaFront.charAt(0) == 'c') {
                String criterii = "";
                while (mesajDeLaFront.charAt(i) == ' ') i++;
                while (i != mesajDeLaFront.length()) {
                    criterii += mesajDeLaFront.charAt(i);
                    i++;
                }
                return paginaFormula.generareCriterii(id_materie, criterii);
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
            if (formule.equals(""))
                return "Nu se vor face calcule fara formula";

            String criterii = functiiGestiune.selectCriterii(id_materie);
            System.out.println("Criteriile sunt = "+criterii);
            System.out.println(formule);
            System.out.println(note);
            String[] listFormule = formule.split(";");
            System.out.println("Pana aici e bine!");
            for (String formula : listFormule) {
                System.out.println("Rundaa : " + formula);
                Calcul calcul = new Calcul(formula);
                System.out.println("Parsare");
                calcul.parsareNote(note);
                System.out.println("Last step!");
                calcul.evaluareFormulaPostfixata();
                System.out.println("Dupa eval!");
                note = calcul.noteUpdatate();
                System.out.println("Dupa noteeca");
            }
            functiiGestiune.updateNote(id_student, id_materie, note);
            //System.out.println("De control!");
            String promovare="";
            if (!criterii.equals("")) {
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

            }
            
            functiiGestiune.updatePromovare(promovare, id_student, id_materie);

            return "Update efectuat";
        }
        return "Solicitarea pentru calcul nu are formatul dorit";

    }


}
