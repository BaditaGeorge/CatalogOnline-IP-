public class PaginaFormula {

    AntetMaterie antet;
    String id_materie;
    String mesajPentruFront;
    private PrelucrareDate prelucrareDate;

    public PaginaFormula(PrelucrareDate prelucrareDate) {
        this.prelucrareDate = prelucrareDate;
    }

    String parsareFormule(String id_materie, String formule) {

        String antet = prelucrareDate.functiiGestiune.selectAntet(id_materie);
        if (antet.equals("")) {
            mesajPentruFront = "Eroare: Antetul nu a fost definit";
            return mesajPentruFront;
        }
        antet = antet.replaceAll("\\s+", " ");
        String[] campuriAntet = antet.split(" ");
        AntetMaterie antetMaterie = new AntetMaterie(campuriAntet);

        String[] list = formule.split(";");
        for (String s : list) {
            Formula formula = new Formula(s);
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
        return mesajPentruFront;
    }


    String parsareCriterii(String criterii) {

        return mesajPentruFront;
    }

    String verificareOrdineFormule(Formula[] formule, int nrFormule) {
        return mesajPentruFront;
    }

    void generareCriterii(String id_materie, String criterii) {

    }

    String generareFormula(String id_materie, String formula) {

        String antet = prelucrareDate.functiiGestiune.selectAntet(id_materie);
        if (antet.equals("")) {
            mesajPentruFront = "Eroare: Antetul nu a fost definit";
            return mesajPentruFront;
        }

        Formula formulaNoua = new Formula(formula);
        formulaNoua.parsareFormula();
        mesajPentruFront = formulaNoua.getMesajPentruFront();
        if (!mesajPentruFront.equals("Formula este valida"))
            return mesajPentruFront;

        antet = antet.replaceAll("\\s+", " ");
        String[] campuriAntet = antet.split(" ");
        AntetMaterie antetMaterie = new AntetMaterie(campuriAntet);
        formulaNoua.verificareVariabileFormula(antetMaterie);
        mesajPentruFront = formulaNoua.getMesajPentruFront();
        if (mesajPentruFront.equals("Formula este valida"))
            this.prelucrareDate.functiiGestiune.updateFormula(id_materie, formula);

        return mesajPentruFront;

    }


    String generareAntet(String id_materie, String antet) {

        antet = antet.replaceAll("\\s+", " ");
        String[] sir = antet.split(" ");
        AntetMaterie antetNou = new AntetMaterie(sir);
        antetNou.verificareAntetMaterie();
        mesajPentruFront = antetNou.getMesajPentruFront();

        if (mesajPentruFront.equals("Antetul este valid"))
            this.prelucrareDate.functiiGestiune.updateAntet(id_materie, antet);

        return mesajPentruFront;


    }

    void schimbareAntet(String id_materie, String antet) {

    }


    void schimbareFormula(String id_materie, String formula) {

    }


    void schimbareCriterii(String id_materie) {

    }


}
