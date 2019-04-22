import java.util.Stack;

public class Calcul {
    public Formula formula;
    public String[] antet;
    public Stack<Double> stack = new Stack<>();
    public double[] note = new double[50];


    public Calcul(String formula) {
        this.formula = new Formula(formula);
        this.formula.infixToPostfix();
    }

    String parsareNote(String stringNote) {

        int a=0;
        int n=0;
        for(int i=0; i<stringNote.length(); i++)
        {
            if(stringNote.charAt(i)!= ('0'|'1'|'2'|'3'|'4'|'5'|'6'|'7'|'8'|'9'))
            {
                while(stringNote.charAt(i)!=' ')
                {
                    antet[a] += stringNote.charAt(i);
                    i++;
                }
                a++;
                i++;
            }
            else
            {
                if(stringNote.charAt(i+1)!=' ')
                {
                    note[n] = Character.getNumericValue(stringNote.charAt(i)) * 10;
                    i++;
                }
                else
                {
                    note[n] = Character.getNumericValue(stringNote.charAt(i));
                }
                n++;
            }
        }
        evaluareFormulaPostfixata();
        return stringNote;
    }

    int getColumnOf(String var) {
        for (int i = 0; i < antet.length; i++)
            if (antet[i].equals(var)) return i;

        return -1;
    }

    void evaluareFormulaPostfixata() {

        boolean isVariable = false;
        boolean isNumber = false;
        double number, decimal;
        char currentChar;
        String var;
        int startIndex=0;
        String resultVar="";


        if(formula.formulaPostfixata.indexOf('=')>=0)
        {
            resultVar="";
            while(formula.formulaPostfixata.charAt(startIndex)==' ') startIndex++;
            currentChar=formula.formulaPostfixata.charAt(startIndex);
            while((currentChar >= 'a' && currentChar<= 'z') || (currentChar >= 'A' && currentChar <= 'Z') || (currentChar >= '0' && currentChar <= '9'))
            {
                resultVar+=currentChar;
                startIndex++;
                currentChar=formula.formulaPostfixata.charAt(startIndex);
            }

            formula.formulaPostfixata=formula.formulaPostfixata.substring(0, formula.formulaPostfixata.length() - 1);
        }

        for (int i = startIndex; i < formula.formulaPostfixata.length(); i++) {
            number = 0;

            isNumber = false;
            while (formula.formulaPostfixata.charAt(i) >= '0' && formula.formulaPostfixata.charAt(i) <= '9') {
                number = number * 10 + formula.formulaPostfixata.charAt(i) - '0';
                i++;
                isNumber = true;
            }

            if (formula.formulaPostfixata.charAt(i) == '.') {
                i++;
                decimal = 0.1;
                int pow = 10;
                while (formula.formulaPostfixata.charAt(i) >= '0' && formula.formulaPostfixata.charAt(i) <= '9') {
                    number = number + (formula.formulaPostfixata.charAt(i) - '0') * decimal;
                    number = ((double) Math.floor(number * pow)) / pow;
                    pow *= 10;
                    decimal /= 10;
                    i++;
                }

            }

            if (isNumber) {
                stack.push(number);
            }

            var = "";
            while ((formula.formulaPostfixata.charAt(i) >= 'a' && formula.formulaPostfixata.charAt(i) <= 'z') || (formula.formulaPostfixata.charAt(i) >= 'A' && formula.formulaPostfixata.charAt(i) <= 'Z') || (formula.formulaPostfixata.charAt(i) >= '0' && formula.formulaPostfixata.charAt(i) <= '9')) {
                var += formula.formulaPostfixata.charAt(i);
                isVariable = true;
                i++;
            }

            if (isVariable) {
                isVariable = false;
                int col = getColumnOf(var);
                stack.push(note[col]);

            }

            if ("+-*/%~:".indexOf(formula.formulaPostfixata.charAt(i)) >= 0) {
                double val1 = stack.pop();
                double val2 = stack.pop();

                switch (formula.formulaPostfixata.charAt(i)) {
                    case '+':
                        stack.push(val2 + val1);
                        break;
                    case '-':
                        stack.push(val2 - val1);
                        break;
                    case '*':
                        stack.push(val2 * val1);
                        break;
                    case '/':
                        stack.push(val2 / val1);
                        break;
                    case '%':
                        stack.push(val2 % val1);
                        break;
                    case '~': {
                        i++;
                        String operator = "";
                        while (formula.formulaPostfixata.charAt(i) != '~') {
                            operator += formula.formulaPostfixata.charAt(i);
                            i++;
                        }

                        switch (operator) {
                            case "min":
                                stack.push(Math.min(val2, val1));
                                break;
                            case "max":
                                stack.push(Math.max(val2, val1));
                                break;
                        }

                        break;
                    }

                    default:
                        break;


                }
            }


        }

        if(startIndex>0)
            note[getColumnOf(resultVar)]=stack.peek();


    }

    void updatePunctaj() {

    }

    void verificarePromovareMaterie() {

    }

}
