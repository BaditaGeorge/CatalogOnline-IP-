public class PaginaFormula {

    AntetMaterie antet;
    String id_materie;
    String mesajPentruFront;
    private PrelucrareDate prelucrareDate;

    public PaginaFormula(PrelucrareDate prelucrareDate) {
        this.prelucrareDate = prelucrareDate;
    }

    String generareFormula(String id_materie, String formula) {

        return mesajPentruFront;
    }

    void parsareFormule(String formule) {


    }

    String generareAntet(String id_materie, String antet) {

        //in constructor antet e de tip String[], functia asta e folosita in alte parti cu prototipul asta si am preferat sa convertesc decat sa modific parametrii
        String[] sir = new String[1];
        sir[0] = antet;
        AntetMaterie antetNou = new AntetMaterie(sir);
        antetNou.verificareAntetMaterie();
        mesajPentruFront = antetNou.getMesajPentruFront();
        this.prelucrareDate.functiiGestiune.raspunsDeLaCalcul(mesajPentruFront);
        if (mesajPentruFront.equals("Antetul este valid"))
            this.prelucrareDate.functiiGestiune.updateNote(antet, id_materie, "10");  //nu am inteles de unde va fi luat stringul cu note
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


}
