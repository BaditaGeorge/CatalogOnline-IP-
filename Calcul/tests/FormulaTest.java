import org.junit.Test;

import static org.junit.Assert.*;

public class FormulaTest {

    @Test
    public void parsareFormula() {

        Formula MockFormula = new Formula("L + E");
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
        assertEquals("Eroare: Variabilele nu pot incepe cu cifre", MockFormula.mesajPentruFront);

        MockFormula.setFormula("B+ min(10,L1)");
        MockFormula.parsareFormula();
        assertEquals("Formula este valida", MockFormula.mesajPentruFront);

        MockFormula.setFormula("sum(L1:L3)");
        MockFormula.parsareFormula();
        assertEquals("Formula este valida", MockFormula.mesajPentruFront);
    }

    @Test
    public void verificaUtilizareSum() {
        int pozitieParantezaInchisa;
        Formula MockFormula = new Formula("E+sum(L1:L14)");

        pozitieParantezaInchisa = MockFormula.verificaUtilizareSum(5);
        assertEquals(12, pozitieParantezaInchisa);
        assertEquals(14, MockFormula.nrVariabile);
        for (int i = 0; i < 14; i++) // pe pozitia 0 ar trebui sa fie variabila E, dar se apeleaza direct verificaUtilizareSum, asa ca E e ignorat
        {
            String var = "L";
            var += (i + 1);
            assertEquals(var, MockFormula.variabile[i]);
        }

        MockFormula.setFormula("sum(L2:L4)");
        pozitieParantezaInchisa = MockFormula.verificaUtilizareSum(3);
        assertEquals(9, pozitieParantezaInchisa);
        assertEquals(3, MockFormula.nrVariabile);
        for (int i = 0; i < 3; i++) {
            String var = "L";
            var += (i + 2);
            assertEquals(var, MockFormula.variabile[i]);
        }


        MockFormula.setFormula("sum(L1:E5)");
        pozitieParantezaInchisa = MockFormula.verificaUtilizareSum(3);
        assertEquals(-1, pozitieParantezaInchisa);
        assertEquals("Eroare: Variabilele din sum nu fac parte din aceeasi componenta", MockFormula.mesajPentruFront);

        MockFormula.setFormula("sum(L4:L2)");
        pozitieParantezaInchisa = MockFormula.verificaUtilizareSum(3);
        assertEquals(-1, pozitieParantezaInchisa);
        assertEquals("Eroare: Ordinea variabilelor din sum nu e corecta", MockFormula.mesajPentruFront);

    }

    @Test
    public void infixToPostfix() {
        Formula MockFormula = new Formula("L + E");
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

        MockFormula.setFormula("P = L + sum(E1:E2)"); //nu stiu daca vom folosi egal, dar pare ca merge
        MockFormula.infixToPostfix();
        assertEquals("P L E1 E2 ++=", MockFormula.formulaPostfixata);

        MockFormula.setFormula("12*L + 16 + T");
        MockFormula.infixToPostfix();
        assertEquals("12 L *16 +T +", MockFormula.formulaPostfixata);

        MockFormula.setFormula("12*L + min(16.20,E) + T"); //aici am testat si numere rationale
        MockFormula.infixToPostfix();
        assertEquals("12 L *16.20 E ~min~+T +", MockFormula.formulaPostfixata); //poti vedea cum arata functia min in forma postfixata

        MockFormula.setFormula("E1 * min(sum(E2:E4),10)* E4");
        MockFormula.infixToPostfix();
        assertEquals("E1 E2 E3 E4 ++10 ~min~*E4 *", MockFormula.formulaPostfixata);

        //IP: 10*(LAB+PROIECT+BONUS+EXAMEN)/MAX_PCT_FARA_BONUS 360
        MockFormula.setFormula("10*(sum(E1:E4))/360");
        MockFormula.infixToPostfix();
        assertEquals("10 E1 E2 E3 E4 +++*360 /", MockFormula.formulaPostfixata);

        //TW PF = P * 0.1 + A * 0.2 + S * 0.5 + T(t1+t2+t3) * 0.2 + E(e1+e2+e3) * 0.1
        MockFormula.setFormula("P*0.1+A*0.2+S*0.5+sum(T1:T3)*0.2+sum(E1:E3)*0.1");
        MockFormula.infixToPostfix();
        assertEquals("P 0.1 *A 0.2 *+S 0.5 *+T1 T2 T3 ++0.2 *+E1 E2 E3 ++0.1 *+", MockFormula.formulaPostfixata);

        //modulo
        MockFormula.setFormula("E1%9");
        MockFormula.infixToPostfix();
        assertEquals("E1 9 %", MockFormula.formulaPostfixata);

        //Retele:0.4*P+0.3*E+0.2*L+1
        MockFormula.setFormula("P*0.4+E*0.3+L*0.2+1");
        MockFormula.infixToPostfix();
        assertEquals("P 0.4 *E 0.3 *+L 0.2 *+1 +", MockFormula.formulaPostfixata);

        //LFAC: 0.3*(T1+T2)/2+0.2*P+0.3*(T1+T2)/2+0.2*E
        MockFormula.setFormula("(sum(T1:T2)/2)*0.3+P*0.2+(sum(T1:T2)/2)*0.3+E*0.2");
        MockFormula.infixToPostfix();
        assertEquals("T1 T2 +2 /0.3 *P 0.2 *+T1 T2 +2 /0.3 *+E 0.2 *+", MockFormula.formulaPostfixata);

        //daca in functia min trebuie sa pui expresii pune-le intre paranteze
        //in primul test se declara niste chestii, deci daca vrei sa folosesti unul ca model, foloseste-le pe urmatoarele

    }

