/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.tcp;

import java.io.DataOutputStream;
import java.io.OutputStream;

/**
 *
 * @author srikanth.possim
 */
public class Transmission implements Runnable{
    public OutputStream outToServer =null;
    public DataOutputStream out = null;
    public static boolean keeprunning = false;
    public static boolean senddata = false;
    public static boolean sendsuccess = false;
    public static int offset = 0;
    public static int length = 0;
    
    public Transmission(){
        try {
            outToServer = ETHCom.socket.getOutputStream();
            out = new DataOutputStream(outToServer);
            keeprunning = true;
            (new Thread(this)).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void closecon(){
        try {
            keeprunning = false;
            out.close();
            outToServer.close();
        } catch (Exception e) {
        }
    }
    public void run(){
        try {
            while(keeprunning){
                if(senddata){
                    senddata = false;
                    out.write(ETHCom.transmit_buffer,offset,length); 
                    sendsuccess = true;
                    //System.out.println("data sent from thread");
                }else{
                    Thread.sleep(10);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ETHCom.transmitError();
        }
        
    }
    
}
