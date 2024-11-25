/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.tcp;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author possi
 */
public class TCPServerCom implements Runnable{
    public static TCPServerCom this_obj = null;
    public boolean run_loop = true;
    public TCPServerCom(){
        this_obj=null;
        new Thread(this).start();
    }
    ServerSocket serversocket = null;
    Socket connectionSocket = null;
    int current_len = 0;
    public void run(){
        try {
            serversocket = new ServerSocket(8000);
            System.out.println("server started");
            while(run_loop){
                try{
                    if(connectionSocket==null){
                        connectionSocket = serversocket.accept();
                        System.out.println("Connection Received");
                        BufferedReader inFromClient =  new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                        DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                        new TCPServerReceive(inFromClient, outToClient);
                    }else{
                        Thread.sleep(100);
                        if(current_len!=TCPServerReceive.data.length()){
                            current_len = TCPServerReceive.data.length();
                        }else if(current_len!=0 && current_len==TCPServerReceive.data.length()){
                            current_len = 0;
                            try {
                              ObjectMapper objectMapper = new ObjectMapper();
                              objectMapper.readValue(TCPServerReceive.data, Object.class);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }                            
                        }
                    }                
                }catch (IOException ioe) {
                }catch (Exception e) {
                }
            }
        } catch (Exception e) {
        }
        
    }
}
