import org.junit.Test;

import static org.junit.Assert.*;

public class PaginaFormulaTest {



    @Test
    public void verificaOrdineFormule() {
        PrelucrareDate prelucrareDate = new PrelucrareDate();
        PaginaFormula paginaFormula = new PaginaFormula(prelucrareDate);

        Formula[] f1 = new Formula[2];
        int i = -1;
        f1[++i] = new Formula("a=b+c");
        f1[0].parsareFormula();
        f1[++i] = new Formula("d=e+f");
        f1[i].parsareFormula();
        assertEquals("1 0", paginaFormula.verificareOrdineFormule(f1, 2));

        Formula[] f2 = new Formula[4];
        i = -1;
        f2[++i] = new Formula("a=b+c+d");
        f2[0].parsareFormula();
        f2[++i] = new Formula("c=e+f");
        f2[i].parsareFormula();
        f2[++i] = new Formula("d=g+h");
        f2[i].parsareFormula();
        f2[++i] = new Formula("h=x+y+z");
        f2[i].parsareFormula();
        assertEquals("3 2 1 0", paginaFormula.verificareOrdineFormule(f2, 4));

        Formula[] f3 = new Formula[3];
        i = -1;
        f3[++i] = new Formula("a=b+c");
        f3[i].parsareFormula();
        f3[++i] = new Formula("b=c+d");
        f3[i].parsareFormula();
        f3[++i] = new Formula("c=a+b");
        f3[i].parsareFormula();
        assertEquals("Eroare: Formulele nu sunt independente - exista circuite in calculul punctajului", paginaFormula.verificareOrdineFormule(f3, 3));
    }

}