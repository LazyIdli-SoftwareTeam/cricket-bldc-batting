/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.logging;

import com.sdt.system.DirectoryManager;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 *
 * @author srikanth.possim
 */
public class LogManager {
    public final static Logger LOGGER =  Logger.getLogger(LogManager.class.getName()); 

    public static void initLogger(){
        if(DirectoryManager.parentFolder==null)
            DirectoryManager.loadDefault();
        //HandleStructure.logFile =  new File(HandleStructure.parentFolder+"/ErrorLogFile.log");
        try {
            File logfolder = new File(DirectoryManager.parentFolder+"/logManager");
            if(!logfolder.exists() )
                logfolder.mkdir();
            FileHandler fh =  new FileHandler(DirectoryManager.parentFolder+"/logManager/ErrorLogFile.log",1024*1024,10,true); 
            fh.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                SimpleDateFormat logTime = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
                Calendar cal = new GregorianCalendar();
                cal.setTimeInMillis(record.getMillis());
                return record.getLevel()
                        + logTime.format(cal.getTime())
                        + " || "
                        + record.getSourceClassName().substring(
                                record.getSourceClassName().lastIndexOf(".")+1,
                                record.getSourceClassName().length())
                        + "."
                        + record.getSourceMethodName()
                        + "() : "
                        + record.getMessage() + "\n";
            }
        });
            LOGGER.addHandler(fh);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logInfo("Application Opened");
    }
    
    public static void logInfo(String message){
        LOGGER.log(Level.INFO, message); 
        //java.util.logging.LogManager lgmngr = java.util.logging.LogManager.getLogManager(); 
        //Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME); 
    }
    public static void logWarning(String message){
        LOGGER.log(Level.WARNING, message); 
    }
    public static void logError(String message){
        LOGGER.log(Level.SEVERE, message); 
    }
}
