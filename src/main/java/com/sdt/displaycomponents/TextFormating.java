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
public class TextFormating {
    
    
    public static String doTime(String value){//incomplete
        String newvalue = "";
        int dotcount =0;
        int pos[] = new int[3];
        String []value1 = new String[3];
        pos[0] = value.indexOf(':');
        if(pos[0] > -1){
            dotcount =1;
            pos[1] =  value.indexOf(':',pos[0]+1);
            pos[2] = -1;
            value1[0] = value.substring(0,pos[0]);
            value1[1] = "";
            value1[2] = "";
            if(pos[1] > pos[0]){
                dotcount = 2;
                pos[2] = value.indexOf(':',pos[1]+1);
                value1[1] = value.substring(pos[0]+1,pos[1]);
                if(pos[2] > pos[1]){//this is error
                    dotcount = 3;
                    value1[2] = value.substring(pos[2]+1,pos[3]);
                }else{//this is normal
                    value1[2] = value.substring(pos[2]+1);
                }
            }else{
                value1[1] = value.substring(pos[0]+1);
            }
        }
        
        if(dotcount == 0){
            newvalue = checkTimeValue(value);
        }else{
            String temp = checkTimeValue(value1[0]);
            if(temp.length()==1)
                newvalue = "0"+temp;
            else if(temp.length()==1)
                newvalue = temp;
        }
        
         if(dotcount >= 1){
             newvalue = newvalue + ":" +checkTimeValue(value1[1]); 
         }
         
         if(dotcount >= 2){
             newvalue = newvalue + ":" +checkTimeValue(value1[2]); 
         }
        
        return newvalue;
    }
    
    static String checkTimeValue(String value){//incomplete
        String newvalue = "";
        
        return newvalue;
    }
    
