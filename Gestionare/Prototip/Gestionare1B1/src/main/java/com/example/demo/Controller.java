/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 * @author GoguSpoder
 */

@RestController
@RequestMapping("/")
@CrossOrigin
public class Controller {
    @RequestMapping("/")
    public String home(){
        //System.out.println(request.toString());
        return "Welctome to Spring BOOT!";
    }
    
    private List<String> findKeys(Map<String,Object> treeMap,List<String> keys){
        treeMap.forEach((key,value)->{
            if(value instanceof LinkedHashMap){
                Map<String,Object> map = (LinkedHashMap) value;
                findKeys(map,keys);
            }
            keys.add(key);
        });
        return keys;
    }
    
     
    @RequestMapping(
     value="/disciplines",
     method=RequestMethod.GET)
    public String listaDiscipline(@RequestBody String something){
        ObjectMapper mapper = new ObjectMapper();
        try{
           Map<String,Object> treeMap = mapper.readValue(something,Map.class);
           List<String> keys = new ArrayList();
           List<String> result = findKeys(treeMap,keys);
           System.out.println(treeMap.get(result.get(0)).toString());
           System.out.println("\n"+parseOne(treeMap.get(result.get(0)).toString()));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return "Multumesc";
    }
    
    @RequestMapping(value= "/formule", method=RequestMethod.OPTIONS)
    public void corsHeaders(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, x-requested-with");
        response.addHeader("Access-Control-Max-Age", "3600");
    }
    
    public String parseOne(String el){
        String resultB="";
        String[] arr = el.split(",");
        for(String temp:arr){
            String[] arrT=temp.split("=");
            resultB+=(arrT[0] + " " + arrT[1] + " ");
        }
        resultB=resultB.substring(2,resultB.length()-3);
        resultB=resultB.replace("{","");
        return resultB;
    }
    
    
     
    @GetMapping(value="/catalog")
    public String getCatalog(@RequestParam("id_Materie") String aRe,@RequestParam("id_prof") String bRe){
        //String aRe="";
        //String bRe="";
        ObjectMapper mapper = new ObjectMapper();
        
           //Map<String,Object> treeMap = mapper.readValue(something,Map.class);
           //List<String> keys = new ArrayList();
           //List<String> result = findKeys(treeMap,keys);
           //aRe=treeMap.get(result.get(0)).toString();
           //bRe=treeMap.get(result.get(1)).toString();
           System.out.println(aRe+"\n"+bRe);
        
        //String forRet = parserPuiuMic(select(aRe,bRe));
        //return forRet;
        return (new FstParser().convertCatalogToJSON(aRe, bRe));
    }
    
    /*
    @GetMapping(value="/catalog")
    public String getCatalog(@RequestBody String something){
        String aRe="";
        String bRe="";
        ObjectMapper mapper = new ObjectMapper();
        try{
           Map<String,Object> treeMap = mapper.readValue(something,Map.class);
           List<String> keys = new ArrayList();
           List<String> result = findKeys(treeMap,keys);
           aRe=treeMap.get(result.get(0)).toString();
           bRe=treeMap.get(result.get(1)).toString();
           System.out.println(aRe+"\n"+bRe);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        //String forRet = parserPuiuMic(selectCatalog(aRe,bRe));
        //return forRet;
        return (new FstParser().convertCatalogToJSON(aRe, bRe));
    }
    */
    
     
    @RequestMapping(
    value="/catalog",
    method=RequestMethod.POST)
    public String postCatalog(@RequestBody String something){
        String aRe="";
        String bRe="";
        ObjectMapper mapper = new ObjectMapper();
        try{
           Map<String,Object> treeMap = mapper.readValue(something,Map.class);
           List<String> keys = new ArrayList();
           List<String> result = findKeys(treeMap,keys);
           String cols = treeMap.get(result.get(2)).toString();
           int cnt=0;
           String antet="";
           for(int i=0;i<cols.length();i++)
               if(cols.charAt(i)=='}')
                   cnt++;
           String[] cols1=cols.split("}");
           int contor=0;
           for(String cols2:cols1){
               contor++;
               cols2=cols2.substring(1,cols2.length());
               System.out.println(cols2+"\n");
               String[] col3=cols2.split(",");
               if(col3[0].equals(cols2))
                   continue;
               String[] cols4=col3[0].split("=");
               if(cols4[0].equals(col3[0]))
                   continue;
               if(contor>3)
                    antet+=(cols4[1]+" ");
           }
           antet="a: "+treeMap.get(result.get(1)).toString()+"; "+antet;
           System.out.println(cnt);
           System.out.println("\n"+antet);
           
           PrelucrareDate pd = new PrelucrareDate();
           String mesajPentruFront=pd.primesteMesajFront(antet);
           System.out.println(mesajPentruFront);
           
           
           String[] splitedVal = treeMap.get(result.get(3)).toString().split("}");
           int cnt2=0;
           for(String tempVal:splitedVal)
           {
               String tempSol="";
               String[] tempAg = tempVal.split(",");
               if(tempAg[0].equals(tempVal))
                   continue;
               for(String str:tempAg){
                   String[] str2=str.split("=");
                   if(str2[0].equals(str))
                       continue;
                   tempSol+=(str2[0] + " " + str2[1]);
               }
               tempSol = tempSol.replace("[", "");
               tempSol = tempSol.replace("{", "");
               tempSol = tempSol.replace(",","");
               String idStud="";
               if(tempSol!=""){
                    int j=0;
                    int ok1=0;
                    for(j=0;j<tempSol.length();j++){
                        if(j>1 && j<tempSol.length()-2){
                            if(tempSol.charAt(j)=='d' && tempSol.charAt(j-1)=='i' && (tempSol.charAt(j-2)==' ' || tempSol.charAt(j-2)==0 || j==1) && tempSol.charAt(j+1)==' '){
                                ok1=0;
                                idStud="";
                            }
                        }
                        if(ok1==1 && tempSol.charAt(j)!=' ')
                            idStud+=tempSol.charAt(j);
                        if(tempSol.charAt(j)==' ')
                            ok1++;
                        if(tempSol.charAt(j)>='A' && tempSol.charAt(j)<='Z' && tempSol.charAt(j+1)>='0' && tempSol.charAt(j+1)<='9')
                            break;
                    }
                    j=j+2;
                    tempSol=tempSol.substring(j,tempSol.length());
                    tempSol = "n: " + idStud + "; " + treeMap.get(result.get(1)).toString() + "; " + tempSol;
                    System.out.println(tempSol+"\n");
                    System.out.println("before\n");
                    mesajPentruFront+=" "+pd.primesteMesajFront(tempSol);
                    System.out.println(mesajPentruFront);
                    System.out.println("after\n");
               }
           }
           return mesajPentruFront;
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return "Error";
        }
    }
    
     
    @RequestMapping(
    value="/catalog",
    method=RequestMethod.PUT)
    public String putCatalog(@RequestBody String something){
        String aRe="";
        String bRe="";
        ObjectMapper mapper = new ObjectMapper();
        try{
           Map<String,Object> treeMap = mapper.readValue(something,Map.class);
           List<String> keys = new ArrayList();
           List<String> result = findKeys(treeMap,keys);
           String cols = treeMap.get(result.get(2)).toString();
           int cnt=0;
           String antet="";
           for(int i=0;i<cols.length();i++)
               if(cols.charAt(i)=='}')
                   cnt++;
           System.out.println(cnt);
           String[] splitedVal = treeMap.get(result.get(3)).toString().split("}");
           int cnt2=0;
           for(String tempVal:splitedVal)
           {
               String tempSol="";
               String[] tempAg = tempVal.split(",");
               if(tempAg[0].equals(tempVal))
                   continue;
               for(String str:tempAg){
                   String[] str2=str.split("=");
                   if(str2[0].equals(str))
                       continue;
                   tempSol+=(str2[0] + " " + str2[1]);
               }
               tempSol = tempSol.replace("[", "");
               tempSol = tempSol.replace("{", "");
               tempSol = tempSol.replace(",","");
               String idStud="";
               if(tempSol!=""){
                    int j=0;
                    int ok1=0;
                    for(j=0;j<tempSol.length();j++){
                        if(j>1 && j<tempSol.length()-2){
                            if(tempSol.charAt(j)=='d' && tempSol.charAt(j-1)=='i' && (tempSol.charAt(j-2)==' ' || tempSol.charAt(j-2)==0 || j==1) && tempSol.charAt(j+1)==' '){
                                ok1=0;
                                idStud="";
                            }
                        }
                        if(ok1==1 && tempSol.charAt(j)!=' ')
                            idStud+=tempSol.charAt(j);
                        if(tempSol.charAt(j)==' ')
                            ok1++;
                        if(tempSol.charAt(j)>='A' && tempSol.charAt(j)<='Z' && tempSol.charAt(j+1)>='0' && tempSol.charAt(j+1)<='9')
                            break;
                    }
                    j=j+2;
                    tempSol=tempSol.substring(j,tempSol.length());
                    tempSol = "n: " + idStud + "; " + treeMap.get(result.get(1)).toString() + "; " + tempSol;
                    System.out.println(tempSol+"\n");
               }
           }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        
        SQL_func test=new SQL_func();
        System.out.println(test.selectDenumireMaterii("1"));
        return "Multumesc";
    }
    
     
    /*@RequestMapping(
    value="/formule",
    method=RequestMethod.GET)
    public String getFormule(@RequestBody String something){
        String[] splArr = something.split(":");
        splArr[1]=splArr[1].replace("}","");
        splArr[1]=splArr[1].replace(" ","");
        splArr[1]=splArr[1].substring(1,splArr[1].length()-2);
        System.out.println(splArr[1]);
        return (new FstParser().convertFormuleToJSON(splArr[1]));
    }*/
    
     @RequestMapping(
     value="/import",
     method=RequestMethod.POST)
     public String getCSV(@RequestBody String body){
        String response = "";
        CSVImporter obj=new CSVImporter();
        String[] els=body.split("\n");
        /*try {
            for(int jj=0;jj<els.length;jj++){
                List<String> line = obj.parseLine(els[jj]);
                for(int i=0;i<line.size();i++)
                    response += (line.get(i)+"\n");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }*/
        for(int jj=0;jj<els.length;jj++){
            System.out.println(els[jj]);
            (new SQL_func()).insertNewStudent(els[jj]);
        }
        return "Done";
     }
    
    @RequestMapping(
    value="/formule",
    method=RequestMethod.GET)
    public String getFormule(@RequestParam("id_profesor") String iDp){
        /*String[] splArr = something.split(":");
        splArr[1]=splArr[1].replace("}","");
        splArr[1]=splArr[1].replace(" ","");
        splArr[1]=splArr[1].substring(1,splArr[1].length()-2);
        System.out.println(splArr[1]);*/
        //return (new FstParser().convertFormuleToJSON(iDp));
        String result = (new SQL_func()).selectFormula(iDp);
        System.out.println(result);
        String[] els=result.split("~");
        String jsonResponse = "[\n";
        for(int ind=0;ind<els.length;ind++){
            if(els[ind].charAt(0) == '_'){
                String[] els2 = els[ind].split("_");
                if(ind<els.length-1){
                    jsonResponse += ("{ \"id_materie\":" + els2[1] + "," + "\"formula_calcul\":" + "\" \"" + " },");
                }else{
                    jsonResponse += ("{ \"id_materie\":" + els2[1] + "," + "\"formula_calcul\":" + "\" \"" + " }");
                }
            }
            else{
                String[] els2 = els[ind].split("_");
                System.out.println(els2[0]);
                if(ind<els.length-1){
                    jsonResponse += ("{ \"id_materie\":" + els2[1] + "," + "\"formula_calcul\":" + "\"" + els2[0] + "\"" + "},");
                }
                else{
                    jsonResponse += ("{ \"id_materie\":" + els2[1] + "," + "\"formula_calcul\":" + "\"" + els2[0] + "\"" + "}");
                }
            }
        }
        jsonResponse += "]";
        return jsonResponse;
    }
    
     
    @RequestMapping(
    value="/formule",
    method=RequestMethod.POST)
    public String postFormule(@RequestBody String something){
        String aRe="";
        String bRe="";
        String mesajPentruFront="";
        ObjectMapper mapper = new ObjectMapper();
        try{
           Map<String,Object> treeMap = mapper.readValue(something,Map.class);
           List<String> keys = new ArrayList();
           List<String> result = findKeys(treeMap,keys);
           aRe=treeMap.get(result.get(0)).toString();
           bRe=treeMap.get(result.get(1)).toString();
           String forC="f: " + aRe + "; " + bRe;
           PrelucrareDate pd = new PrelucrareDate();
           aRe=aRe.replace(" ","");
           
           System.out.println(new SQL_func().selectAntet(aRe));
           mesajPentruFront=pd.primesteMesajFront(forC);
           System.out.println(mesajPentruFront);
           System.out.println(forC);
           
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        //String forRet = parserPuiuMic(updateFormule(id_materie,formula));
        //return forRet;
        return mesajPentruFront;
    }
    
     
    @RequestMapping(
    value="/materii",
    method=RequestMethod.GET)
    public String getProfesor(@RequestParam("id_profesor") String iDp){
        /*String[] splArr = something.split(":");
        splArr[1].replace("}","");
        splArr[1].replace(" ","");
        splArr[1]=splArr[1].substring(1,splArr[1].length()-3);
        System.out.println(splArr[1]);
        //return parser(selectMaterii(splArr[1]);*/
        //return (new FstParser().convertMateriiToJSON(iDp));
        SQL_func obj=new SQL_func();
        String result=obj.selectDenumireMaterii(iDp);
        String []els = result.split("~");
        String jsonResponse = "";
        jsonResponse = "{\"disciplines\":[\n";
        for(int ind=0;ind<els.length;ind++){
            String[] els2 = els[ind].split(":");
            if(ind<els.length-1)
                jsonResponse += ("{ \"denumire_materie\":" + "\"" + els2[0] + "\"" + "," + "\"id_materie\":" + "\"" + els2[1] + "\"" + "},");
            else
                jsonResponse += ("{ \"denumire_materie\":" + "\"" + els2[0] + "\"" + "," + "\"id_materie\":" + "\"" + els2[1] + "\"" + "}\n");
        }
        jsonResponse += "]";
        jsonResponse += "\n}";
        return jsonResponse;
    }
    
     
    @RequestMapping(
    value="/materii",
    method=RequestMethod.POST)
    public String putProfesor(@RequestBody String something){
        String aRe="";
        String bRe="";
        ObjectMapper mapper = new ObjectMapper();
        try{
           Map<String,Object> treeMap = mapper.readValue(something,Map.class);
           List<String> keys = new ArrayList();
           List<String> result = findKeys(treeMap,keys);
           aRe=treeMap.get(result.get(0)).toString();
           bRe=treeMap.get(result.get(1)).toString();
           System.out.println(aRe+"\n"+bRe);
           new SQL_func().insertProfesori(aRe,bRe);
           
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        //String forRet = parserPuiuMic(selectCatalog(aRe,bRe));
        //return forRet;
        return something;
    }
    
    @RequestMapping(
    value="/note",
    method=RequestMethod.GET)
    public String getListaNote(@RequestParam("id_student") String idStud,@RequestParam("id_materie") String idMaterie){
        SQL_func obj = new SQL_func();
        String result = obj.selectStudentRow(idStud,idMaterie);
        System.out.println(result);
        String[] els=result.split("~");
        String jsonResponse="[\n";
        for(int ind=0;ind<els.length-1;ind++){
            //System.out.println(els[ind]);
            String[] tel = els[ind].split(":");
            jsonResponse += "{";
            jsonResponse += ("\"denumire_materie\":" + "\""+tel[0]+"\"" + ",");
            jsonResponse += ("\"valori_note\":");
            System.out.println(tel[1]);
            String[] tele = tel[1].split(" ");
            /*if(tele.length == 3)
                System.out.println(tele.length + tele[0] + tele[1] + tele[2]);
            else
                System.out.println(tele.length + tele[0] + tele[1]);*/
            jsonResponse += "{";
            for(int tt=0;tt<tele.length;tt+=2){
                /*String fst = "";
                String snd = "";
                int oke=0;
                for(int yai=0;yai<tele[tt].length();yai++){
                    if(tele[tt].charAt(yai) == ' ' && !fst.equals(""))
                        oke=1;
                    if(oke==0){
                        if(tele[tt].charAt(yai)!=' ')
                            fst+=tele[tt].charAt(yai);
                    }
                    else{
                        if(tele[tt].charAt(yai)!=' ')
                            snd+=tele[tt].charAt(yai);
                    }
                }*/
                //for(int yai = 0; yai < lle.length-1; yai++){
                    //System.out.println(lle[yai]);
                    //if((lle[yai].charAt(0) >= 'a' && lle[yai].charAt(0)<='z') || (lle[yai].charAt(0)>='A' && lle[yai].charAt(0)<='Z')){
                        //System.out.println(lle[yai]);
                        /*if(tt == tele.length - 1){
                            jsonResponse += (lle[yai] + ":" + lle[yai+1]);
                        }
                        else{
                            jsonResponse += (lle[yai] + ":" + lle[yai+1] + ",");
                        }
                        break;*/
                    //}
                //}
                if(tt == tele.length-2)
                    jsonResponse += ("\""+tele[tt]+"\""+":"+"\""+tele[tt+1]+"\"");
                else
                    jsonResponse += ("\""+tele[tt]+"\""+":"+"\""+tele[tt+1]+"\""+",");
            }
            jsonResponse += "} }";
            if(ind<els.length-2)
                jsonResponse += ("," + "\n");
            else
                jsonResponse += "\n";
        }
        jsonResponse += "]";
        return jsonResponse;
    }
    
    @RequestMapping(
    value="/note",
    method=RequestMethod.POST)
    public String postListaNote(@RequestBody String something){
        return null;
    }
    
    @RequestMapping(
    value="/cursuri",
    method=RequestMethod.GET)
    public String getCursuri(@RequestParam("id_student") String idStud){
        String result="";
        SQL_func obj = new SQL_func();
        result = obj.selectStudentCursuri(idStud);
        System.out.println(result);
        String[] els = result.split("~");
        String jsonResponse="{ \"disciplines\":[\n";
        for(int ind=0;ind<els.length;ind++){
            String[] els2=els[ind].split(":");
            jsonResponse += "{";
            if(ind < els.length-1)
                jsonResponse += ("\"denumire_materie\":" + "\""+els2[0]+"\""+"," +"\"id_materie\":"+"\""+els2[1]+"\"" + "}" + ",");
            else
                jsonResponse += ("\"denumire_materie\":" + "\""+els2[0]+"\""+"," +"\"id_materie\":"+"\""+els2[1]+"\"" + "}");
        }
        jsonResponse += "]";
        jsonResponse += "}";
        return jsonResponse;
    }
    
    @RequestMapping(
    value="/cursruri",
    method=RequestMethod.POST)
    public String postCursuri(@RequestBody String something){
        return null;
    }
}
