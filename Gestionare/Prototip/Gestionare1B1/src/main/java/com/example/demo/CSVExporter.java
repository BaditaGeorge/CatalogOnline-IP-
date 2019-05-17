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
import java.io.File;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

public class CSVExporter {
    private static final char DEFAULT_SEPARATOR = ',';
    public void writeLine(Writer w, List<String> values){
            writeLine(w,values,DEFAULT_SEPARATOR,' ');
    }
    public void writeLine(Writer w,List<String> values,char separators){
            writeLine(w,values,separators,' ');
    }
    public String followCSVformat(String value){
        String result=value;
        if(result.contains("\"")){
            result = result.replace("\"","\"\"");
        }
        return result;
    }
    public void writeLine(Writer w,List<String> values,char separators,char customQuote){
        boolean first = true;
        if(separators == ' '){
            separators = DEFAULT_SEPARATOR;
        }
        StringBuilder sb = new StringBuilder();
        for(String value:values){
            if(!first){
                sb.append(separators);
            }
            if(customQuote == ' '){
                sb.append(followCSVformat(value));
            }
            else{
                sb.append(customQuote).append(followCSVformat(value)).append(customQuote);
            }
            first = false;
        }
        sb.append("\n");
        try {
            w.append(sb.toString());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