    @Test
    public void verificareUtilizareMinMax() {
        int pozitieParantezaInchisa;
        Formula MockFormula = new Formula("min(L, E)");
        pozitieParantezaInchisa = MockFormula.verificaUtilizareMinMax(3);
        assertEquals(8, pozitieParantezaInchisa);
        assertEquals("L", MockFormula.variabile[0]);
        assertEquals("E", MockFormula.variabile[1]);

        MockFormula.setFormula("min((L+E+F),2)");
        pozitieParantezaInchisa = MockFormula.verificaUtilizareMinMax(3);
        assertEquals(13, pozitieParantezaInchisa);

        MockFormula.setFormula("E+S+min((E + S), L1)");
        pozitieParantezaInchisa = MockFormula.verificaUtilizareMinMax(7);
        assertEquals(19, pozitieParantezaInchisa);

        MockFormula.setFormula("E+S+min((E + S), (sum(L1 : L10)))");
        pozitieParantezaInchisa = MockFormula.verificaUtilizareMinMax(7);
        assertEquals(32, pozitieParantezaInchisa);

        MockFormula.setFormula("E+S+min((E + S), (sum(L1:L12) + E))");
        pozitieParantezaInchisa = MockFormula.verificaUtilizareMinMax(7);
        assertEquals(34, pozitieParantezaInchisa);

        //formulele date ca termeni ai functiei sunt intre paranteze
        MockFormula.setFormula("E+S+min(E + S, (sum(L1:L12) + E))");
        pozitieParantezaInchisa = MockFormula.verificaUtilizareMinMax(7);
        assertEquals(-1, pozitieParantezaInchisa);
        assertEquals("Eroare: Primul termen este o formula care nu este introdusa intre paranteze", MockFormula.mesajPentruFront);

        MockFormula.setFormula("E+S+min((E + S), sum(L1:L12) + E)");
        pozitieParantezaInchisa = MockFormula.verificaUtilizareMinMax(7);
        assertEquals(-1, pozitieParantezaInchisa);
        assertEquals("Eroare: Al doilea termen este o formula care nu este introdusa intre paranteze", MockFormula.mesajPentruFront);

        MockFormula.setFormula("min(ES)");
        pozitieParantezaInchisa = MockFormula.verificaUtilizareMinMax(3);
        assertEquals(-1, pozitieParantezaInchisa);
        assertEquals("Eroare: Termenii nu sunt separati prin virgula", MockFormula.mesajPentruFront);

        MockFormula.setFormula("min(L,D");
        pozitieParantezaInchisa = MockFormula.verificaUtilizareMinMax(3);
        assertEquals(-1, pozitieParantezaInchisa);
        assertEquals("Eroare: Paranteze folosite incorect", MockFormula.mesajPentruFront);

        MockFormula.setFormula("E+S+min((B+ T(P + B)), (sum(L1:L12) + E + P / sum(D2:D10)))");
        pozitieParantezaInchisa = MockFormula.verificaUtilizareMinMax(7);
        assertEquals(-1, pozitieParantezaInchisa);
        assertEquals("Eroare: Functia nu e folosita corespunzator - formula invalida", MockFormula.mesajPentruFront);

        MockFormula.setFormula("E+S+min(A, (E + 1L))");
        pozitieParantezaInchisa = MockFormula.verificaUtilizareMinMax(7);
        assertEquals(-1, pozitieParantezaInchisa);
        assertEquals("Eroare: Functia nu e folosita corespunzator - formula invalida", MockFormula.mesajPentruFront);

        MockFormula.setFormula("E+S+min(01, L)");
        pozitieParantezaInchisa = MockFormula.verificaUtilizareMinMax(7);
        assertEquals(-1, pozitieParantezaInchisa);
        assertEquals("Eroare: Numerele nu pot incepe cu 0", MockFormula.mesajPentruFront);

        MockFormula.setFormula("E+S+min(1L, 0)");
        pozitieParantezaInchisa = MockFormula.verificaUtilizareMinMax(7);
        assertEquals(-1, pozitieParantezaInchisa);
        assertEquals("Eroare: Variabila invalida", MockFormula.mesajPentruFront);

        MockFormula.setFormula("E+S+min(0, 1L)");
        pozitieParantezaInchisa = MockFormula.verificaUtilizareMinMax(7);
        assertEquals(-1, pozitieParantezaInchisa);
        assertEquals("Eroare: Variabila invalida", MockFormula.mesajPentruFront);

        MockFormula.setFormula("E+S+min(100, L)");
        pozitieParantezaInchisa = MockFormula.verificaUtilizareMinMax(7);
        assertEquals(14, pozitieParantezaInchisa);
        assertEquals("Eroare: Variabila invalida", MockFormula.mesajPentruFront);
        assertEquals("L", MockFormula.variabile[0]);
    }

