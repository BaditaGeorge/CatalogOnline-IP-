import org.junit.Test;

import static org.junit.Assert.*;

public class CalculTest {

    @Test
    public void evaluareFormulaPostfixata() {

        //Poti da copy paste pentru a scrie mai usor testele, dar incepe cu testul 2. In testul 1 se declara mai multe chestii. Sterge comentariul :)


        //Test 1
        Calcul MockCalcul = new Calcul(); // clasa cu metoda ce trebuie testata
        MockCalcul.formula = new Formula("12 + L"); // formula in care se vor inlocui variabilele

        MockCalcul.antet = new String[50]; //aici il poti privi ca pe sirul de variabile
        MockCalcul.antet[0] = "L"; //daca o variabila e in sir pe pozitia i, valoarea ei trebuie sa fie tot pe coloana i
        MockCalcul.note[0][0] = 5;

        MockCalcul.formula.infixToPostfix(); //functia asta nu a fost complet testata, dar speram ca merge bine
        MockCalcul.evaluareFormulaPostfixata(0); //momentan lucram doar pe linia 0

        Double number = 17d; // asta e valoarea finala pe care ar trebui sa obtii daca inlocuiesti valorile variabilelor in formula ta
        assertEquals(number, MockCalcul.stack.peek()); // aici se verifica daca ai obtinut ce trebuie

        MockCalcul.stack.clear(); // se goleste stiva ca sa nu incurce urmatoarele teste


        //Test 2

        MockCalcul.formula.setFormula("3 * sum(E1:E3)");
        for (int i = 1; i <= 3; i++) {
            MockCalcul.antet[i] = "E" + i;
            MockCalcul.note[0][i] = i;
        }

        MockCalcul.formula.infixToPostfix();
        MockCalcul.evaluareFormulaPostfixata(0);

        number = 18d;
        assertEquals(number, MockCalcul.stack.peek());

        MockCalcul.stack.clear();

        // Test 3

        MockCalcul.formula.setFormula("0.5*sum(E1:E4) + max(E5,10) + E6*(E7 * (E8+E9))");
        for (int i = 1; i <= 9; i++) {
            MockCalcul.antet[i] = "E" + i;
            MockCalcul.note[0][i] = 2;
        }

        MockCalcul.formula.infixToPostfix();
        MockCalcul.evaluareFormulaPostfixata(0);

        number = 30d;
        assertEquals(number, MockCalcul.stack.peek());

        MockCalcul.stack.clear();

        // Test 4

        MockCalcul.formula.setFormula("E1 * (E2+E3)* E4");
        for (int i = 1; i <= 9; i++) {
            MockCalcul.antet[i] = "E" + i;
            MockCalcul.note[0][i] = 2;
        }

        MockCalcul.formula.infixToPostfix();
        MockCalcul.evaluareFormulaPostfixata(0);

        number = 16d;
        assertEquals(number, MockCalcul.stack.peek());

        MockCalcul.stack.clear();

        //Test 5

        MockCalcul.formula.setFormula("E1 * min((E2+E3),10)* E4");  //pot fi formule ca parametri pentru functia min doar daca sunt puse intre paranteze
        for (int i = 1; i <= 9; i++) {
            MockCalcul.antet[i] = "E" + i;
            MockCalcul.note[0][i] = 2;
        }

        MockCalcul.formula.infixToPostfix();
        MockCalcul.evaluareFormulaPostfixata(0);

        number = 16d;
        assertEquals(number, MockCalcul.stack.peek());

        MockCalcul.stack.clear();

        //Test 6

        MockCalcul.formula.setFormula("E1 * min((sum(E2:E4),10)* E4");
        for (int i = 1; i <= 9; i++) {
            MockCalcul.antet[i] = "E" + i;
            MockCalcul.note[0][i] = 2;
        }

        MockCalcul.formula.infixToPostfix();
        MockCalcul.evaluareFormulaPostfixata(0);

        number = 24d;
        assertEquals(number, MockCalcul.stack.peek());

        MockCalcul.stack.clear();


    }
}