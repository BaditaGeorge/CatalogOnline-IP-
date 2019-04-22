public class PaginaFormula {

    AntetMaterie antet;
    String id_materie;
    String mesajPentruFront;
    private PrelucrareDate prelucrareDate;

    public PaginaFormula(PrelucrareDate prelucrareDate) {
        this.prelucrareDate = prelucrareDate;
    }

    void generareFormula(String id_materie, String formula) {


    }

    void parsareFormule(String formule) {


    }

    void generareAntet(String id_materie, String antet) {

        String[] sir = antet.split(" ");
        AntetMaterie antetNou = new AntetMaterie(sir);
        antetNou.verificareAntetMaterie();
        mesajPentruFront = antetNou.getMesajPentruFront();
        this.prelucrareDate.functiiGestiune.raspunsDeLaCalcul(mesajPentruFront);

    }

    void schimbareAntet(String id_materie, String antet) {

    }


    void schimbareFormula(String id_materie, String formula) {

    }


    void generareCriterii(String id_materie) {

    }

    void schimbareCriterii(String id_materie) {

    }


}
