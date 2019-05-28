import org.junit.Test;

import static org.junit.Assert.*;

public class CalculTest {

    @Test
    public void evaluareFormulaPostfixata() {



        //Test 1
        Calcul MockCalcul = new Calcul("L1=12 + L"); // clasa cu metoda ce trebuie testata
        // formula in care se vor inlocui variabilele

        MockCalcul.antet = new String[50]; //aici il poti privi ca pe sirul de variabile
        MockCalcul.antet[0] = "L1"; //daca o variabila e in sir pe pozitia i, valoarea ei trebuie sa fie tot pe coloana i
        MockCalcul.antet[1] = "L";
        MockCalcul.note[0] = 0;
        MockCalcul.note[1]=12;

        //MockCalcul.formula.infixToPostfix(); //functia asta nu a fost complet testata, dar speram ca merge bine
        MockCalcul.evaluareFormulaPostfixata(); //momentan lucram doar pe linia 0

        Double number = 24d; // asta e valoarea finala pe care ar trebui sa obtii daca inlocuiesti valorile variabilelor in formula ta
        assertEquals(number, MockCalcul.stack.peek()); // aici se verifica daca ai obtinut ce trebuie

        MockCalcul.stack.clear(); // se goleste stiva ca sa nu incurce urmatoarele teste


    }


    @Test
    public void parsareNote() {


        Calcul MockCalcul = new Calcul("P = 12 + L");
        MockCalcul.parsareNote("P 5 L -5.5");
        MockCalcul.evaluareFormulaPostfixata();
        String noteNoi=MockCalcul.noteUpdatate();
        assertEquals("P 6.5 L -5.5 ", noteNoi);



    }








}