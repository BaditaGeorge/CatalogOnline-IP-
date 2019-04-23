import org.junit.Test;

import static org.junit.Assert.*;

public class PaginaFormulaTest {

    @Test
    public void generareAntet() {

        PrelucrareDate prelucrareDate= new PrelucrareDate();
       /* PaginaFormula paginaFormula = new PaginaFormula(prelucrareDate);
        paginaFormula.generareAntet("381","L1 L2 WEF");
        assertEquals("Antetul este valid", paginaFormula.mesajPentruFront);

        paginaFormula.generareAntet("2", "1L");
        assertEquals("Eroare: Campurile din antet pot contine doar litere si cifre si trebuie sa inceapa cu o litera", paginaFormula.mesajPentruFront);
*/
       prelucrareDate.primesteMesajFront("n: 1; 7;  L1 5 L2 10 L3 10");

    }

    @Test
    public void generareFormula() {

        PrelucrareDate prelucrareDate= new PrelucrareDate();
        PaginaFormula paginaFormula = new PaginaFormula(prelucrareDate);
        paginaFormula.generareFormula("7","L1 = L2 +L3");
        assertEquals("Eroare: Antetul nu a fost definit", paginaFormula.mesajPentruFront);

        paginaFormula.generareFormula("381","L1 + L2 + L3");
        assertEquals("Eroare: Antetul nu a fost definit", paginaFormula.mesajPentruFront);


    }

}