import java.util.Stack;

public class Formula {
    public String formula;
    public String formulaPostfixata="";
    public int nrVariabile=0;
    public String criteriiPromovare;
    public String[] variabile= new String[50];
    public String mesajPentruFront;


    public Formula(String formula)
    {
        this.formula=formula;
    }


    //used only for tests
    public void setFormula(String formula) {
        this.formula=formula;
        nrVariabile=0;
        formulaPostfixata="";
    }

    //primeste un index de unde incepe paranteza pentru sum, returneaza  pozitia primei paranteze inchise pe care o gaseste daca functia sum a fost folosita corect si -1 altfel
    // Ex: Pentru formula "P+E+sum(L1:L14)" functia primeste indexul 7 si returneaza 14
    // Pentru "P + sum(L)" returneaza -1
    public int verificaUtilizareSum(int index)
    {
        int i=index;
        int indexFinal=-1;

        while(i<formula.length())
        {
            if(formula.charAt(i)==')')
            {
                indexFinal=i;
                break;
            }
            i++;
        }

        return indexFinal;

    }

    //parcurge formula, verifica daca e corecta si determina variabilele
    void parsareFormula()
    {
        int i;
        boolean isVariable=false;
        boolean isNumber=false;
        boolean isOperator=true;
        int nrParantezeDeschise=0;


        String var="";


        for( i=0; i<formula.length(); i++)
        {
           if(formula.charAt(i)==' ')
           {
               if(isVariable) {
                   variabile[nrVariabile++]=var;
                   var="";
               }

               isVariable=false;
               isNumber=false;
           }
           else if((formula.charAt(i)>='a' && formula.charAt(i)<='z') || (formula.charAt(i)>='A' && formula.charAt(i)<='Z'))
           {
               if(isVariable)
               {
                   var=var+formula.charAt(i);
               }
               else if(!isVariable)
               {
                   if(isOperator)
                   {
                       isVariable=true;
                       isOperator=false;
                       var=var+formula.charAt(i);
                   }
                   else if(isNumber)
                   {
                       mesajPentruFront="Eroare: Variabilele nu pot incepe cu cifre";
                       break;
                   }
                   else
                   {
                       mesajPentruFront="Eroare: Intre doi operanzi nu exista operator";
                       break;
                   }
               }

           }

           else if(formula.charAt(i)=='(')
           {
               nrParantezeDeschise++;
               if(!isOperator && !variabile[nrVariabile-1].equals("sum"))
               {
                   mesajPentruFront="Eroare: Inainte de paranteza nu exista operator";
                   break;
               }

               else if(variabile[nrVariabile-1].equals("sum") && isVariable)
               {
                   int newIndex=verificaUtilizareSum(i);

                   if(newIndex==-1) break;
                   else
                   {
                       i=newIndex;
                       nrParantezeDeschise--;
                   }

               }
           }

           else if("+-/*=".indexOf(formula.charAt(i))>=0) {
               if (isOperator)
               {
                   mesajPentruFront="Eroare: Nu pot exista doi operatori unul dupa altul";
                   break;
               }
               else
               {
                   if(isVariable) {
                       variabile[nrVariabile++]=var;
                       var="";
                   }

                   isVariable=false;
                   isNumber=false;
                   isOperator=true;
               }

           }

           else if(formula.charAt(i)>='0' && formula.charAt(i)<='9')
           {
               if(isOperator)
               {
                   isOperator=false;
                   isNumber=true;
               }
               else if(isVariable)
                   var=var+formula.charAt(i);
           }

           else if(formula.charAt(i)==')')
           {
               nrParantezeDeschise--;
               if(nrParantezeDeschise<0)
               {
                   mesajPentruFront="Eroare: Paranteze folosite incorect";
                   break;
               }
               if(isOperator)
               {
                   mesajPentruFront="Eroare: Paranteza inchisa dupa operator";
                   break;
               }

               if(isVariable) {
                   variabile[nrVariabile++]=var;
                   var="";
               }

           }

           else
           {
               mesajPentruFront="Eroare: Caracter nepermis";
               break;
           }

        }

        if(i==formula.length())
        {
            if(isVariable)
                variabile[nrVariabile++]=var;
            mesajPentruFront="Formula este valida";
        }


    }

    void infixToPostfix()
    {
        boolean justReadAVariable=false;
        Stack<String> stack = new Stack<>();
        stack.push("#");
        int l=formula.length();

        for(int i=0; i<l; i++)
        {
            justReadAVariable=false;
            while(i<l && ((formula.charAt(i) >= 'a' && formula.charAt(i) <= 'z')||(formula.charAt(i) >= 'A' && formula.charAt(i) <= 'Z') || (formula.charAt(i) >= '0' && formula.charAt(i) <= '9'))){
                formulaPostfixata+=formula.charAt(i);
                i++;

                justReadAVariable=true;
            }
            if(justReadAVariable)
            {
                if(formulaPostfixata.length()>=3)
                    if(formula.charAt(i-1)=='m' && formula.charAt(i-2)=='u' && formula.charAt(i-3)=='s')
                    {
                        if(formulaPostfixata.length()==3) formulaPostfixata="";
                        else{
                            String deleteSumString=formulaPostfixata.substring(0,formulaPostfixata.length()-4);
                            formulaPostfixata=deleteSumString;
                        }
                    }
                formulaPostfixata+=" ";
            }

            if(i>=l)
                break;

            if(formula.charAt(i)=='(')
                stack.push("(");

            else if(formula.charAt(i)==')')
            {
                while(!stack.peek().equals("#") && !stack.peek().equals("("))
                {
                    formulaPostfixata+=stack.peek();
                    stack.pop();
                }

                if(stack.peek().equals("("))
                    stack.pop();
            }

            else if("+-/*:=".indexOf(formula.charAt(i))>=0)
            {   String currentOperator= "";
                currentOperator+=formula.charAt(i);
                while(!stack.peek().equals("#") && precedence(currentOperator)<=precedence(stack.peek()))
                {
                    formulaPostfixata+=stack.peek();
                    stack.pop();
                }

                stack.push(currentOperator);
            }
        }

        while(!stack.peek().equals("#"))
        {
            formulaPostfixata+=stack.peek();
            stack.pop();
        }



    }

    int precedence(String operator)
    {
        switch(operator)
        {
            case ":":
                return 3;
            case "*":
                return 2;
            case "/":
                return 2;
            case "+":
                return 1;
            case "-":
                return 1;
            default:
                return -1;
        }
    }



    void parsareCriteriiPromovare()
    {

    }

}