    public static String doIPAddress(String value){
        String newvalue = "";
        
         int dotcount =0;
         int pos[] = new int[4];
         String []value1 = new String[4];
         //System.out.println(value.substring(0, value.length()-1)+"t");
        pos[0] = value.indexOf('.');
        //System.out.println(pos);
        if(pos[0] > -1){
            
            dotcount =1;
            pos[1] =  value.indexOf('.',pos[0]+1);
            pos[2] = -1;
            pos[3]= -1;
            value1[0] = value.substring(0,pos[0]);
            value1[1] = "";
            value1[2] = "";
            value1[3] = "";
            if(pos[1] > pos[0]){
                dotcount = 2;
                pos[2] = value.indexOf('.',pos[1]+1);
                value1[1] = value.substring(pos[0]+1,pos[1]);
                if(pos[2] > pos[1]){
                    dotcount = 3;
                    pos[3] = value.indexOf('.',pos[2]+1);
                    value1[2] = value.substring(pos[1]+1,pos[2]);
                    if(pos[3] > pos[2]){//this is error
                        dotcount = 4;
                        value1[3] = value.substring(pos[2]+1,pos[3]);
                    }else{//this is normal
                        value1[3] = value.substring(pos[2]+1);
                    }
                }else{
                    value1[2] = value.substring(pos[1]+1);
                }
            }else{
                value1[1] = value.substring(pos[0]+1);
            }

            //System.out.println(value1[0]+","+value1[1]+","+value1[2]+","+value1[3]);
            //System.out.println(pos+","+pos1+","+value1);
            if(pos[1]== pos[0]+1)
            {
               // this will be handled while Retriving value
            }
        }
        
        if(dotcount == 0){
            newvalue = checkValue(value);
            //if(newvalue.length()==3)
             //   newvalue = newvalue + ".";
        }else{
            newvalue = checkValue(value1[0]);            
        }  
        if(dotcount >= 1){
            newvalue = newvalue + "." +checkValue(value1[1]); 
            //if(dotcount == 1 && checkValue(value1[1]).length()==3)
             //   newvalue = newvalue + ".";
        }
        if(dotcount >= 2){
            newvalue = newvalue + "." +checkValue(value1[2]); 
            //if(dotcount == 2 && checkValue(value1[2]).length()==3)
             //   newvalue = newvalue + ".";
        }
        if(dotcount >= 3){
            newvalue = newvalue + "." +checkValue(value1[3]); 
        }
        
        return newvalue;
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
    static String checkValue(String value){
        String newvalue = "";
       // System.out.println(newvalue);
        if(value.length()> 0){
            String char1 = value.substring(0, 1);
            String char2 = "";
            String char3 = "";
            boolean proceed = false;
            if(value.length() > 1)
                char2 = value.substring(1, 2);
            if(value.length() >=3)
                char3 = value.substring(2, 3);
            if(isNumber(char1)){
                newvalue = newvalue + char1;
                proceed = true;
            }
            if(value.length() > 1 && isNumber(char2)){
                newvalue = newvalue + char2;
                proceed = true;
            }
            if(value.length() >=3 && isNumber(char3)){
                newvalue = newvalue + char3;
                proceed = true;
            }
            
            if(proceed && Integer.parseInt(newvalue) > 255 ){
                 //System.out.println(newvalue);
                newvalue = newvalue.substring(0, 2);
                 //System.out.println(newvalue);
            }
        }
        
        return newvalue;
    }
    public static String doTimeZone(String old_value ,String value){
        String newvalue = "";
        if(value.length()!=0){
            String new_temp="";
            boolean sign = false;
            boolean hours = false;
            boolean seperator = false;
            boolean minutes = true;
            int hour=0;
            int minute =0;
            for(int i = 0 ;  i < value.length();i++){
                //check if it contains only + ,- , : ,0-9 these char
                if((value.charAt(i)==43 || value.charAt(i)==45 || (value.charAt(i)>=48 && value.charAt(i)<=58))&& i < 7){
                    boolean invalid = false;
                    if(i>0 && (value.charAt(i)==43|| value.charAt(i)==45))
                        invalid = true;
                    if(i== 0 && (value.charAt(i)==43|| value.charAt(i)==45))
                        sign = true;
                    if(value.charAt(i) == 58){
                        if(!seperator)
                            seperator=true;
                        else
                            invalid = true;
                    }
                    if(!seperator && (value.charAt(i)>=48 && value.charAt(i)<=57)){
                        if(hour<10)
                            hour= hour*10+Integer.parseInt(value.charAt(i)+"");
                        else 
                            invalid = true;
                        if(hour > 24)
                            invalid = true;
                    }else if(seperator && (value.charAt(i)>=48 && value.charAt(i)<=57)){
                        if(minute<10)
                            minute = minute*10 + Integer.parseInt(value.charAt(i)+"");
                        else
                            invalid = true;
                        if(minute >59)
                            invalid = true;
                    }
                    if(!invalid)
                        new_temp+=value.charAt(i);
                }
            }
            newvalue = new_temp;
        }
        return newvalue;
    }
    
    public static String doIntervalSec(String old_value ,String value){
        String newvalue = "";
        if(value.length()!=0){
            String new_temp="";
            boolean sign = false;
            boolean hours = false;
            boolean seperator = false;
            boolean minutes = true;
            int hour=0;
            int minute =0;
            for(int i = 0 ;  i < value.length();i++){
                //check if it contains only  : ,0-9 these char
                if((value.charAt(i)>=48 && value.charAt(i)<=58)&& i < 6){
                    boolean invalid = false;
                    /*if(i>0 && (value.charAt(i)==43|| value.charAt(i)==45))
                        invalid = true;
                    if(i== 0 && (value.charAt(i)==43|| value.charAt(i)==45))
                        sign = true;*/
                    if(value.charAt(i) == 58){
                        if(!seperator)
                            seperator=true;
                        else
                            invalid = true;
                    }
                    if(!seperator && (value.charAt(i)>=48 && value.charAt(i)<=57)){
                        if(hour<10)
                            hour= hour*10+Integer.parseInt(value.charAt(i)+"");
                        else 
                            invalid = true;
                        if(hour > 24)
                            invalid = true;
                    }else if(seperator && (value.charAt(i)>=48 && value.charAt(i)<=57)){
                        if(minute<10)
                            minute = minute*10 + Integer.parseInt(value.charAt(i)+"");
                        else
                            invalid = true;
                        if(minute >59)
                            invalid = true;
                    }
                    if(!invalid)
                        new_temp+=value.charAt(i);
                }
            }
            newvalue = new_temp;
        }
        return newvalue;
    }
}
