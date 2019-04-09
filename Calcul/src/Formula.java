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

    public int verificaUtilizareMinMax(int index)
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
                       return;
                   }
                   else
                   {
                       mesajPentruFront="Eroare: Intre doi operanzi nu exista operator";
                       return;
                   }
               }

           }

           else if(formula.charAt(i)=='(')
           {
               nrParantezeDeschise++;

               if(isVariable)
               {
                   variabile[nrVariabile++]=var;
               }

               if(!isOperator && !variabile[nrVariabile-1].equals("sum") && !variabile[nrVariabile-1].equals("min") && !variabile[nrVariabile-1].equals("max"))
               {
                   mesajPentruFront="Eroare: Inainte de paranteza nu exista operator";
                   return;
               }

               else if(variabile[nrVariabile-1].equals("sum") && isVariable)
               {
                   int newIndex=verificaUtilizareSum(i);

                   if(newIndex==-1) return;

                   i=newIndex;
                   nrParantezeDeschise--;
                   nrVariabile--;


               }

               else if((variabile[nrVariabile-1].equals("min") || variabile[nrVariabile-1].equals("max")) && isVariable)
               {
                   int newIndex=verificaUtilizareMinMax(i);

                   if(newIndex==-1) return;

                   i=newIndex;
                   nrParantezeDeschise--;
                   nrVariabile--;

               }

               else
               {
                   isVariable=false;
                   isOperator=true;
               }


           }

           else if("+-/*%=".indexOf(formula.charAt(i))>=0) {
               if (isOperator)
               {
                   mesajPentruFront="Eroare: Nu pot exista doi operatori unul dupa altul";
                   return;
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
                   return;
               }
               if(isOperator)
               {
                   mesajPentruFront="Eroare: Paranteza inchisa dupa operator";
                   return;
               }

               if(isVariable) {
                   variabile[nrVariabile++]=var;
                   var="";
               }

           }

           else if(formula.charAt(i)=='.')
           {
               if(isOperator || isVariable)
               {
                   mesajPentruFront="Eroare: Punctul poate fi folosit doar in numere rationale";
                   return;
               }
               else
                   isNumber=true;


           }

           else
           {
               mesajPentruFront="Eroare: Caracter nepermis";
               return;
           }

        }

        if(i==formula.length())
        {
            if(nrParantezeDeschise!=0)
            {
                mesajPentruFront="Eroare: Paranteze folosite incorect";
                return;
            }
            if(isVariable)
                variabile[nrVariabile++]=var;
            mesajPentruFront="Formula este valida";
        }


    }

    int adaugaVariabileDinSum(String var1, String var2)
    {
        int i=0;
        int nr1=0,nr2=0;
        String rad="";
        while(i<var1.length() && ((var1.charAt(i)>='a' && var1.charAt(i)<='z') || (var1.charAt(i)>='A' && var1.charAt(i)<='Z')))
        {
            rad+=var1.charAt(i);
            i++;
        }

        while(i<var1.length() &&(var1.charAt(i)>='0' && var1.charAt(i)<='9'))
        {
            nr1=nr1*10+var1.charAt(i)-'0';
            i++;
        }
        i=0;

        while(i<var2.length() && ((var2.charAt(i)>='a' && var2.charAt(i)<='z') || (var2.charAt(i)>='A' && var2.charAt(i)<='Z')))
            i++;

        while(i<var2.length() &&(var2.charAt(i)>='0' && var2.charAt(i)<='9'))
        {
            nr2=nr2*10+var2.charAt(i)-'0';
            i++;
        }

        for(i=nr1; i<=nr2; i++)
            formulaPostfixata+=rad+i+" ";

        return nr2-nr1;



    }


    void infixToPostfix()
    {
        boolean justReadAVariable=false;
        boolean isMin=false;
        int sum=0;
        int nrPlusuri=0;
        String sumVar1;
        String sumVar2;
        Stack<String> stack = new Stack<>();
        stack.push("#");
        int l=formula.length();

        sumVar1="";
        sumVar2="";

        for(int i=0; i<l; i++)
        {
            justReadAVariable=false;

            while(i<l && ((formula.charAt(i) >= 'a' && formula.charAt(i) <= 'z')||(formula.charAt(i) >= 'A' && formula.charAt(i) <= 'Z') || (formula.charAt(i) >= '0' && formula.charAt(i) <= '9') || formula.charAt(i)=='.')){


                if(sum==1) sumVar1+=formula.charAt(i);
                else if(sum==2) sumVar2+=formula.charAt(i);
                else  formulaPostfixata+=formula.charAt(i);

                i++;
                justReadAVariable=true;


            }
            if(justReadAVariable)
            {
                if(sum==1) sum=2;
                else if(sum==2)
                {
                    nrPlusuri=adaugaVariabileDinSum(sumVar1, sumVar2);
                    for(int j=0; j<nrPlusuri; j++)
                        stack.push("+");

                    sum=0;
                }

                else if(formulaPostfixata.length()>=3) {
                    if (formula.charAt(i - 1) == 'm' && formula.charAt(i - 2) == 'u' && formula.charAt(i - 3) == 's') {
                        if (formulaPostfixata.length() == 3) formulaPostfixata = "";
                        else {
                            String deleteSumString = formulaPostfixata.substring(0, formulaPostfixata.length() - 3);
                            formulaPostfixata = deleteSumString;
                        }
                        sum=1;
                        sumVar1="";
                        sumVar2="";

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
                }
                else formulaPostfixata += " ";
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

            else if("+-/*%=".indexOf(formula.charAt(i))>=0)
            {   String currentOperator= "";
                currentOperator+=formula.charAt(i);
                while(!stack.peek().equals("#") && precedence(currentOperator)<=precedence(stack.peek()))
                {
                    formulaPostfixata+=stack.peek();
                    stack.pop();
                }


                    stack.push(currentOperator);


            }

            else if(formula.charAt(i)==',')
            {
                String currentOperator= "~";
                if(isMin) currentOperator+="min~";
                else currentOperator+="max~";

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
            case "~max~":
                return 3;
            case "~min~":
                return 3;
            case ":":
                return 3;
            case "%":
                return 2;
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
