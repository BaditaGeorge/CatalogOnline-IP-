import java.util.Stack;

public class Calcul {
    public Formula formula;
    public String[] antet=new String[50];
    public Stack<Double> stack = new Stack<>();
    public double[] note = new double[50];
    public int nrNote;


    public Calcul(String formula) {
        this.formula = new Formula(formula);
        this.formula.infixToPostfix();
    }

    String parsareNote(String stringNote) {

        stringNote=stringNote.replaceAll("\\s+"," ");
        String[] splited=stringNote.split(" ");
        nrNote=0;
        for(int i=0; i<splited.length; i+=2) {
            antet[i/2]=splited[i];
            nrNote++;
        }
        for(int i=1; i<splited.length; i+=2){
            int j=0;
            int pow=1;
            if(splited[i].charAt(0)=='-')
            {
                pow=-1;
                j++;
            }

            note[i/2]=0;
            while(j<splited[i].length() && splited[i].charAt(j)>='0' && splited[i].charAt(j)<='9'){
                note[i/2]=note[i/2]+(splited[i].charAt(j)-'0');
                j++;
            }


            if(j<splited[i].length() && splited[i].charAt(j)=='.')
            {
                j++;

               double decimal = 0.1;
                int pow2 = 10;
                while (j<splited[i].length() && splited[i].charAt(j) >= '0' && splited[i].charAt(j) <= '9') {
                    note[i/2] = note[i/2] + ( splited[i].charAt(j)- '0') * decimal;
                    note[i/2] = ((double) Math.floor(note[i/2] * pow2)) / pow2;
                    pow2 *= 10;
                    decimal /= 10;
                    j++;
                }
            }

            note[i/2]*=pow;
        }

        evaluareFormulaPostfixata();
        stringNote="";
        for(int i=0; i<nrNote; i++)
        {
            stringNote+=antet[i]+" ";
            stringNote+=note[i]+" ";

        }


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

        formula.infixToPostfix();


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
