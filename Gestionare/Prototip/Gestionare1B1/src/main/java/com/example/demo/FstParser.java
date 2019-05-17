/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo;

/**
 *
 * @author GoguSpoder
 */
public class FstParser {
    /*public static String selectCatalog(String id_materie, String id_profesor) {
        return "121 Pop Andrei B4 L1 10 L2 8 | 123 Antonie Dan B4 L1 32 L2 3 |";
    }

    public static String selectFormula(String id_materie){
        return "L1+L2+sum(L3:L5) | L5+L5";
    }

    public static String selectDenumireMaterii(String id_profesor){
        return "POO | NOO";

    }*/

    public String openBlock(String JSONString) {
        return JSONString + "\n{\n";
    }
    public String openBlock(String JSONString, String name) {
        return JSONString + "\""+ name +"\" :{\n";
    }

    public String closeBlock(String JSONString){
        JSONString=JSONString.substring(0,JSONString.length()-2);
        return JSONString + "}, ";
    }
    public String openArray(String JSONString, String name){
        return JSONString + "\""+ name +"\" :[ ";
    }
    public String closeArray(String JSONString){
        JSONString=JSONString.substring(0,JSONString.length()-2);
        return JSONString + "], ";
    }

    public String addObjToJSON(String JSONString, String key, String value) {
        return JSONString + "\""+ key +"\":" +  "\""+ value +"\",\n";
    }

    public String convertCatalogToJSON(String id_materie, String id_profesor) {
        SQL_func test=new SQL_func();
        String catalog = test.selectCatalog(id_materie, id_profesor);
        catalog=catalog.replace(";","");
        catalog=catalog.replaceAll("\\s+"," ");
        System.out.println(catalog+"\n");
        String JSONString="";
        JSONString=openBlock(JSONString);
        JSONString=addObjToJSON(JSONString, "profesor", id_profesor);
        JSONString=addObjToJSON(JSONString, "disciplina", id_materie);
        JSONString=openArray(JSONString,"columns");

        JSONString=openBlock(JSONString);
        JSONString=addObjToJSON(JSONString,"key","student");
        JSONString=addObjToJSON(JSONString,"type","text");
        JSONString=closeBlock(JSONString);

        JSONString=openBlock(JSONString);
        JSONString=addObjToJSON(JSONString,"key","id");
        JSONString=addObjToJSON(JSONString,"type","text");
        JSONString=closeBlock(JSONString);

        JSONString=openBlock(JSONString);
        JSONString=addObjToJSON(JSONString,"key","group");
        JSONString=addObjToJSON(JSONString,"type","text");
        JSONString=closeBlock(JSONString);

        String[] splited=catalog.split(" ");
        for(int i=4; i<splited.length && splited[i].indexOf('|')<0; i+=2)
        {
            JSONString=openBlock(JSONString);
            JSONString=addObjToJSON(JSONString,"key",splited[i]);
            JSONString=addObjToJSON(JSONString,"type","number");
            JSONString=closeBlock(JSONString);

        }

        JSONString=closeArray(JSONString);

        JSONString=openArray(JSONString,"rows");

        int i=0;
        while(i<splited.length)
        {
            JSONString=openBlock(JSONString);
            JSONString=addObjToJSON(JSONString,"id",splited[i++]);
            JSONString=addObjToJSON(JSONString,"student",splited[i]+" "+splited[i+1]);
            i+=2;
            JSONString=addObjToJSON(JSONString,"group",splited[i++]);

            while(i<splited.length && splited[i].indexOf('|')<0)
            {
                JSONString=addObjToJSON(JSONString,splited[i],splited[i+1]);
                i+=2;
            }
            JSONString=closeBlock(JSONString);

            i++;

        }

        JSONString=closeArray(JSONString);
        JSONString=closeBlock(JSONString);
        JSONString=JSONString.substring(0,JSONString.length()-2);



        return JSONString;

    }

    public String convertFormuleToJSON(String id_materie)
    {
        SQL_func test=new SQL_func();
        String formule=test.selectFormula(id_materie);
        String[] splited=formule.split("\\|");
        String JSONString="";
        JSONString=openBlock(JSONString);
        JSONString=openBlock(JSONString,"formule");
        JSONString=addObjToJSON(JSONString,"id_materie",id_materie);
        JSONString=openArray(JSONString,"list");

        for(int i=0; i<splited.length; i++)
        {
            JSONString=openBlock(JSONString);
            JSONString=addObjToJSON(JSONString,"formula", splited[i]);
            JSONString=closeBlock(JSONString);
        }
        JSONString=closeArray(JSONString);
        JSONString=closeBlock(JSONString);
        JSONString=closeBlock(JSONString);
        JSONString=JSONString.substring(0,JSONString.length()-2);

        return JSONString;

    }

    public String convertMateriiToJSON(String id_profesor)
    {
        SQL_func test = new SQL_func();
        String materii=test.selectDenumireMaterii(id_profesor);
        String[] splited=materii.split("\\|");
        String JSONString="";
        JSONString=openBlock(JSONString);
        JSONString=openBlock(JSONString,"materii");
        JSONString=addObjToJSON(JSONString,"id_profesor",id_profesor);
        JSONString=openArray(JSONString,"list");

        for(int i=0; i<splited.length; i++)
        {
            JSONString=openBlock(JSONString);
            JSONString=addObjToJSON(JSONString,"materie", splited[i]);
            JSONString=closeBlock(JSONString);
        }
        JSONString=closeArray(JSONString);
        JSONString=closeBlock(JSONString);
        JSONString=closeBlock(JSONString);
        JSONString=JSONString.substring(0,JSONString.length()-2);

        return JSONString;
    }


    /*public static void main(String[] args) {

        System.out.println(convertCatalogToJSON("32","32"));

    }*/
}
