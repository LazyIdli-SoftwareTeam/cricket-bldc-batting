/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.system;

import java.util.ResourceBundle;

/**
 *
 * @author srikanth.possim
 */
public class PropertyFileReader {
    static ResourceBundle project;
    
    public static String getValue(String key){
        String tempstr="";
        if(project == null){
            project = ResourceBundle.getBundle("com.sdt.system.project");
        }
        tempstr=project.getString(key);
        return tempstr;
    }
    
    public static int getIntValue(String key){
        String tempstr="";
        if(project == null){
            project = ResourceBundle.getBundle("com.sdt.system.project");
        }
        tempstr=project.getString(key);
        int val = 0;
        try {
            val = Integer.parseInt(tempstr);
        } catch (Exception e) {
        }
        return val;
    }
    
    public static double getDoubleValue(String key){
        String tempstr="";
        if(project == null){
            project = ResourceBundle.getBundle("com.sdt.system.project");
        }
        tempstr=project.getString(key);
        double val = 0;
        try {
            val = Double.parseDouble(tempstr);
        } catch (Exception e) {
        }
        return val;
    }
}
