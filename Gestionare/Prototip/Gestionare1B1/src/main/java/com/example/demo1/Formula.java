package com.example.demo1;
import java.util.Stack;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;

public class Formula {
    public String formula;
    public String formulaPostfixata = "";
    public int nrVariabile = 0;
    public String criteriiPromovare;
    public String[] variabile = new String[50];
    public String mesajPentruFront;



    public Formula(String formula) {
        this.formula = formula;
    }

    public Formula() {
    }



    //used only for tests
    public void setFormula(String formula) {
        this.formula = formula;
        nrVariabile = 0;
        formulaPostfixata = "";
    }

    public void setCriteriiPromovare(String criteriiPromovare) {
        this.criteriiPromovare = criteriiPromovare;
    }

    //primeste un index de unde incepe paranteza pentru sum, returneaza  pozitia primei paranteze inchise pe care o gaseste daca functia sum a fost folosita corect si -1 altfel
    // Ex: Pentru formula "P+E+sum(L1:L14)" functia primeste indexul 7 si returneaza 14
    // Pentru "P + sum(L)" returneaza -1
    public int verificaUtilizareSum(int index) {
        int i = index;
        int indexFinal = -1;
        String var1 = "", var2 = "";
        String func = formula.substring(index, formula.length());

        if (func.indexOf(')') < 0) {
            mesajPentruFront = "Eroare: Nu s-a inchis paranteza pentru sum";
            return -1;
        }

        func = func.substring(1, func.indexOf(')') + 1);

        if (func.indexOf(':') < 0) {
            mesajPentruFront = "Eroare: Functia sum nu contine caracterul \':\'";
            return -1;
        }
        i = 0;
        while (i < func.indexOf(':') && func.charAt(i) == ' ') i++;
        var1 = func.substring(i, func.indexOf(':'));

        i = func.indexOf(':') + 1;
        while (i < func.indexOf(')') && func.charAt(i) == ' ') i++;
        var2 = func.substring(i, func.indexOf(')'));

        int indexVar1 = 0, indexVar2 = 0;
        while (indexVar1 < var1.length() && ((var1.charAt(indexVar1) >= 'a' && var1.charAt(indexVar1) <= 'z') || (var1.charAt(indexVar1) >= 'A' && var1.charAt(indexVar1) <= 'Z')))
            indexVar1++;
        while (indexVar2 < var2.length() && ((var2.charAt(indexVar2) >= 'a' && var2.charAt(indexVar2) <= 'z') || (var2.charAt(indexVar2) >= 'A' && var2.charAt(indexVar2) <= 'Z')))
            indexVar2++;

        if (indexVar1 != indexVar2) {
            mesajPentruFront = "Eroare: Variabilele din sum nu fac parte din aceeasi componenta";
            return -1;
        }
        if (indexVar1 == 0) {
            mesajPentruFront = "Eroare: Variabila din sum trebuie sa inceapa cu o litera";
            return -1;
        }
        for (i = 0; i < indexVar1; i++)
            if (var1.charAt(i) != var2.charAt(i)) {
                mesajPentruFront = "Eroare: Variabilele din sum nu fac parte din aceeasi componenta";
                return -1;
            }

        int num1 = 0, num2 = 0;
        for (i = indexVar1; i < var1.length(); i++) {
            if (var1.charAt(i) < '0' || var1.charAt(i) > '9') {
                while (i < var1.length() && var1.charAt(i) == ' ') i++;
                if (i < var1.length()) {
                    mesajPentruFront = "Eroare: Variabilele din sum trebuie sa inceapa cu litere si sa se termine cu cifre";
                    return -1;
                }
            }
            num1 = num1 * 10 + (var1.charAt(i) - '0');
        }

        for (i = indexVar2; i < var2.length(); i++) {
            if (var2.charAt(i) < '0' || var2.charAt(i) > '9') {
                while (i < var2.length() && var2.charAt(i) == ' ') i++;
                if (i < var2.length()) {
                    mesajPentruFront = "Eroare: Variabilele din sum trebuie sa inceapa cu litere si sa se termine cu cifre";
                    return -1;
                }
            }
            num2 = num2 * 10 + (var2.charAt(i) - '0');
        }

        if (num1 > num2) {
            mesajPentruFront = "Eroare: Ordinea variabilelor din sum nu e corecta";
            return -1;

        }

        var1 = var1.substring(0, indexVar1);

        for (i = num1; i <= num2; i++)
            variabile[nrVariabile++] = var1 + i;

        i = index;
        while (formula.charAt(i) != ')') i++;

        return i;

    }