    @Test
    public void verificareVariabileFormula(){

        Formula MockFormula = new Formula("L+E+D");
        MockFormula.parsareFormula();
        String[] init = {"D"};
        AntetMaterie antet = new AntetMaterie(init);
        MockFormula.verificareVariabileFormula(antet);
        assertEquals("Eroare: Urmatoarele variabile din formula nu sunt definite in antet: L E", MockFormula.mesajPentruFront);

        MockFormula.setFormula("A+B+C");
        MockFormula.parsareFormula();
        String[] sir1 = {"A","B","C"};
        antet.setCampuriAntet(sir1);
        MockFormula.verificareVariabileFormula(antet);
        assertEquals("Formula este valida", MockFormula.mesajPentruFront);

        MockFormula.setFormula("sum(A:B)");
        MockFormula.parsareFormula();
        String[] sir2 = {"A,B"};
        antet.setCampuriAntet(sir2);
        MockFormula.verificareVariabileFormula(antet);
        assertEquals("Formula este valida", MockFormula.mesajPentruFront);

        MockFormula.setFormula("min(E1,E2)");
        MockFormula.parsareFormula();
        String[] sir3 = {"E1", "E2"};
        antet.setCampuriAntet(sir3);
        MockFormula.verificareVariabileFormula(antet);
        assertEquals("Formula este valida", MockFormula.mesajPentruFront);

        MockFormula.setFormula("max(A,B)");
        String[] sir4 = {"A","B"};
        antet.setCampuriAntet(sir4);
        MockFormula.parsareFormula();
        MockFormula.verificareVariabileFormula(antet);
        assertEquals("Formula este valida", MockFormula.mesajPentruFront);

        MockFormula.setFormula("0.5*sum(E1:E2) + max(E3,10) + E4*(E5 * (E6+E7))");
        MockFormula.parsareFormula();
        String[] sir5 = {""};
        antet.setCampuriAntet(sir5);
        MockFormula.verificareVariabileFormula(antet);
        assertEquals("Eroare: Urmatoarele variabile din formula nu sunt definite in antet: E1 E2 E3 E4 E5 E6", MockFormula.mesajPentruFront);
    }
}
