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
}