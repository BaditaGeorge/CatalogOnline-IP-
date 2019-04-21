public class PrelucrareDate {

    String mesajDeLaFront;
    MockFunctiiGestiune functiiGestiune= new MockFunctiiGestiune();

    public void primesteMesajFront(String mesajDeLaFront) {
        int i = 0;
        if (mesajDeLaFront.charAt(i) == 'f' || mesajDeLaFront.charAt(i) == 'a') {
            PaginaFormula paginaFormula = new PaginaFormula(this);

            String id_materie = "";
            while (mesajDeLaFront.charAt(i) == ' ') i++;
            while (mesajDeLaFront.charAt(i) != ';') {
                id_materie += mesajDeLaFront.charAt(i);
                i++;
            }
            i++;
            if (mesajDeLaFront.charAt(i) == 'f') {
                String formula = "";
                while (mesajDeLaFront.charAt(i) == ' ') i++;
                while (i != mesajDeLaFront.length()) {
                    formula += mesajDeLaFront.charAt(i);
                    i++;
                }
                paginaFormula.generareFormula(id_materie, formula);

            } else if (mesajDeLaFront.charAt(i) == 'a') {
                String antet = "";
                while (mesajDeLaFront.charAt(i) == ' ') i++;
                while (i != mesajDeLaFront.length()) {
                    antet += mesajDeLaFront.charAt(i);
                    i++;
                }
                paginaFormula.generareAntet(id_materie, antet);
            }
        } else if (mesajDeLaFront.charAt(i) == 'n') {
            String id_student = "", id_materie = "", note = "";
            while (mesajDeLaFront.charAt(i) == ' ') i++;
            while (mesajDeLaFront.charAt(i) != ';') {
                id_student += mesajDeLaFront.charAt(i);
                i++;
            }
            while (mesajDeLaFront.charAt(i) == ' ') i++;
            while (mesajDeLaFront.charAt(i) != ';') {
                id_materie += mesajDeLaFront.charAt(i);
                i++;
            }
            while (mesajDeLaFront.charAt(i) == ' ') i++;
            while (mesajDeLaFront.charAt(i) != ';') {
                note += mesajDeLaFront.charAt(i);
                i++;
            }
            Calcul calcul = new Calcul(functiiGestiune.selectFormula(id_materie));
            functiiGestiune.updateNote(id_student, id_materie, calcul.parsareNote(note));
        }
    }

    public void TrimiteMesajCatreFront(String mesajPentruFront)
    {

    }

    public void updateBazaDeDate(String id_materie) {

    }

    boolean cautareFormulaInBD(String id_materie) {
        return false;
    }

}
