import java.util.Stack;

public class Calcul {
    public Formula formula;
    public String[] antet=new String[50];
    public Stack<Double> stack = new Stack<>();
    public double[] note = new double[50];
    public int nrNote;
    private String resultVar="";
    private boolean promovat=false;

    public Calcul(){

    }

    public Calcul(String formula) {
        this.formula = new Formula(formula);

    }

    public boolean getPromovat(){
        return promovat;
    }

    void parsareNote(String stringNote) {

        stringNote = stringNote.replaceAll("\\s+", " ");
        String[] splited = stringNote.split(" ");
        nrNote = 0;
        for (int i = 0; i < splited.length; i += 2) {
            antet[i / 2] = splited[i];
            nrNote++;
        }
        for (int i = 1; i < splited.length; i += 2) {
            int j = 0;
            int pow = 1;
            if (splited[i].charAt(0) == '-') {
                pow = -1;
                j++;
            }

            note[i / 2] = 0;
            while (j < splited[i].length() && splited[i].charAt(j) >= '0' && splited[i].charAt(j) <= '9') {
                note[i / 2] = note[i / 2] * 10 + (splited[i].charAt(j) - '0');
                j++;
            }


            if (j < splited[i].length() && splited[i].charAt(j) == '.') {
                j++;

                double decimal = 0.1;
                int pow2 = 10;
                while (j < splited[i].length() && splited[i].charAt(j) >= '0' && splited[i].charAt(j) <= '9') {
                    note[i / 2] = note[i / 2] + (splited[i].charAt(j) - '0') * decimal;
                    note[i / 2] = ((double) Math.floor(note[i / 2] * pow2)) / pow2;
                    pow2 *= 10;
                    decimal /= 10;
                    j++;
                }
            }

            note[i / 2] *= pow;
        }
    }

    String noteUpdatate(){
        String stringNote="";
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

    void evaluareFormulaPostfixata(){
        int startIndex=0,i=0;
        formula.infixToPostfix();
        while(formula.getFormula().charAt(i)!='='){
            if(formula.getFormula().charAt(startIndex)!=' ')
            {
                resultVar+=formula.getFormula().charAt(startIndex);
                startIndex++;
            }
            i++;
        }
        startIndex++;
        formula.setFormulaPostfixata(formula.getFormulaPostfixata().substring(0, formula.getFormulaPostfixata().length() - 1));

        evaluareFormulaPostfixataGenerala(startIndex);

    }
    void evaluareFormulaPostfixataCriteriu(){

        evaluareFormulaPostfixataGenerala(0);
        if(stack.peek()==0.0) promovat=false;
        else promovat=true;
    }

    void evaluareFormulaPostfixataGenerala(int startIndex) {

        boolean isVariable = false;
        boolean isNumber = false;
        double number, decimal;
        char currentChar;
        String var;


        for (int i = startIndex; i < formula.getFormulaPostfixata().length(); i++) {
            number = 0;

            isNumber = false;
            while (formula.getFormulaPostfixata().charAt(i) >= '0' && formula.getFormulaPostfixata().charAt(i) <= '9') {
                number = number * 10 + formula.getFormulaPostfixata().charAt(i) - '0';
                i++;
                isNumber = true;
            }

            if (formula.getFormulaPostfixata().charAt(i) == '.') {
                i++;
                decimal = 0.1;
                int pow = 10;
                while (formula.getFormulaPostfixata().charAt(i) >= '0' && formula.getFormulaPostfixata().charAt(i) <= '9') {
                    number = number + (formula.getFormulaPostfixata().charAt(i) - '0') * decimal;
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
            while ((formula.getFormulaPostfixata().charAt(i) >= 'a' && formula.getFormulaPostfixata().charAt(i) <= 'z') || (formula.getFormulaPostfixata().charAt(i) >= 'A' && formula.getFormulaPostfixata().charAt(i) <= 'Z') || (formula.getFormulaPostfixata().charAt(i) >= '0' && formula.getFormulaPostfixata().charAt(i) <= '9')) {
                var += formula.getFormulaPostfixata().charAt(i);
                isVariable = true;
                i++;
            }

            if (isVariable) {
                isVariable = false;
                int col = getColumnOf(var);
                stack.push(note[col]);

            }

            if ("+-*/%~:><".indexOf(formula.getFormulaPostfixata().charAt(i)) >= 0) {
                double val1 = stack.pop();
                double val2 = stack.pop();

                switch (formula.getFormulaPostfixata().charAt(i)) {
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
                    case '<':
                        if(val2<val1) stack.push(1.0);
                        else stack.push(0.0);
                        break;
                    case '>':
                        if(val2>val1) stack.push(1.0);
                        else stack.push(0.0);
                        break;
                    case '~': {
                        i++;
                        String operator = "";
                        while (formula.getFormulaPostfixata().charAt(i) != '~') {
                            operator += formula.getFormulaPostfixata().charAt(i);
                            i++;
                        }

                        switch (operator) {
                            case "min":
                                stack.push(Math.min(val2, val1));
                                break;
                            case "max":
                                stack.push(Math.max(val2, val1));
                                break;
                            case ">=":
                                if(val2>=val1) stack.push(1.0);
                                else stack.push(0.0);
                                break;
                            case "<=":
                                if(val2<=val1) stack.push(1.0);
                                else stack.push(0.0);
                                break;
                            case "&&":
                                if(val2>0 && val1>0) stack.push(1.0);
                                else stack.push(0.0);
                                break;
                            case "||":
                                if(val2>0 || val1>0) stack.push(1.0);
                                else stack.push(0.0);
                                break;
                            case "==":
                                if(val2==val1) stack.push(1.0);
                                else stack.push(0.0);
                                break;
                            case "!=":
                                if(val2!=val1) stack.push(1.0);
                                else stack.push(0.0);
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


    void verificarePromovareMaterie() {

    }

}