    public int verificaUtilizareMinMax(int index) {
        int i = index + 1;
        int indexFinal = -1;

        String f1 = ""; //primul termen al functiei
        String f2 = ""; //al doilea termen al functiei
        int form1 = 0, form2 = 0, error = 0;
        boolean checkFormula1 = false, checkFormula2 = false;

        while (i < formula.length()) {
            while (i < formula.length() && formula.charAt(i) != ',') {
                if ((i == index + 1 && formula.charAt(i) == '(') || (formula.charAt(i) == ')' && i < formula.length() - 1 && formula.charAt(i + 1) == ',')) {
                    form1++;
                    i++;
                } else if (formula.charAt(i) == ')' && i == formula.length() - 1)
                    i++;
                else if (formula.charAt(i) != ' ') {
                    f1 += formula.charAt(i);
                    if ("+-/*%:".indexOf(formula.charAt(i)) >= 0)
                        checkFormula1 = true;
                    i++;
                } else i++;
            }

            //System.out.println("Formula 1:" + f1);
            if (form1 != 2 && checkFormula1) {
                mesajPentruFront = "Eroare: Primul termen este o formula care nu este introdusa intre paranteze";
                return -1;
            }

            if (i == formula.length() || formula.charAt(i) != ',') {
                mesajPentruFront = "Eroare: Termenii nu sunt separati prin virgula";
                return -1;
            } else {
                i++;
                int parantezeDeschise = 0, parantezeInchise = 0;

                while (formula.charAt(i) == ' ') i++;

                if (formula.charAt(i) == '(') {
                    parantezeDeschise++;
                    form2++;
                    while (parantezeDeschise != parantezeInchise) {
                        if (formula.charAt(i) != ' ') {
                            if (formula.charAt(i) == '(')
                                parantezeDeschise++;
                            if (formula.charAt(i) == ')')
                                parantezeInchise++;
                            if ("+-/*%:".indexOf(formula.charAt(i)) >= 0)
                                checkFormula2 = true;
                            if (parantezeDeschise != parantezeInchise)
                                f2 += formula.charAt(i);
                        }
                        i++;
                    }

                    if (formula.charAt(i - 1) == ')') {
                        indexFinal = i - 1;
                        form2++;
                    } else error = 1;
                    i = formula.length();
                } else {
                    while (formula.charAt(i) != ')' && i != formula.length() - 1) {
                        if (formula.charAt(i) != ' ')
                            f2 += formula.charAt(i);
                        if ("+-/*%:".indexOf(formula.charAt(i)) >= 0)
                            checkFormula2 = true;

                        i++;
                    }

                    if (formula.charAt(i) == ')')
                        indexFinal = i;
                    else error = 1;
                    i = formula.length();
                }

                //System.out.println("Formula 2:" + f2);
                if (form2 != 2 && checkFormula2) {
                    mesajPentruFront = "Eroare: Al doilea termen este o formula care nu este introdusa intre paranteze";
                    return -1;
                } else if (error == 1) {
                    mesajPentruFront = "Eroare: Paranteze folosite incorect";
                    return -1;
                }
            }
        }

        if (checkFormula1 == true) {
            Formula formula1 = new Formula(f1);
            formula1.parsareFormula();
            if (mesajPentruFront != null) {
                mesajPentruFront = "Eroare: Functia nu e folosita corespunzator - formula invalida";
                return -1;
            }
        } else {
            int check = checkIsValid(f1);
            if (check == -1) {
                return -1;
            } else if (check == 1)
                variabile[nrVariabile++] = f1;
        }


        if (checkFormula2 == true) {
            Formula formula2 = new Formula(f2);
            formula2.parsareFormula();
            if (mesajPentruFront != null) {
                mesajPentruFront = "Eroare: Functia nu e folosita corespunzator - formula invalida";
                return -1;
            }
        } else {
            int check = checkIsValid(f2);
            if (check == 1)
                variabile[nrVariabile++] = f2;
            else if (check == -1) {
                return -1;
            }
        }

        return indexFinal;

    }

