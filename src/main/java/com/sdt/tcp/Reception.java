/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.tcp;

import java.io.DataInputStream;
import java.io.InputStream;

/**
 *
 * @author srikanth.possim
 */
public class Reception implements Runnable{
    public InputStream inFromServer = null;
    public DataInputStream in = null;
    
    public Reception(){
        try {
            inFromServer = ETHCom.socket.getInputStream();
            in =  new DataInputStream(inFromServer);
            
            (new Thread(this)).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void closecon(){
        try {
            in.close();
            inFromServer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public void run(){
        try {
            while (true) {  
                int temp = in.readUnsignedByte();
                //System.out.print(temp+",");
                ETHCom.receive_buffer.add(temp);
                //System.out.println(in.readUnsignedByte());
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            if(ETHCom.status)
                ETHCom.receiveError();
        }
        System.out.println("Ending Reception");
    }
}
