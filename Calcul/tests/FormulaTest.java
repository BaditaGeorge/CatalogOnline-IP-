import org.junit.Test;
import static org.junit.Assert.*;

public class FormulaTest {

    @Test
    public void parsareFormula() {

        Formula MockFormula= new Formula("L + E");
        MockFormula.parsareFormula();
        assertEquals("L", MockFormula.variabile[0]);
        assertEquals("E", MockFormula.variabile[1]);
        assertEquals("Formula este valida", MockFormula.mesajPentruFront);


        MockFormula.setFormula("L+E1");
        MockFormula.parsareFormula();
        assertEquals("L", MockFormula.variabile[0]);
        assertEquals("E1", MockFormula.variabile[1]);
        assertEquals("Formula este valida", MockFormula.mesajPentruFront);

        MockFormula.setFormula("L");
        MockFormula.parsareFormula();
        assertEquals("L", MockFormula.variabile[0]);
        assertEquals("Formula este valida", MockFormula.mesajPentruFront);

        MockFormula.setFormula("L * (T+E)");
        MockFormula.parsareFormula();
        assertEquals("L", MockFormula.variabile[0]);
        assertEquals("T", MockFormula.variabile[1]);
        assertEquals("E", MockFormula.variabile[2]);
        assertEquals("Formula este valida", MockFormula.mesajPentruFront);

        MockFormula.setFormula("L * T ( P + B)");
        MockFormula.parsareFormula();
        assertEquals("Eroare: Inainte de paranteza nu exista operator", MockFormula.mesajPentruFront);


        MockFormula.setFormula(" B + 1L");
        MockFormula.parsareFormula();
        assertEquals("Eroare: Variabilele nu pot incepe cu cifre",MockFormula.mesajPentruFront);


    }

    @Test
    public void verificaUtilizareSum()
    {
        int rezultat;
        Formula MockFormula=new Formula("E+sum(L1:L14)");

        rezultat=MockFormula.verificaUtilizareSum(5);
        assertEquals(12,rezultat);

        MockFormula.setFormula("sum(L2:L4)");
        rezultat=MockFormula.verificaUtilizareSum(3);
        assertEquals(9,rezultat);

        MockFormula.setFormula("sum(L1:E5)");
        rezultat=MockFormula.verificaUtilizareSum(3);
        assertEquals(-1,rezultat);
        assertEquals("Eroare: Variabilele din sum nu fac parte din aceeasi componenta", MockFormula.mesajPentruFront);

        MockFormula.setFormula("sum(L4:L2)");
        rezultat=MockFormula.verificaUtilizareSum(3);
        assertEquals(-1,rezultat);
        assertEquals("Eroare: Ordinea variabilelor din sum nu e corecta", MockFormula.mesajPentruFront);





    }
}