    int checkIsValid(String f) {
        int i = 0;

        if (f.charAt(i) == '0' && f.length() > 1) {
            mesajPentruFront = "Eroare: Numerele nu pot incepe cu 0";
            return -1;
        } else if ("0123456789".indexOf(f.charAt(0)) >= 0 && f.length() == 1)
            return 2;

        while (i < f.length() && (f.charAt(i) >= '0' && f.charAt(i) <= '9'))
            i++;

        // f este un numar
        if (i == f.length())
            return 2;
        else if (f.charAt(0) >= '0' && f.charAt(0) <= '9' && "0123456789".indexOf(f.charAt(i)) < 0) {
            mesajPentruFront = "Eroare: Variabila invalida";
            return -1;
        }

        //f este o variabila
        i = 0;
        while (i < f.length()) {
            if ((f.charAt(i) >= 'a' && f.charAt(i) <= 'z') || (f.charAt(i) >= 'A' && f.charAt(i) <= 'Z') || (f.charAt(i) >= '0' && f.charAt(i) <= '9'))
                i++;
            else break;
        }

        if (i == f.length())
            return 1;

        return -1;
    }

    boolean isVariable(int equalSignIndex){
        int index=0;
        String var="";
        while(formula.charAt(index)==' ' && index<equalSignIndex)index++;
        if(index>=equalSignIndex) return false;

        if(isDigit(formula.charAt(index))) return false;
        while(index<equalSignIndex && (isLetter(formula.charAt(index))||isDigit(formula.charAt(index)))){
            var+=formula.charAt(index);
            index++;
        }

        if(index==equalSignIndex)
        {
            variabile[nrVariabile++]=var;
            return true;
        }
        while(index<equalSignIndex && formula.charAt(index)==' ') index++;

        if(index==equalSignIndex)
        {
            variabile[nrVariabile++]=var;
            return true;
        }
        return false;
    }


    //parcurge formula, verifica daca e corecta si determina variabilele

    void parsareFormula() {
        if(formula.indexOf('=')<0)
            mesajPentruFront="Eroare: Formula trebuie sa fie de forma <variabila>=<expresie>";
        else{
            if(!isVariable(formula.indexOf('=')))
                mesajPentruFront="Eroare: Formula trebuie sa fie de forma <variabila>=<expresie>";
            else
            {
                String expression=formula.substring(formula.indexOf('=')+1);
                formula=expression;
                parsareExpresie(expression);
            }
        }


    }

    void parsareCriteriu() {
        parsareExpresie(criteriiPromovare);
        if(mesajPentruFront.equals("Formula este valida"))
            mesajPentruFront="Criteriul este valid";
    }

