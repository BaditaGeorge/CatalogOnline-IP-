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
    public String getCatalog(@RequestParam("id_Materie") String aRe,@RequestParam("id_prof") String bRe,@RequestParam("id_session") String ses){
        //String aRe="";
        //String bRe="";    
        Sessions obj = new Sessions();
        Access aObj = new Access();
        String uSername = (new SQL_func()).getUsername(ses);
        if(obj.checkSession(uSername) == false)
            return "Invalid session!";
        if(aObj.isAdministrator(uSername) == false && aObj.isProfesor(uSername) == false)
            return "Not allowed!";
        
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
    public String postCatalog(@RequestBody String something,@RequestParam("id_session") String ses){
        String aRe="";
        String bRe="";
        Sessions obj = new Sessions();
        Access aObj = new Access();
        String uSername = (new SQL_func()).getUsername(ses);
        if(obj.checkSession(uSername) == false)
            return "Invalid session!";
        if(aObj.isAdministrator(uSername) == false && aObj.isProfesor(uSername) == false)
            return "Not allowed!";
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
    value="/addStudCatalog",
    method = RequestMethod.POST)
    public String postNewToCatalog(@RequestBody String body,@RequestParam("id_session") String ses){
        Sessions obj = new Sessions();
        Access aObj = new Access();
        String uSername = (new SQL_func()).getUsername(ses);
        if(obj.checkSession(uSername) == false)
            return "Invalid session!";
        if(aObj.isAdministrator(uSername) == false && aObj.isProfesor(uSername) == false)
            return "Not allowed!";
        String[] arr = body.split(",");
        String data = "";
        String[] sols = new String[12];
        for(int i=0;i<arr.length;i++){
            String[] vls = arr[i].split(":");
            sols[i] = vls[1].trim().replace("}","").replace("\"","");
            System.out.println(sols[i]);
        }
        if(sols.length<3)
            return "Wrong format!";
        (new SQL_func()).insertMaterii(sols[0], sols[1], sols[2], sols[3]);
        return "Added";
    }
    
    @RequestMapping(
    value="/importCatalog",
    method = RequestMethod.POST)
    public String importCatalog(@RequestBody String body,@RequestParam("id_Materie") String param,@RequestParam("id_session") String param2){
        Sessions obj = new Sessions();
        Access aObj = new Access();
        String uSername = (new SQL_func()).getUsername(param2);
        if(obj.checkSession(uSername) == false)
            return "Invalid session!";
        if(aObj.isAdministrator(uSername) == false && aObj.isProfesor(uSername) == false)
            return "Not allowed!";
        String[] arr = body.split("\n");
        for(int i=0;i<arr.length;i++){
            String[] vls = arr[i].split(",");
            String den = (new SQL_func()).selectDenM(param.trim());
            //system.out.println(param + "," + vls[0] + "," + den + "," + vls[1]);
            (new SQL_func()).insertMaterii(param,vls[0],den,vls[1]);
        }
        return "Added!";
    }
    
    
    
    @RequestMapping(
    value="/criterii",
    method = RequestMethod.POST)
    public String getCriterii(@RequestBody String body,@RequestParam("id_session") String ses){
        
        Sessions obj = new Sessions();
        Access aObj = new Access();
        String uSername = (new SQL_func()).getUsername(ses);
        if(obj.checkSession(uSername) == false)
            return "Invalid session!";
        if(aObj.isAdministrator(uSername) == false && aObj.isProfesor(uSername) == false)
            return "Not allowed!";
        String[] arr = body.split(",");
        String[] vls1 = arr[0].split(":");
        String[] vls2 = arr[1].split(":");
        vls1[1] = vls1[1].trim();
        vls2[1] = vls2[1].replace("}","").trim();
        String snd = "c: " + vls1[1] + " ; " + vls2[1];
        return (new PrelucrareDate()).primesteMesajFront(snd);
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
    
    public String randId(){
        int ind = 1;
        while((new SQL_func()).selectStudAtId(String.valueOf(ind))){
            ind++;
        }
        //System.out.println((new SQL_func()).selectStudAtId(String.valueOf(ind)));
        return String.valueOf(ind);
    }
    
    @RequestMapping(
    value = "/addStud",
    method = RequestMethod.POST)
    public String addStud(@RequestBody String body,@RequestParam("id_session") String ses){
        Sessions obj = new Sessions();
        Access aObj = new Access();
        String uSername = (new SQL_func()).getUsername(ses);
        if(obj.checkSession(uSername) == false)
            return "Invalid session!";
        if(aObj.isAdministrator(uSername) == false && aObj.isProfesor(uSername) == false)
            return "Not allowed!";
        String[] result = body.split(",");
        String json = "";
        json += (randId() + ",");
        for(int t=0;t<result.length;t++){
            String[] vals = result[t].split(":");
            vals[1] = vals[1].trim();
            int len = vals[1].length();
            if(vals[1].charAt(len-1) == '}'){
                vals[1] = vals[1].substring(0,len-1);
            }
            vals[1] = vals[1].substring(1,len-1);
            if(t < result.length - 1)
                json += (vals[1] + ",");
            else{
                String n = "";
                int indexu = 0;
                while(vals[1].charAt(indexu) != '"'){
                    n += vals[1].charAt(indexu);
                    indexu++;
                }
                json += n;
            }
        }
        (new SQL_func()).insertNewStudent(json);
        return "Done";
    }
    
    public String idPrfG(){
        int ind = 1;
        while((new SQL_func()).getIdProfs(String.valueOf(ind)))
            ind++;
        return String.valueOf(ind);
    }
    
    @RequestMapping(
    value="/profesori",
    method=RequestMethod.POST)
    public String postProfs(@RequestBody String body,@RequestParam("id_session") String ses){
        Sessions obj = new Sessions();
        Access aObj = new Access();
        String uSername = (new SQL_func()).getUsername(ses);
        if(obj.checkSession(uSername) == false)
            return "Invalid session!";
        if(aObj.isAdministrator(uSername) == false)
            return "Not allowed!";
        String[] arr = body.split(",");
        String db = "";
        db += (idPrfG() + ",");
        if(arr.length <= 2)
            return "";
        for(int i=1;i<3;i++){
            String[] vals = arr[i].split(":");
            vals[1].trim();
            vals[1] = vals[1].substring(1,vals[1].length()-1);
            db += (vals[1].trim() + ",");
        }
        for(int i=3;i<arr.length;i+=4){
           String temp = db;
           String[] vls1 = arr[i].split(":");
           if(vls1.length > 2)
               vls1[1] = vls1[vls1.length - 1];
           if((i+1)>=arr.length)
               return "wrong format 1" + " " + i + " " + arr.length;
           String[] vls2 = arr[i+1].split(":");
           vls2[1] = vls2[1].substring(1,vls2[1].length()-1);
           if((i+2)>=arr.length)
               return "wrong format 2";
           String[] vls3 = arr[i+2].split(":");
           if(vls3.length > 2)
               vls3[1] += ":" + vls3[2];
           vls3[1] = vls3[1].substring(1,vls3[1].length()-1);
           if((i+3)>=arr.length)
               return "wrong format 3";
           String[] vls4 = arr[i+3].split(":");
           vls4[1] = vls4[1].substring(1,vls4[1].length()-1);
           while(vls4[1].charAt(vls4[1].length()-1) == '\n' || vls4[1].charAt(vls4[1].length()-1) == '\t' || vls4[1].charAt(vls4[1].length()-1) == ' ' || vls4[1].charAt(vls4[1].length()-1) == '"' || vls4[1].charAt(vls4[1].length()-1) == '}' || vls4[1].charAt(vls4[1].length()-1) == ']')
               vls4[1] = vls4[1].substring(0,vls4[1].length()-1);
           temp += (vls1[1] + "," + vls2[1] + "," + vls3[1] + "," + vls4[1]);
           String[] temV = temp.split(",");
           (new SQL_func()).insertProfesori(temV[0], temV[1],temV[2],temV[3],temV[4],temV[5],temV[6]);
        }
        return "Done";
    }
    @RequestMapping(
    value="/export",
    method=RequestMethod.GET)
    public String exportCSV(@RequestParam("id_profesor") String idP,@RequestParam("id_Materie") String idM,@RequestParam("id_session") String ses){
        Sessions obj = new Sessions();
        Access aObj = new Access();
        String uSername = (new SQL_func()).getUsername(ses);
        if(obj.checkSession(uSername) == false)
            return "Invalid session!";
        if(aObj.isAdministrator(uSername) == false && aObj.isProfesor(uSername) == false)
            return "Not allowed!";
        String result = (new SQL_func()).selectCatalogCSV(idM, idP);
        String[] arr = result.split(" ~");
        String csv = "";
        for(int i=0;i<arr.length;i++){
            arr[i] = arr[i].replace(" ",",");
            csv += (arr[i] + "\n");
        }
        return csv;
    }
    @RequestMapping(
    value="/profesori",
    method=RequestMethod.GET)
    public String getProfs(@RequestParam("id_session") String ses){
        Sessions obj = new Sessions();
        Access aObj = new Access();
        String uSername = (new SQL_func()).getUsername(ses);
        if(obj.checkSession(uSername) == false)
            return "Invalid session!";
        if(aObj.isAdministrator(uSername) == false)
            return "Not allowed!";
        String data = (new SQL_func()).selectAllProfs();
        String[] arr = data.split("~");
        String json = "[";
        for(int t=0;t<arr.length;t++){
            String[] vals = arr[t].split(" _ ");
            json +="{";
            json += ("\"id_profesor\":" + vals[0] + ", \"nume\":" + "\"" + vals[1] + "\"" + ", \"prenume\":" + "\"" + vals[2] + "\"" + ", \"materii\":");
            json += "[";
            data = (new SQL_func()).selectDenumireMaterii(vals[0]);
            String[] vals2 = data.split(" ~");
            for(int k=0;k<vals2.length;k++){
                String[] vel = vals2[k].split(":");
                if(k != vals2.length - 1)
                    json += "{ \"id_materie\":" + vel[1] + ", \"den_materie\":" + "\"" + vel[0] + "\"" +  "},";
                else
                    json += "{ \"id_materie\":" + vel[1] + ", \"den_materie\":" + "\"" + vel[0] + "\"" + " }";
            }
            json += "]";
            if(t != arr.length - 1)
                json += "},";
            else
                json += "}";
        }
        json += "]";
        return json;
    }
    
     @RequestMapping(
     value="/import",
     method=RequestMethod.POST)
     public String getCSV(@RequestBody String body,@RequestParam("id_session") String ses){
        Sessions Sobj = new Sessions();
        Access aObj = new Access();
        String uSername = (new SQL_func()).getUsername(ses);
        if(Sobj.checkSession(uSername) == false)
            return "Invalid session!";
        if(aObj.isAdministrator(uSername) == false && aObj.isProfesor(uSername) == false)
            return "Not allowed";
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
    public String getFormule(@RequestParam("id_profesor") String iDp,@RequestParam("id_session") String ses){
        /*String[] splArr = something.split(":");
        splArr[1]=splArr[1].replace("}","");
        splArr[1]=splArr[1].replace(" ","");
        splArr[1]=splArr[1].substring(1,splArr[1].length()-2);
        System.out.println(splArr[1]);*/
        //return (new FstParser().convertFormuleToJSON(iDp));
        Sessions obj = new Sessions();
        Access aObj = new Access();
        String uSername = (new SQL_func()).getUsername(ses);
        if(obj.checkSession(uSername) == false)
            return "Invalid session!";
        if(aObj.isAdministrator(uSername) == false && aObj.isProfesor(uSername) == false)
            return "Not allowed!";
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
    value="/materiiTot",
    method=RequestMethod.GET)
    public String getCurr(@RequestParam("id_session") String ses){
        Sessions obj = new Sessions();
        Access aObj = new Access();
        String uSername = (new SQL_func()).getUsername(ses);
        if(obj.checkSession(uSername) == false)
            return "Invalid session!";
        if(aObj.isAdministrator(uSername) == false && aObj.isProfesor(uSername) == false)
            return "Not allowed!";
        String str = (new SQL_func()).selectCurricula();
        String[] arr = str.split(" ~");
        String json = "[";
        for(int t=0;t<arr.length;t++){
            String[] vls = arr[t].split(" _ ");
            if(t != arr.length - 1)
                json += "{\"id_materie\":" +  vls[0] + ", \"denumire_materie\":" + "\"" + vls[1] + "\"" + "},";
            else
                json += "{\"id_materie\":" +  vls[0] + ", \"denumire_materie\":" + "\"" + vls[1] + "\"" + "}";
        }
        json += "]";
        return json;
    }
    
    @RequestMapping(
    value="/formule",
    method=RequestMethod.POST)
    public String postFormule(@RequestBody String something,@RequestParam("id_session") String ses){
        Sessions obj = new Sessions();
        Access aObj = new Access();
        String uSername = (new SQL_func()).getUsername(ses);
        if(obj.checkSession(uSername) == false)
            return "Invalid session!";
        if(aObj.isAdministrator(uSername) == false && aObj.isProfesor(uSername) == false)
            return "Not allowed!";
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
    public String getProfesor(@RequestParam("id_profesor") String iDp,@RequestParam("id_session") String ses){
        /*String[] splArr = something.split(":");
        splArr[1].replace("}","");
        splArr[1].replace(" ","");
        splArr[1]=splArr[1].substring(1,splArr[1].length()-3);
        System.out.println(splArr[1]);
        //return parser(selectMaterii(splArr[1]);*/
        //return (new FstParser().convertMateriiToJSON(iDp));
        Sessions sObj = new Sessions();
        Access aObj = new Access();
        String uSername = (new SQL_func()).getUsername(ses);
        if(sObj.checkSession(uSername) == false)
            return "Invalid session!";
        if(aObj.isAdministrator(uSername) == false && aObj.isProfesor(uSername) == false)
            return "Not allowed!";
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
    public String putProfesor(@RequestBody String something,@RequestParam("id_session") String ses){
        Sessions obj = new Sessions();
        Access aObj = new Access();
        String uSername = (new SQL_func()).getUsername(ses);
        if(obj.checkSession(uSername) == false)
            return "Invalid session!";
        if(aObj.isAdministrator(uSername) == false && aObj.isProfesor(uSername) == false)
            return "Not allowed!";
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
    public String getListaNote(@RequestParam("id_student") String idStud,@RequestParam("id_materie") String idMaterie,@RequestParam("id_session") String ses){
        Sessions sObj = new Sessions();
        Access aObj = new Access();
        String uSername = (new SQL_func()).getUsername(ses);
        if(sObj.checkSession(uSername) == false)
            return "Invalid session!";
        if(aObj.isStudent(uSername) == false)
            return "Not allowed!";
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
    public String getCursuri(@RequestParam("id_student") String idStud,@RequestParam("id_session") String ses){
        Sessions sObj = new Sessions();
        Access aObj = new Access();
        String uSername = (new SQL_func()).getUsername(ses);
        if(sObj.checkSession(uSername) == false)
            return "Invalid session!";
        if(aObj.isStudent(uSername) == false)
            return "Not allowed!";
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
