import org.junit.Test;

import static org.junit.Assert.*;

public class CalculTest {

    @Test
    public void evaluareFormulaPostfixata() {

        //Test 1
        Calcul MockCalcul = new Calcul("12 + L"); // clasa cu metoda ce trebuie testata
        // formula in care se vor inlocui variabilele

        MockCalcul.antet = new String[50]; //aici il poti privi ca pe sirul de variabile
        MockCalcul.antet[0] = "L"; //daca o variabila e in sir pe pozitia i, valoarea ei trebuie sa fie tot pe coloana i
        MockCalcul.note[0] = 5;

        MockCalcul.formula.infixToPostfix(); //functia asta nu a fost complet testata, dar speram ca merge bine
        MockCalcul.evaluareFormulaPostfixata(); //momentan lucram doar pe linia 0

        Double number = 17d; // asta e valoarea finala pe care ar trebui sa obtii daca inlocuiesti valorile variabilelor in formula ta
        assertEquals(number, MockCalcul.stack.peek()); // aici se verifica daca ai obtinut ce trebuie

        MockCalcul.stack.clear(); // se goleste stiva ca sa nu incurce urmatoarele teste


        //Test 2

        MockCalcul.formula.setFormula("3 * sum(E1:E3)");
        for (int i = 1; i <= 3; i++) {
            MockCalcul.antet[i] = "E" + i;
            MockCalcul.note[i] = i;
        }

        MockCalcul.formula.infixToPostfix();
        MockCalcul.evaluareFormulaPostfixata();

        number = 18d;
        assertEquals(number, MockCalcul.stack.peek());

        MockCalcul.stack.clear();

        // Test 3

        MockCalcul.formula.setFormula("0.5*sum(E1:E4) + max(E5,10) + E6*(E7 * (E8+E9))");
        for (int i = 1; i <= 9; i++) {
            MockCalcul.antet[i] = "E" + i;
            MockCalcul.note[i] = 2;
        }

        MockCalcul.formula.infixToPostfix();
        MockCalcul.evaluareFormulaPostfixata();

        number = 30d;
        assertEquals(number, MockCalcul.stack.peek());

        MockCalcul.stack.clear();

        // Test 4

        MockCalcul.formula.setFormula("E1 * (E2+E3)* E4");
        for (int i = 1; i <= 9; i++) {
            MockCalcul.antet[i] = "E" + i;
            MockCalcul.note[i] = 2;
        }

        MockCalcul.formula.infixToPostfix();
        MockCalcul.evaluareFormulaPostfixata();

        number = 16d;
        assertEquals(number, MockCalcul.stack.peek());

        MockCalcul.stack.clear();

        //Test 5

        MockCalcul.formula.setFormula("E1 * min((E2+E3),10)* E4");  //pot fi formule ca parametri pentru functia min doar daca sunt puse intre paranteze
        for (int i = 1; i <= 9; i++) {
            MockCalcul.antet[i] = "E" + i;
            MockCalcul.note[i] = 2;
        }

        MockCalcul.formula.infixToPostfix();
        MockCalcul.evaluareFormulaPostfixata();

        number = 16d;
        assertEquals(number, MockCalcul.stack.peek());

        MockCalcul.stack.clear();

        //Test 6

        MockCalcul.formula.setFormula("E1 * min((sum(E2:E4),10)* E4");
        for (int i = 1; i <= 9; i++) {
            MockCalcul.antet[i] = "E" + i;
            MockCalcul.note[i] = 2;
        }

        MockCalcul.formula.infixToPostfix();
        MockCalcul.evaluareFormulaPostfixata();

        number = 24d;
        assertEquals(number, MockCalcul.stack.peek());

        MockCalcul.stack.clear();

        //Test 7 - formula ip  10*(LAB+PROIECT+BONUS+EXAMEN)/MAX_PCT_FARA_BONUS 360?

        MockCalcul.formula.setFormula("10*(sum(E1:E4))/360");
        for (int i = 1; i <= 4; i++)
            MockCalcul.antet[i] = "E" + i;
        MockCalcul.note[1] = 100;
        MockCalcul.note[2] = 140;
        MockCalcul.note[3] = 15;
        MockCalcul.note[4] = 105;

        MockCalcul.formula.infixToPostfix();
        MockCalcul.evaluareFormulaPostfixata();
        number = 10d;
        assertEquals(number, MockCalcul.stack.peek());

        //Test 8 formula TW PF = P * 0.1 + A * 0.2 + S * 0.5 + T(t1+t2+t3) * 0.2 + E(e1+e2+e3) * 0.1,

        MockCalcul.formula.setFormula("E1*0.1+E2*0.2+E3*0.5+sum(E4:E6)*0.2+sum(E7:E9)*0.1");
        for (int i = 1; i <= 9; i++)
            MockCalcul.antet[i] = "E" + i;
        MockCalcul.note[1] = 7;
        MockCalcul.note[2] = 9;
        MockCalcul.note[3] = 8;
        MockCalcul.note[4] = 1.25;
        MockCalcul.note[5] = 2.5;
        MockCalcul.note[6] = 1.25;
        MockCalcul.note[7] = 2;
        MockCalcul.note[8] = 2;
        MockCalcul.note[9] = 1;
        MockCalcul.formula.infixToPostfix();
        MockCalcul.evaluareFormulaPostfixata();
        number = 8d;
        assertEquals(number, MockCalcul.stack.peek());

        //Test 9 calcul modulo
        MockCalcul.formula.setFormula("E1%9");
        MockCalcul.antet[1] = "E" + 1;
        MockCalcul.note[1] = 6;
        MockCalcul.formula.infixToPostfix();
        MockCalcul.evaluareFormulaPostfixata();
        number = 6d;
        assertEquals(number, MockCalcul.stack.peek());

        //Test 10 formula retele 0.4*P+0.3*E+0.2*L+1;

        MockCalcul.formula.setFormula("E1*0.4+E2*0.3+E3*0.2+1");
        for (int i = 1; i <= 3; i++)
            MockCalcul.antet[i] = "E" + i;
        MockCalcul.note[1] = 7.25;
        MockCalcul.note[2] = 6.5;
        MockCalcul.note[3] = 5.25;
        MockCalcul.formula.infixToPostfix();
        MockCalcul.evaluareFormulaPostfixata();
        number = 6.9;
        assertEquals(number, MockCalcul.stack.peek());

        //Test 11 formula lfac 0.3*(T1+T2)/2+0.2*P+0.3*(T1+T2)/2+0.2*E;

        MockCalcul.formula.setFormula("(sum(E1:E2)/2)*0.3+E3*0.2+(sum(E4:E5)/2)*0.3+E6*0.2");
        for (int i = 1; i <= 6; i++)
            MockCalcul.antet[i] = "E" + i;
        MockCalcul.note[1] = 10;
        MockCalcul.note[2] = 10;
        MockCalcul.note[3] = 6.25;
        MockCalcul.note[4] = 10;
        MockCalcul.note[5] = 4;
        MockCalcul.note[6] = 9.25;
        MockCalcul.formula.infixToPostfix();
        MockCalcul.evaluareFormulaPostfixata();
        number = 8.2;
        assertEquals(number, MockCalcul.stack.peek());
    }

}