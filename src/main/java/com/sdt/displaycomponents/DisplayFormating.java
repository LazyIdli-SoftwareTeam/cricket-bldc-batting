/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.displaycomponents;

/**
 *
 * @author srikanth.possim
 */
public class DisplayFormating {
    
    public static String intToIntervalSec(int value){
        String result = "00:10";
        if(value >= 60){
            int temp_val = value/60;
            int min = temp_val % 60;
            int hour = temp_val / 60;
            if(min < 10){
                result = hour + ":0" + min;
            }else{
                result =  hour + ":" + min;
            }
        }
        return result;
    }
     public static int IntervalSecStrToInt(String value){
        int result = 600;
        if(value.length()>0){
            boolean signed = false;
            /*if(value.charAt(0)==43){
                signed = true;
                result[0] = 'P';
            }else if(value.charAt(0)==45){
                signed = true;
                result[0] = 'M';
            }*/
            int pos = 0;
            /*if(signed)
                pos = 1;*/
            int min=0;
            int hour=0;
            int sep_pos = value.indexOf(":");
            if(sep_pos == -1){
                hour = Integer.parseInt(value.substring(pos));
            }else{
                hour = (char)Integer.parseInt(value.substring(pos,sep_pos));
                try {
                    min = (char)Integer.parseInt(value.substring(sep_pos+1));
                } catch (Exception e) {
                }                
            }
            result = (hour * 3600) + (min *60);
        }        
        return result;
     }
    public static String charToTimeZone(char [] value){
        String result = "+5:30";
        if(value != null){
            if(value[0] == 'P')
                result = "+"+(int)value[1]+":"+(int)value[2];
            else
                result = "-"+(int)value[1]+":"+(int)value[2];
        }
        return result;
    }
    
     public static char [] TimeZoneStrToChar(String value){
        char [] result = new char[3];
        if(value.length()>0){
            boolean signed = false;
            if(value.charAt(0)==43){
                signed = true;
                result[0] = 'P';
            }else if(value.charAt(0)==45){
                signed = true;
                result[0] = 'M';
            }
            int pos = 0;
            if(signed)
                pos = 1;
            int sep_pos = value.indexOf(":");
            if(sep_pos == -1){
                result[1] = (char)Integer.parseInt(value.substring(pos));
                result[2] = 0;
            }else{
                result[1] = (char)Integer.parseInt(value.substring(pos,sep_pos));
                result[2] = (char)Integer.parseInt(value.substring(sep_pos+1));
            }
        }        
        return result;
     }
    
    public static String charToIPAddress(char [] value){
        String result = "0.0.0.0";
        if(value != null)
            result = (int)value[0]+"."+(int)value[1]+"."+(int)value[2]+"."+(int)value[3];
        return result;
    }
    
    public static char [] iPAddressStrToChar(String value){
        char [] result = new char[4];
        result[0] = 0;
        result[1] = 0;
        result[2] = 0;
        result[3] = 0;
        int pos[] = new int[4];
        pos[0] = value.indexOf(".");
        String temp[] = new String[4];
        if(pos[0] != -1){
            temp[0] = value.substring(0, pos[0]);
            if(isNumber(temp[0]))
                result[0] = (char)Integer.parseInt(temp[0]);
            pos[1] = value.indexOf(".",pos[0]+1);
            if(pos[1] > pos[0]){
                temp[1] = value.substring(pos[0]+1, pos[1]);
                if(isNumber(temp[1]))
                    result[1] = (char)Integer.parseInt(temp[1]);
                
                pos[2] = value.indexOf(".",pos[1]+1);
                if(pos[2] > pos[1]){
                    temp[2] = value.substring(pos[1]+1, pos[2]);
                    if(isNumber(temp[2]))
                        result[2] = (char)Integer.parseInt(temp[2]);
                    if(pos[2] < value.length()){
                        temp[3] = value.substring(pos[2]+1);
                         if(isNumber(temp[3]))
                            result[3] = (char)Integer.parseInt(temp[3]);
                    }
                    
                   
                }else if(pos[1] < value.length()){
                     temp[2] = value.substring(pos[1]+1);
                    if(isNumber(temp[2]))
                       result[2] = (char)Integer.parseInt(temp[2]);
                }
            }else if(pos[0] < value.length()){
                temp[1] = value.substring(pos[0]+1);
               if(isNumber(temp[1]))
                  result[1] = (char)Integer.parseInt(temp[1]);
           }
                
            
            
        }else if(value.length() == 0 || !isNumber(value)){
            // do nothing
        }else if(isNumber(value)){
            result[0] = (char)Integer.parseInt(value);
        }
        return result;
    }
    
    static boolean isNumber(String value){
        boolean isnumber = false;
        try {
            Integer.parseInt(value);
            isnumber=true;
        } catch (Exception e) {
        }
        return isnumber;
    }
}
