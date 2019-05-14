/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo1;

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
import org.springframework.web.bind.annotation.RestController;


/**
 *
 * @author GoguSpoder
 */

@CrossOrigin(origins = "http://localhost:3000")
@RestController
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
    
//    @RequestMapping(value= "/formule", method=RequestMethod.OPTIONS)
//    public void corsHeaders(HttpServletResponse response) {
//        response.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
//        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
//        response.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, x-requested-with");
//        response.addHeader("Access-Control-Max-Age", "3600");
//    }
    
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
<<<<<<< HEAD
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
=======
>>>>>>> 0bb654169dee5ca819153b6b545173719cdf30af
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
<<<<<<< HEAD
                    System.out.println("before\n");
                    mesajPentruFront+=" "+pd.primesteMesajFront(tempSol);
                    System.out.println(mesajPentruFront);
                    System.out.println("after\n");
=======
>>>>>>> 0bb654169dee5ca819153b6b545173719cdf30af
               }
           }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return "Multumesc";
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
    
     
    @RequestMapping(
    value="/formule",
    method=RequestMethod.GET)
    public String getFormule(@RequestBody String something){
        String[] splArr = something.split(":");
        splArr[1]=splArr[1].replace("}","");
        splArr[1]=splArr[1].replace(" ","");
        splArr[1]=splArr[1].substring(1,splArr[1].length()-2);
        System.out.println(splArr[1]);
        return (new FstParser().convertFormuleToJSON(splArr[1]));
    }
    
     
    @RequestMapping(
    value="/formule",
    method=RequestMethod.POST)
    public String postFormule(@RequestBody String something){
        String aRe="";
        String bRe="";
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
           new SQL_func().updateAntet("L1 L2 L3",aRe);
           System.out.println(new SQL_func().selectAntet(aRe));
           System.out.println(pd.primesteMesajFront(forC));
           System.out.println(forC);
           
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        //String forRet = parserPuiuMic(updateFormule(id_materie,formula));
        //return forRet;
        return something;
    }
    
     
    @RequestMapping(
    value="/materii",
    method=RequestMethod.GET)
    public String getProfesor(@RequestBody String something){
        String[] splArr = something.split(":");
        splArr[1].replace("}","");
        splArr[1].replace(" ","");
        splArr[1]=splArr[1].substring(1,splArr[1].length()-3);
        System.out.println(splArr[1]);
        //return parser(selectMaterii(splArr[1]);
        return (new FstParser().convertMateriiToJSON(splArr[1]));
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
}
