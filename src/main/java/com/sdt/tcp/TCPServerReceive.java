/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.tcp;

import java.io.BufferedReader;
import java.io.DataOutputStream;

/**
 *
 * @author possi
 */
public class TCPServerReceive implements Runnable{
    DataOutputStream outToClient=null;
    BufferedReader inFromClient=null;
    public TCPServerReceive(BufferedReader inFromClient,DataOutputStream outToClient){
        this.outToClient = outToClient;
    	this.inFromClient = inFromClient;
        new Thread(this).start();
    }
    public static String data = "";
    public void run(){
        try {
            System.out.println("Waiting for data");
            int ctr = 0;
            while(true){
                int temp = inFromClient.read();
                //System.out.println(++ctr+","+temp);
                data=data+(char)temp;
                System.out.println("s" + data);
            }
        } catch (Exception e) {
            e.printStackTrace();
            TCPServerCom.this_obj.connectionSocket=null;
        }
    }
}
