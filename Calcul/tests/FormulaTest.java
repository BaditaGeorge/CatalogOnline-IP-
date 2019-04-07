import org.junit.Test;
import static org.junit.Assert.*;

public class FormulaTest {

    @Test
    public void parsareFormula() {

        Formula MockFormula= new Formula("L + E");
        MockFormula.parsareFormula();

        //se verifica daca vectorul cu variabile a fost populat corect
        assertEquals("L", MockFormula.variabile[0]);
        assertEquals("E", MockFormula.variabile[1]);

        //se verifica numarul de variabile
        assertEquals(2, MockFormula.nrVariabile);

        //daca nu s-a gasit nicio eroare in mesajPentruFront ar trebui sa apara mesajul "Formula este valida"
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


        //formula data mai jos e intentionat gresita pentru a verica daca se detecteaza eroarea
        MockFormula.setFormula("B+ T(P + B)");
        MockFormula.parsareFormula();
        assertEquals("Eroare: Inainte de paranteza nu exista operator", MockFormula.mesajPentruFront);


        MockFormula.setFormula(" B + 1L");
        MockFormula.parsareFormula();
        assertEquals("Eroare: Variabilele nu pot incepe cu cifre",MockFormula.mesajPentruFront);

        MockFormula.setFormula("B+ min(10,L1)");
        MockFormula.parsareFormula();
        assertEquals("Formula este valida",MockFormula.mesajPentruFront);




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

        MockFormula.setFormula("L + E* (T+P)"); //aici se  pune formula normala, infixata
        MockFormula.infixToPostfix();
        assertEquals("L E T P +*+", MockFormula.formulaPostfixata); // aici cea postfixata

        //sum(E1:E3) = (E1+E2+E3) In formula postfixata sum e descompus direct in plusuri

        MockFormula.setFormula("L + sum(E1:E2)");
        MockFormula.infixToPostfix();
        assertEquals("L E1 E2 ++", MockFormula.formulaPostfixata);

        MockFormula.setFormula("L + sum(E1:E5)");
        MockFormula.infixToPostfix();
        assertEquals("L E1 E2 E3 E4 E5 +++++", MockFormula.formulaPostfixata);

        MockFormula.setFormula("P=L + sum(E1:E2)"); //nu stiu daca vom folosi egal, dar pare ca merge
        MockFormula.infixToPostfix();
        assertEquals("P L E1 E2 ++=", MockFormula.formulaPostfixata);

        MockFormula.setFormula("12*L + 16 + T");
        MockFormula.infixToPostfix();
        assertEquals("12 L *16 +T +", MockFormula.formulaPostfixata);

        MockFormula.setFormula("12*L + min(16.20,E) + T"); //aici am testat si numere rationale
        MockFormula.infixToPostfix();
        assertEquals("12 L *16.20 E ~min~+T +", MockFormula.formulaPostfixata); //poti vedea cum arata functia min in forma postfixata

        //daca in functia min trebuie sa pui expresii pune-le intre paranteze
        //in primul test se declara niste chestii, deci daca vrei sa folosesti unul ca model, foloseste-le pe urmatoarele

    }

    public void verificareUtilizareMinMax()
    {

    }





}