    void parsareExpresie(String formula) {
        int i;
        boolean isVariable = false;
        boolean isNumber = false;
        boolean isOperator = true;
        int nrParantezeDeschise = 0;

        String var = "";

        for (i = 0; i < formula.length(); i++) {
            if (formula.charAt(i) == ' ') {
                if (isVariable) {
                    variabile[nrVariabile++] = var;
                    var = "";
                }

                isVariable = false;
                isNumber = false;
            } else if ((formula.charAt(i) >= 'a' && formula.charAt(i) <= 'z') || (formula.charAt(i) >= 'A' && formula.charAt(i) <= 'Z')) {
                if (isVariable) {
                    var = var + formula.charAt(i);
                } else if (!isVariable) {
                    if (isOperator) {
                        isVariable = true;
                        isOperator = false;
                        var = var + formula.charAt(i);
                    } else if (isNumber) {
                        mesajPentruFront = "Eroare: Variabilele nu pot incepe cu cifre";
                        return;
                    } else {
                        mesajPentruFront = "Eroare: Intre doi operanzi nu exista operator";
                        return;
                    }
                }

            } else if (formula.charAt(i) == '(') {
                nrParantezeDeschise++;

                if (isVariable) {
                    variabile[nrVariabile++] = var;
                }

                if (!isOperator && !variabile[nrVariabile - 1].equals("sum") && !variabile[nrVariabile - 1].equals("min") && !variabile[nrVariabile - 1].equals("max")) {
                    mesajPentruFront = "Eroare: Inainte de paranteza nu exista operator";
                    return;
                } else if (nrVariabile >= 1 && variabile[nrVariabile - 1].equals("sum") && isVariable) {
                    nrVariabile--;

                    int newIndex = verificaUtilizareSum(i);

                    if (newIndex == -1) return;

                    i = newIndex;
                    nrParantezeDeschise--;


                } else if (nrVariabile >= 1 && (variabile[nrVariabile - 1].equals("min") || variabile[nrVariabile - 1].equals("max")) && isVariable) {

                    nrVariabile--;

                    int newIndex = verificaUtilizareMinMax(i);

                    if (newIndex == -1) return;

                    i = newIndex;
                    nrParantezeDeschise--;


                } else {
                    isVariable = false;
                    isOperator = true;
                }


            } else if ("+-/*%=!><&|".indexOf(formula.charAt(i)) >= 0) {
                if (isOperator) {
                    mesajPentruFront = "Eroare: Nu pot exista doi operatori unul dupa altul";
                    return;
                } else {
                    if (isVariable) {
                        variabile[nrVariabile++] = var;
                        var = "";
                    }
                    if(i<formula.length()-1)
                    {
                        if(formula.charAt(i)=='=' && formula.charAt(i+1)=='=') i++;
                        else if((formula.charAt(i)=='!' || formula.charAt(i)=='<' || formula.charAt(i)=='>')&& formula.charAt(i+1)=='=') i++;
                        else if(formula.charAt(i)=='&' && formula.charAt(i)=='&') i++;
                        else if(formula.charAt(i)=='|' && formula.charAt(i)=='|') i++;

                    }

                    isVariable = false;
                    isNumber = false;
                    isOperator = true;
                }

            } else if (formula.charAt(i) >= '0' && formula.charAt(i) <= '9') {
                if (isOperator) {
                    isOperator = false;
                    isNumber = true;
                } else if (isVariable)
                    var = var + formula.charAt(i);
            } else if (formula.charAt(i) == ')') {
                nrParantezeDeschise--;
                if (nrParantezeDeschise < 0) {
                    mesajPentruFront = "Eroare: Paranteze folosite incorect";
                    return;
                }
                if (isOperator) {
                    mesajPentruFront = "Eroare: Paranteza inchisa dupa operator";
                    return;
                }

                if (isVariable) {
                    variabile[nrVariabile++] = var;
                    var = "";
                }

            } else if (formula.charAt(i) == '.') {
                if (isOperator || isVariable) {
                    mesajPentruFront = "Eroare: Punctul poate fi folosit doar in numere rationale";
                    return;
                } else
                    isNumber = true;


            } else {
                mesajPentruFront = "Eroare: Caracter nepermis";
                return;
            }

        }

        if (i == formula.length()) {
            if (nrParantezeDeschise != 0) {
                mesajPentruFront = "Eroare: Paranteze folosite incorect";
                return;
            }
            if (isVariable && !var.equals("sum") && !var.equals("min") && !var.equals("max"))
                variabile[nrVariabile++] = var;
            mesajPentruFront = "Formula este valida";

        }


    }

