import org.junit.Test;
import static org.junit.Assert.*;

public class FormulaTest {

    @Test
    public void parsareFormula() {

        Formula MockFormula= new Formula("L + E");
        MockFormula.parsareFormula();
        assertEquals("L", MockFormula.variabile[0]);
        assertEquals("E", MockFormula.variabile[1]);
        assertEquals(2, MockFormula.nrVariabile);
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
        int pozitieParantezaInchisa;
        Formula MockFormula=new Formula("E+sum(L1:L14)");

        pozitieParantezaInchisa=MockFormula.verificaUtilizareSum(5);
        assertEquals(12,pozitieParantezaInchisa);
        assertEquals(14,MockFormula.nrVariabile);
        for(int i=0; i<14; i++) // pe pozitia 0 ar trebui sa fie variabila E, dar se apeleaza direct verificaUtilizareSum, asa ca E e ignorat
        {
            String var="L";
            var+=(i+1);
            assertEquals(var,MockFormula.variabile[i]);
        }

        MockFormula.setFormula("sum(L2:L4)");
        pozitieParantezaInchisa=MockFormula.verificaUtilizareSum(3);
        assertEquals(9,pozitieParantezaInchisa);
        assertEquals(3,MockFormula.nrVariabile);
        for(int i=0; i<3; i++)
        {
            String var="L";
            var+=(i+2);
            assertEquals(var,MockFormula.variabile[i]);
        }


        MockFormula.setFormula("sum(L1:E5)");
        pozitieParantezaInchisa=MockFormula.verificaUtilizareSum(3);
        assertEquals(-1,pozitieParantezaInchisa);
        assertEquals("Eroare: Variabilele din sum nu fac parte din aceeasi componenta", MockFormula.mesajPentruFront);

        MockFormula.setFormula("sum(L4:L2)");
        pozitieParantezaInchisa=MockFormula.verificaUtilizareSum(3);
        assertEquals(-1,pozitieParantezaInchisa);
        assertEquals("Eroare: Ordinea variabilelor din sum nu e corecta", MockFormula.mesajPentruFront);

    }

    @Test
    public void infixToPostfix()
    {
        Formula MockFormula=new Formula("L + E");
        MockFormula.infixToPostfix();
        assertEquals("L E +", MockFormula.formulaPostfixata);

        MockFormula.setFormula("L + E* (T+P)");
        MockFormula.infixToPostfix();
        assertEquals("L E T P +*+", MockFormula.formulaPostfixata);

        MockFormula.setFormula("L + sum(E1:E2)");
        MockFormula.infixToPostfix();
        assertEquals("L E1 E2 :+", MockFormula.formulaPostfixata);


    }





}
