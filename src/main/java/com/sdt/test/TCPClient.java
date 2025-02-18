/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.test;

import com.sdt.data.ScoreSequenceBean;
import com.sdt.system.ErrorAlert;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.ArrayList;
import javafx.application.Platform;

/**
 *
 * @author Srikanth
 */
public class TCPClient {
    static ArrayList<ScoreSequenceBean> sequences = new ArrayList<>();
    static String datavals = "";
    public static void main(String args[]){
        DatagramSocket ds =null;
        while(true){
            try{
                if(ds==null){
                    ds = new DatagramSocket(4211);
                }
                byte[] receive = new byte[65535];
                DatagramPacket DpReceive = null;
                DpReceive = new DatagramPacket(receive, receive.length);
                ds.receive(DpReceive);
                String packet = data(receive).toString();
                System.out.println("packet" + packet);
                if(packet.indexOf("SDT")>=0){
                    String ipaddress = DpReceive.getAddress().getHostAddress();
                    System.out.println(ipaddress);
                    try {                        
                        ds.disconnect();
                        ds.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    Socket clientSocket = new Socket(ipaddress, 205);
                    DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                    BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    outToServer.write("BallReleased".getBytes());
                    char [] data = new char[256];
                    inFromServer.read(data);
                    int prefix_len = 5;//HandleEvents.generalSettings.getAutoScotringBean().getSerial_no().length();
                    //sequences.clear();                    
                    datavals = "";
                    for(int i=0;i<20;i++){
                        int start = (i*4)+prefix_len;
                        if(data[start]==0x55)
                            break;
                        ScoreSequenceBean ssb = new ScoreSequenceBean();
                        ssb.setBi_no(data[start]);
                        ssb.setBi_status(data[start+1]);
                        int byte1=data[start+2];
                        if(byte1<0)
                            byte1 = byte1+256;
                        int byte2=data[start+3];
                         if(byte2<0)
                            byte2 = byte2+256;
                         byte2 = (byte2<<8);
                         //System.out.println((byte2)+","+byte1);
                        int val = ((byte2)+(byte1));
                        ssb.setInterval(val);
                        //sequences.add(ssb);
                        datavals = datavals + "("+ssb.getBi_no()+","+ssb.getBi_status()+","+ssb.getInterval()+")";
                        System.out.print(i+"("+ssb.getBi_no()+","+ssb.getBi_status()+","+ssb.getInterval()+")");
                    }
                    /*Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            ErrorAlert.info(datavals);
                        }
                    });*/
                    break;
                }else{
                    
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static StringBuilder data(byte[] a)
    {
        if (a == null)
            return null;
        StringBuilder ret = new StringBuilder();
        int i = 0;
        while (a[i] != 0)
        {
            ret.append((char) a[i]);
            i++;
        }
        return ret;
    }
    
}
