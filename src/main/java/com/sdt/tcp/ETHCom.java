/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.tcp;


import com.sdt.system.ErrorAlert;
import java.net.Socket;
import java.util.ArrayList;
import javafx.application.Platform;
/**
 *
 * @author srikanth.possim
 */
public class ETHCom {
    public static int buffer_size = 300;
    public static byte [] transmit_buffer = new byte[300];
    public static ArrayList<Integer> receive_buffer = new ArrayList<Integer>();
    public static char start_byte = '#';
    public static char end_byte = '!';
    public static boolean status = false;
    
    public static Socket socket = null;
    public static Transmission trsnsmission = null;
    public static Reception reception = null;
    
    public static void connect(String ip , int port){
        if(!status){
            try {
                if(socket!=null ){
                    try {
                        socket.close();
                        socket=null;
                    } catch (Exception e) {
                    }                    
                }
                socket = new Socket(ip, port);
                if(socket.isConnected()){
                    status = true;                    
                    //start threads
                    trsnsmission = new Transmission();
                    reception = new Reception();
                    System.out.println("Ethernet Connected");
                }else{
                    //ErrorAlert.alert("Unable to Connect Please Try Again");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                         //ErrorAlert.alert("Unable to Connect Please Try Again");
                    }
                });               
            }            
        }
    }
    
    public static void disconnect(){
        try {
            if(status){
                if(socket.isConnected())
                    socket.close();
                if(socket != null || !socket.isConnected()){
                    status = false;
                }
            }
            //close threads
            trsnsmission.closecon();
            reception.closecon();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public static void transmitError(){
        disconnect();
    }
    
    public static void receiveError(){
        disconnect();
    }
    
    public static void clearTransmitBuffer(){
        for(int i = 0 ; i < buffer_size ; i++){
            transmit_buffer[i] = 0;
        }
    }
    
    public static void sendData(byte [] data){
        clearTransmitBuffer();
        if(status && data != null && !Transmission.senddata){
            for(int i = 0 ; i < data.length ; i++){
                transmit_buffer[i] = data[i];
                //System.out.println(data[i]);
            }
            Transmission.senddata = true;
            Transmission.sendsuccess = false;
            Transmission.offset=0;
            Transmission.length =data.length;
        }else{
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    //ErrorAlert.alert("Transmit Error");
                    System.out.println("Transmit Error");
                }
            });
        }        
    }
    //static boolean packet_received = false;
    public static int receiveByte(){
        int tempbyte=-1;//if -1 time out error
        int timer=0;
        try {
            if(status){
                while(true){
                    //if(!packet_received&&receive_buffer.size() >= 300)
                    //    packet_received =true;                        
                    if(/*packet_received &&*/ receive_buffer.size() > 0){
                        tempbyte = receive_buffer.remove(0);
                        //System.out.println(tempbyte);
                        break;
                    }else 
                        Thread.sleep(10);
                    timer++;
                    if(timer >= 2){
                        //packet_received = false;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("buffer "+receive_buffer);
            e.printStackTrace();
        }        
        return tempbyte;
    }   
}