    int adaugaVariabileDinSum(String var1, String var2) {
        int i = 0;
        int nr1 = 0, nr2 = 0;
        String rad = "";
        while (i < var1.length() && ((var1.charAt(i) >= 'a' && var1.charAt(i) <= 'z') || (var1.charAt(i) >= 'A' && var1.charAt(i) <= 'Z'))) {
            rad += var1.charAt(i);
            i++;
        }

        while (i < var1.length() && (var1.charAt(i) >= '0' && var1.charAt(i) <= '9')) {
            nr1 = nr1 * 10 + var1.charAt(i) - '0';
            i++;
        }
        i = 0;

        while (i < var2.length() && ((var2.charAt(i) >= 'a' && var2.charAt(i) <= 'z') || (var2.charAt(i) >= 'A' && var2.charAt(i) <= 'Z')))
            i++;

        while (i < var2.length() && (var2.charAt(i) >= '0' && var2.charAt(i) <= '9')) {
            nr2 = nr2 * 10 + var2.charAt(i) - '0';
            i++;
        }

        for (i = nr1; i <= nr2; i++)
            formulaPostfixata += rad + i + " ";

        return nr2 - nr1;


    }
    void infixToPostfix(){
        infixToPostfixGeneral(formula);
    }
    void infixToPostfixCriteriu(){
        infixToPostfixGeneral(criteriiPromovare);
    }

    void infixToPostfixGeneral(String formula) {
        boolean justReadAVariable = false;
        boolean isMin = false;
        int sum = 0;
        int nrPlusuri = 0;
        String sumVar1;
        String sumVar2;
        Stack<String> stack = new Stack<>();
        stack.push("#");
        int l = formula.length();

        sumVar1 = "";
        sumVar2 = "";

        for (int i = 0; i < l; i++) {
            justReadAVariable = false;

            while (i < l && ((formula.charAt(i) >= 'a' && formula.charAt(i) <= 'z') || (formula.charAt(i) >= 'A' && formula.charAt(i) <= 'Z') || (formula.charAt(i) >= '0' && formula.charAt(i) <= '9') || formula.charAt(i) == '.')) {


                if (sum == 1) sumVar1 += formula.charAt(i);
                else if (sum == 2) sumVar2 += formula.charAt(i);
                else formulaPostfixata += formula.charAt(i);

                i++;
                justReadAVariable = true;


            }
            if (justReadAVariable) {
                if (sum == 1) sum = 2;
                else if (sum == 2) {
                    nrPlusuri = adaugaVariabileDinSum(sumVar1, sumVar2);
                    for (int j = 0; j < nrPlusuri; j++)
                        stack.push("+");

                    sum = 0;
                } else if (formulaPostfixata.length() >= 3) {
                    if (formula.charAt(i - 1) == 'm' && formula.charAt(i - 2) == 'u' && formula.charAt(i - 3) == 's') {
                        if (formulaPostfixata.length() == 3) formulaPostfixata = "";
                        else {
                            String deleteSumString = formulaPostfixata.substring(0, formulaPostfixata.length() - 3);
                            formulaPostfixata = deleteSumString;
                        }
                        sum = 1;
                        sumVar1 = "";
                        sumVar2 = "";

                    } else if (formula.charAt(i - 1) == 'x' && formula.charAt(i - 2) == 'a' && formula.charAt(i - 3) == 'm') {
                        if (formulaPostfixata.length() == 3) formulaPostfixata = "";
                        else {
                            String deleteSumString = formulaPostfixata.substring(0, formulaPostfixata.length() - 3);
                            formulaPostfixata = deleteSumString;
                            isMin = false;
                        }
                    } else if (formula.charAt(i - 1) == 'n' && formula.charAt(i - 2) == 'i' && formula.charAt(i - 3) == 'm') {
                        if (formulaPostfixata.length() == 3) formulaPostfixata = "";
                        else {
                            String deleteSumString = formulaPostfixata.substring(0, formulaPostfixata.length() - 3);
                            formulaPostfixata = deleteSumString;
                            isMin = true;
                        }
                    } else
                        formulaPostfixata += " ";
                } else formulaPostfixata += " ";
            }

            if (i >= l)
                break;

            if (formula.charAt(i) == '(')
                stack.push("(");

            else if (formula.charAt(i) == ')') {
                while (!stack.peek().equals("#") && !stack.peek().equals("(")) {
                    formulaPostfixata += stack.peek();
                    stack.pop();
                }

                if (stack.peek().equals("("))
                    stack.pop();
            } else if ("+-/*%=!><&|".indexOf(formula.charAt(i)) >= 0) {
                String currentOperator = "";
                boolean doubleCharOperator=false;
                if(i<formula.length()-1)
                {
                    if(formula.charAt(i)=='=' && formula.charAt(i+1)=='='){
                        currentOperator+="~==~";
                        doubleCharOperator=true;
                        i++;
                    }
                    else if((formula.charAt(i)=='!' || formula.charAt(i)=='<' || formula.charAt(i)=='>')&& formula.charAt(i+1)=='='){
                        currentOperator+="~";
                        currentOperator+=formula.charAt(i);
                        currentOperator+='=';
                        currentOperator+="~";
                        doubleCharOperator=true;
                        i++;
                    }
                    else if(formula.charAt(i)=='&' && formula.charAt(i)=='&') {
                        currentOperator+="~&&~";
                        doubleCharOperator=true;
                        i++;
                    }
                    else if(formula.charAt(i)=='|' && formula.charAt(i)=='|'){
                        currentOperator+="~||~";
                        doubleCharOperator=true;
                        i++;
                    }
                }

                if(!doubleCharOperator) currentOperator += formula.charAt(i);
                while (!stack.peek().equals("#") && precedence(currentOperator) <= precedence(stack.peek())) {
                    formulaPostfixata += stack.peek();
                    stack.pop();
                }


                stack.push(currentOperator);


            } else if (formula.charAt(i) == ',') {
                String currentOperator = "~";
                if (isMin) currentOperator += "min~";
                else currentOperator += "max~";

                while (!stack.peek().equals("#") && precedence(currentOperator) <= precedence(stack.peek())) {
                    formulaPostfixata += stack.peek();
                    stack.pop();
                }

                stack.push(currentOperator);
            }


        }

        while (!stack.peek().equals("#")) {
            formulaPostfixata += stack.peek();
            stack.pop();
        }


    }

