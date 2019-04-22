import org.junit.Test;

import static org.junit.Assert.*;

public class PaginaFormulaTest {

    @Test
    public void generareAntet() {

         PrelucrareDate prelucrareDate= new PrelucrareDate();
        PaginaFormula paginaFormula = new PaginaFormula(prelucrareDate);
        paginaFormula.generareAntet("381","L1 L2 WEF");
        assertEquals("Antetul este valid", paginaFormula.mesajPentruFront);

        paginaFormula.generareAntet("2", "1L");
        assertEquals("Eroare: Campurile din antet pot contine doar litere si cifre si trebuie sa inceapa cu o litera", paginaFormula.mesajPentruFront);


    }

    @Test
    public void generareFormula() {

        PrelucrareDate prelucrareDate= new PrelucrareDate();
        PaginaFormula paginaFormula = new PaginaFormula(prelucrareDate);
        paginaFormula.generareFormula("381","L1 + L2");
        assertEquals("Formula este valida", paginaFormula.mesajPentruFront);

        paginaFormula.generareFormula("381","L1 + L2 + L3");
        assertEquals("Eroare: Urmatoarele variabile din formula nu sunt definite in antet: L3", paginaFormula.mesajPentruFront);


    }

}