/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.system;

import java.io.File;

/**
 *
 * @author srikanth.possim
 */
public class DirectoryManager {
    public static String parentFolder = null;
    public static void loadDefault(){
         try {
             parentFolder = new File(System.getProperty("user.dir")).getPath();             
         } catch (Exception e) {
             //e.printStackTrace();
             ErrorAlert.info(PropertyFileReader.getValue("Error_ParentFolder"));             
         }         
     }
}