    int precedence(String operator) {
        switch (operator) {
            case "~max~":
                return 8;
            case "~min~":
                return 8;
            case ":":
                return 8;
            case "%":
                return 7;
            case "*":
                return 7;
            case "/":
                return 7;
            case "+":
                return 6;
            case "-":
                return 6;
            case "~<=~":
                return 5;
            case "~>=~":
                return 5;
            case ">":
                return 5;
            case "<":
                return 5;
            case "~!=~":
                return 4;
            case "~==~":
                return 4;
            case "~&&~":
                return 3;
            case "~||~":
                return 2;
            case "=":
                return 1;
            default:
                return -1;
        }
    }

    public void verificareVariabileCriteriu(AntetMaterie antet) {
        verificareVariabileExpresie(antet);
        if(mesajPentruFront.equals("Formula este valida"))
            mesajPentruFront="Criteriul este valid";
    }

    public void verificareVariabileFormula(AntetMaterie antet) {
        verificareVariabileExpresie(antet);
    }

    public void verificareVariabileExpresie(AntetMaterie antet) {
        int k = 0;
        boolean check;
        if (nrVariabile == 0)
            return;
        String[] raspuns = new String[nrVariabile];

        if (antet.nrCampuri == 0)
            for (int i = 0; i < nrVariabile; i++)
                raspuns[i] = variabile[i];
        else
            for (int i = 0; i < nrVariabile; i++) {
                check = false;
                for (int j = 0; j < antet.nrCampuri; j++)
                    if (variabile[i].equals(antet.CampuriAntet[j]))
                        check = true;
                if (!check)
                    raspuns[k++] = this.variabile[i];
            }

        if (raspuns[0] != null) {
            mesajPentruFront = "Eroare: Urmatoarele variabile din formula nu sunt definite in antet:";
            for (int i = 0; i < raspuns.length; i++)
                if (raspuns[i] != null)
                    mesajPentruFront += " " + raspuns[i];
        } else
            mesajPentruFront = "Formula este valida";

    }




    String getMesajPentruFront() {
        return mesajPentruFront;
    }

}
